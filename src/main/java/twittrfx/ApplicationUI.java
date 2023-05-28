package twittrfx;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class ApplicationUI extends StackPane {

    private final PresentationModel model;

    public ApplicationUI(PresentationModel model) {
        this.model = model;
        initializeSelf();
        initializeControls();
        layoutControls();
        setupEventHandlers();
        setupValueChangedListeners();
        setupBindings();
    }

    private void initializeSelf() {
        String stylesheet = getClass().getResource("style.css").toExternalForm();
        getStylesheets().add(stylesheet);
    }

    private void initializeControls() {
    }

    private void layoutControls() {
        VBox vBoxTable = new VBox();
        vBoxTable.setSpacing(10);
        vBoxTable.setPadding(new Insets(10));

        TableView<String> table = new TableView<>();
        table.setMaxWidth(600);
        table.setPrefHeight(800);

        TableColumn<String, String> column1 = new TableColumn<>("Name");
        column1.setPrefWidth(200);
        TableColumn<String, String> column2 = new TableColumn<>("Population Trend");
        column2.setPrefWidth(200);
        TableColumn<String, String> column3 = new TableColumn<>("Population Status");
        column3.setPrefWidth(200);

        table.getColumns().addAll(column1,column2,column3);

        vBoxTable.getChildren().add(table);

        getChildren().add(vBoxTable);

        BorderPane borderPane = new BorderPane();
        borderPane.setBottom(vBoxTable);
        BorderPane.setAlignment(vBoxTable, Pos.BOTTOM_LEFT);

        getChildren().add(borderPane);
    }

    private void setupEventHandlers() {
    }

    private void setupValueChangedListeners() {
    }

    private void setupBindings() {
    }
}
