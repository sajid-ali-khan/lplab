import java.util.*;

public class exp_6Non_Recursive_precictive_parser {
    static Map<Character, List<String>> grammar = new HashMap<>();
    static String input;
    static int i = 0;

    static boolean parse(char nonTerminal) {
        if (!grammar.containsKey(nonTerminal)) {
            return false;
        }
        
        int backtrack = i;
        for (String prod : grammar.get(nonTerminal)) {
            i = backtrack;
            boolean success = true;
            for (char symbol : prod.toCharArray()) {
                if (symbol == '@') continue;
                else if (Character.isUpperCase(symbol)) {
                    success &= parse(symbol);
                } else if (i < input.length() && input.charAt(i) == symbol) {
                    i++;
                } else {
                    success = false;
                    break;
                }
            }
            if (success) return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter number of productions:");
        int n = sc.nextInt();
        sc.nextLine();  // Consume the newline after integer input

        System.out.println("Enter productions (Use '@' for epsilon, e.g., A->aA|@):");
        for (int j = 0; j < n; j++) {
            String[] rule = sc.nextLine().split("->");
            grammar.put(rule[0].charAt(0), Arrays.asList(rule[1].split("\\|")));
        }

        System.out.println("Enter the string to check:");
        input = sc.next() + "$";  // Adding end marker

        char startSymbol = 'S';  // Assuming start symbol is 'S'

        boolean result = parse(startSymbol) && i == input.length() - 1;

        if (result) {
            System.out.println("String is accepted");
        } else {
            System.out.println("String is rejected");
        }
    }
}

//Enter number of productions:
//3
//Enter productions (Use '@' for epsilon, e.g., A->aA|@):
//S->aA
//A->bB
//B->c|@
//Enter the string to check:
//abc
//String is accepted