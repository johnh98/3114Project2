/**
 * This class represents a generic BST. It holds data and can be traversed.
 * This class also contains methods that allow for the BST to be modified
 * 
 * @author Sam Robles <robleshs>
 * @author John Hoskinson <johnh98>
 * @version 9/21/2019
 *
 * @param <K>
 *            generic type representing the key value
 * @param <V>
 *            generic type representing the value held by the tree
 */
public class BinarySearchTree<K extends Comparable<? super K>, V extends Comparable<? super V>> {
    private KeyNode<K, V> root;


    // ----------------------------------------------------------
    /**
     * Constructs an empty tree.
     */
    public BinarySearchTree() {
        root = null;
    }


    // ----------------------------------------------------------
    /**
     * Insert into the tree. Uses a recursive private method
     *
     * @param x
     *            the item to insert.
     * @param y
     *            the value of the item we are inserting
     */
    public void insert(K x, V y) {
        root = insert(x, y, root);
    }


    // ----------------------------------------------------------
    /**
     * Remove the specified value from the tree. Uses a recursive private method
     * 
     * @param x
     *            the item to remove.
     * @param y
     *            the value of the item we are removing
     */
    public void remove(K x, V y) {
        root = remove(x, y, root);
    }


    // ----------------------------------------------------------
    /**
     * Find the smallest item in the tree.
     *
     * @return The smallest item, or null if the tree is empty.
     */
    public V findMin() {
        return elementAt(findMin(root));
    }


    /**
     * Find the largest item in the tree.
     *
     * @return The largest item in the tree, or null if the tree is empty.
     */
    public V findMax() {
        return elementAt(findMax(root));
    }


    /**
     * Find an item in the tree with repeat keys.
     *
     * @param x
     *            the item to search for.
     * @param y
     *            the value of the item we are searching for
     * @return the matching item or null if not found.
     */
    public V find(K x, V y) {
        return elementAt(find(x, y, root));
    }


    /**
     * Find an item in a tree with singular keys
     * 
     * @param x
     *            the key we are trying to find
     * @return the matching item or null if not found
     */
    public V find(K x) {
        return elementAt(find(x, root));
    }


    /**
     * Indirectly clear all elements in the tree
     * by dereferencing the root.
     */
    public void makeEmpty() {
        root = null;
    }


    /**
     * Test if the tree is cleared
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return root == null;
    }


    /**
     * Returns the root of the tree
     * 
     * @return the root
     */
    public KeyNode<K, V> getRoot() {
        return root;
    }


    // ----------------------------------------------------------
    /**
     * Internal method to get element value stored in a tree node, with safe
     * handling of null nodes.
     *
     * @param node
     *            the node.
     * @return the element field or null if node is null.
     */
    private V elementAt(KeyNode<K, V> node) {
        return (node == null) ? null : node.getValue();
    }


    // ----------------------------------------------------------
    /**
     * Internal method to insert a value into a subtree.
     *
     * @param x
     *            the key of the item to insert.
     * @param y
     *            the value of the item to insert
     * @param node
     *            the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private KeyNode<K, V> insert(K x, V y, KeyNode<K, V> node) {
        if (node == null) {
            return new KeyNode<K, V>(x, y);
        }
        else if (x.compareTo(node.getKey()) < 0 || (x.compareTo(node
            .getKey()) == 0 && y.compareTo(node.getValue()) != 0)) {
            node.setLeft(insert(x, y, node.getLeft()));
        }
        else if (x.compareTo(node.getKey()) > 0) {
            node.setRight(insert(x, y, node.getRight()));
        }
        else {
            throw new DuplicateItemException();
        }
        return node;
    }


    // ----------------------------------------------------------
    /**
     * Internal method to remove a specified item from a subtree.
     *
     * @param x
     *            the key value of the item to remove.
     * @param y
     *            the item to remove
     * @param node
     *            the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private KeyNode<K, V> remove(K x, V y, KeyNode<K, V> node) {
        // This local variable will contain the new root of the subtree,
        // if the root needs to change.
        KeyNode<K, V> result = node;

        // If there's no more subtree to examine
        if (node == null) {
            throw new ItemNotFoundException();
        }

        // if value should be to the left of the root
        if (x.compareTo(node.getKey()) < 0 || (x.compareTo(node.getKey()) == 0
            && y.compareTo(node.getValue()) != 0)) {
            node.setLeft(remove(x, y, node.getLeft()));
        }
        // if value should be to the right of the root
        else if (x.compareTo(node.getKey()) > 0) {
            node.setRight(remove(x, y, node.getRight()));
        }
        // If value is on the current node
        else {
            // If there are two children
            if (node.getLeft() != null && node.getRight() != null) {
                // A temporary node to hold the values that need to get moved
                KeyNode<K, V> temp = findMin(node.getRight());
                node.setValue(temp.getValue());
                node.setRight(remove(temp.getKey(), temp.getValue(), node
                    .getRight()));
            }
            // If there is only one child on the left
            else if (node.getLeft() != null) {
                result = node.getLeft();
            }
            // If there is only one child on the right
            else {
                result = node.getRight();
            }
        }
        return result;
    }


    // ----------------------------------------------------------
    /**
     * Internal method to find the smallest item in a subtree.
     *
     * @param node
     *            the node that roots the tree.
     * @return node containing the smallest item.
     */
    private KeyNode<K, V> findMin(KeyNode<K, V> node) {
        if (node == null) {
            return node;
        }
        // If the node has no left children it is the smallest in the tree
        else if (node.getLeft() == null) {
            return node;
        }
        else {
            return findMin(node.getLeft());
        }
    }


