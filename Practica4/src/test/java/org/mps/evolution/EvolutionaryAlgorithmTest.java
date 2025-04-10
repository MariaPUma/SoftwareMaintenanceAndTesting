//--------------------------------------------------------------------------------
/* 
 * INTEGRANTES DEL GRUPO:
 * - Javier Toledo Delgado
 * - María Paulina Ordóñez Walkowiak
 */
//--------------------------------------------------------------------------------

package org.mps.evolution;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mps.EvolutionaryAlgorithm;
import org.mps.EvolutionaryAlgorithmException;
import org.mps.crossover.TwoPointCrossover;
import org.mps.mutation.GaussianMutation;
import org.mps.selection.TournamentSelection;

public class EvolutionaryAlgorithmTest {
    private EvolutionaryAlgorithm algorithm;

    @BeforeEach
    public void setUp() throws EvolutionaryAlgorithmException {
        algorithm = new EvolutionaryAlgorithm(
                new TournamentSelection(2),
                new GaussianMutation(0.5, 1.0),
                new TwoPointCrossover()
        );
    }


    // CONSTRUCTOR TESTS

    @Test
    @DisplayName("Constructor with valid parameters initializes correctly")
    public void constructor_validParameters_initializesCorrectly() throws EvolutionaryAlgorithmException {
        // Arrange
        TournamentSelection selectionOperator = new TournamentSelection(2);
        GaussianMutation mutationOperator = new GaussianMutation(0.5, 1.0);
        TwoPointCrossover crossoverOperator = new TwoPointCrossover();
        // Act
        EvolutionaryAlgorithm algorithm = new EvolutionaryAlgorithm(selectionOperator,mutationOperator,crossoverOperator);

        // Assert
        assertNotNull(algorithm);
    }

    @Test
    @DisplayName("Constructor with null crossoverOperator throws exception")
    public void constructor_nullCrossoverOperator_throwsException() throws EvolutionaryAlgorithmException {
        // Arrange
        TournamentSelection selectionOperator = new TournamentSelection(2);
        GaussianMutation mutationOperator = new GaussianMutation(0.5, 1.0);
        //  Act & Assert
        assertThrows(EvolutionaryAlgorithmException.class, () -> {new EvolutionaryAlgorithm(
            selectionOperator, 
            mutationOperator, 
            null);
        });
    }

    @Test
    @DisplayName("Constructor with null selectionOperator throws exception")
    public void constructor_nullSelectionOperator_throwsException() throws EvolutionaryAlgorithmException {
        // Arrange
        GaussianMutation mutationOperator = new GaussianMutation(0.5, 1.0);
        TwoPointCrossover crossoverOperator = new TwoPointCrossover();
        // Act & Assert
        assertThrows(EvolutionaryAlgorithmException.class, () -> { 
            new EvolutionaryAlgorithm(null, new GaussianMutation(0.5, 1.0), new TwoPointCrossover());
        });
    }

    @Test
    @DisplayName("Constructor with null mutationOperator throws exception")
    public void constructor_nullMutationOperator_throwsException() throws EvolutionaryAlgorithmException {
        // Arrangethrows EvolutionaryAlgorithmException 
        TournamentSelection selectionOperator = new TournamentSelection(2);
        TwoPointCrossover crossoverOperator = new TwoPointCrossover();
        // Act & Assert
        assertThrows(EvolutionaryAlgorithmException.class, () -> {
            new EvolutionaryAlgorithm(selectionOperator,null, crossoverOperator);
        });
    }


    // OPTIMIZATION TESTS

    @Test
    @DisplayName("Optimize with null population throws exception")
    public void optimize_nullPopulation_throwsException() throws EvolutionaryAlgorithmException {
        // Act & Assert
        assertThrows(EvolutionaryAlgorithmException.class, () -> algorithm.optimize(null));
    }

    @Test
    @DisplayName("Optimize with empty population throws exception")
    public void optimize_emptyPopulation_throwsException() throws EvolutionaryAlgorithmException {
        // Arrange
        int[][] population = new int[0][0];
        // Act & Assert
        assertThrows(EvolutionaryAlgorithmException.class, () -> algorithm.optimize(population));
    }

    @Test
    @DisplayName("Optimize with valid population executes correctly")
    public void optimize_validPopulation_returnNewPopulation() throws EvolutionaryAlgorithmException {
        // Arrange
        int[][] population = {{1, 2, 3}, {4, 5, 6}};
        // Act
        int[][] optimized = algorithm.optimize(population);
        // Assert
        assertNotNull(optimized);
        assertEquals(population.length, optimized.length);
    }

    // BETTER TESTS
    /*
    Better function is private and not tested directly.
    However, it is indirectly tested through the optimize method.
    */

  
    
}
