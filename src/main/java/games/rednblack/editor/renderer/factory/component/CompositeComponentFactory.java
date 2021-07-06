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

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import games.rednblack.editor.renderer.box2dLight.RayHandler;
import games.rednblack.editor.renderer.components.CompositeTransformComponent;
import games.rednblack.editor.renderer.components.DimensionsComponent;
import games.rednblack.editor.renderer.components.LayerMapComponent;
import games.rednblack.editor.renderer.components.NodeComponent;
import games.rednblack.editor.renderer.data.CompositeItemVO;
import games.rednblack.editor.renderer.data.LayerItemVO;
import games.rednblack.editor.renderer.data.MainItemVO;
import games.rednblack.editor.renderer.factory.EntityFactory;
import games.rednblack.editor.renderer.resources.IResourceRetriever;

/**
 * Created by azakhary on 5/22/2015.
 */
public class CompositeComponentFactory extends ComponentFactory {

    public CompositeComponentFactory(PooledEngine engine, RayHandler rayHandler, World world, IResourceRetriever rm) {
        super(engine, rayHandler, world, rm);
    }

    @Override
    public void createComponents(Entity root, Entity entity, MainItemVO vo) {
        createCommonComponents(entity, vo, EntityFactory.COMPOSITE_TYPE);
        if(root != null) {
            createParentNodeComponent(root, entity);
        }
        createNodeComponent(root, entity);
        createCompositeComponents(entity, (CompositeItemVO) vo);
    }

    @Override
    protected DimensionsComponent createDimensionsComponent(Entity entity, MainItemVO vo) {
        DimensionsComponent component = engine.createComponent(DimensionsComponent.class);
        component.width = ((CompositeItemVO) vo).width;
        component.height = ((CompositeItemVO) vo).height;
        component.boundBox = new Rectangle(0,0,component.width,component.height);
        entity.add(component);
        return component;
    }

    @Override
    protected void createNodeComponent(Entity root, Entity entity) {
        if(root != null) {
            super.createNodeComponent(root, entity);
        }

        NodeComponent node = engine.createComponent(NodeComponent.class);
        entity.add(node);
    }

    protected void createCompositeComponents(Entity entity, CompositeItemVO vo) {
        CompositeTransformComponent compositeTransform = engine.createComponent(CompositeTransformComponent.class);

        compositeTransform.automaticResize = vo.automaticResize;
        compositeTransform.scissorsEnabled = vo.scissorsEnabled;
        compositeTransform.renderToFBO = vo.renderToFBO;

        LayerMapComponent layerMap = engine.createComponent(LayerMapComponent.class);
        if(vo.composite.layers.size() == 0) {
            vo.composite.layers.add(LayerItemVO.createDefault());
        }
        layerMap.setLayers(vo.composite.layers);

        entity.add(compositeTransform);
        entity.add(layerMap);
    }
}
