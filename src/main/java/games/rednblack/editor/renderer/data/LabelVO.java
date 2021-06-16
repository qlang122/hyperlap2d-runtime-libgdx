package games.rednblack.editor.renderer.data;

import com.badlogic.ashley.core.Entity;

import java.util.Arrays;

import games.rednblack.editor.renderer.components.DimensionsComponent;
import games.rednblack.editor.renderer.components.label.LabelComponent;
import games.rednblack.editor.renderer.components.label.TypingLabelComponent;

public class LabelVO extends MainItemVO {

    public String text = "Label";
    public String style = "";
    public int size;
    public int align;

    public float width = 0;
    public float height = 0;

    public boolean wrap = false;
    public boolean isTyping = false;

    public LabelVO() {
        super();
    }

    public LabelVO(LabelVO vo) {
        super(vo);
        text = new String(vo.text);
        style = new String(vo.style);
        size = vo.size;
        align = vo.align;
        width = vo.width;
        height = vo.height;
        wrap = vo.wrap;
        isTyping = vo.isTyping;
    }

    @Override
    public void loadFromEntity(Entity entity) {
        super.loadFromEntity(entity);
        LabelComponent labelComponent = entity.getComponent(LabelComponent.class);
        DimensionsComponent dimensionsComponent = entity.getComponent(DimensionsComponent.class);
        TypingLabelComponent typingLabelComponent = entity.getComponent(TypingLabelComponent.class);

        text = labelComponent.getText().toString();
        style = labelComponent.fontName;
        size = labelComponent.fontSize;
        align = labelComponent.labelAlign;
        wrap = labelComponent.wrap;

        isTyping = typingLabelComponent != null;

        width = dimensionsComponent.width;
        height = dimensionsComponent.height;
    }

    @Override
    public String toString() {
        return "LabelVO{" +
                "text='" + text + '\'' +
                ", style='" + style + '\'' +
                ", size=" + size +
                ", align=" + align +
                ", width=" + width +
                ", height=" + height +
                ", wrap=" + wrap +
                ", isTyping=" + isTyping +
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
