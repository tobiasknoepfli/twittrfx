package twittrfx;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import twittrfx.TextUI;

public class TitleTextUI extends TextUI {

    public TitleTextUI() {
        super();
    }

    public TitleTextUI(String text) {
        super(text);
    }

    @Override
    protected void applyStyles() {
        getStyleClass().add("title-text");
    }
}
