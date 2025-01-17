package games.rednblack.editor.renderer.data;

import com.badlogic.ashley.core.Entity;

import java.util.Arrays;

import games.rednblack.editor.renderer.components.DimensionsComponent;
import games.rednblack.editor.renderer.components.NinePatchComponent;

public class Image9patchVO extends MainItemVO {
    public String atlasName = "";
    public String imageName = "";
    public int index = -1;
    public float width = 0;
    public float height = 0;

    public Image9patchVO() {
        super();
    }

    public Image9patchVO(Image9patchVO vo) {
        super(vo);
        atlasName = vo.atlasName;
        imageName = vo.imageName;
        index = -1;
        width = vo.width;
        height = vo.height;
    }

    @Override
    public void loadFromEntity(Entity entity) {
        super.loadFromEntity(entity);

        NinePatchComponent ninePatchComponent = entity.getComponent(NinePatchComponent.class);
        DimensionsComponent dimensionsComponent = entity.getComponent(DimensionsComponent.class);

        atlasName = ninePatchComponent.textureAtlasName;
        imageName = ninePatchComponent.textureRegionName;
        index = ninePatchComponent.index;
        width = dimensionsComponent.width;
        height = dimensionsComponent.height;
    }

    @Override
    public String toString() {
        return "Image9patchVO{" +
                "atlasName='" + atlasName + '\'' +
                ", imageName='" + imageName + '\'' +
                ", index='" + index + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", uniqueId=" + uniqueId +
                ", id='" + id + '\'' +
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
