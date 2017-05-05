package parserImpl;

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
    Loc.add(new HexaInt(init.location()));
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
      return false;
    }
    if (sz.add(size(nStatement)).getVal() > 30) {
      return false;
    }
    repres+=nStatement.objectCode();
    sz = sz.add(size(nStatement));
    return true;
  }
  
}
