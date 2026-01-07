package org.pneditor.petrinet.models.sadokmelina;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a transition in a Petri network.
 * Transitions are connected to places via input and output arcs.
 * 
 * A transition can fire if all its input arcs are active (their firing conditions are met).
 * When a transition fires:
 * 1. All input arcs consume tokens from their source places
 * 2. All output arcs produce tokens in their destination places
 * 
 * Each transition has:
 * - A unique identifier (ID) automatically assigned at creation
 * - A set of input arcs (arcs coming from places)
 * - A set of output arcs (arcs going to places)
 */
public class Transition {

    private final int id;  
    private final Set<Arc> inputArcs;  //list of the input (entering the transition) arcs 
    private final Set<Arc> outputArcs;  //list of the output (exiting the transition) arcs
    private static int NextID = 1;

    /** Constructor to create a transition without adding it to a Petri network
     * The transition ID is automatically assigned and incremented for each new transition
     * The input and output arc lists are initialized as empty
     */
    public Transition() {
        this.id = NextID++;
        this.inputArcs = new HashSet<>();  
        this.outputArcs = new HashSet<>();
    }

    /** Method to add an input arc to the transition
     * @param arc the input arc to be added
     */
    public void addInputArc(Arc arc) {
        inputArcs.add(arc);
    }

    /** Method to remove an input arc from the transition
     * @param arc the input arc to be removed
     */
    public void removeInputArc(Arc arc) {
        inputArcs.remove(arc);
    }

    /** Method to add an output arc to the transition
     * @param arc the output arc to be added
     */
    public void addOutputArc(Arc arc) {
        outputArcs.add(arc); 
    }

    /** Method to remove an output arc from the transition
     * @param arc the output arc to be removed
     */
    public void removeOutputArc(Arc arc) {
        outputArcs.remove(arc);
    }

    /**
     * Getter for the transition ID
     * @return the unique identifier of the transition
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the list of input arcs
     * @return the list of input arcs
     */
    public Set<Arc> getInputArcs() {
        return inputArcs;
    }

    /**
     * Getter for the list of output arcs
     * @return the list of output arcs
     */
    public Set<Arc> getOutputArcs() {
        return outputArcs;
    }

    /**
     * String representation of the transition
     * @return a string describing the transition       
     */
    @Override
    public String toString() {
        //getting the list of ids of all the input arcs
        Set<Integer> inputArcIds = new HashSet<>();
        for (Arc arc : inputArcs) {
            inputArcIds.add(arc.getId());
        }
        //getting the list of ids of all the output arcs
        Set<Integer> outputArcIds = new HashSet<>();
        for (Arc arc : outputArcs) {
            outputArcIds.add(arc.getId());
        }

        return "Transition [" +
                "id=" + this.id +
                ", inputArcs=" + inputArcIds +
                ", outputArcs=" + outputArcIds +
                ']';
    }

    /**
     * Checks if the transition is enabled (can fire).
     * A transition is enabled if all its input arcs are active (their firing conditions are met).
     * For regular input arcs, this means the source place has enough tokens (at least the arc's weight).
     * For special arcs (ZeroArc, ResetArc), this depends on their specific activation rules.
     * 
     * @return true if the transition is enabled, false otherwise
     */
    public boolean canFire() {
        for (Arc arc : inputArcs) {
            if (!arc.isActive()) {  
                return false;  // If any input arc is not active, the transition is not enabled
            }
        }
        return true;  
    }

    /** Method to fire the transition
     * This method will trigger all input arcs to consume tokens and all output arcs to produce tokens
     */
    public void fire() {
        // First, consume tokens from all input arcs
        for (Arc arc : inputArcs) {
            arc.fire();  // Assuming Arc class has its own method to consume tokens
        }
        // Then, produce tokens on all output arcs
        for (Arc arc : outputArcs) {
            arc.fire();  // Assuming Arc class has its own method to produce tokens
        }
    }

    public static void main(String[] args) {
        // Example usage
        PetriNetwork net = new PetriNetwork();
        Place p1 = new Place(5);  // Place with id 1 and 5 tokens
        Place p2 = new Place(0);  // Place with id 2 and 0 tokens
        net.addPlace(p1);
        net.addPlace(p2);
        Transition t1 = new Transition();  // Transition with id 1

        InputArc ia1 = new InputArc(p1, t1, 3);  // Input arc from p1 to t1 with weight 3
        OutputArc oa1 = new OutputArc(t1, p2, 2); // Output arc from t1 to p2 with weight 2

        System.out.println("Before firing:");
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(t1);
        System.out.println(ia1);
        System.out.println(oa1);

        if (t1.canFire()) {
            t1.fire();
            System.out.println("Transition fired.");
        } else {
            System.out.println("Transition cannot fire.");
        }

        System.out.println("After firing:");
        System.out.println(p1);
        System.out.println(p2);
    }
}

