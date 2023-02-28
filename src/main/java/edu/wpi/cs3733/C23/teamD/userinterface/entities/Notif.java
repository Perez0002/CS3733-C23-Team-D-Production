package edu.wpi.cs3733.C23.teamD.userinterface.entities;

import lombok.Getter;
import lombok.Setter;

public class Notif {

  @Getter @Setter private String notification;

  public Notif(String s) {
    notification = s;
  }
}
