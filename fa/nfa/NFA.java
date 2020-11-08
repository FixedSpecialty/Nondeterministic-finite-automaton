package fa.nfa;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import fa.State;
import fa.dfa.DFA;
import fa.dfa.DFAState;

public class NFA implements NFAInterface{
	// all states associated with the DFA
		Set<NFAState> states = new HashSet<>();
		private char epsilon = 'e';

		// The alphabet of this DFA
		Set<Character> alphabet;

		Set<Character> transitions;
		
		// Set of all the final states of this DFA.
		Set<NFAState> finalStates;

		NFAState initial;

		public NFA() {
			states = new HashSet<NFAState>();
			alphabet = new HashSet<>();
			transitions = new HashSet<>();
			finalStates = new HashSet<NFAState>();
		}
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

	public void addStartState(String nextToken) {
		NFAState newState = new NFAState(nextToken);
		for (NFAState s : states) {
			if (s.getName().equals(nextToken)) {
				initial = s;
				s.setInit(true);				
				return;
			}
		}
		
		newState.setInit(true);
		states.add(newState);
		
	}

	public void addFinalState(String nextToken) {
		NFAState newState = new NFAState(nextToken);
		for(NFAState s : states)
		{
			if(s.getName().equals(nextToken))
			{
				s.setFinal(true);
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
		Set<NFAState> startHash = new HashSet<NFAState>();
		LinkedList<Set<NFAState>> queue = new LinkedList<Set<NFAState>>();
		//Set<NFAState> history = new HashSet<>();
		startHash.add(initial);
		Set<NFAState> initialDFA = eClosure(initial);
		Set<NFAState> checkedStates = new HashSet<NFAState>();
		queue.add(initialDFA);
		dfa.addStartState(Set.of(initial).toString());

		while(!queue.isEmpty())
		{
			Set<NFAState> removed = queue.remove();
			for(NFAState all: removed)
			{
				checkedStates.add(all);
				removed.addAll(eClosure(all));
			}
			for(char alph: alphabet)
			{
				boolean isFinal = false;
				if(alph != 'e')
				{
					Set<NFAState> leafs = new HashSet<>();
					Set<NFAState> eTranS = new HashSet<>();
					for (NFAState state: removed)
					{
						if(state.getTransitionTo(alph) != null)
						{
							leafs.addAll(state.getTransitionTo(alph));
						}
					}
					if(leafs !=null)
					{
						for(NFAState s: leafs)
						{
							if(s.isFinal())
							{
								isFinal=true;
							}
							else isFinal=false;
						eTranS.addAll(eClosure(s));
						}
						
					}
					boolean match = false;
					for(DFAState dfaS :dfa.getStates())
					{
						if(dfaS.toString().equals(eTranS.toString()))
						{
							match = true;
						}
					}
					if(!isFinal)
					{
						if(!removed.toString().equals(eTranS.toString())&&!match)
						{
							dfa.addState(eTranS.toString());
						}
						dfa.addTransition(removed.toString(),alph, eTranS.toString());
					}
					if(!removed.toString().equals(eTranS.toString())&&!match)
						{
							if(!isFinal)
							{
							dfa.addState(eTranS.toString());
							}
							else
							{
								dfa.addFinalState(eTranS.toString());
							}
						}
						dfa.addTransition(removed.toString(),alph, eTranS.toString());
					if(!removed.toString().equals(eTranS.toString()))
					 {
						queue.add(eTranS);
						}
				}
			}

		}

		return dfa;
	}
	

}
