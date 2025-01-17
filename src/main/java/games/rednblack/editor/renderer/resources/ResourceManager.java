package games.rednblack.editor.renderer.resources;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Json;

import games.rednblack.editor.renderer.data.*;
import games.rednblack.editor.renderer.utils.ShadedDistanceFieldFont;

/**
 * Default ResourceManager that you can reuse or extend
 * Generally is good to load all the assets that are exported from editor
 * using default settings (The paths and file structure should be exact)
 * If changed by you manually, please override this class methods in order to keep it working.
 * <p>
 * The main logic is to prepare list of resources that needs to be load for specified scenes, and then loaded.
 * <p>
 * Created by azakhary on 9/9/2014.
 */
public class ResourceManager implements IResourceLoader, IResourceRetriever, Disposable {

    /**
     * Paths (please change if different) this is the default structure exported from editor
     */
    public String packResolutionName = "orig";

    public String scenesPath = "scenes";
    public String particleEffectsPath = "particles";
    public String talosPath = "talos-vfx";
    public String atlasImagesPath = "atlas_images";
    public String spriteAnimationsPath = "sprite_animations";
    public String spineAnimationsPath = "spine_animations";
    public String spriterAnimationsPath = "spriter_animations";
    public String fontsPath = "freetypefonts";
    public String shadersPath = "shaders";

    public final String DEFAULT_FONT = "default-font-cn.fnt";

    protected float resMultiplier;

    protected ProjectInfoVO projectVO;

    protected ArrayList<String> preparedSceneNames = new ArrayList<String>();
    protected HashMap<String, SceneVO> loadedSceneVOs = new HashMap<String, SceneVO>();

    protected HashSet<String> particleEffectNamesToLoad = new HashSet<String>();
    protected HashSet<String> talosNamesToLoad = new HashSet<String>();
    protected HashSet<String> atlasImageNamesToLoad = new HashSet<String>();
    protected HashSet<String> spineAnimNamesToLoad = new HashSet<String>();
    protected HashSet<String> spriteAnimNamesToLoad = new HashSet<String>();
    protected HashSet<String> spriterAnimNamesToLoad = new HashSet<String>();
    protected HashSet<FontSizePair> fontsToLoad = new HashSet<FontSizePair>();
    protected HashSet<String> shaderNamesToLoad = new HashSet<String>();

    protected HashMap<String, String> reverseAtlasMap = new HashMap<String, String>();
    protected HashMap<String, TextureAtlas> atlasesPack = new HashMap<String, TextureAtlas>();

    protected HashMap<String, TextureAtlas> atlasImagesAtlas = new HashMap<>();

    protected HashMap<String, ParticleEffect> particleEffects = new HashMap<>();
    protected HashMap<String, FileHandle> talosVFXs = new HashMap<>();

    protected HashMap<String, FileHandle> skeletonJSON = new HashMap<String, FileHandle>();

    protected HashMap<String, TextureAtlas> spriterAtlas = new HashMap<>();
    protected HashMap<String, FileHandle> spriterSCML = new HashMap<>();
    protected HashMap<String, Array<FileHandle>> spriterExtraSCML = new HashMap<>();

    protected HashMap<String, Array<TextureAtlas.AtlasRegion>> spriteAnimations = new HashMap<>();

    protected HashMap<FontSizePair, BitmapFont> bitmapFonts = new HashMap<>();
    protected HashMap<String, ShaderProgram> shaderPrograms = new HashMap<>();


    /**
     * Constructor does nothing
     */
    public ResourceManager() {

    }

    /**
     * Sets working resolution, please set before doing any loading
     *
     * @param resolution String resolution name, default is "orig" later use resolution names created in editor
     */
    public void setWorkingResolution(String resolution) {
        ResolutionEntryVO resolutionObject = getProjectVO().getResolution(resolution);
        if (resolutionObject != null) {
            packResolutionName = resolution;
        }
    }

    /**
     * Easy use loader
     * Iterates through all scenes and schedules all for loading
     * Prepares all the assets to be loaded that are used in scheduled scenes
     * finally loads all the prepared assets
     */
    public void initAllResources() {
        loadProjectVO();
        for (int i = 0; i < projectVO.scenes.size(); i++) {
            loadSceneVO(projectVO.scenes.get(i).sceneName);
            scheduleScene(projectVO.scenes.get(i).sceneName);
        }
        prepareAssetsToLoad();
        loadAssets();
    }

