package twittrfx;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static twittrfx.AppStarter.WINDOW_HEIGHT;
import static twittrfx.AppStarter.WINDOW_WIDTH;

public class ApplicationUI extends HBox {

    private final PresentationModel model;
    private Label rowCountText, highestSpeedText;
    private ImageView birdImage;
    public HeaderUI headerBar;
    private SplitPane splitPane;
    private VBox leftPane, rightPane;
    private VBox leftOverview;
    private HBox rightOverview;
    private Label leftTitle;
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
    private String originalName;
    private DarkModeController toggleDarkMode;
    private BorderPane headerPane;
    private HBox buttonBox;
    private String stylesheet;

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
        getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    }


    private void initializeControls() {
        headerBar = new HeaderUI();
        headerPane = new BorderPane();
        buttonBox = new HBox();

        newBird = new Button("\uF0C7");
        saveBird = new Button("\uF03C");
        deleteBird = new Button("\uF0CE");
        toggleDarkMode = new DarkModeController(model);

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

        leftTitle = new Label("Birds of Switzerland");
        leftTitle.getStyleClass().add("bird-title-label");

        rowCountText = new Label();
        highestSpeedText = new Label();

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
        toggleDarkMode.getStyleClass().add("toggle-button");

        buttonBox.getChildren().addAll(newBird, saveBird, deleteBird);

        //add BorderPane to HeaderBar
        headerPane.setMinWidth(WINDOW_WIDTH * 0.95);
        headerPane.setLeft(buttonBox);
        headerPane.setRight(toggleDarkMode);

        headerBar.getChildren().add(headerPane);

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
        for (i = 0; i < rightTFGridTitle.size(); i++) {
            birdProperties2.add(new TextField());
            getStyleClass().add("text-field");
        }

        i = 0;
        for (TextField tf : birdProperties2) {
            if (!(i == 0 || i == 1 || i == 6 || i == 9)) {
                rightTextFieldGrid.add(tf, 3, i);
            }
            i++;
        }

        //add left subtitleBox
        subtitleBox.setHgap(10);
        subtitleBox.setPadding(new Insets(5, 0, 20, 0));
        subtitleBox.add(new Label("Amount of bird species:"), 0, 0);
        subtitleBox.add(rowCountText, 1, 0);
        subtitleBox.add(new Label("Highest top speed:"), 0, 1);
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

    private void setupValueChangedListeners() {
    }

    private void setupBindings() {
        //setup bindings for rightOverview
        birdTable.getTableView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                rightBirdTitle.setText(newValue.getName());
                rightSubtitleContinents.setText(newValue.getContinents());
                try {
                    birdImg = new Image(newValue.getImage());
                    birdImage.setImage(birdImg);
                } catch (Exception e) {
                    birdImage.setImage(null);
                }
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
            originalName = new String(birdProperties.get(0).getText());
        });

        //setup bindings for 4th Column
        birdTable.getTableView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                birdProperties2.get(0).setText(null);
                birdProperties2.get(1).setText(null);
                birdProperties2.get(2).setText(newValue.getMaximumLifeSpanInYears());
                birdProperties2.get(3).setText(newValue.getWeight());
                birdProperties2.get(4).setText(newValue.getWingspan());
                birdProperties2.get(5).setText(newValue.getIncubationPeriod());
                birdProperties2.get(6).setText(null);
                birdProperties2.get(7).setText(newValue.getIndependentAge());
                birdProperties2.get(8).setText(newValue.getPopulationStatus());
                birdProperties2.get(9).setText(null);
            }
        });
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

    private void setupEventHandlers() {
        newBird.setOnAction(event -> {
            birdTable.getTableView().getSelectionModel().clearSelection();
            clearTextFields();
        });

        saveBird.setOnAction(event -> {
            int index = birdTable.getTableView().getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                Bird selectedBird = birdList.get(index);
                Bird updatedBird = updatedBird();

                selectedBird.setName(updatedBird.getName());
                selectedBird.setPopulationTrend(updatedBird.getPopulationTrend());
                selectedBird.setPopulationStatus(updatedBird.getPopulationStatus());

                saveUpdatedBirdToTSV(updatedBird, "target/classes/twittrfx/birds_of_switzerland.tsv", originalName);

                birdTable.refresh(birdList);
                clearTextFields();
            } else {
                Bird newBird = createNewBird();
                addBirdToTSV(newBird, "target/classes/twittrfx/birds_of_switzerland.tsv");

                birdList.add(newBird);
                birdTable.refresh(birdList);
                clearTextFields();
            }
        });

        deleteBird.setOnAction(event -> {
            int index = birdTable.getTableView().getSelectionModel().getSelectedIndex();
            if (index >= 0) {
                Bird selectedBird = birdList.get(index);
                deleteBirdFromTSV(selectedBird, "target/classes/twittrfx/birds_of_switzerland.tsv");
                birdList.remove(index);
                birdTable.refresh(birdList);
                clearTextFields();
            }
        });

        toggleDarkMode.setOnAction(event -> {
            if (toggleDarkMode.isSelected()) {
                getStylesheets().clear();
                getStylesheets().add(getClass().getResource("dark-mode.css").toExternalForm());
                toggleDarkMode.setText("Dark Mode Enabled");
            } else {
                getStylesheets().clear();
                getStylesheets().add(getClass().getResource("style.css").toExternalForm());
                toggleDarkMode.setText("Dark Mode Disabled");
            }
        });

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

    private Bird updatedBird() {
        Bird updatedBird = new Bird(
                birdProperties.get(0).getText(), // Name
                birdProperties.get(9).getText(), // Image

                birdProperties.get(1).getText(), // Short Description
                birdProperties.get(2).getText(), // Population Size
                birdProperties2.get(2).getText(), // Maximum Lifespan

                birdProperties.get(3).getText(), // Top Speed
                birdProperties2.get(3).getText(), // Weight

                birdProperties.get(4).getText(), // Length
                birdProperties2.get(4).getText(), // Wingspan

                birdProperties.get(5).getText(), // Continents
                birdProperties2.get(5).getText(), // Diet

                birdProperties.get(6).getText(), // Seasonal Behavior
                birdProperties2.get(7).getText(), // Independent Age

                birdProperties.get(8).getText(), // Population Trend
                birdProperties2.get(8).getText(), // Population Status

                birdProperties2.get(7).getText()  // Incubation Period
        );
        return updatedBird;
    }

    private Bird createNewBird() {
        Bird newBird = new Bird(
                birdProperties.get(0).getText(), // Name
                birdProperties.get(9).getText(), // Image

                birdProperties.get(1).getText(), // Short Description
                birdProperties.get(2).getText(), // Population Size
                birdProperties2.get(2).getText(), // Maximum Lifespan

                birdProperties.get(3).getText(), // Top Speed
                birdProperties2.get(3).getText(), // Weight

                birdProperties.get(4).getText(), // Length
                birdProperties2.get(4).getText(), // Wingspan

                birdProperties.get(5).getText(), // Continents
                birdProperties2.get(5).getText(), // Diet

                birdProperties.get(6).getText(), // Seasonal Behavior
                birdProperties2.get(7).getText(), // Independent Age

                birdProperties.get(8).getText(), // Population Trend
                birdProperties2.get(8).getText(), // Population Status
                ""
        );

        return newBird;
    }

    private void addBirdToTSV(Bird bird, String filePath) {
        try {
            OutputStream outputStream = new FileOutputStream(filePath, true);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            writer.write(bird.toTSVString());
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveUpdatedBirdToTSV(Bird bird, String filePath, String originalName) {
        try {
            Path sourceFile = Paths.get(filePath);

            List<String> lines = Files.readAllLines(sourceFile, StandardCharsets.UTF_8);
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                String[] parts = line.split("\t");
                if (parts.length > 0 && parts[0].equals(originalName)) {
                    updatedLines.add(bird.toTSVString());
                } else {
                    updatedLines.add(line);
                }
            }

            Files.write(sourceFile, updatedLines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteBirdFromTSV(Bird bird, String filePath) {
        try {
            Path sourceFile = Paths.get(filePath);

            List<String> lines = Files.readAllLines(sourceFile, StandardCharsets.UTF_8);
            List<String> updatedLines = new ArrayList<>();

            for (String line : lines) {
                String[] parts = line.split("\t");
                if (parts.length > 0 && !parts[0].equals(bird.getName())) {
                    updatedLines.add(line);
                }
            }

            Files.write(sourceFile, updatedLines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



