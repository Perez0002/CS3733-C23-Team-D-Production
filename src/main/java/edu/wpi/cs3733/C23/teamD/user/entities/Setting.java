package edu.wpi.cs3733.C23.teamD.user.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Setting {

  @Id
  @Getter
  @Setter
  @OneToOne
  @JoinColumn(
      name = "employeeID",
      foreignKey =
          @ForeignKey(
              name = "employee_fk",
              foreignKeyDefinition =
                  "FOREIGN KEY (employeeID) REFERENCES Employee(employeeID) ON UPDATE CASCADE ON DELETE CASCADE"))
  private Employee employeeID;

  @Getter @Setter private int confetti;

  @Getter @Setter private int darkmode;

  public Setting() {}

  public Setting(Employee employeeID, int confetti, int darkmode) {
    this.employeeID = employeeID;
    this.confetti = confetti;
    this.darkmode = darkmode;
  }
}
