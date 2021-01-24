package io.github.aluria.wclick.tooling.util.paginator;

import java.util.ArrayList;
import java.util.List;

public class Paginator {

    public static boolean hasPage(int size, int page, List<?> collection) {
        if (page < 0) return false;

        if (page > getTotal(size, collection)) return false;

        final List<?> list = getPage(size, page, collection);

        return list != null && !list.isEmpty();
    }

    public static int getTotal(int size, List<?> collection) {
        return (int) Math.ceil((double) collection.size() / size);
    }

    public static <T> List<T> getPage(int size, int page, List<T> collection) {
        if (page < 0 || page > getTotal(size, collection)) return null;

        int min = page * size;
        int max = min + size;

        if (max > collection.size()) max = collection.size();

        final List<T> objects = new ArrayList<>();

        for (int i = min; max > i; i++) {
            objects.add(collection.get(i));
        }

        return objects;
    }
}
