import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.nio.file.*;
import java.util.function.*;

public class Seven{
    public static void main (String[] args) throws IOException {
        if (args.length != 1){
          System.out.println("Wrong input: Only need the name of a file as input");
          return;
        }
      
        Set<String> stopWords = Files.lines(Path.of("../stop_words.txt")).map(line -> line.split(",")).flatMap(Arrays::stream).collect(Collectors.toCollection(HashSet::new));
      
        Files.lines(Path.of(args[0])).map(line -> line.toLowerCase().replaceAll("[^\\p{Alnum}]", " ")).
          map(line -> line.split("\\s+")).flatMap(Arrays::stream).filter(word -> word.length() > 1).
          filter(word -> !stopWords.contains(word)).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().
          stream().sorted((e1,e2) -> e2.getValue().compareTo(e1.getValue())).limit(25).
          forEach( e -> System.out.println(e.getKey() + "  -  " + e.getValue()));
  
  }
}
