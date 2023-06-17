package twittrfx;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static twittrfx.AppStarter.WINDOW_HEIGHT;
import static twittrfx.AppStarter.WINDOW_WIDTH;

public class ApplicationUI extends HBox {

    private final PresentationModel model;
    private Text rowCountText;
    private Text highestSpeedText;
    private Text titleText;
    private Label birdNameLabel;
    private ImageView birdImage;


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
        birdNameLabel = new Label();
        birdImage = new ImageView();
        birdImage.setFitWidth(WINDOW_WIDTH / 4);
        birdImage.setPreserveRatio(true);
    }

    private void layoutControls() {
        //set basic layout with a split pane
        SplitPane windowFrame = new SplitPane();
        windowFrame.setDividerPositions(0.5);

        //set HeaderBar
        HeaderUI headerBar = new HeaderUI();

        //GRID PANE LEFT
        //set basic grid pane left
        GridPane leftBasicGrid = new GridPane();
        leftBasicGrid.setPrefWidth(WINDOW_WIDTH / 2);
        leftBasicGrid.setPadding(new Insets(5, 5, 10, 10));
        leftBasicGrid.setHgap(10);
        leftBasicGrid.setVgap(1);

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

        //GRID PANE RIGHT
        //set basic grid pane right
        GridPane rightBasicGrid = new GridPane();
        rightBasicGrid.setPrefWidth(WINDOW_WIDTH / 2);
        rightBasicGrid.setPadding(new Insets(5, 10, 10, 5));
        rightBasicGrid.setHgap(10);
        rightBasicGrid.setVgap(1);

        //set vbox to top of grid pane right
        VBox topRightBox1 = new VBox();
        VBox topRightBox2 = new VBox();

        topRightBox1.setMinHeight(WINDOW_HEIGHT / 3);
        topRightBox1.setMaxHeight(WINDOW_HEIGHT / 3);
        topRightBox1.setPrefWidth(WINDOW_WIDTH / 4);
        topRightBox2.setMinHeight(WINDOW_HEIGHT / 3);
        topRightBox2.setMaxHeight(WINDOW_HEIGHT / 3);
        topRightBox2.setPrefWidth(WINDOW_WIDTH / 4);

        //add the vboxes to the right basic Grid
        rightBasicGrid.add(new TitleTextUI(""), 0, 0);
        rightBasicGrid.add(topRightBox1, 0, 1);
        rightBasicGrid.add(topRightBox2, 1, 1);

        //create a vBox for the first Column
        VBox firstColumn = new VBox();
        firstColumn.setMinHeight(WINDOW_HEIGHT / 3 * 2);
        firstColumn.setMaxWidth(WINDOW_WIDTH / 2 / 5);

        //create a vBox for the second Column
        VBox secondColumn = new VBox();
        firstColumn.setMinHeight(WINDOW_HEIGHT / 3 * 2);
        firstColumn.setMaxWidth(WINDOW_WIDTH / 2 / 5);

        //create a list with all the fix labels
        List<Label> catTitles = new ArrayList<>();
        catTitles.add(new Label("Name"));
        catTitles.add(new Label("Short Description"));
        catTitles.add(new Label("Population Size"));
        catTitles.add(new Label("Top Speed"));
        catTitles.add(new Label("Length"));
        catTitles.add(new Label("Continents"));
        catTitles.add(new Label("Diet"));
        catTitles.add(new Label("Seasonal Behaviour"));
        catTitles.add(new Label("Population Trend"));
        catTitles.add(new Label("Image"));

        //add the list to the first column vbox
        for (Label l : catTitles) {
            l.setPrefHeight(WINDOW_HEIGHT / 3 * 2 / 14);
            firstColumn.getChildren().add(l);
        }

        //add the first column to the right basic grid
        rightBasicGrid.add(firstColumn, 0, 11);

        //Create labels for the bird title on the right side
        Label birdTitleLabel = new Label();
        birdTitleLabel.getStyleClass().add("bird-title-label");

        //create a label for the continents on the right side
        Label continentsLabel = new Label();
        continentsLabel.setWrapText(true);

        //create bindings for the top right side labels
        birdTable.getTableView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                birdTitleLabel.setText(newValue.getName());
                continentsLabel.setText(newValue.getContinents());
                Image birdImg = new Image(newValue.getImage());
                birdImage.setImage(birdImg);
            } else {
                // No selection, clear the label
                birdTitleLabel.setText("");
                continentsLabel.setText("");
                birdImage.setImage(null);
            }
        });

        //add the bound labels to the top right vboxes
        topRightBox1.getChildren().addAll(birdTitleLabel, continentsLabel);
        topRightBox2.getChildren().addAll(birdImage);

        //create Arraylist with TextFields
        List<TextField> birdProperties = new ArrayList<TextField>();
        birdProperties.add(new TextField("birdName"));
        birdProperties.add(new TextField("birdDescription"));
        birdProperties.add(new TextField("birdPopulationSize"));
        birdProperties.add(new TextField("birdTopSpeed"));
        birdProperties.add(new TextField("birdLength"));
        birdProperties.add(new TextField("birdContinents"));
        birdProperties.add(new TextField("birdDiet"));
        birdProperties.add(new TextField("birdSeasonalBehaviour"));
        birdProperties.add(new TextField("birdPopulationTrend"));
        birdProperties.add(new TextField("birdImage"));

        //create bindings for the second column bottom right
        birdTable.getTableView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                birdProperties.get(0).setText(newValue.getName());
                birdProperties.get(1).setText(newValue.getShortDescription());
                birdProperties.get(2).setText(newValue.getPopulationSize());
                birdProperties.get(3).setText(newValue.getTopSpeedInKmh());
                birdProperties.get(4).setText(newValue.getLength());
                birdProperties.get(5).setText(newValue.getContinents());
                birdProperties.get(6).setText(newValue.getDiet());
                birdProperties.get(7).setText(newValue.getSeasonalBehavior());
                birdProperties.get(8).setText(newValue.getPopulationTrend());
                birdProperties.get(9).setText(newValue.getImage());
            }
        });

        //add the list to the second column vbox
        for (TextField t : birdProperties) {
            t.setPrefHeight(WINDOW_HEIGHT / 3 * 2 / 14);
            secondColumn.getChildren().add(t);
        }

        //add the first column to the right basic grid
        rightBasicGrid.add(secondColumn, 1, 11);


        //add children to windowFrame
        windowFrame.getItems().addAll(leftBasicGrid, rightBasicGrid);

        //add headerBar
        HeaderUI header = new HeaderUI();

        //create VBox to hold HeaderBar and SplitPane
        VBox rootBox = new VBox(headerBar, windowFrame);

        //display windowFrame
        getChildren().add(rootBox);

//        rightBasicGrid.setGridLinesVisible(true);
//        leftBasicGrid.setGridLinesVisible(true);

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