    /**
     * Initializes scene by loading it's VO data object and loading all the assets needed for this particular scene only
     *
     * @param sceneName - scene file name without ".dt" extension
     */
    public void initScene(String sceneName) {
        loadSceneVO(sceneName);
        scheduleScene(sceneName);
        prepareAssetsToLoad();
        loadAssets();
    }

    /**
     * Anloads scene from the memory, and clears all the freed assets
     *
     * @param sceneName - scene file name without ".dt" extension
     */
    public void unLoadScene(String sceneName) {
        unScheduleScene(sceneName);
        loadedSceneVOs.remove(sceneName);
        loadAssets();
    }

    /**
     * Schedules scene for later loading
     * if later prepareAssetsToLoad function will be called it will only prepare assets that are used in scheduled scene
     *
     * @param name - scene file name without ".dt" extension
     */
    public void scheduleScene(String name) {
        if (loadedSceneVOs.containsKey(name)) {
            preparedSceneNames.add(name);
        } else {
            //TODO: Throw exception that scene was not loaded to be prepared for asseting
        }

    }


    /**
     * Unschedule scene from later loading
     *
     * @param name
     */
    public void unScheduleScene(String name) {
        preparedSceneNames.remove(name);
    }


    /**
     * Creates the list of uniqe assets used in all of the scheduled scenes,
     * removes all the duplicates, and makes list of assets that are only needed.
     */
    public void prepareAssetsToLoad() {
        atlasImageNamesToLoad.clear();
        particleEffectNamesToLoad.clear();
        talosNamesToLoad.clear();
        spineAnimNamesToLoad.clear();
        spriteAnimNamesToLoad.clear();
        spriterAnimNamesToLoad.clear();
        fontsToLoad.clear();
        shaderPrograms.clear();

        for (String preparedSceneName : preparedSceneNames) {
            CompositeVO composite = loadedSceneVOs.get(preparedSceneName).composite;
            if (composite == null) {
                continue;
            }
            //
            String[] particleEffects = composite.getRecursiveParticleEffectsList();
            String[] talosVFXs = composite.getRecursiveTalosList();
            String[] atlasImages = composite.getRecursiveAtlasImagesList();
            String[] spineAnimations = composite.getRecursiveSpineAnimationList();
            String[] spriteAnimations = composite.getRecursiveSpriteAnimationList();
            String[] spriterAnimations = composite.getRecursiveSpriterAnimationList();
            String[] shaderNames = composite.getRecursiveShaderList();
            FontSizePair[] fonts = composite.getRecursiveFontList();
            for (CompositeItemVO library : projectVO.libraryItems.values()) {
                FontSizePair[] libFonts = library.composite.getRecursiveFontList();
                Collections.addAll(fontsToLoad, libFonts);

                // loading particle effects used in library items
                String[] libEffects = library.composite.getRecursiveParticleEffectsList();
                String[] libTalosVFXs = library.composite.getRecursiveTalosList();
                Collections.addAll(particleEffectNamesToLoad, libEffects);
                Collections.addAll(talosNamesToLoad, libTalosVFXs);
            }

            //
            Collections.addAll(particleEffectNamesToLoad, particleEffects);
            Collections.addAll(talosNamesToLoad, talosVFXs);
            Collections.addAll(atlasImageNamesToLoad, atlasImages);
            Collections.addAll(spineAnimNamesToLoad, spineAnimations);
            Collections.addAll(spriteAnimNamesToLoad, spriteAnimations);
            Collections.addAll(spriterAnimNamesToLoad, spriterAnimations);
            Collections.addAll(fontsToLoad, fonts);
            Collections.addAll(shaderNamesToLoad, shaderNames);
        }
    }

    public void prepareAtlasImage(String name) {
        atlasImageNamesToLoad.add(name);
    }

    /*
        Prepare additional assets
     */

    public void prepareParticleEffect(String name) {
        particleEffectNamesToLoad.add(name);
    }

    public void prepareTalosVFX(String name) {
        talosNamesToLoad.add(name);
    }

    public void prepareSpine(String name) {
        spineAnimNamesToLoad.add(name);
    }

    public void prepareSprite(String name) {
        spriteAnimNamesToLoad.add(name);
    }

    public void prepareSpriter(String name) {
        spriterAnimNamesToLoad.add(name);
    }

