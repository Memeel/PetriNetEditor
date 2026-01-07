package org.pneditor.petrinet.models.sadokmelina;

import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of a Petri network that manages places, transitions, and arcs.
 * 
 * This implementation uses HashSets to store network elements and ensures consistency
 * when adding or removing elements by managing their relationships automatically.
 */
public class PetriNetwork implements IPetriNetwork{

    // List of places available in the network
    private final Set<Place> places;
    // List of transitions available in the network
    private final Set<Transition> transitions;
    // List of arcs available in the network
    private final Set<Arc> arcs;


    /**
     * Constructor of the PetriNetwork class; initializes the attributs
     * places, transitions and arcs as empty lists
     */
    public PetriNetwork() {
        this.places = new HashSet<>();
        this.transitions = new HashSet<>();
        this.arcs = new HashSet<>();
    }

    /**
     * Fire a transition if possible, otherwise it returns a message saying the action is not possible
     * @param t Transition to fire
     */
    public void fire(Transition t) {

        // Indicate if the transition is fireable
        boolean fireable = t.canFire();

        // Fire the transition if possible and return a message accordingly
        if (fireable) {
            t.fire();
            System.out.println("The transition " + t + " was successfully fired");
        } else {
            System.out.println("The transition " + t + " cannot be fired");
        }
    }

    /**
     * Add a transition to the network
     * @param t Transition to add
     */
    @Override
    public void addTransition(Transition t) {
        this.transitions.add(t);
    }

    /**
     * Remove a transition from the network
     * @param transition the transition to remove
     */
    @Override
    public void removeTransition(Transition transition) {
        // Try to remove the transition from the set of transitions
        boolean removed = this.transitions.remove(transition);

        if (removed) {
            // Remove all arcs connected to the transition
            for (Arc arc : transition.getInputArcs()) {
                this.removeArc(arc);
            }
            for (Arc arc : transition.getOutputArcs()) {
                this.removeArc(arc);
            }
        }

        // If no transition was removed, print a message
        else {
            System.out.println("Transition not found: " + transition);
        }
    }

    /**
     * Add a place to the network
     * @param p Place to add
     */
    @Override
    public void addPlace(Place p) {
        this.places.add(p);
    }

    /**
     * Remove a place from the network
     * @param place the place to remove
     */
    @Override
    public void removePlace(Place place) {
        // Try to remove the place from the set of places
        boolean removed = this.places.remove(place);
        
        if (removed) {
            // Remove all arcs connected to the place
            for (Arc arc : new HashSet<>(this.arcs)) {

                // InputArc case
                if (arc instanceof InputArc inputArc) {
                    if (inputArc.getSource().equals(place)) {
                        this.removeArc(arc);
                    }
                } 
                // OutputArc case
                else if (arc instanceof OutputArc outputArc) {
                    if (outputArc.getDestination().equals(place)) {
                        this.removeArc(arc);
                    }
                }
            }
        }
        // If no place was removed, print a message
        else {
            System.out.println("Place not found: " + place);
        }
    }

    /**
     * Add any type of arc to the network
     * @param a Arc to add
     */
    @Override
    public void addArc(Arc a) {

        // Determine the place and transition connected by the arc
        Place place = null;
        Transition transition = null;

        // InputArc case
        if (a instanceof InputArc inputArc) {
            place = inputArc.getSource();
            transition = inputArc.getDestination();
        } 
        
        // OutputArc case
        else if (a instanceof OutputArc outputArc) {
            place = outputArc.getDestination();
            transition = outputArc.getSource();
        } 

        // Ensure the place and transition are part of the network
        if (!this.places.contains(place)) {
            this.addPlace(place);
        }
        if (!this.transitions.contains(transition)) {
            this.addTransition(transition);
        }

        // If the arc is already present, throw an exception, otherwise add it to the transition
        boolean added = this.arcs.add(a);
        if (!added) {
            throw new ArcExceptions("This arc already exists in the Petri Network");
        }

        try {
            if (a instanceof InputArc inputArc) {
                transition.addInputArc(inputArc);
            } else if (a instanceof OutputArc outputArc) {
                transition.addOutputArc(outputArc);
            }
        } catch (Exception e) {
            this.arcs.remove(a); // rollback
            throw e;
        }
    }


    /**
     * Remove an arc from the network
     * @param arc the arc to remove
     */
    @Override
    public void removeArc(Arc arc) {
        // Try to remove the arc from the set of arcs
        boolean removed = this.arcs.remove(arc);

        // If the arc was removed, also remove it from the corresponding transition
        if (removed) {
            // InputArc case
            if (arc instanceof InputArc inputarc) {
                inputarc.getDestination().removeInputArc(inputarc);
            } 
            
            // OutputArc case
            else if (arc instanceof OutputArc outputarc) {
                outputarc.getSource().removeOutputArc(outputarc);
            } 

            else {
                // Never reached
            }
        }
            
        else {
            System.out.println("Arc not found: " + arc);
        }
    }
    
    /**
     * Gets the set of all places in the network.
     * 
     * @return the set of places
     */
    public Set<Place> getPlaces() {
        return this.places;
    }

    /**
     * Gets the set of all transitions in the network.
     * 
     * @return the set of transitions
     */
    public Set<Transition> getTransitions() {
        return this.transitions;
    }

    /**
     * Gets the set of all arcs in the network.
     * 
     * @return the set of arcs
     */
    public Set<Arc> getArcs() {
        return this.arcs;
    }

    /**
     * String representation of the Petri network
     * @return a string describing the Petri network       
     */
    @Override
    public String toString() {
        return """
            PetriNetwork{
                places=%s,
                transitions=%s,
                arcs=%s
            }
            """.formatted(places, transitions, arcs);
    }



    public static void main(String[] args) {
        // Example usage
        PetriNetwork petri = new PetriNetwork();
        Place p1 = new Place(2); // Place with initial tokens not linked to the PetriNetwork
        System.out.println(petri); // Output the empty PetriNetwork details
        petri.addPlace(p1); // Add place p1 to the PetriNetwork
        System.out.println(petri); // Output the PetriNetwork details with one place
        Place p2 = new Place(3); // Place with initial tokens and linked to the PetriNetwork
        petri.addPlace(p2); // Add place p2 to the PetriNetwork
        System.out.println(petri); // Output the PetriNetwork details with two places
        petri.removePlace(p1); // Remove place with ID 1
        
        Transition t1 = new Transition(); // Transition linked to the PetriNetwork
        Arc a1 = new ZeroArc(p2, t1); // Arc from place p2 to transition t1
        petri.addTransition(t1); // Add transition t1 to the PetriNetwork
        petri.addArc(a1); // Add the arc a1 to the PetriNetwork
 
        System.out.println(petri); // Output the PetriNetwork details with the arcs
        
        petri.fire(t1); // Try to fire transition t1
        System.out.println(petri); // Output the PetriNetwork details after firing attempt
        petri.removeArc(a1); // Remove the input arc a1
        System.out.println(petri); // Output the PetriNetwork details after removing the input arc
        System.out.println(petri); // Output the PetriNetwork details after removing the output arc
    }

}
