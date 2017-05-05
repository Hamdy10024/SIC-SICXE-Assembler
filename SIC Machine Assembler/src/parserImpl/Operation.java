package parserImpl;

import parserInterafces.IOperation;

public class Operation extends Statement implements IOperation  {
	
	private String name; // holds the operation
	private String opCode; // holds the operation code in HEXA
	private boolean isSIC; // flag determining whether the operation is SIC-valid or not
	public Operation(String state) {
	  super(state);
	}
	public Operation(String name, String opCode, boolean isSIC) {
	  
		this.name = name;
		this.opCode = opCode;
		this.isSIC = isSIC;
	}
	
	public String name() {
		return name;
	}
	
	public String opCode() {
		return opCode;
	}
	
	public boolean isSIC() {
		return isSIC;
	}

  @Override
  public HexaInt getAddress() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void setAddress(HexaInt address) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public String operation() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String operands() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public String Label() {
    // TODO Auto-generated method stub
    return null;
  }
  public static boolean isOperation(String statement) {
    return true;
  }
  @Override
  public HexaInt getNextLocationCounter(HexaInt LocationCounter) {
    return LocationCounter.add(Indexer.wordSize);
  }

}
