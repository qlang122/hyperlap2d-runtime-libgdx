package games.rednblack.editor.renderer.data;

import com.badlogic.ashley.core.Entity;

import games.rednblack.editor.renderer.components.SpriterDataComponent;

public class SpriterVO extends MainItemVO {
    public String animationName = "";

    public int entity = 0;
    public String currentAnimationName = "";

    public SpriterVO() {

    }

    public SpriterVO(SpriterVO vo) {
        super(vo);
        entity = vo.entity;
        animationName = vo.animationName;
        currentAnimationName = vo.currentAnimationName;
    }

    @Override
    public void loadFromEntity(Entity entity) {
        super.loadFromEntity(entity);

        SpriterDataComponent spriterComponent = entity.getComponent(SpriterDataComponent.class);
        animationName = spriterComponent.animationName;
        currentAnimationName = spriterComponent.currentAnimationName;
    }
}
