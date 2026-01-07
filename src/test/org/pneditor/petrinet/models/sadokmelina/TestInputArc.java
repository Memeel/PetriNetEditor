package org.pneditor.petrinet.models.sadokmelina;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestInputArc {

    private Place p;
    private Transition t;  

    @BeforeEach
    public void setUp() {
        // Common setup for tests
        p = new Place(5);
        t = new Transition();
    }

    // Tests for creation of InputArc
    @Test
    public void testCreateInputArc() throws ArcExceptions {
        // ---------- CAE0 ----------
        // Creating a valid InputArc
        InputArc arc = new InputArc(p, t, 2);

        // Verifying attributes
        assertNotNull(arc);
        assertEquals(p, arc.getSource());
        assertEquals(t, arc.getDestination());
        assertEquals(2, arc.getWeight());
    }

    @Test
    public void testCreateInputArcNegativeCases(){
        // ---------- CAE1 ----------
        Exception exception = assertThrows(ArcExceptions.class, () -> {
            new InputArc(p, t, -1);
        });
        assertEquals("Weight must be positive", exception.getMessage());
    }

    @Test
    public void testCreateInputArcZeroCases() {
        // ---------- CAE2 ----------
        Exception exception = assertThrows(ArcExceptions.class, () -> {
            new InputArc(p, t, 0);
        });
        assertEquals("Weight must be strictly positive unless it's a ZeroArc", exception.getMessage());
    }

    @Test
    public void testCreateInputArcNullPlaceCases() {
        // ---------- CAE3 ----------
        Exception exception = assertThrows(ArcExceptions.class, () -> {
            new InputArc(null, t, 1);
        });
        assertEquals("Place is null", exception.getMessage());  
    }

    @Test
    public void testCreateInputArcNullTransitionCases() {
        // ---------- CAE4 ----------
        Exception exception = assertThrows(ArcExceptions.class, () -> {
            new InputArc(p, null, 1);
        });
        assertEquals("Transition is null", exception.getMessage()); 
    }

    @Test
    public void testToString() {
        InputArc arc = new InputArc(p, t, 3);
        String expected = "InputArc [id=" + arc.getId() + ", place=" + p.getId() + ", transition=" + t.getId() + ", weight=" + 3 + "]";
        assertEquals(expected, arc.toString());
    }
    
}
