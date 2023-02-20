package edu.wpi.cs3733.C23.teamD.servicerequest.controllers;

import edu.wpi.cs3733.C23.teamD.mapeditor.util.MapFactory;
import javafx.geometry.Point2D;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import net.kurobako.gesturefx.GesturePane;

public class ServiceRequestMap {

    static ServiceRequestMap mapSingleton;
    private static GesturePane map;

    ServiceRequestMap(BorderPane mapPaneContainer) {
        if (mapSingleton == null) {
            map = MapFactory.startBuild().build(0);
            map.setStyle("-fx-border-color: #012D5A;");
            map.setMaxSize(700, 500);
            mapPaneContainer.setCenter(map);
        }
        else {
            System.out.println("DEV ERROR: ServiceRequestMap already created. Can not create another.");
        }
    }

    ServiceRequestMap getMapSingleton() {
        return mapSingleton;
    }

    void centerOnNode(int x, int y) {
        map.animate(Duration.millis(50)).centreOn(new Point2D(x, y));
    }

}
