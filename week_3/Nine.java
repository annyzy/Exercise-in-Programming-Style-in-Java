import java.util.*;
import java.nio.file.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SuppressWarnings({"unchecked"})
  
public class Nine {
    public static void readFile(String path, BiConsumer<String, BiConsumer> function) {
        try {
            function.accept(Files.readString(Path.of(path)), func(Nine::normalize));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void filterChars(String data, BiConsumer<String, BiConsumer<String,?> > function) {
        function.accept(data.replaceAll("[^\\p{Alnum}]", " "), func(Nine::scan));
    }

    public static void normalize(String data, BiConsumer<String, BiConsumer<String[],?>> function) {
        function.accept(data.toLowerCase(), func(Nine::removeStopWords));
    }

    public static void scan(String data, BiConsumer<String[], BiConsumer<List<String>,?>> function) {
        function.accept(data.split(" "), func(Nine::frequencies));
    }

    public static void removeStopWords(String[] wordList, BiConsumer<List<String>, BiConsumer<Map<String, Integer>,?>> function) {
        List<String> stopWords = new ArrayList<>();

        try {
            stopWords = Arrays.asList(Files.readString(Path.of("../stop_words.txt")).split(","));

            List<String> words = new ArrayList<>();
            for (String str : wordList) {
                if ((!stopWords.contains(str)) && (str.length() > 1))
                    words.add(str);
            }
            function.accept(words, func(Nine::sort));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void frequencies(List<String> words, BiConsumer< Map<String, Integer>, BiConsumer<List<Map.Entry<String, Integer>>,?>> function) {
        Map<String, Integer> wordFreqs = new HashMap<>();
        for (String word : words)
            wordFreqs.put(word, wordFreqs.getOrDefault(word, 0) + 1);
        function.accept(wordFreqs, func(Nine::printText));
    }

    public static void sort(Map<String, Integer> wordFreqs,
            BiConsumer<List<Map.Entry<String, Integer>>, Consumer<?>> function) {
        List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(wordFreqs.entrySet());
        Collections.sort(list, (e1, e2) -> e2.getValue().compareTo(e1.getValue()));
        function.accept(list, Nine::noOp);
    }

    public static void printText(List<Map.Entry<String, Integer>> list, Consumer<?> last) {
        int num = 1;
        for (Map.Entry<String, Integer> ans : list) {
            if (num <= 25) {
                System.out.println(ans.getKey() + "  -  " + ans.getValue());
                num++;
            }
        }
        last.accept(null);
    }

    public static <T> void noOp(T t) {
        return;
    }

    public static <T, B> BiConsumer<T, BiConsumer> func(BiConsumer<T, B> f) {
      return (BiConsumer<T, BiConsumer>) f;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Wrong input: Only need the name of a file as input");
            return;
        }
        readFile(args[0], Nine::filterChars);
    }
}

                            