package twittrfx;

import javafx.geometry.Insets;
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
        setPrefHeight(50);

        Label titleLabel = new Label("Header");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.WHITE);

        setBackground(new Background(new BackgroundFill(Color.DARKORCHID, null, null)));

        setPadding(new Insets(10));
        setSpacing(10);
        getChildren().add(titleLabel);
    }

    private void layoutControls() {
        // No additional layout needed since this class extends HBox
    }
}
