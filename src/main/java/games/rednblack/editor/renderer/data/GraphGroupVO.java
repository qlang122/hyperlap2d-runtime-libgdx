package games.rednblack.editor.renderer.data;

import java.util.ArrayList;

public class GraphGroupVO {
    public String name = "";
    public ArrayList<String> nodes = new ArrayList<>();

    @Override
    public String toString() {
        return "GraphGroupVO{" +
                "name='" + name + '\'' +
                ", nodes=" + nodes +
                '}';
    }
}
