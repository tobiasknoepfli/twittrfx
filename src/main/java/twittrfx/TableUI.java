package twittrfx;

import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.util.List;

public class TableUI extends BorderPane {

    private TableView<Bird> table;

    public TableUI(List<Bird> birdList) {
        initializeControls();
        populateTable(birdList);
        layoutControls();
    }

    private void initializeControls() {
        table = new TableView<>();
        table.setMaxWidth(600);
        table.setPrefHeight(800);

        TableColumn<Bird, String> column1 = new TableColumn<>("Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));
        column1.setPrefWidth(200);

        TableColumn<Bird, String> column2 = new TableColumn<>("Population Trend");
        column2.setCellValueFactory(new PropertyValueFactory<>("populationTrend"));
        column2.setPrefWidth(200);

        TableColumn<Bird, String> column3 = new TableColumn<>("Population Status");
        column3.setCellValueFactory(new PropertyValueFactory<>("populationStatus"));
        column3.setPrefWidth(200);

        table.getColumns().addAll(column1, column2, column3);
    }

    private void populateTable(List<Bird> birdList) {
        table.getItems().addAll(birdList);
    }

    private void layoutControls() {
        setBottom(table);
        setAlignment(table, Pos.BOTTOM_LEFT);
    }

}
