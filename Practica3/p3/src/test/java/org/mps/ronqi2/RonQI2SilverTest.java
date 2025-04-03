package org.mps.ronqi2;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mps.dispositivo.DispositivoSilver;


public class RonQI2SilverTest {

    //Cosas inservibles que maría quiere guardar
    // @Test
    // @DisplayName("Test de inicialización del dispositivo")
    // public void test (){
    //     DispositivoSilver dispositivoMock = mock(DispositivoSilver.class);
        
    //     // Configurar comportamiento del mock
    //     when(dispositivoMock.conectarSensorPresion()).thenReturn(true);
    //     when(dispositivoMock.conectarSensorSonido()).thenReturn(true);
    //     when(dispositivoMock.configurarSensorPresion()).thenReturn(true);
    //     when(dispositivoMock.configurarSensorSonido()).thenReturn(true);
    //     when(dispositivoMock.estaConectado()).thenReturn(true);
    //     when(dispositivoMock.leerSensorPresion()).thenReturn((29.0f));
    //     when(dispositivoMock.leerSensorSonido()).thenReturn(35.0f);
    //     // Crear una instancia de RonQI2Silver
    //     RonQI2Silver ronQI2Silver = new RonQI2Silver();
    //     ronQI2Silver.anyadirDispositivo(dispositivoMock);

        
    //     // Inicializar el dispositivo
    //     if (ronQI2Silver.inicializar()) {
    //         System.out.println("Dispositivo inicializado correctamente.");
    //     } else {
    //         System.out.println("Error al inicializar el dispositivo.");
    //         return;
    //     }

    //     // Obtener varias lecturas
    //     for (int i = 0; i < 8; i++) {
    //         ronQI2Silver.obtenerNuevaLectura();
    //         System.out.println("Nueva lectura obtenida.");
    //     }

    //     // Evaluar apnea del sueño
    //     boolean apneaDetectada = ronQI2Silver.evaluarApneaSuenyo();
    //     System.out.println("¿Apnea del sueño detectada?: " + apneaDetectada);

    //     assertTrue(apneaDetectada);
    // }

    
    /*
     * Analiza con los caminos base qué pruebas se han de realizar para comprobar que al inicializar funciona como debe ser. 
     * El funcionamiento correcto es que si es posible conectar ambos sensores y configurarlos, 
     * el método inicializar de ronQI2 o sus subclases, 
     * debería devolver true. En cualquier otro caso false. Se deja programado un ejemplo.
     */

    /*
     * Un inicializar debe configurar ambos sensores, comprueba que cuando se inicializa de forma correcta (el conectar es true), 
     * se llama una sola vez al configurar de cada sensor.
     */

    @Nested
    @DisplayName("Test de inicialización del dispositivo")
    class inicializar{
        @Test
        @DisplayName("The initialization of the device is correct with a DispositivoSilver mock")
        public void inicializar_StepsSucceded_ReturnsTrue(){
            // Arrange
            DispositivoSilver dMock = mock(DispositivoSilver.class);
    
            // Configurar comportamiento del mock
            when(dMock.conectarSensorPresion()).thenReturn(true);
            when(dMock.conectarSensorSonido()).thenReturn(true);
            when(dMock.configurarSensorPresion()).thenReturn(true);
            when(dMock.configurarSensorSonido()).thenReturn(true);
            when(dMock.estaConectado()).thenReturn(true);
    
            // Act
            RonQI2Silver ronQI2Silver = new RonQI2Silver();
            ronQI2Silver.anyadirDispositivo(dMock);
            boolean resultado = ronQI2Silver.inicializar();
    
            // Assert
            assertTrue(resultado);
            verify(dMock).conectarSensorPresion();
            verify(dMock).conectarSensorSonido();
            
        }
    
    
        @Test
        @DisplayName("The initialization of the device is incorrect preasure sensor is not connected") 
        public void inicializar_PreasureNotConnected_ReturnsFalse(){
            // Arrange
            DispositivoSilver dMock = mock(DispositivoSilver.class);
    
            // Configurar comportamiento del mock
            when(dMock.conectarSensorPresion()).thenReturn(false);
    
            // Act
            RonQI2Silver ronQI2Silver = new RonQI2Silver();
            ronQI2Silver.anyadirDispositivo(dMock);
            boolean resultado = ronQI2Silver.inicializar();
    
            // Assert
            assertFalse(resultado);
        }
    
