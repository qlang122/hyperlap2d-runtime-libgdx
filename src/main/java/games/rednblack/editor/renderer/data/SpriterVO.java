package games.rednblack.editor.renderer.data;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import games.rednblack.editor.renderer.components.SpriterDataComponent;

public class SpriterVO extends MainItemVO {
    public String animationName = "";

    public boolean isLooping = false;
    public int currentEntityIndex = 0;
    public String currentAnimationName = "";
    public HashSet<String> animations = new HashSet<>();

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

    public String constructJsonString() {
        String str = "";
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        str = json.prettyPrint(this).replace("\t", "  ");
        return str;
    }

    @Override
    public String toString() {
        return "SpriterVO{" +
                "animationName='" + animationName + '\'' +
                ", isLooping=" + isLooping +
                ", currentEntityIndex=" + currentEntityIndex +
                ", currentAnimationName='" + currentAnimationName + '\'' +
                ", animations='" + animations + '\'' +
                ", actionName='" + actionName + '\'' +
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
