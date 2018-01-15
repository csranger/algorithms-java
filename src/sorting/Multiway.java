package sorting;

import edu.princeton.cs.algs4.In;

/**
 * Created by hailong on 5/10/17.
 * 索引优先队列的用例
 * 使用优先队列的多向归并：将多个有序的输入流归并成一个有序地输入流
 */
public class Multiway {
    public static void merge(In[] streams) {
        // 初始化优先队列
        int N = streams.length;
        IndexMinPQ<String> pq = new IndexMinPQ<String>(N);
        // insert元素构造优先队列
        for (int i = 0; i < N; i++) {
            if (!streams[i].isEmpty()) pq.insert(i, streams[i].readString());
        }
        //
        while (!pq.isEmpty()) {
            System.out.print(pq.min() + " ");
            int i = pq.delMin();
            if (!streams[i].isEmpty()) pq.insert(i, streams[i].readString());
        }
    }

    public static void main(String[] args) {
        // 构造 In[] streams
        int N = 3;
        In[] streams = new In[N];
        streams[0] = new In("/Users/hailong/Documents/Data/algs4-data/m1.txt");
        streams[1] = new In("/Users/hailong/Documents/Data/algs4-data/m2.txt");
        streams[2] = new In("/Users/hailong/Documents/Data/algs4-data/m3.txt");
        // 归并多个输入流
        merge(streams);
    }
}
