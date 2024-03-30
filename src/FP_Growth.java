import java.text.Normalizer;
import java.util.*;

public class FP_Growth {
    private HashMap<String, Integer> FreqList;
    private List<String> FP_List;
    private int objectNumber;
    private float threshold;
    final private CSV file;
    private final FP_Tree root;
    private final String filePath;


    // Construct FP List
    public void Construct_FPList() {
        objectNumber = 0;

        String buf;

        while((buf = file.readLine()) != null) {
            String[] goods = buf.split(",");
            for (String s : goods) {
                if(!FreqList.containsKey(s)) {
                    FreqList.put(s, 1);
                } else FreqList.replace(s, FreqList.get(s) + 1);
            }
            objectNumber++;
        }

        threshold *= objectNumber;

        FreqList.keySet().removeIf(s -> FreqList.get(s) <= threshold);

        FP_List = new ArrayList<>(FreqList.keySet());
        FP_List.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return FreqList.get(o2).compareTo(FreqList.get(o1));
            }
        });

        System.out.println(FP_List); // Erase

        root.Construct_HT(FP_List.size());

        file.Reload(filePath);
    }

    public void Construct_FPTree() {
        String buf;
        //int i = 0; // Erase
        while((buf = file.readLine()) != null) {
            HashSet<String> goods = new HashSet<>(List.of(buf.split(",")));
            List<String> lsFreq = new ArrayList<>();
            for (String s : FP_List) {
                if(goods.contains(s)) {
                    lsFreq.add(s);
                }
            }

            //if(i == 11) // Erase
              //  System.out.println(lsFreq); // Erase

            if(!lsFreq.isEmpty())
                root.InsertNode(lsFreq, FP_List);

            //System.out.println(i); // Erase
            //Print_FPTree(); // Erase

            //System.out.println(); // Erase
           // i++; // Erase
        }
    }

    // Just for debugging
    public void Print_FPList() {
        int i = 0;
        for (String s : FP_List) {
            System.out.printf("[%d] : %s\n", i, s);
            i++;
        }
    }

    /* 나아아아중에 FPTree를 분석하는 코드를 하나에 넣을거임
    public HashMap<String, Integer> MineFPTree() {
        FormCPB("", root.GetChild());
    }
     */

    public void CheckFormCPB() {
        HashMap<String, Integer> buf = new HashMap<>();
        for (String s : FP_List) {
            FormCPB(s, "", root.GetChild().GetLeftChild(), buf); //


        }
    }

    public void FormCPB(String aim, String CPB, Node now, HashMap<String, Integer> CPBMap ) {
        if(now == null) return;

        if(now.GetLeftChild() != null) FormCPB(aim, CPB + "," + now.GetProduct(), now.GetLeftChild(), CPBMap);

        if(Objects.equals(now.GetProduct(), aim)) {
            CPBMap.put(CPB, now.GetCount());
            return;
        }

        if(now.GetRightSibling() != null) FormCPB(aim, CPB, now.GetRightSibling(), CPBMap);
    }

    // Just for debugging
    public void Print_FPTree() {
        root.GetChild().PrintNode(root.GetChild(), 0);
    }

    //Just for debugging
    void Print_HashMap()
    {
        int i = 0;
        for (String s : FreqList.keySet()) {
            System.out.printf("[%d] : %s = %d\n", i, s, FreqList.get(s));
            i++;
        }

        System.out.printf("\nThe number of Object is %d\n\n", objectNumber);
        System.out.println("FP List is below");
        System.out.println(FP_List);
    }

    // Constructor
    FP_Growth(String _filePath, float MSV) {
        root = new FP_Tree();
        filePath = _filePath;
        file = new CSV(filePath);
        FreqList = new HashMap<>();
        threshold = MSV;
    }
}
