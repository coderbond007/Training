package net.media.training.live.lsp;

import java.util.LinkedHashSet;

/**
 * Created by IntelliJ IDEA.
 * User: goyalamit
 * Date: Jul 11, 2011
 * Time: 12:56:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class PersistentSet implements SetInterface<String> {
    private java.util.Set<PersistentObject<String>> innerSet;

    public PersistentSet() {
        innerSet = new LinkedHashSet<>();
    }

    public void add(String element) {
        PersistentObject<String> persistentObject = new PersistentObject<>(element);
        innerSet.add(persistentObject);
    }

    public boolean isMember(String element) {
        for (Object o : innerSet) {
            if (o.equals(element))
                return true;
        }
        return false;
    }
}