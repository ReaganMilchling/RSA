import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PrimeFactor {

    public PrimeFactor () {

    }

    public BigInteger factor(BigInteger n) {
        long start = System.nanoTime();
        BigInteger q = PollardMinusOne(n);
        long end = System.nanoTime();
        BigInteger p = n.divide(q);

        assert (p.multiply(q).equals(n));
        System.out.println("Q:  " + q);
        System.out.println("P:  " + p);
        System.out.println("Time: " + (end - start)/1000000000f + "s");
        System.out.println("---------------------\n");
        return q;
    }

    //brent cycle finding
    //https://comeoncodeon.wordpress.com/2010/09/18/pollard-rho-brent-integer-factorization/
    //https://xn--2-umb.com/09/12/brent-pollard-rho-factorisation/index.html
    public BigInteger brentPollardRho(BigInteger n) {
        int m = 1000;
        BigInteger a, x, y, ys, r, q, g = BigInteger.ONE;

        a = new BigInteger(n.bitLength(), new SecureRandom());
        y = new BigInteger(n.bitLength(), new SecureRandom());
        r = BigInteger.ONE;
        q = BigInteger.ONE;

//        while (false) {
//            x = y;
//            for (int i = 0; i < r;)
//
//        }
        return g;
    }

    public static BigInteger PollardMinusOne(BigInteger n){
        BigInteger a = BigInteger.TWO;
        BigInteger i = BigInteger.ONE;
        while(true){
            a = a.modPow(i, n);
            BigInteger d = (a.subtract(BigInteger.ONE)).gcd(n);
            if(d.compareTo(BigInteger.ONE) == 1){
                return d;
            }else{
                i = i.add(BigInteger.ONE);
            }
        }
    }

    //p-1
    public BigInteger newPollardRho(BigInteger n) {
        BigInteger a = BigInteger.TEN;
        BigInteger b = BigInteger.TWO;
        while (!a.equals(b)) {
            a = g(a, n);
            b = g(g(b, n), n);
            BigInteger p = gcd(b.subtract(a).abs(), n);
            if (p.compareTo(BigInteger.ONE) > 0) {
                return p;
            }
        }

        return BigInteger.ONE;
    }

    //wikipedia
    public BigInteger wikiPollardRho(BigInteger n) {
         BigInteger a = BigInteger.TWO;
         BigInteger b = BigInteger.TWO;
         BigInteger d = BigInteger.ONE;

        while (d.equals(BigInteger.ONE)) {
            a = g(a, n);
            b = g(g(b, n),n);
            d = gcd(a.subtract(b).abs(), n);
        }

        if (d.equals(n)) {
            return null;
        } else {
            return d;
        }
    }

    //textbook
    public BigInteger pollardRho(BigInteger n) {
        List<BigInteger> x = new ArrayList<BigInteger>();
        int i = 1;
        x.add(new BigInteger(n.bitLength(), new SecureRandom()));
        BigInteger y = x.get(0);
        //BigInteger k = BigInteger.TWO;
        int k = 2;
        while (true) {
            x.add(x.get(i-1).pow(2).subtract(BigInteger.ONE).mod(n));
            BigInteger d = gcd(y.subtract(x.get(i)), n);
            if (!d.equals(BigInteger.ONE) && !d.equals(n)) {
                System.out.println(d);
                return d;
            }
            if (i == k) {
                y = x.get(i);
                k *= 2;
            }
            i += 1;
        }
    }

    private BigInteger gcd(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) {
            return a;
        } else {
            return gcd(b, a.mod(b));
        }
    }

    //polynomial function
    private BigInteger g(BigInteger x, BigInteger n) {
        return x.pow(2).subtract(BigInteger.ONE).mod(n);
    }
}
