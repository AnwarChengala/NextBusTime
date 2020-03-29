package project.casestudy.utils;

public enum Direction {

    SOUTH("1"),
    EAST ("2"),
    WEST("3"),
    NORTH("4");

    private final String direction;

    Direction(String direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return direction;
    }
}
