package com.lambda.demo;

import com.lambda.bean.Employee;
import com.lambda.handler.MyWordChange;
import com.lambda.handler.Operation;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Demo2 {

    List<Employee> employees = Arrays.asList(
            new Employee("张三",12,22.22f),
            new Employee("李四",42,4444.44f),
            new Employee("王五",32,6666.66f),
            new Employee("赵六",52,7777.77f),
            new Employee("田七",62,3333.33f),
            new Employee("王八",12,9999.99f)
            );

    /**
     * 1. 调用 Collections.sort() 方法，通过定制排序比较两个 Employee (先按年龄比，年龄相同按姓名比)，使用 Lambda 作为参数传递
     */

    @Test
    public void test1(){
        Collections.sort(employees,(e1,e2)->{
            if(e1.getAge()==e2.getAge()){
                return e1.getName().compareTo(e2.getName());
            }else{
                return Integer.compare(e1.getAge(),e2.getAge());
            }
        });
        employees.stream().forEach(System.out::println);
    }

    /**
     * 2.   *声明函数式接口，接口中声明抽象方法 pubic String getValue(String str);
     *      *测试类（Demo2）,类中编写方法使用接口作为参数，将一个字符穿转成大写，并作为方法的返回值。
     *      *再将一个字符穿进行截取第6个到第10个并输出。
     */
    @Test
    public void test2(){
        String str = strHandler("hello word",(e)->e.toUpperCase());
        System.out.println(str);

        String substr = strHandler("hello word",(e)->e.toUpperCase().substring(6,10));
        System.out.println(substr);
    }

    public String strHandler(String str, MyWordChange wordChange){
        return wordChange.getValue(str);
    }

    /**
     * 3    * 声明一个带两个泛型的函数式接口，泛型类型喂<T,R> T 为参数，R 为返回值
     *      * 接口中声明对应抽象方法
     *      * 在测试类（Demo2）中声明方法，使用接口作为参数，计算两个 long 类型的和。
     *      * 在计算两个 long 型参数的乘积
     */
    @Test
    public void test3(){
        Long sum = longHandler(10L,10L,(e1,e2)->e1+e2);
        System.out.println(sum);

        Long ride = longHandler(10L,10L,(e1,e2)->e1*e2);
        System.out.println(ride);
    }

    public long longHandler(Long num1,Long num2,Operation<Long,Long> operation){
        return operation.operator(num1,num2);
    }

    //生产指定个数的整数，并放入集合中
    //用与处理字符串
    //将满足条件的字符穿，放入集合中
}
