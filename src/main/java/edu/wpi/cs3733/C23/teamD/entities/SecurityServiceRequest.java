package edu.wpi.cs3733.C23.teamD.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@AllArgsConstructor
public class SecurityServiceRequest extends ServiceRequest {
    @Getter
    private String typeOfSecurityRequest;

    @Getter
    @Enumerated(value= EnumType.STRING)
    private Urgency urgency;

    public SecurityServiceRequest() {}

    private enum Urgency {
        HIGH,
        MEDIUM,
        LOW
    }
}
