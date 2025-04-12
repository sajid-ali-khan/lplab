import java.util.*;

public class Follow {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<Character, List<String>> rules = new HashMap<>();

        System.out.print("Enter number of rules: ");
        int n = Integer.parseInt(sc.nextLine());

        System.out.println("Enter rules in format like A->aB|#");
        for (int i = 0; i < n; i++) {
            String line = sc.nextLine().replace(" ", "");
            String[] parts = line.split("->");
            if (parts.length != 2) {
                System.out.println("Invalid format. Skipping line.");
                continue;
            }

            char lhs = parts[0].charAt(0);
            String[] productions = parts[1].split("\\|");

            rules.putIfAbsent(lhs, new ArrayList<>());
            for (String prod : productions) {
                rules.get(lhs).add(prod);
            }
        }

        // FIRST set
        Map<Character, Set<Character>> firsts = new HashMap<>();
        for (char ch : rules.keySet()) {
            firsts.put(ch, new HashSet<>());
        }

        findFirsts(firsts, rules);

        System.out.println("\nFIRST sets:");
        for (char ch : firsts.keySet()) {
            System.out.printf("%c: %s\n", ch, firsts.get(ch));
        }

        // FOLLOW set
        Map<Character, Set<Character>> follows = new HashMap<>();
        for (char nonT : rules.keySet()) {
            follows.put(nonT, new HashSet<>());
        }

        if (follows.containsKey('S')) {
            follows.get('S').add('$'); // Rule 1: $ in follow(S)
        }

        findFollows(firsts, rules, follows);

        System.out.println("\nFOLLOW sets:");
        for (char ch : follows.keySet()) {
            System.out.printf("%c: %s\n", ch, follows.get(ch));
        }
    }

    static void findFollows(Map<Character, Set<Character>> firsts, Map<Character, List<String>> rules, Map<Character, Set<Character>> follows) {
        boolean changed;

        do {
            changed = false;

            for (char lhs : rules.keySet()) {
                for (String prod : rules.get(lhs)) {
                    for (int i = 0; i < prod.length(); i++) {
                        char curr = prod.charAt(i);
                        if (!follows.containsKey(curr)) continue; // skip terminals

                        if (i + 1 < prod.length()) {
                            String beta = prod.substring(i + 1);
                            Set<Character> firstBeta = getFirstOfString(beta, firsts);

                            for (char ch : firstBeta) {
                                if (ch != '#' && !follows.get(curr).contains(ch)) {
                                    follows.get(curr).add(ch);
                                    changed = true;
                                }
                            }

                            if (firstBeta.contains('#')) {
                                for (char ch : follows.get(lhs)) {
                                    if (!follows.get(curr).contains(ch)) {
                                        follows.get(curr).add(ch);
                                        changed = true;
                                    }
                                }
                            }
                        } else {
                            for (char ch : follows.get(lhs)) {
                                if (!follows.get(curr).contains(ch)) {
                                    follows.get(curr).add(ch);
                                    changed = true;
                                }
                            }
                        }
                    }
                }
            }

        } while (changed);
    }

    static Set<Character> getFirstOfString(String str, Map<Character, Set<Character>> firsts) {
        Set<Character> result = new HashSet<>();

        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            if (terminal(ch)) {
                result.add(ch);
                break;
            }

            Set<Character> firstSet = firsts.get(ch);
            if (firstSet != null) {
                result.addAll(firstSet);
                if (!firstSet.contains('#')) {
                    break;
                }
            }

            if (i == str.length() - 1 && firstSet != null && firstSet.contains('#')) {
                result.add('#');
            }
        }

        return result;
    }

    static void findFirsts(Map<Character, Set<Character>> fMap, Map<Character, List<String>> rules) {
        for (char nonT : rules.keySet()) {
            computeFirst(nonT, fMap, rules, new HashSet<>());
        }
    }

    static Set<Character> computeFirst(char nonT, Map<Character, Set<Character>> fMap, Map<Character, List<String>> rules, HashSet<Character> visited) {
        if (!fMap.get(nonT).isEmpty()) {
            return fMap.get(nonT);
        }

        Set<Character> result = fMap.get(nonT);
        visited.add(nonT);

        for (String prod : rules.get(nonT)) {
            boolean allEpsilon = true;

            for (char sym : prod.toCharArray()) {
                if (terminal(sym)) {
                    result.add(sym);
                    allEpsilon = (sym == '#');
                    break;
                }

                if (!visited.contains(sym)) {
                    Set<Character> subFirst = computeFirst(sym, fMap, rules, visited);
                    for (char ch : subFirst) {
                        if (ch != '#') {
                            result.add(ch);
                        }
                    }
                    if (!subFirst.contains('#')) {
                        allEpsilon = false;
                        break;
                    }
                }
            }

            if (allEpsilon) {
                result.add('#');
            }
        }

        return result;
    }

    static boolean terminal(char ch) {
        return Character.isLowerCase(ch) || ch == '#';
    }
}
