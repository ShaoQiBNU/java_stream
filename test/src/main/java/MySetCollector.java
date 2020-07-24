import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class MySetCollector<T> implements Collector<T, List<T>, List<T>> {

    @Override
    public Supplier<List<T>> supplier () {
        System.out.println("supplier");
        return LinkedList::new;
    }

    @Override
    public BiConsumer<List<T>, T> accumulator() {
        System.out.println("accumulator");
        return (list, item) -> list.add(item);
    }

    @Override
    public BinaryOperator<List<T>> combiner() {
        System.out.println("combiner");

        return (list1, list2)-> {list1.addAll(list2); return list1;};
    }

    @Override
    public Function<List<T>, List<T>> finisher() {
        System.out.println("finisher");
        return t->t;
    }

    @Override
    public Set<Characteristics> characteristics() {
        System.out.println("characteristics");

        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH,
                Characteristics.UNORDERED));
    }

    public static void main(String[] args) {
        List<String> list = Arrays.asList("new", "new", "year", "day");
        List<String> res = list.stream().collect(new MySetCollector<>());
        res.stream().forEach(System.out::println);
    }



}
