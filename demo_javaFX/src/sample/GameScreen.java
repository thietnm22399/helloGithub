package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.GameContent.Frog;
import sample.GameContent.Pipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScreen extends Application {

    Pane group = new Pane();
    Frog frog;
    List<Pipe> pipeListUp = new ArrayList();
    List<Pipe> pipeListDown = new ArrayList();
    Scene gameScene;
    int now = 0;
    int updown = 1; // UP is 1, down is 0
    int point = 0;
    Label pointLabel = new Label();
    boolean flag = true;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);

    private void updateAlert(int score) {
        alert.setTitle("Happy Frog");
        alert.setHeaderText("Game Over");
        String s = "Your score is " + score;
        if (score < 10) {
            s += ". You have earned a bronze medal";
        } else if (score >= 10 && score < 20) {
            s += ". You have earned a sliver medal";
        } else if (score >= 20) {
            s += ". You have earned a gold medal";
        }
        alert.setContentText(s);
        alert.show();
    }


    Thread frogTimer = new Thread() {
        @Override
        public void run() {
            while (flag) {
                frog.getImageView().setTranslateY(frog.getImageView().getTranslateY() + 5);
                for (int i = 0; i < pipeListDown.size(); i++) {
                    pipeListDown.get(i).getImageView().setTranslateX((pipeListDown.get(i).
                            getImageView().getTranslateX() - 5 + 1400) % 1400);
                    pipeListUp.get(i).getImageView().setTranslateX((pipeListUp.get(i).
                            getImageView().getTranslateX() - 5 + 1400) % 1400);
                }

                if (updown == 1) {
                    // System.out.println(frog.getImageView().intersects(pipeListUp.get(now).getImageView().getBoundsInParent()));

                    if (frog.getImageView().getBoundsInParent().intersects(pipeListUp.get(now).
                            getImageView().getBoundsInParent())) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                updateAlert(point);
                            }
                        });
                        flag = false;
                    } else if (frog.getImageView().getTranslateX() > pipeListUp.get(now).
                            getImageView().getTranslateX() + 75) {
                        point++;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                pointLabel.setText(Integer.toString(point));
                            }
                        });
                        updown = 0;
                        System.out.println(point);
                    }
                } else {
                    if (frog.getImageView().getBoundsInParent().intersects(pipeListDown.get(now).
                            getImageView().getBoundsInParent())) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                updateAlert(point);
                            }
                        });
                        flag = false;
                    } else if (frog.getImageView().getTranslateX() > pipeListUp.get(now).
                            getImageView().getTranslateX() + 75) {
                        point++;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                pointLabel.setText(Integer.toString(point));
                            }
                        });
                        updown = 1;
                        now = (now + 1 + 7) % 7;
                        System.out.println(point);
                    }
                }

                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void setupUIComposment(Stage primaryStage) throws Exception {

        group = new Pane();

        pointLabel.setTranslateX(100);
        pointLabel.setTranslateY(0);
        group.getChildren().add(pointLabel);

        frog = new Frog("frog.png");
        frog.getImageView().setFitWidth(50);
        frog.getImageView().setFitHeight(50);
        frog.addToPane(group, 100, 250);

        for (int i = 0; i < 7; i++) {
            Pipe x = new Pipe("pipe" + new Random().nextInt(3) + ".png");
            x.getImageView().setRotate(x.getImageView().getRotate() + 180);
            x.addToPane(group, 200 + 200 * i, 100);
            x.getImageView().setFitWidth(50);
            pipeListUp.add(x);

            x = new Pipe("pipe" + new Random().nextInt(3) + ".png");
            x.addToPane(group, 300 + 200 * i,
                    600 - x.getImageView().boundsInParentProperty().get().getHeight());
            x.getImageView().setFitWidth(50);
            pipeListDown.add(x);
        }


    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        setupUIComposment(primaryStage);
        primaryStage.setTitle("Flappy Frog");
        gameScene = new Scene(group, 1500, 700);
        primaryStage.setScene(gameScene);

        gameScene.setOnKeyPressed(event1 -> {
            switch (event1.getCode()) {
                case UP:
                    frog.moveUp();
                    break;
            }
        });

        gameScene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    try {
                        frogTimer.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        });




        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                Platform.exit();
                System.exit(0);
            }
        });

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
