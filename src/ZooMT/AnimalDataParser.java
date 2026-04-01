package ZooMT;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnimalDataParser {

    /**
     * Parse the arrivingAnimals.txt file and return a list of Animal objects
     */
    public static List<Animal> parseArrivingAnimals(String filePath, List<String> allNames) throws IOException {
        List<Animal> animals = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        int animalIndex = 0;

        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            Animal animal = parseAnimalLine(line);
            if (animal != null) {
                // Assign a name from the list
                if (animalIndex < allNames.size()) {
                    animal.setName(allNames.get(animalIndex));
                    animalIndex++;
                }
                animals.add(animal);
            }
        }

        reader.close();
        return animals;
    }

    /**
     * Parse a single line of animal data
     * Format: "4 year old female hyena, born in spring, tan color, 70 pounds, from Friguia Park, Tunisia"
     */
    private static Animal parseAnimalLine(String line) {
        try {
            // Extract age
            Pattern agePattern = Pattern.compile("(\\d+)\\s+year");
            Matcher ageMatcher = agePattern.matcher(line);
            if (!ageMatcher.find()) return null;
            int age = Integer.parseInt(ageMatcher.group(1));

            // Extract sex (male/female)
            Pattern sexPattern = Pattern.compile("(male|female)");
            Matcher sexMatcher = sexPattern.matcher(line);
            if (!sexMatcher.find()) return null;
            String sex = sexMatcher.group(1);

            // Extract species (hyena, lion, tiger, bear)
            Pattern speciesPattern = Pattern.compile("(hyena|lion|tiger|bear)");
            Matcher speciesMatcher = speciesPattern.matcher(line);
            if (!speciesMatcher.find()) return null;
            String species = speciesMatcher.group(1);

            // Extract birth season
            Pattern seasonPattern = Pattern.compile("born in (spring|summer|fall|winter|unknown birth season)");
            Matcher seasonMatcher = seasonPattern.matcher(line);
            String season = "unknown";
            if (seasonMatcher.find()) {
                season = seasonMatcher.group(1);
            }

            // Extract color
            Pattern colorPattern = Pattern.compile("([a-zA-Z\\s]+)\\s+color");
            Matcher colorMatcher = colorPattern.matcher(line);
            if (!colorMatcher.find()) return null;
            String color = colorMatcher.group(1).trim();

            // Extract weight
            Pattern weightPattern = Pattern.compile("(\\d+)\\s+pounds");
            Matcher weightMatcher = weightPattern.matcher(line);
            if (!weightMatcher.find()) return null;
            int weight = Integer.parseInt(weightMatcher.group(1));

            // Extract origin (from X, Y or from X)
            Pattern originPattern = Pattern.compile("from\\s+(.+)$");
            Matcher originMatcher = originPattern.matcher(line);
            String origin = "Unknown";
            if (originMatcher.find()) {
                origin = originMatcher.group(1).trim();
            }

            // Create animal
            Animal animal = new Animal(age, sex, species, color, weight, origin);

            // Set birth date with season information
            animal.setBirthDate(calculateBirthDate(age, season));

            return animal;
        } catch (Exception e) {
            System.err.println("Error parsing line: " + line);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Calculate birth date based on age and season
     */
    private static LocalDate calculateBirthDate(int age, String season) {
        LocalDate today = LocalDate.now();
        LocalDate birthDate = today.minusYears(age);

        // Adjust to mid-season date
        Month month;
        if (season.equals("spring")) {
            month = Month.MARCH;
            birthDate = birthDate.withMonth(3).withDayOfMonth(21);
        } else if (season.equals("summer")) {
            month = Month.JUNE;
            birthDate = birthDate.withMonth(6).withDayOfMonth(21);
        } else if (season.equals("fall")) {
            month = Month.SEPTEMBER;
            birthDate = birthDate.withMonth(9).withDayOfMonth(21);
        } else if (season.equals("winter")) {
            month = Month.DECEMBER;
            birthDate = birthDate.withMonth(12).withDayOfMonth(21);
        } else {
            // unknown birth season - use July 1st as default
            birthDate = birthDate.withMonth(7).withDayOfMonth(1);
        }

        return birthDate;
    }

    /**
     * Parse the animalNames.txt file and return a list of names by species
     */
    public static Map<String, List<String>> parseAnimalNames(String filePath) throws IOException {
        Map<String, List<String>> namesMap = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        String currentSpecies = null;

        while ((line = reader.readLine()) != null) {
            line = line.trim();

            if (line.endsWith("Names:")) {
                currentSpecies = line.replace(" Names:", "").toLowerCase();
                namesMap.put(currentSpecies, new ArrayList<>());
            } else if (!line.isEmpty() && currentSpecies != null) {
                // Split by comma and add each name
                String[] names = line.split(",");
                for (String name : names) {
                    String trimmedName = name.trim();
                    if (!trimmedName.isEmpty()) {
                        namesMap.get(currentSpecies).add(trimmedName);
                    }
                }
            }
        }

        reader.close();
        return namesMap;
    }

    /**
     * Merge all animal names into a single list in the order they should be assigned
     * based on the order of species in the arrivingAnimals.txt file
     */
    public static List<String> getAllNamesInOrder(String animalFilePath, String namesFilePath) throws IOException {
        // Parse names map
        Map<String, List<String>> namesMap = parseAnimalNames(namesFilePath);

        // Initialize name indices for each species
        Map<String, Integer> nameIndices = new HashMap<>();
        for (String species : namesMap.keySet()) {
            nameIndices.put(species, 0);
        }

        // Read through animals file and assign names in order
        List<String> allNames = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(animalFilePath));
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) continue;

            // Extract species
            Pattern speciesPattern = Pattern.compile("(hyena|lion|tiger|bear)");
            Matcher speciesMatcher = speciesPattern.matcher(line);
            if (speciesMatcher.find()) {
                String species = speciesMatcher.group(1);
                List<String> names = namesMap.get(species);
                if (names != null) {
                    int index = nameIndices.get(species);
                    if (index < names.size()) {
                        allNames.add(names.get(index));
                        nameIndices.put(species, index + 1);
                    }
                }
            }
        }

        reader.close();
        return allNames;
    }
}


