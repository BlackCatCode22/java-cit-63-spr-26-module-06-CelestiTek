# Zookeeper's Challenge - Implementation Summary

## Project Overview
The "Zookeeper's Challenge" is a Java application designed to manage a dynamic zoo environment by processing animal data, assigning names and IDs, calculating birth dates, and generating a comprehensive population report.

## Key Features Implemented

### 1. **Animal Class** (Animal.java)
- **Purpose**: Represents an individual animal with all associated attributes
- **Key Methods**:
  - `genBirthDay()`: Calculates birth date based on age and birth season using ISO 8601 format (YYYY-MM-DD)
  - `genUniqueID()`: Generates species-specific IDs (e.g., "Hy01", "Li02", "Ti03", "Be04")
  - `assignHabitat()`: Assigns animals to their respective habitats
- **Static Counters**: Maintains count of each species (numOfHyenas, numOfLions, numOfTigers, numOfBears) for unique ID generation
- **Attributes**: age, sex, species, color, weight, origin, birthDate, name, id, habitat, arrivalDate

### 2. **AnimalDataParser Class** (AnimalDataParser.java)
- **Purpose**: Handles file parsing and data extraction
- **Key Methods**:
  - `parseArrivingAnimals()`: Reads arrivingAnimals.txt and creates Animal objects
  - `parseAnimalLine()`: Extracts individual animal data using regex patterns
  - `parseAnimalNames()`: Reads animalNames.txt and organizes names by species
  - `getAllNamesInOrder()`: Assigns names to animals in the order they appear in the file
  - `calculateBirthDate()`: Computes precise birth dates based on age and season
- **Regex Patterns Used**:
  - Age: `(\d+)\s+year`
  - Sex: `(male|female)`
  - Species: `(hyena|lion|tiger|bear)`
  - Season: `born in (spring|summer|fall|winter|unknown birth season)`
  - Color: `([a-zA-Z\s]+)\s+color`
  - Weight: `(\d+)\s+pounds`
  - Origin: `from\s+(.+)$`

### 3. **ZookeeperChallenge Class** (ZookeeperChallenge.java)
- **Purpose**: Main application controller
- **Key Methods**:
  - `main()`: Orchestrates the entire process from file reading to report generation
  - `organizeByHabitat()`: Groups animals by habitat type
  - `generateReport()`: Writes formatted report to zooPopulation.txt
  - `printSummary()`: Outputs results to console
- **Arrival Date**: Set to current date (2026-03-31) for all animals

## Data Processing Flow

1. **Initialization**: Set arrival date and parse all animal names
2. **Parsing**: Extract animal details from arrivingAnimals.txt using regex
3. **Enhancement**: Calculate birth dates and assign unique IDs
4. **Assignment**: Assign names from animalNames.txt to animals in order
5. **Organization**: Group animals by habitat
6. **Reporting**: Generate detailed report with all animal information

## Unique ID Generation Strategy
- Format: `[Species Prefix][2-digit Sequential Number]`
- Examples:
  - Hyenas: Hy01, Hy02, Hy03, Hy04
  - Lions: Li01, Li02, Li03, Li04
  - Tigers: Ti01, Ti02, Ti03, Ti04
  - Bears: Be01, Be02, Be03, Be04
- Uses static counters per species to ensure uniqueness

## Birth Date Calculation
- **With Season Information**: Uses mid-season dates
  - Spring: March 21 (2022-03-21)
  - Summer: June 21
  - Fall: September 21
  - Winter: December 21
- **Without Season**: Uses July 1st as default
- Format: ISO 8601 (YYYY-MM-DD)

## Output Format
```
[ID]; [Name]; birth date: [YYYY-MM-DD]; [color] color; [sex]; [weight] pounds; from [origin]; arrived [YYYY-MM-DD]
```

Example:
```
Hy01; Shenzi; birth date: 2022-03-21; tan color; female; 70 pounds; from Friguia Park, Tunisia; arrived 2026-03-31
```

## Input Files
- **arrivingAnimals.txt**: Contains 16 animal descriptions
  - 4 Hyenas
  - 4 Lions
  - 4 Tigers
  - 4 Bears
- **animalNames.txt**: Contains names organized by species

## Output File
- **zooPopulation.txt**: Complete zoo population report organized by habitat

## Testing Results
✓ All 16 animals successfully parsed
✓ Unique IDs generated correctly for each species
✓ Birth dates calculated with season information
✓ Names assigned in proper order
✓ Animals organized by habitat
✓ Report generated with complete information

## Design Principles Applied
1. **Object-Oriented Design**: Encapsulation of data and methods in Animal class
2. **Separation of Concerns**: Parser logic separated from business logic
3. **Regex Pattern Matching**: Robust data extraction from unstructured text
4. **Static Members**: Efficient unique ID generation without external dependencies
5. **File I/O**: Read from and write to text files
6. **Data Structures**: LinkedHashMap for maintaining habitat order