    public void prepareFont(FontSizePair name) {
        fontsToLoad.add(name);
    }

    public void prepareShader(String name) {
        shaderNamesToLoad.add(name);
    }

    /**
     * Loads all the scheduled assets into memory including
     * main atlas pack, particle effects, sprite animations, spine animations and fonts
     */
    public void loadAssets() {
        loadAtlasPack();
        loadAtlasImages();
        loadParticleEffects();
        loadSpineAnimations();
        loadSpriteAnimations();
        loadSpriterAnimations();
        loadFonts();
        loadShaders();
    }

    @Override
    public void loadAtlasPack() {
        for (String pack : projectVO.imagesPacks.keySet()) {
            String name = pack.equals("main") ? "pack.atlas" : pack + ".atlas";
            FileHandle packFile = Gdx.files.internal(packResolutionName + File.separator + name);
            if (packFile.exists() && atlasesPack.get(pack) == null) {
                atlasesPack.put(pack, new TextureAtlas(packFile));
            }
        }

        for (String pack : projectVO.animationsPacks.keySet()) {
            String name = pack.equals("main") ? "pack.atlas" : pack + ".atlas";
            FileHandle packFile = Gdx.files.internal(packResolutionName + File.separator + name);
            if (packFile.exists() && atlasesPack.get(pack) == null) {
                atlasesPack.put(pack, new TextureAtlas(packFile));
            }
        }

        loadReverseAtlasMap();
    }

    public void loadReverseAtlasMap() {
        for (String atlasPackName : atlasesPack.keySet()) {
            TextureAtlas atlas = atlasesPack.get(atlasPackName);
            for (TextureAtlas.AtlasRegion region : atlas.getRegions()) {
                reverseAtlasMap.put(region.name, atlasPackName);
            }
        }
    }

    @Override
    public void loadAtlasImages() {
        for (String key : atlasImagesAtlas.keySet()) {
            if (!atlasImageNamesToLoad.contains(key)) {
                atlasImagesAtlas.remove(key);
            }
        }

        for (String name : atlasImageNamesToLoad) {
            TextureAtlas animAtlas = new TextureAtlas(Gdx.files.internal(packResolutionName
                    + File.separator + atlasImagesPath + File.separator + name + ".atlas"));
            atlasImagesAtlas.put(name, animAtlas);
        }
    }

    @Override
    public void loadParticleEffects() {
        // empty existing ones that are not scheduled to load
        for (String key : particleEffects.keySet()) {
            if (!particleEffectNamesToLoad.contains(key)) {
                particleEffects.remove(key);
            }
        }

        // load scheduled
        for (String name : particleEffectNamesToLoad) {
            ParticleEffect effect = new ParticleEffect();
            effect.loadEmitters(Gdx.files.internal(particleEffectsPath + File.separator + name));
            for (TextureAtlas atlas : atlasesPack.values()) {
                try {
                    effect.loadEmitterImages(atlas, "");
                    particleEffects.put(name, effect);
                    break;
                } catch (Exception ignore) {
                }
            }
        }

        //Talos
        // empty existing ones that are not scheduled to load
        for (String key : talosVFXs.keySet()) {
            if (!talosNamesToLoad.contains(key)) {
                talosVFXs.remove(key);
            }
        }

        // load scheduled
        for (String name : talosNamesToLoad) {
            talosVFXs.put(name, Gdx.files.internal(talosPath + File.separator + name));
        }
    }

    @Override
    public void loadSpriteAnimations() {
        // empty existing ones that are not scheduled to load
        for (String key : spriteAnimations.keySet()) {
            if (!spriteAnimNamesToLoad.contains(key)) {
                spriteAnimations.remove(key);
            }
        }

        for (String name : spriteAnimNamesToLoad) {
            TextureAtlas atlas = atlasesPack.get(reverseAtlasMap.get(name));
            spriteAnimations.put(name, atlas.findRegions(name));
        }
    }

    public void loadSpineAnimation(String name) {
        skeletonJSON.put(name, Gdx.files.internal("orig" + File.separator + spineAnimationsPath + File.separator + name + File.separator + name + ".json"));
    }


    @Override
    public void loadSpineAnimations() {
        for (String name : spineAnimNamesToLoad) {
            loadSpineAnimation(name);
        }
    }

