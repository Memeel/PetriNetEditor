package org.pneditor.petrinet.models.sadokmelina;

/**
 * Represents an output arc in a Petri network.
 * An output arc connects a transition (source) to a place (destination).
 * When the transition fires, the arc produces tokens in the destination place.
 * 
 * Properties:
 * - Has a weight that determines how many tokens are produced
 * - Is always active (output arcs don't block transitions)
 * - When fired, adds 'weight' tokens to the destination place
 */
public class OutputArc extends AArc {

    private Transition source;  //the transition from which the arc comes
    private Place destination;  //the place to which the arc goes
    private int weight;  //the weight of the arc

    /** Constructor to create an output arc without adding it to a Petri network
     * @param id the unique identifier of the arc
     * @param transition the transition from which the arc comes
     * @param place the place to which the arc goes
     * @param weight the weight of the arc (default is 1)
     */
    public OutputArc(Transition t, Place place, int weight) throws ArcExceptions {
        // Call to the superclass constructor for ID assignment
        super();

        // Validate transition
        if (t == null) {
            throw new ArcExceptions("Transition is null");
        }
        this.source = t;

        // Validate place
        if (place == null) {
            throw new ArcExceptions("Place is null");
        }
        this.destination = place;

        // Validate weight
        if (weight <= 0) {
            throw new ArcExceptions("Weight must be positive");
        }
        this.weight = weight;
    }

    /**
     * Getter for the transition
     * @return the transition from which the arc comes
     */
    public Transition getSource() {
        return source;
    }

    /**
     * Getter for the place
     * @return the place to which the arc goes
     */
    public Place getDestination() {
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
     * String representation of the output arc
     * @return a string describing the output arc       
     */
    @Override
    public String toString() {
        return "OutputArc [id=" + super.getId() + ", transition=" + source.getId() + ", place=" + destination.getId() + ", weight=" + weight + "]";
    }

    /**
     * Equals method to compare two output arcs as way to detect "doublants"
     * @param obj the object to compare with
     * @return true if the arcs are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Optimisation
        
        // Assert that obj is not null and is of the same class
        if (obj == null || getClass() != obj.getClass()) return false;
        
        OutputArc other = (OutputArc) obj;
        
        // Compare the relevant fields for equality
        return source.equals(other.source) && 
            destination.equals(other.destination);
    }


    /**
     * HashCode method consistent with equals
     * @return the hash code of the output arc
     */
    @Override
    public int hashCode() {
        // Generate hash code based on relevant fields
        return java.util.Objects.hash(source, destination);
    }

    /**
     * isactive method that returns true at all times for output arcs
     * @return true 
     */
    @Override
    public boolean isActive() {
        return true;
    }

    /**
     * Method to fire the arc, adding tokens to the destination place
     */
    @Override
    public void fire() {
        destination.addTokens(weight);
    }

    public static void main(String[] args) {
        Place p = new Place(1);
        Transition t = new Transition();
        OutputArc oa = new OutputArc(t, p, 2);

        System.out.println("Before firing:");
        System.out.println(p);
        System.out.println(t);
        System.out.println(oa);

        if (oa.isActive()) {
            oa.fire();
            System.out.println("Output arc fired.");
        } else {
            System.out.println("Output arc cannot fire.");
        }

        System.out.println("After firing:");
        System.out.println(p);
        System.out.println(t);
    }
}
