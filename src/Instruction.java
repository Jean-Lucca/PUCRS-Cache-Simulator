public class Instruction {
    private String type;
    private int adr;
    private int tgt;
    private int pbd;

    public Instruction(String type, String adr, String tgt, String pbd) {
        this.type = type;
        this.adr = Integer.valueOf(adr);
        this.tgt = Integer.valueOf(tgt);
        this.pbd = Integer.valueOf(pbd);
    }

    public Instruction(String type,String adr, String tgt) {
        this.type = type;
        this.adr = Integer.valueOf(adr);
        this.tgt = Integer.valueOf(tgt);
    }

    public Instruction(String type,String adr) {
        this.type = type;
        this.adr = Integer.valueOf(adr);
    }

    public int getAdr() {
        return adr;
    }

    public double getPbd() {
        return pbd;
    }

    public int getTgt() {
        return tgt;
    }

    public String getType() {
        return type;
    }

    public String toString() {
        return "(" + type + ", "+ adr + ", "+ tgt + ", "+ pbd + ")";
    }
}
