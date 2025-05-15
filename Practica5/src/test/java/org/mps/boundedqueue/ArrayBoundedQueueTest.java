//--------------------------------------------------------------------------------
/* 
 * INTEGRANTES DEL GRUPO:
 * - Javier Toledo Delgado
 * - María Paulina Ordóñez Walkowiak
 */
//--------------------------------------------------------------------------------

package org.mps.boundedqueue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;

public class ArrayBoundedQueueTest {
    @Nested
    class ArrayBoundedQueue_Constructor {
        @Test
        @DisplayName("Constructor initialize correctly with a capacity greater than 0")
        public void constructor_capacityGreaterThanZero_initializeCorrectly() {
            /*  INITIALIZE CORRECTLY RESULT: buffer!=null
                buffer = (T[]) new Object[capacity];
                first = 0;
                nextFree = 0;
                size = 0;
            * */
            //Arrange
            int capacity = 3;

            //Act
            ArrayBoundedQueue<Integer> abq = new ArrayBoundedQueue<>(capacity);

            //Assert
            assertThat(abq)
                    .isNotNull();

            assertThat(abq.getFirst())
                    .isZero();

            assertThat(abq.getLast())
                    .isZero();

            assertThat(abq.size())
                    .isNotNull()
                    .isZero();
        }

        @Test
        @DisplayName("Constructor throws IllegalArgumentException with a capacity negative or zero")
        public void constructor_negativeCapacity_throwsIllegalArgumentException() {
            //Arrange
            int capacity = -1;

            //Act & Assert
            assertThatThrownBy(() -> new ArrayBoundedQueue<>(capacity))
                    .isInstanceOf(IllegalArgumentException.class)
                    .withFailMessage("ArrayBoundedException: capacity must be positive");
        }
    }

    @Nested
    class put {
        @Test
        @DisplayName("put inserts an element in the queue, updates value of nextFree and increases the size")
        public void put_queueNotFullAndValueNotNull_addsElement() {
            //Arrange
            ArrayBoundedQueue<Integer> abq = new ArrayBoundedQueue<>(3);

            //Act
            abq.put(5);

            //Assert
            assertThat(abq.size())
                    .isEqualTo(1);

            assertThat(abq.getLast())
                    .isEqualTo(1);

            assertThat(abq.getFirst())
                    .isZero();
        }

        @Test
        @DisplayName("put throws FullBoundedQueueException when queue is full")
        public void put_queueFull_throwsFullBoundedQueueException() {
            //Arrange
            ArrayBoundedQueue<Integer> abq = new ArrayBoundedQueue<>(2);
            abq.put(1);
            abq.put(2);

            //Act & Assert
            assertThatThrownBy(() -> abq.put(3))
                    .isInstanceOf(FullBoundedQueueException.class)
                    .hasMessage("put: full bounded queue");
        }

        @Test
        @DisplayName("put throws IllegalArgumentException when value is null")
        public void put_nullValue_throwsIllegalArgumentException() {
            //Arrange
            ArrayBoundedQueue<Integer> abq = new ArrayBoundedQueue<>(2);

            //Act & Assert
            assertThatThrownBy(() -> abq.put(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("put: element cannot be null");
        }

        @Test
        @DisplayName("put reuses space when queue wraps around (circular behavior)")
        public void put_circularBehavior_worksCorrectly() {
            //Arrange
            ArrayBoundedQueue<Integer> abq = new ArrayBoundedQueue<>(3);
            abq.put(1);
            abq.put(2);
            abq.put(3);
            abq.get();

            //Act
            abq.put(4);

            //Assert
            assertThat(abq.getFirst())
                    .isEqualTo(1);

            assertThat(abq.getLast()).isEqualTo(1);

            assertThat(abq.get())
                    .isEqualTo(2);

            assertThat(abq.get())
                    .isEqualTo(3);

            assertThat(abq.get())
                    .isEqualTo(4);
        }
    }

    @Nested
    class get {

        @Test
        @DisplayName("get return the item on the first position when queue is not empty")
        public void get_queueIsNotEmpty_returnItem() {
            //Arrange
            ArrayBoundedQueue<Integer> abq = new ArrayBoundedQueue<>(2);
            abq.put(3);
            abq.put(5);

            //Act
            int item = abq.get();

            //Assert
            assertThat(abq.size())
                    .isEqualTo(1);

            assertThat(abq.getFirst())
                    .isEqualTo(1);

            assertThat(item)
                    .isEqualTo(3);
        }

        @Test
        @DisplayName("get throws EmptyBoundedQueueException when queue is empty")
        public void get_emptyQueue_throwsEmptyBoundedQueueException() {
            //Arrange
            ArrayBoundedQueue<Integer> abq = new ArrayBoundedQueue<>(2);

            //Act & Assert
            assertThatThrownBy(() -> abq.get())
                    .isInstanceOf(EmptyBoundedQueueException.class)
                    .hasMessage("get: empty bounded queue");
        }
    }

    @Nested
    class iterator {
        @Test
        @DisplayName("iterator initialised correctly an object ArrayBoundedQueueIterator")
        public void iteratorHasNext_array_correctlyInitialised() {
            //Arrange
            ArrayBoundedQueue<Integer> abq = new ArrayBoundedQueue<>(2);
            //Act
            Iterator<Integer> iterator = abq.iterator();
            //Assert
            assertThat(iterator)
                    .isNotNull();
        }

        @Test
        @DisplayName("hasNext return true when there is a next number in the buffer")
        public void hasNext_elementsAvailable_returnsTrue() {
            //Arrange
            ArrayBoundedQueue<Integer> abq = new ArrayBoundedQueue<>(3);
            abq.put(1);
            abq.put(2);
            Iterator<Integer> iterator = abq.iterator();

            //Act & Assert
            assertThat(iterator.hasNext())
                    .isTrue();
        }

        @Test
        @DisplayName("hasNext returns false when there is no more elements")
        public void hasNext_noMoreElements_returnsFalse() {
            //Arrange
            ArrayBoundedQueue<Integer> abq = new ArrayBoundedQueue<>(3);
            abq.put(1);
            Iterator<Integer> iterator = abq.iterator();
            iterator.next();

            //Act & Assert
            assertThat(iterator.hasNext())
                    .isFalse();
        }

        @Test
        @DisplayName("next returns elements in correct order")
        public void next_multipleElements_returnsElementsInOrder() {
            //Arrange
            ArrayBoundedQueue<Integer> abq = new ArrayBoundedQueue<>(3);
            abq.put(1);
            abq.put(2);
            abq.put(3);
            Iterator<Integer> iterator = abq.iterator();

            //Act & Assert
            assertThat(iterator.next())
                    .isEqualTo(1);

            assertThat(iterator.next())
                    .isEqualTo(2);

            assertThat(iterator.next())
                    .isEqualTo(3);
        }

        @Test
        @DisplayName("next throws NoSuchElementException when there is no more elements")
        public void next_noMoreElements_throwsNoSuchElementException() {
            //Arrange
            ArrayBoundedQueue<Integer> abq = new ArrayBoundedQueue<>(2);
            abq.put(1);
            Iterator<Integer> iterator = abq.iterator();
            iterator.next();

            //Act & Assert
            assertThatThrownBy(() -> iterator.next())
                    .isInstanceOf(NoSuchElementException.class)
                    .hasMessage("next: bounded queue iterator exhausted");
        }
    }
}
