package pet_management;

public class Pet {
    private String name, breed, type, age, gender, weight;

    public Pet(String name, String breed, String type, String age, String gender, String weight) {
        this.name = name;
        this.breed = breed;
        this.type = type;
        this.age = age;
        this.gender = gender;
        this.weight = weight;
    }

    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getBreed() { return breed; }
    public void setBreed(String breed) { this.breed = breed; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getWeight() { return weight; }
    public void setWeight(String weight) { this.weight = weight; }
}
