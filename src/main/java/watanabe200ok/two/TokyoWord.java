package watanabe200ok.two;

import watanabe200ok.route.model.Node;

/**
 * 東京23区を列挙します。
 */
public enum TokyoWord implements Node {
    千代田,
    中央,
    港,
    新宿,
    文京,
    台東,
    墨田,
    江東,
    品川,
    目黒,
    大田,
    世田谷,
    渋谷,
    中野,
    杉並,
    豊島,
    北,
    荒川,
    板橋,
    練馬,
    足立,
    葛飾,
    江戸川;

    @Override
    public String getNodeName() {
        return name();
    }

}
