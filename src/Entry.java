import java.sql.Timestamp;

public class Entry {
    public Timestamp time;
    public int tag;
    public int word;
    public int set;
    

    public Entry() {
        this.time = null;
    }

    public Entry(int tag, int set, int word, Timestamp time) {
        this.time = time;
        this.set = set;
        this.word = word;
        this.tag = tag;
    }

    public Entry(int tag, int set, int word) {
        this.set = set;
        this.word = word;
        this.tag = tag;
    }

    public Timestamp getTime() {
        return time;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getSet() {
        return set;
    }

    public int getWord() {
        return word;
    }

    public void setSet(int set) {
        this.set = set;
    }

    @Override
    public String toString() {
        return "set: "+set+" tag "+tag+" word: "+word;
    }

}
