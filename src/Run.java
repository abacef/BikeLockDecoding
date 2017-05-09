import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author Mark Nash
 */
public class Run {

    /** https://en.oxforddictionaries.com/definition/"; */
    private static final String baseURL = "http://www.learnersdictionary" +
            ".com/definition/";

    /** "No exact matches found for"; */
    private static final String notAMatch = "The word you have entered is not" +
            " in the dictionary";

    private PrintWriter possibleWriter;

    private PrintWriter validWriter;

    private int possibleCount;

    private int validCount;

    private void check(String word) {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;

        possibleWriter.println(word);
        possibleCount++;

        try {
            url = new URL(baseURL + word);
            is = url.openStream();
            br = new BufferedReader(new InputStreamReader(is));

            boolean flag = false;
            while ((line = br.readLine()) != null) {
                if (line.contains(notAMatch)) {
                    flag = true;
                }
            }
            if (flag) {
                System.out.println(word + " is not a word");
            }
            else {
                System.out.println(word + " IS A WORD!");
                validCount++;
                validWriter.println(word);
            }
        } catch (FileNotFoundException fnf) {
            System.out.println(word + " is not a word");
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
    }

    public void run(ArrayList<String[]> cylenders) {
        long time = System.currentTimeMillis();
        try {
            validWriter = new PrintWriter(new FileWriter(new File
                    ("data/allValidWords")));
            possibleWriter = new PrintWriter(new FileWriter(new File
                    ("data/allPossibleWords")));
        } catch (IOException e) {
            // Squash
        }
        for (int i0 = 0; i0 < cylenders.get(0).length; i0++) {
            for (int i1 = 0; i1 < cylenders.get(1).length; i1++) {
                for (int i2 = 0; i2 < cylenders.get(2).length; i2++) {
                    for (int i3 = 0; i3 < cylenders.get(3).length; i3++) {
                        check(cylenders.get(0)[i0] + cylenders.get(1)[i1] +
                                cylenders.get(2)[i2] + cylenders.get(3)[i3]);
                    }
                }
            }
        }
        possibleWriter.println("Possible words: " + possibleCount);
        validWriter.println("Valid words: " + validCount);
        System.out.println("This process took: " + (System.currentTimeMillis()
                - time) / 1000 + " seconds.");
    }
}
