package games.rednblack.editor.renderer.components;

public class SpriterDataComponent implements BaseComponent {
    public String animationName = "";

    public boolean isLooping = false;
    public int currentEntityIndex = 0;
    public String currentAnimationName = "";

    @Override
    public void reset() {
        animationName = "";
        isLooping = false;
        currentEntityIndex = 0;
        currentAnimationName = "";
    }
}
