package twittrfx;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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
