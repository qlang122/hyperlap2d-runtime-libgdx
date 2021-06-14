package games.rednblack.editor.renderer.data;

import com.badlogic.ashley.core.Entity;

import java.util.Arrays;

import games.rednblack.editor.renderer.components.particle.ParticleComponent;

public class ParticleEffectVO extends MainItemVO {
    public String particleName = "";
    public boolean transform = true;

    public ParticleEffectVO() {
        super();
    }

    public ParticleEffectVO(ParticleEffectVO vo) {
        super(vo);
        particleName = vo.particleName;
        transform = vo.transform;
    }

    @Override
    public void loadFromEntity(Entity entity) {
        super.loadFromEntity(entity);

        ParticleComponent particleComponent = entity.getComponent(ParticleComponent.class);
        particleName = particleComponent.particleName;
        transform = particleComponent.transform;
    }

    @Override
    public String toString() {
        return "ParticleEffectVO{" +
                "particleName='" + particleName + '\'' +
                ", transform=" + transform +
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
