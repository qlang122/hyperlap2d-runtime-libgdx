package games.rednblack.editor.renderer.data;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;

import games.rednblack.editor.renderer.components.LayerMapComponent;
import games.rednblack.editor.renderer.components.MainItemComponent;
import games.rednblack.editor.renderer.components.NodeComponent;
import games.rednblack.editor.renderer.factory.EntityFactory;
import games.rednblack.editor.renderer.resources.FontSizePair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class CompositeVO {

    public ArrayList<SimpleImageVO> sImages = new ArrayList<>(1);
    public ArrayList<AtlasImageVO> sAtlasImages = new ArrayList<>(1);
    public ArrayList<Image9patchVO> sImage9patchs = new ArrayList<>(1);
    public ArrayList<LabelVO> sLabels = new ArrayList<>(1);
    public ArrayList<CompositeItemVO> sComposites = new ArrayList<>(1);
    public ArrayList<ParticleEffectVO> sParticleEffects = new ArrayList<>(1);
    public ArrayList<TalosVO> sTalosVFX = new ArrayList<>(1);
    public ArrayList<LightVO> sLights = new ArrayList<>(1);
    public ArrayList<SpineVO> sSpineAnimations = new ArrayList<>(1);
    public ArrayList<SpriteAnimationVO> sSpriteAnimations = new ArrayList<>(1);
    public ArrayList<SpriterVO> sSpriterAnimations = new ArrayList<>(1);
    public ArrayList<ColorPrimitiveVO> sColorPrimitives = new ArrayList<>(1);

    public ArrayList<LayerItemVO> layers = new ArrayList<>();

    public HashMap<String, StickyNoteVO> sStickyNotes = new HashMap<>(1);

    public CompositeVO() {

    }

    public CompositeVO(CompositeVO vo) {
        if (vo == null) return;
        update(vo);
    }

    public void update(CompositeVO vo) {
        clear();
        for (int i = 0; i < vo.sImages.size(); i++) {
            sImages.add(new SimpleImageVO(vo.sImages.get(i)));
        }
        for (int i = 0; i < vo.sAtlasImages.size(); i++) {
            sAtlasImages.add(new AtlasImageVO(vo.sAtlasImages.get(i)));
        }
        for (int i = 0; i < vo.sImage9patchs.size(); i++) {
            sImage9patchs.add(new Image9patchVO(vo.sImage9patchs.get(i)));
        }
        for (int i = 0; i < vo.sLabels.size(); i++) {
            sLabels.add(new LabelVO(vo.sLabels.get(i)));
        }
        for (int i = 0; i < vo.sComposites.size(); i++) {
            sComposites.add(new CompositeItemVO(vo.sComposites.get(i)));
        }

        for (int i = 0; i < vo.sParticleEffects.size(); i++) {
            sParticleEffects.add(new ParticleEffectVO(vo.sParticleEffects.get(i)));
        }

        for (int i = 0; i < vo.sTalosVFX.size(); i++) {
            sTalosVFX.add(new TalosVO(vo.sTalosVFX.get(i)));
        }

        for (int i = 0; i < vo.sLights.size(); i++) {
            sLights.add(new LightVO(vo.sLights.get(i)));
        }

        for (int i = 0; i < vo.sSpineAnimations.size(); i++) {
            sSpineAnimations.add(new SpineVO(vo.sSpineAnimations.get(i)));
        }

        for (int i = 0; i < vo.sSpriteAnimations.size(); i++) {
            sSpriteAnimations.add(new SpriteAnimationVO(vo.sSpriteAnimations.get(i)));
        }

        for (int i = 0; i < vo.sSpriterAnimations.size(); i++) {
            sSpriterAnimations.add(new SpriterVO(vo.sSpriterAnimations.get(i)));
        }

        for (int i = 0; i < vo.sColorPrimitives.size(); i++) {
            sColorPrimitives.add(new ColorPrimitiveVO(vo.sColorPrimitives.get(i)));
        }

        layers.clear();
        for (int i = 0; i < vo.layers.size(); i++) {
            layers.add(new LayerItemVO(vo.layers.get(i)));
        }

    }

    public void addItem(MainItemVO vo) {
        String className = vo.getClass().getSimpleName();

        if (className.equals("SimpleImageVO")) {
            sImages.add((SimpleImageVO) vo);
        }
        if (className.equals("AtlasImageVO")) {
            sAtlasImages.add((AtlasImageVO) vo);
        }
        if (className.equals("Image9patchVO")) {
            sImage9patchs.add((Image9patchVO) vo);
        }
        if (className.equals("LabelVO")) {
            sLabels.add((LabelVO) vo);
        }
        if (className.equals("CompositeItemVO")) {
            sComposites.add((CompositeItemVO) vo);
        }
        if (className.equals("ParticleEffectVO")) {
            sParticleEffects.add((ParticleEffectVO) vo);
        }
        if (className.equals("TalosVO")) {
            sTalosVFX.add((TalosVO) vo);
        }
        if (className.equals("LightVO")) {
            sLights.add((LightVO) vo);
        }
        if (className.equals("SpineVO")) {
            sSpineAnimations.add((SpineVO) vo);
        }
        if (className.equals("SpriterVO")) {
            sSpriterAnimations.add((SpriterVO) vo);
        }
        if (className.equals("SpriteAnimationVO")) {
            sSpriteAnimations.add((SpriteAnimationVO) vo);
        }
        if (className.equals("ColorPrimitiveVO")) {
            sColorPrimitives.add((ColorPrimitiveVO) vo);
        }
    }

    public void removeItem(MainItemVO vo) {
        String className = vo.getClass().getSimpleName();
        if (className.equals("SimpleImageVO")) {
            sImages.remove((SimpleImageVO) vo);
        }
        if (className.equals("AtlasImageVO")) {
            sAtlasImages.remove((AtlasImageVO) vo);
        }
        if (className.equals("Image9patchVO")) {
            sImage9patchs.remove((Image9patchVO) vo);
        }
        if (className.equals("LabelVO")) {
            sLabels.remove((LabelVO) vo);
        }
        if (className.equals("CompositeItemVO")) {
            sComposites.remove((CompositeItemVO) vo);
        }
        if (className.equals("ParticleEffectVO")) {
            sParticleEffects.remove((ParticleEffectVO) vo);
        }
        if (className.equals("TalosVO")) {
            sTalosVFX.remove((TalosVO) vo);
        }
        if (className.equals("LightVO")) {
            sLights.remove((LightVO) vo);
        }
        if (className.equals("SpineVO")) {
            sSpineAnimations.remove((SpineVO) vo);
        }
        if (className.equals("SpriterVO")) {
            sSpriterAnimations.remove((SpriterVO) vo);
        }
        if (className.equals("SpriteAnimationVO")) {
            sSpriteAnimations.remove((SpriteAnimationVO) vo);
        }
        if (className.equals("ColorPrimitiveVO")) {
            sColorPrimitives.remove((ColorPrimitiveVO) vo);
        }
    }

    public void clear() {
        sImages.clear();
        sAtlasImages.clear();
        sLabels.clear();
        sComposites.clear();
        sParticleEffects.clear();
        sTalosVFX.clear();
        sLights.clear();
        sSpineAnimations.clear();
        sSpriterAnimations.clear();
        sSpriteAnimations.clear();
        sColorPrimitives.clear();
    }

    public boolean isEmpty() {
        return sComposites.isEmpty() &&
                sImage9patchs.isEmpty() &&
                sImages.isEmpty() &&
                sAtlasImages.isEmpty() &&
                sSpriteAnimations.isEmpty() &&
                sLabels.isEmpty() &&
                sLights.isEmpty() &&
                sParticleEffects.isEmpty() &&
                sTalosVFX.isEmpty() &&
                sSpineAnimations.isEmpty() &&
                sSpriterAnimations.isEmpty() &&
                sColorPrimitives.isEmpty();
    }

    public String[] getRecursiveParticleEffectsList() {
        HashSet<String> list = new HashSet<>();
        for (ParticleEffectVO sParticleEffect : sParticleEffects) {
            list.add(sParticleEffect.particleName);
        }
        for (CompositeItemVO sComposite : sComposites) {
            String[] additionalList = sComposite.composite.getRecursiveParticleEffectsList();
            Collections.addAll(list, additionalList);
        }
        String[] finalList = new String[list.size()];
        list.toArray(finalList);

        return finalList;
    }

    public String[] getRecursiveTalosList() {
        HashSet<String> list = new HashSet<>();
        for (TalosVO sTalos : sTalosVFX) {
            list.add(sTalos.particleName);
        }
        for (CompositeItemVO sComposite : sComposites) {
            String[] additionalList = sComposite.composite.getRecursiveTalosList();
            Collections.addAll(list, additionalList);
        }
        String[] finalList = new String[list.size()];
        list.toArray(finalList);

        return finalList;
    }

    public String[] getRecursiveAtlasImagesList() {
        HashSet<String> list = new HashSet<String>();
        for (AtlasImageVO vo : sAtlasImages) {
            list.add(vo.atlasName);
        }
        for (CompositeItemVO sComposite : sComposites) {
            String[] additionalList = sComposite.composite.getRecursiveAtlasImagesList();
            Collections.addAll(list, additionalList);
        }
        String[] finalList = new String[list.size()];
        list.toArray(finalList);

        return finalList;
    }

    public String[] getRecursiveSpineAnimationList() {
        HashSet<String> list = new HashSet<String>();
        for (SpineVO sSpineAnimation : sSpineAnimations) {
            list.add(sSpineAnimation.animationName);
        }
        for (CompositeItemVO sComposite : sComposites) {
            String[] additionalList = sComposite.composite.getRecursiveSpineAnimationList();
            Collections.addAll(list, additionalList);
        }
        String[] finalList = new String[list.size()];
        list.toArray(finalList);

        return finalList;
    }

    public String[] getRecursiveSpriteAnimationList() {
        HashSet<String> list = new HashSet<String>();
        for (SpriteAnimationVO sSpriteAnimation : sSpriteAnimations) {
            list.add(sSpriteAnimation.animationName);
        }
        for (CompositeItemVO sComposite : sComposites) {
            String[] additionalList = sComposite.composite.getRecursiveSpriteAnimationList();
            Collections.addAll(list, additionalList);
        }
        String[] finalList = new String[list.size()];
        list.toArray(finalList);

        return finalList;
    }

    public String[] getRecursiveSpriterAnimationList() {
        HashSet<String> list = new HashSet<String>();
        for (SpriterVO sSpriterAnimation : sSpriterAnimations) {
            list.add(sSpriterAnimation.animationName);
        }
        for (CompositeItemVO sComposite : sComposites) {
            String[] additionalList = sComposite.composite.getRecursiveSpriterAnimationList();
            Collections.addAll(list, additionalList);
        }
        String[] finalList = new String[list.size()];
        list.toArray(finalList);

        return finalList;
    }

    public FontSizePair[] getRecursiveFontList() {
        HashSet<FontSizePair> list = new HashSet<FontSizePair>();
        for (LabelVO sLabel : sLabels) {
            list.add(new FontSizePair(sLabel.style.isEmpty() ? "arial" : sLabel.style, sLabel.size == 0 ? 12 : sLabel.size));
        }
        for (CompositeItemVO sComposite : sComposites) {
            FontSizePair[] additionalList = sComposite.composite.getRecursiveFontList();
            Collections.addAll(list, additionalList);
        }
        FontSizePair[] finalList = new FontSizePair[list.size()];
        list.toArray(finalList);

        return finalList;
    }

    public String[] getRecursiveShaderList() {
        HashSet<String> list = new HashSet<String>();
        for (MainItemVO item : getAllItems()) {
            if (item.shaderName != null && !item.shaderName.isEmpty()) {
                list.add(item.shaderName);
            }
        }
        String[] finalList = new String[list.size()];
        list.toArray(finalList);
        return finalList;
    }

    public ArrayList<MainItemVO> getAllItems() {
        ArrayList<MainItemVO> itemsList = new ArrayList<MainItemVO>();
        itemsList = getAllItemsRecursive(itemsList, this);

        return itemsList;
    }

    private ArrayList<MainItemVO> getAllItemsRecursive(ArrayList<MainItemVO> itemsList, CompositeVO compositeVo) {
        for (MainItemVO vo : compositeVo.sImage9patchs) {
            itemsList.add(vo);
        }
        for (MainItemVO vo : compositeVo.sImages) {
            itemsList.add(vo);
        }
        for (MainItemVO vo : compositeVo.sAtlasImages) {
            itemsList.add(vo);
        }
        for (MainItemVO vo : compositeVo.sLabels) {
            itemsList.add(vo);
        }
        for (MainItemVO vo : compositeVo.sLights) {
            itemsList.add(vo);
        }
        for (MainItemVO vo : compositeVo.sParticleEffects) {
            itemsList.add(vo);
        }
        for (MainItemVO vo : compositeVo.sTalosVFX) {
            itemsList.add(vo);
        }
        for (MainItemVO vo : compositeVo.sSpineAnimations) {
            itemsList.add(vo);
        }
        for (MainItemVO vo : compositeVo.sSpriterAnimations) {
            itemsList.add(vo);
        }
        for (MainItemVO vo : compositeVo.sSpriteAnimations) {
            itemsList.add(vo);
        }
        for (MainItemVO vo : compositeVo.sColorPrimitives) {
            itemsList.add(vo);
        }
        for (CompositeItemVO vo : compositeVo.sComposites) {
            itemsList = getAllItemsRecursive(itemsList, vo.composite);
            itemsList.add(vo);
        }

        return itemsList;
    }

    public LayerItemVO getLayerByName(String name) {
        for (LayerItemVO layer : layers) {
            if (layer.layerName.equals(name)) return layer;
        }
        return null;
    }

    public void loadFromEntity(Entity compositeEntity) {
        NodeComponent nodeComponent = compositeEntity.getComponent(NodeComponent.class);
        ComponentMapper<MainItemComponent> mainItemComponentMapper = ComponentMapper.getFor(MainItemComponent.class);
        ComponentMapper<LayerMapComponent> layerMainItemComponentComponentMapper = ComponentMapper.getFor(LayerMapComponent.class);

        if (nodeComponent == null) return;
        for (Entity entity : nodeComponent.children) {
            int entityType = mainItemComponentMapper.get(entity).entityType;
            if (entityType == EntityFactory.COMPOSITE_TYPE) {
                CompositeItemVO vo = new CompositeItemVO();
                vo.loadFromEntity(entity);
                sComposites.add(vo);
            }
            if (entityType == EntityFactory.IMAGE_TYPE) {
                SimpleImageVO vo = new SimpleImageVO();
                vo.loadFromEntity(entity);
                sImages.add(vo);
            }
            if (entityType == EntityFactory.ATLAS_IMAGE_TYPE) {
                AtlasImageVO vo = new AtlasImageVO();
                vo.loadFromEntity(entity);
                sAtlasImages.add(vo);
            }
            if (entityType == EntityFactory.NINE_PATCH) {
                Image9patchVO vo = new Image9patchVO();
                vo.loadFromEntity(entity);
                sImage9patchs.add(vo);
            }
            if (entityType == EntityFactory.LABEL_TYPE) {
                LabelVO vo = new LabelVO();
                vo.loadFromEntity(entity);
                sLabels.add(vo);
            }
            if (entityType == EntityFactory.PARTICLE_TYPE) {
                ParticleEffectVO vo = new ParticleEffectVO();
                vo.loadFromEntity(entity);
                sParticleEffects.add(vo);
            }
            if (entityType == EntityFactory.TALOS_TYPE) {
                TalosVO vo = new TalosVO();
                vo.loadFromEntity(entity);
                sTalosVFX.add(vo);
            }
            if (entityType == EntityFactory.SPRITE_TYPE) {
                SpriteAnimationVO vo = new SpriteAnimationVO();
                vo.loadFromEntity(entity);
                sSpriteAnimations.add(vo);
            }
            if (entityType == EntityFactory.SPINE_TYPE) {
                SpineVO vo = new SpineVO();
                vo.loadFromEntity(entity);
                sSpineAnimations.add(vo);
            }
            if (entityType == EntityFactory.SPRITER_TYPE) {
                SpriterVO vo = new SpriterVO();
                vo.loadFromEntity(entity);
                sSpriterAnimations.add(vo);
            }
            if (entityType == EntityFactory.LIGHT_TYPE) {
                LightVO vo = new LightVO();
                vo.loadFromEntity(entity);
                sLights.add(vo);
            }
            if (entityType == EntityFactory.COLOR_PRIMITIVE) {
                ColorPrimitiveVO vo = new ColorPrimitiveVO();
                vo.loadFromEntity(entity);
                sColorPrimitives.add(vo);
            }
        }

        LayerMapComponent layerMapComponent = layerMainItemComponentComponentMapper.get(compositeEntity);
        layers = layerMapComponent.getLayers();
    }

    @Override
    public String toString() {
        return "CompositeVO{" +
                "sImages=" + sImages +
                ", sAtlasImages=" + sAtlasImages +
                ", sImage9patchs=" + sImage9patchs +
                ", sLabels=" + sLabels +
                ", sComposites=" + sComposites +
                ", sParticleEffects=" + sParticleEffects +
                ", sTalosVFX=" + sTalosVFX +
                ", sLights=" + sLights +
                ", sSpineAnimations=" + sSpineAnimations +
                ", sSpriteAnimations=" + sSpriteAnimations +
                ", sSpriterAnimations=" + sSpriterAnimations +
                ", sColorPrimitives=" + sColorPrimitives +
                ", layers=" + layers +
                ", sStickyNotes=" + sStickyNotes +
                '}';
    }
}
