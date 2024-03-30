import java.util.List;

public class Node {
    private Node leftChild;
    private Node rightSibling;
    private String product;
    private Integer count;
    private Node next;


    public void PrintNode(Node node, int depth) {
        if(node == null)
            return;

        for (int i = 0; i < depth; i++) {
            System.out.print("--");
        }

        System.out.printf("%d : [%s] - %d\n", depth, node.product, node.count);

        if(node.leftChild != null)
            PrintNode(node.leftChild, depth+1);

        if(node.rightSibling != null)
            PrintNode(node.rightSibling, depth);
    }


    // Constructor
    Node() {
        leftChild = null;
        rightSibling = null;
        product = null;
        count = 0;
        next = null;
    }

    Node(String p, int n) {
        leftChild = null;
        rightSibling = null;
        product = p;
        count = n;
        next = null;
    }


    // basic method
    void SetLeftChild(Node lc) { leftChild = lc; }
    Node GetLeftChild() { return leftChild; }
    void SetRightSibling(Node rs) { rightSibling = rs; }
    Node GetRightSibling() { return rightSibling; }
    void SetProduct(String s) { product = s; }
    String GetProduct() { return product; }
    void SetCount(Integer d) {
        count = d;
    }
    Integer GetCount() { return count; }
    void SetNext(Node n) {
        next = n;
    }
    Node GetNext() { return next; }
}
