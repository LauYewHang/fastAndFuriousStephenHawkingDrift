package sep.fastAndFuriousStephenHawkingDrift;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    public PerspectiveCamera cam; // a 3d camera object

    public Model model; // create a 3d model
    public ModelInstance instance; // store the data of 3d model

    public ModelBatch modelBatch; // object to draw 3d object
    public Environment environment;

    Vector3 position = new Vector3();

    @Override
    public void create() {
        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        // PerspectiveCamera: fieldOfViewY, viewportWidth, viewportHeight
        // fieldOfViewY: the Y depth of camera (how far you can see?)
        cam.position.set(10f, 10f, 10f);
        // set the position of camera
        cam.lookAt(0, 0, 0);
        // erm... look at there!!
        cam.near = 1f; // the nearest point of camera view frustum
        cam.far = 300f; // the furtest point of camera view frustum
        cam.update(); // recalculate and update the camera

        ModelBuilder modelBuilder = new ModelBuilder(); // modelbuilder to build a 3d model (www)
        Material mat = new Material(ColorAttribute.createDiffuse(Color.BLUE)); // material thingy
        model = modelBuilder.createBox(5, 5, 5, mat, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        // oh nyoooo it is math (| is bitwise operator), I believe this create the model at the center??
        instance = new ModelInstance(model); // store the created asset

        modelBatch = new ModelBatch(); // batch to render the 3d asset
        environment = new Environment(); // environment... hmm...
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        // erm... light...
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        // erm... more light...
    }

    @Override
    public void render(){
        movement();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        // the clear and redraw

        modelBatch.begin(cam);
        modelBatch.render(instance, environment);
        modelBatch.end();
    }

    @Override
    public void dispose(){
        model.dispose();
    }

    private void movement(){
        System.out.println(instance.transform.getTranslation(position));
        instance.transform.getTranslation(position);
        
        if (Gdx.input.isKeyPressed(Input.Keys.W))
            position.x += Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.S))
            position.x -= Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.A))
            position.z -= Gdx.graphics.getDeltaTime();
        if (Gdx.input.isKeyPressed(Input.Keys.D))
            position.z += Gdx.graphics.getDeltaTime();

        instance.transform.setTranslation(position);
    }
}
