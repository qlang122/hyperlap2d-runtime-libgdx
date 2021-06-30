package games.rednblack.editor.renderer.data;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import java.util.HashMap;

public class SpriterRelationVO {
    public HashMap<String, SpriterVO> animations = new HashMap<>();

    public SpriterRelationVO() {
    }

    public String constructJsonString() {
        String str = "";
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);
        str = json.toJson(this);
        return str;
    }

    @Override
    public String toString() {
        return "SpriterRelationVO{" +
                "animations=" + animations +
                '}';
    }
}
