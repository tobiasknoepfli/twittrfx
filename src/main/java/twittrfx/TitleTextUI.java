package twittrfx;

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
