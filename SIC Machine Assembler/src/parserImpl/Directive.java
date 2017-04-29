package parserImpl;

import java.util.HashMap;
import java.util.HashSet;

public class Directive extends Statement {
  public static HashSet<String> directives;

  private HashMap<String, Integer> symTab;

  public Directive(String state) {
    super(state);
    directives.add("START");

    directives.add("ORG");

    directives.add("EQU");

    directives.add("RESW");

    directives.add("RESB");

    directives.add("BASE");

    directives.add("NOBASE");

    directives.add("END");
  }
  
  public static boolean isDirective(String statement) {
    return directives.contains(statement.substring(9,16).toUpperCase().trim());
  }

  public void setSymTab(HashMap<String,Integer> symtab) {
    this.symTab = symtab;
  }
  public Integer targetAddress() {
    try {
      return Integer.valueOf(operands);
    } catch(NumberFormatException e) {
      if (symTab.containsKey(operands)) {
        return symTab.get(operands);
      }
    }
    throw new RuntimeException();//TODO :shoflak message.
  }
  
  @Override
  public Integer getNextLocationCounter(int LocationCounter) {
    switch (operation) {
    case "EQU":
      return LocationCounter;
    case "ORG":
      return targetAddress();
    case "RESB":
      return LocationCounter+targetAddress();
    case "RESW":
      return LocationCounter+targetAddress()*Indexer.wordSize; // TODO change wordSize place.
    default:
      return LocationCounter+Indexer.wordSize;
      
      
    }
  }

}
