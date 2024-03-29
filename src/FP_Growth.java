import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.io.File;
import java.io.FileReader;

public class FP_Growth {
    private final HashMap<String, Integer> FreqList;

    public void Read_CSV(String filePath) {
        CSV file = new CSV(filePath);

        String buf = "";

        while((buf = file.readLine()) != null) {
            String[] goods = buf.split(",");

            for (String s : goods) {
                if(!FreqList.containsKey(s)) {
                    FreqList.put(s, 1);
                } else FreqList.replace(s, FreqList.get(s) + 1);
            }
        }
    }

    //Just for debugging
    void Print_HashMap()
    {
        System.out.println(FreqList);
    }

    // Constructor
    FP_Growth() {
        FreqList = new HashMap<>();
    }
}
