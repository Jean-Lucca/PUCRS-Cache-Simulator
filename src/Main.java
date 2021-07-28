import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static Reader r = new Reader();
    public static Writer w = new Writer();
    public static void main(String[] args) throws IOException {
        menu(args);
    }

    public static void menu(String[] configuration) throws IOException {
        Scanner scan = new Scanner(System.in);

        r.readProg(configuration[0]+".txt");
        r.readCache(configuration[1]+".txt");
        r.readHierarchy(configuration[2]+".txt");
        
        System.out.println("Selecione uma politica de subtituição");
        System.out.println("1. LRU");
        System.out.println("2. Random");
        int replacementAlg = scan.nextInt();

        System.out.println("Numero maximo de instruções (-1 para limite infinito)");
        int limit = scan.nextInt();

        Cache cache = new Cache(r.getCacheInformation(), r.getHierarchy());
        Simulator sim = new Simulator(r.getMemorySize(), r.getInstructionList(), cache, replacementAlg, limit);
        
        System.out.println("Mostrar execução? (não recomendado para um numero grande o instruções)");
        System.out.println("1. Sim");
        System.out.println("2. Não");
        int show = scan.nextInt();
        if(show == 1) {
            cache.setShowExecutio(true);
        }

        sim.runInstructions();
        
        w.write(sim.getTrace());
        w.write(sim.runAlgorithm());
        scan.close();
    }
}