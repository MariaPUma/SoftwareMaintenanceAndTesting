package factorial;
/*
  Test cases
  1. factorial 0 -> 1
  2. factorial 1 -> 1
*/

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class FactorialTest {
  Factorial factorial  ;

  @BeforeEach
  void setup() {
    factorial = new Factorial() ;
  }


  @Test
  @DisplayName("factorial de cero es 1")
  void factorialOfZeroIsOne() {
    int obtainedValue = factorial.factorial(0) ;
    int expectedValue = 1 ;

    assertEquals(expectedValue, obtainedValue) ;
  }

  @Test
  @DisplayName("1️⃣❗ = 1️⃣")
  void factorialOfOneIsOne() {
    int obtainedValue = factorial.factorial(1) ;
    int expectedValue = 1 ;

    assertEquals(expectedValue, obtainedValue) ;
  }

}