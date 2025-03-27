package app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

//@ExtendWith(MockitoExtension.class)  
public class MockedListTest {
  
  //@Mock
	//List<String> mockListAuto;

  @Test
  public void simpleMockingExamples() {
    // STEP 1: create mock object
    List<String> mockedList = mock(List.class);

    // STEP 2: using the mock object
    mockedList.add("green");
    mockedList.add("blue");
    mockedList.add("green");
    
    verify(mockedList, times(2)).add("green");

   // Darle comportamiento al Mock/Stub
    when(mockedList.contains(anyString())).thenReturn(true);

    assertTrue(mockedList.contains("blue"));

    mockedList.clear();

    // STEP 3: verify the calls to the mocked object
    verify(mockedList).add("blue");
    verify(mockedList, times(2)).add("green");
    verify(mockedList).clear();

    verify(mockedList, times(3)).add(anyString());
    verify(mockedList, never()).add("red");
    verify(mockedList, never()).size();
    verify(mockedList, atLeastOnce()).add("green");
    verify(mockedList, never()).remove("green");
    verify(mockedList, atLeast(1)).add(anyString());
  }

  @Test
  public void simpleStubbingExamples() {
    // STEP 2: stubbing
    List<String> mockListAuto = mock(List.class);
    mockListAuto.add("green");
    mockListAuto.add("blue");
    when(mockListAuto.size()).thenReturn(5);

    // STEP 3: verify the calls to the mocked object
    verify(mockListAuto, times(2)).add(anyString());

    assertEquals(5, mockListAuto.size());
  }

  @Test
  public void examplesOfSpying() {
    List<String> list = new ArrayList<>();

    List<String> spyList = spy(list);

    spyList.add("green");
    spyList.add("red");

    //when(spyList.get(50)).thenReturn("Hola");
    //doReturn("hola").when(spyList).get(50);

    when(spyList.contains("green")).thenReturn(false);

    assertFalse(spyList.contains("green"));

    verify(spyList).add("green");
    verify(spyList).add("red");
    verify(spyList, times(2)).add(anyString());

    assertEquals(2, spyList.size());

    doReturn(100).when(spyList).size();
    //doReturn("foo").when(spyList).get(50);

    assertEquals(100, spyList.size());
  }
}
