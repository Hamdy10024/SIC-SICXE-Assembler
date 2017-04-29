package parserInterafces;

/**
 * represents the mnemonic instruction to be stored in the OPTAB
 */
public interface IOperation {

	public String name();
	
	public String opCode();
	
	public boolean isSIC();
	
}
