package games.rednblack.editor.renderer.data;

import games.rednblack.editor.renderer.utils.CustomVariables;

import java.util.Arrays;
import java.util.Map;

/**
 * Created by Osman on 20.08.2015.
 *
 */
public class CoreActorData {
    public String id = null;
    public String[] tags = null;
    public int layerIndex = 0;
    public CustomVariables customVars = null;

    @Override
    public String toString() {
        return "CoreActorData{" +
                "id='" + id + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", layerIndex=" + layerIndex +
                ", customVars=" + customVars +
                '}';
    }
}
