package de.szut.loos.treeDemo;

import org.junit.Assert;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.TreeSet;

import static org.junit.Assert.assertFalse;

public class RedBlackTreeTest {

    private RedBlackTree<Integer> tree = new RedBlackTree<>();
    private LinkedHashSet<Integer> data = new LinkedHashSet<>();

    @org.junit.Test
    public void insert() throws Exception {
        Random random = new Random();
        random.setSeed(42);
        for( int i = 0; i < 100; i++ ) {
            int data = random.nextInt() % 1000;
            if(this.data.add(data)) { // is new item
                tree.insert(data);
                System.out.println(tree);

                try {
                    // condition 1 is true by definition
                    checkCondition2();
                    // condition 3 is true by definition
                    checkCondition4();
                    checkCondition5();

                    checkCompleteness();
                } catch (AssertionError e) {
                    System.out.println(this.data);
                    throw e;
                }
            }
        }
    }

    private void checkCompleteness() {
        for( int data: this.data ) {
            Assert.assertNotNull( String.format( "numer %s is found", this.tree.search( data )));
        }
    }

    private void checkCondition4() {
        checkCondition4( tree.getRoot() );
    }

    private void checkCondition4( RedBlackNode<Integer> node ) {
        RedBlackNode<Integer> leftChild = node.getLeft();
        if( null != leftChild ) {
            if( node.isRed() )
            assertFalse( leftChild.isRed() );
            checkCondition4( leftChild );
        }

        RedBlackNode<Integer> rightChild = node.getRight();
        if( null != rightChild ) {
            if( node.isRed() ) {
                assertFalse( rightChild.isRed() );
            }
            checkCondition4( rightChild );
        }
    }

    private void checkCondition5() {
       checkCondition5(tree.getRoot());
    }

    private int checkCondition5( RedBlackNode<Integer> node ) {
       if( null == node ) {
           return 1;
       } else {
           int leftDepth = checkCondition5( node.getLeft() );
           int rightDepth = checkCondition5( node.getRight() );
           Assert.assertTrue( leftDepth == rightDepth );
           if( node.isRed() ) {
               return leftDepth;
           } else {
               return leftDepth + 1;
           }
       }
    }

    private void checkCondition2() {
        Assert.assertTrue( tree.getRoot().isBlack() );
    }


}