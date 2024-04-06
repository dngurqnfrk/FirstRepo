
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
        csv.Construct_FPTree();


        csv.MineFPTree(csv.GetFP_Tree(), csv.GetFP_List(), "");

        csv.Print_Support();
    }
}