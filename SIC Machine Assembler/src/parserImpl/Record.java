package parserImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import parserInterafces.IStatement;

public class Record {

  private HexaInt Loc;

  private HexaInt BaseLoc;
  private HexaInt sz;
  private String repres;
  public Record(IStatement init) {
    sz = new HexaInt(0,8);
    Loc = new HexaInt(0,24);
    if(init.objectCode() == null || init.objectCode().length() == 0){
      throw new RuntimeException("Invalid initial statement");
    }
    repres = "";
    Loc = Loc.add(new HexaInt(init.location()));
    BaseLoc = (HexaInt) Loc.clone();
    if(!push(init)){
      throw new RuntimeException("Statement overflow");
    }
  }
  
  private HexaInt size(IStatement ns) {
   return new HexaInt((int) Math.ceil(ns.objectCode().length()/2.0)); 
  }
  
  @Override
  public String toString() {
    String Out = "T";
    Out+=BaseLoc;
    Out+=sz;
    Out+=repres;
    return Out;
  }
  
  public boolean push(IStatement nStatement) {
    if(nStatement == null ||
        nStatement.objectCode() == null ||
        nStatement.objectCode().length() == 0)
      return true;
    if(!Loc.equals(nStatement.location())) {
      System.out.println("NOO"+Loc+" "+nStatement.location());
      return false;
    }
    if (sz.add(size(nStatement)).getVal() > 30) {

      System.out.println("NOO"+Loc+" "+nStatement.location());
      return false;
    }
    repres+="."+nStatement.objectCode();
    sz = sz.add(size(nStatement));
    Loc = Loc.add(size(nStatement));
    return true;
  }
  
  public static void main(String args[]) throws FileNotFoundException {
    Scanner sc = new Scanner(new File("testRec.txt"));
    ArrayList<IStatement> tes = new ArrayList<IStatement>();
    while(sc.hasNextLine()) {
      String loc = sc.next();
      String op = sc.next();
      StateImpl g = new StateImpl();
      g.setLocation(loc);
      g.setObjectCode(op);
      tes.add(g);
    }
    Record t = new Record(tes.get(0));
    boolean h = true;
    int i;
    for(i =1; i < tes.size() && h ;i++) {
      h =t.push(tes.get(i));
    }
    System.out.println(t);
    
    System.out.println(i +" "+(tes.size()- i+1));
  }
  
}
