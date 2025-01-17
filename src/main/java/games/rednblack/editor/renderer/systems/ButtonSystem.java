package games.rednblack.editor.renderer.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

import games.rednblack.editor.renderer.components.*;
import games.rednblack.editor.renderer.components.additional.ButtonComponent;
import games.rednblack.editor.renderer.utils.ComponentRetriever;
import games.rednblack.editor.renderer.utils.TransformMathUtils;

/**
 * Created by azakhary on 8/1/2015.
 */
public class ButtonSystem extends IteratingSystem {

    public ButtonSystem() {
        super(Family.all(ButtonComponent.class).get());
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        NodeComponent nodeComponent = ComponentRetriever.get(entity, NodeComponent.class);

        if (nodeComponent == null) return;

        for (int i = 0; i < nodeComponent.children.size; i++) {
            Entity childEntity = nodeComponent.children.get(i);
            MainItemComponent childMainItemComponent = ComponentRetriever.get(childEntity, MainItemComponent.class);
            childMainItemComponent.visible = true;
        }

        ViewPortComponent camera = ComponentRetriever.get(entity, ViewPortComponent.class);
        if (camera != null) {
            // if camera is on this entity, then it should not be processed
            return;
        }

        for (int i = 0; i < nodeComponent.children.size; i++) {
            Entity childEntity = nodeComponent.children.get(i);
            MainItemComponent childMainItemComponent = ComponentRetriever.get(childEntity, MainItemComponent.class);
            ZIndexComponent childZComponent = ComponentRetriever.get(childEntity, ZIndexComponent.class);
            ButtonComponent buttonComponent = ComponentRetriever.get(entity, ButtonComponent.class);
            if (!buttonComponent.isEnable) {
                if (childZComponent.layerName.equals("checked")) {
                    childMainItemComponent.visible = false;
                }
                if (childZComponent.layerName.equals("disable")) {
                    childMainItemComponent.visible = true;
                }
                if (childZComponent.layerName.equals("normal")) {
                    childMainItemComponent.visible = false;
                }
                if (childZComponent.layerName.equals("pressed")) {
                    childMainItemComponent.visible = false;
                }
            } else if (buttonComponent.isChecked) {
                if (childZComponent.layerName.equals("checked")) {
                    childMainItemComponent.visible = true;
                }
                if (childZComponent.layerName.equals("disable")) {
                    childMainItemComponent.visible = false;
                }
                if (childZComponent.layerName.equals("normal")) {
                    childMainItemComponent.visible = false;
                }
                if (childZComponent.layerName.equals("pressed")) {
                    childMainItemComponent.visible = false;
                }
            } else if (isTouched(entity, buttonComponent)) {
                if (childZComponent.layerName.equals("checked")) {
                    childMainItemComponent.visible = false;
                }
                if (childZComponent.layerName.equals("disable")) {
                    childMainItemComponent.visible = false;
                }
                if (childZComponent.layerName.equals("normal")) {
                    childMainItemComponent.visible = false;
                }
                if (childZComponent.layerName.equals("pressed")) {
                    childMainItemComponent.visible = true;
                }
            } else {
                if (childZComponent.layerName.equals("checked")) {
                    childMainItemComponent.visible = false;
                }
                if (childZComponent.layerName.equals("disable")) {
                    childMainItemComponent.visible = false;
                }
                if (childZComponent.layerName.equals("normal")) {
                    childMainItemComponent.visible = true;
                }
                if (childZComponent.layerName.equals("pressed")) {
                    childMainItemComponent.visible = false;
                }
            }
        }

    }

    private boolean isTouched(Entity entity, ButtonComponent buttonComponent) {
        if (Gdx.input.isTouched()) {
            DimensionsComponent dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
            Vector2 localCoordinates = new Vector2(Gdx.input.getX(), Gdx.input.getY());

            TransformMathUtils.globalToLocalCoordinates(entity, localCoordinates);

            if (dimensionsComponent.hit(localCoordinates.x, localCoordinates.y)) {
                buttonComponent.setTouchState(true);
                return true;
            }
        }
        buttonComponent.setTouchState(false);
        return false;
    }
}
