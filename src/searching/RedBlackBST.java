package searching;

import fundanmentals.Queue;

/**
 * Created by hailong on 3/18/17.
 * 3-4 有序的符号表：使用数据结构：红黑二叉查找树实现
 * 在二叉查找树的基础上添加额外的信息变成红黑树，红黑树和一个2-3树对应，实现高效的平衡插入算法
 * 只有 put 与 delete 方法和二叉查找树不同，其余方法不涉及结点颜色和二叉查找树完全一样
 */
public class RedBlackBST<Key extends Comparable<Key>, Value> {
    // 红黑树树：只是在二叉查找树基础上添加了color属性
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;

    private class Node {
        Key key;
        Value val;
        Node left, right;
        int N;
        boolean color;

        public Node(Key key, Value val, int N, boolean color) {
            this.key = key;
            this.val = val;
            this.N = N;
            this.color = color;
        }
    }

    // 以下是红黑树需要用到的4个特有的工具方法
    private boolean isRed(Node h) {
        if (h == null) return false;
        return h.color == RED;
    }

    private Node rotateLeft(Node h) {
        // 交换跟结点h 与 h.right 两结点
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        // 更新结点属性 N 和 color
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        // 返回子树现在的跟结点
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        x.N = h.N;
        h.N = size(h.left) + size(h.right) + 1;
        return x;
    }

    private void flipColors(Node h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;
    }

    // 更新子树的结点总数，与二叉查找树完全一致
    public int size() {
        return size(root);
    }

    private int size(Node h) {
        if (h == null) return 0;
        return h.N;
    }

    // put方法，画图理解三种情况
    public void put(Key key, Value val) {
        root = put(root, key, val);
        root.color = BLACK;
    }

    private Node put(Node h, Key key, Value val) {
        if (h == null) return new Node(key, val, 1, RED);
        int cmp = key.compareTo(h.key);
        if (cmp < 0) h.left = put(h.left, key, val);
        else if (cmp > 0) h.right = put(h.right, key, val);
        else h.val = val;
        if (isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
        if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
        if (isRed(h.left) && isRed(h.right)) flipColors(h);
        h.N = size(h.left) + size(h.right) + 1;
        return h;
    }

    // delete(Key key) 删除操作比较麻烦，略

    // 其他方法(put, delete方法除外)，与二叉查找树完全一致，因为它们不涉及结点颜色！
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node h, Key key) {
        if (h == null) return null;
        int cmp = key.compareTo(h.key);
        if (cmp < 0) return get(h.left, key);
        else if (cmp > 0) return get(h.right, key);
        else return h.val;
    }

    public Key min() {
        return min(root).key;
    }

    private Node min(Node h) {
        if (h.left != null) return min(h.left);
        return h;
    }

    public Key max() {
        return max(root).key;
    }

    private Node max(Node h) {
        if (h.right != null) return max(h.right);
        return h;
    }

    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) return null;
        return floor(root, key).key;
    }

    private Node floor(Node h, Key key) {
        if (h == null) return null;
        int cmp = key.compareTo(h.key);
        if (cmp < 0) return floor(h.left, key);
        else if (cmp == 0) return h;
        else {
            Node t = floor(h.right, key);
            if (t == null) return h;
            else return t;
        }
    }

    public int rank(Key key) {
        return rank(root, key);
    }

    private int rank(Node h, Key key) {
        if (h == null) return 0;
        int cmp = key.compareTo(h.key);
        if (cmp < 0) return rank(h.left, key);
        else if (cmp > 0) return size(h.left) + rank(h.right, key) + 1;
        else return size(h.left);
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key lo, Key hi) {
        Queue<Key> queue = new Queue<>();
        keys(root, queue, lo, hi);
        return queue;
    }

    private void keys(Node h, Queue<Key> queue, Key lo, Key hi) {
        if (h == null) return;
        int cmplo = lo.compareTo(h.key);
        int cmphi = hi.compareTo(h.key);
        if (cmplo < 0) keys(h.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(h.key);
        if (cmphi > 0) keys(h.right, queue, lo, hi);
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public static void main(String[] args) {
        RedBlackBST<String, Integer> st = new RedBlackBST<>();
        st.put("Java", 100);
        st.put("Python", 98);
        st.put("C++", 99);
        System.out.println(st.get("Java"));
        System.out.println(st.get("C++"));
        for (String s : st.keys()) {
            System.out.println(s + "  ----------->  " + st.get(s));
        }
    }
}
