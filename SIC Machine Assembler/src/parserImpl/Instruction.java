package parserImpl;

import java.util.HashMap;

import parserInterafces.IInstruction;


public class Instruction implements IInstruction {
	
	private HashMap<String, Operation> OPTAB; // maps each operation to an object of type "Operation"
	private String version; // holds the current version of the machine
	
	public Instruction(HashMap<String, Operation> OPTAB, String version) {
		this.OPTAB = OPTAB;
		this.version = version;
	}

	public int getFormat(String operation, String operand) throws RuntimeException {
		boolean format4 = operation.startsWith("+");
		if (format4)
			operation = operation.replaceFirst("\\+", "");
		Operation op = OPTAB.get(operation);
		if (version.equals("SIC")) {
			if (!op.isSIC())
				throw new RuntimeException("SIC Machine Does Not Provide \"" + operation + "\" Operation!");
			if(format4)
				throw new RuntimeException("Invalid SIC Instruction Format!");
			return checkOperand(operand);
		} else {
			if (operand.equals("")) // or == null --> according to initial assumption of no operand
				return 1;
			if (operand.contains(","))
				return 2;
			if (!format4)
				return 3;
			return 4;
		}		
	}
	
	private int checkOperand(String operand) {		
		if (isXEAddressing(operand))
			throw new RuntimeException("Invalid SIC Addressing Mode!");
		return 0;
	}
	
	private boolean isXEAddressing(String operand) {
		return operand.startsWith("@") || operand.startsWith("#"); // whether indirect or immediate addressing
	}

}

