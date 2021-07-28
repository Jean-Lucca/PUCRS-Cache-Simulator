import java.util.LinkedList;

public class Simulator {
    
    private CPU cpu;
    private int adressLimit;
    private Instruction[] memory;
    private int memorySize;
    private LinkedList<Instruction> instructionList;
    private LinkedList<Integer> trace;
    private Cache cache;
    private int selectAlgorithm;

    public Simulator(int memorySize, LinkedList<Instruction> instructionList, Cache cache,
    int selectAlgorithm, int adressLimit) {
        this.cpu = new CPU();
        this.memorySize = memorySize;
        this.cache = cache;
        this.memory = new Instruction[memorySize+1];
        this.instructionList = instructionList;
        this.adressLimit = adressLimit;
        this.selectAlgorithm = selectAlgorithm;
        this.trace = new LinkedList<Integer>();
    }

    public Simulator(int memorySize, LinkedList<Instruction> instructionList, Cache cache,
    int selectAlgorithm) {
        this.cpu = new CPU();
        this.memorySize = memorySize;
        this.cache = cache;
        this.memory = new Instruction[memorySize+1];
        this.instructionList = instructionList;
        this.selectAlgorithm = selectAlgorithm;
        this.trace = new LinkedList<Integer>();
        this.adressLimit = -1;
    }

    public String runAlgorithm() {
        select();
        return cache.run(trace);
    }

    public void runInstructions() {
        buildMemory();
        int pc = 0;
        while(pc < memorySize && (trace.size()-1 < adressLimit || adressLimit == -1)) {
            trace.add(pc);
            if(memory[pc] != null) {
                pc = cpu.run(memory[pc]);
            } else {
                pc++;
            }
        }
    }

    public void buildMemory() {
        for(Instruction i : instructionList) {
            memory[i.getAdr()] = i;
        }
    }

    public void select() {
        if(selectAlgorithm == 1) {
            cache.setReplacementAlg(new LRU());
        }
        if(selectAlgorithm == 2) {
            cache.setReplacementAlg(new RR());
        }
    }

    public void showMemory() {
        for(int i=0;i<memorySize;i++) {
            System.err.println(memory[i]);
        }
    }

    public LinkedList<Integer> getTrace() {
        return trace;
    }
}