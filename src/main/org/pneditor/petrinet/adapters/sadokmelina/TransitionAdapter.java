package org.pneditor.petrinet.adapters.sadokmelina;

import org.pneditor.petrinet.*;
import org.pneditor.petrinet.models.sadokmelina.*;

/**
 * Adapter class that bridges the sadokmelina Transition implementation with the PNEditor framework.
 * This adapter wraps a Transition instance and provides the interface expected by
 * the PNEditor's AbstractTransition class.
 * 
 * The adapter allows the custom Transition implementation to be used seamlessly
 * within the PNEditor GUI.
 */
public class TransitionAdapter extends AbstractTransition {

    /** The wrapped transition from the model */
    private final Transition transition;

    /**
     * Constructs a TransitionAdapter.
     * 
     * @param transition the concrete transition instance to wrap
     * @param label the label for display in the PNEditor GUI
     */
    public TransitionAdapter(Transition transition, String label) {
        super(label);
        this.transition = transition;
    }

    /**
     * Gets the wrapped transition instance.
     * 
     * @return the underlying Transition object
     */
    public Transition getTransition() {
        return this.transition;
    }
    
}
