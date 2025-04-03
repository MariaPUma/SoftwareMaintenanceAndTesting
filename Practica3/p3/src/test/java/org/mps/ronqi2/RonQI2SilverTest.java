package org.mps.ronqi2;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mps.dispositivo.DispositivoSilver;


public class RonQI2SilverTest {   
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
        @DisplayName("Reconnect the device when it is connected but the sensors are not connected")
        public void reconectar_ConnectedDeviceButSensorsNotConnected_ReturnsFalse(){
            // Arrange
            DispositivoSilver dMock = mock(DispositivoSilver.class);
    
            // Configurar comportamiento del mock
            when(dMock.estaConectado()).thenReturn(false);
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
            when(dMock.estaConectado()).thenReturn(false);
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
            when(dMock.estaConectado()).thenReturn(false);
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
     */
    
    @Nested
    @DisplayName("Test of evaluating evaluarApneaSuenyo")
    class evaluarApneaSuenyo{
        @Test
        @DisplayName("The device is connected and the average of the readings is greater than the thresholds")
        public void evaluarApneaSuenyo_ConnectedDeviceExceedsThreshold_ReturnsTrue(){
            // Arrange
            DispositivoSilver dMock = mock(DispositivoSilver.class);
    
            // Configurar comportamiento del mock
            when(dMock.leerSensorPresion()).thenReturn(25.0f);
            when(dMock.leerSensorSonido()).thenReturn(35.0f);
    
            // Act
            RonQI2Silver ronQI2Silver = new RonQI2Silver();
            ronQI2Silver.anyadirDispositivo(dMock);
            ronQI2Silver.obtenerNuevaLectura();
            boolean resultado = ronQI2Silver.evaluarApneaSuenyo();
    
            // Assert
            assertTrue(resultado);
        }

        @Test
        @DisplayName("The device is connected and the average of the readings is less than the thresholds")
        public void evaluarApneaSuenyo_ConnectedDeviceNotExceedsThreshold_ReturnsFalse(){
            // Arrange
            DispositivoSilver dMock = mock(DispositivoSilver.class);
    
            // Configurar comportamiento del mock
            when(dMock.leerSensorPresion()).thenReturn(5.0f);
            when(dMock.leerSensorSonido()).thenReturn(6.0f);
    
            // Act
            RonQI2Silver ronQI2Silver = new RonQI2Silver();
            ronQI2Silver.anyadirDispositivo(dMock);
            ronQI2Silver.obtenerNuevaLectura();
            boolean resultado = ronQI2Silver.evaluarApneaSuenyo();
    
            // Assert
            assertFalse(resultado);
        }

        @Test
        @DisplayName("The device is connected and the average of the readings of sound sensor is smaller than the threshold")
        public void evaluarApneaSuenyo_ConnectedDeviceNotExceedsThresholdSound_ReturnsFalse(){
            // Arrange
            DispositivoSilver dMock = mock(DispositivoSilver.class);
    
            // Configurar comportamiento del mock
            when(dMock.leerSensorPresion()).thenReturn(25.0f);
            when(dMock.leerSensorSonido()).thenReturn(5.0f);
    
            // Act
            RonQI2Silver ronQI2Silver = new RonQI2Silver();
            ronQI2Silver.anyadirDispositivo(dMock);
            ronQI2Silver.obtenerNuevaLectura();
            boolean resultado = ronQI2Silver.evaluarApneaSuenyo();
    
            // Assert
            assertFalse(resultado);
        }

        @Test
        @DisplayName("The device is connected and the average of the readings of pressure is smaller than the threshold")
        public void evaluarApneaSuenyo_ConnectedDeviceNotExceedsThresholdPresure_ReturnsFalse(){
            // Arrange
            DispositivoSilver dMock = mock(DispositivoSilver.class);
    
            // Configurar comportamiento del mock
            when(dMock.leerSensorPresion()).thenReturn(5.0f);
            when(dMock.leerSensorSonido()).thenReturn(25.0f);
    
            // Act
            RonQI2Silver ronQI2Silver = new RonQI2Silver();
            ronQI2Silver.anyadirDispositivo(dMock);
            ronQI2Silver.obtenerNuevaLectura();
            boolean resultado = ronQI2Silver.evaluarApneaSuenyo();
    
            // Assert
            assertFalse(resultado);
        }
    }

    
    // No sería necesario, pero para completar el 100% de cobertura,
    @Nested
    @DisplayName("Test of estaConectado")
    class estaConectado{
        @Test
        @DisplayName("The device is connected")
        public void estaConectado_ConnectedDevice_ReturnsTrue(){
            // Arrange
            DispositivoSilver dMock = mock(DispositivoSilver.class);
    
            // Configurar comportamiento del mock
            when(dMock.estaConectado()).thenReturn(true);
    
            // Act
            RonQI2Silver ronQI2Silver = new RonQI2Silver();
            ronQI2Silver.anyadirDispositivo(dMock);
            boolean resultado = ronQI2Silver.estaConectado();
    
            // Assert
            assertTrue(resultado);
        }

        @Test
        @DisplayName("The device is not connected")
        public void estaConectado_DisconnectedDevice_ReturnsFalse(){
            // Arrange
            DispositivoSilver dMock = mock(DispositivoSilver.class);
    
            // Configurar comportamiento del mock
            when(dMock.estaConectado()).thenReturn(false);
    
            // Act
            RonQI2Silver ronQI2Silver = new RonQI2Silver();
            ronQI2Silver.anyadirDispositivo(dMock);
            boolean resultado = ronQI2Silver.estaConectado();
    
            // Assert
            assertFalse(resultado);
        }
    }
    
     /* Realiza un primer test para ver que funciona bien independientemente del número de lecturas.
     * Usa el ParameterizedTest para realizar un número de lecturas previas a calcular si hay apnea o no (por ejemplo 4, 5 y 10 lecturas).
     * https://junit.org/junit5/docs/current/user-guide/index.html#writing-tests-parameterized-tests
     */

    @Nested
    @DisplayName("Parameterized Tests for evaluarApneaSuenyo with variable readings")
    class EvaluarApneaSuenyoParameterized {
        @ParameterizedTest
        @ValueSource(ints = {4, 5, 10})
        @DisplayName("Detects apnea when averages are high after multiple readings")
        void evaluarApneaSuenyo_DetectsApnea_WithVariableHighReadings(int numLecturas) {
            // Arrange
            DispositivoSilver dMock = mock(DispositivoSilver.class);
            RonQI2Silver ronqi2Silver = new RonQI2Silver();
            ronqi2Silver.anyadirDispositivo(dMock);

            when(dMock.leerSensorPresion()).thenReturn(25.0f);
            when(dMock.leerSensorSonido()).thenReturn(35.0f);

            for (int i = 0; i < numLecturas; i++) {
                ronqi2Silver.obtenerNuevaLectura();
            }

            // Act
            boolean resultado = ronqi2Silver.evaluarApneaSuenyo();

            // Assert
            assertTrue(resultado);

            verify(dMock, times(numLecturas)).leerSensorPresion();
            verify(dMock, times(numLecturas)).leerSensorSonido();
        }


        @ParameterizedTest
        @ValueSource(ints = {4, 5, 10}) 
        @DisplayName("Does NOT detect apnea when pressure average is low after multiple readings")
        void evaluarApneaSuenyo_NoApnea_WithVariableLowPressureReadings(int numLecturas) {
            // Arrange
            DispositivoSilver dMock = mock(DispositivoSilver.class);
            RonQI2Silver ronqi2Silver = new RonQI2Silver();
            ronqi2Silver.anyadirDispositivo(dMock);

            when(dMock.leerSensorPresion()).thenReturn(15.0f);
            when(dMock.leerSensorSonido()).thenReturn(35.0f);

            // Act
            for (int i = 0; i < numLecturas; i++) {
                ronqi2Silver.obtenerNuevaLectura();
            }
            
            boolean resultado = ronqi2Silver.evaluarApneaSuenyo();

            // Assert
            assertFalse(resultado);

            verify(dMock, times(numLecturas)).leerSensorPresion();
            verify(dMock, times(numLecturas)).leerSensorSonido();
        }
        
        @ParameterizedTest
        @ValueSource(ints = {4, 5, 10}) 
        @DisplayName("Does NOT detect apnea when sound average is low after multiple readings")
        void evaluarApneaSuenyo_NoApnea_WithVariableLowSoundReadings(int numLecturas) {
            // Arrange
            DispositivoSilver dMock = mock(DispositivoSilver.class);
            RonQI2Silver ronqi2Silver = new RonQI2Silver();
            ronqi2Silver.anyadirDispositivo(dMock);

            when(dMock.leerSensorPresion()).thenReturn(25.0f); 
            when(dMock.leerSensorSonido()).thenReturn(25.0f); 

            // Act 
            for (int i = 0; i < numLecturas; i++) {
                ronqi2Silver.obtenerNuevaLectura();
            }
            
            boolean resultado = ronqi2Silver.evaluarApneaSuenyo();

            // Assert
            assertFalse(resultado);

            verify(dMock, times(numLecturas)).leerSensorPresion();
            verify(dMock, times(numLecturas)).leerSensorSonido();
        }
    }
}