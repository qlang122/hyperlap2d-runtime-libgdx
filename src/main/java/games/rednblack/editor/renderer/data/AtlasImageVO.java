package games.rednblack.editor.renderer.data;

import com.badlogic.ashley.core.Entity;

import java.util.Arrays;

import games.rednblack.editor.renderer.components.TextureRegionComponent;

public class AtlasImageVO extends MainItemVO {
    public String atlasName = "";
    public String imageName = "";
    public boolean isRepeat = false;
    public boolean isPolygon = false;

    public AtlasImageVO() {
        super();
    }

    public AtlasImageVO(AtlasImageVO vo) {
        super(vo);
        atlasName = vo.atlasName;
        imageName = vo.imageName;
        isRepeat = vo.isRepeat;
        isPolygon = vo.isPolygon;
    }

    @Override
    public void loadFromEntity(Entity entity) {
        super.loadFromEntity(entity);

        TextureRegionComponent textureRegionComponent = entity.getComponent(TextureRegionComponent.class);

        atlasName = textureRegionComponent.textureAtlasName;
        imageName = textureRegionComponent.regionName;
        isRepeat = textureRegionComponent.isRepeat;
        isPolygon = textureRegionComponent.isPolygon;
    }

    @Override
    public String toString() {
        return "AtlasImageVO{" +
                "atlasName='" + atlasName + '\'' +
                ", imageName='" + imageName + '\'' +
                ", isRepeat=" + isRepeat +
                ", isPolygon=" + isPolygon +
                ", uniqueId=" + uniqueId +
                ", itemIdentifier='" + itemIdentifier + '\'' +
                ", itemName='" + itemName + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", customVars='" + customVars + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", scaleX=" + scaleX +
                ", scaleY=" + scaleY +
                ", originX=" + originX +
                ", originY=" + originY +
                ", rotation=" + rotation +
                ", zIndex=" + zIndex +
                ", layerName='" + layerName + '\'' +
                ", tint=" + Arrays.toString(tint) +
                ", flipX=" + flipX +
                ", flipY=" + flipY +
                ", visible=" + visible +
                ", shaderName='" + shaderName + '\'' +
                ", shaderUniforms=" + shaderUniforms +
                ", renderingLayer=" + renderingLayer +
                ", shape=" + shape +
                ", physics=" + physics +
                ", light=" + light +
                '}';
    }
}
