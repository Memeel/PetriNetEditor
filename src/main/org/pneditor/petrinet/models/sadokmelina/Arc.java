package org.pneditor.petrinet.models.sadokmelina;
public interface Arc {

    public int getId();
    public void fire();
    public int getWeight();
    public boolean isActive();
    public void setWeight(int weight);

    
} 
