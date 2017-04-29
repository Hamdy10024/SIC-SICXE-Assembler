package parserImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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
	public static HashSet<String> directives;

	public static void load() {
		directives.add("START");

		directives.add("ORG");

		directives.add("EQU");

		directives.add("RESW");

		directives.add("RESB");

		directives.add("WORD");

		directives.add("BYTE");

		directives.add("BASE");

		directives.add("NOBASE");

		directives.add("END");
	}

	public Indexer(List<String> statements) {
		this.statements = statements;
		base = null;
		load();
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

		if (directives.contains(statement.substring(9, 16).toUpperCase())) {
			return new Directive(statement);
		} else
			return new Operation(statement);
		// dreturn null;
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
			loc = state.getNextLocationCounter(loc);
			if (state.operation().equals("BASE"))
				base = symTab.get(state.operands());
			if (state.operation().equals("NOBASE"))
				base = null;
			if (state.Label() != null) {
				if (symTab.containsKey(state.Label())) {
					generated = new RuntimeException(""); // TODO : zabat di
				}
				// TODO :if EQU evaluate expression
				symTab.put(state.Label(), loc);
			}

		}

	}
}