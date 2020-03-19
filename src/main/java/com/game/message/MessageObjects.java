package com.game.message;

import java.util.ArrayList;
import java.util.List;

public class MessageObjects {

    private final List<Object> objects;
    private Boolean gameOver = false;

    public List<Object> getObjects() {
        return objects;
    }

    public MessageObjects(List<Object> objects) {
        this.objects = objects;
    }

    public MessageObjects(){
        this.objects = new ArrayList<>();
    }


    public MessageObjects(Object object){
        List<Object> objects = new ArrayList<>();
        objects.add(object);
        this.objects = objects;
    }

    public void setMessageObjects(Object object){
        this.objects.add(object);
    }
}
