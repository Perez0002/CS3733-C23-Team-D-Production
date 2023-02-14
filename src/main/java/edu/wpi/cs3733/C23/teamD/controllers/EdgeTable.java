package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.databasesubsystem.FDdb;
import edu.wpi.cs3733.C23.teamD.entities.Edge;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class EdgeTable implements Initializable {

  @FXML private TableView<Edge> edgeTable;

  @FXML private TableColumn<Edge, String> edgeID;
  @FXML private TableColumn<Edge, String> startNode;
  @FXML private TableColumn<Edge, String> endNode;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    tablehandling();
  }

  public void tablehandling() {
    ObservableList<Edge> edgeList =
        FXCollections.observableArrayList(FDdb.getInstance().getAllEdges());

    edgeID.setCellValueFactory(new PropertyValueFactory<Edge, String>("edgeID"));
    startNode.setCellValueFactory(new PropertyValueFactory<Edge, String>("fromNodeID"));
    endNode.setCellValueFactory(new PropertyValueFactory<Edge, String>("toNodeID"));
    edgeTable.setItems(edgeList);

    edgeTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    edgeTable.getColumns().stream()
        .forEach(
            (column) -> {
              double size = edgeTable.getColumns().size();
              Text serviceTableValue = new Text(column.getText());
              Object cellData;
              double currentMax = edgeTable.getLayoutBounds().getWidth();
              for (int i = 0; i < edgeTable.getItems().size(); i++) {
                cellData = column.getCellData(i);
                if (cellData != null) {
                  serviceTableValue = new Text(cellData.toString());
                  double width = serviceTableValue.getLayoutBounds().getWidth();
                  if (width > currentMax) {
                    currentMax = width;
                  }
                }
              }
              if (edgeTable.getMaxWidth() / size > currentMax)
                column.setMinWidth(edgeTable.getMaxWidth() / size);
              column.setMinWidth(currentMax);
            });
  }
}
