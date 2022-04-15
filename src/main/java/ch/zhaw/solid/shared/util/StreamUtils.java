package ch.zhaw.solid.shared.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StreamUtils {

    public static <T> Stream<T> collectionStream(Collection<T> collection) {
        return (collection == null || collection.isEmpty()) ? Stream.empty() : collection.stream();
    }

    public static <T> Stream<T> iterableStream(Iterable<T> iterable) {
        return (iterable == null) ? Stream.empty() : StreamSupport.stream(iterable.spliterator(), false);
    }

    public static <T> Stream<T> arrayStream(T[] array) {
        return Arrays.stream(array);
    }
}
