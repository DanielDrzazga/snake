package snakeapp;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * Created by Daniel Drzazga on 07.04.2020.
 */
public class SnakeApp extends Application {

    private int windowWidth = 500;
    private int windowHeight = 500;
    private int margin = 55;
    private int size = 15;

    private double speed = 0.12;
    private double timeNextMove = 0;
    private int score = 0;

    private Pane root = new Pane();
    private Game game;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Scene scene = new Scene(createdMenuContent());
        scene.setFill(new Color(0.19, 0.20, 0.21, 1));

        game = new Game(windowWidth, windowHeight, size, margin);
        game.move(scene);


        primaryStage.setTitle("Snake - Created by Daniel Drzazga");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    private Parent createdMenuContent() {
        root.setPrefSize(windowWidth, windowHeight);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                root.getChildren().removeAll();
                if (game.isOver()){
                    this.stop();
                }
                update();
            }
        }.start();

        return root;
    }

    private void update() {
        timeNextMove += speed;

        if (timeNextMove > 2) {
            root.getChildren().clear();
            game.drawingSnake(root);

            Line lineUp = new Line(margin, margin, root.getWidth() - margin, margin);
            Line lineLeft = new Line(margin, root.getHeight() - margin, margin, margin);
            Line lineRight = new Line(root.getWidth() - margin, margin, root.getWidth() - margin, root.getHeight() - margin);
            Line lineDown = new Line(margin, root.getHeight() - margin, root.getWidth() - margin, root.getHeight() - margin);

            lineUp.setStrokeWidth(3);
            lineLeft.setStrokeWidth(3);
            lineRight.setStrokeWidth(3);
            lineDown.setStrokeWidth(3);

            lineUp.setStroke(new Color(0.80, 0.29, 0.25, 1));
            lineLeft.setStroke(new Color(0.80, 0.29, 0.25, 1));
            lineRight.setStroke(new Color(0.80, 0.29, 0.25, 1));
            lineDown.setStroke(new Color(0.80, 0.29, 0.25, 1));

            root.getChildren().add(lineUp);
            root.getChildren().add(lineLeft);
            root.getChildren().add(lineRight);
            root.getChildren().add(lineDown);



            Text scoreString = new Text("Score: " + score);
            scoreString.setFont(Font.font("Verdana", FontWeight.BOLD, 25));
            scoreString.setX(margin);
            scoreString.setY(margin - 5);
            scoreString.setFill(new Color(0.80, 0.10, 0.10, 1));
            root.getChildren().add(scoreString);


            if (game.snakeCollision() || game.snakeOutOfRange()) {
                timeNextMove = 0;
                speed = 0;
                Text t = new Text("Game Over");
                t.setFont(Font.font("Verdana", FontWeight.BOLD, 55));
                t.setX(75);
                t.setY(250);
                Text ts = new Text("Score: " + score);
                ts.setFont(Font.font("Verdana", FontWeight.BOLD, 60));
                ts.setX(100);
                ts.setY(330);
                root.getChildren().add(t);
                root.getChildren().add(ts);
            }

            if (game.getFood().getPosition().getX() == game.getSnake().getBody().get(0).getX() && game.getFood().getPosition().getY() == game.getSnake().getBody().get(0).getY()) {
                speed += 0.02;
                score += 1;
                game.setFood();
                game.getSnake().grow();
            }

            game.drawingFood(root);
        }

        if (timeNextMove > 2) {
            timeNextMove = 0;
        }
    }


}
