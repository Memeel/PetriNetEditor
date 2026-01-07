package org.pneditor.petrinet.models.sadokmelina;

/**
 * Represents a place in a Petri network.
 * A place is a node that can hold tokens (represented as non-negative integers).
 * Places are connected to transitions via arcs and represent states or conditions
 * in the modeled system.
 * 
 * Each place has:
 * - A unique identifier (ID) automatically assigned at creation
 * - A number of tokens (can be 0 or positive)
 */
public class Place {

    private final int id;
    private int tokens;
    private static int NextID = 1;

    /**
     * Constructor to create a place without adding it to a Petri network
     * @param tokens the initial number of tokens in the place
     */
    public Place(int tokens) throws PlaceExceptions {
        this.id = NextID++;
        if (tokens < 0) {
            throw new PlaceExceptions("Number of tokens cannot be negative");
        }
        this.tokens = tokens;
    }

    /**
     * Getter for the place ID
     * @return the unique identifier of the place
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the number of tokens
     * @return the current number of tokens in the place
     */
    public int getTokens() {
        return tokens;
    }

    /**
     * String representation of the place
     * @return a string describing the place
     */
    @Override
    public String toString() {
        return "Place [id=" + id + ", tokens=" + tokens + "]";
    }

    /**
     * Method to add tokens to the place
     * @param count the number of tokens to be added
     */
    public void addTokens(int count) throws PlaceExceptions {
        if (count < 0) {
            throw new PlaceExceptions("Number of tokens to add cannot be negative");
        }
        tokens += count;
    }

    /**
     * Method to remove tokens from the place
     * @param count the number of tokens to be removed
     */
    public void removeTokens(int count) throws PlaceExceptions {
        if (count < 0) {
            throw new PlaceExceptions("Number of tokens to remove cannot be negative");
        }
        if (tokens >= count) {
            tokens -= count;
        } else {
            tokens = 0;
        }
    }

    /**
     * Equals method to compare two places 
     * @param obj the object to compare with
     * @return true if the places are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Place) {
            Place other = (Place) obj;
            return this.id == other.id;
        }
        return false;
    }

    /**
     * HashCode method consistent with equals
     * @return the hash code of the place
     */
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    public static void main(String[] args) {
        Place p = new Place(5);
        System.out.println(p);
        p.addTokens(3);
        System.out.println("After adding tokens: " + p);
        p.removeTokens(4);
        System.out.println("After removing tokens: " + p);
    }

}
