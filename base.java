import java.util.Random;


public class base {
    
    public static void main(String[] args) {
        int bitstringSize = 8; // length 𝑛
        final int populationSize = 100;
        String[] population = createPopulation(bitstringSize, populationSize);

        /*for (int x = 0; x < 100; x++) {
            System.out.println(population[x]);
            //getScore(population[x]);
        }*/

        String[] newPopulation = crossover(population, populationSize, bitstringSize);

        /*for (int x = 0; x < 100; x++) {
            System.out.println(newPopulation[x]);
            //getScore(newPopulation[x]);
        }*/
        
    }

    public static String[] createPopulation(int bitstringSize, int populationSize) {
        //create a list of a hundred randomly generated bitstrings of length 𝑛 called population
        Random random = new Random();
        int populationCheck = 0;
        String[] population = new String[populationSize];
        do{
            for (int i = 0; i < population.length; i++ ) {
                String bitstring = new String();
                for (int i2 = 0; i2 < bitstringSize; i2++) {
                    int bit = random.nextInt(2);
                    bitstring = bitstring + String.valueOf(bit);
                }
                if (bitstring.equals("11111111")) {
                    populationCheck++;
                }
                population[i] = bitstring;
            }
        } while (populationCheck == 100);

        return population;
    }

    public static int getScore(String bitstring) {
        int score = 0;
        for (int i = 0; i < bitstring.length(); i++) {
            char ch = bitstring.charAt(i);
            if (ch == '1') {
                score++;
            }
        }
        return score;
    }

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
}

