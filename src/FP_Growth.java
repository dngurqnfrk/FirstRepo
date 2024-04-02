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

        if (tree.GetChild() == null || itemList.isEmpty()) {
            System.out.println("End of Mining");
            return;
        }

        for (String s : itemList) {
            if(Objects.equals(itemList, FP_List)) {
                System.out.println("It's for " + s);
                System.out.println("It's for " + s);
                System.out.println("It's for " + s);
            }
            // 혹시나 해서
            if (List.of(freqItemSet.split(",")).contains(s))
                continue;

            if (tree.GetCount(FP_List.indexOf(s)) < threshold)
                continue;

            HashMap<String, Integer> buf = new HashMap<>();

            // Construct Conditional Pattern Base (CPB) (CPB is stored in buf)
            FormCPB(s, "", tree.GetChild().GetLeftChild(), buf);

            System.out.println("CPB is below, [s] : " + s);
            System.out.println(buf);

            // Conditional Pattern Tree
            FP_Tree FPT = new FP_Tree();
            FPT.Construct_HT(itemList.size());

            // new ItemList
            List<String> _ItemList = new ArrayList<>();

            // bufs = CPB
            // Construct Conditional FP_Tree
            for (String bufs : buf.keySet()) {
                List<String> Tbuf = new ArrayList<>(List.of(bufs.split(",")));
                Tbuf.remove("");
                if (Tbuf.isEmpty()) continue;
                Tbuf.sort(new FreqComparator(FP_List));

                for (String string : Tbuf) {
                    if (!_ItemList.contains(string))
                        _ItemList.add(string);
                }

                FPT.InsertNode(Tbuf, FP_List, buf.get(bufs));
            }

            System.out.printf("FPT is below, [s] : %s\n", s);
            FPT.Print_FPTree();

            // After, I'll check it's really necessary code.

            _ItemList.sort(new FreqComparator(FP_List));

            if (FPT.isSingleTree()) {
                int[] _ItemCountList = new int[_ItemList.size()];
                for (int i = 0; i < _ItemList.size(); i++) {
                    _ItemCountList[i] = tree.GetCount(FP_List.indexOf(_ItemList.get(i)));
                }

                Mine_SingleFPT(_ItemList, _ItemCountList, freqItemSet, 0);
                return;
            }
            // It's ok until now.

            String FIS;
            if (freqItemSet.isEmpty())
                FIS = s;
            else {
                FIS = s + "," + freqItemSet;
                System.out.println("\nInsert FreqList : " + (FIS+ ":" + FPT.GetCount(FP_List.indexOf(itemList.get(0)))));
                FreqList.put(FIS, tree.GetCount(FP_List.indexOf(s)));
            }
            MineFPTree(FPT, _ItemList, FIS);

        }
    }

    void Mine_SingleFPT(List<String> itemList, int[] itemCountList, String freqItemSet, int stack) {
        if(itemList.isEmpty()) {
            System.out.println("End of Mining");
            return;
        }

        for (String item : itemList) {
            if(itemCountList[itemList.indexOf(item)] - stack < threshold)
                continue;

            List<String> _ItemList = new ArrayList<>();
            int[] _ItemCountList = new int[itemList.size()];

            for(int i = 0; i < itemList.indexOf(item); i++) {
                _ItemList.add(itemList.get(i));
                _ItemCountList[i] = itemCountList[i];
            }

            String FIS;

            if(freqItemSet.isEmpty()) {
                FIS = item;
                Mine_SingleFPT(_ItemList, _ItemCountList, FIS, stack+itemCountList[itemList.indexOf(item)]);
            }
            else {
                FIS = item + "," + freqItemSet;
                FreqList.put(FIS, itemCountList[itemList.indexOf(item)] - stack);
                Mine_SingleFPT(_ItemList, _ItemCountList, FIS, stack+itemCountList[itemList.indexOf(item)]);
            }
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

    // Just for debugging
    public void Print_item_Count(String[] aimItemArr) {
        file.Reload(filePath);

        int aimItemCount = 0;

        String buf;

        while((buf = file.readLine()) != null) {
            String[] goods = buf.split(",");
            boolean OutWhile = false;
            for (String aimItem  : aimItemArr) {
                if(!List.of(goods).contains(aimItem)) {
                    OutWhile = true;
                    break;
                }
            }
            if(!OutWhile)
                aimItemCount++;
        }

        System.out.printf("The aim Item you want [ %s ] : %d\n\n", Arrays.toString(aimItemArr), aimItemCount);
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
