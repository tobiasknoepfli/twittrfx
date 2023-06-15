package twittrfx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static twittrfx.AppStarter.WINDOW_WIDTH;

public class ApplicationUI extends HBox {

    private final PresentationModel model;
    private Text rowCountText;
    private Text highestSpeedText;
    private Text titleText;

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
        rowCountText = new CategoryTextUI();
        highestSpeedText = new CategoryTextUI();
        titleText = new TitleTextUI("Birds of Switzerland");
    }

    private void layoutControls() {
        //set basic layout
        SplitPane windowFrame = new SplitPane();
        windowFrame.setDividerPositions(0.5);

        //set grid pane left
        GridPane leftBasicGrid = new GridPane();
        leftBasicGrid.setPrefWidth(WINDOW_WIDTH / 2);
        leftBasicGrid.setPadding(new Insets(5, 5, 10, 10));
        leftBasicGrid.setHgap(10);
        leftBasicGrid.setVgap(1);

        //set basic grid pane right
        GridPane rightBasicGrid = new GridPane();
        rightBasicGrid.setPrefWidth(WINDOW_WIDTH / 2);
        rightBasicGrid.setPadding(new Insets(5, 10, 10, 5));
        rightBasicGrid.setHgap(2);
        rightBasicGrid.setVgap(1);

        //set HeaderBar
        HeaderUI headerBar = new HeaderUI();

        //set birdTable
        List<Bird> birdList = readBirdDataFromTSVFile("/twittrfx/birds_of_switzerland.tsv");
        TableUI birdTable = new TableUI(birdList);

        //update texts
        updateRowCountText(birdList);
        updateHighestSpeedText(birdList);

        //populate leftBasicGrid
        leftBasicGrid.add(new TitleTextUI(""), 0, 0);
        leftBasicGrid.add(titleText, 0, 1, 2, 1);
        leftBasicGrid.add(new CategoryTextUI("Amount of bird species:"), 0, 2);
        leftBasicGrid.add(rowCountText, 1, 2);
        leftBasicGrid.add(new CategoryTextUI("Highest top speed:"), 0, 3);
        leftBasicGrid.add(highestSpeedText, 1, 3);
        leftBasicGrid.add(new TitleTextUI(""), 0, 4);
        leftBasicGrid.add(birdTable, 0, 5, 3, 1);

        //add children to windowFrame
        windowFrame.getItems().addAll(leftBasicGrid, rightBasicGrid);

        //add headerBar
        HeaderUI header = new HeaderUI();

        //create VBox to hold HeaderBar and SplitPane
        VBox rootBox = new VBox(headerBar, windowFrame);

        //display windowFrame
        getChildren().add(rootBox);


//        //add texts
//        Text rowCountTitle = new Text("Amount of bird species:");
//        Text topSpeedTitle = new Text("Highest top speed:");
//
//        Text nameCat = new Text("Name");
//        Text shortDescriptionCat = new Text("short description");
//        Text populationCat = new Text("Population size");
//        Text maxLifespanCat= new Text("Maximum life span");
//        Text topSpeedCat = new Text("Top Speed");
//        Text weightCat = new Text("Weight");
//        Text lengthCat = new Text("Length");
//        Text wingspanCat = new Text("Wingspan");
//        Text continentsCat = new Text("Continents");
//        Text incubationPeriodCat = new Text("Incubation period");
//        Text dietCat = new Text("Diet");
//        Text seasonalBehaviourCat = new Text("Seasonal behaviour");
//        Text independentAgeCat = new Text("Independent age");
//        Text populationTrendCat = new Text("Population Trend");
//        Text populationStatusCat = new Text("Population status");
//        Text imageCat = new Text("Image");
//
//

//
//        //add left grid
//        GridPane leftGrid = new GridPane();
//        leftGrid.setPrefWidth(700);
//        leftGrid.setHgap(2);
//        leftGrid.setVgap(10);
//        leftGrid.setPadding(new Insets(0,10,0,10));
//        //add right grid
//        GridPane rightGrid = new GridPane();
//        rightGrid.setPrefWidth(700);
//        rightGrid.setHgap(2);
//        rightGrid.setVgap(10);
//        rightGrid.setPadding(new Insets(0,10,0,20));
//
//        //add title to grid
//        Text titleLeft = new Text("Birds of Switzerland");
//        titleLeft.setFont(Font.font("Calibri",FontWeight.BOLD,25));
//        leftGrid.add(titleLeft,0,2,10,1);
//
//        //add table to left grid
//        leftGrid.add(tableUI,0,6,10,1);
//
//        //add number of data sets to left grid
//        rowCountTitle.setFont(Font.font("Calibri",12));
//        rowCountText.setFont(Font.font("Calibri",12));
//        leftGrid.add(rowCountTitle,0,3,4,1);
//        leftGrid.add(rowCountText,5,3);
//        updateRowCountText(birdList);
//
//        //add highest top speed to left grid
//        topSpeedTitle.setFont(Font.font("Calibri",12));
//        highestSpeedText.setFont(Font.font("Calibri",12));
//        leftGrid.add(topSpeedTitle,0,4,4,1);
//        leftGrid.add(highestSpeedText,5,4);
//        updateHighestSpeedText(birdList);
//
//        //add textCategories to right grid
//        rightGrid.add(nameCat,0,10,2,1);
//        rightGrid.add(shortDescriptionCat,0,11,2,1);
//        rightGrid.add(populationCat,0,12,2,1);
//        rightGrid.add(topSpeedCat,0,13,2,1);
//        rightGrid.add(lengthCat,0,14,2,1);
//
//
//        borderPane.setLeft(leftGrid);
//        borderPane.setRight(rightGrid);
//
//        getChildren().add(borderPane);
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
        getStyleClass().add("category-text");
    }

    private void updateHighestSpeedText(List<Bird> birdList) {
        double highestSpeed = birdList.stream()
                .mapToDouble(bird -> Double.parseDouble(bird.getTopSpeedInKmh()))
                .max()
                .orElse(0.0);
        highestSpeedText.setText(highestSpeed + " km/h");
        getStyleClass().add("category-text");
    }


}
