package games.rednblack.editor.renderer.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.GdxRuntimeException;

import games.rednblack.editor.renderer.data.ProjectInfoVO;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class AsyncResourceManager extends ResourceManager {

    @Override
    public ProjectInfoVO getProjectVO() {
        return super.getProjectVO();
    }

    public void setProjectInfoVO(ProjectInfoVO vo) {
        this.projectVO = vo;
    }

    public void setMainPack(TextureAtlas mainPack) {
        this.mainPack = mainPack;
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

    public HashSet<String> getSpineAnimNamesToLoad() {
        return this.spineAnimNamesToLoad;
    }

    public void loadSpineAnimations(AssetManager manager) {
        Iterator it = skeletonAtlases.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pairs = (Map.Entry) it.next();
            if (spineAnimNamesToLoad.contains(pairs.getKey())) {
                spineAnimNamesToLoad.remove(pairs.getKey());
            } else {
                it.remove();
                skeletonJSON.remove(pairs.getKey());
            }
        }

        for (String name : spineAnimNamesToLoad) {
            loadSpineAnimation(manager, name);
        }
    }

    public void loadSpineAnimation(AssetManager manager, String name) {
        FileHandle packFile = Gdx.files.internal(packResolutionName + File.separator + spineAnimationsPath + File.separator + name + File.separator + name + ".atlas");
        TextureAtlas animAtlas = manager.get(packFile.path(), TextureAtlas.class);
        skeletonAtlases.put(name, animAtlas);
        skeletonJSON.put(name, Gdx.files.internal("orig" + File.separator + spineAnimationsPath + File.separator + name + File.separator + name + ".json"));
    }

    /**
     * Sprite Animations
     */

    @Override
    public void loadSpriteAnimations() {
        throw new GdxRuntimeException("see loadSpriteAnimations(AssetManager)");
    }

    public HashSet<String> getSpriteAnimNamesToLoad() {
        return this.spriteAnimNamesToLoad;
    }

    public void loadSpriteAnimations(AssetManager manager) {
        // empty existing ones that are not scheduled to load
        for (String key : spriteAnimations.keySet()) {
            if (!spriteAnimNamesToLoad.contains(key)) {
                spriteAnimations.remove(key);
            }
        }

        for (String name : spriteAnimNamesToLoad) {
            FileHandle packFile = Gdx.files.internal(packResolutionName + File.separator + spriteAnimationsPath + File.separator + name + File.separator + name + ".atlas");
            spriteAnimations.put(name, manager.get(packFile.path(), TextureAtlas.class));
        }
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
        for (String name : spriterAnimNamesToLoad) {
            FileHandle fileHandle = Gdx.files.internal(packResolutionName + File.separator + spriterAnimationsPath + File.separator + name + File.separator + name + ".atlas");
            spriterAtlas.put(name, manager.get(fileHandle.path(), TextureAtlas.class));
            FileHandle animFile = Gdx.files.internal("orig" + File.separator + spriterAnimationsPath + File.separator + name + File.separator + name + ".scml");
            spriterSCML.put(name, animFile);
        }
    }

    @Override
    public void loadParticleEffects() {
        throw new GdxRuntimeException("see loadParticleEffects(AssetManager)");
    }

    public HashSet<String> getParticleEffectsNamesToLoad() {
        return this.particleEffectNamesToLoad;
    }

    public void loadParticleEffects(AssetManager manager) {
        // empty existing ones that are not scheduled to load
        for (String key : particleEffects.keySet()) {
            if (!particleEffectNamesToLoad.contains(key)) {
                particleEffects.remove(key);
            }
        }

        // load scheduled
        for (String name : particleEffectNamesToLoad) {
            FileHandle res = Gdx.files.internal(particleEffectsPath + File.separator + name);
            particleEffects.put(name, manager.get(res.path(), ParticleEffect.class));
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
}
