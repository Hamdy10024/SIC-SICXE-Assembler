package parserImpl;

import parserInterafces.IStatement;

public abstract class Statement {

  protected String state;

  protected String label;

  protected String operation;

  protected String operands;

  protected String comment;
  protected HexaInt address;

  public Statement() {

  }

  public Statement(String state) {
    this.state = state;
    address = null;
    label = state.substring(0, 8).trim().toUpperCase();
    if (label.length() == 0)
      label = null;
    operation = state.substring(9, 15).trim().toUpperCase();
    if (operation.length() == 0)
      throw new RuntimeException("");
    if (state.length() > 35) {
      operands = state.substring(17, 34).trim().toUpperCase();
      comment = state.substring(35).trim();
    } else if (state.length() > 17)
      operands = state.substring(17).trim();
    else
      operands = comment = null;
    if (!label.matches("[_A-Z][A-Z_0-9]*")) {
      throw new RuntimeException("Invalid Label");
    }
    if (operands != null && !operands.matches("[_A-Z][A-Z_0-9]*") && !operands.matches("[0-9]+")
        && !operands.matches("[_A-Z][A-Z_0-9]*,[XTSA]") && !operands.matches("[XTSA],[XTSA]")) {
      throw new RuntimeException("Invalid Operands");
    }
    if (state.charAt(8) != ' ' || state.charAt(16) != ' ')
      throw new RuntimeException("Invalid Statement");

  }

  public HexaInt getAddress() {
    return address;
  }

  public void setAddress(HexaInt address) {
    this.address = address;
  }

  public String operation() {
    return operation;
  }

  public String operands() {
    return operands;
  }

  public String Label() {
    return label;
  }

  public String getComment() {
    return comment;
  }

  public String getStatement() {
    return state;
  }

  @Override
  public String toString() {
    String state = "";
    if (address == null)
      state += "    "; // 4 spaces
    else
      state += address.toString();
    state += " ";
    if (label == null)
      state += "        "; // 8 spaces
    else {
      state += label;
      while (state.length() < 13)
        state += " ";
    }
    state += " ";
    state += operation;
    while (state.length() < 20)
      state += " ";
    state += " ";
    state += operands;
    return state;
  }

  public abstract HexaInt getNextLocationCounter(HexaInt LocationCounter);

}
