package watanabe200ok.route.core;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import watanabe200ok.route.model.Node;
import watanabe200ok.route.model.NodeAggregator;
import watanabe200ok.route.model.Route;
import watanabe200ok.route.util.Helper;
import watanabe200ok.route.util.RouteComparator;

/**
 * 経路を求めます。
 *
 */
public class RouteCaluculator {
    private final Logger logger = LoggerFactory.getLogger(RouteCaluculator.class);
    private static final Random random = new Random();

    /**
     * 経路を求めます。
     *
     * @param nodes ノードの不変情報
     * @param parentSize 親となる経路の数
     * @param childSize 親を基に生成する子の経路の数
     * @param otherSize 新たに生成する経路の数
     * @param swapRatio 子を生成する際に親の経路をどのくらい変更するかに影響を与える割合
     * @param numberOfGenerations 世代数
     * @return 全世代における最短経路
     */
    public Route execute(
            final NodeAggregator nodes,
            final int parentSize,
            final int childSize,
            final int otherSize,
            final double swapRatio,
            final int numberOfGenerations) {
        logger.info("NodeAggregator: {}", nodes);
        final RouteComparator comparator = new RouteComparator();
        final int totalSize = parentSize + childSize + otherSize;

        List<Route> routes = createRandomRoutes(nodes, totalSize);
        Collections.sort(routes, comparator);
        Route best = routes.iterator().next();

        for (int count = 0; count < numberOfGenerations; count++) {
            final List<Route> parents = routes.subList(0, parentSize);
            final List<Route> children = createChildRoutes(nodes, parents, childSize, swapRatio);
            final List<Route> others = createRandomRoutes(nodes, otherSize);

            // 次の世代の為にリストを初期化する
            routes = new ArrayList<>(parents);
            routes.addAll(children);
            routes.addAll(others);

            Collections.sort(routes, comparator);
            best = routes.iterator().next();
            logger.debug("{}", best);
        }

        return best;
    }

    /**
     * ランダムな経路を生成します。
     *
     * @param nodes ノードの不変情報
     * @param size 生成する経路の数
     * @return 生成した経路
     */
    List<Route> createRandomRoutes(
            final NodeAggregator nodes,
            final int size) {
        final Supplier<Route> supplier = () -> {
            final List<Node> route = new ArrayList<>(nodes.getNodes());
            Collections.shuffle(route);
            return new Route(nodes, route);
        };
        return Helper.create(supplier, size);
    }

    /**
     * 親を基に子を生成します。
     *
     * @param nodes ノードの不変情報
     * @param parents 親
     * @param childSize 生成する経路の数
     * @param swapRatio 子を生成する際に親の経路をどのくらい変更するかに影響を与える割合
     * @return 生成した経路
     */
    List<Route> createChildRoutes(
            final NodeAggregator nodes,
            final List<Route> parents,
            final int childSize,
            final double swapRatio) {
        final Supplier<Route> supplier = () -> {
            final int index = random.nextInt(parents.size());
            final Route parent = parents.get(index);
            final List<Node> childRoute = new ArrayList<>(parent.getRoute());
            final int nodeSize = childRoute.size();
            final int swapSize = random.nextInt((int)(swapRatio * nodeSize)) + 1;
            // 親の経路を基にswapSize回ノードの順序を入れ替える
            for (int i = 0; i < swapSize; i++) {
                final int from = random.nextInt(nodeSize);
                final int to = random.nextInt(nodeSize);
                final Node target = childRoute.remove(from);
                childRoute.add(to, target);
            }
            return new Route(nodes, childRoute);
        };
        return Helper.create(supplier, childSize);
    }

}
