import java.util.*;


public class FP_Tree {
    private final Node Child;
    private List<Node> HT;
    int depth;

    // HT = Header Table
    public void InsertNode(List<String> lsFreq, List<String> cmptor, int n) {
        Node parent = Child;
        Node now = Child.GetLeftChild();

        for (String s : lsFreq) {
            if (now == null) {
                Node newNode = new Node(s, n);
                parent.SetLeftChild(newNode);
                newNode.SetParent(parent);
                AddHashTable(cmptor.indexOf(s), newNode);
                parent = newNode;
                now = newNode.GetLeftChild();
            } else {
                int freqNum = cmptor.indexOf(s);
                if (freqNum < cmptor.indexOf(now.GetProduct())) { // 맨 처음 child의 위치에 삽입 (parent 관계도 수정해야함)
                    Node newNode = new Node(s, n);
                    newNode.SetRightSibling(now);
                    parent.SetLeftChild(newNode);
                    newNode.SetParent(parent);
                    AddHashTable(cmptor.indexOf(s), newNode);
                    parent = newNode;
                    now = newNode.GetLeftChild();
                } else {
                    while (now.GetRightSibling() != null && freqNum >= cmptor.indexOf(now.GetRightSibling().GetProduct())) {
                        now = now.GetRightSibling();
                    }

                    if (freqNum == cmptor.indexOf(now.GetProduct())) {
                        now.SetCount(now.GetCount() + n);
                        parent = now;
                        now = parent.GetLeftChild();
                    } else if (now.GetRightSibling() == null) {
                        Node newNode = new Node(s, n);
                        AddHashTable(cmptor.indexOf(s), newNode);
                        now.SetRightSibling(newNode);
                        newNode.SetParent(parent);
                        parent = newNode;
                        now = newNode.GetLeftChild();
                    } else {
                        Node newNode = new Node(s, n);
                        newNode.SetRightSibling(now.GetRightSibling());
                        now.SetRightSibling(newNode);
                        newNode.SetParent(parent);
                        AddHashTable(cmptor.indexOf(s), newNode);
                        parent = newNode;
                        now = newNode.GetLeftChild();
                    }
                }
            }
        }
    }

    public void AddHashTable(int index, Node FPTNode) {
        Node now = HT.get(index);

        if (now == null) {
            HT.set(index, FPTNode);
            return;
        }

        while (now.GetNext() != null) {
            now = now.GetNext();
        }

        now.SetNext(FPTNode);
    }

    public Node GetItemNode(int index) {
        return HT.get(index);
    }

    public int GetCount(int index) {
        Node now = HT.get(index);
        int sum = 0;

        while(now != null) {
            sum += now.GetCount();
            now = now.GetNext();
        }

        return sum;
    }

    public boolean isSingleTree() {
        if(Child.GetLeftChild() == null)
            return false;
        Node CandidateNode = Child.GetLeftChild();
        do {
            if(CandidateNode.GetRightSibling() != null)
                return false;

            CandidateNode = CandidateNode.GetLeftChild();
        } while(CandidateNode != null);
        return true;
    }


    public void Print_FPTree() {
        Child.PrintNode(Child, 0);
    }

    // Constructor
    FP_Tree(int n) {
        Child = new Node();
        HT = null;
        depth = n;
    }

    // Basic
    Node GetChild() {
        return Child;
    }

    int GetDepth() {
        return depth;
    }

    public void Construct_HT(int n) {
        HT = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            HT.add(null);
        }
    }
}
