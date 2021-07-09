package Lambda;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static final Integer[] baseArray = new Integer[]{23, 1, 373, 1873, 347, 1, 23};

    public static void main(String[] args) {
        sampleCall1();
    }

    private static void sampleCall1() {

    }

    public static void sampleCall() {
        Integer[] a = generateBoxedArrayCopy();
        List<Integer> list = Arrays.stream(a).filter(x -> x % 2 == 0).map(x -> x * x).collect(Collectors.toList());
        System.out.println(list);
    }

    public static Integer[] generateBoxedArrayCopy() {
        Integer[] a = new Integer[baseArray.length];
        System.arraycopy(baseArray, 0, a, 0, baseArray.length);
        return a;
    }

    public static int[] generateUnBoxedArrayCopy() {
        int n = baseArray.length;
        int[] a = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = baseArray[i].intValue();
        }
        return a;
    }

    public static void computeUsingFunctions() {
        List<Integer> list = Arrays.asList(baseArray);
        System.out.println(list.size());
    }

    public static void computeUsingBasic() {
        int[] a = new int[baseArray.length];
        System.arraycopy(baseArray, 0, a, 0, baseArray.length);
        System.out.println(minUsingStream(a));
        System.out.println(min(a));
        System.out.println(maxUsingStream(a));
        System.out.println(max(a));
    }

    public static int minUsingStream(int[] a) {
        return Arrays.stream(a).min().getAsInt();
    }

    public static int maxUsingStream(int[] a) {
        return Arrays.stream(a).max().getAsInt();
    }

    public static int min(int[] a) {
        int min = a[0];
        for (int cur : a) if (cur < min) min = cur;
        return min;
    }

    public static int max(int[] a) {
        int max = a[0];
        for (int cur : a) if (cur > max) max = cur;
        return max;
    }
}
