package org.pneditor.petrinet.models.sadokmelina;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestOutputArc {

    private Place p;
    private Transition t;

    @BeforeEach
    public void setUp() {
        p = new Place(5);
        t = new Transition();
    }

    // Tests for creation of OutputArc 
    @Test
    public void testCreateOutputArc() {
        // ---------- CAE0 ----------
        // Creation of a valid OutputArc
        OutputArc arc = new OutputArc(t, p, 2);
        
        // Assertions
        assertNotNull(arc);
        assertEquals(t, arc.getSource());
        assertEquals(p, arc.getDestination());
        assertEquals(2, arc.getWeight());
    }

    @Test
    public void testCreateOutputArcNegativeCases() {
        // ---------- CAI1 ----------
        Exception exception = assertThrows(ArcExceptions.class, () -> {
            new OutputArc(t, p, -1);
        });
        assertEquals("Weight must be positive", exception.getMessage());
    }

    @Test
    public void testCreateOutputArcNullTransition() {
        // ---------- CAI2 ----------
        Exception exception = assertThrows(ArcExceptions.class, () -> {
            new OutputArc(null, p, 1);
        });
        assertEquals("Transition is null", exception.getMessage());

    }

    @Test
    public void testCreateOutputArcNullPlace() {
        // ---------- CAI3 ----------
        Exception exception = assertThrows(ArcExceptions.class, () -> {
            new OutputArc(t, null, 1);
        });
        assertEquals("Place is null", exception.getMessage());
    }

    @Test
    public void testToString() {
        OutputArc arc = new OutputArc(t, p, 4);
        String expected = "OutputArc [id=" + arc.getId() + ", transition=" + t.getId() + ", place=" + p.getId() + ", weight=" + 4 + "]";
        assertEquals(expected, arc.toString());
    }
}
