package de.szut.loos.treeDemo;

/**
 * Binary Tree implementation demo (without red black balancing)
 */
public class BinTree<T extends Comparable<T>> {
    private Node<T> root;

    @Override
    public String toString() {
        if( null == root ) {
            return "null";
        } else {
            return root.toString();
        }
    }

    public void insert(T data) {
        if(null == root) { // Baum leer?
            root = new Node<>(data);
        } else {
            root.insert(data);
        }
    }

    public Node<T> getRoot() {
        return root;
    }

    public Node<T> search(T data) {
        if(null == root) { // Baum leer?
            return null;
        } else {
            return root.search(data);
        }
    }
}
