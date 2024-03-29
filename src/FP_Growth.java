import java.util.*;

public class FP_Growth {
    private final HashMap<String, Integer> FreqList;
    String[] FP_List;
    private int objectNumber;
    private float threshold;


    public void Construct_FPList(String filePath) {
        CSV file = new CSV(filePath);
        objectNumber = 0;

        String buf = "";

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

        FP_List = keyList.toArray( new String[0]);
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
        System.out.println(Arrays.toString(FP_List));
    }

    // Constructor
    FP_Growth(float MSV) {
        FreqList = new HashMap<>();
        threshold = MSV;
    }
}
