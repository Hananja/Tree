package de.szut.loos.treeDemo;

/**
 * Binary Tree Node implementation demo
 */
public class Node<T extends Comparable<T>> implements Comparable<Node<T>>{
    private T data;
    private Node<T> left;
    private Node<T> right;
    private Node<T> parent;

    public Node(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public Node<T> getLeft() {
        return left;
    }

    public Node<T> getParent() {
        return parent;
    }

    public Node<T> getRight() {
        return right;
    }

    protected void setLeft(Node left) {
        this.left = left;
        if (left != null) { // housekeeping for parent
            left.parent = this;
        }
    }

    protected void setRight(Node right) {
        this.right = right;
        if (right != null) { // housekeeping for parent
            right.parent = this;
        }
    }

    protected void setData(T data) {
        this.data = data;
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

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", left=" + left +
                ", right=" + right +
                '}';
    }

    private void resetParentChild() {
        if( getParent().getLeft() == this) {
            getParent().setLeft(null);
        }
        if( getParent().getRight() == this) {
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

    @Override
    public int compareTo(Node<T> o) {
        return getData().compareTo(o.getData());
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
}
