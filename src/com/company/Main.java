package com.company;

import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

import static java.lang.StrictMath.abs;

class RSA
{
    public static void main(String[] args)
    {
        String result;
        Scanner in = new Scanner(System.in);
        System.out.println("Введите текст: ");
        String m = in.nextLine();
        result =rsa(m);
        System.out.println(result);
    }

    public static String rsa(String m)
    {

        Integer[] Fermatnumber = new Integer[]{3, 5, 17, 257, 65537};

        int qq = 0, pp;
        Mass frame = new Mass();
        Random rand = new Random();
        int pq = frame.pq[rand.nextInt(1062)];
        if (pq < 5000) {
            pp = pq;
            int q_temp = pp * 2;
            for (int i = 0; i <= 1060; i++) {
                if (abs(frame.pq[i] - q_temp) < abs(qq - q_temp) || abs(frame.pq[i] - q_temp) == abs(qq - q_temp) && frame.pq[i] < qq) {
                    qq = frame.pq[i];
                }
            }
        } else {
            pp = pq;
            int q_temp = pp / 2;
            for (int i = 0; i <= 1060; i++) {
                if (abs(frame.pq[i] - q_temp) < abs(qq - q_temp) || abs(frame.pq[i] - q_temp) == abs(qq - q_temp) && frame.pq[i] < qq) {
                    qq = frame.pq[i];
                }
            }
        }
        BigInteger p = BigInteger.valueOf(pp);
        //System.out.println("p = " + p);
        BigInteger q = BigInteger.valueOf(qq);
        //System.out.println("q = " + q);
        BigInteger mm;
        BigInteger n, phi, one, encr, decr;
        Integer d;
        n = p.multiply(q);
        //System.out.println("n = " + n);
        phi = p.subtract(BigInteger.valueOf(1)).multiply(q.subtract(BigInteger.valueOf(1)));
        //System.out.println("Phi = " + phi);
        Integer e = Make_E(phi.intValue(), Fermatnumber);
        //System.out.println("E = " + e);
        d = Make_D(e, phi.intValue());
        //System.out.println("d = " + d);
        String emess = "", dmess = "";
        /*
        Scanner in = new Scanner(System.in);
        System.out.println("Введите текст: ");
        String m = in.nextLine();*/

        for (int i = 0; i < m.length(); i++) {
            mm = BigInteger.valueOf((int) m.charAt(i)); //код первого символа (ASCII)
            encr = mm.modPow(BigInteger.valueOf(e), n);

            emess += encr.toString(10) + " ";
        }
        emess = emess.strip();
        System.out.println("Зашифрованный текст (посимвольно): ");
        System.out.println(emess);
        for (String retval : emess.split(" ")) {
            encr = BigInteger.valueOf(Integer.parseInt(retval));
            decr = encr.modPow(BigInteger.valueOf(d), n);
            dmess += (char) decr.intValue();
        }
        System.out.println("Расшифрованный текст: ");
        //System.out.println(dmess);
        return dmess;
    }

    static Integer Make_D(Integer e, Integer phi) {
        e = e % phi;
        for (Integer i = 1; i < phi; i++) {
            if ((e * i) % phi == 1) {
                return i;
            }
        }
        return 0;
    }

    static int gcdByBruteForce(int n1, int n2) {
        int gcd = 1;
        for (int i = 1; i <= n1 && i <= n2; i++) {
            if (n1 % i == 0 && n2 % i == 0) {
                gcd = i;
            }
        }
        return gcd;
    }

    public static int Make_E(Integer phi, Integer[] Fermatnumber) {
        int i = 0;
        while (i < 5) {
            if (gcdByBruteForce(phi, Fermatnumber[i]) == 1) {
                return Fermatnumber[i];
            } else {
                i++;
            }
        }
        return 0;
    }
}