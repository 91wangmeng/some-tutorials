package lambda;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @Copyright: Zhejiang Lishi Technology Co., Ltd  2018 <br/>
 * @Desc: <br/>
 * @ProjectName: some-tutorials <br/>
 * @Date: 2018/12/29 11:18 <br/>
 * @Author: <a href="wangmengmeng@lswooks.com">汪萌萌</a>
 */
@Slf4j
public class StreamTutorials {

    public static void main(String[] args) {
        List<Person> personList = init();
        filler(personList);
        sorted(personList);
        map(personList);
        limit(personList);
        flatMap();
        reduce(personList);
        groupBy(personList);
        partitioningBy(personList);
        comprehensive(personList);
        personList = init();
        orderBySomeOrders(Arrays.asList("张三",  "Jack", "Jerry", "Tom", "Alien", "Bob", "李四"), personList);

    }

    private static void orderBySomeOrders(List<String> orders, List<Person> personList) {
        Map<String, Integer> itemSortMap = IntStream.range(0, orders.size())
                .mapToObj(value -> Integer.valueOf(value))
                .collect(Collectors.toMap((o -> orders.get(o)), (o -> o)));
        List<String> orderNameList = personList.stream().sorted(Comparator.comparingInt(o -> itemSortMap.getOrDefault(o.getName(), 0))).map(Person::getName).collect(Collectors.toList());
        Assert.assertEquals(orderNameList, orders);
    }

