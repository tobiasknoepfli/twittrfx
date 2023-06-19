package twittrfx;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static twittrfx.AppStarter.WINDOW_HEIGHT;
import static twittrfx.AppStarter.WINDOW_WIDTH;

public class ApplicationUI extends HBox {

    private final PresentationModel model;
    private Text rowCountText, highestSpeedText;
    private ImageView birdImage;
    public HeaderUI headerBar;
    private SplitPane splitPane;
    private VBox leftPane, rightPane;
    private VBox leftOverview;
    private HBox rightOverview;
    private TitleTextUI leftTitle;
    private GridPane subtitleBox;
    private GridPane rightTitleBox;
    private Label rightBirdTitle;
    private TableUI birdTable;
    private Label rightSubtitleContinents;
    private Image birdImg;
    private GridPane rightTextFieldGrid;
    private List<String> rightTFGridTitle;
    private List<Bird> birdList;
    private List<TextField> birdProperties;
    private List<String> rightTFGridTitleCol3;
    private ArrayList<TextField> birdProperties2;
    private ColumnConstraints col1, col2, col3, col4;
    private Button newBird, saveBird, deleteBird;


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
        headerBar = new HeaderUI();
        newBird = new Button("\uF0C7");
        saveBird = new Button("\uF03C");
        deleteBird = new Button("\uF0CE");

        splitPane = new SplitPane();

        leftPane = new VBox();
        leftOverview = new VBox();
        subtitleBox = new GridPane();

        rightPane = new VBox();
        rightOverview = new HBox();
        rightTitleBox = new GridPane();
        rightBirdTitle = new Label();
        rightSubtitleContinents = new Label();
        rightTextFieldGrid = new GridPane();

        leftTitle = new TitleTextUI("Birds of Switzerland");

        rowCountText = new CategoryTextUI();
        highestSpeedText = new CategoryTextUI();

        birdList = readBirdDataFromTSVFile("/twittrfx/birds_of_switzerland.tsv");
        birdTable = new TableUI(birdList);
        updateRowCountText(birdList);
        updateHighestSpeedText(birdList);

        birdImage = new ImageView();

        rightTFGridTitle = Arrays.asList(
                "Name", "Short Description", "Population Size", "Top Speed",
                "Length", "Continents", "Diet", "Seasonal Behaviour",
                "Population Trend", "Image");
        birdProperties = new ArrayList<>();
        rightTFGridTitleCol3 = Arrays.asList(
                "", null, "Maximum Life Span", "Weight", "Wingspan",
                "Incubation Period", null, "Independent Age", "Population Status");
        birdProperties2 = new ArrayList<>();

