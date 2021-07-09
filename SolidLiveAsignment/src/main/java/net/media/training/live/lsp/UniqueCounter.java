package net.media.training.live.lsp;

/**
 * Created by IntelliJ IDEA.
 * User: goyalamit
 * Date: Jul 12, 2011
 * Time: 12:51:00 PM
 * To change this template use File | Settings | File Templates.
 */
public class UniqueCounter {
    private Set elements;

    public UniqueCounter(Set elements) {
        this.elements = elements;
    }

    public int execute() {
        elements.add("hello");
        elements.add(1);
        elements.add(3);
        elements.add("hello");
        elements.add(1);
        return elements.size();
    }

    public static void main(String[] args) {
        UniqueCounter counter = new UniqueCounter(new Set());
        System.out.println(counter.execute());
    }
}
