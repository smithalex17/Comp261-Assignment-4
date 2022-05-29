import java.util.*;
import java.io.*;

public class KMP {

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please call this program with " +
                               "two arguments which is the input file name " +
                               "and the string to search.");
        } else {
            try {
                Scanner s = new Scanner(new File(args[0]));

                // Read the entire file into one String.
                StringBuilder fileText = new StringBuilder();
                while (s.hasNextLine()) {
                    fileText.append(s.nextLine() + "\n");
                }
                int location = search(fileText.toString(), args[1]);
                if(location == -1){
                    System.out.println("Pattern not found");
                }else{
                    System.out.println("Pattern found at: " + location);
                }                
            } catch (FileNotFoundException e) {
                System.out.println("Unable to find file called " + args[0]);
            }
        }
    }

    /**
     * Perform KMP substring search on the given text with the given pattern.
     * 
     * This should return the starting index of the first substring match if it
     * exists, or -1 if it doesn't.
     */
    public static int search(String text, String pattern) {
        // TODO

        int textLength = text.length();
        int patternLength = pattern.length();
        int[] match = new int[patternLength];
        int matchTable[] = computeMatchArray(pattern, patternLength, match);
        int patternIndex = KMPSearch(pattern, text, matchTable);
        return patternIndex;
    }

    public static int[] computeMatchArray(String pattern, int len, int[] match) {
        int[] M = match;
        M[0] = -1;
        M[1] = 0;
        int j = 0; // position in prefix
        int pos = 2; // position in table
        // the loop calculates lps[i] for i = 1 to M-1
        while (pos < len) {
           if(pattern.charAt(pos) == pattern.charAt(j)){
               M[pos] = j+1;
               pos++; j++;
           }else if(j > 0){
               j = M[j];
           }else{
               M[pos] = 0;
               pos++;
           }
        }
        return M;
    }

    public static int KMPSearch(String pattern, String text, int[] matchTable){
        int textPos = 0;
        int patternPos = 0;

        while(textPos+patternPos < text.length()){
            if(pattern.charAt(patternPos) == text.charAt(textPos+patternPos)){
                patternPos = patternPos +1;
                if(patternPos == pattern.length()){
                    return textPos;
                }
            }else if(matchTable[patternPos] == -1){
                textPos = textPos + patternPos + 1;
                patternPos = 0;
            }else{
                textPos = textPos + patternPos - matchTable[patternPos];
                patternPos = matchTable[patternPos];
            }
        }
        return -1;
    }


}
