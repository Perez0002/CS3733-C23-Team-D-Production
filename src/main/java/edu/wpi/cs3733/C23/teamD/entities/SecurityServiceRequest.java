package edu.wpi.cs3733.C23.teamD.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
public class SecurityServiceRequest extends ServiceRequest {
    @Getter
    private String typeOfSecurityRequest;

    @Getter
    private String urgency;

    public SecurityServiceRequest(String typeOfSecurityRequest, String urgency, String staff, Status stat, String reason) {
        super(staff, stat, reason, "Security");
        this.typeOfSecurityRequest = typeOfSecurityRequest;
        this.urgency = urgency;
    }

}
