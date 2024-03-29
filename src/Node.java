import java.util.List;

public class Node {
    private List<Node> child;
    private String product;
    private Integer count;
    private Node next;




    // Constructor
    Node() {
        child = null;
        product = null;
        count = 0;
        next = null;
    }

    Node(String p) {
        child = null;
        product = p;
        count = 1;
        next = null;
    }


    // basic method
    void SetChild(List<Node> c) { child = c; }
    List<Node> GetLeftChild() { return child; }
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
