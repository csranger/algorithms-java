package fundanmentals;

import java.util.Scanner;

/**
 * Created by hailong on 2/25/17.
 * 封装日期的抽象数据类型
 */
public class Date implements Comparable<Date> {           // 不可变类，只要getter方法
    private final int month;
    private final int day;
    private final int year;

    // 两个构造器，第二个构造器会自动解析字符串
    public Date(int m, int d, int y) {
        month = m;
        day = d;
        year = y;
    }

    public Date(String date) {
        String[] a = date.split("/");
        month = Integer.parseInt(a[0]);
        day = Integer.parseInt(a[1]);
        year = Integer.parseInt(a[2]);
    }

    public int month() {
        return month;
    }

    public int day() {
        return day;
    }

    public int year() {
        return year;
    }

    // 重写 toString, equals, hashCode
    public String toString() {
        return month() + "/" + day() + "/" + year();
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (o != null && o.getClass() == Date.class) {
            Date o2 = (Date) o;
            if (o2.day() == this.day() && o2.month() == this.month() && o2.year() == this.year()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = month;
        result = 31 * result + day;
        result = 31 * result + year;
        return result;
    }

    // 必须实现接口的抽象方法
    @Override
    public int compareTo(Date o) {
        if (this.year > o.year) return 1;
        if (this.year < o.year) return -1;
        if (this.month > o.month) return 1;
        if (this.month > o.month) return -1;
        if (this.day > o.day) return 1;
        if (this.day < o.day) return -1;
        return 0;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int m = sc.nextInt();
        int d = sc.nextInt();
        int y = sc.nextInt();
        Date date = new Date(m, d, y);
        System.out.println(date);
    }
}
