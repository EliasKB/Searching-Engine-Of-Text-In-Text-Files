import java.io.File;
import java.io.FileInputStream;
import java.util.*;


public class Searcher {

    public static void main(String[] args) throws Exception {


        String pathToFiles = "data/fileMapForJavaTest/";

        File folder = new File(pathToFiles);

        File[] fileNames = folder.listFiles();


        final HashMap<String, String> fileNames_fileContents = getFileNameAndCorrespondingContentAsHashMap(fileNames);


        checkExistenceOfTheSentenceWhichIsImportedByKeyboardUntilImportingIsStoppedByQuit(fileNames_fileContents);


    }



    public final static HashMap<String, String> getFileNameAndCorrespondingContentAsHashMap(File[] fileNames) throws Exception {


        HashMap<String, String> filesNameAndContent = new HashMap<>();


        for (File fileName : fileNames) {
            String file_content = readFile(fileName);
            filesNameAndContent.put(fileName.getName(), file_content);
        }

        return filesNameAndContent;
    }





    private static void checkExistenceOfTheSentenceWhichIsImportedByKeyboardUntilImportingIsStoppedByQuit(HashMap<String, String> fileNames_fileContents) {

        String input_sentence_by_keybord = getInputSentenceByKeyword();

        String stop_sentence_by_keybord = "quit";

        while (!stop_sentence_by_keybord.equals(input_sentence_by_keybord)) {




            String[] input_sentence_wordByword = input_sentence_by_keybord.split(" ");


            List<FileScore> arrayForsavingResults = new ArrayList<>();


            int[] totalNumberOfScorePerFile = getNumberOfScorePerFile(fileNames_fileContents, input_sentence_wordByword, arrayForsavingResults);


            int sorted_index[] = getOldindexWhilesortingArrayInDescendingOrder(totalNumberOfScorePerFile);

            getResultOfSearchingPrinted(arrayForsavingResults, sorted_index);






            input_sentence_by_keybord = getInputSentenceByKeyword();

        }
    }






    private static void getResultOfSearchingPrinted(List<FileScore> arrayForsavingResults, int[] sorted_index) {

        for (int i : sorted_index) {
            System.out.println(arrayForsavingResults.get(i));
        }
    }





    static String getInputSentenceByKeyword() {

        System.out.print("Please enter a sentnece you want to search in the files > , for stop the process please enter quit > ");

        Scanner sc = new Scanner(System.in);

        return sc.nextLine();
    }





    private static int[] getNumberOfScorePerFile(HashMap<String, String> fileNames_fileContents, String[] input_sentence_wordByword, List<FileScore> arrayForsavingResults) {


        int totalNumberOfScorePerFile[] = new int[fileNames_fileContents.size()];

        int fileIndex = 0;


        for (Map.Entry<String, String> entry : fileNames_fileContents.entrySet()) {


            int numberOfscoreInaFile = getNumberOfExistingWordOfInputSentenceAsScoreInEachFile(input_sentence_wordByword, entry.getValue());


            arrayForsavingResults.add(new FileScore(numberOfscoreInaFile, entry.getKey(), input_sentence_wordByword.length));

            totalNumberOfScorePerFile[fileIndex] = numberOfscoreInaFile;

            fileIndex++;
        }


        return totalNumberOfScorePerFile;
    }





    private static int getNumberOfExistingWordOfInputSentenceAsScoreInEachFile(String[] input_sentence_wordByword, String file_content_as_a_string) {

        int numberOfscoreInaFile = 0;

        // I assume that frequency of each word in the files are not taken into account, only occurence/existens

        for (String word : input_sentence_wordByword) {

            if (file_content_as_a_string.contains(word)) {

                numberOfscoreInaFile++;
            }
        }

        return numberOfscoreInaFile;
    }








    private static String readFile(File file) throws Exception {

        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];

        fis.read(data);
        fis.close();

        return new String(data, "UTF-8");

    }





    private static int[] getOldindexWhilesortingArrayInDescendingOrder(int[] unsorted_array) {

        Object[] object_arrays = copyArrayWithItsIndex(unsorted_array);

        int[] sorted_array = (int[]) object_arrays[0];
        int[] oldIndexOfTheSortedArray = (int[]) object_arrays[1];


        for (int i = 0; i < sorted_array.length - 1; i++) {

            for (int j = i + 1; j < sorted_array.length; j++) {

                if (sorted_array[i] < sorted_array[j]) {

                    int temporay_value = sorted_array[i];
                    int temporary_index = oldIndexOfTheSortedArray[i];

                    sorted_array[i] = sorted_array[j];
                    oldIndexOfTheSortedArray[i] = oldIndexOfTheSortedArray[j];

                    sorted_array[j] = temporay_value;
                    oldIndexOfTheSortedArray[j] = temporary_index;

                }
            }
        }

        return oldIndexOfTheSortedArray;
    }






    private static Object[] copyArrayWithItsIndex(int[] array_original) {

        int[] array_copy = new int[array_original.length];
        int[] index_array_copy = new int[array_original.length];

        for (int i = 0; i < array_original.length; i++) {
            array_copy[i] = array_original[i];
            index_array_copy[i] = i;
        }

        Object[] arrayObjects = new Object[2];
        arrayObjects[0] = array_copy;
        arrayObjects[1] = index_array_copy;

        return arrayObjects;
    }

}
