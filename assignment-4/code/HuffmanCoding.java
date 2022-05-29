
import java.util.*;
import java.io.*;
import java.time.format.TextStyle;
import java.util.PriorityQueue;
import java.util.Comparator;

/**
 * A new instance of HuffmanCoding is created for every run. The constructor is
 * passed the full text to be encoded or decoded, so this is a good place to
 * construct the tree. You should store this tree in a field and then use it in
 * the encode and decode methods.
 */
public class HuffmanCoding {

        // alphabet size of extended ASCII
    private static final int R = 256;

    private static class HuffmanTree implements Comparable<HuffmanTree> {

        private final char ch;
        private final int freq;
     
        HuffmanTree left;
        HuffmanTree right;
    
        HuffmanTree(char ch, int freq, HuffmanTree left, HuffmanTree right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }
    
        // is the node a leaf node?
        private boolean isLeaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return (left == null) && (right == null);
        }
        public int getFrequency() {
            return freq;
        }


        public char getCh() {
            return ch;
        }
    
        // compare, based on frequency
        public int compareTo(HuffmanTree that) {
            return this.freq - that.freq;
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Please call this program with two arguments, which are " +
                               "the input file name and either 0 for constructing tree and printing it, or " +
                               "1 for constructing the tree and encoding the file and printing it, or " +
                               "2 for constructing the tree, encoding the file, and then decoding it and " +
                               "printing the result which should be the same as the input file.");
        } else {
            try {
                Scanner s = new Scanner(new File(args[0]));

                // Read the entire file into one String.
                StringBuilder fileText = new StringBuilder();
                while (s.hasNextLine()) {
                    fileText.append(s.nextLine() + "\n");
                }
                
                if (args[1].equals("0")) {
                    System.out.println(constructTree(fileText.toString()));
                } else if (args[1].equals("1")) {
                    constructTree(fileText.toString()); // initialises the tree field.
                    System.out.println(encode(fileText.toString()));
                } else if (args[1].equals("2")) {
                    constructTree(fileText.toString()); // initialises the tree field.
                    String codedText = encode(fileText.toString());
                     // DO NOT just change this code to simply print fileText.toString() back. ;-)
                    System.out.println(decode(codedText));
                } else {
                    System.out.println("Unknown second argument: should be 0 or 1 or 2");
                }
            } catch (FileNotFoundException e) {
                System.out.println("Unable to find file called " + args[0]);
            }
        }
    }

    // TODO add a field with your ACTUAL HuffmanTree implementation.
    private static HuffmanTree tree; // Change type from Object to HuffmanTree or appropriate type you design.

    /**
     * This would be a good place to compute and store the tree.
     */
    public static HuffmanTree constructTree(String text) {
        // TODO Construct the ACTUAL HuffmanTree here to use with both encode and decode below.
        // TODO fill this in.

        PriorityQueue<HuffmanTree> queue = new PriorityQueue<HuffmanTree>();

        HashMap<Character, Integer> sortTree = new HashMap<Character, Integer>();

        for(int i = 0; i <= text.length(); i++){
            char c = text.charAt(i);           
            if(!sortTree.containsKey(c)){
                sortTree.put(c, 1);
            }else{
                sortTree.replace(c, sortTree.get(c).intValue(), sortTree.get(c).intValue()+1);
            }
        }
        for(char c : sortTree.keySet()){
            HuffmanTree hn = new HuffmanTree(c, sortTree.get(c), null, null);
            queue.add(hn);
        }

        while (queue.peek() != null) {
			HuffmanTree left = queue.poll();
			if (queue.peek() != null) {
				HuffmanTree right = queue.poll();

				//create Parent tree...
                int freq = left.getFrequency() + right.getFrequency();
                HuffmanTree parentTree = new HuffmanTree('\0', freq, left, right);
				queue.add(parentTree);
			}
            tree = left;
		}
        return tree;
    }
    
    /**
     * Take an input string, text, and encode it with the tree computed from the text. Should
     * return the encoded text as a binary string, that is, a string containing
     * only 1 and 0.
     */
    public static String encode(String text) {
        // TODO fill this in.


        String code = buildCode("", tree, "");




        return "";
    }
    
    /**
     * Take encoded input as a binary string, decode it using the stored tree,
     * and return the decoded text as a text string.
     */
    public static String decode(String encoded) {
        // TODO fill this in.
        return "";
    }

    // make a lookup table from symbols and their encodings
    private static String buildCode(String st, HuffmanTree x, String s) {
        if (!x.isLeaf()) {
            buildCode(st, x.left,  s + '0');
            buildCode(st, x.right, s + '1');
        }
        else {
            st = s;
        }
        return st;
    }
}


