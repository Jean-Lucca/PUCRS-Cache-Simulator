import java.sql.Timestamp;


public class LRU implements ReplacementAlgorithm {

    public int replace(Entry[][] memory, int start, int end) {
        int idx = 0;

        Timestamp lruT = new Timestamp(System.nanoTime());

        for(int i=0; i<start; i++) {
            for(int j=0; j<end; j++) {
                if (memory[i][j] != null && memory[i][j].getTime().before(lruT)) {
                    lruT = memory[i][j].getTime();
                    idx = i;
                }
            }
        }
        
        return idx;
    }
}
