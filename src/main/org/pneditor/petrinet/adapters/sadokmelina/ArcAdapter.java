package org.pneditor.petrinet.adapters.sadokmelina;

import org.pneditor.petrinet.*;
import org.pneditor.petrinet.models.sadokmelina.*;

/**
 * Adapter class that bridges the sadokmelina Arc implementation with the PNEditor framework.
 * This adapter wraps a concrete Arc instance and provides the interface expected by
 * the PNEditor's AbstractArc class.
 * 
 * The adapter pattern allows the custom Petri network implementation to integrate
 * seamlessly with the PNEditor GUI without modifying either the model or the editor code.
 */
public class ArcAdapter extends AbstractArc {

    /** The wrapped arc from the model */
    private final Arc arc;
    
    /** The source node (either a place or transition) */
    private final AbstractNode source;
    
    /** The destination node (either a transition or place) */
    private final AbstractNode destination;

    /**
     * Constructs an ArcAdapter.
     * 
     * @param arc the concrete arc instance to wrap
     * @param source the source node of the arc
     * @param destination the destination node of the arc
     */
    public ArcAdapter(Arc arc, AbstractNode source, AbstractNode destination) {
        this.arc = arc;
        this.source = source;
        this.destination = destination;
    }

    /**
     * Gets the wrapped arc instance.
     * 
     * @return the underlying Arc object
     */
    public Arc getArc() {
        return arc;
    }

    /**
     * Gets the source node of the arc.
     * 
     * @return the source node (AbstractNode)
     */
    @Override
    public AbstractNode getSource() {
        return source;
    }

    /**
     * Gets the destination node of the arc.
     * 
     * @return the destination node (AbstractNode)
     */
    @Override
    public AbstractNode getDestination() {
        return destination;
    }

    /**
     * Checks if the source of the arc is a place.
     * 
     * @return true if the source is a PlaceAdapter, false otherwise
     */
    @Override
    public boolean isSourceAPlace() {
        if (source instanceof PlaceAdapter) {
            return true;
        }
        return false;
    }

    /**
     * Checks if this is a reset arc.
     * Reset arcs empty all tokens from a place when fired.
     * 
     * @return true if the wrapped arc is a ResetArc, false otherwise
     */
    @Override
    public boolean isReset() {
        if (arc instanceof ResetArc) {
            return true;
        }
        return false;
    }

    /**
     * Checks if this is a regular arc.
     * Regular arcs are neither reset nor inhibitory (zero) arcs.
     * 
     * @return true if the arc is a regular input or output arc, false otherwise
     */
    @Override
    public boolean isRegular() {
        if (!(arc instanceof ResetArc || arc instanceof ZeroArc)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if this is an inhibitory (zero) arc.
     * Inhibitory arcs are only active when the source place has zero tokens.
     * 
     * @return true if the wrapped arc is a ZeroArc, false otherwise
     */
    @Override
    public boolean isInhibitory() {
        if (arc instanceof ZeroArc) {
            return true;
        }
        return false;
    }

    /**
     * Gets the multiplicity (weight) of the arc.
     * For reset arcs, this operation is not applicable and throws an exception.
     * 
     * @return the weight of the arc
     * @throws ResetArcMultiplicityException if the arc is a reset arc
     */
    @Override
    public int getMultiplicity() throws ResetArcMultiplicityException {
        if (this.isReset()) {
            throw new ResetArcMultiplicityException();
        }
        return arc.getWeight();
    }

    /**
     * Sets the multiplicity (weight) of the arc.
     * For reset arcs, this operation is not applicable and throws an exception.
     * 
     * @param multiplicity the new weight to set
     * @throws ResetArcMultiplicityException if the arc is a reset arc
     */
    @Override
    public void setMultiplicity(int multiplicity) throws ResetArcMultiplicityException {
        if (this.isReset()) {
            throw new ResetArcMultiplicityException();
        }
        arc.setWeight(multiplicity);
    }

}
