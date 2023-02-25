package edu.wpi.cs3733.C23.teamD;

import edu.wpi.cs3733.C23.teamD.database.util.DBSingleton;
import edu.wpi.cs3733.C23.teamD.database.util.FDdb;
import edu.wpi.cs3733.C23.teamD.navigation.Navigation;
import edu.wpi.cs3733.C23.teamD.navigation.Screen;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App extends Application {

  @Setter @Getter private static Stage primaryStage;
  @Setter @Getter private static BorderPane rootPane;

  @Override
  public void init() {
    log.info("Starting Up");
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    /* primaryStage is generally only used if one of your components require the stage to display */
    App.primaryStage = primaryStage;

    final FXMLLoader loader = new FXMLLoader(App.class.getResource("views/Root.fxml"));

    final BorderPane root = loader.load();

    App.rootPane = root;

    Scene scene = new Scene(root);

    // style sheet
    String css = this.getClass().getResource("views/updatedStyleGuide.css").toExternalForm();
    scene.getStylesheets().add(css);
    // end style sheet

    primaryStage.setScene(scene);
    primaryStage.setMaximized(true);
    rootPane.setLeft(null);
    Navigation.navigate(Screen.LOADING_PAGE);
    primaryStage.show();
    Boolean startApp = FDdb.getInstance().refreshAll();
    DBSingleton.refreshSession();
    if (startApp) {
      Navigation.navigate(Screen.LOGIN_PAGE);
    }
  }

  @Override
  public void stop() {
    log.info("Shutting Down");
    System.exit(0);
  }
}
