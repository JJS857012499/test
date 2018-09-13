package com.zhidian.test.st;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 江俊升 on 2018/2/7.
 */
public class TestStream {


    public static void main(String... args) {

        //
//        List<String> words = Arrays.asList("Java 8", "Lambdas", "In", "Action");
//        List<String> uniqueCharaters = words.stream()
//                .map(word -> word.split(""))
//                .flatMap(Arrays::stream)
//                .distinct()
//                .collect(toList());
//        System.out.println(uniqueCharaters);
//
//        //
//        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
//        List<Integer> numbers2 = Arrays.asList(4, 5);
//        numbers1.stream()
//                .flatMap(i -> numbers2.stream().map(j -> i + "," + j))
//                .forEach(System.out::println);
//
//        //
//        Function<String, Object> stringObjectFunction = (s) -> {
//            System.out.println(s);
//            return s;
//        };
//
//        Comparator<Integer> sum = Integer::sum;
//
//        BiFunction<Integer, Integer, String> integerIntegerStringBiFunction = Integer::toString;


        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(i + "");
        }

        long l = System.currentTimeMillis();
        list.stream().forEach(s->{
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long a = System.currentTimeMillis();
        System.out.println(a - l);

        long ll = System.currentTimeMillis();
        for (int i = 0; i < list.size(); i++) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long aa = System.currentTimeMillis();
        System.out.println(aa - ll);

    }


}
