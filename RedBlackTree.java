public class RedBlackTree<V extends Comparable<V>> {
    private Node root;
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        private V value;
        private Node left;
        private Node right;
        private boolean color;

        Node(V value, boolean color) {
            this.value = value;
            this.color = color;
        }
    }

    // (другие методы, включая contains и containsRec)
    public boolean contains(V value){
        Node node = root;
        while (node != null){
            if (node.value.equals(value)){
                return true;
            }
            if (node.value.compareTo(value) > 0) {
                node = node.left;
            }else {
                node = node.right;
            }
        }
        return false;
    }

    //решение рекурсией
    public boolean containsRec(V value) {
        if (root == null){
            return false;
        }
        return containsRec(root, value);
    }

    private boolean containsRec(Node node, V value) {
        if (node.value.equals(value)){
            return true;
        } else {
            if (node.value.compareTo(value) > 0){
                return containsRec(node.left, value);
            } else {
                return containsRec(node.right, value);
            }
        }
    }

    public void insert(V value) {
        root = insert(root, value);
        root.color = BLACK; // Корень всегда черный
    }

    private Node insert(Node node, V value) {
        if (node == null) {
            return new Node(value, RED);
        }

        if (value.compareTo(node.value) < 0) {
            node.left = insert(node.left, value);
        } else if (value.compareTo(node.value) > 0) {
            node.right = insert(node.right, value);
        }

        // Балансировка
        if (isRed(node.right) && !isRed(node.left)) {
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }

        return node;
    }

    private boolean isRed(Node node) {
        if (node == null) {
            return false;
        }
        return node.color == RED;
    }

    private Node rotateLeft(Node node) {
        Node newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;
        newRoot.color = node.color;
        node.color = RED;
        return newRoot;
    }

    private Node rotateRight(Node node) {
        Node newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;
        newRoot.color = node.color;
        node.color = RED;
        return newRoot;
    }

    private void flipColors(Node node) {
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }
}
