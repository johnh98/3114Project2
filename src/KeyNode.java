
/**
 * This class represents a node in a BST. It holds information (such as left and
 * right neighbors)
 * and can be modified
 * 
 * @author Sam Robles <robleshs>
 * @author John Hoskinson <johnh98>
 * @version 9/21/19
 * 
 * @param <K>
 *            The key being held in the node
 * 
 * @param <V>
 *            the value being held in the node
 */
public class KeyNode<K, V> {
    private K key;
    private V value;
    private KeyNode<K, V> left;
    private KeyNode<K, V> right;


    /**
     * Constructor, creates a node with no children.
     * 
     * @param valueValue
     *            the type-less value to store in this node.
     * @param keyValue
     *            The type-less value stored in the key
     */
    KeyNode(K keyValue, V valueValue) {
        value = valueValue;
        key = keyValue;
        left = null;
        right = null;
    }


    /**
     * Get the current data value stored in this node.
     * 
     * @return the value
     */
    public V getValue() {
        return value;
    }


    /**
     * Get the current value of the key in this node
     * 
     * @return the key of the node
     */
    public K getKey() {
        return key;
    }


    /**
     * Set the data value stored in this node.
     * 
     * @param newValue
     *            the new data value to set
     */
    public void setValue(V newValue) {
        value = newValue;
    }


    /**
     * Set the value of the key stored in thsi node
     * 
     * @param newKey
     *            The new key being stored
     */
    public void setKey(K newKey) {
        key = newKey;
    }


    /**
     * Get the left child of this node.
     * 
     * @return a reference to the left child.
     */
    public KeyNode<K, V> getLeft() {
        return left;
    }


    // ----------------------------------------------------------
    /**
     * Set this node's left child.
     * 
     * @param valueHeld
     *            the node to point to as the left child.
     */
    public void setLeft(KeyNode<K, V> valueHeld) {
        left = valueHeld;
    }


    // ----------------------------------------------------------
    /**
     * Get the right child of this node.
     * 
     * @return a reference to the right child.
     */
    public KeyNode<K, V> getRight() {
        return right;
    }


    // ----------------------------------------------------------
    /**
     * Set this node's right child.
     * 
     * @param valueHeld
     *            the node to point to as the right child.
     */
    public void setRight(KeyNode<K, V> valueHeld) {
        right = valueHeld;
    }


    /**
     * Provides an in-order representation of the tree
     * 
     * @return a string representation of the tree in list form
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (left != null) {
            builder.append(left.toString() + "\n");
        }
        builder.append(value.toString());
        if (right != null) {
            builder.append("\n" + right.toString());
        }
        return builder.toString();
    }
}
