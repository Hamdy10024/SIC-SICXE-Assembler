package parserInterafces;
public interface IStatement {

  
  /**
   * @param object code of the current statement
   */
  public void setObjectCode(String objectCode);
  
  /**
   * @param error message in case of a non-executable statement
   */
  public void setError(String error);

  
  public String statement();
  /**
   * @return object code of the current statement, or null otherwise
   */
  public String objectCode();
  
  /**
   * @return error message in case of a non-executable statement, or null otherwise
   */
  public String error();

}