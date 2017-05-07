package parserImpl;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import parserInterafces.IIntermediateParser;
import parserInterafces.IStatement;

public class IntermediateParser implements IIntermediateParser {

	private File intermediateFile;
	private ArrayList<IStatement> statements;
	private HashMap<String, String> SYMTAB;
	
	public IntermediateParser() {
		statements = new ArrayList<IStatement>();
		SYMTAB = new HashMap<String, String>();
	}
	
	@Override
	public void readFile(File intermediateFile) {
		this.intermediateFile = intermediateFile;
		try {
			Scanner sc = new Scanner(intermediateFile);
			String line;
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				if (!(line.equals("SYMTAB") || line.equals("LITTAB")))
					setStatement(line);
				else if (line.equals("SYMTAB"))
					readSYMTAB(sc);
				else if (line.equals("LITTAB"))
					readLITTAB(sc);
			}
			sc.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Intermediate File Not Found!");
		}
	}
	
	@Override
	public ArrayList<IStatement> getStatements() {
		return statements;
	}
	
	@Override
	public HashMap<String, String> getSYMTAB() {
		return SYMTAB;
	}
	
	private void setStatement(String s) {
		Statement statement = new Statement();
		String[] split = s.split("\\s+");
		int i = 0;
		//if (!(split[1].equals("START") || split[1].equals("END")))
			statement.setLocation(split[i++]);
		if (s.charAt(5) != ' ') // label exists (starting at index 5 of the string)
			statement.setLabel(split[i++]);
		statement.setOperation(split[i++]);
		if (i < split.length)
			statement.setOperands(split[i]);
		statements.add(statement);
		//System.out.println(statement.location() + "__" + statement.label() + "__" + statement.operation() + "__" + statement.operands());
	}
	
	private void readSYMTAB(Scanner sc) {
		String line;
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			if (!line.equals("LITTAB")){
				String[] entry = line.split("\\s+") ;
				SYMTAB.put(entry[0], entry[1]);
			}
			else
				readLITTAB(sc);
		}
	}
	
	private void readLITTAB(Scanner sc) {
		// fill LITTAB
	}
}
