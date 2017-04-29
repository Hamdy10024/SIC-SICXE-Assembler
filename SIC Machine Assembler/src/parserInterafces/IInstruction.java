package parserInterafces;

public interface IInstruction {

	/** 
	 * @param operation
	 * @param operand
	 * @return integer representing the format of the current instruction as follows:
	 *   (0) --> SIC Format
	 *   (1, 2, 3, 4) --> SIC/XE Format
	 * @throws RuntimeException once error is detected
	 */
	public int getFormat(String operation, String operand) throws RuntimeException;
}
