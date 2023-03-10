package edu.wpi.cs3733.C23.teamD.database.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.Node;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.ToastController;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.time.ZoneId;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import lombok.Setter;

public class AddNodeController implements AddFormController<Node> {
  @Setter private DatabaseController databaseController;

  @FXML private MFXTextField buildingTextField;

  @FXML private Text errorText;

  @FXML private MFXTextField floorTextField;

  @FXML private MFXButton submitButton;

  @FXML private Label titleLabel;

  @FXML private MFXTextField xCoordTextField;

  @FXML private MFXTextField yCoordTextField;

  @FXML private MFXButton deleteButton;

  @FXML
  public void initialize() {
    deleteButton.setOnMouseClicked(event -> deleteRow());
  }

  private void deleteRow() {
    databaseController.delete();
    ToastController.makeText("The node has been deleted!", 1500, 50, 100);
    dataToChange(null);
  }

  private Node currentNode;

  private boolean checkFields() {
    return !(xCoordTextField.getText().isEmpty()
        || yCoordTextField.getText().isEmpty()
        || buildingTextField.getText().isEmpty()
        || floorTextField.getText().isEmpty());
  }

  @FXML
  void clearFields() {
    errorText.setVisible(false);
    yCoordTextField.clear();
    xCoordTextField.clear();
    buildingTextField.clear();
    floorTextField.clear();
  }

  @FXML
  void submit() {
    if (checkFields()) {
      if (currentNode == null) {
        errorText.setVisible(false);
        ZoneId defaultZoneId = ZoneId.systemDefault();
        FDdb.getInstance()
            .saveNode(
                new Node(
                    Integer.parseInt(xCoordTextField.getText()),
                    Integer.parseInt(yCoordTextField.getText()),
                    floorTextField.getText(),
                    buildingTextField.getText()));
        ToastController.makeText("Your node has been added!", 1500, 50, 100);
        databaseController.refresh();
        dataToChange(null);
      } else {
        currentNode.setBuilding(buildingTextField.getText());
        currentNode.setFloor(floorTextField.getText());
        currentNode.setYcoord(Integer.parseInt(yCoordTextField.getText()));
        currentNode.setXcoord(Integer.parseInt(xCoordTextField.getText()));

        ToastController.makeText("Your node has been changed!", 1500, 50, 100);

        FDdb.getInstance().updateNodePK(currentNode);
        databaseController.refresh();
        dataToChange(null);
      }
    } else {
      errorText.setVisible(true);
    }
  }

  @Override
  public void dataToChange(Node node) {
    currentNode = node;
    if (node == null) {
      submitButton.setText("Add Node");
      titleLabel.setText("Add a Node");
      clearFields();
      deleteButton.setDisable(true);
    } else {
      deleteButton.setDisable(false);
      submitButton.setText("Submit Changes");
      titleLabel.setText("Change a Node");
      xCoordTextField.setText(Integer.toString(node.getXcoord()));
      yCoordTextField.setText(Integer.toString(node.getYcoord()));
      buildingTextField.setText(node.getBuilding());
      floorTextField.setText(node.getFloor());
    }
  }
}
