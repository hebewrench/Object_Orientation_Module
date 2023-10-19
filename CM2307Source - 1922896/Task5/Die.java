import java.util.*;

public class Die extends Game implements PlayGame{
  //Variable used in the die game methods
    public HashSet<Integer> numbersRolled=new HashSet<Integer>();

    public void play() throws Exception {
        // Play die game:

        // Play the main game phase
        mainDieGame();

        // Now see if they have won!
        declareDieGameWinner();
    }

    public void mainDieGame() throws Exception {
        // The main game:

        // Let the user roll the die twice
        for (int i=0; i<2; i++) {
          System.out.println("Hit <RETURN> to roll the die");
          br.readLine();
          int dieRoll=(int)(r.next() * 6) + 1;

          System.out.println("You rolled " + dieRoll);
          numbersRolled.add(dieRoll); //new Integer removed as it was a deprecation
        }

        // Display the numbers rolled
        System.out.println("Numbers rolled: " + numbersRolled);
    }

    public void declareDieGameWinner() throws Exception {
        // Declare the winner:

        // User wins if at least one of the die rolls is a 1
        if (numbersRolled.contains(1)) { //new Integer removed as it was a deprecation
          System.out.println("You won!");
        }
        else System.out.println("You lost!");
    }

}
