//--------------------------------------------------------------------------------
/* 
 * INTEGRANTES DEL GRUPO:
 * - Javier Toledo Delgado
 * - María Paulina Ordóñez Walkowiak
 */
//--------------------------------------------------------------------------------

package org.mps.selection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mps.EvolutionaryAlgorithmException;

public class TournamentSelectionTest {

    private TournamentSelection selection;

    @BeforeEach
    public void setUp() throws EvolutionaryAlgorithmException {
        selection = new TournamentSelection(3);
    }

    //Constructor
    @Test
    @DisplayName("Constructor with valid tournament size initializes correctly")
    public void constructor_validTournamentSize_initializesCorrectly() throws EvolutionaryAlgorithmException {
        //Act
        TournamentSelection s = new TournamentSelection(3);
        //Assert
        assertNotNull(s);
    }

    @Test
    @DisplayName("Constructor with invalid tournament size throws exception")
    public void constructor_invalidTournamentSize_throwsException() {
        //Act & Assert
        assertThrows(EvolutionaryAlgorithmException.class, () -> new TournamentSelection(0));
    }

    //Select
    @Test
    @DisplayName("Select null population throws exception")
    public void select_nullPopulation_throwsException() throws EvolutionaryAlgorithmException {
        //Act & Assert
        assertThrows(EvolutionaryAlgorithmException.class, () -> selection.select(null));
    }

    @Test
    @DisplayName("Select empty population throws exception")
    public void select_emptyPopulation_throwsException() throws EvolutionaryAlgorithmException {
        //Arrange
        int[] emptyPopulation = new int[]{};
        //Act & Assert
        assertThrows(EvolutionaryAlgorithmException.class, () -> selection.select(emptyPopulation));
    }

    @Test
    @DisplayName("Select population smaller than tournament size throws exception")
    public void select_populationSmallerThanTournamentSize_throwsException() throws EvolutionaryAlgorithmException {
        //Arrange
        int[] smallPopulation = {1, 2};
        //Act & Assert
        assertThrows(EvolutionaryAlgorithmException.class, () -> selection.select(smallPopulation));
    }

    @Test
    @DisplayName("Valid population returns correct selection")
    public void select_validPopulation_returnsCorrectSelection() throws EvolutionaryAlgorithmException {
        //Arrange
        int[] population = {5, 3, 8, 6};
        //Act
        int[] selected = selection.select(population);
        //Assert
        assertNotNull(selected);
        // Check that the selected individuals are from the original population
        for (int i = 0; i < selected.length; i++) {
            boolean found = false;
            for (int j = 0; j < population.length; j++) {
                if (selected[i] == population[j]) {
                    found = true;
                    break;
                }
            }
            assertTrue(found);
        }
        //revisar (contains)
    }
}