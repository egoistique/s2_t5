package ru.vsu.cs.course1.tree;

import java.util.*;

public class BinaryTreeAlgorithms {

    @FunctionalInterface
    public interface Visitor<T> {
        /**
         * "Посещение" значения
         *
         * @param value Значение, которое "посещаем"
         * @param level Уровень дерева/поддерева, на котором находится данное значение
         */
        void visit(T value, int level);
    }

//21. (*) В двоичном дереве для целых чисел содержатся как положительные, так и
//отрицательные элементы. Найти все поддеревья с максимумом суммы всех элементов
//поддерева. Вернуть в виде списка путей от вершины дерева до вершины каждого такого
//поддерева. Путь задается в виде строки из букв "L" (если на очередном шаге от узла мы
//идем к левому потомку) и "R" ( – если к правому потомку).

    public static class Answer  <T>{
        public int max;
        public List<BinaryTree.TreeNode<T>> roots;

        Answer(int a, List<BinaryTree.TreeNode<T>> ro) {
            max = a;
            roots = ro;
        }
    }

    public static <T>  int findLargestSumUtil(BinaryTree.TreeNode<T> root, Answer ans) {
        if (root == null) {
            return 0;
        }

        int currSum = (int) root.getValue() + findLargestSumUtil(root.getLeft(), ans) + findLargestSumUtil(root.getRight(), ans);

        if (currSum > ans.max){
            ans.roots.clear();
            ans.max = currSum;
            ans.roots.add(root);
        } else if (currSum == ans.max){
            ans.roots.add(root);
        }

        return currSum;
    }


    public static  <T>  Answer findLargestSubtreeSum(BinaryTree.TreeNode<T> root) {
        List<BinaryTree.TreeNode<T>> roots = new ArrayList<>();
        roots.add(root);
        if (root == null) {
            return null;
        }
        Answer ans = new Answer(-9999999, roots);

        findLargestSumUtil(root, ans);

        return ans;
    }


    public static <T> boolean hasPath(BinaryTree.TreeNode<T> root, ArrayList<String> arr, BinaryTree.TreeNode<T> x){
        if (root==null)
            return false;

        if (root == x)
            return true;

        if(hasPath(root.getLeft(), arr, x)){
            arr.add("L");
            return true;
        }
        if(hasPath(root.getRight(), arr, x)){
            arr.add("R");
            return true;
        }
        return false;
    }

    public static <T> ArrayList<String> printPath(BinaryTree.TreeNode<T> root, BinaryTree.TreeNode<T> x) {
        ArrayList<String> arr = new ArrayList<>();

        if (hasPath(root, arr, x)){
            Collections.reverse(arr);
            return arr;
        } else System.out.print("No Path");
        return arr;
    }

//-8 (-6 (4 (-5), 6), 5 (, 5 (-2, 8)))
//-8 (-6 (4 (-5, 5), 6 (-5, 5)), -500 (-600 (-5, 5000), -50 (-2, 800)))
//-8 (-6 (4 (-5, 5), -6000 (-5, 5000)), -500 (-600 (-5, 5000), -50 (-2, 5)))
//-8 (-6 (4 (-5, 5000), -6000 (-5, 5000)), -500 (-600 (-5, 5000), -50 (-2, 5)))
//-8 (-60000 ( 6000 (-5, 5000), 6000 (-5, 5000)), -500 (-600 (-5, 5000), -50 (-2, 5)))





    /**
     * Обход поддерева с вершиной в данном узле
     * "посетителем" в прямом/NLR порядке - рекурсивная реализация
     *
     * @param treeNode Узел поддерева, которое требуется "обойти"
     * @param visitor Посетитель
     */
    public static <T> void preOrderVisit(BinaryTree.TreeNode<T> treeNode, Visitor<T> visitor) {
        // данный класс нужен только для того, чтобы "спрятать" его метод (c 3-мя параметрами)
        class Inner {
            void preOrderVisit(BinaryTree.TreeNode<T> node, Visitor<T> visitor, int level) {
                if (node == null) {
                    return;
                }
                visitor.visit(node.getValue(), level);
                preOrderVisit(node.getLeft(), visitor, level + 1);
                preOrderVisit(node.getRight(), visitor, level + 1);
            }
        }
        // класс приходится создавать, т.к. статические методы в таких класс не поддерживаются
        new Inner().preOrderVisit(treeNode, visitor, 0);
    }

