import java.io.IOException;
import java.util.*;

public class Follow {
    public static void main(String[] args) throws IOException{
        Map<Character, List<String>> rules = InputReader.readInput("input.txt");
        Map<Character, Set<Character>> firsts = new HashMap();

        for (char ch: rules.keySet()){
            firsts.put(ch, new HashSet());
        }

        FirstPractice.findFirsts(firsts, rules);

        Map<Character, Set<Character>> follows = new HashMap();
        for (char nonT: rules.keySet()){
            follows.put(nonT, new HashSet());
        }
        if (follows.containsKey('S')) follows.get('S').add('$');
        findFollows(firsts, rules, follows);
    }

    static void findFollows(Map<Character, Set<Character>> firsts, Map<Character, List<String>> rules, Map<Character, Set<Character>> follows){
        boolean changed = false;

        do {
            for (char nonT: follows.keySet()){
                
                for (char symbol : follows.keySet()){
                    for (String prod: rules.get(symbol)){
                        for(int i = 0; i < prod.length(); i++){
                            if (prod.charAt(i) != )
                            if (i == prod.length() - 1){
                                continue;
                            }
                            char next = prod.charAt(ind + 1);
                            if (FirstPractice.terminal(next) && next != '#'){
                                follows.get(nonT).add(next);
                                changed = true;
                            }
                        }
                    }
                }
            }
        }while (changed);
    }

    
}
