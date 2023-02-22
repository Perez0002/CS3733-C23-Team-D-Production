package edu.wpi.cs3733.C23.teamD.database.controllers;

import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Setter;

public class MoveTableController extends Application implements Initializable, DatabaseController {
  @FXML private BorderPane MoveTableBorderPane;
  @FXML private TableView<Move> moveTable;
  @FXML private TableColumn<Move, String> moveNodeID;
  @FXML private TableColumn<Move, Date> moveDate;
  @FXML private TableColumn<Move, String> moveLongName;

  @FXML private TableColumn<Move, String> message;
  @Setter private AddFormController addFormController;

  @FXML
  public void openMoveRequest() {
    Navigation.navigate(Screen.MOVES_TABLE);
  }

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {}

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    tablehandling();
  }

  public Node getBox() {
    return MoveTableBorderPane;
  }

  public void setVisible() {
    if (MoveTableBorderPane.isVisible()) {
      MoveTableBorderPane.setVisible(false);
    } else {
      MoveTableBorderPane.setVisible(true);
    }
  }

  public void refresh() {
    ObservableList<Move> moveList = null;
    try {
      moveList = FXCollections.observableArrayList(FDdb.getInstance().getAllMoves());
    } catch (Exception e) {
      e.printStackTrace();
    }
    moveNodeID.setCellValueFactory(new PropertyValueFactory<Move, String>("nodeID"));
    moveDate.setCellValueFactory(new PropertyValueFactory<Move, Date>("moveDate"));
    moveLongName.setCellValueFactory(new PropertyValueFactory<Move, String>("longName"));
    message.setCellValueFactory(new PropertyValueFactory<Move, String>("message"));
    moveTable.setItems(moveList);
    moveTable.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    moveTable.getColumns().stream()
        .forEach(
            (column) -> {
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
            });
  }

  @Override
  public void deselect() {
    moveTable.getSelectionModel().clearSelection();
  }

  @FXML
  public void getSelectedRow() {
    addFormController.dataToChange(moveTable.getSelectionModel().getSelectedItem());
  }

  @Override
  public boolean delete() {
    if (moveTable.getSelectionModel().getSelectedItem() != null) {
      FDdb.getInstance().deleteMove(moveTable.getSelectionModel().getSelectedItem());
      refresh();
      return true;
    } else {
      return false;
    }
  }

  public void tablehandling() {
    ObservableList<Move> moveList = null;
    try {
      moveList = FXCollections.observableArrayList(FDdb.getInstance().getAllMoves());
    } catch (Exception e) {
      e.printStackTrace();
    }
    moveNodeID.setCellValueFactory(new PropertyValueFactory<Move, String>("nodeID"));
    moveDate.setCellValueFactory(new PropertyValueFactory<Move, Date>("moveDate"));
    moveLongName.setCellValueFactory(new PropertyValueFactory<Move, String>("longName"));
    message.setCellValueFactory(new PropertyValueFactory<Move, String>("message"));
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
