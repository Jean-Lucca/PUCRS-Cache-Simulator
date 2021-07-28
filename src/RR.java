import java.util.Random;

//Random Replacement
public class RR implements ReplacementAlgorithm {

    public int replace(Entry[][] memory, int start, int end) {
        int random = new Random().nextInt(start-1) + 1;        
        return random;
    }
    
}
