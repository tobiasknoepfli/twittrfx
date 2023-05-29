package twittrfx;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PresentationModel {

  private final StringProperty applicationTitle = new SimpleStringProperty("JavaFX App");
  private final ObjectProperty<BirdData> selectedBird = new SimpleObjectProperty<>();
  private final ObservableList<BirdData> birdList = FXCollections.observableArrayList();

  public String getApplicationTitle() {
    return applicationTitle.get();
  }

  public StringProperty applicationTitleProperty() {
    return applicationTitle;
  }

  public void setApplicationTitle(String applicationTitle) {
    this.applicationTitle.set(applicationTitle);
  }

  public BirdData getSelectedBird() {
    return selectedBird.get();
  }

  public ObjectProperty<BirdData> selectedBirdProperty() {
    return selectedBird;
  }

  public void setSelectedBird(BirdData selectedBird) {
    this.selectedBird.set(selectedBird);
  }

  public ObservableList<BirdData> getBirdList() {
    return birdList;
  }
}
