import java.io.BufferedReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class InputReader{
    public static Map<Character, List<String>> readInput(String path) throws IOException{
        Path filePath = Path.of(path);
        List<String> contents = new ArrayList();
        BufferedReader reader = Files.newBufferedReader(filePath);
        String line;
        while ((line = reader.readLine()) != null){
            contents.add(line);
        }
        reader.close();
        return parseInput(contents);
    }

    static Map<Character, List<String>> parseInput(List<String> contents) {
        Map<Character, List<String>> rules = new HashMap<>();

        for (String line : contents) {
            String[] parts = line.split("->");
            Character lhs = parts[0].trim().charAt(0);
            List<String> rhs = Arrays.asList(parts[1].trim().split("\\|"));

            rules.put(lhs, rhs);
        }

        return rules;
    }
}