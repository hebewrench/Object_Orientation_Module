import java.io.*;

public abstract class Game{
  // The BufferedReader used throughout
  public static BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

  // The random number generator used throughout
  public static RandomInterface r= new LinearCongruentialGenerator();

  public static void main(String[] args) throws Exception {
    // Ask whether to play a card game or a die game
    Cards c = new Cards();
    Die d = new Die();

    System.out.print("Card (c) or Die (d) game? ");
    String ans=br.readLine();

    if (ans.equals("c")) {
      c.play();
    }

    else if (ans.equals("d")) {
      d.play();
    }

    else System.out.println("Input not understood");
  }
}
