package games.rednblack.editor.renderer.components;

import com.badlogic.gdx.utils.ObjectSet;
import games.rednblack.editor.renderer.utils.CustomVariables;

public class MainItemComponent implements BaseComponent {
    public int uniqueId = 0;
	public String id = "";
	public String libraryLink = "";
    public ObjectSet<String> tags = new ObjectSet<>();
    private String customVars = "";
    public CustomVariables customVariables = new CustomVariables();
	public int entityType;
	public boolean visible = true;
	public boolean culled = false;

	public void setCustomVars(String key, String value) {
		customVariables.setVariable(key, value);
		setCustomVarString(customVariables.saveAsString());
	}

	public void removeCustomVars(String key) {
		customVariables.removeVariable(key);
		setCustomVarString(customVariables.saveAsString());
	}

	public String getCustomVarString() {
		return customVars;
	}

	public void setCustomVarString(String vars) {
		customVars = vars;
		if (customVariables.getCount() == 0) {
			customVariables.loadFromString(customVars);
		}
	}

	@Override
	public void reset() {
		uniqueId = 0;
		id = "";
		libraryLink = "";
		tags.clear();
		customVars = "";
		customVariables.clear();
		entityType = 0;
		visible = true;
		culled = false;
	}
}
