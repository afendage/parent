package com.lambda.demo;

import com.lambda.bean.Trader;
import com.lambda.bean.Transaction;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Demo3 {

    Trader raoul = new Trader("Raoul", "Cambridge");
    Trader mario = new Trader("Mario","Milan");
    Trader alan = new Trader("Alan","Cambridge");
    Trader brian = new Trader("Brian","Cambridge");

    List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950));


    /**
     * 找到交易额最小的交易
     */
    @Test
    public void test8(){
        Optional optional = transactions.stream()
                .min(Comparator.comparingInt(Transaction::getValue))
                .map(Transaction::getValue);
        System.out.println(optional.get());
    }

    /**
     * 所有交易中，最高的交易额是多少
     */
    @Test
    public void test7(){
        Optional optional = transactions.stream().max(Comparator.comparingInt(Transaction::getValue)).map(Transaction::getValue);
        System.out.println(optional.get());

        /*Integer max = transactions.stream().map(Transaction::getValue).reduce(0,Integer::max);
        System.out.println(max);*/
    }

    /**
     * 打印生活在剑桥的交易员的所有交易额
     */
    @Test
    public void test6(){
        List<Integer> list =transactions.stream().filter(e->e.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .collect(Collectors.toList());
        System.out.println(list);
    }

    /**
     * 有没有交易员在米兰工作的?
     */
    @Test
    public void test5(){
        boolean b = transactions.stream().anyMatch(e->e.getTrader().getCity().equals("Milan"));
        System.out.println(b);
    }

    /**
     * 返回所有交易员的姓名字符串，并按字母顺序排序
     */
    @Test
    public void test4(){
        String str =transactions.stream().map(e->e.getTrader().getName()).distinct().sorted().reduce("",(e1,e2)->e1+e2);
        System.out.println(str);
    }

    /**
     * 查找所有来自剑桥的交易员，并按姓名排序
     */
    @Test
    public void test3(){
        List<Trader> list = transactions.stream().filter((e)->e.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getTrader)
                .sorted(Comparator.comparing(Trader::getName))
                .distinct()
                .collect(Collectors.toList())
        ;
        System.out.println(list);
    }

    /**
     * 交易员在哪些不同的城市工作过
     */
    @Test
    public void test2(){
        List<String> list = transactions.stream().map((e)->e.getTrader().getCity()).distinct().collect(Collectors.toList());
        System.out.println(list);
    }

    /**
     * 找出2011年发生的所有交易，并按交易额排序
     */
    @Test
    public void test1(){
        List<Transaction> list = transactions.stream()
                                        .filter((e)->e.getYear()==2011)
                                        .sorted(Comparator.comparing(Transaction::getValue))
                                        .collect(Collectors.toList());
        System.out.println(list);
    }

}
