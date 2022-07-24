//desc: driver for Pollard Rho's p-1 prime factorization algorithm.
//  The code can be hard coded to accept an input to avoid typing large
//  primes by hand for testing, as the consol does not allow copy/paste.
//  Please look to line 21 (String input = "";) and enter your value
//  inside the quotes.

import java.math.BigInteger;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        //begin hardcoded test values for ease of use
        BigInteger test80 = new BigInteger("98834976202698839303077");
        BigInteger test100 = new BigInteger("410082333130306064848248112591");
        BigInteger test120 = new BigInteger("105424524626638150052140323242200343");
        BigInteger tricky1 = new BigInteger("58287130162018717115894883690626993265681465069289827126826807559318142751771499195697880859122243219388083950173834004284883928655363952353044400056981491049905887285181664311787140437370046667276399214525254861205916437634583075489756047225865860452266357671042767274691637127559448998320140196200796161887");
        //end hardcoded test values

        String input = "98834976202698839303077"; //TYPE HERE IN THE QUOTES TO HARD CODE AN INPUT
        BigInteger n = new BigInteger(input); //create the biginteger here

        PrimeFactor primeFactor = new PrimeFactor();
        primeFactor.factor(n);

//        multiThreadSOS(tricky1);

//        multiThreadSOSPrimes(tricky1);
    }

    //function calling and time processing for PollardMinusOne.java
    public static void pMinusOne(BigInteger n) {
        System.out.println("This is Pollard Rho's p-1 prime factorization method.");
        long startTime = System.nanoTime(); //start recoding time
        Double primeFactors = PollardMinusOne.Start(n);
        long stopTime = System.nanoTime(); //stop recording time
        //conversion steps from ns to s
        long differenceTime = stopTime - startTime;
        double secondsTime = (double)Long.parseLong(String.valueOf(differenceTime))/1000000000.0;

        System.out.println("The prime number: " + n + ", has factors: \n" + primeFactors);
        System.out.println("The time till completion was: " + secondsTime + "s");
    }

    //below is two threading implementations for the SumOfSquares classes
    //work is attempted to be split up evenly between cores, but it didn't work to well in practice
    //this method was derived from the Numberphile YouTube video "Breaking RSA"
    //https://www.youtube.com/watch?v=-ShwJqAalOk
    //and the wikipedia page
    //https://en.wikipedia.org/wiki/Fermat%27s_theorem_on_sums_of_two_squares
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
}
