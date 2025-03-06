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
    @DisplayName("Test constructor correcto")
    public void constructorTest() throws Exception{
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
    @DisplayName("Test constructor con tamaño negativo")
    public void constructorTamNegativoTest() throws Exception{
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            ClubDeportivo club = new ClubDeportivo("Club", -1);
        });
    }

    //--------------------------------------------------------------------------------------------------
    // Test buscar

    @Test 
    @DisplayName("Test busqueda correcta con grupo existente en el grupo")
    public void buscarTest() throws Exception{
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
    @DisplayName("Test busqueda correcta con grupo no existente en el club")
    public void buscarTest2() throws Exception{
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
    @DisplayName("Test busqueda incorrecta con grupo null")
    public void buscarIncorrectaTest() throws Exception{
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
    // Test anyadirActividad con String[]

    @Test
    @DisplayName("Test anyadirActividad correcto")
    public void anyadirActividadTest() throws Exception{
        //Arrange
        String [] datos = {"nombre", "actividad", "10", "5", "10.0"};
        ClubDeportivo club = new ClubDeportivo("Club", 10);

        //Act
        club.anyadirActividad(datos);
    }

    @Test 
    @DisplayName("Test anyadirActividad con valor numérico incorrecto")
    public void anyadirActividadIncorrectoTest() throws Exception{
        //Arrange
        String [] datos = {"nombre", "actividad", "error", "5", "10.0"};
        ClubDeportivo club = new ClubDeportivo("Club", 10);

        //Act & Assert
        assertThrows(ClubException.class, () -> {
            club.anyadirActividad(datos);
        });
    }

    @Test
    @DisplayName("Test anyadirActividad incorrecto con matriculado no double")
    public void anyadirActividadIncorrectoTest2() throws Exception{
        //Arrange
        String [] datos = {"nombre", "actividad", "10", "error", "10.0"};
        ClubDeportivo club = new ClubDeportivo("Club", 10);

        //Act & Assert
        assertThrows(ClubException.class, () -> {
            club.anyadirActividad(datos);
        });
    }

    @Test
    @DisplayName("Test anyadirActividad incorrecto con tarifa no double")
    public void anyadirActividadIncorrectoTest3() throws Exception{
        //Arrange
        String [] datos = {"nombre", "actividad", "10", "5", "error"};
        ClubDeportivo club = new ClubDeportivo("Club", 10);

        //Act & Assert
        assertThrows(ClubException.class, () -> {
            club.anyadirActividad(datos);
        });
    }

    //--------------------------------------------------------------------------------------------------
    // Test anyadirActividad con Grupo

    @Test
    @DisplayName("Test anyadirActividad grupo no existente correcto")
    public void anyadirActividadGrupoTest() throws Exception{
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
    @DisplayName("Test anyadirActividad grupo existente correcto")
    public void anyadirActividadGrupoTest2() throws Exception{
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
    @DisplayName("Test anyadirActividad incorrecto grupo null")
    public void anyadirActividadGrupoIncorrectoTest() throws Exception{
        //Arrange
        Grupo g = null;
        ClubDeportivo club = new ClubDeportivo("Club", 10);

        //Act & Assert
        assertThrows(ClubException.class, () -> {
            club.anyadirActividad(g);
        });
    }

    //--------------------------------------------------------------------------------------------------
    // Test plazasLibres

    @Test
    @DisplayName("Test plazasLibres correcto")
    public void plazasLibresTest() throws Exception{
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
    @DisplayName("Test plazasLibres incorrecto actividad no existente")
    public void plazasLibresIncorrectoTest() throws ClubException{
        //Arrange
        ClubDeportivo club = new ClubDeportivo("Club", 10);
        

        //Act
        int plazas = club.plazasLibres("actividad");

        //Assert
        assertEquals(0, plazas);
    }

    //--------------------------------------------------------------------------------------------------
    // Test matricular

    @Test
    @DisplayName("Test matricular correcto")
    public void matricularTest() throws Exception{
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

    //ESTE SE PUEDE QUITAR PERO CONSULTAR   
    @Test
    @DisplayName("Test matricular correcto con plazas libres<npersonas")
    public void matricularIncorrectoTest() throws Exception{
        //Arrange
        String nombreActividad = "actividad";
        ClubDeportivo club = new ClubDeportivo("Club", 10);
        Grupo g = new Grupo("nombre", nombreActividad, 10, 5, 10.0);
        Grupo g2= new Grupo("nombre2", nombreActividad, 15, 5, 10.0);
        club.anyadirActividad(g);
        club.anyadirActividad(g2);

        //Act
        club.matricular(nombreActividad, 7);

        //Assert
        assertEquals(17, g.getMatriculados()+g2.getMatriculados());

    }

    @Test
    @DisplayName("Test matricular incorrecto plazas libres<npersonas")
    public void matricularIncorrectoTest2() throws Exception{
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

    //--------------------------------------------------------------------------------------------------
    // Test ingresos

    @Test
    @DisplayName("Test ingresos correcto")
    public void ingresosTest() throws Exception{
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
    @DisplayName("Test toString correcto")
    public void toStringTest() throws Exception{
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