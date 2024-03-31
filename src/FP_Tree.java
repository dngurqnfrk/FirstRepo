import java.util.*;


public class FP_Tree {
    private final Node Child;
    private List<Node> HT;
    private final HashMap<String, Integer> itemMap;

    // HT = Header Table
    public void InsertNode(List<String> lsFreq, List<String> cmptor, int n) {
        Node parent = Child;
        Node now = Child.GetLeftChild();

        for (String s : lsFreq) {
            itemMap.put(s, itemMap.getOrDefault(s, 0) + n);
            if (now == null) {
                Node buf = new Node(s, n);
                parent.SetLeftChild(buf);
                AddHashTable(cmptor.indexOf(s), buf);
                parent = buf;
                now = buf.GetLeftChild();
            } else {
                int freqNum = cmptor.indexOf(s);
                if (freqNum < cmptor.indexOf(now.GetProduct())) { // 맨 처음 child의 위치에 삽입 (parent 관계도 수정해야함)
                    Node buf = new Node(s, n);
                    buf.SetRightSibling(now);
                    parent.SetLeftChild(buf);
                    AddHashTable(cmptor.indexOf(s), buf);
                    parent = buf;
                    now = buf.GetLeftChild();
                } else {
                    while (now.GetRightSibling() != null && freqNum >= cmptor.indexOf(now.GetRightSibling().GetProduct())) {
                        now = now.GetRightSibling();
                    }

                    if (freqNum == cmptor.indexOf(now.GetProduct())) {
                        now.SetCount(now.GetCount() + n);
                        parent = now;
                        now = parent.GetLeftChild();
                    } else if (now.GetRightSibling() == null) {
                        Node buf = new Node(s, n);
                        AddHashTable(cmptor.indexOf(s), buf);
                        now.SetRightSibling(buf);
                        parent = buf;
                        now = buf.GetLeftChild();
                    } else {
                        Node buf = new Node(s, n);
                        buf.SetRightSibling(now.GetRightSibling());
                        now.SetRightSibling(buf);
                        AddHashTable(cmptor.indexOf(s), buf);
                        parent = buf;
                        now = buf.GetLeftChild();
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

    public int GetCount(int index) {
        Node now = HT.get(index);
        int sum = 0;

        while(now != null) {
            sum += now.GetCount();
            now = now.GetNext();
        }

        return sum;
    }

    /*
    public void Print_ItemMap() {
        for (String s : itemMap.keySet()) {
            System.out.printf("%s : %d\n", s, itemMap.get(s));
        }
    }
*/
    public void Print_FPTree() {
        Child.PrintNode(Child, 0);
    }

    // Constructor
    FP_Tree() {
        Child = new Node();
        HT = null;
        itemMap = new HashMap<>();
    }

    // Basic
    Node GetChild() {
        return Child;
    }

    public void Construct_HT(int n) {
        HT = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            HT.add(null);
        }
    }

    public HashMap<String, Integer> GetItemMap() {
        return itemMap;
    }
}
