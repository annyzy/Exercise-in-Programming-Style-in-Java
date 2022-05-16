import java.io.*;
import java.nio.file.*;

public class Four {
    //Since there are no library used in Four.java, it takes a while(~ 1 min) to run the program. Please be patient!
    static String data;
    static String[] datas;
    static int[] wordFreqs, key, value;

    public static void readFile(String path) throws IOException {
        data = Files.readString(Path.of(path));
    }

    public static void filterCharsAndNormalize() {
        data = data.replaceAll("[^\\p{Alnum}]", " ").toLowerCase();
    }

    public static void scan(){
        datas = data.split(" ");
    }

    public static void removeStopWordsAndFrequencies() throws IOException {
      String[] stopWords =Files.readString(Path.of("../stop_words.txt")).toString().split(",");
      int stopWordsLen = stopWords.length, datasLen = datas.length;
      wordFreqs = new int[datasLen];
      
      for (int i = 0; i < datasLen; i++){
          for (int j = 0; j < stopWordsLen; j++){
            if(datas[i].equals(stopWords[j])) datas[i] = "";
          }
          int len = datas[i].length();
          if(len > 1){
              for(int k = 0; k < datasLen; k++){
                  if(datas[i].equals(datas[k])) ++wordFreqs[i];
              }
          }
        }
    }

    public static void sort() {
        int val = 0, index = -1, len = wordFreqs.length;
        int wordFreqsLen = len;
        key = new int[25];
        value = new int[25];
        for (int i = 0; i < 25; i++){
            for (int j = 0; j < wordFreqsLen; j++) {
              int tmp = wordFreqs[j];
                  if (tmp > val) {
                      if(tmp < len){
                          val = tmp;
                          index = j;
                      }
                  }  
            }
            key[i] = index;
            value[i] = val;
            len = val;
            val = 0;
        }
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Wrong input: Only need the name of a file as input");
           return;
        }

        readFile(args[0]);
        filterCharsAndNormalize();
        scan();
        removeStopWordsAndFrequencies();
        sort();

      for (int i  = 0; i < 25; i++){
          String word = datas[key[i]];
          int freq = value[i];
          System.out.println(word + "  -  " + freq );
      }
    }
}