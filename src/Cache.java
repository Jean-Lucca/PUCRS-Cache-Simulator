import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.Random;

public class Cache {

    private boolean showExecution = false;
    private LinkedList<Level> hierarchy;
    private Entry[][] memory;
    private int associativity;
    private int numSets;
    private ReplacementAlgorithm replacementAlg = new LRU();
    private double[] miss = new double[5];
    private double[] hit = new double[5];

    public Cache(int cacheSize, int wordSize, int wordPerBlock, int ways, 
    LinkedList<Level> hierarchy) {
        int blockSize = wordSize * wordPerBlock;
        this.numSets = cacheSize/blockSize;
        this.associativity = (cacheSize/blockSize)/ways;
        this.memory = new Entry[numSets][associativity];
        this.hierarchy = hierarchy;
        this.replacementAlg = new LRU();
    }

    public Cache(LinkedList<Integer> cacheInformation, LinkedList<Level> hierarchy) {
        this(cacheInformation.get(0), cacheInformation.get(1), cacheInformation.get(2), 
        cacheInformation.get(3), hierarchy);
    }

    public String run(LinkedList<Integer> trace) {
        for(int i : trace) {
            search(i, numSets, associativity);
        }
        return toString();
    }

    public void search(Integer word, int numSet, int associativity) {
        Entry newEntry = newEntry((int)(Math.log(numSets) / Math.log(2)), (int)(Math.log(associativity) / Math.log(2)), word);
        int start = numSet;
        int end = associativity;
        int setIdx = newEntry.getSet();
        
        if(showExecution){
            System.out.println("searching "+newEntry);
            showMemory();
        }
        
        for ( int j = 0; j < end; j++) {
            if (memory[setIdx][j] != null && memory[setIdx][j].getTag() == newEntry.getTag()) {
                if(showExecution) { System.out.println("Hit"); }
                hit[0]++;
                memory[setIdx][newEntry.getWord()] = newEntry;
                return;
            }
        }
        
        for ( int j = 0; j < end; j++) {
            if (memory[setIdx][j] == null) {
                if(showExecution) { System.out.println("Miss"); }
                miss[0]++;
                countPenalty();
                memory[setIdx][newEntry.getWord()] = newEntry;
                return;
            }
        }

        if(showExecution) { System.out.println("Miss"); }
        miss[0]++;

        int replacementIdx = replacementAlg.replace(memory, start, end);

        if(showExecution) { System.out.println("Substituindo conjunto: "+replacementIdx); }

        for(int j=0; j<end; j++) {
            newEntry.setSet(replacementIdx);
            memory[replacementIdx][j] = newEntry;
        }
        countPenalty();
    }

    public Entry newEntry(int setBits, int wordBits, int val) {
        int mask = 0;
        int tagBits = 32 - (wordBits + setBits);

        for(int i=0;i<wordBits;i++) {
            mask = mask <<1;
            mask |= 1;
        }

        int word = val & mask;

        mask = 0;
        for(int i=0; i< setBits ; i++) {
            mask = mask <<1;
            mask |= 1;
        }
        
        int set = (val >> wordBits) & mask;

        mask = 0;

        for(int i=0;i<tagBits ; i++) {
            mask = mask <<1;
            mask |=1;
        }

        int tag = (val >> (wordBits + setBits)) & mask;

        return new Entry(tag, set, word, new Timestamp(System.nanoTime()));
    }

    public void countPenalty() {
        for(int i=0;i<4;i++) {
            Level l = hierarchy.get(i);
            int prob = new Random().nextInt(99) + 1;
            if(prob <= l.getProbability()) {
                hit[i+1]++;
                if(showExecution) {
                    System.out.println("encontrado");
                    System.out.println(l.getName()+" acessado(a) ");
                }
                return;
            }
            miss[i+1]++;
        }
    }

    public double calcHitRatio(int idx) {
        return hit[idx]/(hit[idx]+miss[idx]);
    }

    public double calcAverageMemoryAccessTime(){
        double acc = calcHitRatio(0) + (1-calcHitRatio(0));
        for(int i=0;i<4;i++) {
            acc += (1-calcHitRatio(i)) * (hierarchy.get(i).getPenalty());
        }
        return acc;
    }

    public double calcExecutionTime() {
        double acc = 0;
        for(int i=0;i<4;i++) {
            acc += (hit[i+1] + miss[i+1]) * hierarchy.get(i).getPenalty();
        }
        return acc + hit[0] + miss[0];
    }
    
    public void showMemory() {
        for(int i =0;i<numSets;i++) {
            System.out.println();
            for(int j=0;j<associativity;j++) {
                System.out.print(memory[i][j]+"; ");
            }
        }
        System.out.println();
    }

    public void setShowExecutio(boolean showExecution) {
        this.showExecution = showExecution;
    }

    public void setReplacementAlg(ReplacementAlgorithm replacementAlg) {
        this.replacementAlg = replacementAlg;
    }

    public String toString() {
        return 
        "-----------------------------------------------------------------------"+
        "\nResultado da Simulação"+
        "\n\nConfiguração da cache"+
        "\nNumero de conjuntos: "+numSets+
        "\nNumero de palavras em cada conjunto: "+associativity+
        "\nHierarquia de memoria: "+hierarchy+
        "\n\nCache L1:"+
        "\nHit: "+hit[0]+
        "\nMiss: "+miss[0]+
        "\nHit Ratio: "+String.format("%.2f", calcHitRatio(0))+
        "\nMiss Ratio: "+String.format("%.2f", (1-calcHitRatio(0)))+
        "\n\nCache L2:"+
        "\nHit: "+hit[1]+
        "\nMiss: "+miss[1]+
        "\nHit Ratio: "+String.format("%.2f", calcHitRatio(1))+
        "\nMiss Ratio: "+String.format("%.2f", (1-calcHitRatio(1)))+
        "\n\nCache L3:"+
        "\nHit: "+hit[2]+
        "\nMiss: "+miss[2]+
        "\nHit Ratio: "+String.format("%.2f", calcHitRatio(2))+
        "\nMiss Ratio: "+String.format("%.2f", (1-calcHitRatio(2)))+
        "\n\nMemoria Ram:"+
        "\nHit: "+hit[3]+
        "\nMiss: "+miss[3]+
        "\nHit Ratio: "+String.format("%.2f", calcHitRatio(3))+
        "\nMiss Ratio: "+String.format("%.2f", (1-calcHitRatio(3)))+
        "\n\nHard Disk:"+
        "\nHit: "+hit[4]+
        "\nMiss: "+miss[4]+
        "\nHit Ratio: "+String.format("%.2f", calcHitRatio(4))+
        "\nMiss Ratio: "+String.format("%.2f", (1-calcHitRatio(4)))+
        "\nTempo medio de acesso: "+String.format("%.2f", calcAverageMemoryAccessTime())+
        "\nTempo total de execucao: "+String.format("%.2f", calcExecutionTime())+
        "\n-----------------------------------------------------------------------"+
        "\nFormulas usadas:"+
        "\nNumero de conjuntos = Tamanho da cache / (Tamanho da palavra * Numero de palavras por bloco) "+
        "\nNumero de palavras em cada conjunto = Numero de conjuntos / Numero de vias "+
        "\nTempo medio de acesso = (HitRatio * HitPenalty) + (MissRatio * MissPenalty)";
    }
}
