package io.github.aluria.wclick.composite.linked;

import com.google.common.collect.Lists;
import io.github.aluria.wclick.composite.Manager;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

public class LinkedManager<T> implements Manager<T> {

    private final List<T> collection = Lists.newLinkedList();

    @Override
    public List<T> getCollection() {
        return collection;
    }

    @Override
    public void put(T value) {
        collection.add(value);
    }

    @Override
    public void remove(T value) {
        collection.remove(value);
    }

    @Override
    public void putAll(Collection<T> collection) {
        this.collection.addAll(collection);
    }

    @Override
    public void removeAll(Collection<T> collection) {
        this.collection.removeAll(collection);
    }

    @Override
    public T searchBy(Predicate<T> predicate) {
        for(T content : collection) {
            if(predicate.test(content)) return content;
        }

        return null;
    }

    @Override
    public List<T> fill(Predicate<T> predicate) {
        final List<T> contents = Lists.newArrayList();

        for(T content : collection) {
            if(predicate.test(content)) contents.add(content);
        }

        return contents;
    }
}
