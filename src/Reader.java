import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;


public class Reader {
	
	private int memorySize;
	private LinkedList<Instruction> instructionList; 
	private LinkedList<Level> hierarchy; 
	private LinkedList<Integer> cacheInformation;
	
	public Reader() {
		instructionList = new LinkedList<Instruction>();
		hierarchy =  new LinkedList<Level>();
		cacheInformation = new LinkedList<Integer>();
	}
	
    public void readProg(String path) throws IOException {
		BufferedReader buffRead = new BufferedReader(new FileReader(path));
		String line = buffRead.readLine();
		while (true) {
			if (line != null) {

				String[] instruction = line.split(":");
				String type = instruction[0];
				Instruction temp;

				switch (type) {
					case "ep":
						temp = new Instruction(type, instruction[1]);
						instructionList.add(temp);
						break;

					case "ji":
						temp = new Instruction(type, instruction[1], instruction[2]);
						instructionList.add(temp);
						break;

					case "bi":
						temp = new Instruction(type, instruction[1], instruction[2], instruction[3]);
						instructionList.add(temp);
						break;
					
					default:
						break;
				}

				line = buffRead.readLine();

			} else {
				break;
			}
		}
		setMemorySize();
		buffRead.close();
	}

	public void readHierarchy(String path) throws IOException {
		BufferedReader buffRead = new BufferedReader(new FileReader(path));
		String line = buffRead.readLine();
		while (true) {
			if (line != null) {

				String[] hierarchyString = line.split(":");
				Level temp = new Level(hierarchyString[0], hierarchyString[1], hierarchyString[2]);
				hierarchy.add(temp);
				line = buffRead.readLine();

			} else {
				break;
			}
		}
		buffRead.close();
	}

	public void readCache(String path) throws IOException {
		BufferedReader buffRead = new BufferedReader(new FileReader(path));
		String line = buffRead.readLine();
		while (true) {
			if (line != null) {

				String[] cacheString = line.split(":");
				int temp = Integer.valueOf(cacheString[1]);
				cacheInformation.add(temp);
				line = buffRead.readLine();

			} else {
				break;
			}
		}

		buffRead.close();
	}

	public LinkedList<Instruction> getInstructionList() {
		return instructionList;
	}

	public void setMemorySize() {
		for(Instruction i : instructionList) {
			if(i.getType().equals("ep")) {
				memorySize = i.getAdr();
			}
		}
	}

	public int getMemorySize() {
		return memorySize;
	}

	public LinkedList<Level> getHierarchy() {
		return hierarchy;
	}

	public LinkedList<Integer> getCacheInformation() {
		return cacheInformation;
	}
}
