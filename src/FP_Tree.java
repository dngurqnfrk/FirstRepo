import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.HashMap;


public class FP_Tree {
    Node Child;

    // HT = Header Table
    public void InsertNode(List<String> lsFreq, HashMap<String, Node> HT) {
        Node now = Child;
        Node child = Child;
        int i = 0;
        List<String> Comparator = new ArrayList<>(HT.keySet());
        for (String s : lsFreq) {
            if(i == 0 && Child == null) {

            }
        }
    }

    FP_Tree() {
        Child = null;
    }

}
