package games.rednblack.editor.renderer.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;

import games.rednblack.editor.renderer.data.ProjectInfoVO;

import java.io.File;

public class ResourceManagerLoader extends AsynchronousAssetLoader<AsyncResourceManager, ResourceManagerLoader.AsyncResourceManagerParam> {

    private AsyncResourceManager asyncResourceManager;

    private ProjectInfoVO projectInfoVO;

    public ResourceManagerLoader(FileHandleResolver resolver) {
        super(resolver);
        this.asyncResourceManager = new AsyncResourceManager();
    }

    @Override
    public void loadAsync(AssetManager manager, String fileName, FileHandle file, AsyncResourceManagerParam parameter) {
    }

    @Override
    public AsyncResourceManager loadSync(AssetManager manager, String fileName, FileHandle file, AsyncResourceManagerParam parameter) {
        if (!fileName.equals("project.dt")) {
            throw new GdxRuntimeException("fileName must be project.dt");
        }

        for (String pack : projectInfoVO.imagesPacks.keySet()) {
            String name = pack.equals("main") ? "pack.atlas" : pack + ".atlas";
            FileHandle packFile = Gdx.files.internal(this.asyncResourceManager.packResolutionName + File.separator + name);
            this.asyncResourceManager.addAtlasPack(pack, manager.get(packFile.path(), TextureAtlas.class));
        }
        for (String pack : projectInfoVO.animationsPacks.keySet()) {
            String name = pack.equals("main") ? "pack.atlas" : pack + ".atlas";
            FileHandle packFile = Gdx.files.internal(this.asyncResourceManager.packResolutionName + File.separator + name);
            this.asyncResourceManager.addAtlasPack(pack, manager.get(packFile.path(), TextureAtlas.class));
        }
        this.asyncResourceManager.loadReverseAtlasMap();
        this.asyncResourceManager.loadAtlasImages(manager);
        this.asyncResourceManager.loadSpineAnimations(manager);
        this.asyncResourceManager.loadSpriterAnimations(manager);
        this.asyncResourceManager.loadParticleEffects();
        this.asyncResourceManager.loadSpriteAnimations();
        this.asyncResourceManager.loadFonts();
        this.asyncResourceManager.loadShaders();

        return this.asyncResourceManager;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, AsyncResourceManagerParam parameter) {
        if (!fileName.equals("project.dt")) {
            throw new GdxRuntimeException("fileName must be project.dt");
        }
        this.projectInfoVO = this.asyncResourceManager.loadProjectVO();
        for (int i = 0; i < this.projectInfoVO.scenes.size(); i++) {
            String sceneName = this.projectInfoVO.scenes.get(i).sceneName;

            if (parameter == null || (parameter.loadAllScenes || parameter.scenes.contains(sceneName, false))) {
                this.asyncResourceManager.loadSceneVO(sceneName);
                this.asyncResourceManager.scheduleScene(sceneName);
            }
        }
        this.asyncResourceManager.prepareAssetsToLoad();

        //Prepare additional assets not included in any scenes
        if (parameter != null) {
            for (String name : parameter.atlasImages) {
                this.asyncResourceManager.prepareAtlasImage(name);
            }
            for (String name : parameter.particleEffects) {
                this.asyncResourceManager.prepareParticleEffect(name);
            }
            for (String name : parameter.talosVFXs) {
                this.asyncResourceManager.prepareTalosVFX(name);
            }
            for (String name : parameter.spineAnims) {
                this.asyncResourceManager.prepareSpine(name);
            }
            for (String name : parameter.spriteAnims) {
                this.asyncResourceManager.prepareSprite(name);
            }
            for (String name : parameter.spriterAnims) {
                this.asyncResourceManager.prepareSpriter(name);
            }
            for (FontSizePair name : parameter.fonts) {
                this.asyncResourceManager.prepareFont(name);
            }
            for (String name : parameter.shaders) {
                this.asyncResourceManager.prepareShader(name);
            }
        }

        //Build dependency list
        Array<AssetDescriptor> deps = new Array();

        for (String pack : projectInfoVO.imagesPacks.keySet()) {
            String name = pack.equals("main") ? "pack.atlas" : pack + ".atlas";
            FileHandle packFile = Gdx.files.internal(this.asyncResourceManager.packResolutionName + File.separator + name);
            if (packFile.exists()) {
                deps.add(new AssetDescriptor(packFile, TextureAtlas.class));
            }
        }

        for (String pack : projectInfoVO.animationsPacks.keySet()) {
            String name = pack.equals("main") ? "pack.atlas" : pack + ".atlas";
            FileHandle packFile = Gdx.files.internal(this.asyncResourceManager.packResolutionName + File.separator + name);
            if (packFile.exists()) {
                deps.add(new AssetDescriptor(packFile, TextureAtlas.class));
            }
        }

        for (String name : this.asyncResourceManager.getAtlasImageNamesToLoad()) {
            FileHandle res = Gdx.files.internal(this.asyncResourceManager.packResolutionName + File.separator + this.asyncResourceManager.atlasImagesPath + File.separator + name + ".atlas");
            deps.add(new AssetDescriptor(res, TextureAtlas.class));
        }

        for (String name : this.asyncResourceManager.getSpriterAnimNamesToLoad()) {
            FileHandle res = Gdx.files.internal(this.asyncResourceManager.packResolutionName + File.separator + this.asyncResourceManager.spriterAnimationsPath + File.separator + name + File.separator + name + ".atlas");
            deps.add(new AssetDescriptor(res, TextureAtlas.class));
        }

        return deps;
    }

    public static class AsyncResourceManagerParam extends AssetLoaderParameters<AsyncResourceManager> {
        public final Array<String> atlasImages = new Array<String>();
        public final Array<String> spineAnims = new Array<String>();
        public final Array<String> spriteAnims = new Array<String>();
        public final Array<String> spriterAnims = new Array<String>();
        public final Array<String> particleEffects = new Array<String>();
        public final Array<String> talosVFXs = new Array<String>();
        public final Array<FontSizePair> fonts = new Array<FontSizePair>();
        public final Array<String> shaders = new Array<String>();

        public boolean loadAllScenes = true;
        public final Array<String> scenes = new Array<String>();
    }
}

