package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.databasesubsystem.FDdb;
import edu.wpi.cs3733.C23.teamD.entities.Move;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

public class MoveTable implements Initializable {
  @FXML private TableView<Move> moveTable;
  @FXML private TableColumn<Move, String> moveNodeID;
  @FXML private TableColumn<Move, Date> moveDate;
  @FXML private TableColumn<Move, String> moveLongName;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    tablehandling();
  }

  public void tablehandling() {
    ObservableList<Move> moveList =
        FXCollections.observableArrayList(FDdb.getInstance().getAllMoves());

    moveNodeID.setCellValueFactory(new PropertyValueFactory<Move, String>("nodeID"));
    moveDate.setCellValueFactory(new PropertyValueFactory<Move, Date>("moveDate"));
    moveLongName.setCellValueFactory(new PropertyValueFactory<Move, String>("longName"));
    moveTable.setItems(moveList);

    moveTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    moveTable.getColumns().stream()
        .forEach(
            (column) -> {
              double size = moveTable.getColumns().size();
              Text serviceTableValue = new Text(column.getText());
              Object cellData;
              double currentMax = moveTable.getLayoutBounds().getWidth();
              for (int i = 0; i < moveTable.getItems().size(); i++) {
                cellData = column.getCellData(i);
                if (cellData != null) {
                  serviceTableValue = new Text(cellData.toString());
                  double width = serviceTableValue.getLayoutBounds().getWidth();
                  if (width > currentMax) {
                    currentMax = width;
                  }
                }
              }
              if (moveTable.getMaxWidth() / size > currentMax)
                column.setMinWidth(moveTable.getMaxWidth() / size);
              column.setMinWidth(currentMax);
            });
  }
}
