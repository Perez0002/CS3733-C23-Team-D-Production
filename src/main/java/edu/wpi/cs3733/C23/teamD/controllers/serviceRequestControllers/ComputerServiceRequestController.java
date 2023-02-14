package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

import edu.wpi.cs3733.C23.teamD.controllers.ServiceRequestController;
import edu.wpi.cs3733.C23.teamD.databasesubsystem.FDdb;
import edu.wpi.cs3733.C23.teamD.entities.ComputerServiceRequest;
import edu.wpi.cs3733.C23.teamD.entities.ServiceRequest;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class ComputerServiceRequestController extends ServiceRequestController {
    @FXML
    private ArrayList<String> deviceType;

    @FXML
    private MFXComboBox deviceTypeBox;
    @FXML
    private MFXComboBox urgencyBox;
    @FXML
    private MFXComboBox employeeBox;
    @FXML
    private MFXTextField descriptionBox;
    @FXML
    private MFXComboBox locationBox;


    public  ComputerServiceRequestController(){
        deviceType.add("Laptop Computer");
        deviceType.add("Desktop Computer");
        deviceType.add("Tablet");
        deviceType.add("Kiosk");
        deviceType.add("Other");
    }

    public void initialize(){
        deviceTypeBox.setItems(FXCollections.observableArrayList(deviceType));



    }
    public void submit(){
        ComputerServiceRequest computerServiceRequest= new ComputerServiceRequest(descriptionBox.getText(),employeeBox.getText(), ServiceRequest.Status.PROCESSING,urgencyBox.getText(),deviceTypeBox.getText(),locationBox.getText());
        FDdb.getInstance().saveServiceRequest(computerServiceRequest);
    }
}
