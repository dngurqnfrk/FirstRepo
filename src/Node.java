public class Node {
    private Node leftChild;
    private Node rightSibling;
    private String product;
    private Integer count;
    private Node next;




    // Constructor
    Node() {
        leftChild = null;
        rightSibling = null;
        product = null;
        count = 0;
        next = null;
    }

    //
    void SetLeftChild(Node left) {
        leftChild = left;
    }

    void SetRightSibling(Node right) {
        rightSibling = right;
    }

    void SetProduct(String s) { product = s; }

    void SetCount(Integer d) {
        count = d;
    }

    void SetNext(Node n) {
        next = n;
    }
}
