package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import edu.wpi.cs3733.C23.teamD.App;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class ToastController {
  static Stage currentstage = App.getPrimaryStage();

  public static void makeText(
      String toastMsg, int toastDelay, int fadeInDelay, int fadeOutDelay, int x, int y) {

    Stage toastStage = new Stage();
    toastStage.initOwner(currentstage);
    toastStage.setResizable(false);
    toastStage.initStyle(StageStyle.TRANSPARENT);
    toastStage.setX(x);
    toastStage.setY(y);

    Text text = new Text(toastMsg);
    text.setFont(Font.font("Nunito Sans", 16));
    text.setFill(Color.DARKGREEN);

    StackPane root = new StackPane(text);
    root.setStyle("-fx-background-radius: 20; -fx-background-color: #BBF391; -fx-padding: 18px;");
    root.setOpacity(0);

    Scene scene = new Scene(root);
    scene.setFill(Color.TRANSPARENT);
    toastStage.setScene(scene);
    toastStage.show();

    Timeline fadeInTimeline = new Timeline();
    KeyFrame fadeInKey1 =
        new KeyFrame(
            Duration.millis(fadeInDelay),
            new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 1));
    fadeInTimeline.getKeyFrames().add(fadeInKey1);
    fadeInTimeline.setOnFinished(
        (ae) -> {
          new Thread(
                  () -> {
                    try {
                      Thread.sleep(toastDelay);
                    } catch (InterruptedException e) {
                      // TODO Auto-generated catch block
                      e.printStackTrace();
                    }
                    Timeline fadeOutTimeline = new Timeline();
                    KeyFrame fadeOutKey1 =
                        new KeyFrame(
                            Duration.millis(fadeOutDelay),
                            new KeyValue(toastStage.getScene().getRoot().opacityProperty(), 0));
                    fadeOutTimeline.getKeyFrames().add(fadeOutKey1);
                    fadeOutTimeline.setOnFinished((aeb) -> toastStage.close());
                    fadeOutTimeline.play();
                  })
              .start();
        });
    fadeInTimeline.play();
  }
}
