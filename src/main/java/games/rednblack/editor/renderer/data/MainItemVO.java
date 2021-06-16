package games.rednblack.editor.renderer.data;

import com.badlogic.ashley.core.Entity;

import games.rednblack.editor.renderer.components.*;
import games.rednblack.editor.renderer.components.light.LightBodyComponent;
import games.rednblack.editor.renderer.components.physics.PhysicsBodyComponent;

import java.util.Arrays;
import java.util.HashMap;

public class MainItemVO {
    public enum RenderingLayer {SCREEN, SCREEN_READING}

    public int uniqueId = -1;
    public String id = "";
    public String itemName = "";
    public String[] tags = null;
    public String customVars = "";
    public float x;
    public float y;
    public float scaleX = 1f;
    public float scaleY = 1f;
    public float originX = Float.NaN;
    public float originY = Float.NaN;
    public float rotation;
    public int zIndex = 0;
    public String layerName = "";
    public float[] tint = {1, 1, 1, 1};
    public boolean flipX = false;
    public boolean flipY = false;
    public boolean visible = true;

    public String shaderName = "";
    public HashMap<String, ShaderUniformVO> shaderUniforms = new HashMap<>();
    public RenderingLayer renderingLayer = RenderingLayer.SCREEN;

    public ShapeVO shape = null;
    public PhysicsBodyDataVO physics = null;
    public LightBodyDataVO light = null;

    public MainItemVO() {

    }

    public MainItemVO(MainItemVO vo) {
        uniqueId = vo.uniqueId;
        id = vo.id;
        itemName = vo.itemName;
        if (vo.tags != null) tags = Arrays.copyOf(vo.tags, vo.tags.length);
        customVars = vo.customVars;
        x = vo.x;
        y = vo.y;
        rotation = vo.rotation;
        zIndex = vo.zIndex;
        layerName = vo.layerName;
        if (vo.tint != null) tint = Arrays.copyOf(vo.tint, vo.tint.length);
        scaleX = vo.scaleX;
        scaleY = vo.scaleY;
        originX = vo.originX;
        originY = vo.originY;
        flipX = vo.flipX;
        flipY = vo.flipY;
        visible = vo.visible;

        if (vo.shape != null) {
            shape = vo.shape.clone();
        }

        if (vo.physics != null) {
            physics = new PhysicsBodyDataVO(vo.physics);
        }

        if (vo.light != null) {
            light = new LightBodyDataVO(vo.light);
        }

        shaderName = vo.shaderName;
        shaderUniforms.clear();
        shaderUniforms.putAll(vo.shaderUniforms);

        renderingLayer = vo.renderingLayer;
    }

    public void loadFromEntity(Entity entity) {
        MainItemComponent mainItemComponent = entity.getComponent(MainItemComponent.class);
        TransformComponent transformComponent = entity.getComponent(TransformComponent.class);
        transformComponent = transformComponent.getRealComponent();
        TintComponent tintComponent = entity.getComponent(TintComponent.class);
        ZIndexComponent zindexComponent = entity.getComponent(ZIndexComponent.class);

        uniqueId = mainItemComponent.uniqueId;
        id = mainItemComponent.id;
        itemName = mainItemComponent.libraryLink;
        tags = new String[mainItemComponent.tags.size()];
        tags = mainItemComponent.tags.toArray(tags);
        customVars = mainItemComponent.getCustomVarString();
        visible = mainItemComponent.visible;

        x = transformComponent.x;
        y = transformComponent.y;
        scaleX = transformComponent.scaleX;
        scaleY = transformComponent.scaleY;
        originX = transformComponent.originX;
        originY = transformComponent.originY;
        rotation = transformComponent.rotation;

        flipX = transformComponent.flipX;
        flipY = transformComponent.flipY;

        layerName = zindexComponent.layerName;

        tint = new float[4];
        tint[0] = tintComponent.color.r;
        tint[1] = tintComponent.color.g;
        tint[2] = tintComponent.color.b;
        tint[3] = tintComponent.color.a;

        zIndex = zindexComponent.getZIndex();

        /**
         * Secondary components
         */
        PolygonComponent polygonComponent = entity.getComponent(PolygonComponent.class);
        if (polygonComponent != null && polygonComponent.vertices != null) {
            shape = new ShapeVO();
            shape.polygons = polygonComponent.vertices;
        }
        PhysicsBodyComponent physicsComponent = entity.getComponent(PhysicsBodyComponent.class);
        if (physicsComponent != null) {
            physics = new PhysicsBodyDataVO();
            physics.loadFromComponent(physicsComponent);
        }

        LightBodyComponent lightBodyComponent = entity.getComponent(LightBodyComponent.class);
        if (lightBodyComponent != null) {
            light = new LightBodyDataVO();
            light.loadFromComponent(lightBodyComponent);
        }

        ShaderComponent shaderComponent = entity.getComponent(ShaderComponent.class);
        if (shaderComponent != null && shaderComponent.shaderName != null) {
            shaderName = shaderComponent.shaderName;
            shaderUniforms.clear();
            shaderUniforms.putAll(shaderComponent.customUniforms);
            renderingLayer = shaderComponent.renderingLayer;
        }
    }

    @Override
    public String toString() {
        return "MainItemVO{" +
                "uniqueId=" + uniqueId +
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
