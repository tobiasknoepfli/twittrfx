package twittrfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


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
        BorderPane leftBorderPane = new BorderPane();

        TableView<BirdData> table = new TableView<>();
        table.setMaxWidth(700);
        table.setPrefHeight(800);

        TableColumn<BirdData, String> column1 = new TableColumn<>("Name");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));
        column1.setPrefWidth(700/3);

        TableColumn<BirdData, String> column2 = new TableColumn<>("Population Trend");
        column2.setCellValueFactory(new PropertyValueFactory<>("populationTrend"));
        column2.setPrefWidth(700/3);

        TableColumn<BirdData, String> column3 = new TableColumn<>("Population Status");
        column3.setCellValueFactory(new PropertyValueFactory<>("populationStatus"));
        column3.setPrefWidth(700/3);

        table.getColumns().addAll(column1, column2, column3);

        // Read the TSV file and populate the table
        List<BirdData> birdDataList = readBirdDataFromTSVFile("/twittrfx/birds_of_switzerland.tsv");
        table.getItems().addAll(birdDataList);

        leftBorderPane.setBottom(table);
        BorderPane.setAlignment(table,Pos.BOTTOM_LEFT);

        getChildren().add(leftBorderPane);
    }

    private void setupEventHandlers() {
    }

    private void setupValueChangedListeners() {
    }

    private void setupBindings() {
    }

    private List<BirdData> readBirdDataFromTSVFile(String filePath) {
        List<BirdData> birdDataList = new ArrayList<>();

        try {
            InputStream inputStream = getClass().getResourceAsStream(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Skip the header line
            String headerLine = reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\t");

                if (data.length == 16) {
                    BirdData bird = new BirdData(data[0], data[1], data[2], data[3], data[4], data[5], data[6],
                            data[7], data[8], data[9], data[10], data[11], data[12], data[13], data[14], data[15]);

                    birdDataList.add(bird);
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return birdDataList;
    }
}
