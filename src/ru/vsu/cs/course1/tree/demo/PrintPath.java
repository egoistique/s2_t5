package ru.vsu.cs.course1.tree.demo;

// Реализация Java для печати пути от корня
// к данному узлу в двоичном дереве

import java.util.ArrayList;
import java.util.Collections;

public class PrintPath {
    // Возвращает true, если есть путь от root к данному узлу. Это также населяет 'arr' с заданным путем
    public static boolean hasPath(Node root, ArrayList<String> arr, int x){
        // если root равен NULL пути нет
        if (root==null)
            return false;

        // помещаем значение узла в 'arr'
        // если это обязательный узел вернуть true
        if (root.data == x)
            return true;
        // еще проверить, лежит ли нужный узел в левом поддереве или правом поддереве текущий узел

        if(hasPath(root.left, arr, x)){
            arr.add("L");
            return true;
        }
        if(hasPath(root.right, arr, x)){
            arr.add("R");
            return true;
        }
        arr.add(String.valueOf(root.data));

        // обязательный узел не лежит ни в левое или правое поддерево текущего узла
        // Таким образом, удаляем значение текущего узла из 'arr'и возвращаем false
        arr.remove(arr.size() - 1);
        return false;
    }

    // функция для печати пути от корня до данный узел, если узел лежит в двоичном дереве
    public static void printPath(Node root, int x) {
        // ArrayList для хранения пути
        ArrayList<String> arr = new ArrayList<>();
        // если обязательный узел присутствует затем печатаем путь
        if (hasPath(root, arr, x)){
            Collections.reverse(arr);
            for (int i = 0; i < arr.size() - 1; i++) {
                System.out.print(arr.get(i) + "->");
            }
            System.out.print(arr.get(arr.size() - 1));
        }
        // 'x' отсутствует в двоичном дереве
        else System.out.print("No Path");
    }

    public static void main(String args[]) {
        Node root=new Node(1);

        root.left = new Node(2);

        root.right = new Node(3);

        root.left.left = new Node(4);

        root.left.right = new Node(5);

        root.right.left = new Node(6);

        root.right.right = new Node(7);

        root.left.left.left = new Node(8);

        root.left.left.right = new Node(9);

        root.left.right.left = new Node(10);

        root.left.right.right = new Node(11);

        root.right.left.left = new Node(12);

        root.right.left.right = new Node(13);

        root.right.right.left = new Node(14);

        root.right.right.right = new Node(15);

        int x=8;

        printPath(root, x);
    }
}

// узел двоичного дерева
class Node {
    int data;
    Node left, right;
    Node(int data) {
        this.data=data;
        left=right=null;
    }

};