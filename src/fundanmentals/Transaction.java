package fundanmentals;

import java.util.Comparator;

/**
 * Created by hailong on 3/4/17.
 * Transaction(交易)数据类型
 * 此处按照 amount 进行比较
 */
public class Transaction implements Comparable<Transaction> {
    private final String who;
    private final Date when;
    private final double amount;

    // 两个构造器，第二个构造器会自动解析字符串
    public Transaction(String who, Date when, double amount) {
        this.who = who;
        this.when = when;
        this.amount = amount;
    }

    /**
     * 正则表达式,
     * \\d表示 0-9 的数字,
     * \\s表示 空格,回车,换行等空白符,
     * \\w表示单词字符(数字字母下划线)
     * +号表示一个或多个的意思
     */
    public Transaction(String transaction) {
        String[] a = transaction.split("\\s+");
        who = a[0];
        when = new Date(a[1]);
        amount = Double.parseDouble(a[2]);
    }

    public String who() {
        return who;
    }

    public Date when() {
        return when;
    }

    public double amount() {
        return amount;
    }

    // 重写 toString, equals, hashCode
    public String toString() {
        return String.format("%-10s %10s %8.2f", who, when, amount);
    }

    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null || other.getClass() != this.getClass()) return false;
        Transaction that = (Transaction) other;
        return (that.who.equals(this.who)) && (that.when.equals(this.when)) && (that.amount == this.amount);
    }

    public int hashCode() {          // P295
        int hash = 17;
        hash = 31 * hash + who.hashCode();
        hash = 31 * hash + when.hashCode();
        hash = 31 * hash + ((Double) amount).hashCode();
        return hash;
    }

    // 必须实现的接口的抽象方法 implements Comparable<Transaction> 默认比较对象的大小根据对象的 amount 属性
    public int compareTo(Transaction that) {
        return Double.compare(this.amount, that.amount);
    }

    // 学习使用接口比较器 Comparator：可以将一组对象按照不同的属性进行排序，参见 Inseration 例子；比较排序
    // Comparator是函数式接口 抽象方法是 int compare(T o1, T o2);
    // 静态内部类(2.5节学到)
    public static class whoOrder implements Comparator<Transaction> {
        public int compare(Transaction o1, Transaction o2) {
            return o1.who.compareTo(o2.who);
        }
    }

    public static class whenOrder implements Comparator<Transaction> {
        public int compare(Transaction o1, Transaction o2) {
            return o1.when.compareTo(o2.when);
        }
    }

    public static class amountOrder implements Comparator<Transaction> {
        public int compare(Transaction o1, Transaction o2) {
            return Double.compare(o1.amount, o2.amount);     // amount是基本类型，无法继承Comparable，所以没有compareTo方法
        }
    }
}
