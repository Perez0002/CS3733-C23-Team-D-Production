package edu.wpi.cs3733.C23.teamD.controllers.serviceRequestControllers;

public class ServiceRequestController {
  private RequestSubmitter requestSubmitter;
  private FieldClearer fieldClearer;

  public void submit() {
    requestSubmitter.submit();
  }

  public void setFieldClearer(FieldClearer fieldClearer) {
    this.fieldClearer = fieldClearer;
  }

  public void clearFields() {
    fieldClearer.clearFields();
  }
}