    private static void comprehensive(List<Person> personList) {
        //获取各个国家年龄20岁以上,年龄最小的人员信息
        // 1.8之前
        List<Person> personArrayList = new ArrayList<>();
        Map<String, Person> groupByCountry = new HashMap<>();
        for (Person person : personList) {
            if (person.getAge() > 20) {
                String country = person.getCountry();
                if (groupByCountry.get(country) == null) {
                    groupByCountry.put(country, person);
                    continue;
                }
                if (groupByCountry.get(country).getAge() > person.getAge()) {
                    groupByCountry.put(country, person);
                }
            }
        }
        personArrayList.addAll(groupByCountry.values());

        List<Person> collect = personList.stream()
                .filter(person -> person.getAge() > 20)
                .collect(Collectors.groupingBy(Person::getCountry))
                .entrySet()
                .stream()
                .map(stringListEntry -> stringListEntry
                        .getValue()
                        .stream()
                        .sorted(Comparator.comparingInt(Person::getAge))
                        .limit(1)
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException(stringListEntry.getKey() + "没人")))
                .collect(Collectors.toList());
        Assert.assertEquals(personArrayList, collect);

    }

    private static void partitioningBy(List<Person> personList) {
        //将学生按照本国和外国分组
        //1.8之前
        Map<Boolean, List<Person>> groupByCountry = new HashMap<>();
        for (Person person : personList) {
            Boolean country = "中国".equals(person.getCountry());
            if (groupByCountry.get(country) == null) {
                groupByCountry.put(country, new ArrayList<>());
            }
            groupByCountry.get(country).add(person);
        }
        //使用Stream
        Map<Boolean, List<Person>> groupByCountryWithStream = personList
                .stream()
                .collect(Collectors.partitioningBy(person -> "中国".equals(person.getCountry())));
        Assert.assertEquals(groupByCountry, groupByCountryWithStream);
    }

    private static void groupBy(List<Person> personList) {
        //将学生按照国籍分组
        //1.8之前
        Map<String, List<Person>> groupByCountry = new HashMap<>();
        for (Person person : personList) {
            String country = person.getCountry();
            if (groupByCountry.get(country) == null) {
                groupByCountry.put(country, new ArrayList<>());
            }
            groupByCountry.get(country).add(person);
        }
        //使用Stream
        Map<String, List<Person>> groupByCountryWithStream = personList
                .stream()
                .collect(Collectors.groupingBy(Person::getCountry));
        Assert.assertEquals(groupByCountry, groupByCountryWithStream);
        ;
    }

    private static void reduce(List<Person> personList) {
        //获取人员年龄总和
        //1.8之前
        int sum = 0;
        for (Person person : personList) {
            sum += person.getAge();
        }
        //使用Stream
        int sumByStream = personList.stream().mapToInt(Person::getAge).sum();
        int sumByStreamTwo = personList.stream().mapToInt(Person::getAge).reduce(0, (left, right) -> left + right);
        Assert.assertEquals(sum, sumByStream);
        Assert.assertEquals(sum, sumByStreamTwo);
    }

    private static void flatMap() {
        List<Person> classOne = init();
        List<Person> classTwo = init();
        List<Person> classThree = init();
        List<List<Person>> classes = new ArrayList<>();
        classes.add(classOne);
        classes.add(classTwo);
        classes.add(classThree);
        //获取全校学生列表
        //1.8之前
        ArrayList<Person> school = new ArrayList<>();
        for (List<Person> aClass : classes) {
            school.addAll(aClass);
        }
        //使用Stream
        List<Person> collect = classes.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        Assert.assertEquals(school, collect);

    }

    private static void limit(List<Person> personList) {
        //获取年龄顺序排列前三名的人员信息
        //1.8之前
        Comparator<Person> personComparator = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getAge() - o2.getAge();
            }
        };
        personList.sort(personComparator);
        List<Person> newPersonList = new ArrayList<>();
        for (int i = 0; i < personList.size(); i++) {
            if (i < 3) {
                newPersonList.add(personList.get(i));
            }
        }
        //使用Stream
        List<Person> newPersonListByStream = personList.stream()
                .sorted(Comparator.comparingInt(Person::getAge))
                .limit(3)
                .collect(Collectors.toList());

        Assert.assertEquals(newPersonList, newPersonListByStream);
    }

    private static void map(List<Person> personList) {
        //将人员列表转换为人员姓名列表
        //1.8之前
        List<String> nameList = new ArrayList<>();
        for (Person person : personList) {
            nameList.add(person.getName());
        }
        //使用Stream
        List<String> nameListByStream = personList.stream()
                .map(Person::getName)
                .collect(Collectors.toList());
        Assert.assertEquals(nameList, nameListByStream);

    }

    @Test
    private static void sorted(List<Person> personList) {
        //根据年龄顺序和逆序分别排序
        //1.8之前
        Comparator<Person> personComparator = new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return o1.getAge() - o2.getAge();
            }
        };
        //顺序
        personList.sort(personComparator);

        //逆序
        personList.sort(personComparator.reversed());


        //使用stream
        //顺序
        List<Person> orderPersonList = personList.stream()
                .sorted(Comparator.comparingInt(Person::getAge))
                .collect(Collectors.toList());
        //逆序
        List<Person> reversePersonList = personList.stream()
                .sorted(Comparator.comparingInt(Person::getAge).reversed())
                .collect(Collectors.toList());
        Assert.assertArrayEquals(personList.toArray(), reversePersonList.toArray());

    }

    private static void filler(List<Person> personList) {
        //过滤出年龄大于等于20岁的人员信息
        //1.8之前
        Iterator<Person> iterator = personList.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getAge() < 20) {
                iterator.remove();
            }
        }
        //使用stream
        List<Person> fillerPersonList = personList
                .stream()
                .filter(person -> person.getAge() >= 20)
                .collect(Collectors.toList());
        Assert.assertEquals(personList, fillerPersonList);

    }

    private static void buildStream() {
        Stream<Person> listStream = new ArrayList<Person>().stream();
        Stream<Person> setStream = new HashSet<Person>().stream();
        Stream<Map.Entry<String, Person>> mapStream = new HashMap<String, Person>().entrySet().stream();
        Stream<Person> arrayStream = Arrays.stream(new Person[]{new Person(), new Person()});
        Stream<Person> singelStream = Stream.of(new Person());
        Stream<Person> personStream = Stream.generate(() -> new Person()).limit(5);
    }


    private static List<Person> init() {
        Person personOne = new Person("张三", 25, "中国");
        Person personTwo = new Person("李四", 17, "中国");
        Person personThree = new Person("王五", 33, "中国");
        Person personFour = new Person("Jack", 16, "美国");
        Person personFive = new Person("Bob", 26, "美国");
        Person personSix = new Person("Alien", 18, "美国");
        Person personSeven = new Person("Tom", 20, "加拿大");
        Person personEight = new Person("Jerry", 15, "加拿大");
        ArrayList<Person> personList = new ArrayList<>();
        personList.add(personOne);
        personList.add(personTwo);
        personList.add(personThree);
        personList.add(personFour);
        personList.add(personFive);
        personList.add(personSix);
        personList.add(personSeven);
        personList.add(personEight);
        return personList;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    private static class Person {
        private String name;
        private int age;
        private String country;
    }
}
