package edu.wpi.cs3733.C23.teamD.user.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Setting {

  @Id
  @Getter @Setter
  @OneToOne
  @JoinColumn(
      name = "employee",
      foreignKey =
          @ForeignKey(
              name = "employee_fk",
              foreignKeyDefinition =
                  "FOREIGN KEY (employee) REFERENCES Employee(employeeID) ON UPDATE CASCADE ON DELETE CASCADE"))
  private Employee employee;

  @Getter @Setter private int confetti;

  @Getter @Setter private int darkmode;

  public Setting() {}

  public Setting(Employee employee, int confetti, int darkmode) {
    this.employee = employee;
    this.confetti = confetti;
    this.darkmode = darkmode;
  }
}