    /**
     * Обход поддерева с вершиной в данном узле
     * в виде итератора в прямом/NLR порядке
     * (предполагается, что в процессе обхода дерево не меняется)
     *
     * @param treeNode Узел поддерева, которое требуется "обойти"
     * @return Итератор
     */
    public static <T> Iterable<T> preOrderValues(BinaryTree.TreeNode<T> treeNode) {
        return () -> {
            Stack<BinaryTree.TreeNode<T>> stack = new Stack<>();
            stack.push(treeNode);

            return new Iterator<T>() {
                @Override
                public boolean hasNext() {
                    return stack.size() > 0;
                }

                @Override
                public T next() {
                    BinaryTree.TreeNode<T> node = stack.pop();
                    if (node.getRight() != null) {
                        stack.push(node.getRight());
                    }
                    if (node.getLeft() != null) {
                        stack.push(node.getLeft());
                    }
                    return node.getValue();
                }

            };
        };
    }

    /**
     * Обход поддерева с вершиной в данном узле
     * "посетителем" в симметричном/поперечном/центрированном/LNR порядке - рекурсивная реализация
     *
     * @param treeNode Узел поддерева, которое требуется "обойти"
     * @param visitor Посетитель
     */
    public static <T> void inOrderVisit(BinaryTree.TreeNode<T> treeNode, Visitor<T> visitor) {
        // данный класс нужен только для того, чтобы "спрятать" его метод (c 3-мя параметрами)
        class Inner {
            void inOrderVisit(BinaryTree.TreeNode<T> node, Visitor<T> visitor, int level) {
                if (node == null) {
                    return;
                }
                inOrderVisit(node.getLeft(), visitor, level + 1);
                visitor.visit(node.getValue(), level);
                inOrderVisit(node.getRight(), visitor, level + 1);
            }
        }
        // класс приходится создавать, т.к. статические методы в таких класс не поддерживаются
        new Inner().inOrderVisit(treeNode, visitor, 0);
    }

    /**
     * Обход поддерева с вершиной в данном узле в виде итератора в
     * симметричном/поперечном/центрированном/LNR порядке (предполагается, что в
     * процессе обхода дерево не меняется)
     *
     * @param treeNode Узел поддерева, которое требуется "обойти"
     * @return Итератор
     */
    public static <T> Iterable<T> inOrderValues(BinaryTree.TreeNode<T> treeNode) {
        return () -> {
            Stack<BinaryTree.TreeNode<T>> stack = new Stack<>();
            BinaryTree.TreeNode<T> node = treeNode;
            while (node != null) {
                stack.push(node);
                node = node.getLeft();
            }

            return new Iterator<T>() {
                @Override
                public boolean hasNext() {
                    return !stack.isEmpty();
                }

                @Override
                public T next() {
                    BinaryTree.TreeNode<T> node = stack.pop();
                    T result = node.getValue();
                    if (node.getRight() != null) {
                        node = node.getRight();
                        while (node != null) {
                            stack.push(node);
                            node = node.getLeft();
                        }
                    }
                    return result;
                }
            };
        };
    }

    /**
     * Обход поддерева с вершиной в данном узле
     * "посетителем" в обратном/LRN порядке - рекурсивная реализация
     *
     * @param treeNode Узел поддерева, которое требуется "обойти"
     * @param visitor Посетитель
     */
    public static <T> void postOrderVisit(BinaryTree.TreeNode<T> treeNode, Visitor<T> visitor) {
        // данный класс нужен только для того, чтобы "спрятать" его метод (c 3-мя параметрами)
        class Inner {
            void postOrderVisit(BinaryTree.TreeNode<T> node, Visitor<T> visitor, int level) {
                if (node == null) {
                    return;
                }
                postOrderVisit(node.getLeft(), visitor, level + 1);
                postOrderVisit(node.getRight(), visitor, level + 1);
                visitor.visit(node.getValue(), level);
            }
        }
        // класс приходится создавать, т.к. статические методы в таких класс не поддерживаются
        new Inner().postOrderVisit(treeNode, visitor, 0);
    }

