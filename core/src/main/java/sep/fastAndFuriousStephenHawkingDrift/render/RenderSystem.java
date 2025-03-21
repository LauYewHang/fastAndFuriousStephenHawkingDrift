package sep.fastAndFuriousStephenHawkingDrift.render;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;

import sep.fastAndFuriousStephenHawkingDrift.components.ModelComponent;

public class RenderSystem extends EntitySystem{
    private ImmutableArray<Entity> entities;
    private ModelBatch batch;
    private Environment environment;

    public RenderSystem(ModelBatch batch, Environment environment){
        this.batch = batch;
        this.environment = environment;
        System.out.println("created render system");
    }

    @Override
    public void addedToEngine(Engine e){
        entities = e.getEntitiesFor(Family.all(ModelComponent.class).get());
        System.out.println("added entity");
        System.out.println(entities.size());
    }

    @Override
    public void update(float delta){
        System.out.println(entities.size());
        for (int i = 0; i < entities.size(); i++){
            ModelComponent mod = entities.get(i).getComponent(ModelComponent.class);
            batch.render(mod.instance, environment);
        }
    }
}