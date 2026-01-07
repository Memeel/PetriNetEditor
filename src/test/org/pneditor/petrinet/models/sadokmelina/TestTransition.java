package org.pneditor.petrinet.models.sadokmelina;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestTransition {

    private Transition transition;
    private Place pIn;
    private Place pOut;

    // Setup method to initialize common objects
    @BeforeEach
    public void setUp() {
        transition = new Transition();
        pIn = new Place(5);
        pOut = new Place(0);
    }

    // Tests for the Creation of a Transition
    // ---------- CT ----------
    @Test
    public void testCreation() {
        // Create a Transition without any arcs
        assertNotNull(transition);
        assertNotNull(transition.getInputArcs());
        assertNotNull(transition.getOutputArcs());

        // Verify that the input and output arc lists are empty
        assertTrue(transition.getInputArcs().isEmpty());
        assertTrue(transition.getOutputArcs().isEmpty());
    }

    @Test
    public void testAddInputOutputArc() {
        // Create an InputArc and add it to the Transition
        InputArc inputArc = new InputArc(pIn, transition, 3);
        transition.addInputArc(inputArc);

        // Verify that the InputArc is correctly added
        assertEquals(1, transition.getInputArcs().size());
        assertTrue(transition.getInputArcs().contains(inputArc));
    
        // Create an OutputArc and add it to the Transition
        OutputArc outputArc = new OutputArc(transition, pOut, 2);
        transition.addOutputArc(outputArc);

        // Verify that the OutputArc is correctly added
        assertEquals(1, transition.getOutputArcs().size());
        assertTrue(transition.getOutputArcs().contains(outputArc));
    }

    @Test
    public void testRemoveInputOutputArc() {
        // Create and add an InputArc
        InputArc inputArc = new InputArc(pIn, transition, 3);
        transition.addInputArc(inputArc);

        // Remove the InputArc
        transition.removeInputArc(inputArc);

        // Verify that the InputArc is removed
        assertFalse(transition.getInputArcs().contains(inputArc));
    
        // Create and add an OutputArc
        OutputArc outputArc = new OutputArc(transition, pOut, 2);
        transition.addOutputArc(outputArc);

        // Remove the OutputArc
        transition.removeOutputArc(outputArc);
        
        // Verify that the OutputArc is removed
        assertFalse(transition.getOutputArcs().contains(outputArc));
    }

    @Test
    public void testToStringEmptyTransition() {
        Transition t = new Transition();
        String expectedId = String.valueOf(t.getId());
        String result = t.toString();
        
        assertTrue(result.startsWith("Transition ["));
        assertTrue(result.endsWith("]"));
        assertTrue(result.contains("id=" + expectedId));
        assertTrue(result.contains("inputArcs=[]"));
        assertTrue(result.contains("outputArcs=[]"));
    }

    @Test
    public void testToStringCompleteTransition() {
        // Create places and arcs and add them to the transition
        Place p1 = new Place(1);
        Place p2 = new Place(1);
        Place p3 = new Place(1);
        Transition t = new Transition();
        
        InputArc arcIn1 = new InputArc(p1, t, 1);
        InputArc arcIn2 = new InputArc(p2, t, 1);
        OutputArc arcOut1 = new OutputArc(t, p3, 1);
        
        t.addInputArc(arcIn1);
        t.addInputArc(arcIn2);
        t.addOutputArc(arcOut1);
        
        // Get IDs for assertions
        String idT = String.valueOf(t.getId());
        String idIn1 = String.valueOf(arcIn1.getId());
        String idIn2 = String.valueOf(arcIn2.getId());
        String idOut1 = String.valueOf(arcOut1.getId());

        // Generate the toString result
        String result = t.toString();


        // Assertions
        assertTrue(result.startsWith("Transition ["));
        assertTrue(result.endsWith("]"));
        assertTrue(result.contains("id=" + idT));
        
        assertTrue(result.contains("inputArcs="));
        assertTrue(result.contains(idIn1));
        assertTrue(result.contains(idIn2));

        assertTrue(result.contains("outputArcs="));
        assertTrue(result.contains(idOut1));
    }
}

