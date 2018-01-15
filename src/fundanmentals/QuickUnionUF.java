package fundanmentals;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by hailong on 2/28/17.
 * 1-9 union-find 实现：quick-union 算法
 * 核心数据结构：以触点作为索引的id[]数组,id[]元素是同一分量中另一触点的名称
 * 区别在于 find() 和 union() 方法的实现
 */
public class QuickUnionUF {
    private int[] id;         // 区别：以触点为索引，所对应的元素是同一分量中另一个触点的名称，也可能是自己
    private int count;        // 连通分量数量

    public QuickUnionUF(int N) {
        count = N;
        id = new int[N];
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
        }
    }

    public int find(int p) {
        while (p != id[p]) {
            p = id[p];
        }
        return p;
    }

    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if (pRoot == qRoot) return;
        id[pRoot] = qRoot;
        count--;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public int count() {
        return count;
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new File("/Users/hailong/Documents/Data/algs4-data/tinyUf.txt"));
        int N = sc.nextInt();
        QuickUnionUF uf = new QuickUnionUF(N);
        while (sc.hasNextInt()) {
            int p = sc.nextInt();
            int q = sc.nextInt();
            if (uf.connected(p, q)) continue;
            uf.union(p, q);
            System.out.println(p + " " + q);
        }
        System.out.println(uf.count());
    }
}
