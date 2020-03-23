package com.game.model;

public enum Direction {
    UP("UP"),
    DOWN("DOWN"),
    LEFT("LEFT"),
    RIGHT("RIGHT");

    private String typeValue;

    Direction(String direction) {
        typeValue = direction;
    }

    public static Direction getType(String pType) {
        return Direction.valueOf(pType);
    }
}
