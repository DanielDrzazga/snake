package snakeapp;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

/**
 * Created by Daniel Drzazga on 07.04.2020.
 */
class Game {

    private int windowWidth;
    private int windowHeight;
    private int size;
    private int margin;

    private Snake snake;
    private Food food;

    private boolean isOver;

    private Random random;

    Game(int windowWidth, int windowHeight, int size, int margin) {
        this.windowWidth = windowWidth - margin;
        this.windowHeight = windowHeight - margin;
        this.random = new Random(159);
        this.size = size;
        this.margin = margin;
        this.snake = new Snake(new Point2D(margin + size, margin + size));
        this.food = new Food(getRandomPosition());
    }

    private Point2D getRandomPosition() {
        return new Point2D((random.nextInt((windowWidth - margin) / size) * size) + margin,
                (random.nextInt((windowHeight - margin) / size) * size) + margin);
    }

    Boolean isOver() {
        return this.isOver;
    }

    Snake getSnake() {
        return this.snake;
    }

    Food getFood() {
        return this.food;
    }

    void setFood() {
        Food newFood;
        do {
            newFood = new Food(getRandomPosition());
        } while (foodCollision(newFood.getPosition()));
        this.food = newFood;
    }

    private boolean foodCollision(Point2D foodPosition) {
        return isPointsEquals(getSnake().getBody().get(0), foodPosition);
    }

    private boolean isPointsEquals(Point2D point1, Point2D point2) {
        return point1.getX() == point2.getX() && point1.getY() == point2.getY();
    }

    private void setDirection(Direction direction) {
        this.snake.setDirection(direction);
    }

    void move(Scene scene) {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    if (snake.getDirection() != Direction.DOWN)
                        setDirection(Direction.UP);
                    break;
                case DOWN:
                    if (snake.getDirection() != Direction.UP)
                        setDirection(Direction.DOWN);
                    break;
                case LEFT:
                    if (snake.getDirection() != Direction.RIGHT)
                        setDirection(Direction.LEFT);
                    break;
                case RIGHT:
                    if (snake.getDirection() != Direction.LEFT)
                        setDirection(Direction.RIGHT);
                    break;
            }
        });
    }

    void drawingSnake(Pane root) {
        getSnake().move(size);
        for (int i = 0; i < getSnake().getBody().size(); i++) {
            Rectangle rectangle = new Rectangle();
            if (i == 0) {
                rectangle.setFill(new Color(1.0, 0.99, 0.19, 0.3));
            } else {
                rectangle.setFill(new Color(0.69, 0.36, 0.18, 1));
            }
            rectangle.setX(getSnake().getBody().get(i).getX() + 1);
            rectangle.setY(getSnake().getBody().get(i).getY() + 1);
            rectangle.setHeight(size - 2);
            rectangle.setWidth(size - 2);
            root.getChildren().add(rectangle);
        }


    }

    void drawingFood(Pane root) {
        Rectangle food = new Rectangle();
        food.setX(getFood().getPosition().getX() + 1);
        food.setY(getFood().getPosition().getY() + 1);
        food.setHeight(size - 2);
        food.setWidth(size - 2);

        food.setFill(new Color(0.67, 0.22, 0.31, 1));
        root.getChildren().add(food);
    }

    boolean snakeOutOfRange() {
        if (getSnake().getBody().get(0).getX() < this.margin || getSnake().getBody().get(0).getY() < this.margin ||
                getSnake().getBody().get(0).getX() > windowWidth - size || getSnake().getBody().get(0).getY() > windowHeight - size) {
            this.isOver = true;
            return true;
        }
        return false;
    }

    boolean snakeCollision() {
        for (int i = 1; i <= getSnake().getBody().size() - 1; i++) {
            if (isPointsEquals(getSnake().getBody().get(0), getSnake().getBody().get(i))) {
                this.isOver = true;
                return true;
            }
        }
        return false;
    }
}
