package parserImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import parserInterafces.IStatement;

public class Main {
  
  
  public static void main(String args[]) {
    String inp = "SIC-Example.txt";
    File inter = null;
      try {

        Indexer.loadOpTab();
        inter = new Indexer().Parse(new File(inp));
        if(inter == null)
          throw new RuntimeException("NO");
      } catch (RuntimeException | IOException e) {
        System.out.println("Error during assembly1 "+e.getMessage());
        return;
        
      }
      IntermediateParser par = new IntermediateParser();
      par.readFile(inter);
      try {
        ObjectCodeGenerator ocg = new ObjectCodeGenerator(par.getSYMTAB(),Indexer.opTab);
        ocg.generateObjectCode(par.getStatements());
        List<IStatement> stats = par.getStatements();
        new Lister().generateFile(stats, new File("Listing.txt"));
        new Assembler(par.getStatements());
      }catch(RuntimeException | IOException e) {
        
        System.out.println("Error during assembly2"+" "+e.getMessage());
      }
    
  }

}
