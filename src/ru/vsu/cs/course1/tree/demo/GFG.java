package ru.vsu.cs.course1.tree.demo;

// Java-программа для поиска крупнейших
// сумма поддерева в данном двоичном дереве.

import java.util.*;

class GFG {
// Структура узла дерева.

    static class Node {
        int key;
        Node left, right;
    }

    static class INT {
        int v;
        INT(int a) {
            v = a;
        }
    }

// Функция для создания нового узла дерева.

    static Node newNode(int key) {
        Node temp = new Node();
        temp.key = key;
        temp.left = temp.right = null;
        return temp;
    }

// Вспомогательная функция для поиска наибольшего
// сумма поддеревьев рекурсивно.

    static int findLargestSubtreeSumUtil(Node root, INT ans) {
        // Если текущий узел пуст вернуть 0 в родительский узел.
        if (root == null) {
            return 0;
        }

        // поддерево сумма укорененная на текущем узле.
        int currSum = root.key + findLargestSubtreeSumUtil(root.left, ans) + findLargestSubtreeSumUtil(root.right, ans);

        // Обновить ответ, если текущее поддерево сумма больше, чем ответ до сих пор.
        ans.v = Math.max(ans.v, currSum);
        // Возвращаем текущее поддерево сумма к его родительскому узлу.
        return currSum;
    }


// Функция для поиска
// самая большая сумма поддерева.

    static int findLargestSubtreeSum(Node root) {
        // Если дерево не существует, тогда ответ 0.
        if (root == null) {
            return 0;
        }
        // Переменная для хранения максимальная сумма поддерева.
        INT ans = new INT(-9999999);
        // вызов рекурсивной функции найти максимальную сумму поддерева.
        findLargestSubtreeSumUtil(root, ans);
        return ans.v;
    }

// Код драйвера

    public static void main(String args[]) {
//    / *
//
//        1
//
//                / /
//
//            / /
//
//        -2 3
//
//            / / / /
//
//        / / / /
//
//        4 5 -6 2
//
//            * /

        Node root = newNode(1);

        root.left = newNode(-30);

        root.right = newNode(-14);

        root.left.left = newNode(8);

        root.left.right = newNode(7);

        root.right.left = newNode(10);

        root.right.right = newNode(9);

        root.left.left.left = newNode(-6);
        root.left.left.right = newNode(3);
        root.left.right.right = newNode(8);


        System.out.println(findLargestSubtreeSum(root));

    }
}