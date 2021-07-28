import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Writer {
    public void write(LinkedList<Integer> trace) {
        try (FileWriter writer = new FileWriter("Trace.txt");
            BufferedWriter bw = new BufferedWriter(writer)) {
            for(int i : trace) {
                bw.write(i + "\n");
            }
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    public void write(String str) {
        try (FileWriter writer = new FileWriter("Resultado.txt");
            BufferedWriter bw = new BufferedWriter(writer)) {
            bw.write(str);
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
}
