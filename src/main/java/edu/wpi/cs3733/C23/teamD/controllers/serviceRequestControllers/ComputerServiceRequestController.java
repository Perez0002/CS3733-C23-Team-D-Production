package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import edu.wpi.cs3733.C23.teamD.controllers.ServiceRequestController;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ComputerServiceRequestController extends ServiceRequestController {
    @FXML
    private ArrayList<String> urgency;
    private ArrayList<String> deviceType;

    @FXML
    private MFXComboBox deviceTypeBox;
    @FXML
    private MFXComboBox urgencyBox;
    @FXML
    private MFXTextField descriptionBox;

    public  ComputerServiceRequestController(){

    }
    public void initialize(){



    }
}
