package ru.redenergy.gui.layout.argument;

import org.apache.commons.lang3.tuple.Pair;

import net.objecthunter.exp4j.Expression;

/**
 * Represents layout argument which needs some argument 
 * For example: width and height of the screen
 */
public class LayoutCalculatableArgument<T> implements ILayoutArgument {

    private String fieldName;
    
    private Expression expression;
    
    public LayoutCalculatableArgument(String fieldName, Expression exp) {
        this.fieldName = fieldName;
        this.expression = exp;
    }
    
    @Override
    public String fieldName() {
        return fieldName;
    }

    public int get(Pair<String, Double> ... args) {
        for(Pair<String, Double> p : args){
            expression.setVariable(p.getKey(), p.getValue());
        }
        return (int)expression.evaluate();
    }

    
    
}
