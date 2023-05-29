package twittrfx;

import javafx.geometry.Pos;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

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
        //add list
        BorderPane leftBorderPane = new BorderPane();
        List<Bird> birdList = readBirdDataFromTSVFile("/twittrfx/birds_of_switzerland.tsv");
        TableUI tableUI = new TableUI(birdList);
        leftBorderPane.setBottom(tableUI);
        BorderPane.setAlignment(tableUI, Pos.BOTTOM_LEFT);

        //add header
        BorderPane headerPane = new BorderPane();
        HeaderUI header = new HeaderUI();
        headerPane.setTop(header);

        getChildren().addAll(headerPane, leftBorderPane);
    }

    private void setupEventHandlers() {
    }

    private void setupValueChangedListeners() {
    }

    private void setupBindings() {
    }

    private List<Bird> readBirdDataFromTSVFile(String filePath) {
        List<Bird> birdList = new ArrayList<>();

        try {
            InputStream inputStream = getClass().getResourceAsStream(filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            // Skip the header line
            String headerLine = reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\t");

                if (data.length == 16) {
                    Bird bird = new Bird(data[0], data[1], data[2], data[3], data[4], data[5], data[6],
                            data[7], data[8], data[9], data[10], data[11], data[12], data[13], data[14], data[15]);

                    birdList.add(bird);
                }
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return birdList;
    }
}
