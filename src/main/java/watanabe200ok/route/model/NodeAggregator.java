package watanabe200ok.route.model;

import java.util.List;

/**
 * ノードを集約するモデルです。
 *
 */
public interface NodeAggregator {

    /**
     * ノードの数を取得します。
     *
     * @return ノードの数
     */
    int numberOfNodes();

    /**
     * インデックスを指定してノードを取得します。
     *
     * @param index インデックス
     * @return インデックスで指定されたノード
     */
    Node get(int index);

    /**
     * 全てのノードを取得します。
     *
     * @return 全てのノード
     */
    List<Node> getNodes();

    /**
     * ノード間の距離を取得します。
     *
     * @param from 出発ノード
     * @param to 到達ノード
     * @return ノード間の距離
     */
    double distance(Node from, Node to);

}
