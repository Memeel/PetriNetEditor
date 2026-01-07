package org.pneditor.petrinet.adapters.sadokmelina;

import org.pneditor.petrinet.*;
import org.pneditor.petrinet.models.sadokmelina.*;

/**
 * Adapter class that bridges the sadokmelina Place implementation with the PNEditor framework.
 * This adapter wraps a Place instance and provides the interface expected by
 * the PNEditor's AbstractPlace class.
 * 
 * The adapter handles token manipulation operations and delegates them to the
 * underlying Place object.
 */
public class PlaceAdapter extends AbstractPlace{

    /** The wrapped place from the model */
    private final Place place;

    /**
     * Constructs a PlaceAdapter.
     * 
     * @param p the concrete place instance to wrap
     * @param label the label for display in the PNEditor GUI
     */
    public PlaceAdapter(Place p, String label) {
        super(label);
        this.place = p;
    }
    
    /**
     * Gets the wrapped place instance.
     * 
     * @return the underlying Place object
     */
    public Place getPlace() {
        return this.place;
    }

    /**
     * Adds one token to the place.
     */
    @Override
    public void addToken() {
        place.addTokens(1);
    }

    /**
     * Removes one token from the place.
     */
    @Override
    public void removeToken() {
        place.removeTokens(1);
        
    }

    /**
     * Gets the current number of tokens in the place.
     * 
     * @return the number of tokens
     */
    @Override
    public int getTokens() {
        return place.getTokens();
    }

    /**
     * Sets the number of tokens in the place to a specific value.
     * This replaces the current token count.
     * 
     * @param tokens the new number of tokens
     */
    @Override
    public void setTokens(int tokens) {
        int placeTokens = place.getTokens();
        place.removeTokens(placeTokens);   
        place.addTokens(tokens);     
    }
}
