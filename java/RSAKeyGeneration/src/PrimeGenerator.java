import java.math.BigInteger;
import java.security.SecureRandom;

public class PrimeGenerator {
    //this class is solely to generate primes

    private int bits = 0;
    private int tests = 0;

    PrimeGenerator(int bits, int tests) {
        this.bits = bits;
        this.tests = tests;
    }

    //debugging
    public BigInteger javaGenerate() {
        return BigInteger.probablePrime(this.bits, new SecureRandom());
    }

    //find closest prime
    public BigInteger closestPrimeUp(BigInteger x) {
        if (x.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            x = x.add(BigInteger.ONE);
        }
        while (!millerRabin(x)) {
            x = x.add(BigInteger.TWO);
        }
        return x;
    }

    public BigInteger closestPrimeDown(BigInteger x) {
        if (x.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            x = x.add(BigInteger.ONE);
        }
        while (!millerRabin(x)) {
            x = x.add(BigInteger.TWO);
        }
        return x;
    }

    //an idea that I had while coding
    //may or may not work
    public BigInteger weirdSafePrimeGenerate() {
        BigInteger x = generatePrime(bits/2).multiply(BigInteger.TWO).
                multiply(generatePrime(bits/2).multiply(BigInteger.TWO)).add(BigInteger.ONE);

        while (!millerRabin(x)) {
            //System.out.println(x);
            x = generatePrime(bits/2).multiply(BigInteger.TWO).
                    multiply(generatePrime(bits/2).multiply(BigInteger.TWO)).add(BigInteger.ONE);
        }
        return x;
    }

    //safe prime generation
    //follows in class examples
    public BigInteger safePrimeGenerate() {
        BigInteger x = generatePrime(bits).multiply(BigInteger.TWO).add(BigInteger.ONE);

        while (!millerRabin(x)) {
            //System.out.println(x);
            x = generatePrime(bits).multiply(BigInteger.TWO).add(BigInteger.ONE);
        }
        return x;
    }

    //my personal method for generating primes
    //modelled after what was discussed in class
    public BigInteger generatePrime(int bitsize) {
        BigInteger x = randomBigInteger(bitsize/2, BigInteger.TWO.pow(bitsize/2).subtract(BigInteger.ONE), BigInteger.TWO.pow(bitsize/2 - 1));

        if (x.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            x = x.add(BigInteger.ONE);
        }
        while (!millerRabin(x)) {
            x = x.add(BigInteger.TWO);
        }
        return x;
    }

    //generate random big integer in range
    private BigInteger randomBigInteger(int bits, BigInteger max, BigInteger min) {
        BigInteger r = new BigInteger(bits, new SecureRandom());

        while (r.compareTo(max) > 0 && r.compareTo(min) < 0) {
            r = new BigInteger(bits, new SecureRandom());
        }
        return r;
    }

    //miller rabin derived from wikipedia pseudocode
    //https://en.wikipedia.org/wiki/Miller%E2%80%93Rabin_primality_test
    private Boolean millerRabin(BigInteger n) {
        int r = 0;
        BigInteger d = n.subtract(BigInteger.ONE);

        while (d.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            d = d.divide(BigInteger.TWO);
            r++;
        }

        for (int i = 0; i < tests; i++) {

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

    //getters/setters just in case
    public int getBits() {
        return bits;
    }

    public void setBits(int bits) {
        this.bits = bits;
    }

    public int getTests() {
        return tests;
    }

    public void setTests(int tests) {
        this.tests = tests;
    }
}