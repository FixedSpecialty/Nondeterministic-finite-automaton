# CS361P2

Java DFA
Andre Murphy and Josh Dixon

DFA
This a Java program that reads in a text file with states, transitions
and all possible languages accepted by the DFA. The program will then
print out the formal description of the DFA given in the .txt file.

HOW DOES IT WORK?
Given a package of Java files, such as drivers and interfaces to work off,
we had to create two classes. DFAState and DFA. Starting out we created the
DFAState which is used to hold all the data involved in a state such as
initial, final, transitions. The DFA was really the meat of the project. 
We had to create a set of states, a set for the alphabet, and a set for final
states. In the given files, there were two methods used for checking output;
complement and accepts. For the complement method we created a new DFA, iterated
through the old states and set the new values accordingly.

USAGE
This program is run through the DFADriver.

javac fa/dfa/DFADriver.java

java fa.dfa.DFADriver [testing text file]

ex. 
java fa.dfa.DFADriver ./tests/p1tc1.txt
java fa.dfa.DFADriver ./tests/p2tc1.txt
java fa.dfa.DFADriver ./tests/p3tc1.txt

TESTING
For testing we used custom .txt files to verify if out output was correct.


