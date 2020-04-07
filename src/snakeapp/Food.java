package snakeapp;

import javafx.geometry.Point2D;

/**
 * Created by Daniel Drzazga on 07.04.2020.
 */
class Food {

    private Point2D position;

    Food(Point2D position) {
        this.position = position;
    }

    Point2D getPosition() {
        return position;
    }

}
