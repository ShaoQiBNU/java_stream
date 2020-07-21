import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String args[]){
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl", "abc");

        // 生成stream
        Stream<String> s = null;

        s = strings.stream();

        s = strings.parallelStream();


        // filter 筛选元素>3的个数
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        System.out.println(list.stream().filter(value -> value > 3).count());

        System.out.println();

        // sorted
        List<Integer> list2 = Arrays.asList(11, 22, 22, 3, 14, 5);
        list2.stream().filter(value -> value > 3).sorted().distinct().forEach(System.out::println);
        System.out.println();
        list2.stream().filter(value -> value > 3).sorted(Comparator.reverseOrder()).distinct().forEach(System.out::println);

        System.out.println();

        // map
        List<String> upperList = Arrays.asList("a","b","c");
        List<String> res = upperList.stream().map(value -> value.toUpperCase()).collect(Collectors.toList());
        res.forEach(System.out::println);

        System.out.println();

        // flatmap
        List<List<String>> lists = new ArrayList<>();
        lists.add(Arrays.asList("apple", "click"));
        lists.add(Arrays.asList("boss", "dig", "qq", "vivo"));
        lists.add(Arrays.asList("c#", "biezhi"));

        System.out.println(lists.stream().flatMap(Collection::stream).filter(str -> str.length() > 2).count());

        System.out.println();

        // collect toList
        List<String> res1 = strings.stream().filter(string -> !string.isEmpty()).sorted()
                .map(value -> value.toUpperCase()).collect(Collectors.toList());
        res1.forEach(System.out::println);

        System.out.println();

        // collect toSet
        Set<String> res2 = strings.stream().filter(string -> !string.isEmpty()).sorted(Comparator.reverseOrder())
                .map(value -> value.toUpperCase()).collect(Collectors.toSet());
        res2.forEach(System.out::println);

        System.out.println();

        // collect map
        Map<String, String> res3 = upperList.stream().filter(string -> !string.isEmpty()).collect(Collectors.toMap(e->e, e->e.toUpperCase()));
        res3.entrySet().stream().forEach(e-> System.out.println(e.getKey() + " : " + e.getValue()));

        System.out.println();

        // collect 拼接字符串
        String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", "));
        System.out.println("合并字符串: " + mergedString);

        System.out.println();

        // 列表统计
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

        IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();

        System.out.println("列表中最大的数 : " + stats.getMax());
        System.out.println("列表中最小的数 : " + stats.getMin());
        System.out.println("所有数之和 : " + stats.getSum());
        System.out.println("平均数 : " + stats.getAverage());












    }
}
