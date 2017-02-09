package de.szut.loos.treeDemo;

/**
 * Node of Red Black Tree
 */
public class RedBlackNode<T extends Comparable<T>> extends Node<T>{

    public enum Color { RED, BLACK};
    private Color color = Color.RED;
    private RedBlackTree<T> tree;

    public void setRoot(RedBlackNode<T> root) {
        getTree().setRoot( root );
    }

    private RedBlackTree<T> getTree() {
        return tree;
    }

    public RedBlackNode(RedBlackTree<T> tree, T data) {
        super(data);
        this.tree = tree;
    }

    @Override
    public RedBlackNode<T> getLeft() {
        return (RedBlackNode<T>)super.getLeft();
    }

    @Override
    public RedBlackNode<T> getRight() {
        return (RedBlackNode<T>)super.getRight();
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
    public RedBlackNode<T> getParent() {
        return (RedBlackNode<T>)super.getParent();
    }

    @Override
    public RedBlackNode<T> getUncle() {
        return (RedBlackNode<T>)super.getUncle();
    }

    @Override
    public void insert(T data) { insert(new RedBlackNode<>(tree, data));}

    protected void insert(RedBlackNode<T> node) {
        super.insert(node);
        node.rebalance();
    }

    public Color getUncleColor() {
        RedBlackNode<T> uncle = getUncle();
        if( null == uncle ) {
            return Color.BLACK; // NIL Nodes are black
        } else {
            return uncle.getColor();
        }
    }

    protected void rebalance() {
        // Cases are mutually exclusive, so return if one is true

        // Case 1
        if( isRoot() ) {
            setColor( Color.BLACK );
            return;
        }

        // Case 2
        if( getParent().isBlack() ) {
            return; // everything is fine
        }

        // Case 3
        if( getParent().isRed() && getUncleColor() == Color.RED ) {
            getParent().setColor( Color.BLACK );
            getUncle().setColor( Color.BLACK ); // uncle must exists!
            getParent().getParent().setColor( Color.RED ); // grandfather must exists!

            // recurse
            getParent().getParent().rebalance();
            return;
        }

        // Case 4
        if( getUncleColor() == Color.BLACK && isRightChild() && getParent().isRed() ) { // parent must exists!
            RedBlackNode<T> parent = getParent();
            rotateLeft();
            parent.rebalance();

            return;
        }

        // Case 5
        if( getUncleColor() == Color.BLACK && isLeftChild() && getParent().isRed() ) { // parent must exists!
            RedBlackNode<T> parent = getParent();
            Color parentcolor = parent.getColor();

            RedBlackNode<T> grandparent = parent.getParent();
            Color grandparentcolor = grandparent.getColor();

            rotateRight();

            parent.setColor( grandparentcolor );
            grandparent.setColor( parentcolor );

            return; // just for optical reasons (return is not necessary)
        }
    }

    /** Do a left rotation on parent that switches the roles of the current node N and its parent P
     */
    private void rotateLeft() {
        RedBlackNode<T> parent = getParent();
        RedBlackNode<T> grandparent = parent.getParent();
        grandparent.setLeft( this );
        parent.setRight( getLeft() );
        setLeft( parent );
    }

    /** Do right rotation on gradfather
     */
    private void rotateRight() {
        RedBlackNode<T> parent = getParent();
        RedBlackNode<T> grandparent = parent.getParent();

        if( grandparent.isRoot() ) {
            setRoot( parent );
        } else {
            RedBlackNode<T> greatgrandparent = grandparent.getParent();
            if( parent.isRightChild() ) {
                greatgrandparent.setRight( parent );
            } else { // parent is left child
                greatgrandparent.setLeft(parent);
            }
        }

        grandparent.setLeft( parent.getRight() );
        parent.setRight( grandparent );
    }

}
