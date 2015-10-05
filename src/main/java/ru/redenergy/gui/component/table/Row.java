package ru.redenergy.gui.component.table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Row {
    
    protected final String name;
    protected final List<Object> content;
    
    public Row(String name, Object ... content){
        this(name, new ArrayList(Arrays.asList(content)));
    }
    
    public Row(String name, List<Object> content){
        this.name = name;
        this.content = content;
    }
    
    public String getName(){
        return name;
    }
    
    public List<Object> getContent(){
        return content;
    }
    
    public List<String> getStringContent(){
        List<String> strings = new ArrayList<String>();
        getContent().forEach(element -> strings.add(String.valueOf(element)));
        return strings;
    }
}
