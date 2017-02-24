package de.szut.loos.treeDemo;

/**
 * Node of Red Black Tree
 */
public class RedBlackNode<T extends Comparable<T>> extends Node<T>{

    public enum Color {RED, BLACK}

    private Color color = Color.RED;
    private RedBlackTree<T> tree;

    public RedBlackNode(RedBlackTree<T> tree, T data) {
        super(data);
        this.tree = tree;
    }

    private void setRoot(RedBlackNode<T> root) {
        getTree().setRoot( root );
    }

    private RedBlackTree<T> getTree() {
        return tree;
    }

    @Override
    public RedBlackNode<T> getLeft() {
        return (RedBlackNode<T>)super.getLeft();
    }

    @Override
    public RedBlackNode<T> getRight() {
        return (RedBlackNode<T>)super.getRight();
    }

    @Override
    public RedBlackNode<T> getParent() {
        return (RedBlackNode<T>)super.getParent();
    }

    @Override
    public RedBlackNode<T> getUncle() {
        return (RedBlackNode<T>)super.getUncle();
    }

    public Color getUncleColor() {
        RedBlackNode<T> uncle = getUncle();
        if( null == uncle ) { // no uncle
            return Color.BLACK; // NIL Nodes are black
        } else {
            return uncle.getColor();
        }
    }

    protected void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public boolean isBlack() {
        return this.color == Color.BLACK;
    }

    public boolean isRed() {
        return this.color == Color.RED;
    }

    @Override
    public void insert(T data) {
        insert(new RedBlackNode<>(tree, data));
    }

    protected void insert(RedBlackNode<T> node) {
        super.insert(node);
        node.rebalance();
    }

    @Override
    public String toString() {
        switch (getColor()) {
            case BLACK:
                return "b" + super.toString();
            case RED:
                return "r" + super.toString();
            default:
                return super.toString();
        }
    }

    protected void rebalance() {
        // Cases are mutually exclusive,
        // so return if one is true

        // Case 1
        if( isRoot() ) {
            setColor( Color.BLACK );
            return; // everything is awesome
        }

        // Case 2
        if( getParent().isBlack() ) {
            return; // everything is fine
        }

        // Case 3
        if( getParent().isRed() && getUncleColor() == Color.RED ) {
            getParent().setColor( Color.BLACK );
            getUncle().setColor( Color.BLACK );            // uncle must exists, because it is red!
            getParent().getParent().setColor( Color.RED ); // grandfather must exists, because there is an uncle!

            // recurse
            getParent().getParent().rebalance();
            return;
        }

        if( getUncleColor() == Color.BLACK && isRightChild() && getParent().isRed() ) { // parent must exists!
            if( getParent().isLeftChild() ) { // Case 4
                rotateLeft(getParent());
                this.getLeft().rebalance(); // recurse
                return;
            }
            if( getParent().isRightChild() ) { // Case 5
                getParent().setColor(Color.BLACK);
                getParent().getParent().setColor(Color.RED);
                rotateLeft(getParent().getParent());
                return;
            }
        }

        if( getUncleColor() == Color.BLACK && isLeftChild() && getParent().isRed() ) { // parent must exists!
            if( getParent().isLeftChild() ) { // Case 5
                getParent().setColor(Color.BLACK);
                getParent().getParent().setColor(Color.RED);
                rotateRight(getParent().getParent());
                return;
            }
            if( getParent().isRightChild() ) { // Case 4
                rotateRight(getParent());
                this.getRight().rebalance(); // recurse
                return; // just for optical reasons (return is not necessary)
            }
        }
    }

    /** Do a left rotation on parent that switches the roles of the current node N and its parent P
     * @param center
     */
    private void rotateLeft(RedBlackNode<T> center) {
        RedBlackNode<T> parent = center.getParent();

        if( center.isRoot() ) {
            setRoot( center.getRight() );
        } else { // center is not root
            if( center.isRightChild() ) {
                parent.setRight(center.getRight());
            } else { // center is left child
                parent.setLeft(center.getRight());
            }
        }
        RedBlackNode<T> rightChild = center.getRight(); // overwritten in next line
        center.setRight(rightChild.getLeft());
        rightChild.setLeft(center);
    }

    /** Do right rotation on center
     * @param center
     */
    private void rotateRight(RedBlackNode<T> center) {
        RedBlackNode<T> parent = center.getParent();

        if( center.isRoot() ) {
            setRoot( center.getLeft() );
        } else { // center is not root
            if( center.isLeftChild() ) {
                parent.setLeft(center.getLeft());
            } else { // center is right child
                parent.setRight(center.getLeft());
            }
        }
        RedBlackNode<T> leftChild = center.getLeft();
        center.setLeft(leftChild.getRight());
        leftChild.setRight(center);
    }

}