    @Override
    public void loadSpriterAnimations() {
        // empty existing ones that are not scheduled to load
        for (String key : spriterSCML.keySet()) {
            if (!spriterAnimNamesToLoad.contains(key)) {
                spriterSCML.remove(key);
            }
        }

        FileHandle extraConfigFile = Gdx.files.internal(packResolutionName + File.separator
                + spriterAnimationsPath + File.separator + "anim-relation.dt");
        SpriterRelationVO rVO = null;
        if (extraConfigFile.exists()) {
            Json json = new Json();
            json.setIgnoreUnknownFields(true);
            rVO = json.fromJson(SpriterRelationVO.class, extraConfigFile.readString("utf-8"));
        }

        for (String name : spriterAnimNamesToLoad) {
            TextureAtlas animAtlas = new TextureAtlas(Gdx.files.internal(packResolutionName + File.separator
                    + spriterAnimationsPath + File.separator + name + File.separator + name + ".atlas"));
            spriterAtlas.put(name, animAtlas);
            FileHandle animFile = Gdx.files.internal(packResolutionName + File.separator + spriterAnimationsPath
                    + File.separator + name + File.separator + name + ".scml");
            spriterSCML.put(name, animFile);
            if (rVO != null && rVO.animations != null && rVO.animations.containsKey(name)) {
                SpriterVO vo = rVO.animations.get(name);
                Array<FileHandle> files = new Array<>();
                for (String s : vo.animations) {
                    FileHandle f = new FileHandle(packResolutionName + File.separator + spriterAnimationsPath
                            + File.separator + "extra" + File.separator + s + ".scml");
                    files.add(f);
                }
                spriterExtraSCML.put(name, files);
            }
        }
    }

