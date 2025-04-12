import java.util.*;

public class LeftFactoring {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a production rule: ");
        String rule = sc.nextLine();
        sc.close();

        String[] parts = rule.split("->");
        String nonT = parts[0].trim();
        String[] productions = parts[1].split("\\|");

        List<String> prods = new ArrayList();
        for (String prod: productions){
            prods.add(prod.trim());
        }

        String prefix = findCommonPrefix(prods);

        if (prefix.isEmpty()){
            System.out.println("No left factoring");
            return;
        }

        String newNonT = nonT + "'";

        System.out.println("After left factoring..");

        System.out.println(nonT + " -> " + prefix + newNonT);

        System.out.print(newNonT + " -> ");
        for (int i = 0; i < prods.size(); i++){
            String prod = prods.get(i);
            if (prod.startsWith(prefix)){
                System.out.print(prod.substring(prefix.length()));
            }
            if (i != prods.size() - 1){
                System.out.print( " | ");
            }
        }
        System.out.println();
    }
    static String findCommonPrefix(List<String> prods){
        String prefix = prods.get(0);
        for (String prod: prods){
            while (!prod.startsWith(prefix)){
                if (prefix.isEmpty()) return "";
                prefix = prefix.substring(0, prefix.length()-1);
            }
        }
        return prefix;
    }
}
