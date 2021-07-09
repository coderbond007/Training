package net.media.training.live.lsp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: goyalamit
 * Date: Jul 12, 2011
 * Time: 1:05:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class MembershipChecker {

    public static boolean check(List elements, SetInterface uniqueElements) {
        for (Object element : elements) {
            if (!uniqueElements.isMember(element))
                return false;
        }
        return true;
    }

    public static void main(String[] args) {
        List elements = new ArrayList() {{
            add("a");
            add("b");
            add("c");
        }};

        SetInterface uniqueElements = new PersistentSet();
        uniqueElements.add("c");
        uniqueElements.add("b");
        uniqueElements.add("a");

        System.out.println("Check1: " + MembershipChecker.check(elements, uniqueElements));

        List anotherElements = new ArrayList() {{
            add("a");
            add("c");
            add("d");
        }};
        System.out.println("Check2: " + MembershipChecker.check(anotherElements, uniqueElements));
    }
}
