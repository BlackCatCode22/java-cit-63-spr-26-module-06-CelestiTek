# Zookeeper's Challenge - Complete Implementation

## 🎯 Project Objective
Develop a Java application to manage a dynamic zoo environment by processing animal data from input files, assigning unique identifiers and names, calculating birth dates, and generating a comprehensive population report.

## 📁 Project Structure
```
Module 06 - ZookeeperChallenge/
├── src/
│   ├── ZooMT/
│   │   ├── Animal.java              # Animal entity with ID and birthday generation
│   │   ├── AnimalDataParser.java    # Data parsing from text files
│   │   ├── ZookeeperChallenge.java  # Main application controller
│   │   └── zooPopulation.txt        # Generated report output
│   ├── arrivingAnimals.txt          # (From Module 04) Input animal data
│   └── animalNames.txt              # (From Module 04) Animal name database
├── bin/
│   └── ZooMT/                       # Compiled .class files
└── IMPLEMENTATION_SUMMARY.md        # Technical documentation
```

## 🔧 Core Classes

### 1. Animal.java
**Purpose**: Represents a zoo animal with all its attributes and methods

**Key Methods**:
```java
// Calculate birth date from age and season (ISO 8601 format)
public LocalDate genBirthDay()

// Generate unique ID (Hy01, Li02, Ti03, Be04, etc.)
public String genUniqueID()

// Assign animal to appropriate habitat
private String assignHabitat()
```

**Key Attributes**:
- age, sex, species, color, weight
- origin, birthDate, name, id
- habitat, arrivalDate
- Static counters: numOfHyenas, numOfLions, numOfTigers, numOfBears

### 2. AnimalDataParser.java
**Purpose**: Extract and parse data from input files

**Key Methods**:
```java
// Read and parse arrivingAnimals.txt
public static List<Animal> parseArrivingAnimals(String filePath, List<String> allNames)

// Extract individual animal details using regex
private static Animal parseAnimalLine(String line)

// Read and organize names by species
public static Map<String, List<String>> parseAnimalNames(String filePath)

// Calculate precise birth date from age and season
private static LocalDate calculateBirthDate(int age, String season)
```

### 3. ZookeeperChallenge.java
**Purpose**: Main application controller orchestrating the entire workflow

**Key Methods**:
```java
// Main entry point
public static void main(String[] args)

// Group animals by habitat
private static Map<String, List<Animal>> organizeByHabitat(List<Animal> animals)

// Generate and write report to file
private static void generateReport(Map<String, List<Animal>> animalsByHabitat, String outputPath)

// Display results to console
private static void printSummary(List<Animal> animals, Map<String, List<Animal>> animalsByHabitat)
```

## 📊 Data Processing Pipeline

```
Input Files
    ↓
[arrivingAnimals.txt + animalNames.txt]
    ↓
AnimalDataParser
    ├─→ Extract animal details using regex patterns
    ├─→ Calculate birth dates from age & season
    └─→ Assign names in order of appearance
    ↓
Create Animal Objects
    ├─→ Generate unique IDs (Hy01, Li01, etc.)
    ├─→ Assign habitats (species-based)
    └─→ Set arrival date
    ↓
Organize by Habitat
    ↓
Generate Report
    ↓
zooPopulation.txt
```

## 🔍 Regex Patterns Used for Parsing

| Data | Pattern | Example |
|------|---------|---------|
| Age | `(\d+)\s+year` | "4 year" → 4 |
| Sex | `(male\|female)` | "female" → female |
| Species | `(hyena\|lion\|tiger\|bear)` | "hyena" → hyena |
| Season | `born in (spring\|summer\|fall\|winter\|unknown)` | "born in spring" → spring |
| Color | `([a-zA-Z\s]+)\s+color` | "tan color" → tan |
| Weight | `(\d+)\s+pounds` | "70 pounds" → 70 |
| Origin | `from\s+(.+)$` | "from Friguia Park, Tunisia" → Friguia Park, Tunisia |

## 🆔 Unique ID Generation Strategy

- **Format**: `[Species Prefix][2-digit Sequence Number]`
- **No External ID Generator**: Uses static counters per species
- **Examples**:
  - Hyena: Hy01, Hy02, Hy03, Hy04
  - Lion: Li01, Li02, Li03, Li04
  - Tiger: Ti01, Ti02, Ti03, Ti04
  - Bear: Be01, Be02, Be03, Be04

