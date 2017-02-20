package de.szut.loos.treeDemo;

/**
 * Red Black Tree implementation demo
 */
public class RedBlackTree<T extends Comparable<T>> extends BinTree<T> {
    @Override
    public RedBlackNode<T> getRoot() {
        return (RedBlackNode<T>)super.getRoot();
    }

    @Override
    public void insert(T data) {
        if (null == getRoot()) { // Baum leer?
            setRoot(new RedBlackNode<T>(this, data));
            getRoot().rebalance();
        } else {
            getRoot().insert(data); // delegate to root node
        }
    }
}
