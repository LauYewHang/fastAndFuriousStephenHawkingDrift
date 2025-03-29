package sep.fastAndFuriousStephenHawkingDrift;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import sep.fastAndFuriousStephenHawkingDrift.components.ModelComponent;
import static sep.fastAndFuriousStephenHawkingDrift.managers.EntityFactory.createStaticEntity;
import sep.fastAndFuriousStephenHawkingDrift.render.RenderSystem;

public class GameWorld{
    private static final float FOV = 67f;
    private ModelBatch modelBatch;
    private Environment environment;
    private PerspectiveCamera cam;

    ModelBuilder modelBuilder = new ModelBuilder();
    Material boxMaterial = new Material(ColorAttribute.createDiffuse(Color.WHITE), ColorAttribute.createSpecular(Color.RED), FloatAttribute.createShininess(16f));
    Model box = modelBuilder.createBox(5, 5, 5, boxMaterial, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
    Model sphere = modelBuilder.createSphere(10, 10, 10, 10, 10, boxMaterial, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

    Entity entity = new Entity();
    Entity entity2 = new Entity();
    Engine engine = new Engine();

    public GameWorld(){
        initPersCamera();
        initEnvironment();
        initModelBatch();

        entity.add(new ModelComponent(box, 10, 10, 10));
        entity.add(new ModelComponent(box, 20, 20, 20));
        entity2.add(new ModelComponent(sphere, 30, 20, 0));
        engine.addSystem(new RenderSystem(modelBatch, environment));
        engine.addEntity(entity);
        engine.addEntity(entity2);

        engine.addEntity(createStaticEntity(box, -30, -20, -40));
    }

    private void initPersCamera(){
        cam = new PerspectiveCamera(FOV, Main.VIRTUAL_WIDTH, Main.VIRTUAL_HEIGHT);
        cam.position.set(30f, 40f, 30f);
        cam.lookAt(0f, 0f, 0f);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
    }

    private void initEnvironment(){
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.3f, 0.3f, 0.3f, 1f));
    }

    private void initModelBatch(){
        modelBatch = new ModelBatch();
    }

    public void dispose(){
        modelBatch.dispose();
    }

    public void resize(int width, int height){
        cam.viewportHeight = height;
        cam.viewportWidth = width;
        System.err.println("GameWorld resize");
    }

    public void render(float delta){
        modelBatch.begin(cam);
        engine.update(delta);
        modelBatch.end();
    }
}