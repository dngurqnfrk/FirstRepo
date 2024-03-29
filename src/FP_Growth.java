import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.io.File;
import java.io.FileReader;
import java.util.Map;
import java.util.PriorityQueue;

public class FP_Growth {
    private final HashMap<String, Integer> FreqList;
    private int objectNumber;
    private float threshold;

    public void Read_CSV(String filePath, float MSV) {
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

        threshold = objectNumber * MSV;

        for (String s : FreqList.keySet()) {
            if(FreqList.get(s) <= threshold) {
                FreqList.remove(s);
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
    FP_Growth() {
        FreqList = new HashMap<>();
    }
}
