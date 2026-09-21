import java.util.ArrayList;

public class OCLibrary {
    private ArrayList<OC> characters = new ArrayList<>();

    public void add(OC character) {
        characters.add(character);
    }

    public OC get(int index) {
        return characters.get(index);
    }

    public OC remove(int index) {
        return characters.remove(index);
    }

    public int size() {
        return characters.size();
    }

    public boolean isEmpty() {
        return characters.isEmpty();
    }

    public ArrayList<OC> getAll() {
        return characters;
    }

    public OC findByName(String name) {
        for (OC character : characters) {
            if (character.getName().equalsIgnoreCase(name)) {
                return character;
            }
        }
        return null;
    }
}
