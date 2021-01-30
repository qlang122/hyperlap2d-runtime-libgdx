/*
 * ******************************************************************************
 *  * Copyright 2015 See AUTHORS file.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *   http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *****************************************************************************
 */

package games.rednblack.editor.renderer.factory.component;

import box2dLight.RayHandler;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import games.rednblack.editor.renderer.components.BoundingBoxComponent;
import games.rednblack.editor.renderer.components.DimensionsComponent;
import games.rednblack.editor.renderer.components.particle.ParticleComponent;
import games.rednblack.editor.renderer.data.MainItemVO;
import games.rednblack.editor.renderer.data.ParticleEffectVO;
import games.rednblack.editor.renderer.data.ProjectInfoVO;
import games.rednblack.editor.renderer.factory.EntityFactory;
import games.rednblack.editor.renderer.resources.IResourceRetriever;

/**
 * Created by azakhary on 5/22/2015.
 */
public class ParticleEffectComponentFactory extends ComponentFactory {


    public ParticleEffectComponentFactory(PooledEngine engine, RayHandler rayHandler, World world, IResourceRetriever rm) {
        super(engine, rayHandler, world, rm);
    }

    @Override
    public void createComponents(Entity root, Entity entity, MainItemVO vo) {
        createCommonComponents(entity, vo, EntityFactory.PARTICLE_TYPE);
        entity.remove(BoundingBoxComponent.class);
        createParentNodeComponent(root, entity);
        createNodeComponent(root, entity);
        createParticleComponent(entity, (ParticleEffectVO) vo);
    }

    @Override
    protected DimensionsComponent createDimensionsComponent(Entity entity, MainItemVO vo) {
        DimensionsComponent component = engine.createComponent(DimensionsComponent.class);

        ProjectInfoVO projectInfoVO = rm.getProjectVO();
        float boundBoxSize = 70f;
        component.boundBox = new Rectangle((-boundBoxSize / 2f) / projectInfoVO.pixelToWorld, (-boundBoxSize / 2f) / projectInfoVO.pixelToWorld, boundBoxSize / projectInfoVO.pixelToWorld, boundBoxSize / projectInfoVO.pixelToWorld);
        component.width = boundBoxSize / projectInfoVO.pixelToWorld;
        component.height = boundBoxSize / projectInfoVO.pixelToWorld;

        entity.add(component);
        return component;
    }

    protected ParticleComponent createParticleComponent(Entity entity, ParticleEffectVO vo) {
        ParticleComponent component = engine.createComponent(ParticleComponent.class);
        component.particleName = vo.particleName;
        component.transform = vo.transform;
		ParticleEffect particleEffect = new ParticleEffect(rm.getParticleEffect(vo.particleName));
        component.particleEffect = particleEffect;
        ProjectInfoVO projectInfoVO = rm.getProjectVO();
        component.worldMultiplier = 1f/projectInfoVO.pixelToWorld;
        component.scaleEffect(1f);

        entity.add(component);
        return component;
    }
}
