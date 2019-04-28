package test;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Test {

    public static void main(String[] args) {
        int[] array = {1,2,3,4};

        int[] attay = new int[4];

        int index = 0;

        System.arraycopy(array, index, attay, 0, 4-index);
        System.arraycopy(array, 0, attay, (4-index), index);

        for (int i : attay) {
            System.out.println(i);
        }

        System.out.println("\uD83C\uDCCE");
        System.out.println(" \uD83D\uDE03");

        Set<Integer> integers = new LinkedHashSet<>();
        List<Integer> integerList = new ArrayList<>(integers);
        integerList.add(1);
        integerList.add(1);

        integerList.forEach(System.out::println);

    }
}

