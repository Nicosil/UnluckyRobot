/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unluckyrobot;

import java.util.Scanner;
import java.util.Random;

/**
 *
 * @author Nicolas Nguyen
 */
public class UnluckyRobot {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Input
        Scanner console = new Scanner(System.in);
        int totalScore = 300;
        int itrCount = 0;
        int x = 0;
        int y = 0;
        do {
            displayInfo(x, y, itrCount, totalScore);
            char direction = inputDirection();
            switch (direction) {
                case 'u':
                    y++;
                    totalScore -= 10;
                    break;
                case 'd':
                    y--;
                    totalScore -= 50;
                    break;
                case 'r':
                    x++;
                    totalScore -= 50;
                    break;
                default:
                    x--;
                    totalScore -= 50;
                    break;
            }
            if (doesExceed(x, y, direction)) {
                totalScore -= 2000;
                System.out.println("Exceed boundary, -2000 applied");
            }
            // Makes location remain the same when grid limits is exceeded
            if (x > 4) {
                x--;
            }
            if (y > 4) {
                y--;
            }
            int reward = reward();
            reward = punishOrMercy(direction, reward);
            totalScore += reward;
            System.out.println();
            itrCount++;
        } while (!isGameOver(x, y, totalScore, itrCount));
        String toTitleCase = console.next();
        evaluation(totalScore);
    }

    /**
     * Indicates the position of the robot with coordinates x and y and displays
     * the total score when the robot moves in each specified iteration
     *
     * @param x the x coordinate of the robot relating to his position
     * @param y the y coordinate of the robot relating to his position
     * @param itrCount keeps track of the number of iterations made so far
     * @param totalScore the total number of points obtained
     */
    public static void displayInfo(int x, int y, int itrCount, int totalScore) {
        System.out.printf("For point(X=%d, Y=%d) at iterations: %d "
                + "the total score is: %d\n", x, y, itrCount, totalScore);
    }

    /**
     * Receives the coordinates x and y of the robot and the direction inputted
     * by the user, only returns if the robot exceeds the grid limits
     *
     * @param x the x coordinate of the robot relating to his direction
     * @param y the y coordinate of the robot relating to his direction
     * @param direction the desired direction inputted by user that the robot
     * will move towards (list of supported directions below) 'r' means robot
     * moves to the right 'l' means robot moves to the left 'u' means robot
     * moves up 'd' means robot moves down
     * @return only when the robot has exceeded the grid limits, if that
     * condition is false then it does nothing
     */
    public static boolean doesExceed(int x, int y, char direction) {
        return (y > 4 && Character.toLowerCase(direction) == 'u' || x < 0
                && Character.toLowerCase(direction) == 'l' || y < 0
                && Character.toLowerCase(direction) == 'd' || x > 4
                && Character.toLowerCase(direction) == 'r');
    }

    /**
     * Makes a dice roll and return a number based on the rule displayed below,
     * also displays the result of the dice and the value of the reward If 1 is
     * displayed it will return -100 If 2 is displayed it will return -200 If 3
     * is displayed it will return -300 If 4 is displayed it will return 300 If
     * 5 is displayed it will return 400 If 6 is displayed it will return 600
     *
     * @return the value of the reward depending on the result of the dice
     */
    public static int reward() {
        Random rand = new Random();
        int dice = rand.nextInt(6) + 1;
        switch (dice) {
            case 1:
                System.out.println("Dice 1, reward: -100");
                return -100;
            case 2:
                System.out.println("Dice 2, reward: -200");
                return -200;
            case 3:
                System.out.println("Dice 3, reward: -300");
                return -300;
            case 4:
                System.out.println("Dice 4, reward: 300");
                return 300;
            case 5:
                System.out.println("Dice 5, reward: 400");
                return 400;
            case 6:
                System.out.println("Dice 6, reward: 600");
                return 600;
        }
        return reward();
    }

    /**
     * Checks if the reward value is negative and if the direction is 'u' (up)
     * if those conditions are met it will flip a newly generated coin
     *
     * @param direction the direction of the robot inputted by the user
     * @param reward the reward obtained by robot after rolling the dice
     * @return the result of the coin flip, it will decide if the negative
     * reward is applied or removed depending on the side of the coin
     */
    public static int punishOrMercy(char direction, int reward) {
        if (reward < 0 && direction == 'u') {
            Random rand = new Random();
            int coin = rand.nextInt(2);
            if (coin == 0) {
                reward = 0;
                System.out.println("Coin: tail | Mercy, the negative reward is "
                        + "removed.");
            } else {
                System.out.println("Coin: head | No mercy, the negative "
                        + "reward is " + "applied.");
                return reward;
            }
        }
        return reward;
    }

    /**
     * Convert the string to title case assuming that it only contains two words
     * separated by a space
     *
     * @param str the string inputted by user
     * @return string in title case format
     */
    public static String toTitleCase(String str) {
        char firstName = str.charAt(0);
        String string1 = str.substring(1, str.indexOf(' ') + 1);
        String string2 = str.substring(str.indexOf(' '), str.indexOf(' ') + 2);
        char lastName = string2.charAt(1);
        String string3 = str.substring(str.indexOf(' ') + 2, str.length());
        String string4 = Character.toTitleCase(firstName) + string1.toLowerCase()
                + Character.toTitleCase(lastName) + string3.toLowerCase();
        return string4;
    }

    /**
     * Takes the input of the total score obtained by the robot and prints a
     * statement based on the value of the total score
     *
     * @param totalScore the total score obtained by the robot
     */
    public static void evaluation(int totalScore) {
        Scanner console = new Scanner(System.in);
        System.out.print("Please enter you name (only two words): ");
        String name = console.nextLine();
        if (totalScore >= 2000) {
            System.out.print("Victory! " + toTitleCase(name)
                    + ", your score is " + totalScore);
        } else {
            System.out.print("Mission failed! " + toTitleCase(name)
                    + ", your score is " + totalScore);
        }
    }

    /**
     * Ask user to input the letter of the supported directions will keep asking
     * until one of the below direction is inputted 'r' means robot moves to the
     * right 'l' means robot moves to the left 'u' means robot moves up 'd'
     * means robot moves down
     *
     * @return the supported direction inputted by the user
     */
    public static char inputDirection() {
        Scanner console = new Scanner(System.in);
        char outConsole = ' ';
        do {
            System.out.print("Please input a valid direction: ");
            char directChar = console.nextLine().toLowerCase().charAt(0);
            if (directChar == 'u' || directChar == 'd' || directChar == 'l'
                    || directChar == 'r') {
                outConsole = directChar;
            }
        } while (outConsole == ' ');
        return outConsole;
    }

    /**
     * Terminates the game when any of the below condition are met 1. The number
     * of the steps/iterations exceeds 20 2. Your total score falls under -1,000
     * 3. Your total score exceeds 2,000 4. You reach one of the end locations
     * (x=4, y=4) or (x=4, y=0)
     *
     * @param x the x coordinate of the robot relating to his position
     * @param y the y coordinate of the robot relating to his position
     * @param totalScore the total score obtained by the robot
     * @param itrCount the number of steps/iterations
     * @return the game is terminated when at least one of the condition is met
     */
    public static boolean isGameOver(int x, int y, int totalScore, int itrCount) {
        if (itrCount > 20 || totalScore < -1000 || totalScore > 2000
                || x == 4 && y == 4 || x == 4 && y == 0) {
            evaluation(totalScore);
            return true;
        } else {
            return false;
        }
    }
}
