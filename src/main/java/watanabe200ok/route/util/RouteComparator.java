package watanabe200ok.route.util;

import java.util.Comparator;

import watanabe200ok.route.model.Route;

/**
 * 経路を総距離の自然順序に基づいて比較するComparatorです。
 *
 */
public class RouteComparator implements Comparator<Route> {

    @Override
    public int compare(Route that, Route other) {
        if (that.getDistance() == other.getDistance()) {
            return 0;
        }
        return that.getDistance() < other.getDistance() ? Integer.MIN_VALUE : Integer.MAX_VALUE;
    }

}
