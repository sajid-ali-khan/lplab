import java.io.IOException;
import java.util.*;

public class First {
    public static void main(String[] args) throws IOException {
        Map<Character, List<String>> rules = InputReader.readInput("input.txt");

        Map<Character, List<Character>> firstsMap = new HashMap<>();
        for (Character ch : rules.keySet()) {
            firstsMap.put(ch, new ArrayList<>());
            for (String production : rules.get(ch)) {
                for (char symbol : production.toCharArray()) {
                    if (!firstsMap.containsKey(symbol) && Character.isUpperCase(symbol)) {
                        firstsMap.put(symbol, new ArrayList<>());
                    }
                }
            }
        }

        findFirsts(firstsMap, rules);

        for (Character ch : firstsMap.keySet()) {
            System.out.println("First of " + ch + " is " + firstsMap.get(ch));
        }
    }

    static void findFirsts(Map<Character, List<Character>> firstsMap, Map<Character, List<String>> rules) {
        for (char symbol : rules.keySet()) {
            computeFirst(symbol, firstsMap, rules, new HashSet<>());
        }
    }

    static List<Character> computeFirst(char symbol, Map<Character, List<Character>> firstsMap,
            Map<Character, List<String>> rules, Set<Character> visited) {

        if (!firstsMap.get(symbol).isEmpty()) {
            return firstsMap.get(symbol);
        }

        List<Character> result = firstsMap.get(symbol);
        visited.add(symbol);

        for (String production : rules.get(symbol)) {
            boolean allEpsilon = true;

            for (int i = 0; i < production.length(); i++) {
                char sym = production.charAt(i);

                if (isTerminal(sym)) {
                    if (!result.contains(sym)) {
                        result.add(sym);
                    }
                    allEpsilon = (sym == '#');
                    break;
                }

                if (!visited.contains(sym)) {
                    List<Character> subFirst = computeFirst(sym, firstsMap, rules, visited);
                    for (char ch : subFirst) {
                        if (ch != '#' && !result.contains(ch)) {
                            result.add(ch);
                        }
                    }
                    if (!subFirst.contains('#')) {
                        allEpsilon = false;
                        break;
                    }
                }
            }

            if (allEpsilon && !result.contains('#')) {
                result.add('#');
            }
        }

        return result;
    }

    static boolean isTerminal(char ch) {
        return Character.isLowerCase(ch) || ch == '#';
    }

}
