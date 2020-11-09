# CS361P2

Java NFA
Andre Murphy and Josh Dixon

NFA
This a Java program that reads in a text file with states, transitions
and all possible languages accepted by the NFA. The program will then
print out the formal description of a DFA created from and NFA created from the given in the .txt file.

HOW DOES IT WORK?
Given a package of Java files, such as drivers and interfaces to work off,
we had to create two classes. NFAState and NFA. Starting out we created the
NFAState which is used to hold all the data involved in a state such as
initial, final, transitions. 
We had to create a set of states, a set for the alphabet, and a set for final
states. The bulk of the programming was in creating a DFA from the NFA. This was accomplished in the DFA method using a combination of breath and depth first serches. 

USAGE
This program is run through the DFADriver.

javac fa/nfa/NFADriver.java

java fa.nfa.NFADriver [testing text file]

ex. 
java fa.nfa.NFADriver ./tests/p1tc1.txt
java fa.nfa.NFADriver ./tests/p2tc1.txt
java fa.nfa.NFADriver ./tests/p3tc1.txt

TESTING
For testing we used custom .txt files to verify if out output was correct.


