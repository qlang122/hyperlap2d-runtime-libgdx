package games.rednblack.editor.renderer.data;

import java.util.HashMap;

public class GraphNodeVO {
    public String id = "";
    public String type = "";
    public float x, y;
    public HashMap<String, String> data = new HashMap<>();

    @Override
    public String toString() {
        return "GraphNodeVO{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", data=" + data +
                '}';
    }
}
