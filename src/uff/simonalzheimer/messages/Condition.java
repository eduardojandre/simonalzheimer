package uff.simonalzheimer.messages;


import java.io.Serializable;

public class Condition<X,Y> implements Serializable {

    private X key;
    private Y value;
    private boolean notValue = false;

    public Condition(X key, Y value, boolean notValue) {
        this.key = key;
        this.value = value;
        this.notValue = notValue;
    }

    public boolean getNotValueBool() {
        return notValue;
    }

    public void setNotValue(boolean notValue) {
        this.notValue = notValue;
    }

    public X getKey() {
        return key;
    }

    public void setKey(X key) {
        this.key = key;
    }

    public Y getValue() {
        return value;
    }

    public void setValue(Y value) {
        this.value = value;
    }
}
