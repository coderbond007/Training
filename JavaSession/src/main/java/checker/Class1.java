package checker;

import java.util.HashSet;
import java.util.Set;

public class Class1 {
    Set<String> strings;

    public Class1() {
        this.strings = new HashSet<>();
    }

    public boolean check(String name) {
        return strings.contains(name);
    }
}
