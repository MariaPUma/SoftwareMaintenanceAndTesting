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
        Field gruposField = ClubDeportivo.class.getDeclaredField("grupos");
        gruposField.setAccessible(true);
        Grupo[] gruposAntes = (Grupo[]) gruposField.get(club);
        int longitudPre = gruposAntes.length;

        //Act
        club.anyadirActividad(datos);
        //Assert ESTA MAL

        Grupo[] gruposPost = (Grupo[]) gruposField.get(club);
        int longitudPost = gruposPost.length;

        assertEquals(longitudPre + 1, longitudPost);

        Field nombreField = ClubDeportivo.class.getDeclaredField("nombre");
        nombreField.setAccessible(true);
        assertEquals(incremento, nombreField.get(club));

        assertEquals("Futbol", gruposPost[0].getActividad());  
    }
}
