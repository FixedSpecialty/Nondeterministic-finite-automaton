package fa.nfa;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import fa.State;
/**
 * Implementation of NFAState class 
 * in p1p2
 * @author Andre Murphy
 * @author Josh Dixon
 */
public class NFAState extends State{

	private HashMap<Character, Set<NFAState>> transitions;
	private boolean finalState;
	private boolean init;
	
	/**
	 * Constructor for state
	 * @param stateName string representing the name of the state
	 */
	public NFAState(String stateName)
	{
		name = stateName;
		init = false;
		finalState = false;
		transitions = new HashMap<Character, Set<NFAState>>();
		
	}
	
	/**
	 * setter for init trait of state
	 * @param b boolean value, true if this is a start state
	 */
	public void setInit(boolean b) {
		init = b;
		
	}

	/**
	 * setter for final state
	 * @param b boolean value, true if this is a start date
	 */
	public void setFinal(boolean b) {
		finalState = b;
	}

	/**
	 * getter for if this this an initial state 
	 * @return boolean, true if this is a start state
	 */
	public boolean isInit() {
		return init;
	}

	/**
	 * getter for if this this an final state 
	 * @return boolean, true if this is a final state
	 */
	public boolean isFinal() {
		return finalState;
	}

	/**
	 * Adds a transition to this nfa state
	 * @param onSymb - the symbol used to take the transition
	 * @param toState - the state to transition to based on symbol
	 */
	public void addTransition(char onSymb, NFAState toState) {
		if(transitions.get(onSymb)!=null)
		{
			Set<NFAState> tranList = transitions.get(onSymb);
			tranList.add(toState);
			transitions.put(onSymb, tranList);
		}
		else if(transitions.get(onSymb)==null)
		{
			Set<NFAState> stateSet = new HashSet<>();
			stateSet.add(toState);
			transitions.put(onSymb, stateSet);
		}
		
	}

	/**
	 * returns a set of all states that can be transitioned to based on the 
	 * current character onSymb
	 * @param onSymb
	 * @return
	 */
	public Set<NFAState> getTransitionTo(char onSymb) {
		return transitions.get(onSymb);
	}
	
	
}
