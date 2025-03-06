package clubdeportivo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GrupoTest {

    @Test 
    @DisplayName("Test constructor correcto")
    public void constructorTest() throws ClubException{
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
    @DisplayName("Test constructor numero de plazas negativo")
    public void constructorCodigoNuloTest() throws ClubException{
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            Grupo grupo = new Grupo("G1", "Futbol", -1, 10, 10.0);
        });
    }

    @Test
    @DisplayName("Test constructor numero de matriculados negativo")
    public void constructorMatriculadosNegativoTest() throws ClubException{
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            Grupo grupo = new Grupo("G1", "Futbol", 20, -1, 10.0);
        });
    }

    @Test
    @DisplayName("Test constructor tarifa negativa")
    public void constructorTarifaNegativaTest() throws ClubException{
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            Grupo grupo = new Grupo("G1", "Futbol", 20, 10, -10.0);
        });
    }

    @Test
    @DisplayName("Test constructor plazas menor que matriculados")
    public void constructorPlazasMenorMatriculadosTest() throws ClubException{
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            Grupo grupo = new Grupo("G1", "Futbol", 10, 20, 10.0);
        });
    }

    @Test
    @DisplayName("Test actualizar plazas")
    public void actualizarPlazasTest() throws ClubException{
        //Arrange
        Grupo grupo = new Grupo("G1", "Futbol", 20, 10, 10.0);
        int n = 10;
        //Act
        grupo.actualizarPlazas(n);
        //Assert
        assertEquals(n, grupo.getPlazas());
    }
    
    @Test
    @DisplayName("Test actualizar plazas negativo")
    public void actualizarPlazasNegativoTest() throws ClubException{
        // Arrange
        Grupo grupo = new Grupo("G1", "Futbol", 20, 10, 10.0);
        int n = -5;
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            grupo.actualizarPlazas(n);
        });
    }

    @Test
    @DisplayName("Test actualizar plazas menor que matriculados")
    public void actualizarPlazasMenorMatriculadosTest() throws ClubException{
        // Arrange
        Grupo grupo = new Grupo("G1", "Futbol", 10, 10, 10.0);
        int n = 5;
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            grupo.actualizarPlazas(n);
        });
    }
    
    @Test
    @DisplayName("Test matricular correcto")
    public void matricularTest() throws ClubException{
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
    @DisplayName("Test matricular incorrecto")
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
    @DisplayName("Test matricular con n<=0 ")
    public void matricularNIncorrecto() throws ClubException{
        // Arrange
        Grupo grupo = new Grupo("G1", "Futbol", 20, 10, 10.0);
        int n = -1;
        
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            grupo.matricular(n);
        });
    }

    @Test
    @DisplayName("Test toString")
    public void toStringTest() throws ClubException{
        Grupo grupo = new Grupo("G1", "Futbol", 20, 10, 10.0);
        String expected = "(G1 - Futbol - 10.0 euros - P:20 - M:10)";
        assertEquals(expected, grupo.toString());
    }

    @Test
    @DisplayName("Test equals true")
    public void equalsTest() throws ClubException{
        //Arrange
        Grupo grupo1 = new Grupo("G1", "Futbol", 20, 10, 10.0);
        Grupo grupo2 = new Grupo("G1", "Futbol", 20, 10, 10.0);
        //Act
        boolean res = grupo1.equals(grupo2);
        //Assert
        assertTrue(res);
    } 

    @Test
    @DisplayName("Test equals false codigo")
    public void equalsFalseCodeTest() throws ClubException{
        //Arrange
        Grupo grupo1 = new Grupo("G1", "Futbol", 20, 10, 10.0);
        Grupo grupo2 = new Grupo("G2", "Futbol", 20, 10, 10.0);
        //Act
        boolean res = grupo1.equals(grupo2);
        //Assert
        assertFalse(res);
    }
    
    @Test
    @DisplayName("Test equals false actividad")
    public void equalsFalseActivityTest() throws ClubException{
        //Arrange
        Grupo grupo1 = new Grupo("G1", "Futbol", 20, 10, 10.0);
        Grupo grupo2 = new Grupo("G1", "Baloncesto", 20, 10, 10.0);
        //Act
        boolean res = grupo1.equals(grupo2);
        //Assert
        assertFalse(res);
    }

    @Test
    @DisplayName("Test equals false instance")
    public void equalsFalseInstanceTest() throws ClubException{
        //Arrange
        Grupo grupo1 = new Grupo("G1", "Futbol", 20, 10, 10.0);
        Object obj = new ClubDeportivo("pruebaClub");
        //Act
        boolean res = grupo1.equals(obj);
        //Assert
        assertFalse(res);
    }
    
    @Test
    @DisplayName("Test hascode iguales")
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

    @Test
    @DisplayName("Test getcodigo")
    public void getCodigoTest() throws ClubException{
        //Arrange
        String c = "G1";
        Grupo grupo = new Grupo(c, "Futbol", 20, 10, 10.0);
        //Act
        String codigo = grupo.getCodigo();
        //Assert
        assertEquals(c, codigo);
    }

    @Test
    @DisplayName("Test getActividad")
    public void getActividadTest() throws ClubException{
        //Arrange
        String a = "Futbol";
        Grupo grupo = new Grupo("G1", a, 20, 10, 10.0);
        //Act
        String actividad = grupo.getActividad();
        //Assert
        assertEquals(a, actividad);
    }

    @Test
    @DisplayName("Test getTarifa")
    public void getTarifaTest() throws ClubException{
        //Arrange
        double t = 10.0;
        Grupo grupo = new Grupo("G1", "Futbol", 20, 10, t);
        //Act
        double tarifa = grupo.getTarifa();
        //Assert
        assertEquals(t, tarifa);
    }
}