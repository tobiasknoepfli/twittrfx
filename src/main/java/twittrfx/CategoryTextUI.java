package twittrfx;

public class CategoryTextUI extends TextUI {

    public CategoryTextUI() {
        super();
    }

    public CategoryTextUI(String text) {
        super(text);
    }

    @Override
    protected void applyStyles() {
        getStyleClass().add("category-text");
    }
}
