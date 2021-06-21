package games.rednblack.editor.renderer.components;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import games.rednblack.editor.renderer.commons.RefreshableObject;
import games.rednblack.editor.renderer.utils.ComponentRetriever;
import games.rednblack.editor.renderer.utils.PolygonUtils;
import games.rednblack.editor.renderer.utils.RepeatablePolygonSprite;

public class TextureRegionComponent extends RefreshableObject implements BaseComponent {
    public String textureAtlasName = "";
    public String regionName = "";
    public int index = -1;
    public TextureRegion region = null;
    public boolean isRepeat = false;
    public boolean isPolygon = false;

    // optional
    public RepeatablePolygonSprite repeatablePolygonSprite = null;

    public void setPolygonSprite(PolygonComponent polygonComponent) {
        Vector2[] verticesArray = PolygonUtils.mergeTouchingPolygonsToOne(polygonComponent.vertices);
        float[] vertices = new float[verticesArray.length * 2];
        for (int i = 0; i < verticesArray.length; i++) {
            vertices[i * 2] = verticesArray[i].x;
            vertices[i * 2 + 1] = verticesArray[i].y;
        }

        if (repeatablePolygonSprite == null)
            repeatablePolygonSprite = new RepeatablePolygonSprite();
        repeatablePolygonSprite.clear();
        repeatablePolygonSprite.setVertices(vertices);
        repeatablePolygonSprite.setTextureRegion(region);
    }

    @Override
    public void reset() {
        textureAtlasName = "";
        regionName = "";
        index = -1;
        region = null;
        repeatablePolygonSprite = null;
        isRepeat = false;
        isPolygon = false;
        needsRefresh = false;
    }

    @Override
    protected void refresh(Entity entity) {
        PolygonComponent polygonComponent = ComponentRetriever.get(entity, PolygonComponent.class);

        if (isPolygon && polygonComponent != null) {
            DimensionsComponent dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
            dimensionsComponent.setPolygon(polygonComponent);
            setPolygonSprite(polygonComponent);
        }
    }
}
