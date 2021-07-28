public class Level {
    String name;
    int penalty;
    int probability;
    
    public Level(String name, String penalty, String probability) {
        this.name = name;
        this.penalty = Integer.valueOf(penalty);
        this.probability = Integer.valueOf(probability);
    }

    public String getName() {
        return name;
    }

    public int getPenalty() {
        return penalty;
    }

    public int getProbability() {
        return probability;
    }

    @Override
    public String toString() {
        return "("+ name +", "+ penalty +", "+  probability+")";
    }
}
