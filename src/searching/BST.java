package searching;

import fundanmentals.Queue;

/**
 * Created by hailong on 3/12/17.
 * 3-3 有序的泛型符号表：基于二叉查找树实现
 * BST：二叉查找树
 * 主要实现：size，get，put，min，floor，select，rank，deleteMin，delete，keys方法(中序遍历，理解)
 * 以上全部使用 递归 实现
 */
public class BST<Key extends Comparable<Key>, Value> {
    private Node root;

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private int N;

        public Node(Key key, Value val, int N) {
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

    public BST() {
    }

    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.N;
    }

    // get方法
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else return x.val;
    }

    // put方法
    public void put(Key key, Value val) {
        root = put(root, key, val);
    }

    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = put(x.left, key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else x.val = val;
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    // 最大键和最小键
    public Key min() {
        return min(root).key;
    }

    private Node min(Node x) {
        if (x.left == null) return x;
        return min(x.left);
    }

    public Key max() {
        return max(root).key;
    }

    private Node max(Node x) {
        if (x.right == null) return x;
        return max(x.right);
    }

    // 向上取整：略难
    public Key floor(Key key) {
        Node x = floor(root, key);    // 如果key比二叉树中任意结点的key还小，就会返回null
        if (x == null) return null;
        return x.key;
    }

    private Node floor(Node x, Key key) {  // 在以x为跟结点的子树中查找小于等于key的最大键所在结点
        if (x == null) return null;   // 关键语句
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0) return floor(x.left, key);
        // 关键就是 cmp > 0 时的情况
        Node t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }

    // 选择操作
    public Key select(int k) {
        Node x = select(root, k);
        if (x == null) return null;
        return select(root, k).key;
    }

    private Node select(Node x, int k) {
        if (x == null) return null;    // 很容易漏掉，当 k > size(root) 时会出现此种情况
        int t = size(x.left);
        if (k < t) return select(x.left, k);
        else if (k > t) return select(x.right, k - t - 1);
        else return x;
    }

    // 排名
    public int rank(Key key) {
        return rank(root, key);
    }

    private int rank(Node x, Key key) { // 注意返回的是在以x为根结点时key的rank，所以注意cmp>0情况
        if (x == null) return 0;    // 注意h为null时返回0，在当前以x为跟结点的子树下的索引
        int t = size(x.left);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(x.left, key);
        else if (cmp > 0) return t + 1 + rank(x.right, key);
        else return t;
    }

    // delete方法(难)
    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x == null) return x.right;             // 递归结束条件，最后情况
        x.left = deleteMin(x.left);                // 递归
        x.N = size(x.left) + size(x.right) + 1;
        return x;                                  // 递归结束条件，一般情况
    }

    public void delete(Key key) {   // 难
        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;            // 指定节点不在二叉树上
        // 查找指定结点
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {                             // 找到了要删除的结点，难在此处！
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(t.right);
            x.right = deleteMin(t.right);   // 理解此句
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    // keys方法：中序遍历，从小到大遍历
    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new Queue<>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node x, Queue<Key> queue, Key lo, Key hi) { // 注意Queue<Key>参数类型含有泛型
        if (x == null) return;
        int cmplo = lo.compareTo(x.key);
        int cmphi = hi.compareTo(x.key);
        if (cmplo < 0) keys(x.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(x.key);
        if (cmphi > 0) keys(x.right, queue, lo, hi);
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public static void main(String[] args) {
        BST<String, Integer> st = new BST<>();
        st.put("Java", 100);
        st.put("Python", 100);
        st.put("C++", 99);
        st.put("JavaScript", 99);
        st.put("Scala", 99);
        for (String s : st.keys()) {
            System.out.println(s + " ---> " + st.get(s));
        }
        System.out.println(st.select(2));
    }
}