    // ----------------------------------------------------------
    /**
     * Internal method to find the largest item in a subtree.
     *
     * @param node
     *            the node that roots the tree.
     * @return node containing the largest item.
     */
    private KeyNode<K, V> findMax(KeyNode<K, V> node) {
        if (node == null) {
            return node;
        }
        // If a node has no right children it is the largest value
        else if (node.getRight() == null) {
            return node;
        }
        else {
            return findMax(node.getRight());
        }
    }


    // ----------------------------------------------------------
    /**
     * Internal method to find an item in a subtree where keys are repeated.
     *
     * @param x
     *            is item to search for.
     * @param y
     *            value of item we're seraching for
     * @param node
     *            the node that roots the tree.
     * @return node containing the matched item.
     */
    private KeyNode<K, V> find(K x, V y, KeyNode<K, V> node) {
        if (node == null) {
            return null; // Not found
        }
        else if (x.compareTo(node.getKey()) < 0 || (x.compareTo(node
            .getKey()) == 0 && y.compareTo(node.getValue()) != 0)) {
            // Search in the left subtree
            return find(x, y, node.getLeft());
        }
        else if (x.compareTo(node.getKey()) > 0) {
            // Search in the right subtree
            return find(x, y, node.getRight());
        }
        else {
            return node; // Match
        }
    }


    /**
     * Internal method to find an item in a subtree where keys aren't repeated
     *
     * @param x
     *            is item to search for.
     * @param node
     *            the node that roots the tree.
     * @return node containing the matched item.
     */
    private KeyNode<K, V> find(K x, KeyNode<K, V> node) {
        if (node == null) {
            return null; // Not found
        }
        else if (x.compareTo(node.getKey()) < 0) {
            // Search in the left subtree
            return find(x, node.getLeft());
        }
        else if (x.compareTo(node.getKey()) > 0) {
            // Search in the right subtree
            return find(x, node.getRight());
        }
        else {
            return node; // Match
        }
    }


    /**
     * Uses copyTraverse() to allow a user class to create a copy without node
     * access
     * 
     * @param copyArr
     *            is the array to copy data into
     */
    public void makeCopy(V[] copyArr) {
        int i = 0;
        copyTraverse(copyArr, i, root);
    }


    /**
     * Recursively creates an array representation of the data in the tree.
     * 
     * @param copyArr
     *            is the array to place the data in
     * @param num
     *            is the current index to place at
     * @param rootNode
     *            is the node to center the recursion at
     */
    private void copyTraverse(V[] copyArr, int num, KeyNode<K, V> rootNode) {
        if (rootNode.getLeft() != null) {
            copyTraverse(copyArr, num, rootNode.getLeft());
        }
        copyArr[num] = rootNode.getValue();
        num++;
        if (rootNode.getRight() != null) {
            copyTraverse(copyArr, num, rootNode.getRight());
        }
    }


    /**
     * Gets an in-order string representation of the tree for testing
     * 
     * @return an in-order string representation of the tree
     */
    @Override
    public String toString() {
        if (root == null) {
            return "";
        }
        else {
            return (root.toString() + '\n');
        }
    }
}
