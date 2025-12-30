PNEditor (Petri Net editor)
========

The original PNE can be downloaded from [www.pneditor.org](http://www.pneditor.org/)

This instance is the result of a student project by Joris Thaveau for teaching purpose.

It is a simplified Petri net editor that allows the editing of many PetriNet models.

To use:

1. Run org.pneditor.editor.Main as a Java application
2. Select the model used (the menu scans the org.pneditor.petrinet.adapters folder to build a list of available models and adapters). initial and imta are available. Places and transitions are displayed in different ways.
3. Edit the PetriNet and fire transitions.

You may experiment some unexpected exceptions. Especially if you mix models.

The pedagogical approach consists in:

1. Develop your own PetriNet model in an independent project/environment - with no GUI, just the ''business'' view
2. Pack it as a jar, and let it be visible in the path
3. Develop an Adapter in the org.pneditor.petrinet.adapters folder of PNE to make your model editable

The adapter may be simple or complex depending on the "distance" between your model and the one expected by PNE.

Code license: [GNU GPL v3](http://www.gnu.org/licenses/gpl.html)

Requirements: Java SE 8+

Our PetriNetwork Model (sadokmelina)
======================

## Class Diagrams

![First class diagram (before the implementation)](doc/FirstClassDiagram.jpg)

![Final class diagram made with Eclipse ObjectAID UML](doc/FinalClassDiagram.gif)

## Conception vs Code 

1. Before integration : 

Our final implementation is, for the most part, very coherent with our modeling choices from the beginning except for the method fireall in the PetriNetwork Interface that was intended to fire all the transitions at once and that we had to delete because we found it unnecessary for the validation of the project and very ambiguious when it comes to its implementation logic (the order in which to fire the transitions). Furthermore, we decided to add the abstract class AArc that implements the interface Arc so that we can stock the ID count of all arcs regardless of their type (InputArc or OutputArc) in a commun attribute called nextID that is incremented by the AArc's constructor wether it's an input arc or an output arc that is initialised. This way no two arcs would have the same ID and presentation of the network to the user would be simpler.

Otherwise, we implemented the same classes specified in the diagram and gave them the same chosen behavior shown in the sequence diagrams with the network calling the canfire method of transitions to check their "fireability" and then calling fire() if needed. Also, these canfire() and fire() methods work as intended with canfire() using isActive() from InputArc which compares the weight of the arc and the number of tokens in all the places it's coming from and with fire() using fire() from InputArc to remove tokens from the source places according to the weight of each input arc and then fire() from OutputArc to add tokens to the destination places according to the weight of each output arc. 

2. After integration :

When integrating our model by implementing the adapters classes, and specifically the ArcAdapter, we realised that in order to implement the setMultiplicity method correctly we had to add our own setter for the weight of the arc class in our model.
