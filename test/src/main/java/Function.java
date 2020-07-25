import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Function {
    public static Integer s=0;
    public static Integer ss=1;
    public static void main(String[] args) {
        List<String> list = Arrays.asList("hello", "world", "java", "C", "C++");
        List<String> res = new LinkedList<>();

        // Imperative programming
        for (String str: list) {
            res.add(str.toUpperCase());
        }
        for (String str: res) {
            System.out.println(str);
        }


        // Functional programming
        List<String> res2 = list.stream().map(str->str.toUpperCase()).collect(Collectors.toList());
        res2.forEach(System.out::println);

        Function function = new Function();

        int aa = 1;
        int res3 = function.sum(aa);
        System.out.println(res3);
        System.out.println(s);
        res3 = function.sum(aa);
        System.out.println(res3);
        System.out.println(s);

        // 类型声明
        MathAdd addition = (int a, int b) -> a + b;

        int bb = 1;

        int res4 = function.operate(ss, bb, addition);
        System.out.println(res4);
        System.out.println(ss);
        res4 = function.operate(ss, bb, addition);

        System.out.println(res4);
        System.out.println(ss);


        String str = "HelloWorld";

        String res5 = function.reverse(str);
        System.out.println(res5);


        list.parallelStream().forEach(System.out::println);
        list.stream().forEach(System.out::println);

    }

    public Integer sum(int a) {
        s = s+a;
        return s;
    }

    interface MathAdd {
        int operation(int a, int b);
    }

    private int operate(int a, int b, MathAdd mathOperation){
        return mathOperation.operation(a, b);
    }

    public String reverse(String  str) {

        if (str.length()==0) {
            return str;

        }
        else {
            return reverse(str.substring(1)) + str.substring(0, 1);
        }


    }

}
