
public class Main {
    public static void main(String[] args) {
        /*
        v 2.01
        I make FP-Growth Code.
        If we submit this code, We erase the code with comment "// Erase" and "// debugging"
        And maybe this code don't have enough ability because I didn't use Header Table.
        It's so hard
         */

        //argument process
        String filePath = args[0];
        float MSV = Float.parseFloat(args[1]);

        FP_Growth csv = new FP_Growth(filePath, MSV);

        csv.Construct_FPList();
        //csv.Print_FPList(); // Erase
        csv.Construct_FPTree();

        System.out.println("Now the FPList will be started\n\n"); // Erase
        csv.Print_FPList(); // Erase

        System.out.println("Now the Print_HashMap will be started\n\n"); // Erase
        csv.Print_HashMap(); // Erase

        System.out.println("Now the Print_FPTree will be started\n\n"); // Erase
        csv.Print_FPTree(); // Erase

        csv.MineFPTree();

        System.out.println("Now the Print_Support will be started\n\n"); // Erase

        csv.Print_Support();
    }
}