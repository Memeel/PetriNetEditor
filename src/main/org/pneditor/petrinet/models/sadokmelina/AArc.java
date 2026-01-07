package org.pneditor.petrinet.models.sadokmelina;

/**
 * Abstract base class for all arc types in a Petri network.
 * Provides automatic ID assignment and management for arcs.
 * All specific arc types (InputArc, OutputArc, ResetArc, ZeroArc) should extend this class.
 */
public abstract class AArc implements Arc {

    // incremental ID for each arc
    private static int nextID = 1;
    // unique ID of the arc
    private final int id;

    /**
     * Constructor for AArc.
     * Automatically assigns a unique ID to the arc and increments the counter for the next arc.
     */
    public AArc() {
        // Assign unique ID and increment for next arc
        this.id = nextID++; 
    }

    /**
     * Getter for the arc ID
     * @return the unique identifier of the arc
     */
    @Override
    public int getId() {
        return this.id;
    }
}