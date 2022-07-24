//desc: Wrapper class containing a few variations of Pollards Rho
//      Does not take advantage of Double.java

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class PrimeFactor {

    public PrimeFactor () {
        //do nothing
    }

    public BigInteger factor(BigInteger n) {
        long start = System.nanoTime(); //start time

        //We would change the function depending on what we wanted to test
        //just hard code values
        BigInteger q = wikiPollardRho(n);

        long end = System.nanoTime(); // end time
        BigInteger p = n.divide(q);

        try {
            assert (p.multiply(q).equals(n));
        } catch (Exception e) {
            System.out.println("_____Failed____");
        }
        System.out.println("Q:  " + q);
        System.out.println("P:  " + p);
        System.out.println("Time: " + (end - start)/1000000000f + "s"); //calculate time
        System.out.println("---------------------\n");
        return q;
    }

    //one wikipedia implementation
    //https://en.wikipedia.org/wiki/Pollard%27s_rho_algorithm
    private BigInteger wikiPollardRho(BigInteger n) {
         BigInteger a = BigInteger.TWO;
         BigInteger b = BigInteger.TWO;
         BigInteger d = BigInteger.ONE;

        while (d.equals(BigInteger.ONE)) {
            a = g(a, n);
            b = g(g(b, n),n);
            d = (a.subtract(b).abs()).gcd(n);
        }

//        if (d.equals(n)) {
//            return null;
//        } else {
//            return d;
//        }
        return d.equals(n) ? null : d;
    }

    //textbook implementation
    //uses list and is very slow
    //Reagan made this to test out the algorithm before coding further
    private BigInteger pollardRho(BigInteger n) {
        List<BigInteger> x = new ArrayList<BigInteger>();
        int i = 1;
        x.add(new BigInteger(n.bitLength(), new SecureRandom()));
        BigInteger y = x.get(0);
        //BigInteger k = BigInteger.TWO;
        int k = 2;
        while (true) {
            x.add(x.get(i-1).pow(2).subtract(BigInteger.ONE).mod(n));
            BigInteger d = (y.subtract(x.get(i))).gcd(n);
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

    //polynomial function
    //We played around a bit with changing the function but
    //didn't find much of a difference in speed
    private BigInteger g(BigInteger x, BigInteger n) {
        return x.pow(2).subtract(BigInteger.ONE).mod(n);
    }
}
