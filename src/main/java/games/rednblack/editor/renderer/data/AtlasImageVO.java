package games.rednblack.editor.renderer.data;

import com.badlogic.ashley.core.Entity;

import games.rednblack.editor.renderer.components.TextureRegionComponent;

public class AtlasImageVO extends MainItemVO {
    public String imageName = "";
    public boolean isRepeat = false;
    public boolean isPolygon = false;

    public AtlasImageVO() {
        super();
    }

    public AtlasImageVO(AtlasImageVO vo) {
        super(vo);
        imageName = vo.imageName;
        isRepeat = vo.isRepeat;
        isPolygon = vo.isPolygon;
    }

    @Override
    public void loadFromEntity(Entity entity) {
        super.loadFromEntity(entity);

        TextureRegionComponent textureRegionComponent = entity.getComponent(TextureRegionComponent.class);
        loadFromComponent(textureRegionComponent);
    }

    public void loadFromComponent(TextureRegionComponent textureRegionComponent) {
        imageName = textureRegionComponent.regionName;
        isRepeat = textureRegionComponent.isRepeat;
        isPolygon = textureRegionComponent.isPolygon;
    }
}
