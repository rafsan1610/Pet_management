package pet_management;

public class Pet {
    private int id;
    private String name;
    private String breed;
    private String type;
    private String age;
    private String gender;
    private String weight;

    public Pet(int id, String name, String breed, String type, String age, String gender, String weight) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.type = type;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
    }

    public Pet(String name, String breed, String type, String age, String gender, String weight) {
        this(-1, name, breed, type, age, gender, weight);
    }

    public int getId() {
        return id;
    }

    // Getters and setters for all other fields
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }
    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getWeight() {
        return weight;
    }
    public void setWeight(String weight) {
        this.weight = weight;
    }
}
