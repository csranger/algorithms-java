package fundanmentals;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by hailong on 2/28/17.
 * 1-10 union-find 实现：加权 quick-union 算法
 * 时间复杂度o(log(N))
 * 特点：记录每一棵树的大小(大小指的是树的节点数量)，并总将较小的树连接到大树上；只需添加一个数组用于记录各个根节点的分量大小
 */
public class WeightedQuickUnionUF {
    private int[] id;
    private int count;        // 连通分量数量
    private int[] sz;         // 记录各个跟节点的分量大小

    public WeightedQuickUnionUF(int N) {
        count = N;
        id = new int[N];
        for (int i = 0; i < id.length; i++) {
            id[i] = i;
        }
        sz = new int[N];
        for (int i = 0; i < id.length; i++) {
            sz[i] = 1;
        }
    }

    public int find(int p) {
        while (p != id[p]) p = id[p];   // 或 if (p == id[p] return p; return find(id[p]);
        return p;
    }

    public void union(int p, int q) {
        int pRoot = find(p);
        int qRoot = find(q);
        if (pRoot == qRoot) return;
        if (sz[pRoot] < sz[qRoot]) {
            id[pRoot] = qRoot;
            sz[qRoot] += sz[pRoot];
        } else {
            id[qRoot] = pRoot;
            sz[pRoot] += sz[qRoot];
        }
        count--;
    }

    public int count() {
        return count;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new File("/Users/hailong/Documents/Data/algs4-data/mediumUF.txt"));
        int N = sc.nextInt();
        QuickFindUF uf = new QuickFindUF(N);
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
