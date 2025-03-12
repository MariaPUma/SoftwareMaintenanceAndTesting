package clubdeportivo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GrupoTest {

    //Constructor
    @Test 
    @DisplayName("Constructor correctly instanciates a Grupo object")
    public void Grupo_ValidParameters_CorrectlyInstanciated() throws ClubException{
        //Arrange
        Grupo grupo = new Grupo("G1", "Futbol", 20, 10, 10.0);
        //Assert
        assertEquals("G1", grupo.getCodigo());
        assertEquals("Futbol", grupo.getActividad());
        assertEquals(20, grupo.getPlazas());
        assertEquals(10, grupo.getMatriculados());
        assertEquals(10.0, grupo.getTarifa());
    }

    @Test
    @DisplayName("Constructor throws ClubException if the nplazas is negative")
    public void Grupo_nPlazasNegative_ReturnsClubException() throws ClubException{
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            Grupo grupo = new Grupo("G1", "Futbol", -1, 10, 10.0);
        });
    }

    @Test
    @DisplayName("Constructor throws ClubException if the matriculados is negative")
    public void Grupo_matriculadosNegative_ReturnsClubException() throws ClubException{
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            Grupo grupo = new Grupo("G1", "Futbol", 20, -1, 10.0);
        });
    }

    @Test
    @DisplayName("Constructor throws ClubException if the tarifa is negative")
    public void Grupo_tarifaNegative_ReturnsClubException() throws ClubException{
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            Grupo grupo = new Grupo("G1", "Futbol", 20, 10, -10.0);
        });
    }

    @Test
    @DisplayName("Constructor throws ClubException if nplazas<matriculados")
    public void Grupo_plazasGreaterMatriculados_ReturnsClubException() throws ClubException{
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            Grupo grupo = new Grupo("G1", "Futbol", 10, 20, 10.0);
        });
    }

    //actualizarPlazas
    @Test
    @DisplayName("Updating nPlazas with valid parameters updates the field correctly")
    public void actualizarPlazas_validParameter_UpdatesNplazas() throws ClubException{
        //Arrange
        Grupo grupo = new Grupo("G1", "Futbol", 20, 10, 10.0);
        int n = 10;
        //Act
        grupo.actualizarPlazas(n);
        //Assert
        assertEquals(n, grupo.getPlazas());
    }
    
    @Test
    @DisplayName("Updating nPlazas with negative parameter throws ClubException, nPlazas remains unchanged")
    public void actualizarPlazas_NegativeParameter_ReturnsClubException() throws ClubException{
        // Arrange
        int nPlazas = 20;
        Grupo grupo = new Grupo("G1", "Futbol", nPlazas, 10, 10.0);
        int n = -5;
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            grupo.actualizarPlazas(n);
        });
        assertEquals(nPlazas, grupo.getPlazas());

    }

    @Test
    @DisplayName("Updating nPlazas with nPlazas < matriculados throws ClubException, nPlazas remains unchanged")
    public void actualizarPlazas_nPlazasLowerMatriculados_ReturnsClubException() throws ClubException{
        // Arrange
        int nPlazas = 10;
        Grupo grupo = new Grupo("G1", "Futbol", nPlazas, 10, 10.0);
        int n = 5;
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            grupo.actualizarPlazas(n);
        });
        assertEquals(nPlazas, grupo.getPlazas());

    }

    //matricular
    @Test
    @DisplayName("Test matricular correcto")
    public void matricular_ValidParameter_IncreasesMatriculados() throws ClubException{
        //Arrange
        Grupo grupo = new Grupo("G1", "Futbol", 20, 10, 10.0);
        int n = 5;
        int matriculadosPre = grupo.getMatriculados();
        //Act
        grupo.matricular(n);
        //Assert
        assertEquals(n + matriculadosPre, grupo.getMatriculados());
    }

    @Test
    @DisplayName("matricular with valid parameters increases matriculados correctly")
    public void matricularIncorrectoTest() throws ClubException{
        // Arrange
        Grupo grupo = new Grupo("G1", "Futbol", 20, 10, 10.0);
        int n = 15;
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            grupo.matricular(n);
        });
    }

    @Test
    @DisplayName("matricular with invalid parameters throws ClubException, matriculados remains unchanged")
    public void matricular_InvalidParameter_ReturnsClubException() throws ClubException{
        // Arrange
        Grupo grupo = new Grupo("G1", "Futbol", 20, 10, 10.0);
        int n = -1;
        
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            grupo.matricular(n);
        });
    }

    //toString
    @Test
    @DisplayName("toString returns the expected string")
    public void toString_ReturnExpectedFormat() throws ClubException{
        Grupo grupo = new Grupo("G1", "Futbol", 20, 10, 10.0);
        String expected = "(G1 - Futbol - 10.0 euros - P:20 - M:10)";
        assertEquals(expected, grupo.toString());
    }

    //equals
    @Test
    @DisplayName("equals returns true when comparing two equal objects") 
    public void equals_EqualObjects_ReturnsTrue() throws ClubException{
        //Arrange
        Grupo grupo1 = new Grupo("G1", "Futbol", 20, 10, 10.0);
        Grupo grupo2 = new Grupo("G1", "Futbol", 20, 10, 10.0);
        //Act
        boolean res = grupo1.equals(grupo2);
        //Assert
        assertTrue(res);
    } 

    @Test
    @DisplayName("equals returns false when comparing two objects with different code")
    public void equals_DifferentCode_ReturnsFalse() throws ClubException{
        //Arrange
        Grupo grupo1 = new Grupo("G1", "Futbol", 20, 10, 10.0);
        Grupo grupo2 = new Grupo("G2", "Futbol", 20, 10, 10.0);
        //Act
        boolean res = grupo1.equals(grupo2);
        //Assert
        assertFalse(res);
    }
    
    @Test
    @DisplayName("equals returns false when comparing two objects with different activity")
    public void equals_DifferentActivity_ReturnsFalse() throws ClubException{
        //Arrange
        Grupo grupo1 = new Grupo("G1", "Futbol", 20, 10, 10.0);
        Grupo grupo2 = new Grupo("G1", "Baloncesto", 20, 10, 10.0);
        //Act
        boolean res = grupo1.equals(grupo2);
        //Assert
        assertFalse(res);
    }

    @Test
    @DisplayName("equals returns false when comparing with an object of a different class")
    public void equals_ObjectOfDifferentClass_ReturnsFalse() throws ClubException{
        //Arrange
        Grupo grupo1 = new Grupo("G1", "Futbol", 20, 10, 10.0);
        Object obj = new ClubDeportivo("pruebaClub");
        //Act
        boolean res = grupo1.equals(obj);
        //Assert
        assertFalse(res);
    }

    @Test
    @DisplayName("equals returns false when comparing to null")
    public void equals_NullComparison_ReturnsFalse() throws ClubException {
        //Arrange
        Grupo grupo = new Grupo("G1", "Futbol", 20, 10, 10.0);
        //Act
        boolean res = grupo.equals(null);
        //Assert
        assertFalse(res);
    }
    
    //hashCode
    @Test
    @DisplayName("hashCode produces the same value for objects with identical fields")
    public void hashCodeTest() throws ClubException{
        //Arrange
        Grupo grupo1 = new Grupo("G1", "Futbol", 20, 10, 10.0);
        Grupo grupo2 = new Grupo("G1", "Futbol", 20, 10, 10.0);
        //Act
        int hash1 = grupo1.hashCode();
        int hash2 = grupo2.hashCode();
        //Assert
        assertEquals(hash1, hash2);
    }

    //getCodigo
    @Test
    @DisplayName("getCodigo returns the expected code")
    public void getCodigo_ReturnsCorrectCode() throws ClubException{
        //Arrange
        String c = "G1";
        Grupo grupo = new Grupo(c, "Futbol", 20, 10, 10.0);
        //Act
        String codigo = grupo.getCodigo();
        //Assert
        assertEquals(c, codigo);
    }

    @Test
    @DisplayName("getActividad returns the expected code")
    public void getActividad_ReturnsCorrectCode() throws ClubException{
        //Arrange
        String a = "Futbol";
        Grupo grupo = new Grupo("G1", a, 20, 10, 10.0);
        //Act
        String actividad = grupo.getActividad();
        //Assert
        assertEquals(a, actividad);
    }

    @Test
    @DisplayName("getTarifa returns the expected code")
    public void getTarifa_ReturnsCorrectCode() throws ClubException{
        //Arrange
        double t = 10.0;
        Grupo grupo = new Grupo("G1", "Futbol", 20, 10, t);
        //Act
        double tarifa = grupo.getTarifa();
        //Assert
        assertEquals(t, tarifa);
    }
}