        col1 = new ColumnConstraints();
        col2 = new ColumnConstraints();
        col3 = new ColumnConstraints();
        col4 = new ColumnConstraints();
    }

    private void layoutControls() {
        //add buttons to HeaderBar
        newBird.getStyleClass().add("header-button");
        saveBird.getStyleClass().add("header-button2");
        deleteBird.getStyleClass().add("header-button");
        headerBar.getChildren().addAll(newBird, saveBird, deleteBird);

        //Set splitPanePositions
        splitPane.setDividerPositions(0.5);

        //vBox leftPane and rightPane
        leftPane.setPrefSize(WINDOW_WIDTH / 2, WINDOW_HEIGHT * 0.9);
        rightPane.setPrefSize(WINDOW_WIDTH / 2, WINDOW_HEIGHT * 0.9);

        //VBox leftOverview
        leftOverview.setMinHeight(WINDOW_HEIGHT / 8);
        leftOverview.setPadding(new Insets(20, 0, 5, 10));

        //HBox rightOverview
        rightOverview.setMinHeight(WINDOW_HEIGHT / 9 * 4);
        rightOverview.setPadding(new Insets(20, 10, 5, 10));
        rightOverview.setSpacing(10);

        //rightTitleBox
        rightBirdTitle.getStyleClass().add("bird-title-label");
        rightTitleBox.add(rightBirdTitle, 0, 0);
        rightSubtitleContinents.getStyleClass().add("category-text");
        rightSubtitleContinents.setWrapText(true);
        rightSubtitleContinents.setMinWidth(WINDOW_WIDTH / 2 / 3 * 1.5);
        rightTitleBox.add(rightSubtitleContinents, 0, 1);
        birdImage.setFitWidth(WINDOW_WIDTH / 2 / 3 * 1.5);
        birdImage.setPreserveRatio(true);
        rightTitleBox.add(birdImage, 1, 1, 1, 20);

        //rightTextFieldGrid
        rightTextFieldGrid.setPadding(new Insets(10, 10, 5, 10));
        rightTextFieldGrid.setVgap(6);
        rightTextFieldGrid.setHgap(10);
        col1.setPercentWidth(20);
        col2.setPercentWidth(30);
        col3.setPercentWidth(20);
        col4.setPercentWidth(30);
        rightTextFieldGrid.getColumnConstraints().addAll(col1, col2, col3, col4);

        //add right TextFieldGrid 1st Column Labels
        int i = 0;
        for (String s : rightTFGridTitle) {
            rightTextFieldGrid.add(new Label(s), 0, i);
            rightTextFieldGrid.setMinHeight(10);
            getStyleClass().add("table-text");
            i++;
        }
        //add 2nd Column TextFields
        for (i = 0; i < rightTFGridTitle.size(); i++) {
            birdProperties.add(new TextField());
            getStyleClass().add("text-field");
        }
        i = 0;
        for (TextField tf : birdProperties) {
            if (i == 1 || i == 6 || i == 9) {
                GridPane.setColumnSpan(tf, 3);
            }
            rightTextFieldGrid.add(tf, 1, i);
            i++;
        }
        //add right TextFieldGrid 3rd Column Labels
        i = 0;
        for (String r : rightTFGridTitleCol3) {
            if (!(r == null)) {
                rightTextFieldGrid.add(new Label(r), 2, i);
                rightTextFieldGrid.setMinHeight(10);
                getStyleClass().add("table-text");
            }
            i++;
        }
        //add 4th Column TextFields
        for (i = 1; i < rightTFGridTitle.size(); i++) {
            birdProperties2.add(new TextField());
            getStyleClass().add("text-field");
        }

        i = 1;
        for (TextField tf : birdProperties2) {
            rightTextFieldGrid.add(tf, 3, i);
            i++;
        }

        //add left subtitleBox
        subtitleBox.setHgap(10);
        subtitleBox.setPadding(new Insets(5, 0, 20, 0));
        subtitleBox.add(new CategoryTextUI("Amount of bird species:"), 0, 0);
        subtitleBox.add(rowCountText, 1, 0);
        subtitleBox.add(new CategoryTextUI("Highest top speed:"), 0, 1);
        subtitleBox.add(highestSpeedText, 1, 1);

//        COLLECT
        leftOverview.getChildren().addAll(leftTitle, subtitleBox);
        rightOverview.getChildren().add(rightTitleBox);

        //leftPane holds leftOverview and table
        leftPane.getChildren().addAll(leftOverview, birdTable);
        //rightPane holds rightOverview
        rightPane.getChildren().addAll(rightOverview, rightTextFieldGrid);

        //splitPane holds leftPane and rightPane
        splitPane.getItems().addAll(leftPane, rightPane);

        //VBox to hold SplitPane and HeaderBar
        VBox root = new VBox(headerBar, splitPane);

        getChildren().add(root);
    }


    private void setupEventHandlers() {
        newBird.setOnAction(event -> {
            birdTable.getTableView().getSelectionModel().clearSelection();
            clearTextFields();
        });

        saveBird.setOnAction(event -> {
            int selectedIndex = birdTable.getTableView().getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                Bird selectedBird = birdTable.getTableView().getItems().get(selectedIndex);
                String birdName = selectedBird.getName();

                // Update the bird object with the changes
                selectedBird.setName(birdProperties.get(0).getText());
                selectedBird.setShortDescription(birdProperties.get(1).getText());
                selectedBird.setPopulationSize(birdProperties.get(2).getText());
                selectedBird.setTopSpeedInKmh(birdProperties.get(3).getText());
                selectedBird.setLength(birdProperties.get(4).getText());
                selectedBird.setContinents(birdProperties.get(5).getText());
                selectedBird.setDiet(birdProperties.get(6).getText());
                selectedBird.setSeasonalBehavior(birdProperties.get(7).getText());
                selectedBird.setPopulationTrend(birdProperties.get(8).getText());
                selectedBird.setImage(birdProperties.get(9).getText());
                selectedBird.setMaximumLifeSpanInYears(birdProperties2.get(1).getText());
                selectedBird.setWeight(birdProperties2.get(2).getText());
                selectedBird.setWingspan(birdProperties2.get(3).getText());
                selectedBird.setIncubationPeriod(birdProperties2.get(4).getText());
                selectedBird.setIndependentAge(birdProperties2.get(6).getText());
                selectedBird.setPopulationStatus(birdProperties2.get(7).getText());

                // Update the table view
                birdTable.getTableView().getItems().set(selectedIndex, selectedBird);

                // Save changes to the TSV file
                saveBirdDataToTSVFile("/twittrfx/birds_of_switzerland.tsv");

                // Clear the text fields and selection
                clearTextFields();
                birdTable.getTableView().getSelectionModel().clearSelection();
            }
        });

    }

    private void setupValueChangedListeners() {
    }

    private void setupBindings() {
        //setup bindings for rightOverview
        birdTable.getTableView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                rightBirdTitle.setText(newValue.getName());
                rightSubtitleContinents.setText(newValue.getContinents());
                birdImg = new Image(newValue.getImage());
                birdImage.setImage(birdImg);
            } else {
                rightBirdTitle.setText("");
                rightSubtitleContinents.setText("");
                birdImage.setImage(null);
            }
        });

        //setup bindings for 2nd Column
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

        //setup bindings for 4th Column
        birdTable.getTableView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                birdProperties2.get(0).setText(null);
                birdProperties2.get(1).setText(newValue.getMaximumLifeSpanInYears());
                birdProperties2.get(2).setText(newValue.getWeight());
                birdProperties2.get(3).setText(newValue.getWingspan());
                birdProperties2.get(4).setText(newValue.getIncubationPeriod());
                birdProperties2.get(5).setText(null);
                birdProperties2.get(6).setText(newValue.getIndependentAge());
                birdProperties2.get(7).setText(newValue.getPopulationStatus());
                birdProperties2.get(8).setText(null);
            }
        });
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

    private void clearTextFields() {
        for (TextField tf : birdProperties) {
            tf.clear();
        }
        for (TextField tf : birdProperties2) {
            tf.clear();
        }
        if (!birdProperties.isEmpty()) {
            birdProperties.get(0).requestFocus();
        }
    }

    private void saveBirdDataToTSVFile(String filePath) {
        try {
            File file = new File(getClass().getResource(filePath).toURI());

            // Read the existing TSV file content
            String existingContent = new String(Files.readAllBytes(Paths.get(file.toURI())));

            // Generate the new TSV content with updated bird data
            StringBuilder newContentBuilder = new StringBuilder();

            // Append the header line
            newContentBuilder.append("Name\tImage\tShort Description\tPopulation Size\tMaximum Life Span\tTop Speed\tWeight\tLength\tWingspan\tContinents\tDiet\tSeasonal Behaviour\tIndependent Age\tPopulation Trend\tPopulation Status\tIncubation Period");
            newContentBuilder.append(System.lineSeparator());

            // Append the bird data
            for (Bird bird : birdList) {
                if (bird.getName().equals(getBirdName())) {
                    newContentBuilder.append(bird.getName()).append("\t");
                    newContentBuilder.append(bird.getImage()).append("\t");
                    newContentBuilder.append(bird.getShortDescription()).append("\t");
                    newContentBuilder.append(bird.getPopulationSize()).append("\t");
                    newContentBuilder.append(bird.getMaximumLifeSpanInYears()).append("\t");
                    newContentBuilder.append(bird.getTopSpeedInKmh()).append("\t");
                    newContentBuilder.append(bird.getWeight()).append("\t");
                    newContentBuilder.append(bird.getLength()).append("\t");
                    newContentBuilder.append(bird.getWingspan()).append("\t");
                    newContentBuilder.append(bird.getContinents()).append("\t");
                    newContentBuilder.append(bird.getDiet()).append("\t");
                    newContentBuilder.append(bird.getSeasonalBehavior()).append("\t");
                    newContentBuilder.append(bird.getIndependentAge()).append("\t");
                    newContentBuilder.append(bird.getPopulationTrend()).append("\t");
                    newContentBuilder.append(bird.getPopulationStatus()).append("\t");
                    newContentBuilder.append(bird.getIncubationPeriod());
                } else {
                    newContentBuilder.append(bird.toTSVString()); // Append the original bird data
                }
                newContentBuilder.append(System.lineSeparator());
            }

            // Write the new content to the TSV file
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(newContentBuilder.toString());
            writer.close();
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private String getBirdName(){
        String birdName = rightBirdTitle.getText();
        return birdName;
    }
    private String getChanges() {
        String changedName = birdProperties.get(0).getText();
        String changedDescription = birdProperties.get(1).getText();
        String changedPopSize = birdProperties.get(2).getText();
        String changedTopSpeed = birdProperties.get(3).getText();
        String changedLength = birdProperties.get(4).getText();
        String changedContinents = birdProperties.get(5).getText();
        String changedDiet = birdProperties.get(6).getText();
        String changedSeasBehaviour = birdProperties.get(7).getText();
        String changedPopTrend = birdProperties.get(8).getText();
        String changedImage = birdProperties.get(9).getText();

        String changedLifeSpan = birdProperties2.get(1).getText();
        String changedWeight = birdProperties2.get(2).getText();
        String changedWingspan = birdProperties2.get(3).getText();
        String changedIncPeriod = birdProperties2.get(4).getText();
        String changedIndAge = birdProperties2.get(6).getText();
        String changedPopStatus = birdProperties2.get(7).getText();

        return changedName + "\t" + changedImage + "\t" + changedDescription + "\t" + changedPopSize + "\t" +
                changedLifeSpan + "\t" + changedTopSpeed+ "\t" + changedWeight+ "\t" +changedLength+ "\t" +
                changedWingspan+ "\t" +changedContinents+ "\t" +changedDiet+ "\t" +changedSeasBehaviour+ "\t" +
                changedIndAge+ "\t" +changedPopTrend + "\t" + changedPopStatus+ "\t" +changedIncPeriod;
    }
}
