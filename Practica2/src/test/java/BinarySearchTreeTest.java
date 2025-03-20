import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class BinarySearchTreeTest {

    @Nested //sirve para agrupar pruebas y que visualmente se entienda mejor a que parte del código corresponden los tests
    @DisplayName("when new")
    class whenNew{
        @Test
    public void Test(){ //SOLO PARA PROBAR QUE MAVEN Y JACOCO FUNCIONAN
        assertTrue(true);
    }
    }
   
}
