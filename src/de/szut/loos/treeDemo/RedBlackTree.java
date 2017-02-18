package de.szut.loos.treeDemo;

/**
 * Red Black Tree implementation demo
 */
public class RedBlackTree<T extends Comparable<T>> extends BinTree<T> {
    RedBlackNode<T> root;

    @Override
    public RedBlackNode<T> getRoot() {
        return root;
    }

    @Override
    public String toString() {
        if( null == root ) {
            return "null";
        } else {
            return root.toString();
        }
    }

    /**
     * set new root
     * <p>
     * this is package private to allow RedBlacNode to set a new root
     *
     * @param root
     */
    void setRoot(RedBlackNode<T> root) {
        this.root = root;
        root.resetParent();
    }

    @Override
    public void insert(T data) {
        if (null == root) { // Baum leer?
            root = new RedBlackNode<T>(this, data);
            root.rebalance();
        } else {
            root.insert(data);
        }
    }
}
