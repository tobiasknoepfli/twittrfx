package twittrfx;

import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import java.util.List;

import static twittrfx.AppStarter.WINDOW_HEIGHT;
import static twittrfx.AppStarter.WINDOW_WIDTH;

public class TableUI extends BorderPane {

    private TableView<Bird> table;

    public TableUI(List<Bird> birdList) {
        initializeControls();
        populateTable(birdList);
        layoutControls();
    }

    private void initializeControls() {
        table = new TableView<>();
        table.setPrefWidth(WINDOW_WIDTH);
        table.setPrefHeight(WINDOW_HEIGHT * 0.75);

        TableColumn<Bird, String> column1 = new TableColumn<>("Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));
        column1.setPrefWidth(WINDOW_WIDTH / 6);

        TableColumn<Bird, String> column2 = new TableColumn<>("Population Trend");
        column2.setCellValueFactory(new PropertyValueFactory<>("populationTrend"));
        column2.setPrefWidth(WINDOW_WIDTH / 6);

        TableColumn<Bird, String> column3 = new TableColumn<>("Population Status");
        column3.setCellValueFactory(new PropertyValueFactory<>("populationStatus"));
        column3.setPrefWidth(WINDOW_WIDTH / 6);

        table.getColumns().addAll(column1, column2, column3);
    }


    private void populateTable(List<Bird> birdList) {
        table.getItems().addAll(birdList);
    }

    private void layoutControls() {
        setCenter(table);
        setAlignment(table, Pos.CENTER);
    }

    public TableView<Bird> getTableView() {
        return table;
    }

    public void refresh(List<Bird> birdList) {
        table.getItems().clear();
        table.getItems().addAll(birdList);
    }
}
