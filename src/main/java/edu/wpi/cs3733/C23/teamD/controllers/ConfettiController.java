package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.App;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class ConfettiController {
  static Stage currentstage = App.getPrimaryStage();

  public static void makeConfetti(int toastDelay, int fadeInDelay, int fadeOutDelay)
      throws IOException {

    Stage confettiStage = new Stage();
    confettiStage.initOwner(currentstage);
    confettiStage.setResizable(false);
    confettiStage.initStyle(StageStyle.TRANSPARENT);

    InputStream confetti =
        new FileInputStream(
            "src/main/resources/edu/wpi/cs3733/C23/teamD/views/images/confetti.gif");
    Image image = new Image(confetti);
    // Creating the image view
    ImageView imageView = new ImageView();
    // Setting image to the image view
    imageView.setImage(image);
    // Setting the image view parameters
    imageView.setX(75);
    imageView.setY(100);
    imageView.setFitWidth(1200);
    imageView.setPreserveRatio(true);

    StackPane root = new StackPane(imageView);
    root.setStyle(
        "-fx-background-radius: 20; -fx-background-color: rgba(0, 0, 0, 0); -fx-padding: 20px;");

    Scene scene = new Scene(root);
    scene.setFill(Color.TRANSPARENT);
    confettiStage.setScene(scene);
    confettiStage.show();

    Timeline fadeInTimeline = new Timeline();
    KeyFrame fadeInKey1 =
        new KeyFrame(
            Duration.millis(fadeInDelay),
            new KeyValue(confettiStage.getScene().getRoot().opacityProperty(), 1));
    fadeInTimeline.getKeyFrames().add(fadeInKey1);
    fadeInTimeline.setOnFinished(
        (ae) -> {
          new Thread(
                  () -> {
                    try {
                      Thread.sleep(toastDelay);
                    } catch (InterruptedException e) {
                      e.printStackTrace();
                    }
                    Timeline fadeOutTimeline = new Timeline();
                    KeyFrame fadeOutKey1 =
                        new KeyFrame(
                            Duration.millis(fadeOutDelay),
                            new KeyValue(confettiStage.getScene().getRoot().opacityProperty(), 0));
                    fadeOutTimeline.getKeyFrames().add(fadeOutKey1);
                    fadeOutTimeline.setOnFinished((aeb) -> confettiStage.close());
                    fadeOutTimeline.play();
                  })
              .start();
        });
    fadeInTimeline.play();
  }
}
