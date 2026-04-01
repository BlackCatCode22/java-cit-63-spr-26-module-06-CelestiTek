package ZooMT;

import java.time.LocalDate;
import java.time.Month;

public class Animal {
    private int age;
    private String sex;
    private String species;
    private String color;
    private int weight;
    private String origin;
    private LocalDate birthDate;
    private String name;
    private String id;
    private String habitat;
    private static LocalDate arrivalDate;

    // Static counters for each species
    private static int numOfHyenas = 0;
    private static int numOfLions = 0;
    private static int numOfTigers = 0;
    private static int numOfBears = 0;

    public Animal(int age, String sex, String species, String color, int weight, String origin) {
        this.age = age;
        this.sex = sex;
        this.species = species;
        this.color = color;
        this.weight = weight;
        this.origin = origin;
        this.birthDate = genBirthDay();
        this.id = genUniqueID();
        this.habitat = assignHabitat();
        // Set arrival date to today if not already set
        if (arrivalDate == null) {
            arrivalDate = LocalDate.now();
        }
    }

    /**
     * Generate a birth date based on age and birth season
     * ISO 8601 format (YYYY-MM-DD)
     */
    public LocalDate genBirthDay() {
        LocalDate today = LocalDate.now();
        LocalDate birthDate = today.minusYears(this.age);
        
        // Extract birth season from the age string (will be done in parser)
        // For now, we'll use a default mid-season date
        // This will be refined when parsing the actual data
        return birthDate;
    }

    /**
     * Generate a unique ID for the animal
     * Format: Species abbreviation + sequential number (e.g., "Hy01", "Li02")
     */
    public String genUniqueID() {
        String prefix = getSpeciesPrefix();
        int count = 0;
        
        switch (this.species.toLowerCase()) {
            case "hyena":
                numOfHyenas++;
                count = numOfHyenas;
                break;
            case "lion":
                numOfLions++;
                count = numOfLions;
                break;
            case "tiger":
                numOfTigers++;
                count = numOfTigers;
                break;
            case "bear":
                numOfBears++;
                count = numOfBears;
                break;
        }
        
        return String.format("%s%02d", prefix, count);
    }

    /**
     * Get the species prefix for ID generation
     */
    private String getSpeciesPrefix() {
        switch (this.species.toLowerCase()) {
            case "hyena":
                return "Hy";
            case "lion":
                return "Li";
            case "tiger":
                return "Ti";
            case "bear":
                return "Be";
            default:
                return "An";
        }
    }

    /**
     * Assign the animal to its habitat based on species
     */
    private String assignHabitat() {
        switch (this.species.toLowerCase()) {
            case "hyena":
                return "Hyena Habitat";
            case "lion":
                return "Lion Habitat";
            case "tiger":
                return "Tiger Habitat";
            case "bear":
                return "Bear Habitat";
            default:
                return "Unknown Habitat";
        }
    }

    // Getters and Setters
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public static LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public static void setArrivalDate(LocalDate date) {
        arrivalDate = date;
    }

    public static int getNumOfHyenas() {
        return numOfHyenas;
    }

    public static int getNumOfLions() {
        return numOfLions;
    }

    public static int getNumOfTigers() {
        return numOfTigers;
    }

    public static int getNumOfBears() {
        return numOfBears;
    }

    @Override
    public String toString() {
        return String.format("%s; %s; birth date: %s; %s color; %s; %d pounds; from %s; arrived %s",
                id, name, birthDate, color, sex, weight, origin, arrivalDate);
    }
}

