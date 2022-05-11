package ru.vsu.cs.course1;

import java.util.Map;
import java.util.TreeMap;

public class Prog {
    public static void main(String[] args) {
        Map<String, Integer> map = new PutOrderMap<>();
        map.put("k1", 1);
        map.put("k12", 1);
        map.put("k3", 2);
        map.put("k14", 3);
        map.remove("k14");
        map.put("k55", 4);
        map.put("k6", 5);
        map.put("k7", 6);

        for (var entry : map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        System.out.println();

        for (var value : map.values()) {
            System.out.println(value);
        }
        System.out.println();

        for (var entry : map.keySet()) {
            System.out.println(entry);
        }
        System.out.println();
    }
}
