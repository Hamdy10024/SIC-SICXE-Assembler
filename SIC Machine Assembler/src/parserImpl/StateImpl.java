package parserImpl;

import parserInterafces.IStatement;

public class StateImpl implements IStatement {

	public String loc, opcode, label, operation, operand;

	@Override
	public void setLocation(String location) {
		loc = location;

	}

	@Override
	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public void setOperation(String operation) {

		this.operation = operation;
	}

	@Override
	public void setOperands(String operands) {
		this.operand = operands;
	}

	@Override
	public void setObjectCode(String objectCode) {
		opcode = objectCode;

	}

	@Override
	public void setError(String error) {
		// TODO Auto-generated method stub

	}

	@Override
	public String location() {

		return loc;
	}

	@Override
	public String Label() {
		// TODO Auto-generated method stub
		return label;
	}

	@Override
	public String operation() {
		// TODO Auto-generated method stub
		return operation;
	}

	@Override
	public String operands() {
		// TODO Auto-generated method stub
		return operand;
	}

	@Override
	public String objectCode() {

		return opcode;
	}

	@Override
	public String error() {
		// TODO Auto-generated method stub
		return " ";
	}

}
