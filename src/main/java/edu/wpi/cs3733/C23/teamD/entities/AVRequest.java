package edu.wpi.cs3733.C23.teamD.entities;

import edu.wpi.cs3733.C23.teamD.controllers.components.EmployeeDropdownComboBoxController;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.persistence.Entity;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
public class AVRequest extends ServiceRequest{

    @Getter @Setter
     private String systemFailureTextField;
@Getter @Setter
     private Date dateFirstSeen;

    public AVRequest(String associatedStaff, Status stat, String reason, String serviceRequestType, String systemFailureTextField, Date dateFirstSeen) {
        super(associatedStaff, stat, reason, serviceRequestType);
        this.systemFailureTextField = systemFailureTextField;
        this.dateFirstSeen = dateFirstSeen;
    }

    public AVRequest(int serviceId, String associatedStaff, Status stat, String reason, String serviceRequestType, Date date, String systemFailureTextField, Date dateFirstSeen) {
        super(serviceId, associatedStaff, stat, reason, serviceRequestType, date);
        this.systemFailureTextField = systemFailureTextField;
        this.dateFirstSeen = dateFirstSeen;
    }

    public AVRequest(String systemFailureTextField, Date dateFirstSeen) {
        this.systemFailureTextField = systemFailureTextField;
        this.dateFirstSeen = dateFirstSeen;
    }
}
