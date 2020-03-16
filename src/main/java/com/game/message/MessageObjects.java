package com.game.message;

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
}
