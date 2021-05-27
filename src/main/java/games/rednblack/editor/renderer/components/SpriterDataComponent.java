package games.rednblack.editor.renderer.components;

public class SpriterDataComponent implements BaseComponent {
    public String animationName = "";
    public String currentAnimationName = "";
    public int animation;

    @Override
    public void reset() {
        animationName = "";
        currentAnimationName = "";
        animation = 0;
    }
}
