package parserImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import parserInterafces.IStatement;

public class ObjectCodeGenerator {

	private HashMap<String, String> SYMTAB;
	private HashMap<String, Properties> OPTAB;
	private RuntimeException throwIt;

	public ObjectCodeGenerator(HashMap<String, String> SYMTAB, HashMap<String, Properties> OPTAB) {
		this.SYMTAB = SYMTAB;
		this.OPTAB = OPTAB;
	}

	public void generateObjectCode(ArrayList<IStatement> statements) {
		String operation, operand;
		throwIt  = null;
		for (IStatement statement : statements) {
			operation = statement.operation();
			operand = statement.operands();
			if (OPTAB.containsKey(operation) && OPTAB.get(operation).containsKey("opCode") ) { // assuming that OPTAP contains operations only
				toObjectcode(statement, operation, operand);
			} else if (isCodable(operation)) {
        //System.out.println(operation+" "+operand);
				defineConstant(statement, operation, operand);
			}
		}
		if(throwIt != null)
		  throw throwIt;
	}
	
	private void toObjectcode(IStatement statement, String operation, String operand) {
		String opCode = OPTAB.get(operation).getProperty("opCode"); 
		String address = "0000";
		
		if (operand != null && operand.trim().length() > 0) {
			int x = 0;
			if (operand.contains(",")) {
				operand = operand.substring(0, operand.length() - 2);
				x = 8;
			}
			if (operand.matches("[0-9]+"))
				address = operand;
			else if (operand.toUpperCase().startsWith("0X"))
				address = operand.substring(2);
			else
				address = SYMTAB.get(operand);
			if (address == null) {
				statement.setError("Undefined Symbol!");
				throwIt  = new RuntimeException("Undefined Symbol!"+operation+" "+operand);
				return;
			}			
			int hexaBit = Integer.parseInt(address.substring(0, 1), 16);
			hexaBit |= x;
			address = Integer.toHexString(hexaBit) + address.substring(1);
		}
		statement.setObjectCode((opCode + address).toUpperCase());

   // System.out.println(operation+" "+operand);
	}
	
	private boolean isCodable(String operation) {
		if (operation.equals("BYTE") || operation.equals("WORD")) // or literal
			return true;
		return false;
	}
	
	private void defineConstant(IStatement statement, String operation, String operand) { // update for literals
		String objectCode = "";
		if (operand.startsWith("X"))
			objectCode = operand.substring(2, operand.length() - 1); // HEXA
		else if(operand.startsWith("0X"))
      objectCode = operand.substring(2, operand.length()); // HEXA
		else if (operand.startsWith("C"))
			for (int i = 2; i < operand.length() - 1; i++)
				objectCode += Integer.toHexString((int)operand.charAt(i)); // DECIMAL ASCII	-> HEXA	
		else if (operand.matches("[+-]?[0-9]+"))
			objectCode = Integer.toHexString(Integer.parseInt(operand)); // DECIMAL -> HEXA
		else {
			statement.setError("Cannot Define Constant! "+operand);
			throwIt = new RuntimeException("Cannot Define Constant!"+operand);
			return;
		}
		String format = format(operation, objectCode.length());		
		objectCode = String.format(format, objectCode).replace(' ', '0').toUpperCase();
		statement.setObjectCode(objectCode);
	}
	
	private String format(String operation, int length) {
		int hexaBits;
		if (operation.equals("BYTE")) // -> 2 HEXA digits
			hexaBits = 2;
		else // WORD -> 3 BYTE -> 6 HEXA digits
			hexaBits = 6;
		int units = length / hexaBits;
		if (length % hexaBits != 0)
			units++;
		String format = "%" + units * hexaBits + "s";
		return format;
	}

}
