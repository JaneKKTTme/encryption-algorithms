import java.util.ArrayList;
import java.util.Random;

public class GenerationOfSimpleOddRandomNumbers {
    private final static int MIN_LINE = 2;
    private final static int MAX_LINE = 50;

    /*
    doGenerationOfSimpleOddRandomNumbers() - main public function
     */
    public static String doGenerationOfSimpleOddRandomNumbers() {
        ArrayList<Integer> simpleOddNumbers = doSieveOfEratosthenes();

        //  Delete the one even number : 2
        simpleOddNumbers.remove(0);

        /*
        Build and return a pair, which contents :
        1) a simple and odd number P
        2) a numbers that is antiderivative root on module P
        */
        return buildPair(simpleOddNumbers);
    }

    /*
    doSieveOfEratosthenes() - accomplish sieve of Eratosthenes algorithm
    */
    private static ArrayList<Integer> doSieveOfEratosthenes() {
        ArrayList<Integer> simpleNumbers = new ArrayList<>();

        // Full list with numbers from 2 to 50
        for (int i = MIN_LINE; i <= MAX_LINE; i++) {
            simpleNumbers.add(i);
        }

        //  Delete all numbers that are not simple but composite
        for (int i = 0; i <= simpleNumbers.size() + 2; i++) {
            for (int j = i + 1; j < simpleNumbers.size(); j++) {
                if (simpleNumbers.get(j) % simpleNumbers.get(i) == 0) {
                    simpleNumbers.remove(j);
                }
            }
        }

        return simpleNumbers;
    }

    /*
    buildPair() - pick up two numbers:
    1) simple and odd number P
    2) antiderivative root on module P
     */
    private static String buildPair(ArrayList<Integer> simpleNumbers) {
        // Take first simple and odd number from created list
        Random rnd = new Random(System.currentTimeMillis());
        Integer simpleOddNumber = simpleNumbers.get(rnd.nextInt(simpleNumbers.size()));
        // If this number equals to 3, change it
        while (simpleOddNumber == 3)
            simpleOddNumber = simpleNumbers.get(rnd.nextInt(simpleNumbers.size()));

        // Save list of divisors
        ArrayList<Integer> divisors = findDivisors(simpleOddNumber - 1);

        // Pick up numbers that are antiderivative roots on module simpleOddNumber
        ArrayList<Integer> antiderivativeRoot = new ArrayList<>();

        /*
        Check the condition of existence an antiderivative root
        g ^ i[k] mod p != 1, where:
        1) g - sought antiderivative root on module p;
        2) p - number for which is searched g;
        3) i[k] - one of p divisors
        */
        for (int degree = MIN_LINE; degree < simpleOddNumber; degree++) {
            int amountOfExecutedCondition = 0;
            for (Integer integer : divisors) {
                if (Math.pow(degree, integer) % simpleOddNumber != 1) {
                    amountOfExecutedCondition += 1;
                }
            }
            if (amountOfExecutedCondition == divisors.size() && simpleNumbers.contains(degree)) {
                antiderivativeRoot.add(degree);
            }
        }

        // Return simpleOddNumber and random number from all antiderivative roots
        return (simpleOddNumber + " " + antiderivativeRoot.get(/*rnd.nextInt(antiderivativeRoot.size())*/0));
    }

    /*
    findDivisors() - canculate divisors to the number
    */
    private static ArrayList<Integer> findDivisors(Integer number) {
        // Declare empty list for divisors
        ArrayList<Integer> divisors = new ArrayList<>();

        /*
        Check each number less that the number itself
        If remainder of division is zero, it is one of divisors
        */
        for (int i = 2; i < number; i++) {
            if (number % i == 0)
                divisors.add(i);
        }

        return divisors;
    }
}