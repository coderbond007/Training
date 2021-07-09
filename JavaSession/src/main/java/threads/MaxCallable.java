package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class MaxCallable implements Callable<Integer> {
    List<Integer> list;
    static final int ZERO = 0;

    public MaxCallable(int... a) {
        list = new ArrayList<>();
        for (int aa : a) list.add(aa);
    }

    @Override
    public Integer call() throws Exception {
        Integer max = list.get(ZERO);
        for (int i = 1; i < list.size(); ++i) {
            max = Math.max(max, list.get(i));
        }
        System.out.println("Max for current callable : " + max);
        return max;
    }
}
