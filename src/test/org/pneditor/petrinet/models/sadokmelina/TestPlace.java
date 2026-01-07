package org.pneditor.petrinet.models.sadokmelina;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestPlace {

    // Tests for the Creation of a Place
    @Test
    public void testCreationNegativeTokens() {
        // ---------- CP1 ----------
        PlaceExceptions exception = assertThrows(PlaceExceptions.class, () -> {
            new Place(-1);
        });
        assertEquals("Number of tokens cannot be negative", exception.getMessage());
    }

    @Test
    public void testCreation() {
        // ---------- CP2 ----------
        Place p1 = new Place(5);
        assertNotNull(p1);
        assertEquals(5, p1.getTokens());
    }

    // Tests for Adding Tokens
    @Test
    public void testAddNegativeTokens() throws PlaceExceptions {
        
        // ---------- CAJ0 ----------
        Place p = new Place(2);
        
        PlaceExceptions exception = assertThrows(PlaceExceptions.class, () -> {
            p.addTokens(-1);
        });
        assertEquals("Number of tokens to add cannot be negative", exception.getMessage());
    }

    @Test
    public void testAddTokens() throws PlaceExceptions {
        // ---------- CAJ1 ----------
        Place p = new Place(2);

        p.addTokens(3);
        assertEquals(5, p.getTokens());
    }

    // Tests for Removing Tokens
    @Test
    public void testRemoveNegativeTokens() throws PlaceExceptions {
        // ---------- CSJ0 ----------
        Place p = new Place(4);
        
        PlaceExceptions exception = assertThrows(PlaceExceptions.class, () -> {
            p.removeTokens(-2);
        });
        assertEquals("Number of tokens to remove cannot be negative", exception.getMessage());
    }

    @Test
    public void testRemoveTokens() throws PlaceExceptions {
        // ---------- CSJ1 ----------
        Place p = new Place(4);

        p.removeTokens(2);
        assertEquals(2, p.getTokens());
    }

    @Test
    public void testRemoveMoreTokensThanAvailable() throws PlaceExceptions {
        // ---------- CSJ2 ----------
        Place p = new Place(2);
        
        p.removeTokens(5); // removing more tokens than available
        assertEquals(0, p.getTokens());
    }

    @Test
    public void testToString() {
        Place p = new Place(3);
        String expected = "Place [id=" + p.getId() + ", tokens=3]";
        assertEquals(expected, p.toString());
    }

}
