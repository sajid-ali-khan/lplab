import java.util.Scanner;

public class DFA {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter a string:");
        String input = sc.next();
        sc.close();

        if (valid(input)) {
            System.out.println("Accepted");
        } else {
            System.out.println("Rejected");
        }
    }

    static boolean valid(String input) {
        int state = 0;
        for (char ch : input.toCharArray()) {
            switch (state) {
                case 0:
                    if (ch == 'a')
                        state = 1;
                    else
                        state = 0;
                    break;
                case 1:
                    if (ch == 'b')
                        state = 2;
                    else if (ch == 'a')
                        state = 1;
                    else
                        state = 0;
                    break;
                case 2:
                    if (ch == 'c')
                        state = 3;
                    else if (ch == 'a')
                        state = 1;
                    else
                        state = 0;
                    break;
                case 3:
                    if (ch == 'a')
                        state = 1;
                    else
                        state = 0;
                    break;
            }
        }
        return state == 3;
    }
}
