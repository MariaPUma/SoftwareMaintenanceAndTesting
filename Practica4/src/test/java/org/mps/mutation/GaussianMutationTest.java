//--------------------------------------------------------------------------------
/* 
 * INTEGRANTES DEL GRUPO:
 * - Javier Toledo Delgado
 * - María Paulina Ordóñez Walkowiak
 */
//--------------------------------------------------------------------------------

package org.mps.mutation;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mps.EvolutionaryAlgorithmException;

public class GaussianMutationTest {

    private GaussianMutation mutation;

    @BeforeEach
    public void setUp() {
        mutation = new GaussianMutation();
    }

    @Test
    @DisplayName("Constructor initializes correctly without values")
    public void constructor_defaultValues_initializesCorrectly() {
        //Act
        GaussianMutation m = new GaussianMutation();
        //Assert
        assertNotNull(m);
    }

    @Test
    @DisplayName("Constructor with specific values does not throw exception")
    public void constructor_specificValues_initializesCorrectly() {
        //Act
        GaussianMutation m = new GaussianMutation(0.5, 1.0);
        //Assert
        assertNotNull(m);
    }

    @Test
    @DisplayName("Mutate with null individual throws exception")
    public void mutate_nullIndividual_throwsException() {
        // Act & Assert
        assertThrows(EvolutionaryAlgorithmException.class, () -> mutation.mutate(null));
    }

    @Test
    @DisplayName("Mutate with empty individual throws exception")
    public void mutate_emptyIndividual_throwsException() {
        // Act & Assert
        assertThrows(EvolutionaryAlgorithmException.class, () -> mutation.mutate(new int[]{}));
    }

    @Test
    @DisplayName("Mutate with valid individual and no standarDeviation returns original")
    public void mutate_validIndividualNoStandarDeviation_returnsOriginal() throws EvolutionaryAlgorithmException {
        //Arrange
        GaussianMutation m = new GaussianMutation(1.0, 0.0);
        int[] individual = {1, 2, 3};
        //Act
        int[] mutated = m.mutate(individual);
        //Assert
        assertArrayEquals(individual, mutated);
    }

    @Test
    @DisplayName("Mutate with valid individual and mutation returns mutated array")
    public void mutate_validIndividualWithMutation_returnsMutatedArray() throws EvolutionaryAlgorithmException {
        //Arrange
        GaussianMutation m = new GaussianMutation(1.0, 1.0);
        int[] individual = {1, 2, 3};
        //Act
        int[] mutated = m.mutate(individual);
        //Assert
        assertEquals(individual.length, mutated.length);
    }
}