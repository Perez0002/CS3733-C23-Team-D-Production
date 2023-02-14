package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

public class AVRequestController extends ServiceRequestController{
    @FXML
    private MFXTextField descriptionTextField;

    @FXML
    private MFXTextField staffIDTextField;

    @FXML
    private MFXTextField systemFailureTextBox;

    @FXML
    private MFXTextField timeTextField;

    public void initialize() {

    }
}
