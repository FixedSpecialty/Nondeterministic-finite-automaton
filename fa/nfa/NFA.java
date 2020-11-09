package fa.nfa;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import fa.State;
import fa.dfa.DFA;
/**
 * Implementation of NFA class
 * @author Andre Murphy
 * @author Josh Dixon
 */
public class NFA implements NFAInterface{
	// all states associated with the DFA
		Set<NFAState> states;
		private char epsilon = 'e';

		// The alphabet of this DFA
		Set<Character> alphabet;
		
		// Set of all the final states of this DFA.
		Set<NFAState> finalStates;

		//can only be one intial state
		NFAState initial;

		public NFA() {
			states = new HashSet<NFAState>();
			alphabet = new HashSet<>();
			finalStates = new HashSet<NFAState>();
		}
	//add transition and 'link' two states
	public void addTransition(String fromState, char onSymb, String toState) {
		for (NFAState s : states) { //iterate through states
			if (fromState.equals(s.getName())) { //if equal...
				for(NFAState e : states){ //iterate through states again
					if(e.getName().equals(toState)) //if equals destination
						s.addTransition(onSymb, e); //add transition
				}
			}
		}
		for(Character c : alphabet){			
			if(Character.valueOf(c) == Character.valueOf(onSymb)){
				return;
			}
		}
		alphabet.add(onSymb);
		
	}

	//add state to set
	public void addState(String nextToken) {
		NFAState newState = new NFAState(nextToken);
		states.add(newState);
		
	}
	//add start state to set
	public void addStartState(String nextToken) {
		NFAState newState = new NFAState(nextToken); //create new state				
		newState.setInit(true); //set boolean value
		initial = newState; //set global variable
		for(NFAState s : states){ //iterate through states
			if(s.getName().equals(nextToken)){ //if  equal,,
				s.setInit(true); //set boolean value
				initial = s; //set global value
			}
		}		
		states.add(initial); //add initial global value to set
		
	}

	public void addFinalState(String nextToken) { //add final state to set
		NFAState newState = new NFAState(nextToken); //create new state
		for(NFAState s : states)
		{
			if(s.getName().equals(nextToken)) //if set has multiple states,
			{
				s.setFinal(true); //set true
				return;				
			}
		}
		newState.setFinal(true);
		finalStates.add(newState);
		states.add(newState);		
	}

	@Override
	public Set<? extends State> getStates() {
		return states;
	}

	@Override
	public Set<? extends State> getFinalStates() {
		return finalStates;
	}

	@Override
	public State getStartState() {
		for (NFAState s : states) {
			if (s.isInit()) {
				initial = s;
			}
		}
		return initial;
	}

	@Override
	public Set<Character> getABC() {
		return alphabet;
	}

	@Override
	public Set<NFAState> getToState(NFAState from, char onSymb) {
		
		return from.getTransitionTo(onSymb);
	}


	@Override
	public Set<NFAState> eClosure(NFAState s) {
		Set<NFAState> nfaSet = new HashSet<>();
		nfaSet.add(s);
		if(s.getTransitionTo(epsilon) != null)
		{
			for(NFAState nfa : s.getTransitionTo(epsilon))
			{
				nfaSet.add(nfa);
				if(!nfaSet.contains(nfa))
				{
					nfaSet.addAll(this.eClosure(nfa));
				}
			}
		}
		return nfaSet;
	}

	@Override
	//BFS algo for returning a NFA from NFA
	public DFA getDFA() {
		DFA dfa = new DFA(); //declare dfa
		Queue<Set<NFAState>> queue = new LinkedList<>(); //used a linked list as the start for adding the initial state
		Set<String> tracker = new HashSet<>();//hash set to keep track of states visited
		Set<NFAState> currState = eClosure(initial); //find the current state
		queue.add(currState); //add current state to queue
		tracker.add(currState.toString());	//add current state to tracker set			
		dfa.addStartState(currState.toString()); //set the final state for the DFA
		while(!queue.isEmpty()){ //while there are still states...
			currState = queue.remove(); //dequeue the last state
			for(Character sigma: alphabet){ //for everything in the language...
				Set<NFAState> tranStates = new HashSet<>();//create a set for transition states
				if(sigma != epsilon){ 	//if is not epsilon
					for(NFAState s: currState){ //for all states in our current states
						Set<NFAState> tran = getToState(s,sigma); //make a set of transition states
						if(tran != null){ //if the transition exits...
							tranStates.addAll(tran); 
						}
					}
					for(NFAState s:tranStates){ //this iterates through all transition states
						tranStates.addAll(eClosure(s)); //add epsilon transition states recursively
					}
					if(!tracker.contains(tranStates.toString())){ //if we still havent gone through all states...
						boolean isFinal = false; 
						for(NFAState s : tranStates){ //iterate through transition states and check for a final state
							if(s.isFinal()){
								isFinal = true; //set boolean if found
							}
						}
						if(isFinal){ //add the found final states to the DFA
							dfa.addFinalState(tranStates.toString());
						}
						else{ //else it was a normal state, add to the DFA
							dfa.addState(tranStates.toString());
						}
						queue.add(tranStates); 	//add transition states to the queue to deal with them on next loop
						tracker.add(tranStates.toString()); //add transitions states to already traveled set
					}
					dfa.addTransition(currState.toString(),sigma,tranStates.toString()); //add DFA transition
				}	
			}
		}
		return dfa;
	}
	

}
