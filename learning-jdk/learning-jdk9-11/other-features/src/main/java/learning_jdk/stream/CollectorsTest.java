package learning_jdk.stream;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static java.util.Comparator.*;

/**
 * https://www.webfunny.com/blog/post/37
 * CollectorsTest
 */
public class CollectorsTest {
    public static final List<Dish> menu = Arrays.asList(new Dish("pork", false, 800, Dish.Type.MEAT),
            new Dish("beef", false, 700, Dish.Type.MEAT),
            new Dish("chicken", false, 400, Dish.Type.MEAT),
            new Dish("french fries", true, 500, Dish.Type.OTHER),
            new Dish("rice", true, 500, Dish.Type.OTHER),
            new Dish("season fruit", true, 500, Dish.Type.OTHER),
            new Dish("pizza", true, 550, Dish.Type.OTHER),
            new Dish("prawns", false, 400, Dish.Type.FISH),
            new Dish("salmon", false, 450, Dish.Type.FISH));

    public static void main(String[] args) {

        // 1、tolist 将流中所有的项目收集到一个List
        List<Dish> list = menu.stream().collect(toList());

        // 2.toSet 将流中所有项目收集到一个Set，删除重复项
        Set<Dish> set = menu.stream().collect(toSet());

        // 3.toCollection 将流中所有项目收集到给定的供应源创建的集合
        Collection<Dish> dishes = menu.stream().collect(Collectors.toCollection(ArrayList::new));

        // 4.counting 计算流中元素个数
        long howManyDishes = menu.stream().collect(counting());

        // 5.summingInt 对流中项目的一个整数属性求和
        int totalCalories = menu.stream().collect(summingInt(Dish::getCalories));

        // 6.averagingInt 计算流中项目Integer属性的平均值
        double avgCalories = menu.stream().collect(averagingInt(Dish::getCalories));

        // 7.summarizingInt() 收集关于流中项目Integer 属性的统计值，例如最大、最小、总和与平均值
        IntSummaryStatistics intSummaryStatistics = menu.stream().collect(summarizingInt(Dish::getCalories));
        System.out.println(intSummaryStatistics.getAverage());

        // maxBy() 一个包裹了流中按照给定比较器选出的最大元素的Optional，或如果流为空则Optional.empty()
        Optional<Dish> max = menu.stream().collect(maxBy(comparingInt(Dish::getCalories)));
        // minxBy() 一一个包裹了流中按照给定比较器选出的最x小元素的Optional，或如果流为空则为Optional.empty()
        Optional<Dish> min = menu.stream().collect(minBy(comparingInt(Dish::getCalories)));

        // reducing() 归约操作产生的类型 从一个座位累加器的初始值开始，利用BinaryOperator 与流中元素租个结合，从而将流归约为单个值。
        int reducing = menu.stream().collect(reducing(0, Dish::getCalories, Integer::sum));

        // groupingBy() 一根据项目的一个属性的值对流中的项目作分组，并将属性值作为结果Map的键
        Map<Dish.Type, List<Dish>> dishesByType = menu.stream().collect(groupingBy(Dish::getType));

        // partitioningBy() 根据对流中每个项目应用谓词的结果来对项目进行分区
        Map<Boolean, List<Dish>> vegetarianDishes = menu.stream().collect(partitioningBy(Dish::isVegetarian));

        Comparator<Dish> byLastName = Comparator.comparing(Dish::getCalories);
        List<Dish> sorts = menu.stream().sorted(byLastName).collect(toList());
        sorts.forEach(s -> System.out.println(s.toString()));
    }

    @Getter
    @AllArgsConstructor
    @ToString
    static class Dish {
        private final String name; // 名字
        private final boolean vegetarian;// 是否为素食
        private final int calories;// 热量
        private final Type type;// 类型

        public enum Type {
            MEAT, FISH, OTHER
        }
    }
}
