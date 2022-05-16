import java.util.*;
import java.nio.file.*;
import java.io.*;
import java.util.function.Function;

@SuppressWarnings({"unchecked"})
  
public class Ten {
    public static class TFTheOne {
        Object value;

        public TFTheOne(Object v) {
            this.value = v;
        }

        public TFTheOne bind(Function func) {
            this.value = func.apply(this.value);
            return this;
        }

        public void printme() {
            System.out.print(this.value);
        }
    }

    public static class readFile implements Function<String, String> {
        @Override
        public String apply(String path) {
            try {
                path = Files.readString(Path.of(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return path;
        }
    }

    public static class filterChars implements Function<String, String> {
        @Override
        public String apply(String data) {
            return data.replaceAll("[^\\p{Alnum}]", " ");
        }
    }

    public static class normalize implements Function<String, String> {
        @Override
        public String apply(String data) {
            return data.toLowerCase();
        }
    }

    public static class scan implements Function<String, String[]> {
        @Override
        public String[] apply(String data) {
            return data.split(" ");
        }
    }

    public static class removeStopWords implements Function<String[], List<String>> {
        @Override
        public List<String> apply(String[] wordList) {
            List<String> stopWords = new ArrayList<>();
            try {
                stopWords = Arrays.asList(Files.readString(Path.of("../stop_words.txt")).split(","));
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<String> words = new ArrayList<>();
            for (String str : wordList) {
                if ((!stopWords.contains(str)) && (str.length() > 1))
                    words.add(str);
            }
            return words;
        }
    }

    public static class frequencies implements Function<List<String>, Map<String, Integer>> {
        @Override
        public Map<String, Integer> apply(List<String> words) {
            Map<String, Integer> wordFreqs = new HashMap<>();
            for (String word : words)
                wordFreqs.put(word, wordFreqs.getOrDefault(word, 0) + 1);
            return wordFreqs;
        }
    }

    public static class sort implements Function<Map<String, Integer>, List<Map.Entry<String, Integer>>> {
        @Override
        public List<Map.Entry<String, Integer>> apply(Map<String, Integer> wordFreqs) {
            List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(wordFreqs.entrySet());
            Collections.sort(list, (e1, e2) -> e2.getValue().compareTo(e1.getValue()));
            return list;
        }
    }

    public static class top25Freqs implements Function<List<Map.Entry<String, Integer>>, String> {
        @Override
        public String apply(List<Map.Entry<String, Integer>> list) {
            String tmp = "";
            int num = 1;
            for (Map.Entry<String, Integer> ans : list) {
                if (num <= 25) {
                    System.out.println(ans.getKey() + "  -  " + ans.getValue());
                    num++;
                }
            }
            return tmp;
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Wrong input: Only need the name of a file as input");
            return;
        }
        new TFTheOne(args[0])
                .bind(new readFile())
                .bind(new filterChars())
                .bind(new normalize())
                .bind(new scan())
                .bind(new removeStopWords())
                .bind(new frequencies())
                .bind(new sort())
                .bind(new top25Freqs())
                .printme();
    }
}