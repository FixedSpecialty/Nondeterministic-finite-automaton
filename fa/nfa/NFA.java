package fa.nfa;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import fa.State;
import fa.dfa.DFA;

public class NFA implements NFAInterface{
	// all states associated with the DFA
		Set<NFAState> states = new HashSet<>();
		private char epsilon = 'e';

		// The alphabet of this DFA
		Set<Character> alphabet;

		// Set of all the final states of this DFA.
		Set<NFAState> finalStates;


		//think this works? need to test but logic seems correct...
	public void addTransition(String fromState, char onSymb, String toState) {
		NFAState from = new NFAState(fromState);
		NFAState to = new NFAState(toState);
		for (NFAState s : states) {
			if (fromState.equals(s.getName())) {
				s.addTransition(onSymb, to);
			}
		}
		for(Character c : alphabet){			
			if(Character.valueOf(c) == Character.valueOf(onSymb)){
				return;
			}
		}
		alphabet.add(onSymb);
		
	}

	public void addState(String nextToken) {
		NFAState newState = new NFAState(nextToken);
		states.add(newState);
		
	}

	public void addStartState(String name) {
		for (NFAState s : states) {
			if (s.getName().equals(name)) {
				s.setInit(true);				
				return;
			}
		}
		NFAState newState = new NFAState(name);
		newState.setInit(true);
		states.add(newState);
		
	}

	public void addFinalState(String nextToken) {
		NFAState newState = new NFAState(nextToken);
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
				return s;
			}
		}
		return null;
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
	//THIS IS THE BIG BOI. BIG ALGORITHM, MUCH CODE
	public DFA getDFA() {
		DFA dfa = new DFA();
		PriorityQueue<Set<NFAState>> dfaQueue = new PriorityQueue<Set<NFAState>>();
		return dfa;
	}

}
