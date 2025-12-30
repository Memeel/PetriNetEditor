package org.pneditor.petrinet.models.sadokmelina;

/**
 * Represents an input arc in a Petri network.
 * An input arc connects a place (source) to a transition (destination).
 * When the transition fires, the arc consumes tokens from the source place.
 * 
 * Properties:
 * - Has a weight that determines how many tokens are consumed
 * - Is active when the source place has at least 'weight' tokens
 * - When fired, removes 'weight' tokens from the source place
 */
public class InputArc extends AArc {
    
    private Place source;  //the place from which the arc comes
    private Transition destination;  //the transition to which the arc goes
    private int weight;  //the weight of the arc

    /** Constructor for creating an input arc 
     * @param place the place from which the arc comes
     * @param transition the transition to which the arc goes
     * @param weight the weight of the arc (default is 1)
     */
    public InputArc(Place place, Transition t, int weight) throws ArcExceptions {
        // Call to the superclass constructor for ID assignment
        super();

        // Validate place
        if (place == null) {
            throw new ArcExceptions("Place is null");
        }
        this.source = place;

        // Validate transition
        if (t == null) {
            throw new ArcExceptions("Transition is null");
        }
        this.destination = t;
        
        // Validate weight
        if (weight < 0) {
            throw new ArcExceptions("Weight must be positive");
        }
        if (weight == 0 && !(this instanceof ZeroArc)) {
            throw new ArcExceptions("Weight must be strictly positive unless it's a ZeroArc");
        }
        this.weight = weight;
    }

    /**
     * Getter for the place
     * @return the place from which the arc comes
     */
    public Place getSource() {
        return source;
    }

    /**
     * Getter for the transition
     * @return the transition to which the arc goes
     */
    public Transition getDestination() {
        return destination;
    }

    /**
     * Getter for the weight
     * @return the weight of the arc
     */
    @Override
    public int getWeight() {
        return weight;
    }

    /**
     * Setter for the weight
     * @param weight the new weight of the arc
     */
    @Override
    public void setWeight(int weight) {
        this.weight = weight;
    }

    /**
     * String representation of the input arc
     * @return a string describing the input arc       
     */
    @Override
    public String toString() {
        return "InputArc [id=" + super.getId() + ", place=" + source.getId() + ", transition=" + destination.getId() + ", weight=" + weight + "]";
    }

    /**
     * Equals method to compare two input arcs as way to detect "doublants"
     * @param obj the object to compare with
     * @return true if the arcs are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Optimisation
        
        // Assert that obj is not null and is of the same class
        if (obj == null || getClass() != obj.getClass()) return false;
        
        InputArc other = (InputArc) obj;
        
        // Compare the relevant fields for equality
        return source.equals(other.source) && 
            destination.equals(other.destination);
    }

    /**
     * HashCode method consistent with equals
     * @return the hash code of the input arc
     */
    @Override
    public int hashCode() {
        // Generate hash code based on relevant fields
        return java.util.Objects.hash(source, destination);
    }

    /**
     * Method to check if the arc can be fired
     * @return true if the arc can be fired (i.e., if the place has enough tokens)
     */
    @Override
    public boolean isActive() {
        return source.getTokens() >= weight;
    }
    
    /**
     * the Fire method for the input arc
     */
    @Override
    public void fire() {
        if (this.isActive()) {
            source.removeTokens(weight);
        }
    }

    public static void main(String[] args) {
        Place p = new Place(5);
        Transition t = new Transition();
        InputArc ia = new InputArc(p, t, 3);

        System.out.println("Before firing:");
        System.out.println(p);
        System.out.println(t);
        System.out.println(ia);

        if (ia.isActive()) {
            ia.fire();
            System.out.println("Input arc fired.");
        } else {
            System.out.println("Input arc cannot fire.");
        }

        System.out.println("After firing:");
        System.out.println(p);
        System.out.println(t);
    }

}
