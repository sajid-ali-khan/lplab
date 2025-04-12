import java.util.Scanner;
import java.util.Stack;

public class ShiftReduceParsing {
    static final String[][] rules = {
        {"E", "E+E"},
        {"E", "E*E"},
        {"E", "(E)"},
        {"E", "E-E"},
        {"E", "i"}
    };

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println();
        System.out.println("Enter input:");
        String input = sc.nextLine();
        sc.close();

        Stack<String> stack = new Stack();
        int ptr = 0;

        while (true){
            boolean reduced;
            if (ptr < input.length()){
                stack.push(String.valueOf(input.charAt(ptr++)));
                System.out.println("Shift: "+stack);
            }

            do{
                reduced = false;

                for (String[] rule : rules){
                    String lhs = rule[0];
                    String rhs = rule[1];

                    String stackStr = String.join("", stack);
                    int idx = stackStr.indexOf(rhs);

                    if (idx != -1){
                        for (int i = 0; i < rhs.length(); i++){
                            stack.pop();
                        }
                        reduced = true;
                        stack.push(lhs);
                        System.out.println("Reduced: "+rhs + " -> " + lhs + " | "+ stack);
                        break;
                    }
                }
            }while (reduced);

            if (ptr == input.length() && stack.size() == 1 && stack.peek().equals("E")){
                System.out.println("Input accepted");
                break;
            }

            if (ptr == input.length() && !reduced){
                System.out.println("Input rejected");
                break;
            }
        }
    }
}