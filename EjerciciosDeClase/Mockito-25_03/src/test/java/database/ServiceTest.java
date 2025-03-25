package database;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.booleanThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.xml.crypto.Data;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ServiceTest {

    @Test
        public void query_WhenDatabaseAvailable_ReturnTrue() {
        Database databaseMock = mock(Database.class);
            when(databaseMock.isAvailable()).thenReturn(true);
            Service t = new Service(databaseMock); 

            boolean b = t.query("* from t");

            assertTrue(b);
            verify(databaseMock, times(1)).isAvailable();
        }

        @Test
        @DisplayName("El metodo query devuelve true con una consulta")
        public void query_SelectAll_ReturnTrue(){
            //Arrange
            Database database = mock(Database.class);
            Service service = new Service(database);
            when(database.isAvailable()).thenReturn(true);

            //Act
            boolean result = service.query("Select * from uma");

            //Asssert
            assertTrue(result);
        }

        @Test
        public void toString_Users42_ReturnCorrectString()
        {
            //Arrange
            int number = 42;
            String expected ="Users in the database : " +number;
            Database database = mock(Database.class);
            Service service = new Service(database);
            when(database.getNumberofUsers()).thenReturn(number);

            //Act
            String result= service.toString();

            //Assert
            assertEquals(expected, result);

        }

    
}
