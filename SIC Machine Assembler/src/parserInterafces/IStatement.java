package parserInterafces;

public interface IStatement {
  
  /**
   * gets address of current statement.
   * @return address of the statement in decimal format.
   */
  public Integer getAddress();
  
  /**
   * sets address of current statement.
   * @parameter address of the statement in decimal format.
   */
  public void setAddress(Integer address);
  
  public String operation(); 
  
  public String operands();  
  
  public String Label();
  
  public Integer getNextLocationCounter(int LocationCounter);
  
  

}
