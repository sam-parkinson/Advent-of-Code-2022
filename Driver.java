public class Driver {
    public static void main(String[] args) {
        CalorieCounter calorieCounter = new CalorieCounter("inputs/day1.txt");

        System.out.println("Advent of Code 2022");

        System.out.println();
        System.out.println("Problem 1.1: " + calorieCounter.getMostCalories());
        System.out.println("Problem 1.2: " + calorieCounter.getTopThreeCalories());
    }
}