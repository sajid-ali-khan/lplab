import java.util.*;

public class exp10_IntermediateCodeGenerator {
    static Stack<String> operandStack = new Stack<>();
    static Stack<Character> operatorStack = new Stack<>();
    static List<String> intermediateCode = new ArrayList<>();
    static int tempVarCounter = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the arithmetic expression:");
        String expression = sc.nextLine();
        
        // Remove all spaces to simplify parsing
        expression = expression.replaceAll("\\s+", "");

        generateIntermediateCode(expression);

        System.out.println("\nGenerated Intermediate Code:");
        for (String code : intermediateCode) {
            System.out.println(code);
        }
    }

    static void generateIntermediateCode(String expr) {
        operandStack.clear();
        operatorStack.clear();
        intermediateCode.clear();
        tempVarCounter = 0;

        StringBuilder currentOperand = new StringBuilder();

        for (int i = 0; i < expr.length(); i++) {
            char ch = expr.charAt(i);

            if (Character.isLetterOrDigit(ch)) {
                currentOperand.append(ch);

                // Check if the next character is not part of the operand
                if (i == expr.length() - 1 || !Character.isLetterOrDigit(expr.charAt(i + 1))) {
                    operandStack.push(currentOperand.toString());
                    currentOperand.setLength(0);
                }
            }
            else if (ch == '(') {
                operatorStack.push(ch);
            }
            else if (ch == ')') {
                while (!operatorStack.isEmpty() && operatorStack.peek() != '(') {
                    createInstruction();
                }
                if (!operatorStack.isEmpty()) {
                    operatorStack.pop(); // remove '(' safely
                }
            }
            else if (isOperator(ch)) {
                while (!operatorStack.isEmpty() && precedence(ch) <= precedence(operatorStack.peek())) {
                    createInstruction();
                }
                operatorStack.push(ch);
            }
        }

        // After parsing the expression, apply remaining operations
        while (!operatorStack.isEmpty()) {
            createInstruction();
        }
    }

    static void createInstruction() {
        if (operandStack.size() < 2) {
            System.out.println("Error: Not enough operands to apply operator!");
            return;
        }
        char op = operatorStack.pop();
        String rightOperand = operandStack.pop();
        String leftOperand = operandStack.pop();
        String tempVar = "t" + tempVarCounter++;

        String code = tempVar + " = " + leftOperand + " " + op + " " + rightOperand;
        intermediateCode.add(code);
        operandStack.push(tempVar);
    }

    static boolean isOperator(char ch) {
        return "+-*/".indexOf(ch) != -1;
    }

    static int precedence(char ch) {
        if (ch == '+' || ch == '-') return 1;
        else return 2; // '*' or '/'
    }
}