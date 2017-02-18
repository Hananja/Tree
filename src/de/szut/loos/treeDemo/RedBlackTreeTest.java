package de.szut.loos.treeDemo;

import org.junit.Assert;

import java.util.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class RedBlackTreeTest {


    @org.junit.Test
    public void insert() throws Exception {
        Random random = new Random();
        for( int seed: new int[]{42, 23, 0, -1}) {
            random.setSeed(seed);
            RedBlackTree<Integer> tree = new RedBlackTree<>();
            System.out.println(String.format("Initial tree: %s", tree));
            LinkedHashSet<Integer> datas = new LinkedHashSet<>();
            for (int i = 0; i < 1000; i++) {
                int data = random.nextInt() % 10000;
                if (datas.add(data)) { // is new item
                    try {
                        tree.insert(data);
                        System.out.println(String.format("% 3d: add % 5d -> %s", i, data, tree));

                        checkCondition1_hasColor(tree);
                        checkCondition2_rootIsBlack(tree);

                        // condition 3 is true by definition:
                        // nil nodes are not handled
                        // getUncleColor() is tested implicitly by conditions 4 and 5

                        checkCondition4_redHasNoRedChild(tree);
                        checkCondition5_blackDepth(tree);


                        checkCompleteness(datas, tree);      // datas is subset of tree nodes
                        checkNoAdditionalNodes(datas, tree); // datas is superset of tree nodes
                    } catch (AssertionError | NullPointerException e) {
                        System.out.println(datas);
                        throw e;
                    }
                }
            }
        }
    }

    /**
     * check for no additional nodes in a tree
     *
     * @param datas data set
     * @param tree tree to check
     */
    private void checkNoAdditionalNodes(Set<Integer> datas, RedBlackTree<Integer> tree) {
        checkNoAdditionalNodes(datas, tree.getRoot());
    }

    /**
     * check for no additional nodes in a tree
     *
     * @param datas data set
     * @param node root of subtree to check
     */
    private void checkNoAdditionalNodes(Set<Integer> datas, RedBlackNode<Integer> node) {
        if( null != node ) {
            assertTrue(datas.contains(node.getData()));
            checkNoAdditionalNodes(datas, node.getLeft());
            checkNoAdditionalNodes(datas, node.getRight());
        }
    }

    /**
     * Every node das a color
     *
     * @param tree tree to check
     */
    private void checkCondition1_hasColor(RedBlackTree<Integer> tree) {
        checkCondition1_hasColor(tree.getRoot());
    }

    /**
     * Every node das a color
     *
     * @param node root of subtree to check
     */
    private void checkCondition1_hasColor(RedBlackNode<Integer> node) {
        if( null != node ) {
            assertNotNull(node.getColor());
            checkCondition1_hasColor(node.getLeft());
            checkCondition1_hasColor(node.getRight());
        }
    }

    /**
     * All data is found in the tree
     *
     * @param datas set of data
     * @param tree tree to check
     */
    private void checkCompleteness(Set<Integer> datas, RedBlackTree<Integer> tree) {
        for( int data: datas ) {
            Assert.assertNotNull( String.format( "numer %s is found", tree.search( data )));
        }
    }

    /**
     * Check that no red node has a red child
     *
     * @param tree tree to check
     */
    private void checkCondition4_redHasNoRedChild(RedBlackTree<Integer> tree) {
        checkCondition4_redHasNoRedChild( tree.getRoot() );
    }

    /**
     * Check that no red node has a red child
     *
     * @param node root of subtree to check
     */
    private void checkCondition4_redHasNoRedChild(RedBlackNode<Integer> node ) {
        RedBlackNode<Integer> leftChild = node.getLeft();
        if( null != leftChild ) {
            if( node.isRed() )
            assertFalse( leftChild.isRed() );
            checkCondition4_redHasNoRedChild( leftChild );
        }

        RedBlackNode<Integer> rightChild = node.getRight();
        if( null != rightChild ) {
            if( node.isRed() ) {
                assertFalse( rightChild.isRed() );
            }
            checkCondition4_redHasNoRedChild( rightChild );
        }
    }

    /**
     * check that black depth is the same on all branches
     *
     * @param tree tree to check
     */
    private void checkCondition5_blackDepth(RedBlackTree<Integer> tree ) {
       checkCondition5_blackDepth(tree.getRoot());
    }

    /**
     * check that black depth is the same on all branches
     *
     * @param node root of subtree to check
     */
    private int checkCondition5_blackDepth(RedBlackNode<Integer> node ) {
       if( null == node ) {
           return 1;
       } else {
           int leftDepth = checkCondition5_blackDepth( node.getLeft() );
           int rightDepth = checkCondition5_blackDepth( node.getRight() );
           Assert.assertTrue( leftDepth == rightDepth );
           if( node.isRed() ) {
               return leftDepth;
           } else {
               return leftDepth + 1;
           }
       }
    }

    /**
     * check that root is black
     *
     * @param tree tree to check
     */
    private void checkCondition2_rootIsBlack(RedBlackTree<Integer> tree) {
        Assert.assertTrue( tree.getRoot().isBlack() );
    }


}