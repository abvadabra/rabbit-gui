package ru.redenergy.gui.show;

import java.util.List;

import ru.redenergy.gui.layout.LayoutComponentWrapper;

/**
 * Represents show which has been generated from layout
 *
 */
public class LayoutShow extends Show {

    /**
     * Contains components which has been parsed from layout
     */
    private final List<LayoutComponentWrapper> layoutComponents;

    public LayoutShow(List<LayoutComponentWrapper> components) {
        super();
        this.layoutComponents = components;
    }
    
    public List<LayoutComponentWrapper> getLayoutComponents(){
        return layoutComponents;
    }
    
    @Override
    public void setup(){
        super.setup();
        for(LayoutComponentWrapper com : layoutComponents){
            try{
                registerComponent(com.create(this));
            } catch(Exception ex){
                ex.printStackTrace();
                throw new RuntimeException("Error while trying to create " + com.getType().getName() + " component");
            }
        }
    }
}
