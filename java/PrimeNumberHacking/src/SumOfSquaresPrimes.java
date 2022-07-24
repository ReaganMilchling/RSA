//desc: Sum of Squares method but user miller-rabin to search for
//      the next closest primes
//      will not self-terminate just like SumOfSquares.java
//      Does not take advantage of Double.java

import java.math.BigInteger;
import java.security.SecureRandom;

public class SumOfSquaresPrimes implements Runnable {

    private Thread t = null;
    private final String threadName;
    private final BigInteger offset;
    private final BigInteger n;
    private final BigInteger a;
    private final boolean up;

    public SumOfSquaresPrimes(String threadName, BigInteger offset, BigInteger n, BigInteger a, boolean up) {
        this.threadName = threadName;
        this.offset = offset;
        this.n = n;
        this.a = a;
        this.up = up;
    }

    @Override
    public void run() {
        System.out.println("Running thread: " + threadName);
        BigInteger ret = sumOfSquares(this.n, this.a);
        System.out.println(threadName + "- Factor: " + ret);

    }

    public void start() {
        System.out.println("Starting thread: " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

    private BigInteger sumOfSquares(BigInteger n, BigInteger sqrt) {
        BigInteger a = sqrt;
        while (true) {
            BigInteger b2 = a.multiply(a).subtract(n).abs();
            //System.out.println(threadName + ":  " + a);
            if (isSquare(b2)) {
                return a.add(b2.sqrt());
            }
            if (up) {
                a = closestPrimeUp(a.add(BigInteger.ONE), offset);
            } else {
                a = closestPrimeDown(a.subtract(BigInteger.ONE), offset);
            }

        }
        //return null;
    }

    private boolean isSquare(BigInteger x) {
        return x.sqrtAndRemainder()[1].equals(BigInteger.ZERO);
    }

    private BigInteger closestPrimeUp(BigInteger x, BigInteger offset) {
        if (x.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            x = x.add(BigInteger.ONE);
        }
        while (!millerRabin(x)) {
            x = x.add(offset);
        }
        return x;
    }

    private BigInteger closestPrimeDown(BigInteger x, BigInteger offset) {
        if (x.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            x = x.subtract(BigInteger.ONE);
        }
        while (!millerRabin(x)) {
            x = x.subtract(offset);
        }
        return x;
    }

    private Boolean millerRabin(BigInteger n) {
        int r = 0;
        BigInteger d = n.subtract(BigInteger.ONE);

        while (d.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            d = d.divide(BigInteger.TWO);
            r++;
        }

        for (int i = 0; i < 25; i++) {

            BigInteger a = randomBigInteger(n.bitLength(), BigInteger.TWO.pow(n.bitLength()), BigInteger.TEN);
            BigInteger x = a.modPow(d, n);

            if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE))) {
                break;
            }

            int j;
            for (j = 1; j < r; j++) {
                x = x.modPow(BigInteger.TWO, n);
                if (x.equals(n.subtract(BigInteger.ONE))) {
                    break;
                }
            }
            if (j==r) {
                //I have no idea how else to write this code
                //this implementation isn't too great
                return false;
            }
        }
        return true;
    }

    private BigInteger randomBigInteger(int bits, BigInteger max, BigInteger min) {
        BigInteger r = new BigInteger(bits, new SecureRandom());

        while (r.compareTo(max) > 0 && r.compareTo(min) < 0) {
            r = new BigInteger(bits, new SecureRandom());
        }
        return r;
    }
}
