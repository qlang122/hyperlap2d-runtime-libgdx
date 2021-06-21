package games.rednblack.editor.renderer.components;

import com.badlogic.gdx.graphics.g2d.NinePatch;

public class NinePatchComponent implements BaseComponent {
    public String textureAtlasName = "";
    public String textureRegionName = "";
    public int index = -1;
    public NinePatch ninePatch;

    @Override
    public void reset() {
        textureAtlasName = "";
        textureRegionName = "";
        index = -1;
        ninePatch = null;
    }
}
