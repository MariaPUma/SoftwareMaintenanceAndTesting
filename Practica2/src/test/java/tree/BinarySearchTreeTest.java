package tree;
import java.util.Comparator;

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

        // NO haría falta estos tests porque ya se comprueba en los anteriores

        // @Test
        // @DisplayName("contains return false when the tree has left branch and the value is not in the tree and smaller than the root")
        // public void Contains_NotExistingValueInLeftBranch_ReturnValue(){
        //     //Arrange
        //     tree.insert(5);
        //     tree.insert(3);
        //     //Act
        //     boolean result = tree.contains(1);
        //     //Assert
        //     assertTrue(result);
        // }

        // @Test
        // @DisplayName("contains return false when the tree has right branch and the value is not in the tree and bigger than the root")
        // public void Contains_NotExistingValueInRightBranch_ReturnValue(){
        //     //Arrange
        //     tree.insert(5);
        //     tree.insert(10);
        //     //Act
        //     boolean result = tree.contains(15);
        //     //Assert
        //     assertTrue(result);
        // }

        
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
        @DisplayName("removeBranch throws exception when the value is not in the tree")
        public void removeBranch_valueNotInTree_throwsException(){
            //Arrange
            tree.insert(5);
            //Act & Assert
            assertThrows(BinarySearchTreeException.class, () -> {
                tree.removeBranch(10);
            });
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
        @DisplayName("removeBranch non-existing value smaller than root, throws exception")
        public void removeBranch_nonExistingValueSmallerThanRoot_throwsException() {
            // Arrange
            tree.insert(10);
            tree.insert(5);
            // Act & Assert
            assertThrows(BinarySearchTreeException.class, () -> {
                tree.removeBranch(3);
            });
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
}
