package games.rednblack.editor.renderer.data;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import games.rednblack.editor.renderer.components.CompositeTransformComponent;
import games.rednblack.editor.renderer.components.DimensionsComponent;
import games.rednblack.editor.renderer.utils.ComponentRetriever;

import java.util.ArrayList;
import java.util.Arrays;

public class CompositeItemVO extends MainItemVO {

	public CompositeVO composite;

	public float width;
	public float height;
	public boolean automaticResize = true;
	public boolean scissorsEnabled = false;
	public boolean renderToFBO = false;
	
	public CompositeItemVO() {
		composite = new CompositeVO();
	}
	
	public CompositeItemVO(CompositeVO vo) {
		composite = new CompositeVO(vo);
	}
	
	public CompositeItemVO(CompositeItemVO vo) {
		super(vo);
		composite = new CompositeVO(vo.composite);
	}
	
	public void update(CompositeItemVO vo) {
		composite = new CompositeVO(vo.composite);
	}
	
	public CompositeItemVO clone() {
		/*CompositeItemVO tmp = new CompositeItemVO();
		tmp.composite = composite;
        tmp.itemName = itemName;
        tmp.layerName = layerName;
        tmp.rotation = rotation;
        tmp.tint = tint;
        tmp.x = x;
        tmp.y = y;
        tmp.zIndex = zIndex;

		tmp.width = width;
		tmp.height = height;*/
		Json json = new Json(JsonWriter.OutputType.json);

		return json.fromJson(CompositeItemVO.class, json.toJson(this));
	}

	@Override
	public void loadFromEntity(Entity entity) {
		super.loadFromEntity(entity);

		composite = new CompositeVO();
		composite.loadFromEntity(entity);

		DimensionsComponent dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
		CompositeTransformComponent compositeTransformComponent = ComponentRetriever.get(entity, CompositeTransformComponent.class);

		width = dimensionsComponent.width;
		height = dimensionsComponent.height;
		automaticResize = compositeTransformComponent.automaticResize;
		scissorsEnabled = compositeTransformComponent.scissorsEnabled;
		renderToFBO = compositeTransformComponent.renderToFBO;
	}

	public void cleanIds() {
		uniqueId = -1;
		ArrayList<MainItemVO> items = composite.getAllItems();
		for(MainItemVO subItem: items) {
			subItem.uniqueId = -1;
		}
	}

	@Override
	public String toString() {
		return "CompositeItemVO{" +
				"composite=" + composite +
				", width=" + width +
				", height=" + height +
				", automaticResize=" + automaticResize +
				", scissorsEnabled=" + scissorsEnabled +
				", renderToFBO=" + renderToFBO +
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
