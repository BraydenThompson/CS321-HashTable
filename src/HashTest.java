/**
 * Test class for two HashTables, one using linear hashing, and one using double hashing.
 * Supports three input types, TODO
 */

import java.util.Random;

public class HashTest {
    public static void main(String[] args)
    {
        final long MIN_TABLE_SIZE = 500;
        final long MAX_TABLE_SIZE = 1000;
        final int PRIME_TESTS = 2;

        int inputType;                 // Input type: 1 - Random Integers, 2 - Current Time Ms, 3 - word - list
        float loadFactor;                 // Load factor: Target alpha value, between 0 and 1
        int debugMode;

        /* Read in user arguments */
        if (args.length == 2 || args.length == 3)
        {
            /* Input type */
            if (args[0].equals("1") || args[0].equals("2") || args[0].equals("3"))
            {
                inputType = Integer.parseInt(args[0]);
            }
            else
            {
                printUsage();
                System.exit(1);
            }

            /* Load Factor */
            if (Float.parseFloat(args[1]) > 0 && Float.parseFloat(args[1]) <= 1)
            {
                loadFactor = Float.parseFloat(args[1]);
            }
            else
            {
                printUsage();
                System.exit(1);
            }
        }

        if (args.length == 3)
        {
            /* Load Factor */
            if (args[2].equals("0") || args[2].equals("1"))
            {
                debugMode = Integer.parseInt(args[0]);
            }
            else
            {
                printUsage();
                System.exit(1);
            }
        }
    }

    private static void printUsage()
    {
        System.out.println("HashTest is used as follows:");
        System.out.println("java HashTest <input type> <load factor> [<debug level>]");
        System.out.println("Input type: 1 - Random Integers, 2 - Current Time Ms, 3 - word - list");
        System.out.println("Load factor: Target alpha value, between 0 and 1");
        System.out.println("(Optional) Debug level: (Default) 0 - Print summary, 1 - Print summary and dump tables");
    }


    /**
     * findTwinPrimes - Finds the smallest twin prime numbers (a prime two away from another prime)
     * @param min the lower bound of the range to search (inclusive)
     * @param max the upper bound of the range to search (exclusive)
     * @param tests the number of times to test if a number is prime, each test has a false positive chance of (1 / 10^13)
     * @return The smallest prime number p within the range such that p - 2 is also prime. -1 if none exits
     */
    private static long findTwinPrimes(long min, long max, int tests)
    {
        // Make sure min starts on an odd number
        if ((min % 2) == 0)
        {
            min += 1;
        }

        for (long i = min; i < max; i += 2)      // Test each number in range
        {
            if (isPrime(i + 2, tests) && isPrime(i, tests))    // If the number + 2 and the number are both prime
            {
                return i + 2;                                     // Return the larger of the two twin primes
            }
        }

        return -1;
    }

    /**
     * isPrime - T
     * @param p the number to test if it is prime
     * @param tests the number of times to test if a number is prime, each test has a false positive chance of (1 / 10^13)
     * @return true if tests return true, false otherwise
     */
    private static boolean isPrime(long p, int tests)
    {
        int numTests = 0;       // The number of tests run so far
        long value;          // The result of each test (= 1 if prime)
        long a;                  // A random number where 1 < a < p
        boolean pass = true;    // Whether the current test has passed
        Random rand = new Random();

        String binaryString = Long.toBinaryString(p - 1);    // Generate the binary of (p - 1)
        while (pass && (numTests < tests))
        {
            a = (rand.nextInt((int) (p - 1)) + 1);      // set a to a random number where 1 < a < p
            value = a;                               // set result to a for the first iteration

            /* Perform a^(p - 1) mod p using Multiply and Divide method */
            for (int i = 1; i < binaryString.length(); i++) // Iterate along each digit in (p - 1) from left to right (ignoring first digit)
            {
                value = (value * value) % p;              // Always square the current value

                if (binaryString.charAt(i) == '1')
                {
                    value = (value * a) % p;              // If the digit is a 1, multiply
                }
            }

            if (value != 1) {
                pass = false;   // a^(p - 1) % p != 1, then p is not prime.
            }
            numTests++;
        }
        return pass;
    }
}