package com.rabbit.gui.layout;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.google.gson.internal.UnsafeAllocator;
import com.rabbit.gui.component.IGui;
import com.rabbit.gui.layout.argument.ILayoutArgument;
import com.rabbit.gui.layout.argument.LayoutArgument;
import com.rabbit.gui.layout.argument.LayoutCalculatableArgument;
import com.rabbit.gui.show.IShow;

import sun.reflect.*;

public class LayoutComponentWrapper {
    private Class type;
    private Set<ILayoutArgument> args;
    
    public LayoutComponentWrapper(Class type, Set<ILayoutArgument> args){
        this.type = type;
        this.args = args;
    }
    
    public Class getType(){
        return type;
    }
    
    public Set<ILayoutArgument> getArguments(){
        return args;
    }
    
    public IGui create() throws Exception{
        return create(null);
    }
    
    public IGui create(IShow show) throws Exception{
        IGui com;
        
        com = instantiateType(type);
        for(ILayoutArgument arg : args){
            Object value = null;
            if(arg instanceof LayoutArgument){
                value = ((LayoutArgument) arg).get();
            } else if(arg instanceof LayoutCalculatableArgument && show != null){
                value = ((LayoutCalculatableArgument) arg).get();
            }
            
            FieldUtils.writeField(com, arg.fieldName(), value, true);
        }
        return com;
    }
    
    private static IGui instantiateType(Class type){
        try {
            Constructor constr = type.getDeclaredConstructor();
            if(constr != null) constr.setAccessible(true);
            return (IGui) constr.newInstance();
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException("Can't instantiate " + type.getName() + " with zero-arg constructor");
        }
    }
}
