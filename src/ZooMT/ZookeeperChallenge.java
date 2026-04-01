package ZooMT;

import java.io.*;
import java.time.LocalDate;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ZookeeperChallenge {

    public static void main(String[] args) {
        try {
            Path[] paths = resolveProjectPaths();
            String animalFilePath = paths[0].toString();
            String namesFilePath = paths[1].toString();
            String outputFilePath = paths[2].toString();

            // Set the arrival date for all animals
            Animal.setArrivalDate(LocalDate.now());

            // Parse the animal names in order
            List<String> allNames = AnimalDataParser.getAllNamesInOrder(animalFilePath, namesFilePath);

            // Parse arriving animals
            List<Animal> animals = AnimalDataParser.parseArrivingAnimals(animalFilePath, allNames);

            // Organize animals by habitat
            Map<String, List<Animal>> animalsByHabitat = organizeByHabitat(animals);

            // Generate and write report
            generateReport(animalsByHabitat, outputFilePath);

            // Print summary to console
            printSummary(animals, animalsByHabitat);

        } catch (IOException e) {
            System.err.println("Error processing files: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Resolve module 04 input files and module 06 output file for different working directories.
     */
    private static Path[] resolveProjectPaths() throws IOException {
        final String module04 = "java-cit-63-spr-26-module-04-CelestiTek";
        final String module06 = "java-cit-63-spr-26-module-06-CelestiTek";

        Path cwd = Paths.get(System.getProperty("user.dir")).toAbsolutePath().normalize();
        List<Path> candidates = new ArrayList<>();
        candidates.add(cwd);
        if (cwd.getParent() != null) {
            candidates.add(cwd.getParent());
        }
        if (cwd.getParent() != null && cwd.getParent().getParent() != null) {
            candidates.add(cwd.getParent().getParent());
        }

        for (Path root : candidates) {
            Path module04Src = root.resolve(module04).resolve("src");
            Path module06SrcZoo = root.resolve(module06).resolve("src").resolve("ZooMT");

            Path animalFile = module04Src.resolve("arrivingAnimals.txt");
            Path namesFile = module04Src.resolve("animalNames.txt");

            if (Files.exists(animalFile) && Files.exists(namesFile)) {
                Files.createDirectories(module06SrcZoo);
                Path outputFile = module06SrcZoo.resolve("zooPopulation.txt");
                return new Path[] { animalFile, namesFile, outputFile };
            }
        }

        throw new FileNotFoundException(
                "Could not locate required files. Expected to find " + module04 + "/src/arrivingAnimals.txt and "
                        + module04 + "/src/animalNames.txt near working directory: " + cwd);
    }

    /**
     * Organize animals by their habitat
     */
    private static Map<String, List<Animal>> organizeByHabitat(List<Animal> animals) {
        Map<String, List<Animal>> habitatMap = new LinkedHashMap<>();

        // Initialize habitats in a specific order
        habitatMap.put("Hyena Habitat", new ArrayList<>());
        habitatMap.put("Lion Habitat", new ArrayList<>());
        habitatMap.put("Tiger Habitat", new ArrayList<>());
        habitatMap.put("Bear Habitat", new ArrayList<>());

        // Add animals to their respective habitats
        for (Animal animal : animals) {
            String habitat = animal.getHabitat();
            if (habitatMap.containsKey(habitat)) {
                habitatMap.get(habitat).add(animal);
            }
        }

        return habitatMap;
    }

    /**
     * Generate the zooPopulation.txt report
     */
    private static void generateReport(Map<String, List<Animal>> animalsByHabitat, String outputPath) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(outputPath));

        writer.println("===== ZOO POPULATION REPORT =====");
        writer.println("Date: " + LocalDate.now());
        writer.println();

        for (Map.Entry<String, List<Animal>> entry : animalsByHabitat.entrySet()) {
            String habitat = entry.getKey();
            List<Animal> animals = entry.getValue();

            if (!animals.isEmpty()) {
                writer.println(habitat + ":");
                for (Animal animal : animals) {
                    writer.println(animal.toString());
                }
                writer.println();
            }
        }

        writer.close();
        System.out.println("Report generated successfully: " + outputPath);
    }

    /**
     * Print summary to console
     */
    private static void printSummary(List<Animal> animals, Map<String, List<Animal>> animalsByHabitat) {
        System.out.println("===== ZOO POPULATION REPORT =====");
        System.out.println("Date: " + LocalDate.now());
        System.out.println("Total Animals: " + animals.size());
        System.out.println();

        for (Map.Entry<String, List<Animal>> entry : animalsByHabitat.entrySet()) {
            String habitat = entry.getKey();
            List<Animal> animals2 = entry.getValue();

            if (!animals2.isEmpty()) {
                System.out.println(habitat + ":");
                for (Animal animal : animals2) {
                    System.out.println(animal.toString());
                }
                System.out.println();
            }
        }
    }
}

