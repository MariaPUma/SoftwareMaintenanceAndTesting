package clubdeportivo;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ClubDeportivoAltoRendimientoTest {
    
    @Test
    @DisplayName("Test constructor correcto")
    public void constructorTest() throws Exception{
        //Arrange
        String nombre = "Club";
        int maximo = 20;
        double incremento = 10.0;
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
    @DisplayName("Test constructor maximo negativo")
    public void constructorMaximoNegativoTest() throws Exception{
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club", -1, 10.0);
        });
    }
    
    @Test
    @DisplayName("Test constructor incremento negativo")
    public void constructorIncrementoNegativoTest() throws Exception{
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club", 20, -10.0);
        });
    }

    @Test
    @DisplayName("Test constructor con tamaño correcto")
    public void constructorTamTest() throws Exception{
        //Arrange
        String nombre = "Club";
        int tam = 10;
        int maximo = 20;
        double incremento = 10.0;
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
    @DisplayName("Test constructor con tamaño negativo")
    public void constructorTamNegativoTest() throws Exception{
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club", -1, 20, 10.0);
        });
    }

    @Test
    @DisplayName("Test constructor con tamaño y maximo negativo")
    public void constructorTamMaximoNegativoTest() throws Exception{
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club", 5, -1, 10.0);
        });
    }

    @Test
    @DisplayName("Test constructor con tamaño y incremento negativo")
    public void constructorTamIncrementoNegativoTest() throws Exception{
        // Act & Assert
        assertThrows(ClubException.class, () -> {
            ClubDeportivoAltoRendimiento club = new ClubDeportivoAltoRendimiento("Club", 5, 20, -10.0);
        });
    }

    @Test
    @DisplayName("Test anyadirActividad correcto")
    public void anyadirActividadTest() throws Exception{
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
    @DisplayName("Test anyadirActividad con datos < 5")
    public void anyadirActividadDatosTest() throws Exception{
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
    @DisplayName("Test anyadirActividad con plazas > maximo")
    public void anyadirActividadPlazasTest() throws Exception{
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
    @DisplayName("Test anyadirActividad tipo numero incorrecto")
    public void anyadirActividadNumeroIncorrectoTest() throws Exception{
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
    @DisplayName("Test ingresos correcto")
    public void ingresosTest() throws Exception{
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

    //Minitest para probar ClubException()
    @Test
    @DisplayName("Test ClubException")
    public void clubExceptionTest() throws Exception{
        assertThrows(ClubException.class, () -> {
            throw new ClubException();
        });
    }
}
