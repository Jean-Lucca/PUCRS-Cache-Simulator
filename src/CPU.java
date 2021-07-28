import java.util.Random;

public class CPU {

    public CPU() {}

    public int run(Instruction instruction) {

        if(instruction.getType().equals("ji")) {
            return instruction.getTgt();
        }

        if(instruction.getType().equals("bi")) {

            int prob = new Random().nextInt(99) + 1;

            if(prob <= instruction.getPbd()) {
                return instruction.getTgt();
            }
            
        }
            
        return instruction.getAdr()+1;
    }
}
