package edu.wpi.cs3733.C23.teamD.userinterface.controllers;

import edu.wpi.cs3733.C23.teamD.App;
import edu.wpi.cs3733.C23.teamD.database.entities.CurrentUserEnum;
import edu.wpi.cs3733.C23.teamD.database.entities.Move;
import edu.wpi.cs3733.C23.teamD.database.entities.Node;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import edu.wpi.cs3733.C23.teamD.userinterface.components.controllers.*;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import java.io.IOException;
import java.net.URL;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.controlsfx.control.PopOver;

public class MoveRequestTableController implements Initializable {
  @FXML private MFXDatePicker datePicker;
  @FXML private TableView<Move> moveTable;
  @FXML private TableColumn<Move, String> moveNodeID;
  @FXML private TableColumn<Move, Date> moveDate;
  @FXML private TableColumn<Move, String> moveLongName;
  @FXML private TableColumn<Move, String> messageColumn;
  @FXML private Parent nodeBox;
  @FXML private NodeComboBoxController nodeBoxController;
  @FXML private Parent locationBox;
  @FXML private LocationComboBoxController locationBoxController;
  @FXML private Text errorText;
  @FXML private TextArea messageBox;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    tablehandling();
  }

  @FXML
  public void displayMove() {
    Navigation.navigate(Screen.MOVE_DISPLAY);
  }

  @FXML
  public void submit() throws IOException {
    if (checkFields()) {
      errorText.setVisible(false);
      ZoneId defaultZoneId = ZoneId.systemDefault();
      Date date = Date.from(datePicker.getValue().atStartOfDay(defaultZoneId).toInstant());
      if (messageBox.getText() == null) {
        Move move =
            new Move(
                FDdb.getInstance().getNode(nodeBoxController.getNode()),
                FDdb.getInstance().getNode(locationBoxController.getLocation()),
                date);
        FDdb.getInstance().saveMove(move);
        clearFields();
        if (CurrentUserEnum._CURRENTUSER.getSetting().getConfetti() == 1) {
          ConfettiController.makeConfetti(1500, 50, 100);
        }
        ToastController.makeText("Move Request Submitted!", 3000, 50, 100, 200, 720);
      } else {
        Move move =
            new Move(
                FDdb.getInstance().getNode(nodeBoxController.getNode()),
                FDdb.getInstance().getNode(locationBoxController.getLocation()),
                date,
                messageBox.getText());
        FDdb.getInstance().saveMove(move);
        clearFields();
        if (CurrentUserEnum._CURRENTUSER.getSetting().getConfetti() == 1) {
          ConfettiController.makeConfetti(1500, 50, 100);
        }
        ToastController.makeText("Move Request Submitted!", 3000, 50, 100, 200, 720);
      }
    } else {
      errorText.setVisible(true);
    }
    moveTable.getItems().clear();

    ArrayList<Move> allmoves = FDdb.getInstance().getAllMoves();
    ArrayList<Move> futureMoveList = new ArrayList<>();
    long millis = System.currentTimeMillis();
    Date todayDate = new java.sql.Date(millis);

    for (Move m : allmoves) {
      Move aNewMove = new Move();
      if (m.getMoveDate().after(todayDate)) {
        System.out.println(todayDate.after(m.getMoveDate()));
        System.out.println(m.getMoveDate());
        aNewMove.setMoveDate(m.getMoveDate());
        aNewMove.setLocation(m.getLocation());
        aNewMove.setNode(m.getNode());
        if (m.getMessage() == null) {
          aNewMove.setMessage("null");
        } else {
          aNewMove.setMessage(m.getMessage());
        }
        futureMoveList.add(aNewMove);
      }
    }
    ObservableList<Move> moveList = FXCollections.observableArrayList(futureMoveList);
    moveTable.setItems(moveList);

    try {
      generateRequestsPopup();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private boolean checkFields() {
    return !(datePicker.getValue() == null
        || nodeBoxController.getNodeID().isEmpty()
        || locationBoxController.getLocationLongName().isEmpty());
  }

  @FXML
  public void clearFields() {
    errorText.setVisible(false);
    nodeBoxController.clearForm();
    locationBoxController.clearForm();
    datePicker.clear();
    messageBox.clear();
  }

  public void tablehandling() {
    ArrayList<Node> nodes = FDdb.getInstance().getAllNodes();
    ArrayList<Move> allmoves = FDdb.getInstance().getAllMoves();
    ArrayList<Move> futureMoveList = new ArrayList<>();

    long millis = System.currentTimeMillis();
    Date todayDate = new java.sql.Date(millis);
    for (Move m : allmoves) {
      Move aNewMove = new Move();
      if (m.getMoveDate().after(todayDate)) {
        aNewMove.setMoveDate(m.getMoveDate());
        aNewMove.setLocation(m.getLocation());
        aNewMove.setNode(m.getNode());
        if (m.getMessage() == null) {
          aNewMove.setMessage("null");
        } else {
          aNewMove.setMessage(m.getMessage());
        }
        futureMoveList.add(aNewMove);
      }
    }

    ObservableList<Move> moveList = FXCollections.observableArrayList(futureMoveList);

    moveNodeID.setCellValueFactory(new PropertyValueFactory<Move, String>("nodeID"));
    moveDate.setCellValueFactory(new PropertyValueFactory<Move, Date>("moveDate"));
    moveLongName.setCellValueFactory(new PropertyValueFactory<Move, String>("longName"));
    messageColumn.setCellValueFactory(new PropertyValueFactory<Move, String>("message"));
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

  private void generateRequestsPopup() throws IOException {
    final var resource = App.class.getResource("views/AutoGeneratePopup.fxml");
    final FXMLLoader loader = new FXMLLoader(resource);
    PopOver popover = new PopOver(loader.load());
    popover.setArrowSize(0);
    popover.setCornerRadius(32);
    popover.setTitle("Generated Service Request Editor");
    popover.show(App.getPrimaryStage());
  }
}