## 📅 Birth Date Calculation

**With Season Information**:
- Spring → March 21
- Summer → June 21
- Fall → September 21
- Winter → December 21

**Without Season**: July 1st (default)

**Format**: ISO 8601 (YYYY-MM-DD)

**Algorithm**:
```java
LocalDate birthDate = today.minusYears(age);
// Adjust to mid-season date based on provided season
```

## 📄 Output Format

### Report Header
```
===== ZOO POPULATION REPORT =====
Date: 2026-03-31
Total Animals: 16
```

### Entry Format
```
[ID]; [Name]; birth date: [ISO-8601-Date]; [Color] color; [Sex]; [Weight] pounds; from [Origin]; arrived [Arrival-Date]
```

### Complete Example
```
Hy01; Shenzi; birth date: 2022-03-21; tan color; female; 70 pounds; from Friguia Park, Tunisia; arrived 2026-03-31
```

### Organized by Habitat
```
Hyena Habitat:
Hy01; Shenzi; ...
Hy02; Banzai; ...
[blank line]
Lion Habitat:
Li01; Scar; ...
Li02; Mufasa; ...
[blank line]
Tiger Habitat:
Ti01; Tony; ...
Ti02; Tigger; ...
[blank line]
Bear Habitat:
Be01; Yogi; ...
Be02; Smokey; ...
```

## 🎬 Running the Program

### Compilation
```bash
javac -d bin src/ZooMT/*.java
```

### Execution
```bash
java -cp bin ZooMT.ZookeeperChallenge
```

### Output
1. Console display of the full report
2. File saved to: `src/ZooMT/zooPopulation.txt`
3. Status message: "Report generated successfully: [path]"

## ✅ Testing Results

- **Total Animals Processed**: 16
- **Species Distribution**:
  - Hyenas: 4 (Hy01-Hy04)
  - Lions: 4 (Li01-Li04)
  - Tigers: 4 (Ti01-Ti04)
  - Bears: 4 (Be01-Be04)
- **Name Assignment**: Successfully matched all animals with names
- **ID Generation**: All IDs unique and correctly formatted
- **Birth Date Calculation**: All dates calculated with season information (except one with unknown season)
- **Report Generation**: Successfully created and formatted

## 📋 Sample Output - Hyena Habitat

```
Hyena Habitat:
Hy01; Shenzi; birth date: 2022-03-21; tan color; female; 70 pounds; from Friguia Park, Tunisia; arrived 2026-03-31
Hy02; Banzai; birth date: 2014-09-21; brown color; male; 150 pounds; from Friguia Park, Tunisia; arrived 2026-03-31
Hy03; Ed; birth date: 2022-03-21; black color; male; 120 pounds; from Friguia Park, Tunisia; arrived 2026-03-31
Hy04; Zig; birth date: 2018-07-01; black and tan striped color; female; 105 pounds; from Friguia Park, Tunisia; arrived 2026-03-31
```

## 🏗️ Design Patterns & Principles

1. **Object-Oriented Design**: Encapsulation of animal data and behavior
2. **Separation of Concerns**: 
   - Animal: Data model
   - AnimalDataParser: Data extraction
   - ZookeeperChallenge: Orchestration
3. **Regex-Based Parsing**: Robust extraction from unstructured text
4. **Static Members**: Efficient, no-dependency ID generation
5. **Collections Framework**: LinkedHashMap for maintaining insertion order
6. **File I/O**: Proper resource handling for reading/writing

## 🔐 Error Handling

- File not found exceptions caught and reported
- Parsing errors logged to console
- Graceful fallback for missing season information
- Missing animal names handled gracefully

## 📝 Code Quality

- ✓ Comprehensive JavaDoc comments
- ✓ Meaningful variable names
- ✓ Proper exception handling
- ✓ Regex patterns tested and validated
- ✓ No hardcoded values for ID generation
- ✓ Modular and reusable methods

## 🚀 Future Enhancements

- Database integration instead of text files
- Web interface for viewing population
- Animal health tracking
- Feeding schedule management
- Habitat environmental controls
- Export to multiple formats (CSV, JSON, XML)

---

**Status**: ✅ Complete and Fully Functional
**Last Updated**: March 31, 2026

