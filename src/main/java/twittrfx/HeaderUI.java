package twittrfx;

import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class HeaderUI extends HBox {


    public HeaderUI() {
        initializeControls();
        layoutControls();
    }

    private void initializeControls() {
        getStyleClass().add("header-bar");

        setMaxHeight(30);

        setPadding(new Insets(5,10,5,10));
        setSpacing(10);
    }

    private void layoutControls() {

    }
}
