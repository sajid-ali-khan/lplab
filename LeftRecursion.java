import java.util.*;

public class LeftRecursion {
    static final String epsilon = "#";
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a production rule: ");
        String rule = sc.nextLine();
        sc.close();

        String[] parts = rule.split("->");
        String nonT = parts[0].trim();
        String[] prods = parts[1].split("\\|");

        // System.out.println(nonT + "\n" + Arrays.toString(prods));

        List<String> lr = new ArrayList<>();
        List<String> nlr = new ArrayList<>();

        for (String prod : prods){
            prod = prod.trim();
            if (prod.startsWith(nonT)){
                lr.add(prod.substring(nonT.length()));
            }else{
                nlr.add(prod);
            }
        }

        if (lr.isEmpty()){
            System.out.println("No left recursion detected");
            return;
        }

        System.out.println("Left recursion detected");
        String newNonT = nonT + "'";
        System.out.print(nonT + " -> ");
        for (int i = 0; i < nlr.size(); i++){
            System.out.print(nlr.get(i) + newNonT);
            if (i != nlr.size() - 1){
                System.out.print(" | ");
            }
        }
        System.out.println();

        System.out.print(newNonT + " -> ");
        for (int i = 0; i < lr.size(); i++){
            System.out.print(lr.get(i) + newNonT);
            if (i != lr.size() - 1){
                System.out.print(" | ");
            }
        }
        System.out.println(" | " + epsilon);

    }
}
