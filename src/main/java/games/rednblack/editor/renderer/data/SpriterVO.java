package games.rednblack.editor.renderer.data;

import com.badlogic.ashley.core.Entity;

import games.rednblack.editor.renderer.components.SpriterDataComponent;

public class SpriterVO extends MainItemVO {

    public int 	entity;
    public int 	animation;
    public String animationName = "";

    //wtf is this?
    public float scale	=	1f;

    public SpriterVO() {

    }

    public SpriterVO( SpriterVO vo) {
        super(vo);
        entity 			= vo.entity;
        animation		= vo.animation;
        animationName 	= vo.animationName;
        scale 			= vo.scale;
    }

    @Override
    public void loadFromEntity(Entity entity) {
        super.loadFromEntity(entity);

        SpriterDataComponent spriterComponent = entity.getComponent(SpriterDataComponent.class);
        animationName = spriterComponent.animationName;
        animation = spriterComponent.animation;
    }

}
