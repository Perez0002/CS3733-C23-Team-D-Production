package edu.wpi.cs3733.C23.teamD.user.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Kiosk {
  @Id @Getter @Setter String IPaddress;

  @Getter @Setter String location;
}
