import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class CSV {
    BufferedReader br;
    File csv;

    Read_CSV(String filePath) {
        csv = new File(filePath);
        br = new BufferedReader(new FileReader(csv));
    }
}
