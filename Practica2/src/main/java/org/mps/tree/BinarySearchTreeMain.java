//MENSAJE PARA MARÍA (BORRA EL MENSAJE CUANDO LO VEAS):
//METODO CREADO POR MI, NO ES PARTE DEL CODIGO QUE NOS DAN, NO HAY QUE HACERLE 100% DE COBERTURA Y SUPONGO QUE NI ENTREGARLO,
//SOLO ESTÁ PARA PROBAR EL ARBOL
package org.mps.tree;

import java.util.Comparator;

public class BinarySearchTreeMain {
    public static void main(String[] args) {
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        };
    
        BinarySearchTree<Integer> arbol = new BinarySearchTree<>(comparator);
        arbol.insert(5);
        arbol.insert(3);
        arbol.insert(7);
        arbol.insert(2);
        arbol.insert(1);
        info(arbol);
        if (arbol.contains(3)) System.out.println("Contiene 3"); //string para probar el contains
        arbol.removeBranch(3);
        info(arbol);
        if (!arbol.contains(3)) System.out.println("No contiene 3"); //string para probar el contains     
    }
        
    public static void info(BinarySearchTree<Integer> arbol){
        System.out.println("Tam: " + arbol.size() + ", max; " + arbol.maximum() + ", min: " + arbol.minimum() + ", depth: " + arbol.depth());
        System.out.println("Arbol: " + arbol.render());
    }
}
