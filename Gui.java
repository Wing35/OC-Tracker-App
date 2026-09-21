import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Insets;

public class Gui {

    private OCLibrary library = new OCLibrary();

    private DefaultListModel<OC> listModel = new DefaultListModel<>();
    private JList<OC> characterList = new JList<>(listModel);
    private JTextArea details = new JTextArea();

    public Gui() {
        Storage.load(library);
        for (OC character : library.getAll()) {
            listModel.addElement(character);
        }

        JFrame frame = new JFrame("OC Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650, 400);

        details.setEditable(false);
        details.setLineWrap(true);
        details.setWrapStyleWord(true);
        details.setMargin(new Insets(10, 10, 10, 10));

        characterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        characterList.addListSelectionListener(event -> showSelected());

        JButton addButton = new JButton("Add character");
        addButton.addActionListener(event -> addCharacter());

        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(event -> deleteSelected());

        JButton editButton = new JButton("Edit");
        editButton.addActionListener(event -> editSelected());

        JPanel buttons = new JPanel();
        buttons.add(addButton);
        buttons.add(deleteButton);
        buttons.add(editButton);

        JSplitPane split = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(characterList),
                new JScrollPane(details));
        split.setDividerLocation(200);

        frame.add(split, BorderLayout.CENTER);
        frame.add(buttons, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void showSelected() {
        OC selected = characterList.getSelectedValue();
        if (selected == null) {
            details.setText("");
        } else {
            details.setText(selected.getDetails());
        }
    }

   
    private void addCharacter() {
        String name = JOptionPane.showInputDialog("Name:");
        if (name == null || name.isBlank()) {
            return; 
        }

        String ageText = JOptionPane.showInputDialog("Age:");
        if (ageText == null) {
            return;
        }
        int age;
        try {
            age = Integer.parseInt(ageText.trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Age has to be a whole number.");
            return;
        }

        String description = JOptionPane.showInputDialog("Description:");
        if (description == null) {
            description = "";
        }

        OC character = new OC(name.trim(), description.trim(), age);
        library.add(character);
        listModel.addElement(character);
        Storage.save(library);
    }

    private void deleteSelected() {
        int index = characterList.getSelectedIndex();
        if (index == -1) {
            JOptionPane.showMessageDialog(null, "Pick a character first.");
            return;
        }

        OC character = library.get(index);
        int answer = JOptionPane.showConfirmDialog(
                null, "Delete " + character.getName() + "?", "Confirm",
                JOptionPane.YES_NO_OPTION);

        if (answer == JOptionPane.YES_OPTION) {
            library.remove(index);
            listModel.remove(index);
            Storage.save(library);
        }
    }

    private void editSelected() {
    int index = characterList.getSelectedIndex();
    if (index == -1) {
        JOptionPane.showMessageDialog(null, "Pick a character first.");
        return;
    }

    OC character = library.get(index);

    String name = (String) JOptionPane.showInputDialog(
            null, "Name:", "Edit Character",
            JOptionPane.PLAIN_MESSAGE, null, null, character.getName());
    if (name == null || name.isBlank()) {
        return; // user cancelled
    }

    String ageText = (String) JOptionPane.showInputDialog(
            null, "Age:", "Edit Character",
            JOptionPane.PLAIN_MESSAGE, null, null, String.valueOf(character.getAge()));
    if (ageText == null) {
        return;
    }
    int age;
    try {
        age = Integer.parseInt(ageText.trim());
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Age has to be a whole number.");
        return;
    }

    String description = (String) JOptionPane.showInputDialog(
            null, "Description:", "Edit Character",
            JOptionPane.PLAIN_MESSAGE, null, null, character.getDescription());
    if (description == null) {
        return;
    }

    character.setName(name.trim());
    character.setAge(age);
    character.setDescription(description.trim());

    listModel.set(index, character); 
    showSelected();                  
    Storage.save(library);
}

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new Gui());
    }
}
