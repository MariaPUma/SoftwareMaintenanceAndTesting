package org.mps.tree;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BinarySearchTree<T> implements BinarySearchTreeStructure<T> {
    private Comparator<T> comparator;
    private T value;
    private BinarySearchTree<T> left;
    private BinarySearchTree<T> right;

    public String render(){
        String render = "";

        if (value != null) {
            render += value.toString();
        }

        if (left != null || right != null) {
            render += "(";
            if (left != null) {
                render += left.render();
            }
            render += ",";
            if (right != null) {
                render += right.render();
            }
            render += ")";
        }

        return render;
    }

    public BinarySearchTree(Comparator<T> comparator) {
        if(comparator == null){
            throw new BinarySearchTreeException("ERROR: comparator is null");
        }

        this.comparator = comparator;
        this.left = null;
        this.right = null;
        this.value = null;
    }

    @Override
    public void insert(T value) {
        if (value == null){
            throw new BinarySearchTreeException("ERROR: value is null");
        }

        if (this.contains(value)){
            throw new BinarySearchTreeException("ERROR: value already exists in the tree");
        }

        if (this.value == null) {
            this.value = value;
        } 
        else if (comparator.compare(value, this.value) < 0) {
            if (left == null) {
                left = new BinarySearchTree<>(comparator);
            }
            left.insert(value);
        } 
        else { //comparator.compare(value, this.value) > 0) 
            if (right == null) {
                right = new BinarySearchTree<>(comparator);
            }
            right.insert(value);
        } 
    }

    @Override
    public boolean isLeaf() {
        if (this.value == null) {
            throw new BinarySearchTreeException("ERROR: tree is empty");
        } 
        return this.left == null && this.right == null;
    }

    @Override
    public boolean contains(T value) {
        if (this.value == null) {
            return false;
        }
        else if (comparator.compare(value, this.value) == 0){
            return true;
        } 
        else {
            if (this.left != null && comparator.compare(value, this.value) < 0){
                return this.left.contains(value);
            }
            else if (this.right != null && comparator.compare(value, this.value) > 0){
                return this.right.contains(value);
            }
        }
        return false;
    }

    @Override
    public T minimum() {
        if (this.value == null) {
            throw new BinarySearchTreeException("ERROR: tree is empty");
        } 
        if (this.left == null){
            return this.value;
        }
        return this.left.minimum();
    }

    @Override
    public T maximum() {
        if (this.value == null) {
            throw new BinarySearchTreeException("ERROR: tree is empty");
        } 
        if (this.right == null){
            return this.value;
        }
        return this.right.maximum();
    }

    @Override
    public void removeBranch(T value) {
        if (value == null) {
            throw new BinarySearchTreeException("ERROR: value is null");
        }
        
        // if (!this.contains(value)) {
        //     throw new BinarySearchTreeException("ERROR: value not found trying to remove branch");
        // }
    
        if (comparator.compare(value, this.value) == 0) {
            this.value = null;
            this.left = null;
            this.right = null;
        }

        else if (comparator.compare(value, this.value) < 0) {
            if (this.left != null) {
                if (comparator.compare(value, this.left.value) == 0){
                    this.left = null;
                } else {
                    this.left.removeBranch(value);
                }
            } 
        } 
        else { // comparator.compare(value, this.value) > 0
            if (this.right != null) {
                if (comparator.compare(value, this.right.value) == 0){
                    this.right = null;
                } else {
                    this.right.removeBranch(value);
                }
            } 
        }
    }
    

    @Override
    public int size() {
        if (this.value == null) {
            return 0; // Un árbol vacío tiene tamaño 0
        }
        int leftSize = (this.left == null) ? 0 : this.left.size();
        int rightSize = (this.right == null) ? 0 : this.right.size();
        return 1 + leftSize + rightSize;
    }

    @Override
    public int depth() {
        if (this.value == null) {
            return 0; // Si el nodo no tiene valor, el árbol está vacío
        }
    
        int leftDepth = (this.left == null) ? 0 : this.left.depth();
        int rightDepth = (this.right == null) ? 0 : this.right.depth();
    
        return 1 + Math.max(leftDepth, rightDepth);
    }
    
    // Complex operations

    @Override
    public void removeValue(T value) {
        if(value == null || !this.contains(value)){
            throw new BinarySearchTreeException("ERROR: value not valid");
        }
        
        if (comparator.compare(value, this.value) == 0) {
            if (this.isLeaf()) {
                this.value = null;
            } else if (this.right != null) {
                T rightMinimum = this.right.minimum();
                this.removeValue(rightMinimum);
                this.value = rightMinimum;
            } else { // Solo tiene hijo izquierdo
                this.value = this.left.value;
                this.right = this.left.right;
                this.left = this.left.left;
            } 
        } else if(comparator.compare(value, this.value) < 0){
            if (comparator.compare(value, this.left.value) == 0 && this.left.isLeaf()) {
                this.left = null; // Si el hijo izquierdo es el nodo a eliminar y es hoja, lo eliminamos
            } else {
                this.left.removeValue(value);
            }
        }else{ //comparator.compare(value, this.value) > 0
            if (comparator.compare(value, this.right.value) == 0 && this.right.isLeaf()){
                this.right = null;
            }else {
               this.right.removeValue(value); 
            }
        }
        
        
    }

    @Override
    public List<T> inOrder() {
        List<T> orderedItems = new ArrayList<>();
         
        if (this.left != null) orderedItems.addAll(this.left.inOrder());
        if (this.value != null) orderedItems.add(this.value);
        if (this.right != null) orderedItems.addAll(this.right.inOrder());

        return orderedItems;
    }

    @Override
    public void balance() {
        List<T> orden = this.inOrder();
        
        this.value = null;
        this.left = null;
        this.right = null;
    
        insertarBalanceado(orden, 0, orden.size() - 1);
    }
    
    private void insertarBalanceado(List<T> lista, int inicio, int fin) {
        if (inicio > fin) {
            return;
        }
    
        int mitad = (inicio + fin) / 2;
        this.insert(lista.get(mitad));
    
        insertarBalanceado(lista, inicio, mitad - 1);
        insertarBalanceado(lista, mitad + 1, fin);
    }
}
