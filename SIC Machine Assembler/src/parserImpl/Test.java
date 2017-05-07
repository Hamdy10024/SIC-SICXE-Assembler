package parserImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.io.File;

import parserInterafces.IStatement;

public class Test {

	public static void main(String[] args) {
				
		File intermediateFile = new File("IntermediateFile.txt");
		IntermediateParser parser = new IntermediateParser();
		parser.readFile(intermediateFile);
		
		/*
		HashMap<String, Properties> OPTAB = new HashMap<String, Properties>();
		HashMap<String, String> SYMTAB = new HashMap<String, String>();
		ArrayList<Statement> statements = new ArrayList<Statement>();
		
		// for each operation name in the array
		// create new Properties p
		// setProperty("opCode", opCodes[i])
		// add this operation as (operation[i], p)
		
		Properties p1 = new Properties();
		p1.setProperty("opCode", "4C");
		
		Properties p2 = new Properties();
		p2.setProperty("opCode", "14");
		
		Properties p3 = new Properties();
		p3.setProperty("opCode", "54");
		
		OPTAB.put("RSUB", p1);
		OPTAB.put("STL", p2);
		OPTAB.put("STCH", p3);
		
		SYMTAB.put("RETADR", "1033");
		SYMTAB.put("BUFFER", "1039");
		
		String statement1 = "1000 FIRST    STL     RETADR";
		// String statement1 = "1000 FIRST    STL     RET";
		String statement2 = "204E          STCH    BUFFER,X";
		// String statement3 = "102A EOF      BYTE    C'EOF'";
		// String statement3 = "102A EOF      BYTE    C'EOFL'";
		String statement3 = "102A EOF      WORD    C'EOFL'";
		String statement4 = "102D THREE    WORD    3";
		// String statement5 = "205D INPUT    BYTE    X'F1'";
		String statement5 = "205D INPUT    WORD    X'F1'";
		String statement6 = "205E MAXLEN   WORD    4096";
		String statement7 = "205A          RSUB";
		// String statement8 = "2069 OUTPUT   BYTE    X'05'";
		// String statement8 = "2069 OUTPUT   WORD    X'05'";
		// String statement8 = "2069 OUTPUT   BYTE    X'5'";
		String statement8 = "2069 OUTPUT   WORD    X'5'";
		String statement9 = "2052          RESB    1";
		
		Statement s1 = new Statement(statement1);
		Statement s2 = new Statement(statement2);
		Statement s3 = new Statement(statement3);
		Statement s4 = new Statement(statement4);
		Statement s5 = new Statement(statement5);
		Statement s6 = new Statement(statement6);
		Statement s7 = new Statement(statement7);
		Statement s8 = new Statement(statement8);
		Statement s9 = new Statement(statement9);
		
		statements.add(s1);
		statements.add(s2);
		statements.add(s3);
		statements.add(s4);
		statements.add(s5);
		statements.add(s6);
		statements.add(s7);
		statements.add(s8);
		statements.add(s9);
		
		ObjectCodeGenerator o = new ObjectCodeGenerator(SYMTAB, OPTAB);
		o.generateObjectCode(statements);
		for (Statement statement : statements)
			System.out.println(statement.objectCode() + " ERROR: " + statement.error());
		*/		
	}

}
