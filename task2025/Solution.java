package com.javarush.task.task20.task2025;

import java.math.BigInteger;
import java.util.*;

import static java.lang.System.*;
import static java.util.Arrays.copyOfRange;
import static java.util.Arrays.fill;

/* 
Алгоритмы-числа
*/

public class Solution {


    static long[][] pows = new long[10][20];

    static {
        for (int i = 0; i < pows.length; i++) {
            for (int j = 0; j < pows[i].length; j++) {
                pows[i][j] = pow(i, j);//BigInteger.valueOf(i).pow((int) j).longValue();
            }
        }
    }

    private static Long pow(long base, long exponent) {//проверить армы, проверить массив степеней
        long res = 1;
        for (int i = 0; i < exponent; i++) {
            res *= base;
        }
        return res;
    }

    public static long[] getNumbers(long N) {
        long[] result;
        if (N < 2) {
            result = new long[0];
            return result;
        }

        int lengthOfN = 0;
        long V = N;
        while (V > 0) {
            V /= 10;
            lengthOfN++;
        }
        TreeSet<Long> armstrongs = new TreeSet<>();

        byte[] num = new byte[lengthOfN];
        fill(num, (byte) 9);
        int index = 0;
        while (index < num.length && num[num.length - 1] > 0) {
            while (num[index] == 0) {//есть какой-нить метод взять кусок аррая и чекнуть?
                byte[] num1 = copyOfRange(num, index, num.length);
                long summm = checkArm(num1);
                if (summm != -1 && summm < N) armstrongs.add(summm);
            }
            if (num[index] > 0) {
                long summm0 = checkArm(num);
                if (summm0 != -1 && summm0 < N) armstrongs.add(summm0);
                byte[] num2 = copyOfRange(num, index, num.length);
                long summm1 = checkArm(num2);
                if (summm1 != -1 && summm1 < N) armstrongs.add(summm1);
                Arrays.fill(num, 0, index + 1, --num[index]);
                long summ = checkArm(num);
                if (summ != -1 && summ < N) armstrongs.add(summ);
                index = 0;
            }

        }
        int forResult = 0;
        result = new long[armstrongs.size()];//какова всё-таки природа этого самого ТРисета? Он действительно тока уникальные экземпляры содержит?
        for (long l : armstrongs) {
            result[forResult++] = l;
        }
        Arrays.sort(result);
        armstrongs.clear();
        return result;
    }

    public static long checkArm(byte[] num) {
        long summ = 0;
        for (int i = 0; i < num.length; i++) {
            summ += pows[num[i]][num.length];
        }

        if (isArmstrong(summ) && summ > 0) return summ;
        else return -1;

        //нули отсекать лучше видимо в том методе. а этот будет исключительно проверять массив на арма
    }
    public static boolean isArmstrong(long n){
        long sum = 0;
        int degree = getNumLen(n);
        long n1 = n;
        int digit = (int) (n1%10);

        while (n1 > 0){
            sum += pows[digit][degree];
            n1/=10;
            digit = (int)(n1%10);
        }
        if (n == sum) return true;
        return false;
    }
    private static int getNumLen(long n){
        int count = 0;
        while (n > 0){
            n/=10;
            count++;
        }
        return count;
    }

    public static void main(String[] args) {
        long a = currentTimeMillis();
        out.println(Arrays.toString(getNumbers(1000)));
        long b = currentTimeMillis();
        out.println("memory " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (8 * 1024));
        out.println("time = " + (b - a) / 1000);

        a = currentTimeMillis();
        out.println(Arrays.toString(getNumbers(1000000)));
        b = currentTimeMillis();
        out.println("memory " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (8 * 1024));
        out.println("time = " + (b - a) / 1000);
    }
}
