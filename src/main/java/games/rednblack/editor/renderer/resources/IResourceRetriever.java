package games.rednblack.editor.renderer.resources;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.utils.Array;

import games.rednblack.editor.renderer.data.ResolutionEntryVO;
import games.rednblack.editor.renderer.data.ProjectInfoVO;
import games.rednblack.editor.renderer.data.SceneVO;

/**
 * @author Created by azakhary on 9/9/2014.
 */
public interface IResourceRetriever {
    TextureRegion getTextureRegion(String name, int index);

    TextureAtlas getTextureAtlas(String atlasName);

    TextureRegion getAtlasImagesTextureRegion(String atlasName, String name, int index);

    boolean hasTextureRegion(String name);

    ParticleEffect getParticleEffect(String name);

    FileHandle getSkeletonJSON(String name);

    FileHandle getTalosVFX(String name);

    FileHandle getSpriterSCML(String name);

    Array<FileHandle> getSpriterExtraSCML(String name);

    TextureAtlas getSpriterAtlas(String name);

    Array<TextureAtlas.AtlasRegion> getSpriteAnimation(String name);

    BitmapFont getBitmapFont(String name, int size);

    SceneVO getSceneVO(String sceneName);

    ProjectInfoVO getProjectVO();

    ResolutionEntryVO getLoadedResolution();

    ShaderProgram getShaderProgram(String shaderName);
}
