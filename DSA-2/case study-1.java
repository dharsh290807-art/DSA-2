import java.util.*;

class AVLNode {
    int key;
    AVLNode left, right;
    int height;

    AVLNode(int key) {
        this.key = key;
        this.height = 1;
    }
}

class AVLTree {

    int height(AVLNode node) {
        return (node == null) ? 0 : node.height;
    }

    int getBalance(AVLNode node) {
        return (node == null) ? 0 :
                height(node.left) - height(node.right);
    }

    AVLNode rightRotate(AVLNode y) {

        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left),
                            height(y.right)) + 1;

        x.height = Math.max(height(x.left),
                            height(x.right)) + 1;

        return x;
    }

    AVLNode leftRotate(AVLNode x) {

        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left),
                            height(x.right)) + 1;

        y.height = Math.max(height(y.left),
                            height(y.right)) + 1;

        return y;
    }

    AVLNode insert(AVLNode node, int key) {

        if (node == null)
            return new AVLNode(key);

        if (key < node.key)
            node.left = insert(node.left, key);

        else if (key > node.key)
            node.right = insert(node.right, key);

        else
            return node;

        node.height =
            1 + Math.max(height(node.left),
                         height(node.right));

        int balance = getBalance(node);

        // LL
        if (balance > 1 &&
            key < node.left.key)
            return rightRotate(node);

        // RR
        if (balance < -1 &&
            key > node.right.key)
            return leftRotate(node);

        // LR
        if (balance > 1 &&
            key > node.left.key) {

            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL
        if (balance < -1 &&
            key < node.right.key) {

            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    void inorder(AVLNode root) {
        if (root != null) {
            inorder(root.left);
            System.out.print(root.key + " ");
            inorder(root.right);
        }
    }
}

public class Main {

    public static void main(String[] args) {

        AVLTree tree = new AVLTree();
        AVLNode root = null;

        int[] appointments = {
            30, 35, 40, 45, 50,
            60, 65, 70, 75, 80,
            85, 90
        };

        for (int id : appointments)
            root = tree.insert(root, id);

        System.out.println(
            "Inorder Traversal of AVL Tree:"
        );

        tree.inorder(root);
    }
}