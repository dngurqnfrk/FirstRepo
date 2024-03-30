import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.HashMap;


public class FP_Tree {
    private Node Child;
    private Node find;

    // HT = Header Table
    public void InsertNode(List<String> lsFreq, HashMap<String, Node> HT) {
        Node parent = null;
        Node now = Child;
        List<String> cmptor = new ArrayList<>(HT.keySet());
        for (String s : lsFreq) {
            if(Child == null) {
                Child = new Node(s);
                now = Child.GetLeftChild();
                parent = Child;
            } else if (now == null) {
                Node buf = new Node(s);
                parent.SetLeftChild(buf);
                parent = buf;
                now = buf.GetLeftChild();
            } else {
                int freqNum = cmptor.indexOf(s);
                if(freqNum < cmptor.indexOf(now.GetProduct())) {
                    Node buf = new Node(s);
                    buf.SetRightSibling(now);
                    now = buf;
                    parent = buf;
                } else {
                    while(freqNum > cmptor.indexOf(now.GetProduct()) && now.GetRightSibling() != null) {
                        now = now.GetRightSibling();
                    }

                    if(now.GetRightSibling() == null) {
                        Node buf = new Node(s);
                        now.SetRightSibling(buf);
                        parent = buf;
                        now = buf.GetLeftChild();
                    }
                }
            }
        }
    }

    public void Print_FPTree() {
        Child.PrintNode(Child, 0);
    }


    // Constructor
    FP_Tree() {
        Child = null;
        find = null;
    }

    // Basic
    Node GetChild() {
        return Child;
    }

}
