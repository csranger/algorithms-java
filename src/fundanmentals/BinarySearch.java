package fundanmentals;

import edu.princeton.cs.algs4.In;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by hailong on 2/25/17.
 * 1-2 经典二分查找算法：返回key所在已排序的数组中的索引，不存在返回－1
 */
public class BinarySearch {
    public static int rank(int key, int[] a) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    public static void main(String[] args) throws IOException {
        In in = new In("/Users/hailong/Documents/Data/algs4-data/largeW.txt");  // 相当于Scanner，只是多了一个读取为数组的静态方法
        int[] whitelist = in.readAllInts();
        Arrays.sort(whitelist);
        Scanner sc = new Scanner(new File("/Users/hailong/Documents/Data/algs4-data/largeT.txt"));
        while (sc.hasNextInt()) {    // 读取键值，如果不存在于白名单中将其打印
            int key = sc.nextInt();
            if (rank(key, whitelist) == -1) {
                System.out.println(key);
            }
        }
    }
}
