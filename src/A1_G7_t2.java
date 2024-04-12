public class A1_G7_t2 {
    public static void main(String[] args) {
        /*
        v 3.04
         */
        //long startTime = System.nanoTime();

        //argument process
        String filePath = args[0];
        double MSV = Double.parseDouble(args[1]);

        FP_Growth csv = new FP_Growth(filePath, MSV);

        csv.Construct_FPList();
        csv.Construct_FPTree();


        csv.MineFPTree(csv.GetFP_Tree(), csv.GetFP_List(), "");

        csv.Print_Support();

        //long endTime = System.nanoTime();

        //long duration = (endTime - startTime);
        //System.out.println("\nExecution Time : " + duration / 1000000 + "ms");
    }
}