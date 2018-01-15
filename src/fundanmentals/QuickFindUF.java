package fundanmentals;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by hailong on 2/28/17.
 * 1-8 union-find 实现：quick-find 算法
 * 核心数据结构：以触点作为索引的id[]数组,id[]元素是触点所在分量
 * 但quick-find算法一般无法处理大型问题，因为对于每一对输入union()都会扫描整个数组
 * union操作对于每一对输入都需要扫描整个数组，时间复杂度 o(N)  find操作很快
 */
public class QuickFindUF {
    private int[] id;     // 核心数据结构！分量id，以触点为索引，以所在分量为值
    private int count;    // 分量数量

    public QuickFindUF(int N) {
        // 初始化分量数组，以整数 0...N-1 标识初始化N个触点
        count = N;
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    public int find(int p) {
        return id[p];
    }

    public void union(int p, int q) {
        int pId = find(p);
        int qId = find(q);
        // 如果 p 和 q 已经在同一个分量之中则不需要采取任何行动
        if (pId == qId) return;
        // 将 p 的分量重命名为 q 的名称
        for (int i = 0; i < id.length; i++) {
            if (id[i] == pId) id[i] = qId;     // 此处不可写成find(p)之类的，因为循环到p时，find(p)值会改变
        }
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
