package ru.redenergy.gui.layout.argument;

/**
 * Represents component argument parsed from json layout
 */
public class LayoutArgument<T> implements ILayoutArgument{
    
    private String fieldName;
    private T value;
    
    public LayoutArgument(String fieldname, T value){
        this.fieldName = fieldname;
        this.value = value;
    }

    @Override
    public String fieldName() {
        return fieldName;
    }
    
    public T get(){
        return value;
    }
}
