package tree;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mps.tree.BinarySearchTree;
import org.mps.tree.BinarySearchTreeException;

public class BinarySearchTreeTest {
    Comparator<Integer> comparator;
    BinarySearchTree<Integer> tree;

    @BeforeEach
    public void setUp() {
        // Se ejecuta antes de cada test
        this.comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };
        
        tree = new BinarySearchTree<>(comparator);

    }

@Nested
    @DisplayName("render function tests")
    class render{
        @Test
        @DisplayName("render return an empty string when empty tree")
        public void Render_TreeWithNoElement_ReturnEmptyString(){
            //Arrange
            String expected = "";
            //Act
            String actual = tree.render();
            //Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("render return a string when tree with one element")
        public void Render_TreeWithOneElement_ReturnStringElement(){
            //Arrange
            tree.insert(5);
            String expected = "5";
            //Act
            String actual = tree.render();
            //Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("render return a string when tree with multiple elements")
        public void Render_TreeWithMultipleElements_ReturnStringElements(){
            //Arrange
            tree.insert(5);
            tree.insert(3);
            tree.insert(7);
            tree.insert(2);
            tree.insert(1);
            String expected = "5(3(2(1,),),7)";

            //Act
            String actual = tree.render();
            //Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("render return a string when tree has right branch")
        public void Render_TreeWithRightBranch_ReturnStringElements(){
            //Arrange
            tree.insert(5);
            tree.insert(3);
      
            String expected = "5(3,)";

            //Act
            String actual = tree.render();
            //Assert
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("render return a string when tree has left branch")
        public void Render_TreeWithLeftBranch_ReturnStringElements(){
            //Arrange
            tree.insert(5);
            tree.insert(7);
      
            String expected = "5(,7)";

            //Act
            String actual = tree.render();
            //Assert
            assertEquals(expected, actual);
        }

        
    }

    @Nested //sirve para agrupar pruebas y que visualmente se entienda mejor a que parte del código corresponden los tests
    @DisplayName("constructor tests")
    class whenNew{
        @Test
        @DisplayName("Constructor correctly instanciates an empty tree")
        public void Constructor_ValidParameters_CorrectlyInstanciated(){
            //Arrange
            BinarySearchTree<Integer> tree_test = new BinarySearchTree<>(comparator);
            String expected = "";
            //Act
            String actual = tree_test.render();
            //Assert

            assertEquals(expected, actual);
            assertEquals(0, tree_test.size());
            
        }
        @Test
        @DisplayName("Constructor throws ClubException if the comparator is null")
        public void Constructor_NullComparator_ThrowsException(){
            //Act & Assert
            assertThrows(BinarySearchTreeException.class, () -> {
                new BinarySearchTree<>(null);
            });
        }
        
    }

    @Nested
    @DisplayName("insert function tests")
    class insert{
        @Test
        @DisplayName("Insert one element correctly in an empty tree")
        public void Insert_ValueNotNullEmptyTree_InsertsAnElement(){
            //Arrange
            Integer value = 5;
            //Act
            tree.insert(value);
            //Assert
            assertTrue(tree.contains(value));
        }

        @Test
        @DisplayName("Insert an element bigger than the root of a tree with more than one element")
        public void Insert_SmallerValueEmptyTree_InsertsElements(){
            //Arrange
            tree.insert(5); //root
            tree.insert(10);
            Integer value = 15;
            //Act
            tree.insert(value);
            //Assert
            assertTrue(tree.contains(value));
        }

        @Test
        @DisplayName("Insert an element smaller than the root of a tree with more than one element")
        public void Insert_BiggerValueEmptyTree_InsertsElements(){
            //Arrange
            tree.insert(5); //root
            tree.insert(10);
            Integer value = 4;
            //Act
            tree.insert(value);
            //Assert
            assertTrue(tree.contains(value));
        }

        @Test
        @DisplayName("Insert an element that already exists in the tree")
        public void Insert_ValueAlreadyExists_ThrowsException(){
            //Arrange
            tree.insert(5);
            //Act & Assert
            assertThrows(BinarySearchTreeException.class, () -> {
                tree.insert(5);
            });
        }

        @Test
        @DisplayName("Insert a null value throws exception")
        public void Insert_NullValue_ThrowsException(){
            //Act & Assert
            assertThrows(BinarySearchTreeException.class, () -> {
                tree.insert(null);
            });
        }
    }
   
    @Nested
    @DisplayName("isLeaf function tests")
    class isLeaf{
        @Test
        @DisplayName("isLeaf returns true when the tree has only one element")
        public void isLeaf_TreeWithOneElement_ReturnTrue(){
            //Act
            tree.insert(5);
            //Assert
            assertTrue(tree.isLeaf());
        }

        @Test
        @DisplayName("isLeaf returns false when the tree has right branch")
        public void isLeaf_TreeWithRightBranch_ReturnFalse(){
            //Act
            tree.insert(5);
            tree.insert(10);
            //Assert
            assertFalse(tree.isLeaf());
        }

        @Test
        @DisplayName("isLeaf returns false when the tree has left branch")
        public void isLeaf_TreeWithLeftBranch_ReturnFalse(){
            //Act
            tree.insert(5);
            tree.insert(3);
            //Assert
            assertFalse(tree.isLeaf());
        }

        @Test
        @DisplayName("isLeaf throws a BinarySearchTreeException when the tree is empty")
        public void isLeaf_TreeWithNoElement_ThrowsException(){
            //Act & Assert
            assertThrows(BinarySearchTreeException.class, () -> {
                tree.isLeaf();
            });
        }
        
    }

    @Nested
    @DisplayName("contains function tests")
    class contains{
        @Test
        @DisplayName("contains return true when the tree has the value on the root")
        public void Contains_ExistingValueInTree_ReturnValue(){
            //Arrange
            tree.insert(5);
            //Act
            boolean result = tree.contains(5);
            //Assert
            assertTrue(result);
        }

        @Test
        @DisplayName("contains return false when the tree does not have the value")
        public void Contains_NonExistingValueInTree_ReturnFalse(){
            //Arrange
            tree.insert(5);
            //Act
            boolean result = tree.contains(10);
            //Assert
            assertFalse(result);
        }

        @Test
        @DisplayName("contains return true when the tree has the value in the left branch")
        public void Contains_ExistingValueInLeftBranch_ReturnValue(){
            //Arrange
            tree.insert(5);
            tree.insert(3);
            //Act
            boolean result = tree.contains(3);
            //Assert
            assertTrue(result);
        }

        @Test
        @DisplayName("contains return true when the tree has the value in the right branch")
        public void Contains_ExistingValueInRightBranch_ReturnValue(){
            //Arrange
            tree.insert(5);
            tree.insert(10);
            //Act
            boolean result = tree.contains(10);
            //Assert
            assertTrue(result);
        }

        @Test
        @DisplayName("contains return false when the tree is empty")
        public void Contains_EmptyTree_ReturnFalse(){
            //Act
            boolean result = tree.contains(10);
            //Assert
            assertFalse(result);
        }

        @Test
        @DisplayName("contains throws exception when the value is null")
        public void Contains_NullValue_ThrowsException(){
            //Act & Assert
            assertFalse(tree.contains(null));
        }       
    }

    @Nested
    @DisplayName("maximum function tests")
    class maximum{
        @Test
        @DisplayName("maximum of one element")
        public void maximum_emptyTree_returnsException(){
            //Act & Assert
            assertThrows(BinarySearchTreeException.class , () -> {
                tree.maximum();
            });
        }

        @Test
        @DisplayName("maximum of a single-node tree")
        public void maximum_singleNodeTree_returnsValue() {
            // Arrange
            tree.insert(10);
            // Act
            Integer maximum = tree.maximum();
            // Assert
            assertEquals(10, maximum);
        }

        @Test
        @DisplayName("maximum of a tree with multiple nodes")
        public void maximum_multipleNodes_returnsLargestValue() {
            // Arrange
            tree.insert(10);
            tree.insert(20);
            tree.insert(5);
            tree.insert(15);
            // Act
            Integer maximum = tree.maximum();
            // Assert
            assertEquals(20, maximum);
        }

        @Test
        @DisplayName("maximum after adding nodes to the right")
        public void maximum_rightSubtree_containsLargestValue() {
            // Arrange
            tree.insert(10);
            tree.insert(15);
            tree.insert(20);
            // Act
            Integer maximum = tree.maximum();
            // Assert
            assertEquals(20, maximum);
        }

        @Test
        @DisplayName("maximum when left subtree exists but doesn't affect result")
        public void maximum_withLeftSubtree_returnsCorrectMaximum() {
            // Arrange
            tree.insert(10);
            tree.insert(5);
            tree.insert(20);
            // Act
            Integer maximum = tree.maximum();
            // Assert
            assertEquals(20, maximum);
        }

        @Test
        @DisplayName("maximum when right subtree does not exists")
        public void maximum_withoutRightSubtree_returnsCorrectMaximum() {
            // Arrange
            tree.insert(10);
            tree.insert(5);
            // Act
            Integer maximum = tree.maximum();
            // Assert
            assertEquals(10, maximum);
        }
    }

    @Nested
    @DisplayName("minimum function tests")
    class minimum{
        @Test
        @DisplayName("minimum of one element")
        public void minimum_emptyTree_returnsException(){
            //Act & Assert
            assertThrows(BinarySearchTreeException.class , () -> {
                tree.minimum();
            });
        }

        @Test
        @DisplayName("minimum of a single-node tree")
        public void minimum_singleNodeTree_returnsValue() {
            // Arrange
            tree.insert(10);
            // Act
            Integer minimum = tree.minimum();
            // Assert
            assertEquals(10, minimum);
        }

        @Test
        @DisplayName("minimum of a tree with multiple nodes")
        public void minimum_multipleNodes_returnsSmallestValue() {
            // Arrange
            tree.insert(10);
            tree.insert(20);
            tree.insert(5);
            tree.insert(15);
            // Act
            Integer minimum = tree.minimum();
            // Assert
            assertEquals(5, minimum);
        }

        @Test
        @DisplayName("minimum after adding nodes to the left")
        public void minimum_leftSubtree_containsSmallestValue() {
            // Arrange
            tree.insert(10);
            tree.insert(5);
            tree.insert(3);
            // Act
            Integer minimum = tree.minimum();
            // Assert
            assertEquals(3, minimum);
        }

        @Test
        @DisplayName("minimum when right subtree exists but doesn't affect result")
        public void minimum_withRightSubtree_returnsCorrectMinimum() {
            // Arrange
            tree.insert(10);
            tree.insert(5);
            tree.insert(20);
            // Act
            Integer minimum = tree.minimum();
            // Assert
            assertEquals(5, minimum);
        }

        @Test
        @DisplayName("minimum when left subtree does not exists")
        public void minimum_withoutLeftSubtree_returnsCorrectMinimum() {
            // Arrange
            tree.insert(10);
            tree.insert(20);
            // Act
            Integer minimum = tree.minimum();
            // Assert
            assertEquals(10, minimum);
        }
    }

    @Nested
    @DisplayName("removeBranch function tests")
    class removeBranch {
        @Test
        @DisplayName("returns exception when value is null")
        public void removeBranch_nullValue_throwsException() {
            // Act & Assert
            assertThrows(BinarySearchTreeException.class, () -> {
                tree.removeBranch(null);
            });
        }
    

        @Test 
        @DisplayName("removeBranch does nothing when the value is not in the tree")
        public void removeBranch_valueNotInTree_doesNothing(){
            //Arrange
            tree.insert(5);
            String arbolExpected = tree.render();
            //Act 
            tree.removeBranch(10);
            //Arrange
            assertEquals(arbolExpected, tree.render());
        }
    
        @Test
        @DisplayName("removeBranch removes the root of a single-node tree")
        public void removeBranch_singleNodeTree_removesRoot() {
            // Arrange
            tree.insert(10);
            // Act
            tree.removeBranch(10);
            // Assert
            assertFalse(tree.contains(10));
        }
    

        @Test
        @DisplayName("removeBranch existing value smaller than root, removes left branch of a tree")
        public void removeBranch_existingValueSmallerThanRoot_removesLeftBranch() {
            // Arrange
            tree.insert(10);
            tree.insert(5);
            // Act
            tree.removeBranch(5);
            // Assert
            assertFalse(tree.contains(5));
        }

        @Test
        @DisplayName("removeBranch existing value smaller than root, removes left branch of a node of the tree")
        public void removeBranch_existingValueSmallerThanRoot_removesLeftBranchNode() {
            // Arrange
            tree.insert(10);
            tree.insert(5);
            tree.insert(3);
            // Act
            tree.removeBranch(3);
            // Assert
            assertFalse(tree.contains(3));
        }

        @Test
        @DisplayName("removeBranch existing value bigger than root, removes right branch of a tree")
        public void removeBranch_existingValueBiggerThanRoot_removesRightBranch() {
            // Arrange
            tree.insert(10);
            tree.insert(15);
            // Act
            tree.removeBranch(15);
            // Assert
            assertFalse(tree.contains(15));
        }

        @Test
        @DisplayName("removeBranch existing value bigger than root, removes right branch of a node of the tree")
        public void removeBranch_existingValueBiggerThanRoot_removesRightBranchNode() {
            // Arrange
            tree.insert(10);
            tree.insert(15);
            tree.insert(20);
            // Act
            tree.removeBranch(15);
            // Assert
            assertFalse(tree.contains(15));
        }

        @Test
        @DisplayName("removeBranch removes existing value bigger than root, removes right branch of a node of the tree")
        public void removeBranch_existingValueBiggerThanRoot_removesRightBranchNode2() {
            // Arrange
            tree.insert(10);
            tree.insert(15);
            tree.insert(20);
            // Act
            tree.removeBranch(20);
            // Assert
            assertFalse(tree.contains(20));
        }

        @Test
        @DisplayName("")
        public void test(){
            //Arrange
            tree.insert(10);
            //Act
            tree.removeBranch(5);
            //Assert
            assertFalse(tree.contains(5));
        }
    }

        
    
    
    @Nested
    @DisplayName("size function tests")
    class size{
        @Test
        @DisplayName("size of an empty tree")
        public void size_emptyTree_returnsZero() {
            // Act
            int size = tree.size();
            // Assert
            assertEquals(0, size);
        }

        @Test
        @DisplayName("size of a single-node tree")
        public void size_singleNodeTree_returnsOne() {
            // Arrange
            tree.insert(10);
            // Act
            int size = tree.size();
            // Assert
            assertEquals(1, size);
        }

        @Test
        @DisplayName("size of a tree with multiple nodes")
        public void size_multipleNodes_returnsCorrectSize() {
            // Arrange
            tree.insert(10);
            tree.insert(5);
            tree.insert(15);
            // Act
            int size = tree.size();
            // Assert
            assertEquals(3, size);
        }

        @Test
        @DisplayName("size after removing nodes")
        public void size_afterRemovingNodes_returnsCorrectSize() {
            tree.insert(10);
            tree.insert(5);
            tree.insert(15);
            tree.removeBranch(5);
            // Act
            int size = tree.size();
            // Assert
            assertEquals(2, size);
        }
    }


    @Nested
    @DisplayName("depth function tests")
    class depth{
        @Test
        @DisplayName("depth of an empty tree")
        public void depth_emptyTree_returnsZero() {
            // Act
            int depth = tree.depth();
            // Assert
            assertEquals(0, depth);
        }

        @Test
        @DisplayName("depth of a single-node tree")
        public void depth_singleNodeTree_returnsOne() {
            // Arrange
            tree.insert(10);
            // Act
            int depth = tree.depth();
            // Assert
            assertEquals(1, depth);
        }

        @Test
        @DisplayName("depth of a tree with multiple nodes")
        public void depth_multipleNodes_returnsCorrectDepth() {
            // Arrange
            tree.insert(10);
            tree.insert(5);
            tree.insert(15);
            // Act
            int depth = tree.depth();
            // Assert
            assertEquals(2, depth);
        }

        @Test
        @DisplayName("depth after removing nodes")
        public void depth_afterRemovingNodes_returnsCorrectDepth() {
            // Arrange
            tree.insert(10);
            tree.insert(5);
            tree.insert(15);
            tree.removeBranch(5);
            // Act
            int depth = tree.depth();
            // Assert
            assertEquals(2, depth);
        }
    }


    // REMOVEVALUE TESTS
    @Nested
    @DisplayName("removeValue function tests")
    class removeValue{


        @Test
        @DisplayName("removeValue given a null value")
        public void removeValue_NullValue_ThrowsException() {
            // Act & Assert
            assertThrows(BinarySearchTreeException.class, () -> {
                tree.removeValue(null);
            });
        }

        @Test
        @DisplayName("removeValue given a value that is in the tree and is the root and leaf")
        public void removeValue_ValueIsRootIsLeaf_RemovesValueFromTree() {
            // Arrange
            tree.insert(10);
            // Act
            tree.removeValue(10);
            // Assert
            assertFalse(tree.contains(10));
        }

        @Test
        @DisplayName("removeValue given a value that is in the tree and is the root")
        public void removeValue_ValueIsRoot_RemoveValueFromTree() {
            // Arrange
            tree.insert(10);
            tree.insert(4);
            tree.insert(5);
            tree.insert(11);
            tree.insert(12);
            // Act
            tree.removeValue(10);
            // Assert
            assertFalse(tree.contains(10));
            assertTrue(tree.contains(4));
            assertTrue(tree.contains(5));
            assertTrue(tree.contains(11));
            assertTrue(tree.contains(12));
        }

        @Test
        @DisplayName("removeValue given a value that is in the tree and is not the root, isLeaf in the left branch")
        public void removeValue_ValueIsNotRoot_RemoveValueFromTree() {
            // Arrange
            tree.insert(10);
            tree.insert(4);
            tree.insert(3);
            tree.insert(11);

            // Act
            tree.removeValue(3);
            // Assert
            assertFalse(tree.contains(3));
            assertTrue(tree.contains(4));
            assertTrue(tree.contains(10));
            assertTrue(tree.contains(11));
        }

        @Test
        @DisplayName("removeValue given a value that is in the tree and is not the root, isLeaf in the right branch")
        public void removeValue_ValueIsNotRootInTheRightBranch_RemoveValueFromTree() {
            // Arrange
            tree.insert(10);
            tree.insert(4);
            tree.insert(11);
            tree.insert(12);

            // Act
            tree.removeValue(12);
            // Assert
            assertFalse(tree.contains(12));
            assertTrue(tree.contains(4));
            assertTrue(tree.contains(10));
            assertTrue(tree.contains(11));
        }

        @Test
        @DisplayName("removeValue given a value that is in the tree and is not the root, not isLeaf in the left branch")
        public void removeValue_ValueIsNotRootInTheLeftBranch_RemoveValueFromTree() {
            // Arrange
            tree.insert(10);
            tree.insert(4);
            tree.insert(5);
            tree.insert(3);

            // Act
            tree.removeValue(4);
            // Assert
            assertFalse(tree.contains(4));
            assertTrue(tree.contains(3));
            assertTrue(tree.contains(5));
            assertTrue(tree.contains(10));

        }

        @Test
        @DisplayName("removeValue given a value that is in the tree and is not the root, not isLeaf in the left branch, has one children in the left")
        public void removeValue_ValueIsNotRootInTheLeftBranchWithOneChildren_RemoveValueFromTree() {
            // Arrange
            tree.insert(10);
            tree.insert(4);
            tree.insert(5);
            tree.insert(3);
            tree.insert(2);

            // Act
            tree.removeValue(3);
            // Assert
            assertFalse(tree.contains(3));
            assertTrue(tree.contains(4));
            assertTrue(tree.contains(5));
            assertTrue(tree.contains(2));
            assertTrue(tree.contains(10));
        }

        @Test
        @DisplayName("removeValue given a value that is in the tree and is not the root, not isLeaf in the right branch")
        public void removeValue_ValueIsNotRootNotLeafInTheRightBranch_RemoveValueFromTree(){
            // Arrange
            tree.insert(10);
            tree.insert(4);
            tree.insert(11);
            tree.insert(12);
            tree.insert(13);

            // Act
            tree.removeValue(11);
            // Assert
            assertFalse(tree.contains(11));
            assertTrue(tree.contains(12));
            assertTrue(tree.contains(13));
            assertTrue(tree.contains(10));
            assertTrue(tree.contains(4));
        }

        

        @Test
        @DisplayName("removeValue given a value that is not in the tree")
        public void removeValue_ValueIsNotInTree_DoNothing() {
            // Arrange
            tree.insert(10);

            // Act && Assert
            assertThrows(BinarySearchTreeException.class, () -> {
                tree.removeValue(5);
            });
           
        }



        
    }
    

    @Nested
    @DisplayName("inOrder tests")
    class inOrder{
        @Test
        @DisplayName("inOrder of an empty tree returns an empty list")
        public void inOrder_EmptyTree_RetunrnsEmptyList(){;
            //Act & Assert
            assertTrue(tree.inOrder().isEmpty());
        }

        @Test
        @DisplayName("inOrder of a single-node tree returns a list with only that node")
        public void inOrder_singleNodeTree_returnsSingleElement() {
            // Arrange
            tree.insert(10);
            
            // Act & Assert
            assertEquals(List.of(10), tree.inOrder());
        }

        @Test
        @DisplayName("inOrder of a left-skewed tree")
        public void inOrder_leftSkewedTree_returnsSortedOrder() {
            // Arrange
            tree.insert(10);
            tree.insert(5);
            tree.insert(2);
            
            // Act & Assert
            assertEquals(List.of(2, 5, 10), tree.inOrder());
        }

        @Test
        @DisplayName("inOrder of a right-skewed tree")
        public void inOrder_rightSkewedTree_returnsSortedOrder() {
            // Arrange
            tree.insert(10);
            tree.insert(15);
            tree.insert(20);
            
            // Act & Assert
            assertEquals(List.of(10, 15, 20), tree.inOrder());
        }

        @Test
        @DisplayName("In-order traversal of a balanced tree")
        public void inOrder_balancedTree_returnsSortedOrder() {
            // Arrange
            tree.insert(12);
            tree.insert(15);
            tree.insert(20);
            tree.insert(2);
            tree.insert(5);
            tree.insert(7);
            tree.insert(10);
            //tree.balance();
            
            // Act
            List<Integer> result = tree.inOrder();
            
            // Assert
            assertEquals(List.of(2, 5, 7, 10, 12, 15, 20), result);
        }
    }

    @Nested
    @DisplayName("balance tests")
    class balance{
        @Test
        @DisplayName("tree is empty, remains empty")
        void balance_emptyTree_remainsEmptyAndBalanced() {
            // Act
            tree.balance(); 
            
            // Assert
            assertEquals(0, tree.size());
        }
    
        @Test
        @DisplayName("single node tree remains single and balanced")
        void balance_singleNodeTree_remainsSingleAndBalanced() {
            // Arrange
            tree.insert(10);
            int initialSize = tree.size();
    
            // Act
            tree.balance();
            
            // Assert
            assertEquals(initialSize, tree.size());
        }
    
        @Test
        @DisplayName("balance a deeply skewed tree (right) ")
        void balance_skewedOddTree_becomesBalanced() {
            tree.insert(10);
            tree.insert(20);
            tree.insert(30);
            tree.insert(40);
            tree.insert(50); 
    
            // Act
            tree.balance();
            
            // Assert
            assertFalse(tree.render().equals("10(,20(,30(,40(,50))))"));
            assertTrue(tree.render().equals("30(10(,20),40(,50))"));
            assertEquals((int) Math.ceil(Math.log(5 + 1) / Math.log(2)), tree.depth());
        }
    
        @Test
        @DisplayName("balance a deeply skewed tree (left)")
        void balance_skewedEvenTree_becomesBalanced() {
            // Arrange: Crea un árbol muy desbalanceado (lista a la izquierda)
            tree.insert(60);
            tree.insert(50);
            tree.insert(40);
            tree.insert(30);
            tree.insert(20);
            tree.insert(10);
    
            // Act
            tree.balance();
            
            // Assert
            assertFalse(tree.render().equals("60(50(40(30(20(10,),),),),)"));
            assertTrue(tree.render().equals("30(10(,20),50(40,60))"));
            assertEquals((int) Math.ceil(Math.log(7 + 1) / Math.log(2)), tree.depth());
        }

        @Test
        @DisplayName("balance a balanced tree")
        void balance_balancedTree_remainsBalanced() {
            // Arrange
            tree.insert(12);
            tree.insert(15);
            tree.insert(13);
            tree.insert(20);
            tree.insert(7);
            tree.insert(5);
            tree.insert(10);
            String expected = tree.render();
    
            // Act
            tree.balance();
            
            // Assert
            assertEquals(expected, tree.render());
            assertEquals((int) Math.ceil(Math.log(7 + 1) / Math.log(2)), tree.depth());
        }
    }
}
