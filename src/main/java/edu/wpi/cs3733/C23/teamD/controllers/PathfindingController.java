package edu.wpi.cs3733.C23.teamD.controllers;

import edu.wpi.cs3733.C23.teamD.entities.GraphMap;
import edu.wpi.cs3733.C23.teamD.entities.PathNode;
import edu.wpi.cs3733.C23.teamD.entities.Pathfinder;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

import java.util.ArrayList;


public class PathfindingController {
  @FXML private MFXButton cancelButton;

  @FXML private MFXTextField endRoom;
  private GraphMap mainMap= new GraphMap();
  @FXML private MFXTextField startRoom;

  @FXML
  public void initialize() {cancelButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));}

  @FXML
  void clearFields() {}

  @FXML
  void displayHelp() {}

  @FXML
  void submit() {

    Pathfinder PathfinderAStar=new Pathfinder(mainMap);
    ArrayList<PathNode> Path=new ArrayList<PathNode>();
    
    Path=PathfinderAStar.aStarSearch(mainMap.getNode(startRoom.getText()),mainMap.getNode(endRoom.getText()));
  }
}
