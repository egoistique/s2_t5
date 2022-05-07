package ru.vsu.cs.course1;

import ru.vsu.cs.course1.tree.bst.avl.AVLTreeMap;
import ru.vsu.cs.util.dummy.DefaultNotSupportedMap;
import ru.vsu.cs.util.dummy.DefaultNotSupportedSet;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PutOrderMap<K extends Comparable<K>, V> implements DefaultNotSupportedMap {
    public PutOrderMap(K key, V value) {

    }

    private class Pair<K, V> implements Entry<K, V> {
        public K key;
        public V value;
        public int order;

        public Pair(K key, V value, int order) {
            this.key = key;
            this.value = value;
            this.order = order;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }
    }
//10. (*) Реализовать словарь PutOrderMap<K, V>, который обеспечивает итерацию по
//словарю в порядке добавления элементов. При этом все операции со словарем должны
//остаться эффективными (со сложностью не более O(log(n))). Для этого внутри словаря
//должно храниться два других словаря, где в первом хранятся пары (ключ -> (значение +
//порядок добавления)), а во втором – пары (порядок добавления –> (значение + ключ)).
//Все операции поиска осуществляются по первому словарю, вставка / удаление
//затрагивают два словаря одновременно, а итерация проходит по второму словарю.


    Map<K, Pair<K, V>> keyMap = new AVLTreeMap<>();
    Map<Integer, Pair<K, V>> orderMap = new AVLTreeMap<>();
    int order = 0;

    @Override
    public V get(Object key) {
        Pair<K, V> pair = keyMap.get(key);
        return (pair == null) ? null : pair.value;
    }

    public V put(K key, V value) {
        Pair<K, V> pair = keyMap.get(key);
        if (pair != null) {
            V oldValue = pair.value;
            pair.value = value;
            return oldValue;
        } else {
            pair = new Pair<>(key, value, order);
            keyMap.put(key, pair);
            orderMap.put(order, pair);
            order++;
            return null;
        }
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new DefaultNotSupportedSet<Entry<K, V>>() {
            @Override
            public int size() {
                return keyMap.size();
            }

            @Override
            public Iterator<Entry<K, V>> iterator() {
                return orderMap.values().iterator();
            }
        };
    }
}
