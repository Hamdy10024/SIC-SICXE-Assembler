package parserInterafces;

import java.util.HashMap;
import java.util.List;

public interface IIndexer {
  public HashMap<String, Integer> getSymTab();
  

  public HashMap<Integer, Integer> getLitTab();
  
  public List<IStatement> statements();
  
  public void Parse() throws RuntimeException;
  

}
