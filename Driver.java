public class Driver {
    public static void main(String[] args) {
        CalorieCounter calorieCounter = new CalorieCounter("inputs/day1.txt");
        RockPaperScissors rockPaperScissors = new RockPaperScissors("inputs/day2.txt");
        RucksackSorter rucksackSorter = new RucksackSorter("inputs/day3.txt");
        CleanupAssignments cleanupAssignments = new CleanupAssignments("inputs/day4.txt");
        CrateStacker crateStacker = new CrateStacker("inputs/day5.txt");
        SignalTuner signalTuner = new SignalTuner("inputs/day6.txt");
        FileSystem fileSystem = new FileSystem("inputs/day7.txt");
        Forest forest = new Forest("inputs/day8.txt");
        RopeBridge ropeBridge = new RopeBridge("inputs/day9.txt");
        CathodeRayTube cathodeRayTube = new CathodeRayTube("inputs/day10.txt");
        MonkeyTroop monkeyTroop = new MonkeyTroop("inputs/test.txt");

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

        System.out.println();
        System.out.println("Problem 6.1: " + signalTuner.getFirstPacketMarker());
        System.out.println("Problem 6.2: " + signalTuner.getFirstMessageMarker());

        System.out.println();
        System.out.println("Problem 7.1: " + fileSystem.getSmallDirectorySize());
        System.out.println("Problem 7.2: " + fileSystem.getMinDeletableDirSize());

        System.out.println();
        System.out.println("Problem 8.1: " + forest.getVisibleTreesCount());
        System.out.println("Problem 8.2: " + forest.getMaxVisibilityScore());

        System.out.println();
        System.out.println("Problem 9.1: " + ropeBridge.getTailVisitedCount());
        System.out.println("Problem 9.2: " + ropeBridge.getLongTailVisitedCount());

        System.out.println();
        System.out.println("Problem 10.1: " + cathodeRayTube.getInterestingStrengths());
        System.out.println("Problem 10.2: "); // uncomment below to see working
        // cathodeRayTube.viewScreen();

        System.out.println();
        System.out.println("Problem 11.1: " + monkeyTroop.getMonkeyBusinessCalm());
        System.out.println("Problem 11.2: " + monkeyTroop.getMonkeyBusinessWild());
    }
}