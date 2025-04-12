import java.util.*;

public class OperatorPrecedenceParser {
    static Map<String, Map<String, String>> pTable = new HashMap<>();
    static {
        pTable.put("+",Map.of("+", ">", "*", "<", "i", "<", "$", ">"));
        pTable.put("*", Map.of("+", ">", "*", ">", "i", "<", "$", ">"));
        pTable.put("i", Map.of("+", ">", "*", ">", "i", "", "$", ">"));
        pTable.put("$", Map.of("+", "<", "*", "<", "i", "<", "$", "A"));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter input:");
        String input = sc.nextLine().replaceAll("\\s+", "");
        input = input + "$";

        sc.close();

        Stack<String> stack = new Stack<>();
        stack.push("$");

        int i = 0; 

        while (true){
            String a = String.valueOf(input.charAt(i));
            String top = getNonTerminal(stack);

            String relation = pTable.getOrDefault(top, new HashMap<String, String>()).getOrDefault(a, "");

            if (relation.equals("<") || relation.equals("=")){
                stack.push(a); 
                i++;
                System.out.println("Shift: "+ stack);
            }else if (relation.equals(">")){
                while(true){
                    if (stack.size() <= 1) break;
                    String prev = getNonTerminal(stack.subList(0, stack.size() - 1));
                    String top2 = getNonTerminal(stack);

                    String rel = pTable.getOrDefault(prev, new HashMap<String, String>()).getOrDefault(top2, "");

                    stack.pop();
                    if (rel.equals("<") || rel.equals("")) break;
                }
                stack.push("E");
                System.out.println("Reduce: "+ stack);
            }else if (relation.equals("A")){
                System.out.println("Input.Accepted");
                break;
            }else{
                System.out.println("Input Rejected");
                break;
            }
        }
    }

    static String getNonTerminal(List<String> stack){
        for(int i = stack.size() -1; i >= 0; i --){
            String c = stack.get(i);
            if (!c.equals("E")) return c;
        }
        return "$";
    }

    static String getNonTerminal(Stack<String> stack){
        return getNonTerminal(new ArrayList<String>(stack));
    }
}