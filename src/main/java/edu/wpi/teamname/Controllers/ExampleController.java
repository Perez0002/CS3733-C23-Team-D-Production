package edu.wpi.teamname.Controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ExampleController {
  @FXML Button ClickButton;

  public void buttonClicked(ActionEvent actionEvent) throws IOException {
    System.out.println("Button was clicked");
  }
}
