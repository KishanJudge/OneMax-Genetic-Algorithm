package src;

import java.util.Random;
import java.util.Scanner;

public class base {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int timesForAverage = 20;
        int graphStart = 1;
        int graphEnd = 30;

        //task eight
        /*System.out.println("Enter solution length: ");
        String tryInput = scanner.nextLine(); // length 𝑛
        while (!(isDigit(tryInput)) || isZero(tryInput)) {
            System.out.println("Solution length must be an even number");
            tryInput = scanner.nextLine();
        }
        int bitstringSize = Integer.parseInt(tryInput);
        while ((bitstringSize % 2) != 0) {
            System.out.println("Solution length must be even: ");
            bitstringSize = scanner.nextInt();
        }*/

        //task nine
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
            for (int n2 = 0; n2 < timesForAverage; n2++) {
                long startTime = System.nanoTime();
                String[] population = createPopulation(bitstringSize, populationSize);

                boolean perfectCheck = isPerfect(populationSize, population, bitstringSize);
                int generations = 0;

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

                    generations++;
                    perfectCheck = isPerfect(populationSize, population, bitstringSize);
                }

                long endTime = System.nanoTime();
                float finalTime = (endTime - startTime) / 1000000;

                totalGenerations = totalGenerations + generations;
                totalTime = totalTime + finalTime;

            }

            averageGenerations[n - graphStart] = (totalGenerations / timesForAverage);
            averageTime[n - graphStart] = (totalTime / (float) (timesForAverage));
        }

        for (int x4 = 0; x4 < arrSize; x4++) {
            System.out.println("n: " + valueOfN[x4]);
            System.out.println("generations: " + averageGenerations[x4]);
            System.out.println("time: " + averageTime[x4]);
        }


        //task seven
        /*final int populationSize = 100;
        int mutationRate = 15;
        String[] population = createPopulation(bitstringSize, populationSize);

        boolean perfectCheck = isPerfect(populationSize, population, bitstringSize);
        int generations = 0;

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

           generations++;
           perfectCheck = isPerfect(populationSize, population, bitstringSize);
        }

        for (int x3 = 0; x3 < populationSize; x3++) {
            System.out.println(population[x3]);
        }

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

        graph graphObject = new graph(averageGenerations, averageTime, valueOfN);
        graphObject.displayGraph();

    }

    //task one
    public static String[] createPopulation(int bitstringSize, int populationSize) {
        //create a list of a hundred randomly generated bitstrings of length 𝑛 called population
        Random random = new Random();
        boolean perfectCheck = false;
        String[] population = new String[populationSize];
        do{
            for (int i = 0; i < population.length; i++ ) {
                String bitstring = new String();
                for (int i2 = 0; i2 < bitstringSize; i2++) {
                    int bit = random.nextInt(2);
                    bitstring = bitstring + String.valueOf(bit);
                }
                population[i] = bitstring;
            }
            perfectCheck = isPerfect(populationSize, population, bitstringSize);

        } while (perfectCheck);

        return population;
    }

    //task two
    public static int score(String bitstring) {
        int score = 0;
        for (int i = 0; i < bitstring.length(); i++) {
            char ch = bitstring.charAt(i);
            if (ch == '1') {
                score++;
            }
        }
        return score;
    }

    //task three
    public static String[] crossover(String[] population, int populationSize, int bitstringSize) {
        String[] newPopulation = new String[populationSize];
        for (int i = 0; i < populationSize / 2; i++) {
            String parent1 = population[2 * i];
            String parent2 = population[(2 * i) +1];

            String offspring1 = parent1.substring(0 , bitstringSize / 2)
                    + parent2.substring(bitstringSize / 2 , bitstringSize);
            String offspring2 = parent2.substring(0 , bitstringSize / 2)
                    + parent1.substring(bitstringSize / 2 , bitstringSize);

            newPopulation[2 * i] = offspring1;
            newPopulation[(2 * i) + 1] = offspring2;


        }
        return newPopulation;
    }

    //task four
    public static String mutation(int mutationRate, int bitstringSize, String bitstring) {
        Random random = new Random();
        StringBuilder newBitstring = new StringBuilder(bitstring);

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
    public static boolean evaluate(String bitstring, String newBitstring, int populationSize) {
        int parentScore = score(bitstring);
        int offspringScore = score(newBitstring);
        if (offspringScore > parentScore) {
            return true;
        }
        return false;
    }

    public static boolean isPerfect(int populationSize, String[] population, int bitstringSize) {
        int fullPerfectCheck = 0;
        for (int i = 0; i < populationSize; i++) {
            if (score(population[i]) == bitstringSize) {
                fullPerfectCheck++;
            }
        }
        if (fullPerfectCheck == populationSize) {
            return true;
        }
        return false;
    }

    public static boolean isDigit(String string) {
        if (string == null || string.isEmpty()) {
            return false;
        }
        for (char c : string.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isZero(String string) {
        int count = 0;
        if (string == null || string.isEmpty()) {
            return true;
        }
        if (string.equals("0")) {
            return true;
        }
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
}