    /**
     * Обход поддерева с вершиной в данном узле в виде итератора в обратном/LRN порядке
     * (предполагается, что в процессе обхода дерево не меняется)
     *
     * @param treeNode Узел поддерева, которое требуется "обойти"
     * @return Итератор
     */
    public static <T> Iterable<T> postOrderValues(BinaryTree.TreeNode<T> treeNode) {
        return () -> {
            // Реализация TreeNode<T>, где left = right = null
            BinaryTree.TreeNode<T> emptyNode = () -> null;

            Stack<BinaryTree.TreeNode<T>> stack = new Stack<>();
            Stack<T> valuesStack = new Stack<>();
            stack.push(treeNode);

            return new Iterator<T>() {
                @Override
                public boolean hasNext() {
                    return stack.size() > 0;
                }

                @Override
                public T next() {
                    for (BinaryTree.TreeNode<T> node = stack.pop(); node != emptyNode; node = stack.pop()) {
                        if (node.getRight() == null && node.getLeft() == null) {
                            return node.getValue();
                        }
                        valuesStack.push(node.getValue());
                        stack.push(emptyNode);
                        if (node.getRight() != null) {
                            stack.push(node.getRight());
                        }
                        if (node.getLeft() != null) {
                            stack.push(node.getLeft());
                        }
                    }
                    return valuesStack.pop();
                }
            };
        };
    }


    /**
     *  Класс для хранения узла дерева вместе с его уровнем, нужен для методов
     *  {@link #byLevelVisit(BinaryTree.TreeNode, Visitor)} и {@link #byLevelValues(BinaryTree.TreeNode)}
     *
     * @param <T>
     */
    private static class QueueItem<T> {
        public BinaryTree.TreeNode<T> node;
        public int level;

        public QueueItem(BinaryTree.TreeNode<T> node, int level) {
            this.node = node;
            this.level = level;
        }
    }

    /**
     * Обход поддерева с вершиной в данном узле "посетителем" по уровням (обход в ширину)
     *
     * @param treeNode Узел поддерева, которое требуется "обойти"
     * @param visitor Посетитель
     */
    public static <T> void byLevelVisit(BinaryTree.TreeNode<T> treeNode, Visitor<T> visitor) {
        Queue<QueueItem<T>> queue = new LinkedList<>();
        queue.add(new QueueItem<>(treeNode, 0));
        while (!queue.isEmpty()) {
            QueueItem<T> item = queue.poll();
            if (item.node.getLeft() != null) {
                queue.add(new QueueItem<>(item.node.getLeft(), item.level + 1));
            }
            if (item.node.getRight() != null) {
                queue.add(new QueueItem<>(item.node.getRight(), item.level + 1));
            }
            visitor.visit(item.node.getValue(), item.level);
        }
    }

    /**
     * Обход поддерева с вершиной в данном узле в виде итератора по уровням (обход в ширину)
     * (предполагается, что в процессе обхода дерево не меняется)
     *
     * @param treeNode Узел поддерева, которое требуется "обойти"
     * @return Итератор
     */
    public static <T> Iterable<T> byLevelValues(BinaryTree.TreeNode<T> treeNode) {
        return () -> {
            Queue<QueueItem<T>> queue = new LinkedList<>();
            queue.add(new QueueItem<>(treeNode, 0));

            return new Iterator<T>() {
                @Override
                public boolean hasNext() {
                    return queue.size() > 0;
                }

                @Override
                public T next() {
                    QueueItem<T> item = queue.poll();
                    if (item == null) {
                        // такого быть не должно, но на вский случай
                        return null;
                    }
                    if (item.node.getLeft() != null) {
                        queue.add(new QueueItem<>(item.node.getLeft(), item.level + 1));
                    }
                    if (item.node.getRight() != null) {
                        queue.add(new QueueItem<>(item.node.getRight(), item.level + 1));
                    }
                    return item.node.getValue();
                }
            };
        };
    }


    /**
     * Представление дерева в виде строки в скобочной нотации
     *
     * @param treeNode Узел поддерева, которое требуется представить в виже скобочной нотации
     * @return дерево в виде строки
     */
    public static <T> String toBracketStr(BinaryTree.TreeNode<T> treeNode) {
        // данный класс нужен только для того, чтобы "спрятать" его метод (c 2-мя параметрами)
        class Inner {
            void printTo(BinaryTree.TreeNode<T> node, StringBuilder sb) {
                if (node == null) {
                    return;
                }
                sb.append(node.getValue());
                if (node.getLeft() != null || node.getRight() != null) {
                    sb.append(" (");
                    printTo(node.getLeft(), sb);
                    if (node.getRight() != null) {
                        sb.append(", ");
                        printTo(node.getRight(), sb);
                    }
                    sb.append(")");
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        // класс приходится создавать, т.к. статические методы в таких класс не поддерживаются
        new Inner().printTo(treeNode, sb);

        return sb.toString();
    }
}
