package fa.nfa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import fa.State;

public class NFAState extends State{

	private HashMap<Character, Set<NFAState>> transitions;
	private boolean finalState;
	private boolean init;
	
	public NFAState(String stateName)
	{
		name = stateName;
		init = false;
		finalState = false;
		transitions = new HashMap<Character, Set<NFAState>>();
		
	}

	public void setInit(boolean b) {
		init = b;
		
	}

	public void setFinal(boolean b) {
		finalState = b;
	}

	
	public boolean isInit() {
		return init;
	}

	public void addTransition(char onSymb, NFAState toState) {
		if(transitions.get(onSymb)!=null)
		{
			transitions.get(onSymb).add(toState);
		}
		else if(transitions.get(onSymb)==null)
		{
			Set<NFAState> stateSet = new HashSet<>();
			stateSet.add(toState);
			transitions.put(onSymb, stateSet);
		}
		
	}

	public Set<NFAState> getTransitionTo(char onSymb) {
		return transitions.get(onSymb);
	}
	
	
}
