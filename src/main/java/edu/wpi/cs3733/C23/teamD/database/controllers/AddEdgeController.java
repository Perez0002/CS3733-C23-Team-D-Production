package edu.wpi.cs3733.C23.teamD.database.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.Edge;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.NodeComboBoxController;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.ToastController;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import lombok.Setter;

public class AddEdgeController implements AddFormController<Edge> {

  @Setter DatabaseController databaseController;

  @FXML private Parent fromNodeBox;
  @FXML private NodeComboBoxController fromNodeBoxController;
  @FXML private Parent toNodeBox;
  @FXML private NodeComboBoxController toNodeBoxController;
  @FXML private MFXButton submitButton;
  @FXML private Label titleLabel;
  @FXML private Text errorText;
  @FXML private MFXButton deleteButton;

  @FXML
  public void initialize() {
    deleteButton.setOnMouseClicked(event -> deleteRow());
  }

  private void deleteRow() {
    databaseController.delete();
    ToastController.makeText("The edge has been deleted!", 1500, 50, 100);
    dataToChange(null);
  }

  private Edge currentEdge;

  @FXML
  public void submit() {
    if (checkFields()) {
      if (currentEdge == null) {
        FDdb.getInstance()
            .saveEdge(new Edge(fromNodeBoxController.getNode(), toNodeBoxController.getNode()));
        databaseController.refresh();
        dataToChange(null);
        ToastController.makeText("Your edge has been added!", 1500, 50, 100);
      } else {
        Edge newEdge = new Edge();
        newEdge.setFromNode(fromNodeBoxController.getNode());
        newEdge.setToNode(toNodeBoxController.getNode());
        newEdge.genEdgeID();
        FDdb.getInstance().updateEdgePK(currentEdge, newEdge);
        databaseController.refresh();
        clearFields();
        ToastController.makeText("Your edge has been changed!", 1500, 50, 100);
      }
    } else {
      errorText.setVisible(true);
    }
  }

  private boolean checkFields() {
    return !(fromNodeBoxController.getNodeID() == null || toNodeBoxController.getNodeID() == null);
  }

  @FXML
  public void clearFields() {
    errorText.setVisible(false);
    toNodeBoxController.clearForm();
    fromNodeBoxController.clearForm();
  }

  @Override
  public void dataToChange(Edge edge) {
    currentEdge = edge;
    if (edge == null) {
      submitButton.setText("Add Edge");
      titleLabel.setText("Add an Edge");
      clearFields();
      deleteButton.setDisable(true);
    } else {
      deleteButton.setDisable(false);
      submitButton.setText("Submit Changes");
      titleLabel.setText("Change an Edge");
      toNodeBoxController.setNodeID(edge.getToNodeID());
      fromNodeBoxController.setNodeID(edge.getFromNodeID());
    }
  }
}
