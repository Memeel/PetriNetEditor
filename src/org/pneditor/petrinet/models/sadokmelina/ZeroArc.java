package org.pneditor.petrinet.models.sadokmelina;

/**
 * Represents a zero arc (also known as inhibitor arc) in a Petri network.
 * A zero arc is a special type of input arc that has opposite behavior to regular arcs:
 * it is only active when the source place is empty (has zero tokens).
 * 
 * Properties:
 * - Extends InputArc with weight 0
 * - Is active ONLY when the source place has exactly 0 tokens
 * - When fired, does nothing (doesn't consume or produce tokens)
 */
public class ZeroArc extends InputArc {

    /** Constructor for creating a zero arc
     * @param place the place from which the zero arc originates
     * @param transition the transition to which the zero arc points
     */
    public ZeroArc(Place place, Transition transition) {
        super(place, transition,0);  //weight is always 0 for ZeroArc   
    }

    /**
     * String representation of the zero arc
     * @return a string describing the zero arc
     */
    @Override
    public String toString() {
        return "ZeroArc [id=" + super.getId() + ", Place= " + super.getSource().getId() + ", Transition= " + super.getDestination().getId() + "]";
    }

    /**
     * a specific isActive method for ZeroArc
     * @return true if the place has zero tokens, false otherwise
     */
    @Override
    public boolean isActive() {
        return super.getSource().getTokens() == 0;
    }

    /**
     * the Fire method for the zero arc
     * does nothing as firing a zero arc does not change the number of tokens in the place
     */
    @Override
    public void fire() {
        // ZeroArc does not change the number of tokens in the place when fired
    }


    public static void main(String[] args) {
        // Example usage
        Place p = new Place(1);
        Place p2 = new Place(2); // Another place with 1 token
        Transition t = new Transition();
        ZeroArc za = new ZeroArc(p, t);
        ZeroArc za2 = new ZeroArc(p2, t); // ZeroArc connected to place with 1 token

        System.out.println(za); // Output the ZeroArc details
        System.out.println("Is ZeroArc active? " + za.isActive()); // Should be true since place has 0 tokens
        System.out.println(za2); // Output the ZeroArc details
        System.out.println("Is ZeroArc active? " + za2.isActive()); // Should be false since place has 1 token
    }
}
