package org.pneditor.petrinet.adapters.sadokmelina;

import org.pneditor.petrinet.*;
import org.pneditor.petrinet.models.sadokmelina.*;;

/**
 * Adapter class that bridges the sadokmelina PetriNetwork implementation with the PNEditor framework.
 * This adapter wraps a PetriNetwork instance and provides the interface expected by
 * the PNEditor's PetriNetInterface class.
 * 
 * This adapter implements all required methods for:
 * - Creating and managing places, transitions, and arcs
 * - Adding different types of arcs (regular, inhibitory, reset)
 * - Firing transitions and checking their enabled state
 * - Removing network elements
 * 
 * The adapter creates wrapper objects (PlaceAdapter, TransitionAdapter, ArcAdapter)
 * for all elements to ensure compatibility with the PNEditor framework.
 */
public class PetriNetAdapter extends PetriNetInterface {

    /** The wrapped Petri network from the model */
    private final PetriNetwork petriNetwork;

    /**
     * Constructs a new PetriNetAdapter with an empty Petri network.
     */
    public PetriNetAdapter() {
        this.petriNetwork = new PetriNetwork();
    }

    /**
     * Creates and adds a new place to the network.
     * The place is initialized with zero tokens.
     * 
     * @return a PlaceAdapter wrapping the newly created place
     */
    @Override
    public AbstractPlace addPlace() {
        Place p = new Place(0);
        petriNetwork.addPlace(p);
        AbstractPlace ap = new PlaceAdapter(p,"");
        return ap;
    };

    /**
     * Creates and adds a new transition to the network.
     * 
     * @return a TransitionAdapter wrapping the newly created transition
     */
    @Override
	public AbstractTransition addTransition() {
        Transition t = new Transition();
        petriNetwork.addTransition(t);
        AbstractTransition at = new TransitionAdapter(t, "");
        return at;
    };

    /**
     * Creates and adds a regular arc between two nodes.
     * Regular arcs can be:
     * - Input arcs: from a place to a transition (consume tokens)
     * - Output arcs: from a transition to a place (produce tokens)
     * 
     * The arc is created with a default weight of 1.
     * 
     * @param source the source node (place or transition)
     * @param destination the destination node (transition or place)
     * @return an ArcAdapter wrapping the newly created arc
     * @throws UnimplementedCaseException if the arc type is not supported
     */
    @Override
	public AbstractArc addRegularArc(AbstractNode source, AbstractNode destination)
			throws UnimplementedCaseException {
        if (source instanceof AbstractPlace && destination instanceof AbstractTransition) {
            InputArc arc = new InputArc(
                    ((PlaceAdapter) source).getPlace(),
                    ((TransitionAdapter) destination).getTransition(),
                    1);
            petriNetwork.addArc(arc);
            AbstractArc aa = new ArcAdapter(arc, source, destination);
            return aa;
        }
        else if (source instanceof AbstractTransition && destination instanceof AbstractPlace) {
            OutputArc arc = new OutputArc(
                    ((TransitionAdapter) source).getTransition(),
                    ((PlaceAdapter) destination).getPlace(),
                    1);
            petriNetwork.addArc(arc);
            AbstractArc aa = new ArcAdapter(arc, source, destination);
            return aa;
        }
        throw new UnimplementedCaseException("Unsupported arc type");
    };

    /**
     * Creates and adds an inhibitory (zero) arc from a place to a transition.
     * An inhibitory arc is only active when the source place has zero tokens.
     * It prevents the transition from firing when tokens are present in the place.
     * 
     * @param place the source place
     * @param transition the destination transition
     * @return an ArcAdapter wrapping the newly created zero arc
     * @throws UnimplementedCaseException if parameters are invalid
     */
    @Override
	public AbstractArc addInhibitoryArc(AbstractPlace place, AbstractTransition transition)
			throws UnimplementedCaseException {
        if ( !(place instanceof AbstractPlace) || !(transition instanceof AbstractTransition)) {
            throw new UnimplementedCaseException("Inhibitory arc can only be created from Place to Transition");
        }
        ZeroArc arc = new ZeroArc(
                ((PlaceAdapter) place).getPlace(),
                ((TransitionAdapter) transition).getTransition());
        petriNetwork.addArc(arc);
        AbstractArc aa = new ArcAdapter(arc, place, transition);
        return aa;
    };
    
    /**
     * Creates and adds a reset arc from a place to a transition.
     * A reset arc removes all tokens from the source place when the transition fires,
     * regardless of how many tokens are present.
     * 
     * @param place the source place
     * @param transition the destination transition
     * @return an ArcAdapter wrapping the newly created reset arc
     * @throws UnimplementedCaseException if parameters are invalid
     */
    @Override
	public AbstractArc addResetArc(AbstractPlace place, AbstractTransition transition)
			throws UnimplementedCaseException {
        if (!(place instanceof AbstractPlace) || !(transition instanceof AbstractTransition)) {
            throw new UnimplementedCaseException("Reset arc can only be created from Place to Transition");
        }
        ResetArc arc = new ResetArc(
                ((PlaceAdapter) place).getPlace(),
                ((TransitionAdapter) transition).getTransition());
        petriNetwork.addArc(arc);
        AbstractArc aa = new ArcAdapter(arc, place, transition);
        return aa;
    };

    /**
     * Removes a place from the network.
     * All arcs connected to the place are also removed automatically.
     * 
     * @param place the place to remove
     */
    @Override
	public void removePlace(AbstractPlace place) {
        petriNetwork.removePlace(((PlaceAdapter) place).getPlace());
    };

    /**
     * Removes a transition from the network.
     * All arcs connected to the transition are also removed automatically.
     * 
     * @param transition the transition to remove
     */
    @Override
	public void removeTransition(AbstractTransition transition) {
        petriNetwork.removeTransition(((TransitionAdapter) transition).getTransition());
    };

    /**
     * Removes an arc from the network.
     * 
     * @param arc the arc to remove
     */
    @Override
	public void removeArc(AbstractArc arc) {
        petriNetwork.removeArc(((ArcAdapter) arc).getArc());
    };

    /**
     * Checks if a transition is enabled (can fire).
     * A transition is enabled if all its input arcs are active.
     * 
     * @param transition the transition to check
     * @return true if the transition can fire, false otherwise
     * @throws ResetArcMultiplicityException if there's an issue with reset arc multiplicity
     */
    @Override
	public boolean isEnabled(AbstractTransition transition) throws ResetArcMultiplicityException {
        return ((TransitionAdapter) transition).getTransition().canFire();
    };

    /**
     * Fires a transition.
     * When a transition fires:
     * 1. All input arcs consume tokens from their source places
     * 2. All output arcs produce tokens in their destination places
     * 
     * @param transition the transition to fire
     * @throws ResetArcMultiplicityException if there's an issue with reset arc multiplicity
     */
    @Override
	public void fire(AbstractTransition transition) throws ResetArcMultiplicityException {
        ((TransitionAdapter) transition).getTransition().fire();
    };
    
}
