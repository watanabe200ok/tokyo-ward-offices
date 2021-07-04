package watanabe200ok.two;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import watanabe200ok.route.core.RouteCaluculator;
import watanabe200ok.route.model.Node;
import watanabe200ok.route.model.Route;

public class TokyoWardOfficesRouteCaluculator {
    @Option(name="-p", aliases="--parents", usage="number of parents")
    private int parentSize = 2000;
    @Option(name="-c", aliases="--childlen", usage="number of childlen")
    private int childSize = 2000;
    @Option(name="-o", aliases="--others", usage="number of others")
    private int otherSize = 2000;
    @Option(name="-s", aliases="--swap", usage="gene swap ratio")
    private double swapRatio = 0.1;
    @Option(name="-g", aliases="--generations", usage="number of generations")
    private int numberOfGenerations = 2000;

    public void execute(
            final int parentSize,
            final int childSize,
            final int otherSize,
            final double swapRatio,
            final int numberOfGenerations) {
        final RouteCaluculator caluculator = new RouteCaluculator();
        final Route bestRoute = caluculator.execute(
                new TokyoWardAggregator(), parentSize, childSize, otherSize, swapRatio, numberOfGenerations);

        System.out.println("==================================");
        System.out.println(bestRoute.getDistance());

        final List<Node> sorted = sort(bestRoute.getRoute(), TokyoWard.千代田);
        System.out.println(sorted);
        Collections.reverse(sorted);
        System.out.println(sort(sorted, TokyoWard.千代田));
    }

    private List<Node> sort(final List<Node> original, final TokyoWard firstWord) {
        final int indexOfChiyoda = original.indexOf(firstWord);
        final List<Node> normalized = new ArrayList<>(original.subList(indexOfChiyoda, original.size()));
        if (indexOfChiyoda != 0) {
            normalized.addAll(original.subList(0, indexOfChiyoda));
        }
        return normalized;
    }

    public static void main(String[] args) {
        final TokyoWardOfficesRouteCaluculator tokyoWordOffices = new TokyoWardOfficesRouteCaluculator();
        tokyoWordOffices.doMain(args);
    }

    private void doMain(String[] args) {
        final CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
            return;
        }
        execute(parentSize, childSize, otherSize, swapRatio, numberOfGenerations);
    }

}
