package games.rednblack.editor.renderer.data;

import com.badlogic.ashley.core.Entity;

import games.rednblack.editor.renderer.components.light.LightObjectComponent;

import java.util.Arrays;
import java.util.Objects;

public class LightVO extends MainItemVO {
    //public int itemId = -1;
    public enum LightType {POINT, CONE}

    public LightType type;
    public int rays = 12;
    public float distance = 300;
    public float directionDegree = 0;
    public float height = 0;
    public float coneDegree = 30;
    public float softnessLength = -1f;
    public float intensity = 1f;
    public boolean isStatic = true;
    public boolean isXRay = true;
    public boolean isSoft = true;
    public boolean isActive = true;

    public LightVO() {
        tint = new float[4];
        tint[0] = 1f;
        tint[1] = 1f;
        tint[2] = 1f;
        tint[3] = 1f;
    }

    public LightVO(LightVO vo) {
        super(vo);
        type = vo.type;
        rays = vo.rays;
        distance = vo.distance;
        directionDegree = vo.directionDegree;
        height = vo.height;
        intensity = vo.intensity;
        coneDegree = vo.coneDegree;
        isStatic = vo.isStatic;
        isXRay = vo.isXRay;
        softnessLength = vo.softnessLength;
        isActive = vo.isActive;
        isSoft = vo.isSoft;
    }

    @Override
    public void loadFromEntity(Entity entity) {
        super.loadFromEntity(entity);

        LightObjectComponent lightObjectComponent = entity.getComponent(LightObjectComponent.class);
        type = lightObjectComponent.getType();
        rays = lightObjectComponent.rays;
        distance = lightObjectComponent.distance;
        directionDegree = lightObjectComponent.directionDegree;
        height = lightObjectComponent.height;
        coneDegree = lightObjectComponent.coneDegree;
        isStatic = lightObjectComponent.isStatic;
        isXRay = lightObjectComponent.isXRay;
        softnessLength = lightObjectComponent.softnessLength;
        isSoft = lightObjectComponent.isSoft;
        isActive = lightObjectComponent.isActive;
        intensity = lightObjectComponent.intensity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LightVO lightVO = (LightVO) o;
        return rays == lightVO.rays &&
                Float.compare(lightVO.distance, distance) == 0 &&
                Float.compare(lightVO.directionDegree, directionDegree) == 0 &&
                Float.compare(lightVO.height, height) == 0 &&
                Float.compare(lightVO.intensity, intensity) == 0 &&
                Float.compare(lightVO.coneDegree, coneDegree) == 0 &&
                Float.compare(lightVO.softnessLength, softnessLength) == 0 &&
                isStatic == lightVO.isStatic &&
                isXRay == lightVO.isXRay &&
                isSoft == lightVO.isSoft &&
                isActive == lightVO.isActive &&
                type == lightVO.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, rays, distance, directionDegree, height, coneDegree, softnessLength, isStatic, isXRay, isSoft, isActive, intensity);
    }

    @Override
    public String toString() {
        return "LightVO{" +
                "type=" + type +
                ", rays=" + rays +
                ", distance=" + distance +
                ", directionDegree=" + directionDegree +
                ", height=" + height +
                ", coneDegree=" + coneDegree +
                ", softnessLength=" + softnessLength +
                ", intensity=" + intensity +
                ", isStatic=" + isStatic +
                ", isXRay=" + isXRay +
                ", isSoft=" + isSoft +
                ", isActive=" + isActive +
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