        @Test
        @DisplayName("The initialization of the device is incorrect sound sensor is not connected")
        public void inicializar_SoundNotConnected_ReturnsFalse(){
            // Arrange
            DispositivoSilver dMock = mock(DispositivoSilver.class);
    
            // Configurar comportamiento del mock
            when(dMock.conectarSensorPresion()).thenReturn(true);
            when(dMock.configurarSensorPresion()).thenReturn(true);
            when(dMock.conectarSensorSonido()).thenReturn(false);
    
            // Act
            RonQI2Silver ronQI2Silver = new RonQI2Silver();
            ronQI2Silver.anyadirDispositivo(dMock);
            boolean resultado = ronQI2Silver.inicializar();
    
            // Assert
            assertFalse(resultado);
        }
    
        @Test
        @DisplayName("The initialization of the device is incorrect preasure sensor is not configured")
        public void inicializar_PreasureNotConfigured_ReturnsFalse(){
            // Arrange
            DispositivoSilver dMock = mock(DispositivoSilver.class);
    
            // Configurar comportamiento del mock
            when(dMock.conectarSensorPresion()).thenReturn(true);
            when(dMock.configurarSensorPresion()).thenReturn(false);
            when(dMock.conectarSensorSonido()).thenReturn(true);
            when(dMock.configurarSensorSonido()).thenReturn(true);
    
            // Act
            RonQI2Silver ronQI2Silver = new RonQI2Silver();
            ronQI2Silver.anyadirDispositivo(dMock);
            boolean resultado = ronQI2Silver.inicializar();
    
            // Assert
            assertFalse(resultado);
        }
    
        @Test
        @DisplayName("The initialization of the device is incorrect sound sensor is not configured")
        public void inicializar_SoundNotConfigured_ReturnsFalse(){
            // Arrange
            DispositivoSilver dMock = mock(DispositivoSilver.class);
    
            // Configurar comportamiento del mock
            when(dMock.conectarSensorPresion()).thenReturn(true);
            when(dMock.configurarSensorPresion()).thenReturn(true);
            when(dMock.conectarSensorSonido()).thenReturn(true);
            when(dMock.configurarSensorSonido()).thenReturn(false);
    
            // Act
            RonQI2Silver ronQI2Silver = new RonQI2Silver();
            ronQI2Silver.anyadirDispositivo(dMock);
            boolean resultado = ronQI2Silver.inicializar();
    
            // Assert
            assertFalse(resultado);
        }
    
        @Test
        @DisplayName("The initialization of the device is incorrect both sensors are not configured")
        public void inicializar_BothSensorsNotConnected_ReturnsFalse(){
            // Arrange
            DispositivoSilver dMock = mock(DispositivoSilver.class);
            // Configurar comportamiento del mock
            when(dMock.conectarSensorPresion()).thenReturn(true);
            when(dMock.conectarSensorSonido()).thenReturn(true);
            when(dMock.configurarSensorPresion()).thenReturn(false);
            when(dMock.configurarSensorSonido()).thenReturn(false);
    
            // Act
            RonQI2Silver ronQI2Silver = new RonQI2Silver();
            ronQI2Silver.anyadirDispositivo(dMock);
            boolean resultado = ronQI2Silver.inicializar();
    
            // Assert
            assertFalse(resultado);
        }
    }


    /*
     * Un reconectar, comprueba si el dispositivo desconectado, en ese caso, conecta ambos y devuelve true si ambos han sido conectados. 
     * Genera las pruebas que estimes oportunas para comprobar su correcto funcionamiento. 
     * Centrate en probar si todo va bien, o si no, y si se llama a los métodos que deben ser llamados.
     */

