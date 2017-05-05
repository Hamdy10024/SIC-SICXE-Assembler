package parserImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

import parserInterafces.IIndexer;
import parserInterafces.IStatement;

public class Indexer implements IIndexer {

  private List<String> statements;
  private HashMap<String, HexaInt> symTab;
  private List<String> outStates;
  public static HashMap<String, Properties> opTab;
  public static Integer wordSize = 3;
  private HashMap<String, HexaInt> LitTab;
  private HexaInt Loc;
  private Stack<HexaInt> orgs;
  private String progName;

  public Indexer() {
    symTab = new HashMap<String, HexaInt>();
    statements = new ArrayList<String>();
    outStates = new ArrayList<String>();
    orgs = new Stack<HexaInt>();
  }

  private void validateFormat(String state) {
    boolean validF = state.trim().matches("[A-Za-z0-9 ]{8} [A-Za-z ]{6}  [A-Za-z0-9//']+");
    validF |= state.trim().matches("[A-Za-z0-9 ]{8} [A-Za-z ]{6}  [A-Za-z0-9]+,[X]");
    validF |= state.trim().matches("[A-Za-z0-9 ]{8} [A-Za-z]+");
    validF |= state.trim().matches("[A-Za-z]+");
    validF |= state.trim().matches("[A-Za-z ]{6}  [A-Za-z0-9]+");
    validF |= state.trim().matches("[A-Za-z ]{6}  [A-Za-z0-9]+,[X]");
    boolean validOp = state.trim().matches("[A-Za-z][A-Za-z0-9]*\\s+[A-Za-z]+\\s+[A-Za-z0-9//']+");
    validOp |= state.trim().matches("[A-Za-z][A-Za-z0-9]*\\s+[A-Za-z]+\\s+[A-Za-z0-9]+,[X]");
    validOp |= state.trim().matches("[A-Za-z]+\\s+[A-Za-z0-9]+");
    validOp |= state.trim().matches("[A-Za-z]+\\s+[A-Za-z0-9]+,[X]");
    validOp |= state.trim().matches("[A-Za-z][A-Za-z0-9]*\\s+[A-Za-z]+");
    validOp |= state.trim().matches("[A-Za-z]+");
    if (opTab.containsKey(state.substring(8, 15).trim()))
      if (validF && validOp)
        return;
    throw new RuntimeException("INVALID Instruction format".toUpperCase());
  }

  private HexaInt getValue(String address) {
    if (address == null || address.length() == 0)
      return null;
    if (address.startsWith("0X")) {
      return new HexaInt(address.substring(2).trim().toLowerCase());
    } else if (address.trim().matches("[0-9]+"))
      return new HexaInt(Integer.parseInt(address));
    else if (symTab.containsKey(address)) {
      return symTab.get(address);
    }
    throw new RuntimeException("INVALID OPERAND format".toUpperCase() + " " + address);

  }

  private void updateSymTab(String statement) {
    String label = statement.substring(0, 8).trim();
    if (label.length() == 0)
      return;
    if (symTab.containsKey(label)) {
      throw new RuntimeException("Dupicate label definition".toUpperCase());
    }
    symTab.put(label, Loc);
  }

  private void updateLoc(String operation, String address) {
    if (opTab.get(operation).get("opCode") != null) {
      Loc = Loc.add((Integer) opTab.get(operation).get("size"));
      return;
    }

    if(operation.equals("END"))
    return;
    HexaInt loc = getValue(address);
    switch (operation) {
    
    case "ORG":
      if (loc != null) {
        orgs.push(Loc);
        Loc = loc;
        return;
      }
      if (!orgs.empty())
        Loc = orgs.pop();
      return;
    case "RESB":
      Loc = Loc.add(loc);
      return;
    case "RESW":
      Loc = Loc.add(loc.multiply(Indexer.wordSize));
      return;
    case "BYTE":
      Loc = Loc.add(getSize(address));
      return;
    case "WORD":
      int incr = getSize(address);
      if (incr % 3 != 0)
        incr += (3 - incr % 3);
      Loc = Loc.add(incr);
      return;
    }
  }

  private Integer getSize(String Literal) {
    if (Literal.matches("[0-9]+")) {
      return ((Integer.parseInt(Literal) > 255) ? wordSize : 1);
    } else if (Literal.startsWith("0C")) {
      return Literal.substring(2).replace("'", " ").trim().length();
    } else if (Literal.startsWith("0X")) {
      int half = Literal.substring(2).replace("'", " ").trim().length();
      return (int) Math.ceil(half / 2.0);
    }
    return 0;
  }

  @Override
  public File Parse(File source) throws RuntimeException, FileNotFoundException, IOException {
    Scanner input = new Scanner(source);
    statements.clear();
    while (input.hasNextLine()) {
      statements.add(input.nextLine());
    }
    Parse();
    for (String line : outStates)
      System.out.println(line);
    return null;
  }

  private void validateStart(String state) {

    boolean validF = state.matches("[A-Za-z0-9 ]{8} START   [0-9]+")
        || state.matches("[A-Za-z0-9 ]{8} START   0X[0-9A-F]+");
    validF &= state.matches("[A-Za-z][A-Za-z0-9]+\\s+START\\s+[0-9]+") | 
        state.matches("[A-Za-z][A-Za-z0-9]+\\s+START\\s+0X[0-9A-F]+");
    if (!validF) {
      throw new RuntimeException("INVALID START OPERATION");
    }
    Loc = getValue(state.substring(16).trim());
    progName = state.substring(0, 8).trim();
    symTab.put(progName, Loc);
  }

  private boolean Parse() {
    boolean err = true;
    Loc = new HexaInt(0, 16);
    for (int i = 0; i < statements.size(); i++) {
      boolean rerr = true;
      String state = statements.get(i).toUpperCase();
      if (state.trim().length() == 0 || state.trim().charAt(0) == '.')
        continue;
      if (state.length() > 35)
        state = state.substring(0, 35);
      if (i == 0) {
        try {
          validateStart(state);
        } catch (RuntimeException e) {
          err = false;
          state += " " + e.getMessage();
        }
        state = Loc.toString() + " " + state;
        outStates.add(state);
        continue;
      }
      try {
        validateFormat(state);
        updateSymTab(state);
      } catch (RuntimeException e) {
        rerr = false;
        state += " " + e.getMessage();
      }
      ;
      outStates.add(Loc.toString() + " " + state);
      String label = state.substring(9, 15).trim();

      String address = null;
      if (state.length() > 17)
        address = state.substring(17).trim();
      if (rerr)
        updateLoc(label, address);
      err &= rerr;
    }
    return err;
  }

  public static void loadOpTab() {
    Indexer.opTab = new HashMap<String, Properties>();
    Properties B = new Properties();
    Properties A = new Properties();

    B.put("opCode", 24);
    B.put("size", 3);
    String[] arr = { "ADD", "SUB", "AND", "COMP", "MUL", "DIV", "OR", "J", "JEQ", "JLT", "JGT", "JSUB", "LDA", "LDCH",
        "LDX", "LDL", "RD", "RSUB", "STA", "STX", "STL", "STCH", "SUB", "TD", "TIX", "WD" };
    for (String ar : arr) {
      Indexer.opTab.put(ar, B);
    }
    A.put("size", 3);
    String[] arr2 = { "RESW", "RESB", "BYTE", "WORD", "END", "EQU", "ORG" };
    for (String ar : arr2) {
      Indexer.opTab.put(ar, A);
    }
  }

  public static void main(String args[]) throws FileNotFoundException, RuntimeException, IOException {
    loadOpTab();
    new Indexer().Parse(new File("test.asm"));
  }
}