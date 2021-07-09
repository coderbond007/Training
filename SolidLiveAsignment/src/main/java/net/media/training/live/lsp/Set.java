package net.media.training.live.lsp;

import java.util.LinkedHashSet;

/**
 * Created by IntelliJ IDEA.
 * User: goyalamit
 * Date: Jul 11, 2011
 * Time: 12:52:22 PM
 * To change this template use File | Settings | File Templates.
 */
public class Set<T> implements SetInterface<T> {
    protected java.util.Set<T> innerSet;

    public Set() {
        innerSet = new LinkedHashSet<>();
    }

    public void add(final T element) {
        innerSet.add(element);
    }

    public boolean isMember(final T element) {
        return innerSet.contains(element);
    }

    public int size() {
        return innerSet.size();
    }
}
