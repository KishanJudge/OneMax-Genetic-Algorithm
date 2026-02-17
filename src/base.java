package src;

import java.util.Random;
import java.util.Scanner;

public class base {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //define the number of attempts used to calculate an average, the start of the graph, and the end of the graph
        int timesForAverage = 20;
        int graphStart = 1;
        int graphEnd = 30;

        //task eight
        //gets bitstring length input, and validates input by ensuring it is an even number
        /*System.out.println("Enter solution length: ");
        String tryInput = scanner.nextLine(); // length 𝑛
        //while the input is not a digit, or is zero, or is not even, get the input again
        while (!(isDigit(tryInput)) || isZero(tryInput) || !(isEven(tryInput)) {
            System.out.println("Solution length must be an even number");
            tryInput = scanner.nextLine();
        }
        //convert the input into an integer
        int bitstringSize = Integer.parseInt(tryInput);
        */

        //task nine
        //creates arrays to store the averages generations and time, and also the valu of n
        int arrSize = graphEnd - graphStart + 1;
        int[] averageGenerations = new int[arrSize];
        float[] averageTime = new float[arrSize];
        int[] valueOfN = new int[arrSize];

        for (int n = graphStart; n < graphEnd + 1; n++) {
            int bitstringSize = n;
            valueOfN[n - graphStart] = n;
            final int populationSize = 100;
            int mutationRate = 15;
            int totalGenerations = 0;
            float totalTime = 0;
            //repeat for number of times needed for an average
            for (int n2 = 0; n2 < timesForAverage; n2++) {
                //start recording the time taken
                long startTime = System.nanoTime();
                //create an initial population
                String[] population = createPopulation(bitstringSize, populationSize);
                //create a variable to store whether the population is perfect
                boolean perfectCheck = isPerfect(populationSize, population, bitstringSize);
                //create a variable to track the generations taken to get the perfect solution
                int generations = 0;
                //repeat the processes of crossover, mutation and evalutation until the population is perfect
                while (!perfectCheck) {

                    String[] newPopulation = crossover(population, populationSize, bitstringSize);

                    for (int x = 0; x < populationSize; x++) {
                        newPopulation[x] = mutation(mutationRate, bitstringSize, newPopulation[x]);
                    }

                    for (int x2 = 0; x2 < populationSize; x2++) {
                        if (evaluate(population[x2], newPopulation[x2], populationSize)) {
                            population[x2] = newPopulation[x2];
                        }
                    }
                    //increment number of generations, and check if population is perfect
                    generations++;
                    perfectCheck = isPerfect(populationSize, population, bitstringSize);
                }
                //stop tracking time taken
                long endTime = System.nanoTime();
                //calculate total time taken in ms
                float finalTime = (endTime - startTime) / 1000000;
                //add to the total generations and total time
                totalGenerations = totalGenerations + generations;
                totalTime = totalTime + finalTime;

            }
            //after each of x times for an average, calculate the average for generations and time, then add to arrays
            averageGenerations[n - graphStart] = (totalGenerations / timesForAverage);
            averageTime[n - graphStart] = (totalTime / (float) (timesForAverage));
        }
        //display value of n, and the average generations and time for that value
        for (int x4 = 0; x4 < arrSize; x4++) {
            System.out.println("n: " + valueOfN[x4]);
            System.out.println("generations: " + averageGenerations[x4]);
            System.out.println("time: " + averageTime[x4]);
        }


        //task seven
        /*final int populationSize = 100;
        int mutationRate = 15;
        //create an initial population
        String[] population = createPopulation(bitstringSize, populationSize);
        //create a variable to store whether the population is perfect
        boolean perfectCheck = isPerfect(populationSize, population, bitstringSize);
        //create a variable to track the generations taken to get the perfect solution
        int generations = 0;
        //repeat the processes of crossover, mutation and evalutation until the population is perfect
        while (!perfectCheck) {

            String[] newPopulation = crossover(population, populationSize, bitstringSize);

            for (int x = 0; x < populationSize; x++) {
                newPopulation[x] = mutation(mutationRate, bitstringSize, newPopulation[x]);
            }

            for (int x2 = 0; x2 < populationSize; x2++) {
                if (evaluate(population[x2], newPopulation[x2], populationSize)) {
                   population[x2] = newPopulation[x2];
               }
           }
           //increment number of generations, and check if population is perfect
           generations++;
           perfectCheck = isPerfect(populationSize, population, bitstringSize);
        }

        //display the number of generations taken to reach the perfect solution
        System.out.println("algorithm took " + generations + " generations");*/


