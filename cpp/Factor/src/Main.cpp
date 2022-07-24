//desc: C/C++ implementation of Pollard-Rho 
//      Also contains an implementation of Brent's optimized Pollard-Rho

#include <iostream>
#include <ctime>
#include <cmath>
#include <stdio.h>
#include <gmpxx.h>

mpz_class BrentPollardRho(mpz_class&);
mpz_class PollardRho(mpz_class&);
mpz_class gx(mpz_class, mpz_class);

gmp_randclass rng(gmp_randinit_default);

int main()
{
    //hardcoded values to make my life easier
    const std::string test80 = "98834976202698839303077";
    const std::string test120 = "105424524626638150052140323242200343";
    const std::string test140 = "45319868827794527371331674843886952588281";
    const std::string test160 = "368885247776930520818119416349538289444373190953";
    const std::string test180 = "817647456309020395072646021766285397615408588070089049";
    const std::string test200 = "475736781468584142860558801413519229596593476345036008040593";
    const std::string num = "3743418093783689343889377032792021";
    
    time_t begin, end;
    mpz_class n(test120);
    mpz_class p;
    mpz_class q;
    
    gmp_printf("test140: %Zd\n", n); //I change this based on what I run
    
    time (&begin);
    p = BrentPollardRho(n);
    //p = PollardRho(n);
    time(&end);
    
    double diff = difftime(end, begin);
    
    q = n / p;
    
    gmp_printf("p: %Zd\n", p);
    gmp_printf("q: %Zd\n", q);
    printf ("time taken: %.2lf seconds.\n", diff );
}

//brent cycle finding
//this is the fastest algorithm we found
//Factored 140 in 22 minutes
//https://comeoncodeon.wordpress.com/2010/09/18/pollard-rho-brent-integer-factorization/
//https://xn--2-umb.com/09/12/brent-pollard-rho-factorisation/index.html
//https://maths-people.anu.edu.au/~brent/pd/rpb051i.pdf
mpz_class BrentPollardRho(mpz_class &n) {
    
    const uint64_t m = 1000;
    mpz_class x, y, ys, g, r, q;
    
    y = rng.get_z_range(n-1);
    g = 1;
    r = 1;
    q = 1;
    
    //repeats the polynomial function m times on value1 before comparing to value2
    while (g == 1)
    {
        x = y;
        for (uint64_t i = 0; i < r; ++i)
        {
            y = gx(y, n);
        }
        uint64_t k = 0;
        while (k < r && g==1)
        {
            for (uint64_t i = 0; i < m && i < r-k; ++i)
            {
                ys = y;
                y = gx(y, n);
                q = (q*abs(x-y))%n;
            }
            g = gcd(q, n);
            k += m;
        }
        r <<= 1;
    }
    
    if (g == n)
    {
        while (true) 
        {
            ys = gx(ys, n);
            g = gcd(abs(x-ys), n);
            if (g > 1)
                break;
        }
    }
    return g;
}

//textbook implementation similar to Java's
mpz_class PollardRho(mpz_class &n) {
    mpz_class a = rng.get_z_range(n);
    mpz_class b = a;
    mpz_class d = 1;
    
    while (d == 1) {
        a = gx(a, n);
        b = gx(gx(b, n), n);
        d = gcd(abs(a - b), n);
    }
    return d;
}

//same polynomial function
mpz_class gx(mpz_class x, mpz_class n) {
    return (x*x - 1) % n;
}
