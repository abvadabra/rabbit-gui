package ru.redenergy.gui.component.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.redenergy.gui.api.components.MultipleModel;

public class Row<T> implements MultipleModel<T>{
    
    protected final String name;
    protected final List<T> content;
    
    public Row(String name, T ... content){
        this(name, new ArrayList(Arrays.asList(content)));
    }
    
    public Row(String name, List<T> content){
        this.name = name;
        this.content = content;
    }
    
    public String getName(){
        return name;
    }
    
    public List<T> getContent(){
        return content;
    }
    
    public List<String> getStringContent(){
        List<String> strings = new ArrayList<String>();
        getContent().forEach(element -> strings.add(String.valueOf(element)));
        return strings;
    }

    @Override
    public Row<T> add(T object) {
        this.content.add(object);
        return this;
    }

    @Override
    public Row<T> addAll(T... objects) {
        this.content.addAll(Arrays.asList(objects));
        return this;
    }

    @Override
    public Row<T> remove(T object) {
        this.content.remove(object);
        return this;
    }

    @Override
    public Row<T> clear() {
        this.content.clear();
        return this;
    }
}
