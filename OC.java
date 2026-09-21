public class OC {
    private String name;
    private String description;
    private int age;

    public OC(String name, String description, int age) {
        this.name = name;
        this.description = description;
        this.age = age;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    
    @Override
    public String toString() {
        return name + " (age " + age + ")";
    }

    
    public String getDetails() {
        return "Name:        " + name
             + "\nAge:         " + age
             + "\nDescription: " + description;
    }
}
