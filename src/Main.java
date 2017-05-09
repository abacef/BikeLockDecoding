import java.io.*;
import java.util.ArrayList;

/**
 * Takes in an input file. Each line represents the letters on a cylinder,
 * starting from the left, and moving right. Only works with 4 cylindered locks.
 * @author Mark Nash
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        ArrayList<String[]> cylenders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File
                (args[0])))) {
            String line;
            while ((line = reader.readLine()) != null) {
                cylenders.add(line.split(" "));
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Your file was not found.");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Run run = new Run();
        run.run(cylenders);
    }

}
