package games.rednblack.editor.renderer.data;

import com.badlogic.ashley.core.Entity;

import games.rednblack.editor.renderer.components.SpriterDataComponent;

public class SpriterVO extends MainItemVO {
    public String animationName = "";

    public boolean isLooping = false;
    public int currentEntityIndex = 0;
    public String currentAnimationName = "";

    private String actionName = "";

    public SpriterVO() {

    }

    public SpriterVO(SpriterVO vo) {
        super(vo);
        animationName = vo.animationName;
        isLooping = vo.isLooping;
        currentEntityIndex = vo.currentEntityIndex;
        currentAnimationName = vo.currentAnimationName;
    }

    public void setActionName(String name) {
        actionName = name;
    }

    public String getActionName() {
        return actionName;
    }

    @Override
    public void loadFromEntity(Entity entity) {
        super.loadFromEntity(entity);

        SpriterDataComponent spriterComponent = entity.getComponent(SpriterDataComponent.class);
        animationName = spriterComponent.animationName;
        isLooping = spriterComponent.isLooping;
        currentEntityIndex = spriterComponent.currentEntityIndex;
        currentAnimationName = spriterComponent.currentAnimationName;
    }
}
