import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author Mark Nash
 */
public class Run {

    /** The url for the dictionary website */
    private static final String baseURL = "http://www.learnersdictionary" +
            ".com/definition/";

    /** A string that says that there is not a match */
    private static final String notAMatch = "The word you have entered is not" +
            " in the dictionary";

    /** A reference to the writer for all of the possible words */
    private PrintWriter possibleWriter;

    /** A reference to the writer for all of the valid words */
    private PrintWriter validWriter;

    /** A counter for all of the possible words */
    private int possibleCount;

    /** A counter for all of the valid words */
    private int validCount;

    /**
     * @param word a string to check if it is actually a word
     * @return if the parameter is actually a word or not
     */
    private boolean check(String word) {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;

        possibleWriter.println(word);
        possibleCount++;

        if (!word.contains("a") && !word.contains("e") && !word.contains("i")
                && !word.contains("o") && !word.contains("u")) {
            System.out.println(word + " is not a word");
            return false;
        }

        try {
            url = new URL(baseURL + word);
            is = url.openStream();
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                if (line.contains(notAMatch)) {
                    System.out.println(word + " is not a word");
                    return false;
                }
            }
            System.out.println(word + " IS A WORD!");
            validCount++;
            validWriter.println(word);
            return true;

        } catch (FileNotFoundException fnf) {
            System.out.println(word + " is not a word");
            return false;
        }
        catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                // Squash
            }
        }
        return false;
    }

    /**
     * initializes some variables and runs every possible combination. Then
     * it prints out the time it took to run the code.
     *
     * @param cylinders an array of strings of the cylinders of the lock
     *                  going from left to right.
     */
    public void run(ArrayList<String[]> cylinders) {
        long time = System.currentTimeMillis();
        try {
            validWriter = new PrintWriter(new FileWriter(new File
                    ("data/allValidWords")));
            possibleWriter = new PrintWriter(new FileWriter(new File
                    ("data/allPossibleWords")));
        } catch (IOException e) {
            // Squash
        }
        for (int i0 = 0; i0 < cylinders.get(0).length; i0++) {
            for (int i1 = 0; i1 < cylinders.get(1).length; i1++) {
                for (int i2 = 0; i2 < cylinders.get(2).length; i2++) {
                    for (int i3 = 0; i3 < cylinders.get(3).length; i3++) {
                        check(cylinders.get(0)[i0] + cylinders.get(1)[i1] +
                                cylinders.get(2)[i2] + cylinders.get(3)[i3]);
                    }
                }
            }
        }
        possibleWriter.println("Possible words: " + possibleCount);
        validWriter.println("Valid words: " + validCount);
        System.out.println("This process took: " + (System.currentTimeMillis()
                - time) / (1000 * 60) + " minutes.");
        validWriter.close();
        possibleWriter.close();
    }
}
