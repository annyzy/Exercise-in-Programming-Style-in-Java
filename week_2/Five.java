import java.util.*;
import java.io.*;
import java.nio.file.*;

public class Five {
    static String data;
    static String[] datas;
    static List<String> words;
    static Map<String, Integer> wordFreqs;
    static List<Map.Entry<String, Integer>> list;

    public static void readFile(String path) throws IOException {
        data = Files.readString(Path.of(path));
    }

    public static void filterCharsAndNormalize() {
        data = data.replaceAll("[^\\p{Alnum}]", " ").toLowerCase();
    }

    public static void scan(){
        datas = data.split(" ");
    }

    public static void removeStopWords() throws IOException {
        List<String> stopWords = new ArrayList<>();
        stopWords = Arrays.asList(Files.readString(Path.of("../stop_words.txt")).split(","));

        words = new ArrayList<>();
        for (String str : datas) {
            if ((!stopWords.contains(str)) && (str.length() > 1))
                words.add(str);
        }
    }

    public static void frequencies() {
        wordFreqs = new HashMap<>();
        for (String word : words)
            wordFreqs.put(word, wordFreqs.getOrDefault(word, 0) + 1);
    }

    public static void sort() {
        list = new ArrayList<Map.Entry<String, Integer>>(wordFreqs.entrySet());
        Collections.sort(list, (e1, e2) -> e2.getValue().compareTo(e1.getValue()));
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Wrong input: Only need the name of a file as input");
           return;
        }

        readFile(args[0]);
        filterCharsAndNormalize();
        scan();
        removeStopWords();
        frequencies();
        sort();

        int num = 1;
        for (Map.Entry<String, Integer> ans : list) {
            if (num <= 25) {
                System.out.println(ans.getKey() + "  -  " + ans.getValue());
                num++;
            }
        }
    }
}