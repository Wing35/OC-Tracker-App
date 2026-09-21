import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Reads and writes the character list to a plain text file so your
 * characters are still there the next time you run the program.
 *
 * File format: one character per line, fields separated by a tab.
 *     name <TAB> age <TAB> description
 */
public class Storage {
    private static final String FILE_NAME = "characters.txt";
    private static final String SEPARATOR = "\t";

    public static void save(OCLibrary library) {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (OC character : library.getAll()) {
                out.println(clean(character.getName())
                          + SEPARATOR + character.getAge()
                          + SEPARATOR + clean(character.getDescription()));
            }
            System.out.println("Saved " + library.size() + " character(s) to " + FILE_NAME);
        } catch (IOException e) {
            System.out.println("Could not save: " + e.getMessage());
        }
    }

    public static void load(OCLibrary library) {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return; // First run - nothing to load yet, and that's fine.
        }

        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = in.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] parts = line.split(SEPARATOR, -1);
                if (parts.length != 3) {
                    System.out.println("Skipping a line I couldn't read: " + line);
                    continue;
                }
                try {
                    String name = parts[0];
                    int age = Integer.parseInt(parts[1].trim());
                    String description = parts[2];
                    library.add(new OC(name, description, age));
                } catch (NumberFormatException e) {
                    System.out.println("Skipping a line with a bad age: " + line);
                }
            }
            System.out.println("Loaded " + library.size() + " character(s).");
        } catch (IOException e) {
            System.out.println("Could not load: " + e.getMessage());
        }
    }

    /**
     * Tabs and newlines would break the one-line-per-character format,
     * so they get swapped for spaces on the way out.
     */
    private static String clean(String text) {
        return text.replace("\t", " ").replace("\n", " ").replace("\r", " ");
    }
}
