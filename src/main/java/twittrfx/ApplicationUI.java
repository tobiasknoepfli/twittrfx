package twittrfx;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

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
        birdImage.setFitWidth(WINDOW_WIDTH/4);
        birdImage.setPreserveRatio(true);
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
        rightBasicGrid.setHgap(10);
        rightBasicGrid.setVgap(1);

        //set vbox to top of grid pane right
        VBox topRightBox1 = new VBox();
        VBox topRightBox2 = new VBox();

        topRightBox1.setMinHeight(WINDOW_HEIGHT/3);
        topRightBox1.setMaxHeight(WINDOW_HEIGHT/3);
        topRightBox1.setPrefWidth(WINDOW_WIDTH /4);
        topRightBox2.setMinHeight(WINDOW_HEIGHT/3);
        topRightBox2.setMaxHeight(WINDOW_HEIGHT/3);
        topRightBox2.setPrefWidth(WINDOW_WIDTH /4);

        rightBasicGrid.add(new TitleTextUI(""), 0, 0);
        rightBasicGrid.add(topRightBox1,0,1);
        rightBasicGrid.add(topRightBox2,1,1);

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

        //populate rightBasicGrid
        List<CategoryTextUI> catTitles = new ArrayList<>();
        catTitles.add(new CategoryTextUI("Name"));
        catTitles.add(new CategoryTextUI("Short Description"));
        catTitles.add(new CategoryTextUI("Population Size"));
        catTitles.add(new CategoryTextUI("Top Speed"));
        catTitles.add(new CategoryTextUI("Length"));
        catTitles.add(new CategoryTextUI("Continents"));
        catTitles.add(new CategoryTextUI("Diet"));
        catTitles.add(new CategoryTextUI("Seasonal Behaviour"));
        catTitles.add(new CategoryTextUI("Population Trend"));
        catTitles.add(new CategoryTextUI("Image"));

        int i = 10;
        for (CategoryTextUI c:catTitles){
            rightBasicGrid.add(c,0,i);
            i++;
        }

        Label birdTitleLabel = new Label();
        birdTitleLabel.getStyleClass().add("bird-title-label");
        Label continentsLabel = new Label();

        continentsLabel.setWrapText(true);

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

        topRightBox1.getChildren().addAll(birdTitleLabel,continentsLabel);
        topRightBox2.getChildren().addAll(birdImage);

        //add children to windowFrame
        windowFrame.getItems().addAll(leftBasicGrid, rightBasicGrid);

        //add headerBar
        HeaderUI header = new HeaderUI();

        //create VBox to hold HeaderBar and SplitPane
        VBox rootBox = new VBox(headerBar, windowFrame);

        //display windowFrame
        getChildren().add(rootBox);

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
