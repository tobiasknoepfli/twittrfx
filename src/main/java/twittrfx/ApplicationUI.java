package twittrfx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ApplicationUI extends StackPane {

    private final PresentationModel model;
    private  Text rowCountText;
    private Text highestSpeedText;

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
        rowCountText = new Text();
        highestSpeedText = new Text();
    }

    private void layoutControls() {
        //add basic layout
        BorderPane borderPane = new BorderPane();

        //add texts
        Text rowCountTitle = new Text("Amount of bird species:");
        Text topSpeedTitle = new Text("Highest top speed:");

        //add HeaderBar
        VBox header = new VBox();
        HeaderUI headerBar = new HeaderUI();
        header.getChildren().add(headerBar);
        borderPane.setTop(header);

        //add left grid
        GridPane grid = new GridPane();
        grid.setHgap(2);
        grid.setVgap(10);
        grid.setPadding(new Insets(0,10,0,10));

        //add title to grid
        Text titleLeft = new Text("Birds of Switzerland");
        titleLeft.setFont(Font.font("Calibri",FontWeight.BOLD,25));
        grid.add(titleLeft,0,2,10,1);

        //add table to grid
        List<Bird> birdList = readBirdDataFromTSVFile("/twittrfx/birds_of_switzerland.tsv");
        TableUI tableUI = new TableUI(birdList);
        grid.add(tableUI,0,6,10,1);

        //add number of data sets to grid
        rowCountTitle.setFont(Font.font("Calibri",12));
        rowCountText.setFont(Font.font("Calibri",12));
        grid.add(rowCountTitle,0,3,4,1);
        grid.add(rowCountText,5,3);
        updateRowCountText(birdList);

        //add highest top speed
        topSpeedTitle.setFont(Font.font("Calibri",12));
        highestSpeedText.setFont(Font.font("Calibri",12));
        grid.add(topSpeedTitle,0,4,4,1);
        grid.add(highestSpeedText,5,4);
        updateHighestSpeedText(birdList);

        borderPane.setLeft(grid);

        getChildren().add(borderPane);
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

    private void updateRowCountText(List<Bird> birdList) {
        int rowCount = birdList.size();
        rowCountText.setText(String.valueOf(rowCount));
    }

    private void updateHighestSpeedText(List<Bird> birdList) {
        double highestSpeed = birdList.stream()
                .mapToDouble(bird -> Double.parseDouble(bird.getTopSpeedInKmh()))
                .max()
                .orElse(0.0);
        highestSpeedText.setText(highestSpeed +" km/h");
    }


}
