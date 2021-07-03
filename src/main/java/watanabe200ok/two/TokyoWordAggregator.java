package watanabe200ok.two;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opencsv.CSVReader;

import watanabe200ok.route.model.Node;
import watanabe200ok.route.model.NodeAggregator;

/**
 * 東京23区を集約するモデルです。
 *
 */
public class TokyoWordAggregator implements NodeAggregator {
    private final Logger logger = LoggerFactory.getLogger(TokyoWordAggregator.class);
    private final String DATA_RESOURCE_NAME = "data.csv";
    private final List<Node> nodes = List.of(TokyoWord.values());
    private final double[][] distances;

    TokyoWordAggregator() {
        try (final InputStream is = getClass().getResourceAsStream(DATA_RESOURCE_NAME);
                final InputStreamReader isr = new InputStreamReader(is);
                final CSVReader reader = new CSVReader(isr)) {
            distances = toTable(reader.readAll());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    static double[][] toTable(final List<String[]> allLines) {
        return allLines.stream()
            .map(TokyoWordAggregator::toDouble)
            .toArray(double[][]::new);
    }

    static double[] toDouble(final String[] line) {
        return Stream.of(line)
            .mapToDouble(Double::parseDouble)
            .toArray();
    }

    @Override
    public int numberOfNodes() {
        return nodes.size();
    }

    @Override
    public Node get(int index) {
        return nodes.get(index);
    }

    @Override
    public List<Node> getNodes() {
        return nodes;
    }

    @Override
    public double distance(Node from, Node to) {
        final int fromIndex = nodes.indexOf(from);
        final int toIndex = nodes.indexOf(to);
        return distances[fromIndex][toIndex];
    }

    @Override
    public String toString() {
        return "TokyoWordOffices [nodes=" + nodes + "]";
    }

}
