package purchase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PurchaseOrderTest {

  @Test
  public void shouldPurchaseWorkProperlyWhenThereAreEnoughProducts() {
    // STEP 1: create mock object
    Warehouse warehouse = mock(Warehouse.class) ;
    // STEP 2: define behavior
    when(warehouse.thereAreProducts("Beer", 20)).thenReturn(true) ;

    // STEP 3: execute
    PurchaseOrder purchaseOrder = new PurchaseOrder("Beer", 20) ;
    purchaseOrder.purchase(warehouse);

    // STEP 4: verify
    verify(warehouse).remove("Beer", 20);
    verify(warehouse, times(1)).remove("Beer", 20);
  }

  // Probamos que al llamara purchase y al haber productos
  @Test 
  public void purchase_ThereIsProduct_RemoveProductFromWarehouse() {
    // Arrange
      // STEP 1: create mock object
    Warehouse almacen = mock(Warehouse.class) ;
    PurchaseOrder compra = new PurchaseOrder("mandarina", 50) ;

    //Act
    when(almacen.thereAreProducts("mandarina", 50)).thenReturn(true) ;
    compra.purchase(almacen) ;

    //Assert
    verify(almacen).remove("mandarina", 50) ;

  }

  // Probamos que al llamara purchase y al no haber productos
  @Test 
  public void purchase_ThereNoIsProduct_RemoveProductFromWarehouse() {
    // Arrange
      // STEP 1: create mock object
    Warehouse almacen = mock(Warehouse.class) ;
    PurchaseOrder compra = new PurchaseOrder("mandarina", 50) ;

    //Act
    when(almacen.thereAreProducts("mandarina", 50)).thenReturn(false) ;
    compra.purchase(almacen) ;

    //Assert
    verify(almacen,never()).remove("mandarina", 50) ;
    
  }


  // Probamos que al llamar a search y al haber productos
  @Test
  public void search_ThereIsProduct_ReturnAmount() {
    // Arrange
    Warehouse almacen = mock(Warehouse.class) ;
    String name = "beerxXxv2" ;
    PurchaseOrder compra = new PurchaseOrder(name, 50); 
    when(almacen.contains(name)).thenReturn(true) ;
    when(almacen.getAmount(name)).thenReturn(100) ;


    //Act
    int result = compra.search(name, almacen) ;

    //Assert
    assertEquals(100, result);
  }

  

}