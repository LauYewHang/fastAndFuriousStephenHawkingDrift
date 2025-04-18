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
import com.badlogic.gdx.physics.bullet.Bullet;

import sep.fastAndFuriousStephenHawkingDrift.components.CharacterComponent;
import sep.fastAndFuriousStephenHawkingDrift.managers.EntityFactory;
import sep.fastAndFuriousStephenHawkingDrift.systems.BulletSystem;
import sep.fastAndFuriousStephenHawkingDrift.systems.PlayerSystem;
import sep.fastAndFuriousStephenHawkingDrift.systems.RenderSystem;

public class GameWorld{
    private static final float FOV = 67f;
    private ModelBatch modelBatch;
    private Environment environment;
    private PerspectiveCamera cam;

    // the player
    private Entity character;

    ModelBuilder modelBuilder = new ModelBuilder();

    Engine engine;
    public BulletSystem bulletSystem;

    // default vertex attributes
    long vertexAttribute = VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal;

    // walls
    Model wallHorizontal = modelBuilder.createBox(40, 20, 1, new Material(ColorAttribute.createDiffuse(Color.WHITE), ColorAttribute.createSpecular(Color.RED), FloatAttribute.createShininess(16f)), vertexAttribute);
    Model wallVertical = modelBuilder.createBox(1, 20, 40, new Material(ColorAttribute.createDiffuse(Color.GREEN), ColorAttribute.createSpecular(Color.WHITE), FloatAttribute.createShininess(16f)), vertexAttribute);
    Model groundModel = modelBuilder.createBox(40, 1, 40, new Material(ColorAttribute.createDiffuse(Color.YELLOW), ColorAttribute.createSpecular(Color.BLUE), FloatAttribute.createShininess(16f)), vertexAttribute);

    public GameWorld(){
        Bullet.init();
        initPersCamera();
        initEnvironment();
        initModelBatch();
        addSystems();
        addEntities();
    }

/*-------------------------------------------------------------------------------------------------*/
    // initialize 3D camera
    private void initPersCamera(){
        cam = new PerspectiveCamera(FOV, Main.VIRTUAL_WIDTH, Main.VIRTUAL_HEIGHT);
        /*
        cam.position.set(30f, 40f, 30f);
        cam.lookAt(0f, 0f, 0f);
        cam.near = 1f;
        cam.far = 300f;
        cam.update();
        */
    }

    // initialize environment
    private void initEnvironment(){
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.3f, 0.3f, 0.3f, 1f));
    }

    // initialize model batch
    private void initModelBatch(){
        modelBatch = new ModelBatch();
    }

/*-------------------------------------------------------------------------------------------------*/
    // add entities
    private void addEntities(){
        createGround();
        createPlayer(5, 3, 5);
    }

    private void addSystems(){
        engine = new Engine();
        engine.addSystem(new RenderSystem(modelBatch, environment));
        engine.addSystem(bulletSystem = new BulletSystem());
        engine.addSystem(new PlayerSystem(this, cam));
    }

/*-------------------------------------------------------------------------------------------------*/
    // create player entity
    private void createPlayer(float x, float y, float z){
        character = EntityFactory.createPlayer(bulletSystem, x, y, z);
        engine.addEntity(character);
        
        engine.addEntity(EntityFactory.createCharacter(bulletSystem, x, y, z));
        engine.addEntity(EntityFactory.createCharacter(bulletSystem, x, 40, z));
    }

/*-------------------------------------------------------------------------------------------------*/
    // building walls
    private void createGround(){
        engine.addEntity(EntityFactory.createStaticEntity(groundModel, 0, 0, 0));
        engine.addEntity(EntityFactory.createStaticEntity(wallHorizontal, 0, 10, -20));
        engine.addEntity(EntityFactory.createStaticEntity(wallHorizontal, 0, 10, 20));
        engine.addEntity(EntityFactory.createStaticEntity(wallVertical, 20, 10, 0));
        engine.addEntity(EntityFactory.createStaticEntity(wallVertical, -20, 10, 0));
    }

/*-------------------------------------------------------------------------------------------------*/
    public void dispose(){
        bulletSystem.collisionWorld.removeAction(character.getComponent(CharacterComponent.class).characterController);
        bulletSystem.collisionWorld.removeCollisionObject(character.getComponent(CharacterComponent.class).ghostObject);
        character.getComponent(CharacterComponent.class).characterController.dispose();
        character.getComponent(CharacterComponent.class).ghostObject.dispose();
        character.getComponent(CharacterComponent.class).ghostShape.dispose();

        bulletSystem.dispose();
        bulletSystem = null;
        
        wallHorizontal.dispose();
        wallVertical.dispose();
        groundModel.dispose();

        modelBatch.dispose();
        modelBatch = null;
    }

    public void resize(int width, int height){
        cam.viewportHeight = height;
        cam.viewportWidth = width;
    }

    public void render(float delta){
        renderWorld(delta);
    }

/*-------------------------------------------------------------------------------------------------*/
    private void renderWorld(float delta){
        modelBatch.begin(cam);
        engine.update(delta);
        modelBatch.end();
    }
}