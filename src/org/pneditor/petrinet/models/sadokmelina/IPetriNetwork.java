package org.pneditor.petrinet.models.sadokmelina;

/**
 * Interface defining the core operations for managing a Petri network.
 * A Petri network consists of places, transitions, and arcs that connect them.
 * This interface provides methods to add and remove these elements.
 */
public interface IPetriNetwork {

    /**
     * Adds a transition to the network
     * @param t Transition to add
     */ 
    public void addTransition(Transition t);

    /**
     * Removes a transition
     * @param transition the transition we want to remove
     */ 
    public void removeTransition(Transition transition);

    /**
     * Adds a place to the network
     * @param place Place to add
     */
    public void addPlace(Place place);

    /**
     * Removes a place
     * @param place the place we want to remove
     */
    public void removePlace(Place place);

    /**
     * Adds an arc to the network
     * @param arc Arc to add
     */
    public void addArc(Arc arc);

    /**
     * Removes an arc
     * @param arc the arc to remove
     */
    public void removeArc(Arc arc);

}
