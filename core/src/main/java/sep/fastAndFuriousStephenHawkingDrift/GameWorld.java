package sep.fastAndFuriousStephenHawkingDrift;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;

public class GameWorld{
    private static final float FOV = 67f;
    private ModelBatch modelBatch;
    private Environment environment;
    private PerspectiveCamera cam;

    public GameWorld(){
        initPersCamera();
        initEnvironment();
        initModelBatch();
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
    }

    public void render(float delta){
        modelBatch.begin(cam);
        modelBatch.end();
    }
}