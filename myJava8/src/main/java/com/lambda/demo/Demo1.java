package com.lambda.demo;

import com.lambda.bean.Employee;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class Demo1 {

    List<Employee> employees = Arrays.asList(
            new Employee("张三",22,8888.88f, Employee.Status.BUSY),
            new Employee("李四",42,4444.44f, Employee.Status.BUSY),
            new Employee("王五",32,6666.66f, Employee.Status.FREE),
            new Employee("赵六",52,7777.77f, Employee.Status.FREE),
            new Employee("田七",62,3333.33f, Employee.Status.BUSY),
            new Employee("王八",12,9999.99f, Employee.Status.VICATION)
    );

    /**
     * Lambda 表达式基础语法
     */
    @Test
    public void test1() {
        Runnable runnable = new Runnable(){
            @Override
            public void run() {
                System.out.println("hello world!");
            }
        };
        runnable.run();

        // Lambda
        // 语法格式一：无参，无返回值
        Runnable r = () -> System.out.println("Hello Lambda!");
        r.run();

        // 语法格式二：有参，无返回值
        Consumer<String> consumer = (e) -> System.out.println(e);
        consumer.accept("Hello Lambda Consumer!");

        //语法格式三：有一个参数，小括号可以不写
        Consumer<String> c = e -> System.out.println(e);
        c.accept("Hello Lambda Consumer!");

        //语法格式四：有两个以上的参数，Lambda 体中有多条语句
        Comparator<Integer> comparator = (x, y) -> {
            System.out.println("排序开始");
            return Integer.compare(x, y);
        };

        //语法格式五：若 Lambda 体中只有一条语句， return 和大括号都可以省略不写
        Comparator<Integer> comparator1 = (Integer x, Integer y) -> Integer.compare(x, y);

        //语法格式六：Lambda 参数列表的数据类型可以省略不写，因为JVM编译器通过上下文推断出数据类型，即“类型推断”
        Comparator<Integer> comparator2 = (x, y) -> Integer.compare(x, y);

    }

    /**
     * 四大核心函数式接口
     *      Consumer<T>：消费型接口
     * 			void accept(T t);
     *
     * 		Supplier<T>：供给行接口
     * 			T get();
     *
     * 		Function<T,R> : 函数型接口
     * 			R apply(T t);
     *
     * 		Predicate<T> : 断言型接口
     * 			R apply(T t);
     */
    @Test
    public void test2(){
        // Consumer<T>：消费型接口
        Consumer<String> consumer= (e)-> System.out.println("Hello "+e);
        consumer.accept("world!");

        // Supplier<T>：供给行接口
        Supplier<String> supplier = ()->"Hello";
        System.out.println(supplier.get());

        //Function<T,R> : 函数型接口
        Function<Integer,Integer> function = (e)->e*10;
        Integer sum = function.apply(10);
        System.out.println(sum);

        //Predicate<T> : 断言型接口
        Predicate<String> predicate = e->e.equals("hello world");
        System.out.println(predicate.test("hello"));
        System.out.println(predicate.test("hello world"));
    }

    /**
     方法引用：若 Lambda 体中的内容有方法已经实现，我们可以使用“方法引用”
     （可以理解为方法引用是 Lambda 表达式的另外以中表现形式）

     主要有三种语法格式：
     对象::实列方法名
     类::静态方法名
     类::实列方法名
     注意：
     1. Lambda 体中调用方法的参数列表与返回值类型，要与函数式接口中抽象方法的韩术列表和返回值保持一致！
     2. 若 Lambda 参数列表中的第一个参数是实例方法的调用者，而第二个参数是实例方法的参数时，可以用 ClassName::method

     数组引用：Type[]::new
     */
    @Test
    public void test3(){
        //对象::实列方法名
        Consumer<String> consumer = System.out::println;
        consumer.accept("Hello world");

        Employee employee= new Employee();
        employee.setAge(10);
        Supplier<Integer> supplier=employee::getAge;
        System.out.println(supplier.get());

        Employee employee1= new Employee();
        Function<Float,Float> function=employee1::getPrice1;
        System.out.println(function.apply(10f));

        //类::静态方法名
        Comparator<Integer> comparator= Integer::compare;
        System.out.println(comparator.compare(10,1));

        //类::实列方法名
        BiPredicate<String,String> biPredicate = String::equals;
        System.out.println(biPredicate.test("a","a"));

        //构造器引用
        Supplier<Employee> supplier1= Employee::new;
        System.out.println(supplier1.get());
        Function<Float,Employee> function1=Employee::new;
        System.out.println(function1.apply(11f));

        //数组引用：Type[]::new
        Function<Integer,String[]> f = (e)->new String[e];
        System.out.println(f.apply(10).length);
        Function<Integer,String[]> f1=String[]::new;
        System.out.println(f1.apply(20).length);

    }

    /**
     * 查找与匹配
     * 	allMatch--检查是否匹配所有元素
     * 	anyMath--检查是否至少匹配一个元素
     * 	noneMatch--检查是否没有匹配所有元素
     * 	findFirst--返回第一个元素
     * 	findAny-- 返回当前流中的任意元素
     * 	count--返回流中元素的总个数
     * 	max--返回流中最大值
     * 	min--返回流中最小值
     */
    @Test
    public void test4(){
        boolean b = employees.stream().allMatch((e)->e.getPrice()>3000);
        System.out.println(b);

        boolean b1 = employees.stream().anyMatch((e)->e.getPrice()>5000);
        System.out.println(b1);

        boolean b2 = employees.stream().noneMatch((e)->e.getPrice()>10000);
        System.out.println(b2);

        Optional<Employee> opt = employees.stream().findFirst();
        System.out.println(opt);

        Optional<Employee> opt1 = employees.stream().findAny();
        System.out.println(opt1);

        Long count = employees.stream().count();
        System.out.println(count);

        Optional<Employee> opt3 = employees.stream().max((e1,e2)->Float.compare(e1.getPrice(),e2.getPrice()));
        System.out.println(opt3.get());

        Optional<Float> opt4 = employees.stream().map(Employee::getPrice).min(Float::compareTo);
        System.out.println(opt4.get());
    }

    /**
     * 规约与收集
     *
     * 	规约-- 可以将流中元素结合起来得到一个值。
         * 	reduce(T identity,BinaryOerator)
         * 	reduce(BinaryOperator)
     *
     * 	收集--collect 接收一个 Collector 接口的实现，用于给 Stream 元素汇总的方法
     */
    @Test
    public void test5(){
        // 规约--reduce
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        Integer sum = list.stream().reduce(0,(x,y)->x+y);
        System.out.println(sum);

        //统计员工工资总和
        Optional<Float> opt = employees.stream().map(Employee::getPrice).reduce(Float::sum);
        System.out.println(opt.get());

        //收集--collect
        employees.stream().map(Employee::getPrice).collect(Collectors.toList()).forEach(System.out::println);
        //平均值
        Double avg = employees.stream().collect(Collectors.averagingDouble(Employee::getPrice));
        System.out.println(avg);
        //总合
        Double sum1 = employees.stream().collect(Collectors.summingDouble(Employee::getPrice));
        System.out.println(sum1);
        //最大值
        Optional opt1 = employees.stream().collect(Collectors.maxBy((e1,e2)->Float.compare(e1.getPrice(),e2.getPrice())));
        System.out.println(opt1.get());
        //最小值
        Optional opt2 = employees.stream().map(Employee::getPrice).collect(Collectors.minBy(Float::compare));
        System.out.println(opt2.get());
        //分组
        Map<Employee.Status,List<Employee>> map = employees.stream().collect(Collectors.groupingBy(Employee::getStatus));
        System.out.println(map);
        //多级分组
        Map<Employee.Status,Map<String,List<Employee>>> map1 = employees.stream().collect(Collectors.groupingBy(Employee::getStatus,Collectors.groupingBy((e)->{
            if(e.getAge()>20){
                return "青年";
            }else if(e.getAge()>30){
                return "中年";
            }else{
                return "老年";
            }
        })));
        System.out.println(map1);
        //分区
        Map<Boolean,List<Employee>> map3 = employees.stream().collect(Collectors.partitioningBy(e->e.getAge()>30));
        System.out.println(map3);
        //Summary 使用
        IntSummaryStatistics summary = employees.stream().collect(Collectors.summarizingInt(Employee::getAge));
        System.out.println(summary.getMax());
        System.out.println(summary.getAverage());
        System.out.println(summary.getCount());
        // join 拼接
        String str = employees.stream().map(Employee::getName).collect(Collectors.joining());
        System.out.println(str);
    }

    /**
     * 并行流-parallel
     */
    // 20亿 888 毫秒左右
    @Test
    public void test6(){
        Instant start = Instant.now();
        Long sum = LongStream.rangeClosed(0L,2000000000L).parallel().reduce(0,Long::sum);
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println(Duration.between(start,end).toMillis());
    }
    // 对比串行流 20亿 1387 毫秒左右。在往上加到 50 亿，串行流我这里已经跑不动了，但是并行parallel的可以
    @Test
    public void test7(){
        Instant start = Instant.now();
        long sum = 0L;
        for(int i=0;i<=2000000000L;i++){
            sum +=i;
        }
        System.out.println(sum);
        Instant end = Instant.now();
        System.out.println(Duration.between(start,end).toMillis());
    }
}

