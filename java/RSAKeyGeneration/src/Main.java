//Written by Reagan Milchling
//CMSC441 Final Project RSA
//
//This program generates a new random keypair everytime it is run
//
//You can also generate however many primes you wish by using the
//static method primeTesting(int int int) in main
//
//The program will spit out encrypted, decrypted, and signed versions
//of the string message in main(args) along with the keys needed

import java.math.BigInteger;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        BigInteger test80 = new BigInteger("98834976202698839303077");
        BigInteger test100 = new BigInteger("410082333130306064848248112591");
        BigInteger test120 = new BigInteger("105424524626638150052140323242200343");
        BigInteger test140 = new BigInteger("45319868827794527371331674843886952588281");
        BigInteger test160 = new BigInteger("368885247776930520818119416349538289444373190953");
        BigInteger test180 = new BigInteger("817647456309020395072646021766285397615408588070089049");
        BigInteger test200 = new BigInteger("475736781468584142860558801413519229596593476345036008040593");
        BigInteger tricky2 = new BigInteger("44823340001134646725215287284882501479971162747655312474344195491841621439454012870908997074594908656835436716399848026553605085401889315409018740249527539522367772100014131585967074633456150783820782832110200062925034142234431854800755901388394761775501642493746523598583689888382215414386425444410018014903");
        BigInteger tricky3 = new BigInteger("16163539281320744315171269616742395520744304400587166659377979228736312913575261779241514389163984286910444417652676641519489681932501410505816300557161094027146711937922886306245911261677681346281229840252306331329218093702680267397539085739595910542456451860764685202846820082490721685704336924217468941841");
        BigInteger tricky4 = new BigInteger("56209596429710520352479532071363737767233798437107688000246030657551507032250497170041548515975785265052766737721766135153840147878380629339161063369195354848709756421260864228054014900275509792955771190568707206083130847562837153109420205935284217970202488210961797878241055607555055039000334829695792779817");

        BigInteger p = new BigInteger("6864797660130609714981900799081393217269435300143305409394463459185543183397656052122559640661454554977296311391480858037121987999716643812574028291115057151");
        BigInteger q = new BigInteger("2354554362934160632043894486078042708492333378880940305711756378823279786354430141207369027491579460021176552545748799423359384276779021491649492126191");

        assert(p.multiply(q).equals(tricky3));

        PrimeFactor primeFactor = new PrimeFactor();

        BigInteger x = primeFactor.factor(test80);
        //System.out.println(x);
    }

    public static void multiThreadSOSPrimes(BigInteger n) {
        BigInteger offset = new BigInteger("4");
        BigInteger sqrt = n.sqrt().add(BigInteger.ONE);
        System.out.println(sqrt);
        SumOfSquaresPrimes pt1 = new SumOfSquaresPrimes("up", offset, n, sqrt, true);
        SumOfSquaresPrimes pt2 = new SumOfSquaresPrimes("down", offset, n, sqrt, false);
        SumOfSquaresPrimes pt3 = new SumOfSquaresPrimes("up2", offset, n, sqrt.add(BigInteger.ONE), true);
        SumOfSquaresPrimes pt4 = new SumOfSquaresPrimes("down2", offset, n, sqrt.add(BigInteger.ONE), false);

        pt1.start();
        pt2.start();
        pt3.start();
        pt4.start();
    }

    public static void multiThreadSOS(BigInteger n) {
        BigInteger offset = new BigInteger("2");
        BigInteger sqrt = n.sqrt().add(BigInteger.ONE);
        System.out.println(sqrt);
        SumOfSquares pt1 = new SumOfSquares("up", offset, n, sqrt, true);
        SumOfSquares pt2 = new SumOfSquares("down", offset, n, sqrt, false);
        SumOfSquares pt3 = new SumOfSquares("up2", offset, n, sqrt.add(BigInteger.ONE), true);
        SumOfSquares pt4 = new SumOfSquares("down2", offset, n, sqrt.add(BigInteger.ONE), false);

        pt1.start();
        pt2.start();
        pt3.start();
        pt4.start();
    }

    public static void part1()  {
        String message = "I deserve an A";
        int bits = 118;

        Scanner s = new Scanner(System.in);
        System.out.print("Enter number of bits: ");
        bits = Integer.parseInt(s.next());

        //bits is the number of bits for N
        RSA rsa = new RSA(bits);
        BigInteger c = rsa.encrypt(message);
        BigInteger signed = rsa.sign(message);
        String unsigned = rsa.unSign(signed);
        String d = rsa.decrypt(c);
        System.out.println("\n--------------------");
        System.out.println("Message to encrypt: " + message);
        System.out.println("Public Key:  " + rsa.getPublicKey());
        System.out.println("Private Key: " + rsa.getPrivateKey());
        System.out.println("ModulusN:    " + rsa.getModulusN());
        System.out.println("p: " + rsa.getP());
        System.out.println("q: " + rsa.getQ());
        System.out.println("Signed Message:    " + signed);
        System.out.println("Unsigned Message:  " + unsigned);
        System.out.println("Encrypted Message: " + c);
        System.out.println("Decrypted Message: " + d);
        System.out.println("--------------------\n");

        primeTesting(1024, 25, 1);
    }

    public static void primeTesting(int bits, int tests, int iterations) {
        PrimeGenerator primeGenerator = new PrimeGenerator(bits, tests);
        BigInteger temp;
        int count = 0;
        long start = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            temp = primeGenerator.safePrimeGenerate();
            //temp = null;
            if (!temp.isProbablePrime(1)) {
                System.out.println("Not Prime: " + temp);
                count ++;
            }
        }
        long end = System.nanoTime();
        System.out.println("Number of iterations/bits/S tests: " + iterations + "/" + bits + "/" + tests);
        System.out.println("Number wrong: " + count);
        System.out.println("Time: " + (end - start)/1000000000f);
    }
}
