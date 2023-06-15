package twittrfx;

import javafx.scene.text.Text;

public abstract class TextUI extends Text {

    public TextUI(String text) {
        super(text);
        applyStyles();
    }

    public TextUI() {
        super();
    }

    protected abstract void applyStyles();
}
