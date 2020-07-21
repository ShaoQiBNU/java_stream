# java stream的用法

## 背景

> Stream 是用函数式编程方式在集合类上进行复杂操作的工具，其使用一种类似用 SQL 语句从数据库查询数据的直观方式来提供一种对 Java 集合运算和表达的高阶抽象。
>
> Stream API可以极大提高Java程序员的生产力，让程序员写出高效率、干净、简洁的代码。这种风格将要处理的元素集合看作一种流， 流在管道中传输， 并且可以在管道的节点上进行处理， 比如筛选， 排序，聚合等。元素流在管道中经过中间操作（intermediate operation）的处理，最后由最终操作(terminal operation)得到前面处理的结果。

```shell
+--------------------+       +------+   +------+   +---+   +-------+
| stream of elements +-----> |filter+-> |sorted+-> |map+-> |collect|
+--------------------+       +------+   +------+   +---+   +-------+
```

## 什么是stream

> Stream（流）是一个来自数据源的元素队列并支持聚合操作
>
> - 元素是特定类型的对象，形成一个队列。 Java中的Stream并不会存储元素，而是按需计算。
> - **数据源** 流的来源。 可以是集合，数组，I/O channel， 产生器generator 等。
> - **聚合操作** 类似SQL语句一样的操作， 比如filter, map, reduce, find, match, sorted等。
>
> 和以前的Collection操作不同， Stream操作还有两个基础的特征：
>
> - **Pipelining**: 中间操作都会返回流对象本身。 这样多个操作可以串联成一个管道， 如同流式风格（fluent style）。 这样做可以对操作进行优化， 比如延迟执行(laziness)和短路( short-circuiting)。
> - **内部迭代**： 以前对集合遍历都是通过Iterator或者For-Each的方式, 显式的在集合外部进行迭代， 这叫做外部迭代。 Stream提供了内部迭代的方式， 通过访问者模式(Visitor)实现。

## 生成流

> 集合接口有两个方法来生成流：
>
> stream() − 为集合创建串行流；parallelStream() − 为集合创建并行流。
>
> 两者的区别在于：stream()是顺序执行，而parallelStream()是并行执行，具体可参考：https://zhuanlan.zhihu.com/p/43039062

```java
List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd","", "jkl");

Stream<String> s = null;

s = strings.stream();

s = strings.parallelStream();
```

## 流操作

> java.util.stream.Stream中定义了许多流操作的方法，流的操作主要分为两类：**处理操作**、**聚合操作**。
>
> - 处理操作：诸如filter、map等处理操作将Stream一层一层的进行抽离，返回一个流给下一层使用。
>
> - 聚合操作：从最后一次流中生成一个结果给调用方，foreach只做处理不做返回。

### 处理操作

#### filter

> filter 方法用于过滤 Stream 中的值，接受一个 lambda 表达式，而且必须是 Predicate 接口的实现。Predicate 接口定义了一个 test 抽象方法，返回 true 的参数会被放入一个新的 Stream 中。例如，筛选数组里大于3的元素的数量。

```java
// filter 筛选元素>3的个数
List<Integer> list = Arrays.asList(1,2,3,4,5);
System.out.println(list.stream().filter(value -> value > 3).count());

返回结果：
2
```

#### sorted

> sorted 方法用于对流进行排序。以下代码片段使用 sorted 方法对数组进行排序（正序和倒序）：distinct()可用于去重

```java
// sorted
List<Integer> list2 = Arrays.asList(11, 22, 22, 3, 14, 5);
list2.stream().filter(value -> value > 3).sorted().distinct().forEach(System.out::println);

list2.stream().filter(value -> value > 3).sorted(Comparator.reverseOrder()).distinct().forEach(System.out::println);

返回结果：
  
5
11
14
22

22
14
11
5
```

#### map

> map 方法用于把 Stream 中的值替换为新的值。map 方法接受一个 lambda 表达式，而且必须是 Function 接口的实现。Function 接口的参数类型对应旧值的类型，返回值类型对应新值的类型，这两个类型可以是不同的类型。例如，返回数组元素的大写值：

```java
// map
List<String> upperList = Arrays.asList("a","b","c");
List<String> res = upperList.stream().map(value -> value.toUpperCase()).collect(Collectors.toList());
        res.forEach(System.out::println);

返回结果：

A
B
C
```

#### flatMap 方法

> 有的时候会遇到提取子流的操作，flatMap 可以用于把多个 Stream 连接成一个 Stream。

```java
// flatmap
List<List<String>> lists = new ArrayList<>();
lists.add(Arrays.asList("apple", "click"));
lists.add(Arrays.asList("boss", "dig", "qq", "vivo"));
lists.add(Arrays.asList("c#", "biezhi"));

System.out.println(lists.stream().flatMap(Collection::stream).filter(str -> str.length() > 2).count());

返回结果：

6
```

#### foreach

> forEach来迭代流中的每个数据。

### 聚合操作

#### collect

> collect 方法主要采用collectors类，对stream的值进行聚合操作，Collectors 类实现了很多归约操作，例如将流转换成集合和聚合元素等，具体可参考https://www.jianshu.com/p/ccbb42ad9551。Collectors 可用于返回列表或字符串，主要如下：

##### Collectors.toList()

转换成list

```java
// collect toList
List<String> res1 = strings.stream().filter(string -> !string.isEmpty()).sorted()
  .map(value -> value.toUpperCase()).collect(Collectors.toList());
res1.forEach(System.out::println);

返回结果：

ABC
ABC
ABCD
BC
EFG
JKL
```

##### Collectors.toSet()

转换成set集合

```java
// collect toSet
Set<String> res2 = strings.stream().filter(string -> !string.isEmpty()).sorted(Comparator.reverseOrder())
  .map(value -> value.toUpperCase()).collect(Collectors.toSet());
res2.forEach(System.out::println);


返回结果：

BC
ABC
EFG
JKL
ABCD
```

##### Collectors.toMap(x -> x, x -> x + 1)

转换成map

```java
// collect map
Map<String, String> res3 = upperList.stream().filter(string -> !string.isEmpty()).collect(Collectors.toMap(e->e, e->e.toUpperCase()));
res3.entrySet().stream().forEach(e-> System.out.println(e.getKey() + " : " + e.getValue()));

返回结果：
  
a : A
b : B
c : C
```

##### Collectors.joining(",")

拼接字符串

```java
// collect 拼接字符串
String mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", "));
System.out.println("合并字符串: " + mergedString);

返回结果：

合并字符串: abc, bc, efg, abcd, jkl, abc
```

#### 统计

> 一些产生统计结果的收集器也非常有用。它们主要用于int、double、long等基本类型上，可以用来产生类似如下的统计结果。

```java
// 列表统计
List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();

System.out.println("列表中最大的数 : " + stats.getMax());
System.out.println("列表中最小的数 : " + stats.getMin());
System.out.println("所有数之和 : " + stats.getSum());
System.out.println("平均数 : " + stats.getAverage());

返回结果：


列表中最大的数 : 7
列表中最小的数 : 2
所有数之和 : 25
平均数 : 3.5714285714285716
```
