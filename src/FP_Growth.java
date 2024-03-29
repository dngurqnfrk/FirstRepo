import java.io.BufferedReader;
import java.util.HashMap;
import java.io.File;
import java.io.FileReader;

public class FP_Growth {
    HashMap<String, Integer> FreqList;
    public void ReadCSV(String filePath) {
        File csv = new File(filePath);
        BufferedReader br;

        br = new BufferedReader(new FileReader(csv));
    }
}
