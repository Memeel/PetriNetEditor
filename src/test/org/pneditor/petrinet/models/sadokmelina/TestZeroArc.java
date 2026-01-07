package org.pneditor.petrinet.models.sadokmelina;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;

public class TestZeroArc {

    private Place pTokens;
    private Place pNoTokens;
    private Transition t;

    @BeforeEach
    public void setUp() {
        pTokens = new Place(5); // Place with tokens
        pNoTokens = new Place(0); // Place without tokens
        t = new Transition();
    }

    // Tests for creation of ZeroArc
    @Test
    public void testCreateZeroArcFromPlaceWithTokens() {
        // ---------- CAZ ----------
        // Create ZeroArc from Place with tokens
        ZeroArc za = new ZeroArc(pTokens, t);

        // Assertions
        assertNotNull(za);
        assertEquals(pTokens, za.getSource());
        assertEquals(t, za.getDestination());
        assertEquals(0, za.getWeight());

        // Ensure that the transition is fireable only if the place has zero tokens
        assertFalse(za.isActive());
    }

    @Test
    public void testCreateZeroArcFromPlaceWithoutTokens() {
        // ---------- CAZ ----------
        // Create ZeroArc from Place without tokens
        ZeroArc za = new ZeroArc(pNoTokens, t);

        // Assertions
        assertNotNull(za);
        assertEquals(pNoTokens, za.getSource());
        assertEquals(t, za.getDestination());
        assertEquals(0, za.getWeight());

        // Ensure that the transition is fireable since the place has zero tokens
        assertTrue(za.isActive());
    }
        
    @Test
    public void testToString() {
        // No tokens in place
        ZeroArc za = new ZeroArc(pNoTokens, t);
        String expected = "ZeroArc [id=" + za.getId() + ", Place= " + pNoTokens.getId() + ", Transition= " + t.getId() + "]";
        assertEquals(expected, za.toString());

        // Tokens in place
        ZeroArc za2 = new ZeroArc(pTokens, t);
        String expected2 = "ZeroArc [id=" + za2.getId() + ", Place= " + pTokens.getId() + ", Transition= " + t.getId() + "]";
        assertEquals(expected2, za2.toString());
    }
}
