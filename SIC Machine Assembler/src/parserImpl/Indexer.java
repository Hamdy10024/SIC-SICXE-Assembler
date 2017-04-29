package parserImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import parserInterafces.IIndexer;
import parserInterafces.IStatement;

public class Indexer implements IIndexer {
	
	private List<String> statements;
	private HashMap<String, Integer> symTab;
	private HashMap<Integer, Integer> litTab;
	private Integer loc;
	private Integer base;
	private Integer pc;
	private List<IStatement> states;
	private Queue < String> Literals;
	//public static HashSet<String> directives;
	public static int wordSize = 3; //TODO :read from configuration file.
	

	public Indexer(List<String> statements) {
		this.statements = statements;
		base = null;
		Literals = new LinkedList<String>();
	}

	@Override
	public HashMap<String, Integer> getSymTab() {
		return symTab;
	}

	@Override
	public HashMap<Integer, Integer> getLitTab() {
		return litTab;
	}

	@Override
	public List<IStatement> statements() {
		return states;
	}

	protected Statement createStatement(String statement) {

		if (Directive.isDirective(statement)) {
			return new Directive(statement);
		} else if(Operation.isOperation(statement))
			return new Operation(statement);
		return null;
	}
	
	

	@Override
	public void Parse() throws RuntimeException {
		states = new ArrayList<IStatement>();
		Exception generated = null;
		for (String statement : statements) {
			Statement state = createStatement(statement);

			if (state == null) {
				generated = new RuntimeException(""); // TODO : zabat di
			}
			state.setAddress(loc);
			if (state.operation().equals("BASE")) {
				base = symTab.get(state.operands());
				if(base == null)
				  generated = new RuntimeException(""); // TODO : zabat di
			}
			else if (state.operation().equals("NOBASE")) {
				base = null;
			}
			if (state.Label() != null) {
				if (symTab.containsKey(state.Label())) {
					generated = new RuntimeException(""); // TODO : zabat di
				}
				// TODO :if EQU evaluate expression
				symTab.put(state.Label(), state.getAddress());
			}

      loc = state.getNextLocationCounter(loc);
      if(state.operands().charAt(0)=='=') {
        Literals.add(state.operands());
      }

		}

	}
	
	protected Integer evaluateLiterals() {
	  
    return null;
	}
}