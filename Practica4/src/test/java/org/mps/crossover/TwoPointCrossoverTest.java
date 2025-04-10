//--------------------------------------------------------------------------------
/* 
 * INTEGRANTES DEL GRUPO:
 * - Javier Toledo Delgado
 * - María Paulina Ordóñez Walkowiak
 */
//--------------------------------------------------------------------------------

package org.mps.crossover;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mps.EvolutionaryAlgorithmException;

public class TwoPointCrossoverTest {

    private TwoPointCrossover crossover;

    @BeforeEach
    public void setUp() {
        // Arrange
        crossover = new TwoPointCrossover();
    }

    // CONSTRUCTOR TESTS

    @Test
    @DisplayName("Default constructor initializes correctly")
    public void constructor_default_initializesCorrectly() {
        // Act
        TwoPointCrossover c = new TwoPointCrossover();
        // Assert
        assertNotNull(c);
    }

    // CROSSOVER TESTS

    @Test
    @DisplayName("Crossover with null parent1 throws exception")
    public void crossover_nullParent1_throwsException() {
        // Arrange
        int[] parent2 = {1, 2, 3};
        // Act & Assert
        assertThrows(EvolutionaryAlgorithmException.class, () -> crossover.crossover(null, parent2));
    }

    @Test
    @DisplayName("Crossover with null parent2 throws exception")
    public void crossover_nullParent2_throwsException() {
        // Arrange
        int[] parent1 = {1, 2, 3};
        // Act & Assert
        assertThrows(EvolutionaryAlgorithmException.class, () -> crossover.crossover(parent1, null));
    }

    @Test
    @DisplayName("Crossover with parents of different lengths throws exception")
    public void crossover_parentsDifferentLengths_throwsException() {
        // Arrange
        int[] parent1 = {1, 2, 3};
        int[] parent2 = {4, 5};
        // Act & Assert
        assertThrows(EvolutionaryAlgorithmException.class, () -> crossover.crossover(parent1, parent2));
    }

    @Test
    @DisplayName("Crossover with valid parents returns offspring")
    public void crossover_validParents_returnsOffspring() throws EvolutionaryAlgorithmException {
        // Arrange
        int[] parent1 = {1, 2, 3, 4};
        int[] parent2 = {5, 6, 7, 8};
        // Act
        int[][] offspring = crossover.crossover(parent1, parent2);
        // Assert
        assertNotNull(offspring);
        assertEquals(parent1.length, offspring[0].length);
        assertEquals(parent2.length, offspring[1].length);
    }

    @Test
    @DisplayName("Crossover with parents of length 1 throws exception")
    public void crossover_parentsLengthOne_throwsException() {
        // Arrange
        int[] parent1 = {1};
        int[] parent2 = {2};
        // Act & Assert
        assertThrows(EvolutionaryAlgorithmException.class, () -> crossover.crossover(parent1, parent2));
    }
}
