package fundanmentals;

/**
 * Created by hailong on 2/25/17.
 * 1-1 欧几里得算法：计算两个非负整数的最大公约数
 */
public class Eucid {
    public static int gcd(int p, int q) {
        if (q == 0) return p;
        int r = p % q;
        return gcd(q, r);
    }

    public static void main(String[] args) {
        System.out.println(gcd(12, 8));
        System.out.println(gcd(8, 24));
    }
}
