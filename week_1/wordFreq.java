import java.io.*;
import java.util.*;
import java.text.ParseException;

public class wordFreq{
  public static void main (String[] args) throws ParseException {
    //edge case
    if (args.length != 1){
      System.out.println("Wrong input: Only need the name of a file as input");
      return;
    }
    
    //Initialization
    Set<String> set = new HashSet<>();
    HashMap<String, Integer> map = new HashMap<>(); 
    String[] stopWords;
       
    //https://www.javatpoint.com/how-to-read-file-line-by-line-in-java
    //part 1
    try{
      File file=new File("../stop_words.txt"); //create a new file instance for stop_words.txt 
      FileReader fr=new FileReader(file); //read the file  
      BufferedReader br=new BufferedReader(fr); //create a buffering character input stream  
      StringBuffer sb=new StringBuffer();    //construct a string buffer with no characters  
      String line;  
      while((line=br.readLine())!=null) {  
        sb.append(line);//append line to string buffer 
      }  
      fr.close(); //close the stream and release the resources  
      
      stopWords = sb.toString().split(",");  
      for(String str: stopWords) set.add(str);
    }
    catch(IOException e) {  
      e.printStackTrace();  
    }

    //part 2   
    try {
      File file=new File(args[0]); //create a new file instance for the first input argument
      FileReader fr=new FileReader(file);
      BufferedReader br=new BufferedReader(fr); 
      StringBuffer sb=new StringBuffer(); 
      String line;  
      while((line=br.readLine())!=null){  
        sb.append(line);
        sb.append("\n"); 
      }  
      fr.close(); 

      String[] words= sb.toString().toLowerCase().split("[^a-z]+");
      for(String word: words){
        //only get the word that is not contain stopsWord and its length greater than 1 
        if(word.length() > 1 && !set.contains(word) ){
          map.put(word, map.getOrDefault(word, 0) + 1);           
        }
      }
    }
    catch(IOException e) {  
      e.printStackTrace();  
    }

    //sort the map entries by values in decreasing order
    List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
    Collections.sort(list, (e1,e2) -> e2.getValue().compareTo(e1.getValue()));
    // print the top 25 words
    int num = 1;
    for(Map.Entry<String, Integer> ans: list) {  
      if(num <= 25) {
        System.out.println(ans.getKey() + "  -  " + ans.getValue()); 
        num++; 
      }  
    }
    
  }
}
