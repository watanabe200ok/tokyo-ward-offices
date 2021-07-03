package watanabe200ok.route.util;

import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * ヘルパーです。
 */
public class Helper {
    /**
     * 指定されたSupplierを利用してオブジェクトを生成します。
     *
     * @param supplier Supplier
     * @param size 生成するオブジェクトの数
     * @return 生成したオブジェクトのリスト
     */
    public static <T> List<T> create(
            final Supplier<T> supplier,
            final int size) {
        final Iterator<T> iterator = new Iterator<>() {
            @Override
            public boolean hasNext() {
                return true;
            }
            @Override
            public T next() {
                return supplier.get();
            }
        };
        final Spliterator<T> spliterator = Spliterators.spliteratorUnknownSize(iterator, Spliterator.NONNULL);
        return StreamSupport.stream(spliterator, false)
                .limit(size)
                .collect(Collectors.toList());
    }

}