        //task five
        /*final int populationSize = 100;
        int mutationRate = 15;
        String[] population = createPopulation(bitstringSize, populationSize);

        for (int x = 0; x < populationSize; x++) {
            score(population[x]);
            population[x] = mutation(mutationRate, bitstringSize, population[x]);
        }

        String[] newPopulation = crossover(population, populationSize, bitstringSize);

        //task six
        for (int x2 = 0; x2 < populationSize; x2++) {
            if (evaluate(population[x2], newPopulation[x2], populationSize)) {
                population[x2] = newPopulation[x2];
            }
        }*/
        //passes in the values of average generations, average time, and value of n to the graph class
        graph graphObject = new graph(averageGenerations, averageTime, valueOfN);
        //displays the graph
        graphObject.displayGraph();

    }

    //task one
    //creates an initial population
    public static String[] createPopulation(int bitstringSize, int populationSize) {
        //create a list of a hundred randomly generated bitstrings of length 𝑛 called population
        Random random = new Random();
        boolean perfectCheck = false;
        String[] population = new String[populationSize];
        do{
            //iterates through the population
            for (int i = 0; i < population.length; i++ ) {
                String bitstring = new String();
                //iterates through each bit in a bitstring
                for (int i2 = 0; i2 < bitstringSize; i2++) {
                    //assigns 0 or 1 to the bit using Random
                    int bit = random.nextInt(2);
                    bitstring = bitstring + String.valueOf(bit);
                }
                population[i] = bitstring;
            }
            perfectCheck = isPerfect(populationSize, population, bitstringSize);
        //ensures the population is remade to be imperfect if it starts off perfect
        } while (perfectCheck);

        return population;
    }

    //task two
    //calculates a score based on how many 1s in a bitstring
    public static int score(String bitstring) {
        int score = 0;
        //iterates through the bitstring and adds to score everytime a 1 is encountered
        for (int i = 0; i < bitstring.length(); i++) {
            char ch = bitstring.charAt(i);
            if (ch == '1') {
                score++;
            }
        }
        return score;
    }

    //task three
    //crosses over parts of two parent bitstring to create two offsprings
    public static String[] crossover(String[] population, int populationSize, int bitstringSize) {
        //create a new population to store offspring
        String[] newPopulation = new String[populationSize];
        //iterates through population
        for (int i = 0; i < populationSize / 2; i++) {
            //defines the parents
            String parent1 = population[2 * i];
            String parent2 = population[(2 * i) +1];
            //combines the parents to make two offspring
            String offspring1 = parent1.substring(0 , bitstringSize / 2)
                    + parent2.substring(bitstringSize / 2 , bitstringSize);
            String offspring2 = parent2.substring(0 , bitstringSize / 2)
                    + parent1.substring(bitstringSize / 2 , bitstringSize);
            //adds the offspring to the new population
            newPopulation[2 * i] = offspring1;
            newPopulation[(2 * i) + 1] = offspring2;


        }
        return newPopulation;
    }

    //task four
    //randomly switches bits in a bitstring
    public static String mutation(int mutationRate, int bitstringSize, String bitstring) {
        Random random = new Random();
        StringBuilder newBitstring = new StringBuilder(bitstring);
        //iterates through each bit, and flips the bits if random number falls within the probability
        for (int i = 0; i < bitstringSize; i++) {
            int probability = random.nextInt(0, 100);
            if (probability < mutationRate) {
                if (bitstring.charAt(i) == '1') {
                    newBitstring.setCharAt(i, '0');
                }
                else {
                    newBitstring.setCharAt(i, '1');
                }
            }
        }
        String finalBitstring = newBitstring.toString();
        return finalBitstring;
    }

    //task six
    //checks if offspring has a higher score then parent
    public static boolean evaluate(String bitstring, String newBitstring, int populationSize) {
        int parentScore = score(bitstring);
        int offspringScore = score(newBitstring);
        //comapres parent and offspring scores, if offspring greater, return true
        if (offspringScore > parentScore) {
            return true;
        }
        return false;
    }

    //checks if population is perfect
    public static boolean isPerfect(int populationSize, String[] population, int bitstringSize) {
        int fullPerfectCheck = 0;
        //iterates through population and checks if each member is perfect
        for (int i = 0; i < populationSize; i++) {
            if (score(population[i]) == bitstringSize) {
                fullPerfectCheck++;
            }
        }
        //if each member perfect, whole population is perfect so return true
        if (fullPerfectCheck == populationSize) {
            return true;
        }
        return false;
    }

    //checks if input is digit
    public static boolean isDigit(String string) {
        //checks if input is empty
        if (string == null || string.isEmpty()) {
            return false;
        }
        //checks if all chars are digits
        for (char c : string.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    //checks if input = 0
    public static boolean isZero(String string) {
        //checks if input is empty
        int count = 0;
        if (string == null || string.isEmpty()) {
            return true;
        }
        //checks if 0
        if (string.equals("0")) {
            return true;
        }
        //checks if multiple 0s
        for (int i = 0; i < string.length() - 1; i++) {
            if (string.charAt(i) == '0'){
                count++;
            }
        }
        if (count == string.length()) {
            return false;
        }
        return false;
    }

    //checks if input is even
    public static boolean isEven(String string) {
        //first ensures input is a digit
        if (isDigit(string)) {
            //sees if dividing by two gives 0 as a remainder, if so it is even
            if ((Integer.parseInt(string) % 2) == 0) {
                return true;
            }
        }
        return false;
    }
}

