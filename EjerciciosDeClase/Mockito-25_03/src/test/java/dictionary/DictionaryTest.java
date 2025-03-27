package dictionary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)  
public class DictionaryTest {
    
    @Mock
    Map<String, String> wordMap;

    @InjectMocks
    Dictionary dic = new Dictionary();

    @Test
    public void whenUseInjectMocksAnnotation_thenCorrect() {
        Mockito.when(wordMap.get("Spain")).thenReturn("Espanya");

        assertEquals("Espanya", dic.getMeaning("Spain"));
    }
    
}
