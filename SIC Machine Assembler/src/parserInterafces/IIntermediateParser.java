package parserInterafces;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import parserImpl.Statement;

public interface IIntermediateParser {
	
	public void readFile(File intermediateFile);

	public ArrayList<IStatement> getStatements();
	
	public HashMap<String, String> getSYMTAB(); // LITTAB getter
	
}
