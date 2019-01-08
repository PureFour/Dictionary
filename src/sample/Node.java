package sample;

import java.io.Serializable;

public class Node implements Serializable
{
    private static final long serialVersionUID = 123L;

    public Object key;
    public Object value;


    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getKey() {
        return key;
    }

    public void setKey(Object key) {
        this.key = key;
    }

    public Node(){}
    // Node constructor
    public Node(Object keyValue, Object dataValue)
    {
        value = dataValue;
        key = keyValue;
    }
    @Override
    public String toString()
    {
        return ("[K: " + this.key + " | D: " + this.value + ']' + ' ');
    }

}