package games.rednblack.editor.renderer.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;

import games.rednblack.editor.renderer.data.ProjectInfoVO;
import games.rednblack.editor.renderer.data.SpriterRelationVO;
import games.rednblack.editor.renderer.data.SpriterVO;

import java.io.File;
import java.util.HashSet;

public class AsyncResourceManager extends ResourceManager {

    @Override
    public ProjectInfoVO getProjectVO() {
        return super.getProjectVO();
    }

    public void setProjectInfoVO(ProjectInfoVO vo) {
        this.projectVO = vo;
    }

    public void addAtlasPack(String name, TextureAtlas pack) {
        this.atlasesPack.put(name, pack);
    }

    @Override
    public void loadAtlasImages() {
        throw new GdxRuntimeException("see loadAtlasImages(AssetManager)");
    }

    public HashSet<String> getAtlasImageNamesToLoad() {
        return this.atlasImageNamesToLoad;
    }

    public void loadAtlasImages(AssetManager manager) {
        for (String key : atlasImagesAtlas.keySet()) {
            if (!atlasImageNamesToLoad.contains(key)) {
                atlasImagesAtlas.remove(key);
            }
        }

        for (String name : atlasImageNamesToLoad) {
            FileHandle fileHandle = Gdx.files.internal(packResolutionName + File.separator + atlasImagesPath + File.separator + name + ".atlas");
            atlasImagesAtlas.put(name, manager.get(fileHandle.path(), TextureAtlas.class));
        }
    }

    @Override
    public void loadSpineAnimations() {
        throw new GdxRuntimeException("see loadSpineAnimations(AssetManager)");
    }

    @Override
    public void loadSpineAnimation(String name) {
        throw new GdxRuntimeException("see loadSpineAnimation(AssetManager, String)");
    }

    public void loadSpineAnimations(AssetManager manager) {
        for (String name : spineAnimNamesToLoad) {
            loadSpineAnimation(manager, name);
        }
    }

    public void loadSpineAnimation(AssetManager manager, String name) {
        skeletonJSON.put(name, Gdx.files.internal("orig" + File.separator + spineAnimationsPath + File.separator + name + File.separator + name + ".json"));
    }

    /**
     * Sprite Animations
     */

    @Override
    public void loadSpriteAnimations() {
        throw new GdxRuntimeException("see loadSpriteAnimations(AssetManager)");
    }

    @Override
    public void loadSpriterAnimations() {
        throw new GdxRuntimeException("see loadSpriterAnimations(AssetManager)");
    }

    public HashSet<String> getSpriterAnimNamesToLoad() {
        return this.spriterAnimNamesToLoad;
    }

    public void loadSpriterAnimations(AssetManager manager) {
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
            FileHandle fileHandle = Gdx.files.internal(packResolutionName + File.separator + spriterAnimationsPath + File.separator + name + File.separator + name + ".atlas");
            spriterAtlas.put(name, manager.get(fileHandle.path(), TextureAtlas.class));
            FileHandle animFile = Gdx.files.internal("orig" + File.separator + spriterAnimationsPath + File.separator + name + File.separator + name + ".scml");
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
    public void loadParticleEffects() {
        throw new GdxRuntimeException("see loadParticleEffects(AssetManager)");
    }
}
