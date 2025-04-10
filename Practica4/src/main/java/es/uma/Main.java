//--------------------------------------------------------------------------------
/* 
 * INTEGRANTES DEL GRUPO:
 * - Javier Toledo Delgado
 * - María Paulina Ordóñez Walkowiak
 */
//--------------------------------------------------------------------------------

package es.uma;

import org.mps.EvolutionaryAlgorithm;
import org.mps.EvolutionaryAlgorithmException;
import org.mps.crossover.CrossoverOperator;
import org.mps.crossover.TwoPointCrossover;
import org.mps.mutation.GaussianMutation;
import org.mps.mutation.MutationOperator;
import org.mps.selection.SelectionOperator;
import org.mps.selection.TournamentSelection;

public class Main {
    public static void main(String[] args) throws EvolutionaryAlgorithmException {
        //This is why EvolutionaryAlgorithm fails in certain cases
        int tournamentSize = 2;

        SelectionOperator selectionOperator = new TournamentSelection(tournamentSize);
        MutationOperator mutationOperator = new GaussianMutation(1, 2.5);
        CrossoverOperator crossoverOperator = new TwoPointCrossover();

        EvolutionaryAlgorithm algorithm = new EvolutionaryAlgorithm(selectionOperator, mutationOperator, crossoverOperator);
        int[][] population = {
            {7, 2, 9},
            {4, 5, 6},
            {3, 6, 3}
        };
        

        int[][] optimizedPopulation = algorithm.optimize(population);
        // Print the optimized population
        System.out.println("Optimized Population:");
        for (int i = 0; i < optimizedPopulation.length; i++) {
            for (int j = 0; j < optimizedPopulation[i].length; j++) {
                System.out.print(optimizedPopulation[i][j] + " ");
            }
            System.out.println();
        }
        //Before fixing the code, the output was:
        // Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: Index 3 out of bounds for length 3
        // at org.mps.EvolutionaryAlgorithm.optimize(EvolutionaryAlgorithm.java:57)
        // at es.uma.Main.main(Main.java:29)

        //This is because the for loop in the optimize method of EvolutionaryAlgorithm tries to access i and i+1 
        //in the population array, but when i is the last index, i+1 is out of bounds.

        //The fix was to change the for loop to iterate until population.length - 1, so that i+1 is always within bounds.
        //The output after the fix is:
        // Optimized Population:
        // 7 2 9 
        // 4 5 6
        // 3 4 0 
    }
}