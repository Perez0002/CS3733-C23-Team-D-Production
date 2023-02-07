import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class LandingPageController {

  @FXML private MFXButton backButton;

  @FXML private MFXButton internalPatientTransportationRequestFormButton;

  @FXML private MFXButton pathfindingButton;

  @FXML private MFXButton sanitationServiceRequestFormButton;

  @FXML
  public void initialize() {
    internalPatientTransportationRequestFormButton.setOnMouseClicked(
        event -> Navigation.navigate(Screen.PATIENT_TRANSPORT_REQUEST));
    sanitationServiceRequestFormButton.setOnMouseClicked(
        event -> Navigation.navigate(Screen.SANITATION_FORM));
    pathfindingButton.setOnMouseClicked(event -> Navigation.navigate(Screen.PATHFINDING_REQUEST));
  }
}
