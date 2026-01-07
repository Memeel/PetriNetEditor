package org.pneditor.petrinet.models.sadokmelina;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestPetriNetwork {

    private PetriNetwork net;

    @BeforeEach
    public void setUp() {
        net = new PetriNetwork();
    }

    // Tests for Creation and Assembly of Petri Network
    // CR
    @Test
    public void testCreation() {
        assertNotNull(net);
        assertNotNull(net.getPlaces());
        assertNotNull(net.getTransitions());
        assertNotNull(net.getArcs());

        assertTrue(net.getPlaces().isEmpty()); // Initially, there should be no places
        assertTrue(net.getTransitions().isEmpty()); // Initially, there should be no transitions
        assertTrue(net.getArcs().isEmpty()); // Initially, there should be no arcs
    }

    @Test
    public void testAddElements() {
        // Creation of elements
        Transition t1 = new Transition();
        Place p1 = new Place(1);
        InputArc arc1 = new InputArc(p1, t1, 1);

        // Direct Addition to the network
        net.addPlace(p1);
        net.addTransition(t1);
        net.addArc(arc1);

        // Check that the elements have been added
        assertEquals(1, net.getPlaces().size());
        assertEquals(1, net.getTransitions().size());
        assertEquals(1, net.getArcs().size());
        assertEquals(1, t1.getInputArcs().size());  // Check that the arc has been added to the transition

        // Check that the elements are the correct ones
        assertTrue(net.getPlaces().contains(p1));
        assertTrue(net.getTransitions().contains(t1));
        assertTrue(net.getArcs().contains(arc1));
        assertTrue(t1.getInputArcs().contains(arc1));
    }

    @Test
    public void testRemoveElements() {
        // Creation of elements
        Transition t1 = new Transition();
        Place p1 = new Place(1);
        InputArc arc1 = new InputArc(p1, t1, 1);

        // Direct Addition to the network
        net.addPlace(p1);
        net.addTransition(t1);
        net.addArc(arc1);

        // Removal from the network
        net.removeArc(arc1);
        net.removePlace(p1);
        net.removeTransition(t1);

        // Check that the elements have been removed
        assertEquals(0, net.getArcs().size());
        assertEquals(0, net.getPlaces().size());
        assertEquals(0, net.getTransitions().size());
        assertEquals(0, t1.getInputArcs().size());  // Check that the removal of the arc from the network also removed it from the transition
    }

    @Test
    public void testAddElementsViaArc() {
        // Creation of elements
        Place p1 = new Place(3);
        Transition t1 = new Transition();
        InputArc arc1 = new InputArc(p1, t1, 3);

        // Initially, the network is empty
        assertEquals(0, net.getPlaces().size());
        assertEquals(0, net.getTransitions().size());
        assertEquals(0, net.getArcs().size());

        // Adding the arc should also add the place and transition to the network
        net.addArc(arc1);
        assertEquals(1, net.getPlaces().size());
        assertEquals(1, net.getTransitions().size());
        assertEquals(1, net.getArcs().size());
        assertEquals(1, t1.getInputArcs().size());  // Check that the arc has been added to the transition
        assertTrue(t1.getInputArcs().contains(arc1));

    }

    // Tests for handling duplicate InputArcs error
    // ------------- CADI -------------
    @Test
    public void testDoublingInputArc() throws ArcExceptions {
        Place p = new Place(5);
        Transition t = new Transition();
        InputArc arc1 = new InputArc(p, t, 2); 
        net.addArc(arc1);
        InputArc arc2 = new InputArc(p, t, 3);
        Exception exception = assertThrows(ArcExceptions.class, () -> {
            net.addArc(arc2);
        });
        assertEquals("This arc already exists in the Petri Network", exception.getMessage());
        assertEquals(1, net.getArcs().size());
    }

    // Tests for handling duplicate OutputArcs error
    // ------------- CADO -------------
    @Test
    public void testDoublingOutputArc() throws ArcExceptions {
        Place p = new Place(5);
        Transition t = new Transition();
        OutputArc arc1 = new OutputArc(t, p, 2);
        net.addArc(arc1);
        OutputArc arc2 = new OutputArc(t, p, 3);
        Exception exception = assertThrows(ArcExceptions.class, () -> {
            net.addArc(arc2);
        });
        assertEquals("This arc already exists in the Petri Network", exception.getMessage());
        assertEquals(1, net.getArcs().size());
    }


    // Activation tests
    // Tests for a successful firing of a transition
    @Test
    public void testSimpleFire() {
        // ---------- RD2 ----------
        // Create a Petri net with one place, one transition, and one input arc
        Place p1 = new Place(5);
        Transition t1 = new Transition();
        InputArc arc1 = new InputArc(p1, t1, 3);

        net.addArc(arc1);

        // Initially, the transition should be fireable
        assertTrue(t1.canFire());

        // Fire the transition
        net.fire(t1);
        // After firing, the place should have 2 tokens left
        assertEquals(2, p1.getTokens());

        // The transition should no longer be fireable
        assertFalse(t1.canFire());
    }

    // Tests for an unsuccessful firing of a transition due to insufficient tokens
    @Test
    public void testUnsuccessfulFire() {
        // ---------- RDE1 ----------
        // Create a Petri net with two places, one transition, one input arc and one output arc
        Place p1 = new Place(2);
        Place p2 = new Place(0);
        Transition t1 = new Transition();
        InputArc arc1 = new InputArc(p1, t1, 3);
        OutputArc arc2 = new OutputArc(t1, p2, 1);

        net.addArc(arc1);
        net.addArc(arc2);

        // Initially, the transition should not be fireable
        assertFalse(t1.canFire());

        // Attempt to fire the transition
        net.fire(t1);
        // After attempting to fire, the place should still have 2 tokens and p2 should have 0 tokens
        assertEquals(2, p1.getTokens());
        assertEquals(0, p2.getTokens());
    }

    // Tests for ZeroArc behavior
    @Test
    public void testZeroArcBehavior() {
        // ---------- RZ ----------
        // Create a Petri net with two places, one transition, one zero arc and one output arc
        Place p1 = new Place(0);
        Place p2 = new Place(0);
        Transition t1 = new Transition();
        ZeroArc arc1 = new ZeroArc(p1, t1);
        OutputArc arc2 = new OutputArc(t1, p2, 1);

        net.addArc(arc1);
        net.addArc(arc2);

        // For a ZeroArc, the transition should be fireable if the place has zero tokens
        assertTrue(t1.canFire());

        // Fire the transition
        net.fire(t1);

        // After firing, p1 should still have 0 tokens and p2 should have 1 token
        assertEquals(0, p1.getTokens());
        assertEquals(1, p2.getTokens());

        // The transition should still be fireable since p1 has 0 tokens
        assertTrue(t1.canFire());
    }

    @Test
    public void testZeroArcNotActive() {
        // ---------- RZE ----------
        // Create a Petri net with two places, one transition, one zero arc and one output arc
        Place p1 = new Place(1);
        Place p2 = new Place(0);
        Transition t1 = new Transition();
        ZeroArc arc1 = new ZeroArc(p1, t1);
        OutputArc arc2 = new OutputArc(t1, p2, 1);

        net.addArc(arc1);
        net.addArc(arc2);

        // The ZeroArc should not be active since the place has tokens
        assertFalse(arc1.isActive());

        // Therefore, the transition should not be fireable
        assertFalse(t1.canFire());
    }

    // Tests for ResetArc behavior
    @Test
    public void testResetArcBehavior() {
        // ---------- RV ----------
        // Create a Petri net with two places, one transition, one reset arc and one output arc
        Place p1 = new Place(5);
        Place p2 = new Place(0);
        Transition t1 = new Transition();
        ResetArc arc1 = new ResetArc(p1, t1);
        OutputArc arc2 = new OutputArc(t1, p2, 1);

        net.addArc(arc1);
        net.addArc(arc2);

        // The transition should be fireable since the place has tokens
        assertTrue(t1.canFire());
        // Fire the transition
        net.fire(t1);

        // After firing, p1 should have 0 tokens (reset) and p2 should have 1 token
        assertEquals(0, p1.getTokens());
        assertEquals(1, p2.getTokens());

        // The transition should not be fireable anymore since p1 has 0 tokens
        assertFalse(t1.canFire());
    }

    @Test
    public void testResetArcNotActive() {
        // ---------- RVE ----------
        // Create a Petri net with two places, one transition, one reset arc and one output arc
        Place p1 = new Place(0);
        Place p2 = new Place(0);
        Transition t1 = new Transition();
        ResetArc arc1 = new ResetArc(p1, t1);
        OutputArc arc2 = new OutputArc(t1, p2, 1);

        net.addArc(arc1);
        net.addArc(arc2);

        // The ResetArc should not be active since the place has zero tokens
        assertFalse(arc1.isActive());

        // Therefore, the transition should not be fireable
        assertFalse(t1.canFire());
    }


    // Tests for multiple firings
    @Test
    public void testMultipleInput() {
        // ---------- RM1 ----------
        // Create a PetriNet with two places, one transition, and two input arcs
        Place p1 = new Place(1);
        Place p2 = new Place(2);
        Transition t1 = new Transition();
        InputArc arc1 = new InputArc(p1, t1, 1);
        InputArc arc2 = new InputArc(p2, t1, 2);

        net.addArc(arc1);
        net.addArc(arc2);

        // Initially, the transition should be fireable
        assertTrue(t1.canFire());

        // Fire the transition
        net.fire(t1);

        // After firing, both places should have 0 tokens left
        assertEquals(0, p1.getTokens());
        assertEquals(0, p2.getTokens());

        // The transition should no longer be fireable
        assertFalse(t1.canFire());
    }

    @Test
    public void testMultipleOutput() {
        // ---------- RM2 ----------
        // Create a PetriNet with three places, one transition, one input arc, and two output arcs
        Place p1 = new Place(1);
        Place p2 = new Place(0);
        Place p3 = new Place(0);
        Transition t1 = new Transition();
        InputArc arc1 = new InputArc(p1, t1, 1);
        OutputArc arc2 = new OutputArc(t1, p2, 1);
        OutputArc arc3 = new OutputArc(t1, p3, 1);

        net.addArc(arc1);
        net.addArc(arc2);
        net.addArc(arc3);

        // Initially, the transition should be fireable
        assertTrue(t1.canFire());

        // Fire the transition
        net.fire(t1);

        // After firing, p1 should have 0 tokens, p2 and p3 should each have 1 token
        assertEquals(0, p1.getTokens());
        assertEquals(1, p2.getTokens());
        assertEquals(1, p3.getTokens());

        // The transition should no longer be fireable
        assertFalse(t1.canFire());
    }

    @Test 
    public void testMultipleFail() {
        // ---------- RME ----------
        // Create a PetriNet with three places, one transition, two input arcs, and one output arc
        Place p1 = new Place(0);
        Place p2 = new Place(2);
        Place p3 = new Place(0);
        Transition t1 = new Transition();
        ZeroArc arc1 = new ZeroArc(p1, t1);
        InputArc arc2 = new InputArc(p2, t1, 3);
        OutputArc arc3 = new OutputArc(t1, p3, 1);

        net.addArc(arc1);
        net.addArc(arc2);
        net.addArc(arc3);

        // Initially, the transition should not be fireable
        assertFalse(t1.canFire());

        // Attempt to fire the transition
        net.fire(t1);

        // After attempting to fire, both places should still have their original tokens
        assertEquals(0, p1.getTokens());
        assertEquals(2, p2.getTokens());
    }

    @Test
    public void testMultipleInputOutput() {
        // ---------- RMV ----------
        // Create a PetriNet with four places, one transition, two input arcs, and two output arcs
        Place p1 = new Place(1);
        Place p2 = new Place(4);
        Place p3 = new Place(0);
        Place p4 = new Place(0);
        Transition t1 = new Transition();
        InputArc arc1 = new InputArc(p1, t1, 1);
        ResetArc arc2 = new ResetArc(p2, t1);
        OutputArc arc3 = new OutputArc(t1, p3, 1);
        OutputArc arc4 = new OutputArc(t1, p4, 1);

        net.addArc(arc1);
        net.addArc(arc2);
        net.addArc(arc3);
        net.addArc(arc4);

        // Initially, the transition should be fireable
        assertTrue(t1.canFire());

        // Fire the transition
        net.fire(t1);

        // After firing, p1 should have 0 tokens, p2 should have 0 tokens (reset), and p3 and p4 should each have 1 token
        assertEquals(0, p1.getTokens());
        assertEquals(0, p2.getTokens());
        assertEquals(1, p3.getTokens());
        assertEquals(1, p4.getTokens());

        // The transition should no longer be fireable
        assertFalse(t1.canFire());
    }

    // Tests for toString() method
    @Test
    public void testToStringEmptyPetriNetwork() {
        // Start with an empty PetriNetwork
        String result = net.toString();

        // Assertions
        assertTrue(result.contains("PetriNetwork{"));
        assertTrue(result.contains("places=[]"));
        assertTrue(result.contains("transitions=[]"));
        assertTrue(result.contains("arcs=[]"));
        assertTrue(result.contains("}"));
    }

    // Display Tests
    @Test
    public void testToStringCompletePetriNetwork() {
        // Create a PetriNetwork with places, transitions, and arcs
        Place p1 = new Place(5);
        Transition t1 = new Transition();
        InputArc arc1 = new InputArc(p1, t1, 2);
        OutputArc arc2 = new OutputArc(t1, p1, 1);
        
        // Add elements to the PetriNetwork
        net.addArc(arc1);
        net.addArc(arc2);
        
        // Get the string representations of individual components
        String p1String = p1.toString();
        String t1String = t1.toString();       
        String arc1String = arc1.toString();   
        String arc2String = arc2.toString();   

        // Get the string representation of the entire PetriNetwork
        String netString = net.toString();
        
        // Assertions
        assertTrue(netString.contains(p1String));
        assertTrue(netString.contains(t1String));
        assertTrue(netString.contains(arc1String));
        assertTrue(netString.contains(arc2String));
        
        assertTrue(netString.contains("PetriNetwork{"));
        assertTrue(netString.contains("places="));
        assertTrue(netString.contains("transitions="));
        assertTrue(netString.contains("arcs="));
    }
}
