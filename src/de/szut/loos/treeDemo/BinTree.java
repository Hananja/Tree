package de.szut.loos.treeDemo;

/**
 * Binary Tree implementation demo (without red black balancing)
 */
public class BinTree<T extends Comparable<T>> {
    private Node<T> root;

    public Node<T> getRoot() {
        return root;
    }

    void setRoot(Node<T> root) {
        this.root = root;
        root.resetParent();
    }

    public Node<T> search(T data) {
        if (null == root) { // Baum leer?
            return null;
        } else {
            return root.search(data);
        }
    }

    public void insert(T data) {
        if (null == root) { // Baum leer?
            root = new Node<>(data);
        } else {
            root.insert(data);
        }
    }

    @Override
    public String toString() {
        if (null == root) {
            return "null";
        } else {
            return root.toString();
        }
    }
}
