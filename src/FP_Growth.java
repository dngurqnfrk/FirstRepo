import java.util.HashMap;
import java.util.Iterator;

public class FP_Growth {
    private final HashMap<String, Integer> FreqList;
    private int objectNumber;
    private float threshold;

    public void Read_CSV(String filePath) {
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

        Iterator<String> iterator = FreqList.keySet().iterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
            if (FreqList.get(s) <= threshold) {
                iterator.remove();
            }
        }
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
    }

    // Constructor
    FP_Growth(float MSV) {
        FreqList = new HashMap<>();
        threshold = MSV;
    }
}
