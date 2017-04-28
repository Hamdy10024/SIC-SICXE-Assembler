package parserImpl;

import parserInterafces.IStatement;

public abstract class Statement implements IStatement {

  protected String state;

  protected String label;

  protected String operation;

  protected String operands;
  protected Integer address;
  public Statement (String state) {
    this.state = state;
    address = null;
    label = state.substring(0, 8);

    operation = state.substring(9, 16);
    operands = state.substring(16);
  }
  @Override
  public Integer getAddress() {
    return address;
  }

  @Override
  public void setAddress(Integer address) {
    this.address = address;

  }

  @Override
  public String operation() {
    return operation;
  }

  @Override
  public String operands() {
    return operands;
  }

  @Override
  public String Label() {
    return label;
  }

  @Override
  public Integer getNextLocationCounter(int LocationCounter) {
    return null;
  }

}
