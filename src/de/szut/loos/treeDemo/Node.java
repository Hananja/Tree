package de.szut.loos.treeDemo;

/**
 * Binary Tree Node implementation demo
 */
public class Node<T extends Comparable<T>> implements Comparable<Node<T>>{
    private T data;
    private Node<T> left;
    private Node<T> right;
    protected Node<T> parent;

    public Node(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    protected void setData(T data) {
        this.data = data;
    }

    public Node<T> getLeft() {
        return left;
    }

    protected void setLeft(Node left) {
        this.left = left;
        if (left != null) { // housekeeping for parent
            left.parent = this;
        }
    }

    public Node<T> getRight() {
        return right;
    }

    protected void setRight(Node right) {
        this.right = right;
        if (right != null) { // housekeeping for parent
            right.parent = this;
        }
    }

    public Node<T> getParent() {
        return parent;
    }

    /**
     * set new parent
     *
     * this is package private to allow RedBlacTree to set a new root
     *
     */
    void resetParent() {
        this.parent = null;
    }

    public Node<T> getUncle() {
        Node<T> parent = this.getParent();
        if( null == parent || null == parent.getParent() ){
            return null;
        } else {
            if( parent.isRightChild() ) {
                return parent.getParent().getLeft();
            } else { // isLeftChild
                return parent.getParent().getRight();
            }
        }
    }

    public boolean isRoot() {
        return null == getParent();
    }

    public boolean isLeftChild() {
        return null != getParent() && getParent().getLeft() == this;
    }

    public boolean isRightChild() {
        return null != getParent() && getParent().getRight() == this;
    }

    protected void insert(Node node) {
        int comparison = compareTo(node);
        if (0 == comparison) {
            throw new IllegalArgumentException("data is equal");
        }
        if (0 > comparison) {
            if (null == getRight()) { // empty right branch
                setRight(node);
            } else {
                getRight().insert(node); // recursion
            }
        }
        if (0 < comparison) {
            if (null == getLeft()) { // empty left branch
                setLeft(node);
            } else {
                getLeft().insert(node); // recursion
            }
        }
    }

    public void insert(T data) {
        insert(new Node<>(data));
    }

    private void resetParentChild() {
        if( isLeftChild() ) {
            getParent().setLeft(null);
        }
        if( isRightChild() ) {
            getParent().setRight(null);
        }
    }

    public void delete() {
        parent = getParent();
        resetParentChild();
        if( null != getLeft() ) { // left children?
            parent.insert(getLeft());
        }
        if( null != getRight() ) { // right children?
            parent.insert(getRight());
        }
    }

    public Node<T> search(T data) {
        int comparison = getData().compareTo(data);
        if( 0 == comparison ) {
            return this;
        } else {
            if( 0 > comparison ) {
                if (null == getRight()) {
                    return null; // not found
                } else {
                    return getRight().search(data); // recursion
                }
            } else { // 0 < comparison
                if( null == getLeft() ){
                    return null; // not found
                } else {
                    return getLeft().search(data); // recursion
                }
            }
        }
    }

    @Override
    public int compareTo(Node<T> o) {
        return getData().compareTo(o.getData());
    }

    @Override
    public String toString() {
        return "N{" +
                data +
                ", l=" + nullToDash(left) +
                ", r=" + nullToDash(right) +
                '}';
    }

    private static String nullToDash(Object o) {
        if( null == o ) {
            return "-";
        } else {
            return o.toString();
        }
    }
}
