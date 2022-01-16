import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class PhoneBookLogic {

	private AssociationTable<String, String> table;//the table we use to save the info

	// constructors
	public PhoneBookLogic() {
		table = new AssociationTable<String, String>();
	}
	
	//methods
	public boolean loadFile(File file) {
		Scanner sc;			//Scanner for reading
		String inputLine;	//input string
		int tabIndex;		//the index of the '\t' char
		try {
			sc = new Scanner(file);	//creating the Scanner
		} catch (FileNotFoundException e) {
			System.out.println("Error, File not found");
			return false;
		}
		if(table.size()!=0) {//if this loading is not the first one
			initializeTable();
		}
		while (sc.hasNextLine()) {//reading the data one line at a time 
			inputLine = sc.nextLine();
			tabIndex = inputLine.indexOf('\t');
			table.add(inputLine.substring(0, tabIndex), inputLine.substring(tabIndex + 1));//adding to the table
		}
		sc.close();//closing the Scanner
		return true;
	}
	
	public void initializeTable() {//delete all existing data
		for (String i : table) {
			table.remove(i);
		}
	}

	public String list4Print() {//create a String of all the data in the table
		String list = "";
		for (String i : table) {
			list = list + i + "\t" + table.get(i) + "\n";
		}
		return list;
	}

	public void add(String name, String number) {//adding to the table
		table.add(name, number);
	}

	public void remove(String name) {//removing for the table
		table.remove(name);
	}

	public boolean contain(String name) {//check if the key is in the table
		return table.contains(name);
	}

	public String getNumber(String name) {//getting the number of that person
		return table.get(name);
	}
	
	public boolean save(File file) throws IOException {
		String dir;
		String contant = list4Print();//saving the data
		
		dir = file.getAbsolutePath();//saving the dir
		file.delete();//deleting the file
		file = new File(dir);//recreating it
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));//creating a buffer
	    writer.write(contant);//filing the file
	    writer.close();
	    return true;
	}

}
