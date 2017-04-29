package parserImpl;

import parserInterafces.IOperation;

public class Operation implements IOperation {
	
	private String name; // holds the operation
	private String opCode; // holds the operation code in HEXA
	private boolean isSIC; // flag determining whether the operation is SIC-valid or not
	
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
}
