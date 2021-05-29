import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = "a";
        String next = "";
         int i = 0;
         while (!StdIn.isEmpty()) {
            i++;
            next = StdIn.readString();
            if (StdRandom.bernoulli(1.0 / i)) {
                champion = next;
            }
        }
        StdOut.println(champion);
        // StdOut.println(next);
    }
}