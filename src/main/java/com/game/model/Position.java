package com.game.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;


public class Position {

    private Double x ;
    private Double y;

    public Position(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    public Position(int x, int y) {
        this.x = Double.valueOf(x);
        this.y = Double.valueOf(y);
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }
}
