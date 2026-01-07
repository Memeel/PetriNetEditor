package org.pneditor.petrinet.models.sadokmelina;

/**
 * Represents a reset arc in a Petri network.
 * A reset arc is a special type of input arc that empties all tokens from the source place
 * when the transition fires, regardless of how many tokens are present.
 * 
 * Properties:
 * - Extends InputArc with infinite weight (Integer.MAX_VALUE)
 * - Is active when the source place has at least one token
 * - When fired, removes ALL tokens from the source place (resets it to 0)
 */
public class ResetArc extends InputArc {

    /** Constructor for creating a reset arc
     * @param place the place from which the arc comes
     * @param transition the transition to which the arc goes
     */
    public ResetArc(Place place, Transition t) {
        super(place, t, Integer.MAX_VALUE); // Call to InputArc constructor with infinite weight
    }

    /**
     * String representation of the reset arc
     * @return a string describing the reset arc       
     */
    @Override
    public String toString() {
        return "ResetArc [id=" + super.getId() + ", source=Place" + super.getSource().getId() + ", destination=Transition" + super.getDestination().getId() + "]";
    }
    
    /**
     * Checks if the reset arc is active.
     * A reset arc is active when the source place has at least one token.
     * 
     * @return true if the place has at least one token, false otherwise
     */
    @Override
    public boolean isActive() {
        return this.getSource().getTokens() > 0;
    }

    /**
     * Fires the reset arc, removing all tokens from the source place.
     * This effectively resets the place to zero tokens.
     */
    @Override
    public void fire() {
        super.getSource().removeTokens(super.getSource().getTokens());  
    }

    public static void main(String[] args) {
        // Example usage
        Place p = new Place(1);
        Transition t = new Transition();
        ResetArc ra = new ResetArc(p, t);

        System.out.println(ra); // Output the ResetArc details
        System.out.println("Is ResetArc ra active? " + ra.isActive()); // Should be true
        System.out.println("Tokens in Place p before firing: " + p.getTokens()); // Should be 5
        ra.fire(); // Fire the ResetArc
        System.out.println("Tokens in Place p after firing: " + p.getTokens()); // Should be 0
    }
}
