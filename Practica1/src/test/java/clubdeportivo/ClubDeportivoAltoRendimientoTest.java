//--------------------------------------------------------------------------------
/* 
 * INTEGRANTES DEL GRUPO:
 * - Javier Toledo Delgado
 * - María Paulina Ordóñez Walkowiak
 */
//--------------------------------------------------------------------------------


package clubdeportivo;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClubDeportivoAltoRendimientoTest {
    
    //Constructor ClubDeportivoAltoRendimiento(nombre, maximo, incremento)
    @Test
    @DisplayName("Constructor correctly instanciates a ClubDeportivoAltoRendimiento object")
    public void ClubDeportivoAltoRendimiento_ValidParameters_CorrectlyInstanciated() throws Exception{
        //Arrange
        String nombre = "Club";
        int maximo = 20;
        double incremento = 10.0;
        //Act
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento(nombre, maximo, incremento);
        //Assert
        //El campo nombre del club deportivo se comprueba directamente en club deportivo

        Field maximoField = ClubDeportivoAltoRendimiento.class.getDeclaredField("maximoPersonasGrupo");
        maximoField.setAccessible(true);
        assertEquals(maximo, maximoField.get(club));

        Field incrementoField = ClubDeportivoAltoRendimiento.class.getDeclaredField("incremento");
        incrementoField.setAccessible(true);
        assertEquals(incremento, incrementoField.get(club));
    }
    
    @Test
    @DisplayName("Constructor throws ClubException if the maximo is negative")
    public void ClubDeportivoAltoRendimiento_MaximoNegative_CorrectlyInstanciated() throws Exception{
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club", -1, 10.0);
        });
    }
    
    @Test
    @DisplayName("Constructor throws ClubException if the incremento is negative")
    public void ClubDeportivoAltoRendimiento_IncrementoNegative_CorrectlyInstanciated() throws Exception{
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club", 20, -10.0);
        });
    }

    //Constructor ClubDeportivoAltoRendimiento(nombre, tam, maximo, incremento)
    @Test
    @DisplayName("Constructor correctly instanciates a ClubDeportivoAltoRendimiento object with tam")
    public void constructor_ValidParametersWithTam_SetsFieldsCorrectly() throws Exception{
        //Arrange
        String nombre = "Club";
        int tam = 10;
        int maximo = 20;
        double incremento = 10.0;
        //Act
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento(nombre, tam, maximo, incremento);
        //Assert
        //El campo nombre del club deportivo se comprueba directamente en club deportivo
        //El campo tam del club deportivo se comprueba directamente en club deportivo
        Field maximoField = ClubDeportivoAltoRendimiento.class.getDeclaredField("maximoPersonasGrupo");
        maximoField.setAccessible(true);
        assertEquals(maximo, maximoField.get(club));

        Field incrementoField = ClubDeportivoAltoRendimiento.class.getDeclaredField("incremento");
        incrementoField.setAccessible(true);
        assertEquals(incremento, incrementoField.get(club));
    }

    @Test
    @DisplayName("Constructor throws ClubException when tam is negative")
    public void constructor_NegativeTam_ThrowsClubException() throws Exception {
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club", -1, 20, 10.0);
        });
    }

    @Test
    @DisplayName("Constructor throws ClubException when both tam and maximo are negative")
    public void constructor_NegativeTamAndMaximo_ThrowsClubException() throws Exception {
    // Act & Assert
        assertThrows(ClubException.class, () -> {
            ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club", 5, -1, 10.0);
        });
    }

    @Test
    @DisplayName("Constructor throws ClubException when tam and incremento are negative")
    public void constructor_NegativeTamAndIncremento_ThrowsClubException() throws Exception {
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club", 5, 20, -10.0);
        });
    }

    //añadirActividad
    @Test
    @DisplayName("anyadir actividad adds a new group and updates the group count")
    public void anyadirActividad_ValidInput_IncrementsGroupCount() throws Exception{
        //Arrange
        String nombre = "Club";
        int maximo = 20;
        double incremento = 10.0;
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento(nombre, maximo, incremento);
        String[] datos = {"G1", "Futbol", "10", "10", "10.0"};

        //obtener longitud grupos antes 
        Field ngruposField = ClubDeportivo.class.getDeclaredField("ngrupos");
        ngruposField.setAccessible(true);
        int longitudPre = (int) ngruposField.get(club);

        //Act
        club.anyadirActividad(datos);
        //Assert

        int longitudPost = (int) ngruposField.get(club);

        assertEquals(longitudPre + 1, longitudPost);

        Field nombreField = ClubDeportivo.class.getDeclaredField("nombre");
        nombreField.setAccessible(true);
        assertEquals(nombre, nombreField.get(club));
    }

    @Test
    @DisplayName("anyadirActividad throws ClubException when input data has fewer than 5 fields")
    public void anyadirActividad_LessThanRequiredFields_ThrowsClubException() throws Exception{
        //Arrange
        String nombre = "Club";
        int maximo = 20;
        double incremento = 10.0;
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento(nombre, maximo, incremento);
        String[] datos = {"G1", "Futbol", "10", "10"};

        //Act & Assert
        assertThrows(ClubException.class, () -> {
            club.anyadirActividad(datos);
        });
    }

    @Test
    @DisplayName("anyadirActividad caps the number of places to the maximum allowed when exceeding limit")
    public void anyadirActividad_PlacesExceedingMax_CapsToMaximum() throws Exception{
        //Arrange
        String nombre = "Club";
        int maximo = 20;
        double incremento = 10.0;
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento(nombre, maximo, incremento);
        String[] datos = {"G1", "Futbol", "30", "10", "10.0"};


        //Act
        club.anyadirActividad(datos);
        //Assert

        // Acceder al array de grupos con reflexión
        Field gruposField = ClubDeportivo.class.getDeclaredField("grupos");
        gruposField.setAccessible(true);
        Grupo[] grupos = (Grupo[]) gruposField.get(club);

        // Acceder al contador de grupos (ngrupos) para saber cuántos hay
        Field ngruposField = ClubDeportivo.class.getDeclaredField("ngrupos");
        ngruposField.setAccessible(true);
        int ngrupos = (int) ngruposField.get(club);

        // Obtener el último grupo añadido (en la posición ngrupos-1)
        Grupo grupoAñadido = grupos[ngrupos - 1];

        // Acceder a las plazas del grupo con reflexión (si no tiene getter)
        Field plazasField = Grupo.class.getDeclaredField("nplazas");
        plazasField.setAccessible(true);
        int plazas = (int) plazasField.get(grupoAñadido);

        // Assert - Verificar que las plazas son igual al máximo permitido (20)
        assertEquals(maximo, plazas);
    }

    @Test
    @DisplayName("anyadirActividad throws ClubException when a numeric input is invalid")
    public void addActivity_InvalidNumericField_ThrowsClubException() throws Exception{
        //Arrange
        String nombre = "Club";
        int maximo = 20;
        double incremento = 10.0;
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento(nombre, maximo, incremento);
        String[] datos = {"G1", "Futbol", "10", "10.2", "10"};

        //Act & Assert
        assertThrows(ClubException.class, () -> {
            club.anyadirActividad(datos);
        });
    }

    @Test
    @DisplayName("anyadirActividad throws ClubException when datos is null")
    public void anyadirActividad_NullData_ThrowsClubException() throws Exception{
        //Arrange
        String nombre = "Club";
        int maximo = 20;
        double incremento = 10.0;
        String datos[] = null;
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento(nombre, maximo, incremento);

        //Act & Assert
        assertThrows(ClubException.class, () -> {
            club.anyadirActividad(datos);
        });
    }

    @Test
    @DisplayName("anyadirActividad throws ClubException when datos is empty")
    public void anyadirActividad_EmptyData_ThrowsClubException() throws Exception{
        //Arrange
        String nombre = "Club";
        int maximo = 20;
        double incremento = 10.0;
        String[] datos = {};
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento(nombre, maximo, incremento);

        //Act & Assert
        assertThrows(ClubException.class, () -> {
            club.anyadirActividad(datos);
        });
    }

    //ingresos
    @Test
    @DisplayName("ingresos returns the correct value")
    public void ingresos_ReturnsCorrectValue() throws Exception{
        //Arrange
        String nombre = "Club";
        int maximo = 20;
        double incremento = 10.0;
        ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento(nombre, maximo, incremento);
        String[] datos = {"G1", "Futbol", "10", "10", "10.0"};
        club.anyadirActividad(datos);

        //Act
        double ingresos = club.ingresos();
        //Assert
        assertEquals(110.0, ingresos);
    }

    //Minitest para probar ClubException() cuando no se pasa mensaje
    @Test
    @DisplayName("ClubException without a message is thrown correctly")
    public void clubException_NoMessage_ThrowsClubException() throws Exception{
        assertThrows(ClubException.class, () -> {
            throw new ClubException();
        });
    }
}
