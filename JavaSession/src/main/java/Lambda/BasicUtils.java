package Lambda;

import java.util.Comparator;
import java.util.List;

public class BasicUtils {

    public Integer min(List<Integer> list, Comparator<Integer> comparator) {
        Integer min = Integer.MAX_VALUE;
        for (Integer aa : list) {
            if (comparator.compare(aa, min) < 0) {
                min = aa;
            }
        }
        return min;
    }
}
