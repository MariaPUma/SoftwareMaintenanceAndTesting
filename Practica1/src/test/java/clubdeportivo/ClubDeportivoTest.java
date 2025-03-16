//--------------------------------------------------------------------------------
/* 
 * INTEGRANTES DEL GRUPO:
 * - Javier Toledo Delgado
 * - María Paulina Ordóñez Walkowiak
 */
//--------------------------------------------------------------------------------


package clubdeportivo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClubDeportivoTest {

    //--------------------------------------------------------------------------------------------------
    // Test constructor
    @Test
    @DisplayName("Constructor correctly instanciates a ClubDeportivo object")
    public void constructor_ValidParameters_CorrectlyInstanciated() throws Exception{
        //Arrange
        int tam = 10;
        String nombre = "Club";

        //Act
        ClubDeportivo club = new ClubDeportivo(nombre, tam);

        
        Field nombreField = ClubDeportivo.class.getDeclaredField("nombre");
        Field gruposField = ClubDeportivo.class.getDeclaredField("grupos");

        nombreField.setAccessible(true);
        gruposField.setAccessible(true);

        Grupo[] grupos = (Grupo[]) gruposField.get(club);
        //Assert
        assertEquals(nombre, nombreField.get(club));
        assertEquals(tam, grupos.length);
    }

    @Test
    @DisplayName("Constructor throws ClubException when tam is negative")
    public void constructor_NegativeTam_ThrowsClubException() throws Exception{
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            ClubDeportivo club = new ClubDeportivo("Club", -1);
        });
    }

    @Test
    @DisplayName("Constructor throws ClubException when tam is zero")
    public void constructor_ZeroTam_ThrowsClubException() throws Exception{
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            ClubDeportivo club = new ClubDeportivo("Club", 0);
        });
    }

    @Test
    @DisplayName("Constructor throws ClubException when name is null")
    public void constructor_NullName_ThrowsClubException() throws Exception{
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            ClubDeportivo club = new ClubDeportivo(null, 10);
        });
    }

    @Test
    @DisplayName("Constructor throws ClubException when name is empty")
    public void constructor_EmptyName_ThrowsClubException() throws Exception{
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            ClubDeportivo club = new ClubDeportivo("", 10);
        });
    }

    //--------------------------------------------------------------------------------------------------
    // Test buscar

    @Test 
    @DisplayName("buscar returns the correct index when the group exists in the club")
    public void buscar_ExistingGroup_ReturnsCorrectIndex() throws Exception{
        //Arrange
        ClubDeportivo club = new ClubDeportivo("nombre", 10);
        Grupo g = new Grupo("nombre", "actividad", 10, 5, 10.0);
        club.anyadirActividad(g);

        Method buscar= ClubDeportivo.class.getDeclaredMethod("buscar", Grupo.class);
        buscar.setAccessible(true);

        //Act
        int pos = (int) buscar.invoke(club, g);

        //Assert
        assertEquals(0, pos);

    }

    @Test 
    @DisplayName("buscar returns -1 when the group does not exist in the club")
    public void buscar_NonExistingGroup_ReturnsNegativeOne() throws Exception{
        //Arrange
        ClubDeportivo club = new ClubDeportivo("nombre", 10);
        Grupo g = new Grupo("nombre", "actividad", 10, 5, 10.0);
        
        Method buscar= ClubDeportivo.class.getDeclaredMethod("buscar", Grupo.class);
        buscar.setAccessible(true);

        //Act
        int pos = (int) buscar.invoke(club, g);

        //Assert
        assertEquals(-1, pos);
    }

    @Test 
    @DisplayName("buscar returns -1 when the group is null")
    public void buscar_NullGroup_ReturnsNegativeOne() throws Exception{
        //Arrange
        ClubDeportivo club = new ClubDeportivo("nombre", 10);
        Grupo g = null;
        
        Method buscar= ClubDeportivo.class.getDeclaredMethod("buscar", Grupo.class);
        buscar.setAccessible(true);

        //Act
        int pos = (int) buscar.invoke(club, g);

        //Assert
        assertEquals(-1, pos);
    }

    //--------------------------------------------------------------------------------------------------
    // Test anyadirActividad

    @Test
    @DisplayName("Test anyadirActividad adds the activity successfully with valid input")
    public void anyadirActividad_ValidInput_AddsActivitySuccessfully() throws Exception{
        //Arrange
        String [] datos = {"nombre", "actividad", "10", "5", "10.0"};
        ClubDeportivo club = new ClubDeportivo("Club", 10);

        //Act
        club.anyadirActividad(datos);
    }

    @Test 
    @DisplayName("anyadirActividad throws ClubException for invalid numeric value in input")
    public void anyadirActividad_InvalidNumericValue_ThrowsClubException() throws Exception{
        //Arrange
        String [] datos = {"nombre", "actividad", "error", "5", "10.0"};
        ClubDeportivo club = new ClubDeportivo("Club", 10);

        //Act & Assert
        assertThrows(ClubException.class, () -> {
            club.anyadirActividad(datos);
        });
    }

    @Test
    @DisplayName("anyadirActividad throws ClubException when enrolled value is not a valid number")
    public void anyadirActividad_InvalidMatriculadosValue_ThrowsClubException() throws Exception{
        //Arrange
        String [] datos = {"nombre", "actividad", "10", "error", "10.0"};
        ClubDeportivo club = new ClubDeportivo("Club", 10);

        //Act & Assert
        assertThrows(ClubException.class, () -> {
            club.anyadirActividad(datos);
        });
    }

    @Test
    @DisplayName("anyadirActividad throws ClubException when tarifa value is not a valid number")
    public void anyadirActividad_InvalidTarifaValue_ThrowsClubException() throws Exception{
        //Arrange
        String [] datos = {"nombre", "actividad", "10", "5", "error"};
        ClubDeportivo club = new ClubDeportivo("Club", 10);

        //Act & Assert
        assertThrows(ClubException.class, () -> {
            club.anyadirActividad(datos);
        });
    }

    @Test
    @DisplayName("anyadirActividad throws ClubException when the datos is null")
    public void anyadirActividad_NullDatos_ThrowsClubException() throws Exception{
        //Arrange
        String [] datos = null;
        ClubDeportivo club = new ClubDeportivo("Club", 10);

        //Act & Assert
        assertThrows(ClubException.class, () -> {
            club.anyadirActividad(datos);
        });
    }

    @Test
    @DisplayName("anyadirActividad throws ClubException when the datos is empty")
    public void anyadirActividad_EmptyDatos_ThrowsClubException() throws Exception{
        //Arrange
        String [] datos = {};
        ClubDeportivo club = new ClubDeportivo("Club", 10);

        //Act & Assert
        assertThrows(ClubException.class, () -> {
            club.anyadirActividad(datos);
        });
    }

    //--------------------------------------------------------------------------------------------------
    // Test anyadirActividad con Grupo

    @Test
    @DisplayName("anyadirActividad adds a new group successfully when the group does not exist")
    public void anyadirActividad_NonExistingGroup_AddsSuccessfully() throws Exception{
        //Arrange
        Grupo g = new Grupo("nombre", "actividad", 10, 5, 10.0);
        ClubDeportivo club = new ClubDeportivo("Club", 10);

        //obtener longitud grupos antes
        Field gruposField = ClubDeportivo.class.getDeclaredField("ngrupos");
        gruposField.setAccessible(true);
        int longitudPre = (int) gruposField.get(club);
        
        //Act
        club.anyadirActividad(g);

        int longitudPost = (int) gruposField.get(club);

        //Assert
        assertEquals(longitudPre + 1, longitudPost);
    }

    @Test
    @DisplayName("anyadirActividad updates the existing group correctly without increasing group count")
    public void anyadirActividad_ExistingGroup_UpdatesGroupCorrectly() throws Exception{
        //Arrange
        Grupo g = new Grupo("nombre", "actividad", 10, 5, 10.0);
        Grupo g2 = new Grupo("nombre", "actividad", 15, 5, 10.0);
        ClubDeportivo club = new ClubDeportivo("Club", 10);

        //obtener longitud grupos antes
        Field ngruposField = ClubDeportivo.class.getDeclaredField("ngrupos");
        ngruposField.setAccessible(true);

        Field gruposField = ClubDeportivo.class.getDeclaredField("grupos");
        gruposField.setAccessible(true);


        club.anyadirActividad(g);
        int longitudPre = (int) ngruposField.get(club);
        
        //Act

        club.anyadirActividad(g2);

        int longitudPost = (int) ngruposField.get(club);

        Grupo[] grupos = (Grupo[]) gruposField.get(club);
        Grupo actual = grupos[0];

        //Assert
        assertEquals(longitudPre, longitudPost);
        assertEquals(g2.getPlazas(), actual.getPlazas());

    }


    @Test
    @DisplayName("anyadirActividad throws ClubException when the group is null")
    public void anyadirActividad_NullGroup_ThrowsClubException() throws Exception{
        //Arrange
        Grupo g = null;
        ClubDeportivo club = new ClubDeportivo("Club", 10);

        //Act & Assert
        assertThrows(ClubException.class, () -> {
            club.anyadirActividad(g);
        });
    }

    @Test
    @DisplayName("anyadirActividad throw ClubException when the club is full")
    public void anyadirActividad_ClubFull_ReturnsMessage() throws Exception{
        //Arrange
        Grupo g = new Grupo("nombre", "actividad", 10, 5, 10.0);
        Grupo g2 = new Grupo("nombre2", "actividad", 15, 5, 10.0);
        ClubDeportivo club = new ClubDeportivo("Club", 1);

        //Act
        club.anyadirActividad(g);

        //Act & Assert
        assertThrows(ClubException.class, () -> {
            club.anyadirActividad(g2);
        });
    }

    //--------------------------------------------------------------------------------------------------
    // Test plazasLibres

    @Test
    @DisplayName("plazasLibres returns the number of available spaces for an existing activity")
    public void plazas_ExistingActivity_ReturnsAvailableSpaces() throws Exception{
        //Arrange
        ClubDeportivo club = new ClubDeportivo("Club", 10);
        Grupo g = new Grupo("nombre", "actividad", 10, 5, 10.0);
        club.anyadirActividad(g);

        //Act
        int plazas = club.plazasLibres("actividad");

        //Assert
        assertEquals(5, plazas);
    }

    @Test 
    @DisplayName("plazasLibres returns 0 when the activity does not exist")
    public void plazas_NonExistingActivity_ReturnsZero() throws ClubException{
        //Arrange
        ClubDeportivo club = new ClubDeportivo("Club", 10);
        Grupo g = new Grupo("nombre", "actividad", 10, 5, 10.0);
        club.anyadirActividad(g);

        //Act
        int plazas = club.plazasLibres("noExiste");

        //Assert
        assertEquals(0, plazas);
    }

    @Test
    @DisplayName("plazasLibres throws ClubException when the activity is null")
    public void plazas_NullActivity_ThrowsClubException() throws Exception{
        //Arrange
        ClubDeportivo club = new ClubDeportivo("Club", 10);  

        //Act & Assert
        assertThrows(ClubException.class, () -> {
            club.plazasLibres(null);
        });
    }

    //--------------------------------------------------------------------------------------------------
    // Test matricular

    @Test
    @DisplayName("matricular adds the correct number of participants when sufficient spaces are available")
    public void matricular_ValidInput_AddsCorrectly() throws Exception{
        //Arrange
        String nombreActividad = "actividad";
        ClubDeportivo club = new ClubDeportivo("Club", 10);
        Grupo g = new Grupo("nombre", nombreActividad, 10, 5, 10.0);
        club.anyadirActividad(g);

        //Act
        club.matricular(nombreActividad, 5);

        //Assert
        assertEquals(10, g.getMatriculados());

    }

    @Test
    @DisplayName("matricular throws ClubException when required spaces exceed available spaces")
    public void matricular_InsufficientSpaces_ThrowsClubException() throws Exception{
        //Arrange
        String nombreActividad = "actividad";
        ClubDeportivo club = new ClubDeportivo("Club", 10);
        Grupo g = new Grupo("nombre", nombreActividad, 10, 5, 10.0);
        Grupo g2= new Grupo("nombre2", nombreActividad, 15, 5, 10.0);
        club.anyadirActividad(g);
        club.anyadirActividad(g2);

        //Act & Assert
        assertThrows(ClubException.class, () -> {
            club.matricular(nombreActividad, 16);
        });
    }

    @Test
    @DisplayName("matricular adds the correct number of participants when npersonas < plazasGrupo")
    public void matricular_LessThanPlazasGrupo_AddsCorrectly() throws Exception{
        //Arrange
        String nombreActividad = "actividad";
        ClubDeportivo club = new ClubDeportivo("Club", 10);
        Grupo g = new Grupo("nombre", nombreActividad, 10, 0, 10.0);
        club.anyadirActividad(g);

        //Act
        club.matricular(nombreActividad, 3);

        //Assert
        assertEquals(3, g.getMatriculados());
    }

    @Test
    @DisplayName("matricular does nothing if actividad is not found")
    public void matricular_NonExistingActivity_DoesNothing() throws Exception{
        //Arrange
        String nombreActividad = "actividad";
        ClubDeportivo club = new ClubDeportivo("Club", 10);
        Grupo g = new Grupo("nombre", nombreActividad, 10, 10, 10.0);
        club.anyadirActividad(g);

        //Act
        club.matricular("noExiste", 3);

        //Assert
        assertEquals(10, g.getMatriculados());
    }

    @Test
    @DisplayName("matricular does nothing if npersonas is 0")
    public void matricular_ZeroNPersonas_DoesNothing() throws Exception{
        //Arrange
        String nombreActividad = "actividad";
        ClubDeportivo club = new ClubDeportivo("Club", 10);
        Grupo g = new Grupo("nombre", nombreActividad, 10, 10, 10.0);
        club.anyadirActividad(g);

        //Act
        club.matricular(nombreActividad, 0);

        //Assert
        assertEquals(10, g.getMatriculados());
    }

    @Test
    @DisplayName("matricular throws ClubException when actividad is null")
    public void matricular_NullActividad_ThrowsClubException() throws Exception{
        //Arrange
        ClubDeportivo club = new ClubDeportivo("Club", 10);

        //Act & Assert
        assertThrows(ClubException.class, () -> {
            club.matricular(null, 5);
        });
    }

    @Test
    @DisplayName("matricular throws ClubException when npersonas is negative")
    public void matricular_NegativeNPersonas_ThrowsClubException() throws Exception{
        //Arrange
        ClubDeportivo club = new ClubDeportivo("Club", 10);

        //Act & Assert
        assertThrows(ClubException.class, () -> {
            club.matricular("actividad", -5);
        });
    }


    //--------------------------------------------------------------------------------------------------
    // Test ingresos

    @Test
    @DisplayName("ingresos returns the correct total income when activities are present")
    public void ingresos_ValidInput_ReturnsCorrectTotalIncome() throws Exception{
        //Arrange
        ClubDeportivo club = new ClubDeportivo("Club", 10);
        Grupo g = new Grupo("nombre", "actividad", 10, 5, 10.0);
        club.anyadirActividad(g);

        //Act
        double ingresos = club.ingresos();

        //Assert
        assertEquals(50.0, ingresos);
    }

    //--------------------------------------------------------------------------------------------------
    // Test toString

    @Test
    @DisplayName("toString returns the expected string")
    public void toString_ReturnsCorrectString() throws Exception{
        //Arrange
        ClubDeportivo club = new ClubDeportivo("Club", 10);
        Grupo g = new Grupo("nombre", "actividad", 10, 5, 10.0);
        club.anyadirActividad(g);

        //Act
        String s = club.toString();

        //Assert
        assertEquals("Club --> [ (nombre - actividad - 10.0 euros - P:10 - M:5) ]", s);
    }



}