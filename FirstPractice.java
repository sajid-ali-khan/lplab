import java.io.IOException;
import java.util.*;

public class FirstPractice {
    public static void main(String[] args) throws IOException{
        Map<Character, List<String>> rules = InputReader.readInput("input.txt");
        Map<Character, Set<Character>> fMap = new HashMap<>();

        for (char nont : rules.keySet()){ //nont = not terminal
            fMap.put(nont, new HashSet<>());
        }

        findFirsts(fMap, rules);

        for (char nonT : fMap.keySet()){
            System.out.printf("%c: %s\n", nonT, fMap.get(nonT));
        }
    }

    static void findFirsts(Map<Character, Set<Character>> fMap, Map<Character, List<String>> rules){
        for (char nonT: rules.keySet()){
            computeFirst(nonT, fMap, rules, new HashSet());
        }
    }

    static Set<Character> computeFirst(char nonT, Map<Character, Set<Character>> fMap, Map<Character, List<String>> rules, HashSet<Character> visited){
        if (!fMap.get(nonT).isEmpty()){
            return fMap.get(nonT);
        }

        Set<Character> result = fMap.get(nonT);
        visited.add(nonT);

        for (String prod: rules.get(nonT)){
            boolean allEpsilon = true;

            for (char sym: prod.toCharArray()){
                if (terminal(sym)){
                    if (! result.contains(sym)){
                        result.add(sym);
                    }
                    allEpsilon = (sym == '#');
                    break;
                }

                if (! visited.contains(sym)){
                    Set<Character> subFirst = computeFirst(sym, fMap, rules, visited);
                    for (char ch: subFirst){
                        if (ch != '#' && !result.contains(ch)){
                            result.add(ch);
                        }
                    }
                    if (! subFirst.contains('#')){
                        allEpsilon = false;
                        break;
                    }
                }
            }

            if (allEpsilon && !result.contains('#')){
                result.add('#');
            }
        }
        return result;
    }

    static boolean terminal(char ch){
        return Character.isLowerCase(ch) || ch == '#';
    }

}
