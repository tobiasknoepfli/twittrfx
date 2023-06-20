package twittrfx;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PresentationModel {

    private final StringProperty applicationTitle = new SimpleStringProperty("JavaFX App");
    private final ObjectProperty<Bird> selectedBird = new SimpleObjectProperty<>();
    private final ObservableList<Bird> birdList = FXCollections.observableArrayList();
    private boolean darkModeEnabled;

    public String getApplicationTitle() {
        return applicationTitle.get();
    }

    public StringProperty applicationTitleProperty() {
        return applicationTitle;
    }

    public void setApplicationTitle(String applicationTitle) {
        this.applicationTitle.set(applicationTitle);
    }

    public Bird getSelectedBird() {
        return selectedBird.get();
    }

    public ObjectProperty<Bird> selectedBirdProperty() {
        return selectedBird;
    }

    public void setSelectedBird(Bird selectedBird) {
        this.selectedBird.set(selectedBird);
    }

    public ObservableList<Bird> getBirdList() {
        return birdList;
    }

    public boolean isDarkModeEnabled() {
        return darkModeEnabled;
    }

    public void setDarkModeEnabled(boolean darkModeEnabled) {
        this.darkModeEnabled = darkModeEnabled;
    }
}
