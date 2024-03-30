import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.HashMap;


public class FP_Tree {
    private Node Child;
    private Node find;

    // HT = Header Table
    public void InsertNode(List<String> lsFreq, HashMap<String, Node> HT) {
        Node parent = Child;
        Node now = Child.GetLeftChild();
        List<String> cmptor = new ArrayList<>(HT.keySet());
        System.out.println(lsFreq); // Erase
        for (String s : lsFreq) {
            if(Child.GetLeftChild() == null) { // 트리에 아무것도 없음
                Node buf = new Node(s);
                Child.SetLeftChild(buf);
                AddHashTable(HT, buf);
                parent = buf;
                now = buf.GetLeftChild();
            } else if (now == null) {
                Node buf = new Node(s);
                AddHashTable(HT, buf);
                parent.SetLeftChild(buf);
                parent = buf;
                now = buf.GetLeftChild();
            } else {
                int freqNum = cmptor.indexOf(s);
                if(freqNum < cmptor.indexOf(now.GetProduct())) { // 맨 처음 child의 위치에 삽입 (parent 관계도 수정해야함)
                    Node buf = new Node(s);
                    buf.SetRightSibling(now);
                    parent.SetLeftChild(buf);
                    AddHashTable(HT, buf);
                    parent = buf;
                    parent = buf.GetLeftChild();
                } else {
                    while(freqNum > cmptor.indexOf(now.GetProduct()) && now.GetRightSibling() != null) {
                        now = now.GetRightSibling();
                    }

                    if(freqNum == cmptor.indexOf(now.GetProduct())) {
                        now.SetCount(now.GetCount() + 1);
                        parent = now;
                        now = parent.GetLeftChild();
                    } else if(now.GetRightSibling() == null) {
                        Node buf = new Node(s);
                        AddHashTable(HT, buf);
                        now.SetRightSibling(buf);
                        parent = buf;
                        now = buf.GetLeftChild();
                    } else {
                        Node buf = new Node(s);
                        buf.SetRightSibling(now.GetRightSibling());
                        now.SetRightSibling(buf);
                        AddHashTable(HT, buf);
                        parent = buf;
                        now = buf.GetLeftChild();
                    }
                }
            }
        }
    }

    public void AddHashTable(HashMap<String, Node> HT, Node FPTNode) {
        String product = FPTNode.GetProduct();
        Node now = HT.get(product);

        if(now == null) {
            HT.replace(product, FPTNode);
            return;
        }

        while(now.GetNext() != null) {
            now = now.GetNext();
        }

        now.SetNext(FPTNode);
    }

    public void Print_FPTree() {
        Child.PrintNode(Child, 0);
    }


    // Constructor
    FP_Tree() {
        Child = new Node();
        find = null;
    }

    // Basic
    Node GetChild() {
        return Child;
    }

}
