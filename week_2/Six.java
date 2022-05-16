import java.util.*;
import java.io.*;
import java.nio.file.*;

public class Six {
    public static String readFile(String path) throws IOException {
        return Files.readString(Path.of(path));
    }

    public static String filterCharsAndNormalize(String data) {
        return data.replaceAll("[^\\p{Alnum}]", " ").toLowerCase();
    }

    public static String[] scan(String data) {
        return data.split(" ");
    }

    public static List<String> removeStopWords(String[] wordList) throws IOException {
        List<String> stopWords = new ArrayList<>();
        stopWords = Arrays.asList(Files.readString(Path.of("../stop_words.txt")).split(","));

        List<String> words = new ArrayList<>();
        for (String str : wordList) {
            if ((!stopWords.contains(str)) && (str.length() > 1))
                words.add(str);
        }
        return words;
    }

    public static Map<String, Integer> frequencies(List<String> words) {
        Map<String, Integer> wordFreqs = new HashMap<>();
        for (String word : words)
            wordFreqs.put(word, wordFreqs.getOrDefault(word, 0) + 1);
        return wordFreqs;
    }

    public static List<Map.Entry<String, Integer>> sort(Map<String, Integer> wordFreqs) {
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(wordFreqs.entrySet());
        Collections.sort(list, (e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        return list;
    }

    public static void printAll(List<Map.Entry<String, Integer>> list) {
        int num = 1;
        for (Map.Entry<String, Integer> ans : list) {
            if (num <= 25) {
                System.out.println(ans.getKey() + "  -  " + ans.getValue());
                num++;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Wrong input: Only need the name of a file as input");
            return;
        }
        printAll(sort((frequencies(removeStopWords(scan(filterCharsAndNormalize(readFile(args[0]))))))));
    }
}