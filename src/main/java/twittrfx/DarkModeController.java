package twittrfx;

import javafx.scene.control.ToggleButton;
import twittrfx.PresentationModel;

public class DarkModeController extends ToggleButton {
    private PresentationModel model;

    public DarkModeController(PresentationModel model) {
        this.model = model;
        buttonText();
        setOnAction(event -> {
            toggleDarkMode();
            buttonText(); // Call updateButtonText() after toggling
        });
    }

    private void toggleDarkMode() {
        boolean darkModeEnabled = model.isDarkModeEnabled();
        model.setDarkModeEnabled(!darkModeEnabled);
        buttonText();
        // Additional code to apply stylesheet changes if necessary
    }


    private void buttonText() {
            setText("Dark Mode Disabled");
    }
}
