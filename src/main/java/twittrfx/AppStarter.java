package twittrfx;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class AppStarter extends Application {
    public static double WINDOW_WIDTH = 1400;
    public static double WINDOW_HEIGHT= 1000;

    @Override
    public void start(Stage primaryStage) throws Exception {

        PresentationModel pm = new PresentationModel();
        Parent rootPanel = new ApplicationUI(pm);

        Scene scene = new Scene(rootPanel);
        primaryStage.titleProperty().bind(pm.applicationTitleProperty());
        primaryStage.setScene(scene);

        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
