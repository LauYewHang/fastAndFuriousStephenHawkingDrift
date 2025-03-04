package sep.spacegladiators;

import static java.lang.Math.abs;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private FitViewport viewport;
    private Texture image;

    // custom asset uwu
    private Texture poster;
    private Music bgm;
    private Texture stephenhawking;
    private StephenHawking stephenhawking_s;
    private Texture fire;
    private Sprite fire_s;

    // vector
    private Vector2 touchPos;
    private Vector2 stephenPos;

    @Override
    public void create() {
        batch = new SpriteBatch();
        viewport = new FitViewport(8, 5);
        image = new Texture("libgdx.png");

        // load the assets
        poster = new Texture("fastandfurious2.png");
        stephenhawking = new Texture("stephenhawking.png");
        stephenhawking_s = new StephenHawking(stephenhawking);
        stephenhawking_s.setOrigin(stephenhawking_s.getWidth()/2, stephenhawking_s.getHeight()/2);
        stephenhawking_s.setOriginCenter();
        stephenhawking_s.setSize(2, 2);
        stephenhawking_s.setPosition(0, 0);

        fire = new Texture("fire.png");
        fire_s = new Sprite(fire);
        fire_s.setSize(1, 1);
        fire_s.setOrigin(fire_s.getWidth()/2, fire_s.getHeight()/2);
        fire_s.setRotation(90);

        // the bgm
        bgm = Gdx.audio.newMusic(Gdx.files.internal("Max Coveri - Running In the 90's 8bit.mp3"));
        bgm.setLooping(true);
        bgm.setVolume(0.7f);
        bgm.play();

        // vector
        touchPos = new Vector2();
    }

    @Override
    public void render() {
        input();
        logic();
        draw();
    }

    private void input(){
        float speed = 6f;
        float dt = Gdx.graphics.getDeltaTime();

        stephenPos = new Vector2(stephenhawking_s.getX(), stephenhawking_s.getY());
        fire_s.setPosition(stephenPos.x, stephenPos.y);

        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            stephenhawking_s.translateY(speed * dt);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)){
            stephenhawking_s.translateY(-speed * dt);
        } 
        
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            stephenhawking_s.translateX(speed * dt);
            stephenhawking_s.setFlip(false, false);
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)){
            stephenhawking_s.translateX(-speed * dt);
            stephenhawking_s.setFlip(true, false);
        }

        if (Gdx.input.isTouched()){
            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
            viewport.unproject(touchPos);

            if (stephenPos.x < touchPos.x && abs(stephenPos.x - touchPos.x) > 0.5){
                stephenhawking_s.setFlip(false, false);
                stephenhawking_s.translateX(speed * dt);
            }else if (stephenPos.x > touchPos.x && abs(stephenPos.x - touchPos.x) > 0.5){
                stephenhawking_s.setFlip(true, false);
                stephenhawking_s.translateX(-speed * dt);
            }

            if (stephenPos.y < touchPos.y && abs(stephenPos.y - touchPos.y) > 0.5){
                stephenhawking_s.translateY(speed * dt);
            }else if (stephenPos.y > touchPos.y && abs(stephenPos.y - touchPos.y) > 0.5){
                stephenhawking_s.translateY(-speed * dt);
            }
        }
    }

    private void logic(){
        System.out.print(fire_s.getX());
        System.out.print(fire_s.getY());
        System.out.println();
    }

    private void draw(){
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();

        batch.draw(poster, 0, 0, worldWidth, worldHeight);
        fire_s.draw(batch);
        stephenhawking_s.draw(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width, height, true);
    }
}
