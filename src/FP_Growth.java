import java.util.*;

public class FP_Growth {
    private final HashMap<String, Integer> FreqList; // result table(HashMap)
    // We memorize frequent itemSet in FreqList
    private List<String> FP_List; // the Order of Frequent item
    private int objectNumber; // the whole number of Object
    private float threshold; // if itemset is frequent, support count must be over threshold
    final private CSV file; // using CSV Class, we open groceries.csv file
    private final FP_Tree root; // root node of FP_Tree, we use FP_Tree class
    private final String filePath; // the filepath of groceries.csv

    /*
    Explanation of FP_Growth class' method

    Construct_FPList() : scan csv file and count each product's support count.
    based on that, method create Frequent order(FP_List) and put it on FP_List variable(List<String>)
    the first step of FP-Growth Alg

    Construct_FPTree() : scan csv file again and create FP_Tree based on FP_List.

    Mine_FPTree() : by using FP_List and root(FP_Tree), mining the tree and get frequent itemSet.
    in this method, using other 2 methods.
    1. FormCPB(String, String, Node, HashMap<>) : by scanning FP_Tree, collect Prefix path(conditional pattern base) of aim item.
    2. InsertFreqSet(String, String, Node) : by scanning conditional FP_Tree, find frequent itemSet.

    Print_Support() : print FreqList in order. this method is for the assignment.

    If you want to check the method's code, just find by Ctrl+f

    Warning!!
    There is error for constructing this Alg. In Mine_FPTree() method, I can't scan fully FP_Tree completely.
    Example 1.
    If conditional patter base is {f, a, b : 3} and {f, c, d : 2}, my code just add this pattern to new FP_Tree
    and extract frequent item of tree's node. it's not the way that FP-Growth Alg do. my code is Garbage code! Plz fix it!
     */



    // Construct FP List
    // The first step of FP-Growth Alg
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

        FP_List.sort((o1, o2) -> FreqList.get(o2).compareTo(FreqList.get(o1)));

        System.out.println(FreqList); // Erase

        System.out.println(FP_List); // Erase

        root.Construct_HT(FP_List.size());

        file.Reload(filePath);
    }

    // based on FP_List, scan csv file again to create FP_Tree
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

            if(!lsFreq.isEmpty())
                root.InsertNode(lsFreq, FP_List, 1);
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

    // Mine FPTree and get the frequent itemsSet.
    // In this method, using other 2 methods
    // 1. FormCPB(String, String, Node, HashMap<>) : by scanning FP_Tree, collect Prefix path(conditional pattern base) of aim item.
    // 2. InsertFreqSet(String, String, Node) : by scanning conditional FP_Tree, find frequent itemSet.
    public void MineFPTree(FP_Tree tree, List<String> itemList, String freqItemSet) {
        if(tree.GetChild() == null || itemList.isEmpty())
        {
            System.out.println("End of Mining");
            return;
        }

        for (String s : itemList) {
            HashMap<String, Integer> buf = new HashMap<>();

            // Construct Conditional Pattern Base
            FormCPB(s, "", tree.GetChild().GetLeftChild(), buf);

            // Conditional Pattern Tree
            FP_Tree FPT = new FP_Tree();
            FPT.Construct_HT(itemList.size());

            // Construct conditional FP Tree
            List<String> _ItemList = new ArrayList<>();
            for (String bufs : buf.keySet()) {
                List<String> Tbuf = new ArrayList<>(List.of(bufs.split(",")));
                Tbuf.remove("");
                Tbuf.sort(new FreqComparator(FP_List));

                for (String string : Tbuf) {
                    if(!_ItemList.contains(string))
                        _ItemList.add(string);
                }
                FPT.InsertNode(Tbuf, FP_List, buf.get(bufs));
            }

            _ItemList.sort(new FreqComparator(FP_List));
            // It's ok until now.

            for (String item : _ItemList) {
                if(FPT.GetItemMap().get(item) > threshold) {
                    if(List.of(freqItemSet.split(",")).contains(item))
                        continue;
                    String FIS;
                    if(freqItemSet.isEmpty())
                        FIS = item + ", " + s;
                    else
                        FIS = freqItemSet + ", " + item + ", " + s;

                    FreqList.put(FIS, FPT.GetCount(FP_List.indexOf(item)));
                    FPT.Print_FPTree();
                    System.out.printf("MineFPTree is called with [s]:%s in [item]%s\nFIS is %s\n", s, item, FIS);
                    if(FPT.GetItemMap().size() > 1)
                        MineFPTree(FPT, _ItemList, FIS);
                }
            }
            //FPT.Print_FPTree(); // Erase
            //FPT.Print_ItemMap(); //Erase
        }
    }

    // Just for debugging
        /*
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
     */

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

    // Basic
    public float GetThreshold() {
        return threshold;
    }

    public FP_Tree GetFP_Tree() {
        return root;
    }

    public List<String> GetFP_List() {
        return FP_List;
    }
}
