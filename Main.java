// TODO: Could add difficulty selector to provide different levels of challenge where the lower bound is made bigger for each difficulty.
// TODO: Implement system to judge diffculty of question and reward more ===>>> perhaps if the product of nums is prime or odd etc. it is considereed harder than others.
// TODO: Use argv to allow user to request score file ===>>> read contents and System.out.println(fileContents/scores).
// TODO: Save highest streak per game ===>>> before streak wipe add to array, at end of game iterate through array and find largest number (bubble sort and grab first/last index).
package mathsGame;

import java.util.Scanner;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.io.File; // Import the File class
import java.io.FileWriter;
import java.io.IOException; // Import the IOException class to handle errors

public class Main {

  static Scanner scan = new Scanner(System.in); // Scanner initialization and declaration
  static Random rand = new Random();
  static int time = 60;
  static int score = 0;
  static int streak = 0;

  public static void main(String[] args) {

    final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    executorService.scheduleAtFixedRate(Main::timer, 0, 1, TimeUnit.SECONDS);

    while (time > 0) {

      System.out.println("\nTime remaining: " + time + " seconds");

      int int1 = rand.nextInt(13);
      int int2 = rand.nextInt(13);

      float startTime = System.nanoTime();

      int userAnswer = 0; // Initialising to 0 so Java doesn't whine at me.

      boolean answerGiven = false;
      while (!answerGiven) { // Could just use break instead - however potentially less semantic?
        try {
          System.out.println("\n" + int1 + "x" + int2 + ":");
          userAnswer = scan.nextInt();
          answerGiven = true;
        } catch (Exception e) {
          System.out.println("You must enter a number based input! int please :<)");
          scan.nextLine();
        }
      }

      float endTime = System.nanoTime();
      float answerTime = ((endTime - startTime) / 1000000000); // Divison converts to seconds

      if (userAnswer == int1 * int2) {
        streak += 1;

        if (answerTime < 1.5)
          score += 1 * streak * 10;
        else if (answerTime < 2)
          score += 1 * streak * 7;
        else if (answerTime < 2.5)
          score += 1 * streak * 4;
        else
          score += 1 * streak;
        System.out.println("\nCorrect! (score: " + score + ")");

      } else {
        streak = 0;
        System.out.println("\n(" + int1 * int2 + ") " + "Incorrect! (score: " + score + ")");
      }
    }

    // Currently never executed due to my only known method of interupting system
    // input - which is sys exit killing the program at ln 96.
    scan.close();
    executorService.shutdown();

  }

  public static void timer() {
    time -= 1;
    if (time == 0) {
      System.out.println("\n---------------- Time Up! ----------------\n\nYou scored: " + score + " with a streak of: "
          + streak + "\n");
      try {
        File myObj = new File("scores.txt");
        if (myObj.createNewFile()) {
          FileWriter myWriter = new FileWriter("scores.txt", true);
          myWriter.write("Score: " + score + "\n\n");
          myWriter.close();
        } else {
          FileWriter myWriter = new FileWriter("scores.txt", true);
          myWriter.write("Score: " + score + "\n\n");
          myWriter.close();
        }

      } catch (IOException e) {
        System.out.println("An error occured");
        e.printStackTrace();
      }
      System.exit(0);
    }
  }
}
