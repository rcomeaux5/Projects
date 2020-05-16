import java.util.*;
import java.io.*;
public class TextReader{

    private static final String DEFAULT_TEXT_FILE = "WarAndPeace.txt";
    
    public static void main(String[] args) {
        String filename = DEFAULT_TEXT_FILE;
        if (args.length > 0) {
            filename = args[0];
        }
        Scanner keyb = new Scanner(System.in);
        ArrayList<String> list_of_words = getWordsFromFile(filename);
        System.out.printf("%d total words in %s\n", list_of_words.size(), DEFAULT_TEXT_FILE);


        String filename_x = "stop_words.txt";
        if (args.length > 0) {
            filename = args[0];
        }
        ArrayList<String> list_of_stop_words = getStopWordsFromFile(filename_x);


        Set<String> unique_words = createSetOfUniqueWords(list_of_words); // unusually high number 
        System.out.printf("%d unique words, including stop words\n", unique_words.size());

        ArrayList<String> list_of_words_exclud_stop_words = new ArrayList<>();
        list_of_words_exclud_stop_words.addAll(list_of_words);
        for (int ndx = 0; ndx < list_of_stop_words.size(); ndx++) {
            if (list_of_words_exclud_stop_words.contains(list_of_words.get(ndx))) {
                list_of_words_exclud_stop_words.remove(list_of_stop_words.get(ndx));
            }
        }
        System.out.printf("%d total non-stop words in %s\n", list_of_words_exclud_stop_words.size(), DEFAULT_TEXT_FILE);

        ArrayList<String> cleanedList_of_words = new ArrayList<>();
        cleanedList_of_words = getWordsFromFile2(filename);

        Set<String> unique_words_exclud_stopwords = new HashSet<>();
        for (String uniq_word : unique_words) {

            if (!(list_of_stop_words.contains(uniq_word))) {
                unique_words_exclud_stopwords.add(uniq_word);
            }
        }
        System.out.printf("%d unique words, excluding stop words\n", unique_words_exclud_stopwords.size());


        Map<Integer, ArrayList<String>> reportMap = createMapOfUniqueWords(unique_words);
        System.out.println("Length" + "         " + "Number of Words");
        reportMap.forEach((key, value) -> System.out.println(key + "              " + value.size())); 


        int longest_list_locationInMap = findthelongestWords(reportMap);
        //System.out.println("The longest list is" + longest_list_locationInMap);
        ArrayList<String> listContents = new ArrayList<>();
        listContents = reportMap.get(longest_list_locationInMap);
        int contentsSize = listContents.size();
        System.out.println("The largest group of words has " + contentsSize + " letters.");
        String answer = "";
        do {
            System.out.print("Show words of what length?: ");
            int newListKey = keyb.nextInt();
            int longest_list_locationInMap2 = newListKey;
            ArrayList<String> listContents2 = new ArrayList<>();
            listContents2 = reportMap.get(newListKey);
            System.out.println(listContents2);

            System.out.println("Another?: ");
            answer = keyb.next(); // why does this not work when using .nextLine but works with .next?
        } while(answer.equals("y")); 


        
    }//main
    
    private static Set<String> createSetOfUniqueWords(ArrayList<String> list_of_words){
        Set<String> unique_words=new HashSet<>();
        unique_words.addAll(list_of_words);
        return unique_words;
    }


    private static ArrayList<String> getWordsFromFile(String filename){
        System.out.printf("Analyzing: \"%s\"\n",filename);
        File wordFile=new File(filename);
        Scanner wordScan=null; //why is this null?
        try{
            wordScan=new Scanner(wordFile);
        }catch(FileNotFoundException e){
            System.err.printf("Cannot open \"%s\", program exiting.\n",filename);
            System.exit(0);
        }
        ArrayList<String>list_of_words=new ArrayList<>();
        while(wordScan.hasNext()){
            String lineFromFile=wordScan.nextLine();
            String[]arrOfString=lineFromFile.split(" ");
            for(String word:arrOfString){
                String cleanWord=removeSpaces(word);
                list_of_words.add(cleanWord.toLowerCase());
            }
        }
        wordScan.close();
        return list_of_words;
    }
private static ArrayList<String> getWordsFromFile2(String filename){
        File wordFile=new File(filename);
        Scanner wordScan=null; //why is this null?
        try{
            wordScan=new Scanner(wordFile);
        }catch(FileNotFoundException e){
            System.err.printf("Cannot open \"%s\", program exiting.\n",filename);
            System.exit(0);
        }
        ArrayList<String>list_of_words=new ArrayList<>();
        while(wordScan.hasNext()){
            String lineFromFile=wordScan.nextLine();
            String[]arrOfString=lineFromFile.split(" ");
            for(String word:arrOfString){
                String cleanWord=removeSpaces(word);
                list_of_words.add(cleanWord.toLowerCase());
            }
        }
        wordScan.close();
        return list_of_words;
    }

    
    
    
// ; " ! 
/*
 * Character.isLetter(c);
 */

    static String removeSpaces(String toClean){
        StringBuilder clean=new StringBuilder();
        for(int i=0;i<toClean.length();i++){
            char ltr=toClean.charAt(i);
            if(Character.isLetter(ltr)){
                clean.append(ltr);
            }
        }
        return clean.toString(); //proper syntax to change type to arraylist??
    }
    
    private static ArrayList<String> getStopWordsFromFile(String filename_x){
        File stop_Word_File=new File(filename_x);
        Scanner stopWordScan=null;
        try{
            stopWordScan=new Scanner(stop_Word_File);
        }catch(FileNotFoundException e){
            System.err.printf("Cannot open \"%s\", program exiting.\n",filename_x);
            System.exit(0);
        }
        //String headers = stopWordScan.nextLine(); // check to make sure 1st line is headers
        ArrayList<String> list_of_stop_words=new ArrayList<>();
        while(stopWordScan.hasNext()){ // adds nothing to the list???
            String sw=new String(stopWordScan.nextLine());
            list_of_stop_words.add(sw);
        }
        stopWordScan.close();
        return list_of_stop_words;
    }
    
    private static Map<Integer, ArrayList<String>>createMapOfUniqueWords(Set<String> setOfWords){
        Map<Integer, ArrayList<String>>tempMap=new HashMap<>();
        for(String w:setOfWords){
            String cleanedWord=removeSpaces(w);
            if(!(tempMap.containsKey(cleanedWord.length()))){ // if the key does not exist in the map
                ArrayList<String>valuesList=new ArrayList<>();
                valuesList.add(cleanedWord);
                tempMap.put(cleanedWord.length(),valuesList);
            } else { 
                if(tempMap.containsKey(cleanedWord.length())){
                   ArrayList<String> valuesList = tempMap.get(cleanedWord.length());
                    valuesList.add(cleanedWord);
                }
            }
        }
        return tempMap;
    }
    
       
private static int findthelongestWords(Map<Integer, ArrayList<String>>inMap){
        int largest_listSize = 0;
        int listSize = 0;
        int largest_listKey=0;
    for(Map.Entry<Integer,ArrayList<String>> entry : inMap.entrySet()){  
        List<String> listName = new ArrayList<>();
        listName = entry.getValue();
        listSize = listName.size();
        if(listSize >= largest_listSize){
            largest_listSize = listName.size();
            largest_listKey = entry.getKey();
         }   
    }
     return largest_listKey;       
  }
}
    