    @Nested
    @DisplayName("Test of reconnecting the device")
    class reconect{
        @Test
        @DisplayName("Reconnect the device when it is disconnected")
        public void reconectar_DisconnectedDevice_ReturnsTrue(){
            // Arrange
            DispositivoSilver dMock = mock(DispositivoSilver.class);
    
            // Configurar comportamiento del mock
            when(dMock.conectarSensorPresion()).thenReturn(true);
            when(dMock.conectarSensorSonido()).thenReturn(true);
            when(dMock.estaConectado()).thenReturn(false);
    
            // Act
            RonQI2Silver ronQI2Silver = new RonQI2Silver();
            ronQI2Silver.anyadirDispositivo(dMock);
            boolean resultado = ronQI2Silver.reconectar();
    
            // Assert
            assertTrue(resultado);
            verify(dMock).conectarSensorPresion();
            verify(dMock).conectarSensorSonido();
        }

        @Test
        @DisplayName("Reconnect the device when it is connected")
        public void reconectar_ConnectedDevice_ReturnsFalse(){
            // Arrange
            DispositivoSilver dMock = mock(DispositivoSilver.class);
    
            // Configurar comportamiento del mock
            when(dMock.estaConectado()).thenReturn(true);
    
            // Act
            RonQI2Silver ronQI2Silver = new RonQI2Silver();
            ronQI2Silver.anyadirDispositivo(dMock);
            boolean resultado = ronQI2Silver.reconectar();
    
            // Assert
            assertFalse(resultado);
        }
        

        @Test
        @DisplayName("Reconnect the device when it is connected but the sensors are not connected")
        public void reconectar_ConnectedDeviceButSensorsNotConnected_ReturnsFalse(){
            // Arrange
            DispositivoSilver dMock = mock(DispositivoSilver.class);
    
            // Configurar comportamiento del mock
            when(dMock.estaConectado()).thenReturn(true);
            when(dMock.conectarSensorPresion()).thenReturn(false);
            when(dMock.conectarSensorSonido()).thenReturn(false);
    
            // Act
            RonQI2Silver ronQI2Silver = new RonQI2Silver();
            ronQI2Silver.anyadirDispositivo(dMock);
            boolean resultado = ronQI2Silver.reconectar();
    
            // Assert
            assertFalse(resultado);
        }

        @Test
        @DisplayName("Reconnect the device when it is connected but the sensors are not connected")
        public void reconectar_ConnectedDeviceButSoundSensorNotConnected_ReturnsFalse(){
            // Arrange
            DispositivoSilver dMock = mock(DispositivoSilver.class);
    
            // Configurar comportamiento del mock
            when(dMock.estaConectado()).thenReturn(true);
            when(dMock.conectarSensorPresion()).thenReturn(true);
            when(dMock.conectarSensorSonido()).thenReturn(false);
    
            // Act
            RonQI2Silver ronQI2Silver = new RonQI2Silver();
            ronQI2Silver.anyadirDispositivo(dMock);
            boolean resultado = ronQI2Silver.reconectar();
    
            // Assert
            assertFalse(resultado);
        }

        @Test
        @DisplayName("Reconnect the device when it is connected but the sensors are not connected")
        public void reconectar_ConnectedDeviceButPreasureSensorNotConnected_ReturnsFalse(){
            // Arrange
            DispositivoSilver dMock = mock(DispositivoSilver.class);
    
            // Configurar comportamiento del mock
            when(dMock.estaConectado()).thenReturn(true);
            when(dMock.conectarSensorPresion()).thenReturn(false);
            when(dMock.conectarSensorSonido()).thenReturn(true);
    
            // Act
            RonQI2Silver ronQI2Silver = new RonQI2Silver();
            ronQI2Silver.anyadirDispositivo(dMock);
            boolean resultado = ronQI2Silver.reconectar();
    
            // Assert
            assertFalse(resultado);
        }

        
    }
    
    /*
     * El método evaluarApneaSuenyo, evalua las últimas 5 lecturas realizadas con obtenerNuevaLectura(), 
     * y si ambos sensores superan o son iguales a sus umbrales, que son thresholdP = 20.0f y thresholdS = 30.0f;, 
     * se considera que hay una apnea en proceso. Si hay menos de 5 lecturas también debería realizar la media.
     * /
     
     /* Realiza un primer test para ver que funciona bien independientemente del número de lecturas.
     * Usa el ParameterizedTest para realizar un número de lecturas previas a calcular si hay apnea o no (por ejemplo 4, 5 y 10 lecturas).
     * https://junit.org/junit5/docs/current/user-guide/index.html#writing-tests-parameterized-tests
     */
}