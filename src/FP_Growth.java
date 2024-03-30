import java.util.*;

public class FP_Growth {
    private HashMap<String, Integer> FreqList;
    private HashMap<String, Node> FP_List;
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

        List<String> keyList = new ArrayList<>(FreqList.keySet());
        keyList.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return FreqList.get(o2).compareTo(FreqList.get(o1));
            }
        });

        FP_List = new HashMap<>();
        for (String s : keyList) { FP_List.put(s, null); }

        file.Reload(filePath);
    }

    public void Construct_FPTree() {
        String buf;
        while((buf = file.readLine()) != null) {
            HashSet<String> goods = new HashSet<>(List.of(buf.split(",")));
            List<String> lsFreq = new ArrayList<>();
            for (String s : FP_List.keySet()) {
                if(goods.contains(s)) {
                    lsFreq.add(s);
                }
            }

            if(lsFreq.size() > 0)
                root.InsertNode(lsFreq, FP_List);
        }
    }

    // Just for debugging
    public void Print_FPList() {
        int i = 0;
        for (String s : FP_List.keySet()) {
            System.out.printf("[%d] : %s\n", i, s);
            Node now = FP_List.get(s);

            while(now != null) {
                System.out.printf("[%s] %d\n", now.GetProduct(), now.GetCount());
                now = now.GetNext();
            }
            System.out.println("-----------\n\n");
            i++;
        }
    }

    /* 나아아아중에 FPTree를 분석하는 코드를 하나에 넣을거임
    public HashMap<String, Integer> MineFPTree() {
        FormCPB();
    }
     */



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
        System.out.println(Arrays.toString(FP_List.keySet().toArray()));
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
