package org.pneditor.petrinet.models.sadokmelina;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestResetArc {

    private Place pTokens;
    private Place pNoTokens;
    private Transition t;

    @BeforeEach
    public void setUp() {
        pTokens = new Place(1);
        pNoTokens = new Place(0);
        t = new Transition();
    }

    // Tests for creation of ResetArc
    @Test
    public void testCreateResetArcTokens() {
        // ---------- CAR ----------
        // Creation of a valid ResetArc
        ResetArc arc = new ResetArc(pTokens, t);

        // Assertions
        assertNotNull(arc);
        assertEquals(pTokens, arc.getSource());
        assertEquals(t, arc.getDestination());
        assertEquals(Integer.MAX_VALUE, arc.getWeight());

        // Ensure that ResetArc is active when the place has tokens
        assertTrue(arc.isActive());
    }

    @Test
    public void testCreateResetArcNoTokens() {
        // ---------- CAR ----------
        // Creation of a valid ResetArc
        ResetArc arc = new ResetArc(pNoTokens, t);

        // Assertions
        assertNotNull(arc);
        assertEquals(pNoTokens, arc.getSource());
        assertEquals(t, arc.getDestination());
        assertEquals(Integer.MAX_VALUE, arc.getWeight());

        // Ensure that ResetArc is not active when the place has no tokens
        assertFalse(arc.isActive());
    }

    @Test
    public void testToString() {
        ResetArc arc = new ResetArc(pTokens, t);
        String expected = "ResetArc [id=" + arc.getId() + ", source=Place" + pTokens.getId() + ", destination=Transition" + t.getId() + "]";
        assertEquals(expected, arc.toString());
    }
}
