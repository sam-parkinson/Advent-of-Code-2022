public class Driver {
    public static void main(String[] args) {
        CalorieCounter calorieCounter = new CalorieCounter("inputs/day1.txt");
        RockPaperScissors rockPaperScissors = new RockPaperScissors("inputs/day2.txt");
        RucksackSorter rucksackSorter = new RucksackSorter("inputs/day3.txt");
        CleanupAssignments cleanupAssignments = new CleanupAssignments("inputs/day4.txt");
        CrateStacker crateStacker = new CrateStacker("inputs/day5.txt");

        System.out.println("Advent of Code 2022");

        System.out.println();
        System.out.println("Problem 1.1: " + calorieCounter.getMostCalories());
        System.out.println("Problem 1.2: " + calorieCounter.getTopThreeCalories());

        System.out.println();
        System.out.println("Problem 2.1: " + rockPaperScissors.getHelpfulScore());
        System.out.println("Problem 2.2: " + rockPaperScissors.getProperScore());

        System.out.println();
        System.out.println("Problem 3.1: " + rucksackSorter.getTotalCommonPriority());
        System.out.println("Problem 3.2: " + rucksackSorter.getTotalBadgePriority());

        System.out.println();
        System.out.println("Problem 4.1: " + cleanupAssignments.getMutualOverlapCount());
        System.out.println("Problem 4.2: " + cleanupAssignments.getAnyOverlapCount());

        System.out.println();
        System.out.println("Problem 5.1: " + crateStacker.getTopCrates9000());
        System.out.println("Problem 5.2: " + crateStacker.getTopCrates9001());
    }
}