import java.util.*;

public class FP_Growth {
    private final HashMap<String, Integer> FreqList; // result table(HashMap)
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

        System.out.println(FreqList); // Erase

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
                root.InsertNode(lsFreq, FP_List, 1);

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

    public void MineFPTree() {
        for (String s : FP_List) {
            System.out.println("For item : " + s); // Erase

            HashMap<String, Integer> buf = new HashMap<>();

            // Construct Conditional Pattern Base
            FormCPB(s, "", root.GetChild().GetLeftChild(), buf);

            // Conditional Pattern Tree
            FP_Tree FPT = new FP_Tree();
            FPT.Construct_HT(FP_List.size());

            // Construct conditional FP Tree
            for (String bufs : buf.keySet()) {
                List<String> Tbuf = new ArrayList<>(List.of(bufs.split(",")));
                Tbuf.remove("");
                System.out.printf("%s : %s\n", bufs, Tbuf);
                Tbuf.sort(new FreqComparator(FP_List));

                FPT.InsertNode(Tbuf, FP_List, buf.get(bufs));
            }

            FPT.Print_FPTree();

            InsertFreqSet(s, "", FPT.GetChild().GetLeftChild());
        }
    }

    // Just for debugging
    public void CheckFormCPB() {
        System.out.println("Threshold is " + threshold);
        HashMap<String, Integer> buf = new HashMap<>();
        for (String s : FP_List) {
            FormCPB(s, "", root.GetChild().GetLeftChild(), buf);
            System.out.println(buf);
        }

        for (String s : buf.keySet()) {
            System.out.printf("%s\n-- %d", s, buf.get(s));
        }
    }

    public void FormCPB(String aim, String CPB, Node now, HashMap<String, Integer> CPBMap) {
        if(now == null) return;

        if(Objects.equals(now.GetProduct(), aim) && !CPB.isEmpty()) {
            CPBMap.put(CPB, now.GetCount());
            return;
        } else if(Objects.equals(now.GetProduct(), aim) && CPB.isEmpty()) {
            return;
        }

        if(now.GetLeftChild() != null && !CPB.isEmpty()) FormCPB(aim, CPB + "," + now.GetProduct(), now.GetLeftChild(), CPBMap);
        else if(now.GetLeftChild() != null && CPB.isEmpty()) FormCPB(aim, now.GetProduct(), now.GetLeftChild(), CPBMap);

        if(now.GetRightSibling() != null) FormCPB(aim, CPB, now.GetRightSibling(), CPBMap);
    }

    // Insert itemSet over the support count of threshold.
    public void InsertFreqSet(String item, String pathSet, Node now) {
        if(now == null) return;

        if(now.GetCount() >= threshold && !pathSet.isEmpty()) {
            FreqList.put(pathSet + ", " + now.GetProduct() + ", " + item, now.GetCount());
        } else if(now.GetCount() >= threshold && pathSet.isEmpty()) {
            FreqList.put(now.GetProduct() + ", " + item, now.GetCount());
        }

        if(now.GetLeftChild() != null && !pathSet.isEmpty()) InsertFreqSet(item, pathSet + ", " + now.GetProduct(), now.GetLeftChild());
        else if(now.GetLeftChild() != null && pathSet.isEmpty()) InsertFreqSet(item, now.GetProduct(), now.GetLeftChild());

        if(now.GetRightSibling() != null) InsertFreqSet(item, pathSet, now.GetLeftChild());
    }

    // Print all the Frequent itemSet and its support
    public void Print_Support() {
        PriorityQueue<Map.Entry<String, Integer>> bufPQ = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        bufPQ.addAll(FreqList.entrySet());

        while(!bufPQ.isEmpty()) {
            Map.Entry<String, Integer> buf = bufPQ.remove();

            System.out.println(buf.getKey() + " " + (float)buf.getValue() / objectNumber);
        }
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
