import java.math.BigInteger;
import java.security.SecureRandom;

public class SumOfSquares implements Runnable {

    private Thread t = null;
    private final String threadName;
    private final BigInteger offset;
    private final BigInteger n;
    private final BigInteger a;
    private final boolean up;

    public SumOfSquares(String threadName, BigInteger offset, BigInteger n, BigInteger a, boolean up) {
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
            BigInteger b2 = a.pow(2).subtract(n);
            //System.out.println(threadName + ":  " + a);
            if (isSquare(b2)) {
                return b2.sqrt();
            }
//            if (up) {
//                a = a.add(offset);
//            } else {
//                a = a.subtract(offset);
//            }
            a = a.add(offset);

        }
        //return null;
    }

    private boolean isSquare(BigInteger x) {
        return x.sqrtAndRemainder()[1].equals(BigInteger.ZERO);
    }
}