    @Override
    public void loadFonts() {
        //resolution related stuff
        ResolutionEntryVO curResolution = getProjectVO().getResolution(packResolutionName);
        resMultiplier = 1;
        if (!packResolutionName.equals("orig")) {
            if (curResolution.base == 0) {
                resMultiplier = (float) curResolution.width / (float) getProjectVO().originalResolution.width;
            } else {
                resMultiplier = (float) curResolution.height / (float) getProjectVO().originalResolution.height;
            }
        }

        // empty existing ones that are not scheduled to load
        for (FontSizePair pair : bitmapFonts.keySet()) {
            if (!fontsToLoad.contains(pair)) {
                bitmapFonts.remove(pair);
            }
        }

        for (FontSizePair pair : fontsToLoad) {
            try {
                loadFont(pair);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadFont(FontSizePair pair) throws IOException {
        if ("Internal".equals(pair.fontName)) {
            loadInternalFont(pair.fontSize);
            return;
        }

        String name = URLEncoder.encode(new String(pair.fontName.getBytes(), "utf-8"), "utf-8");
        FileHandle fontFile = Gdx.files.internal(fontsPath + File.separator + name + ".ttf");

        FileHandle txtFile = Gdx.files.internal(fontsPath + File.separator + "gbk-chars.txt");
        String charsTxt = txtFile.readString("utf-8");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = Math.round(pair.fontSize * resMultiplier);
        parameter.characters = charsTxt;
        parameter.gamma = 4.0f;
        parameter.renderCount = 3;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        BitmapFont font = generator.generateFont(parameter);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.setUseIntegerPositions(false);
        generator.dispose();

        bitmapFonts.put(pair, font);
    }

    private void loadInternalFont(int size) {
        ShadedDistanceFieldFont font = new ShadedDistanceFieldFont(Gdx.files.internal(fontsPath + File.separator + DEFAULT_FONT));
        font.setDistanceFieldSmoothing(1.5f);
        font.getData().setScale(size / font.getCapHeight());
        bitmapFonts.put(new FontSizePair("Internal", size), font);
    }

    @Override
    public SceneVO loadSceneVO(String sceneName) {
        FileHandle file = Gdx.files.internal(scenesPath + File.separator + sceneName + ".dt");
        Json json = new Json();
        SceneVO sceneVO = json.fromJson(SceneVO.class, file.readString("utf-8"));

        loadedSceneVOs.put(sceneName, sceneVO);

        return sceneVO;
    }

    public void unLoadSceneVO(String sceneName) {
        loadedSceneVOs.remove(sceneName);
    }

    @Override
    public ProjectInfoVO loadProjectVO() {

        FileHandle file = Gdx.files.internal("project.dt");
        Json json = new Json();
        projectVO = json.fromJson(ProjectInfoVO.class, file.readString());

        return projectVO;
    }

    @Override
    public void loadShaders() {
        // empty existing ones that are not scheduled to load
        for (String key : shaderPrograms.keySet()) {
            if (!shaderNamesToLoad.contains(key)) {
                shaderPrograms.get(key).dispose();
                shaderPrograms.remove(key);
            }
        }

        for (String name : shaderNamesToLoad) {
            ShaderProgram shaderProgram = new ShaderProgram(Gdx.files.internal(shadersPath + File.separator + name + ".vert"), Gdx.files.internal(shadersPath + File.separator + name + ".frag"));
            shaderPrograms.put(name, shaderProgram);
        }
    }

    /**
     * Following methods are for retriever interface, which is intended for runtime internal use
     * to retrieve any already loaded into memory asset for rendering
     */

    @Override
    public TextureRegion getTextureRegion(String name, int index) {
        if (reverseAtlasMap.get(name) == null)
            return null;
        TextureAtlas atlas = atlasesPack.get(reverseAtlasMap.get(name));
        return atlas.findRegion(name, index);
    }

    @Override
    public TextureAtlas getTextureAtlas(String atlasName) {
        return atlasesPack.get(atlasName);
    }

    @Override
    public TextureRegion getAtlasImagesTextureRegion(String atlasName, String name, int index) {
        if (!atlasName.isEmpty() && atlasImagesAtlas.containsKey(atlasName)) {
            TextureAtlas textureAtlas = atlasImagesAtlas.get(atlasName);
            return textureAtlas.findRegion(name, index);
        } else {
            for (Map.Entry<String, TextureAtlas> entry : atlasImagesAtlas.entrySet()) {
                TextureAtlas.AtlasRegion region = entry.getValue().findRegion(name, index);
                if (region != null) return region;
            }
        }
        return null;
    }

    @Override
    public ParticleEffect getParticleEffect(String name) {
        return new ParticleEffect(particleEffects.get(name));
    }

    @Override
    public FileHandle getSkeletonJSON(String name) {
        return skeletonJSON.get(name);
    }

    @Override
    public FileHandle getTalosVFX(String name) {
        return talosVFXs.get(name);
    }

    @Override
    public FileHandle getSpriterSCML(String name) {
        return spriterSCML.get(name);
    }

    @Override
    public Array<FileHandle> getSpriterExtraSCML(String name) {
        Array<FileHandle> array = spriterExtraSCML.get(name);
        return array == null ? new Array<>() : array;
    }

    @Override
    public TextureAtlas getSpriterAtlas(String name) {
        return spriterAtlas.get(name);
    }

    @Override
    public Array<TextureAtlas.AtlasRegion> getSpriteAnimation(String name) {
        return spriteAnimations.get(name);
    }

    @Override
    public BitmapFont getBitmapFont(String name, int size) {
        return bitmapFonts.get(new FontSizePair(name, size));
    }

    @Override
    public boolean hasTextureRegion(String regionName) {
        if (reverseAtlasMap.get(regionName) == null)
            return false;
        TextureAtlas atlas = atlasesPack.get(reverseAtlasMap.get(regionName));
        return atlas.findRegion(regionName) != null;
    }

    @Override
    public SceneVO getSceneVO(String sceneName) {
        return loadedSceneVOs.get(sceneName);
    }

    @Override
    public ProjectInfoVO getProjectVO() {
        return projectVO;
    }

    @Override
    public ResolutionEntryVO getLoadedResolution() {
        if (packResolutionName.equals("orig")) {
            return getProjectVO().originalResolution;
        }
        return getProjectVO().getResolution(packResolutionName);
    }

    @Override
    public void dispose() {
        for (TextureAtlas atlas : atlasesPack.values()) {
            atlas.dispose();
        }
        for (TextureAtlas textureAtlas : atlasImagesAtlas.values()) {
            textureAtlas.dispose();
        }
        for (TextureAtlas textureAtlas : spriterAtlas.values()) {
            textureAtlas.dispose();
        }
        for (BitmapFont font : bitmapFonts.values()) {
            font.dispose();
        }
    }

    @Override
    public ShaderProgram getShaderProgram(String shaderName) {
        return shaderPrograms.get(shaderName);
    }
}
