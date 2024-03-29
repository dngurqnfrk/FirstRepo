
public class Main {
    public static void main(String[] args) {
        //argument process
        String filePath = args[0];
        float MSV = Float.parseFloat(args[1]);

        FP_Growth csv = new FP_Growth(MSV);

        csv.Construct_FPList(filePath);

        csv.Print_HashMap(); // Erase

    }
}