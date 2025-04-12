import java.util.*;

public class LexicalAnalyser {
    static final Set<String> keywords = new HashSet<String>(Arrays.asList("int float char double".split(" ")));
    static final Set<String> operators = new HashSet<String>(Arrays.asList("* = + - / ++ --".split(" ")));
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Enter a piece of code:");
        String code = sc.nextLine();

        if (! code.endsWith(";")){
            System.out.println("Invalid, missing semicolon");
            return;
        }
        code = code.substring(0, code.length()-1) + " ;";

        for (String token : code.split(" ")){
            System.out.print(token);
            if (keywords.contains(token)){
                System.out.println(": keyword");
            }else if(operators.contains(token)){
                System.out.println(": operator");
            }else if(token.equals(",") || token.equals(";")){
                System.out.println(": delimeter");
            }else if (token.matches("[a-zA-z][a-zA-Z0-9]*")){
                System.out.println(": identifier");
            }else{
                System.out.println(": literal");
            }
        }
    }
}
