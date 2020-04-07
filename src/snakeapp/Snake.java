package snakeapp;


import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel Drzazga on 07.04.2020.
 */
class Snake {

    private Direction direction = Direction.RIGHT;
    private List<Point2D> body;

    Snake(Point2D head) {
        body = new ArrayList<>();
        this.body.add(head);
        this.body.add(new Point2D(head.getX(),head.getY()-1.0));
    }

    List<Point2D> getBody() {
        return body;
    }

    void grow(){
        body.add(getTailPosition());
    }

    private Point2D getTailPosition() {
        return body.get(body.size()-1);
    }

    void setDirection(Direction direction) {
        this.direction = direction;
    }

    Direction getDirection() {
        return direction;
    }

    void move(int size) {
        for (int i = body.size() - 1; i > 0; i--) {
            body.set(i, body.get(i - 1));
        }
        body.set(0,new Point2D(body.get(0).getX() + (direction.vector.getX() * size), body.get(0).getY() + (direction.vector.getY()*size) ));
    }
}
