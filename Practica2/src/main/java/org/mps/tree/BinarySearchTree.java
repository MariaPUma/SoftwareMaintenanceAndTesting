package org.mps.tree;

import java.util.Comparator;

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
        this.comparator = comparator;
        this.left = null;
        this.right = null;
        this.value = null;
    }

    @Override
    public void insert(T value) {
        if (this.value == null) {
            this.value = value;
        } else {
            if (comparator.compare(value, this.value) < 0) {
                if (left == null) {
                    left = new BinarySearchTree<>(comparator);
                }
                left.insert(value);
            } else if (comparator.compare(value, this.value) > 0) {
                if (right == null) {
                    right = new BinarySearchTree<>(comparator);
                }
                right.insert(value);
            }
        }
    }

    @Override
    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }

    @Override
    public boolean contains(T value) {
        if (this.value == value){
            return true;
        } else {
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
        // TODO
        return null;
    }

    @Override
    public T maximum() {
        // TODO
        return null;
    }

    @Override
    public void removeBranch(T value){
        // TODO
    }

    @Override
    public int size() {
        //TODO
        return 0;
    }

    @Override
    public int depth() {
        // TODO
        return 0;
    }

    // Complex operations
    // (Estas operaciones se incluirán más adelante para ser realizadas en la segunda
    // sesión de laboratorio de esta práctica)
}
