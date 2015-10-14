package ru.redenergy.gui.layout.argument;

import org.apache.commons.lang3.tuple.Pair;

import net.objecthunter.exp4j.Expression;

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

    public double get(Pair<String, Double> ... args) {
        for(Pair<String, Double> p : args){
            expression.setVariable(p.getKey(), p.getValue());
        }
        return expression.evaluate();
    }

    
    
}
