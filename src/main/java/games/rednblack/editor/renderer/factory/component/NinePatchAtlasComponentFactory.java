package games.rednblack.editor.renderer.factory.component;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.World;

import games.rednblack.editor.renderer.box2dLight.RayHandler;
import games.rednblack.editor.renderer.components.DimensionsComponent;
import games.rednblack.editor.renderer.components.NinePatchComponent;
import games.rednblack.editor.renderer.data.Image9patchVO;
import games.rednblack.editor.renderer.data.MainItemVO;
import games.rednblack.editor.renderer.data.ProjectInfoVO;
import games.rednblack.editor.renderer.data.ResolutionEntryVO;
import games.rednblack.editor.renderer.factory.EntityFactory;
import games.rednblack.editor.renderer.resources.IResourceRetriever;

public class NinePatchAtlasComponentFactory extends ComponentFactory {

    private NinePatchComponent ninePatchComponent;

    public NinePatchAtlasComponentFactory(PooledEngine engine, RayHandler rayHandler, World world, IResourceRetriever rm) {
        super(engine, rayHandler, world, rm);
    }

    @Override
    public void createComponents(Entity root, Entity entity, MainItemVO vo) {
        ninePatchComponent = createNinePatchComponent(entity, (Image9patchVO) vo);
        createCommonComponents(entity, vo, EntityFactory.NINE_PATCH);
        createParentNodeComponent(root, entity);
        createNodeComponent(root, entity);
    }

    @Override
    protected DimensionsComponent createDimensionsComponent(Entity entity, MainItemVO vo) {
        DimensionsComponent component = engine.createComponent(DimensionsComponent.class);
        component.height = ((Image9patchVO) vo).height;
        component.width = ((Image9patchVO) vo).width;
        if (component.width == 0) {
            component.width = ninePatchComponent.ninePatch.getTotalWidth();
        }

        if (component.height == 0) {
            component.height = ninePatchComponent.ninePatch.getTotalHeight();
        }

        entity.add(component);
        return component;
    }

    private NinePatchComponent createNinePatchComponent(Entity entity, Image9patchVO vo) {
        NinePatchComponent ninePatchComponent = engine.createComponent(NinePatchComponent.class);
        AtlasRegion atlasRegion = (AtlasRegion) rm.getAtlasImagesTextureRegion(vo.atlasName, vo.imageName, vo.index);
        int[] splits = atlasRegion.findValue("split");
        if (splits == null) {
            splits = new int[]{0, 0, 0, 0};
        }
        ninePatchComponent.ninePatch = new NinePatch(atlasRegion, splits[0], splits[1], splits[2], splits[3]);

        ResolutionEntryVO resolutionEntryVO = rm.getLoadedResolution();
        ProjectInfoVO projectInfoVO = rm.getProjectVO();
        float multiplier = resolutionEntryVO.getMultiplier(rm.getProjectVO().originalResolution);

        ninePatchComponent.ninePatch.scale(multiplier / projectInfoVO.pixelToWorld, multiplier / projectInfoVO.pixelToWorld);

        ninePatchComponent.textureAtlasName = vo.atlasName;
        ninePatchComponent.textureRegionName = vo.imageName;
        entity.add(ninePatchComponent);

        return ninePatchComponent;
    }

}
