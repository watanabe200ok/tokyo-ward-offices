package watanabe200ok.route.model;

import java.util.Collections;
import java.util.List;

/**
 * 経路を表現するモデルです。
 *
 */
public class Route {
    private List<Node> route;
    private double distance;

    /**
     * @param nodes ノードの不変情報
     * @param route この経路の順序で並ぶノードのリスト
     */
    public Route(
            final NodeAggregator nodes,
            final List<Node> route) {
        this.route = Collections.unmodifiableList(route);
        this.distance = distance(nodes, route);
    }

    /**
     * 経路の総距離を計算します。
     *
     * @param nodes ノードの不変情報
     * @param route この経路の順序で並ぶノードのリスト
     * @return 経路の総距離
     */
    static double distance(
            final NodeAggregator nodes,
            final List<Node> route) {
        final int lastIndex = route.size() - 1;
        Node from = route.get(lastIndex);
        Node to = route.get(0);
        double sum = nodes.distance(from, to);
        for (int i = 0; i < lastIndex; i++) {
            from = route.get(i);
            to = route.get(i + 1);
            sum += nodes.distance(from, to);
        }
        return sum;
    }

    /**
     * この経路の順序で並ぶノードのリストを取得します。
     *
     * @return この経路の順序で並ぶノードのリスト
     */
    public List<Node> getRoute() {
        return route;
    }

    /**
     * この経路の総距離を取得します。
     *
     * @return この経路の総距離
     */
    public double getDistance() {
        return distance;
    }

    @Override
    public String toString() {
        return "Route [distance=" + distance + ", route=" + route + "]";
    }

}
