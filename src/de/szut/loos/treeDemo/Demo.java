package de.szut.loos.treeDemo;

/**
 * Demonstration of BinrayTree demo implementation
 */
public class Demo {
    public static void main(String args[]) {
        BinTree<String> tree = new BinTree<>();
        tree.insert("r");
        tree.insert("a");
        tree.insert("b");
        tree.insert("t");
        tree.insert("v");
        tree.insert("u");
        tree.insert("s");

        System.out.println(tree);

        Node<String> root = tree.getRoot();
        Node<String> node = root.getRight();
        node.delete(); // t löschen

        System.out.println(tree);

        System.out.println(tree.search("t"));
        System.out.println(tree.search("s"));
    }
}
