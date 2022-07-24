import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class RSA {
    private BigInteger p;
    private BigInteger q;
    private final BigInteger publicKey = BigInteger.valueOf(65537);
    private final BigInteger modulusN;
    private final BigInteger privateKey;

    public RSA(int bits) {
        //generates p and q from primeGenerator class
        //uses extended euclid to generate privatekey
        populateKeys(new PrimeGenerator(bits, 50));
        this.modulusN = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.valueOf(1)).multiply(q.subtract(BigInteger.valueOf(1)));
        this.privateKey = extgcd(publicKey, phi).get(1).mod(phi);
    }

    //takes string and converts to encrypted integer
    public BigInteger encrypt(String s) {
        //System.out.println(convertFromString(s));
        return convertFromString(s).modPow(publicKey, modulusN);
    }

    //takes encrypted integer and converts to string
    public String decrypt(BigInteger d) {
        return convertToString(d.modPow(privateKey, modulusN));
    }

    //converts string to signed integer
    public BigInteger sign(String s) {
        return convertFromString(s).modPow(privateKey, modulusN);
    }

    //unsigns signed integer into string
    public String unSign(BigInteger d) {
        return convertToString(d.modPow(publicKey, modulusN));
    }

    //code given in project description
    private BigInteger convertFromString(String m) {
        BigInteger x = BigInteger.ZERO;
        for (int i =0; i < m.length(); i++) {
            x = x.shiftLeft(8);
            x = x.xor(new BigInteger(String.valueOf(m.charAt(i)).getBytes()));
        }
        return x;
    }

    //code given in HW7 part 5
    private String convertToString(BigInteger x) {
        String m = "";
        while (x.compareTo(BigInteger.valueOf(0)) > 0) {
            m = (char) x.and(BigInteger.valueOf(0xff)).intValue() + m;
            x = x.shiftRight(8);
        }
        return m;
    }

    //populates p and q
    private void populateKeys(PrimeGenerator primeGenerator) {
        this.p = primeGenerator.safePrimeGenerate();
        this.q = primeGenerator.safePrimeGenerate();

        //make sure primes aren't equal or close together
        //todo more here
        //sum of squares test
        while (p.equals(q)) {
            this.q = primeGenerator.safePrimeGenerate();
        }
    }

    //pseudo code courtesy of the textbook and
    //https://en.wikipedia.org/wiki/Extended_Euclidean_algorithm
    private List<BigInteger> extgcd(BigInteger a, BigInteger b) {
        if (b.equals(BigInteger.ZERO)) {
            return Arrays.asList(a, BigInteger.ONE, BigInteger.ZERO);
        } else {
            List<BigInteger> temp = extgcd(b, a.mod(b));
            return Arrays.asList(temp.get(0), temp.get(2), temp.get(1).subtract(a.divide(b).multiply(temp.get(2))));
        }
    }

    //getters
    public BigInteger getPublicKey() {
        return publicKey;
    }

    public BigInteger getPrivateKey() {
        return privateKey;
    }

    public BigInteger getModulusN() {
        return modulusN;
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getQ() {
        return q;
    }
}
