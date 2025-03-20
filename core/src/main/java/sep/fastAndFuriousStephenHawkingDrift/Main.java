package sep.fastAndFuriousStephenHawkingDrift;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    public static final float VIRTUAL_WIDTH = 960;
    public static final float VIRTUAL_HEIGHT = 540;
    Screen screen;

    @Override
    public void create(){
        Gdx.input.setCatchKey(Input.Keys.BACK, true); // check (and catch) if the android's 'back' key is pressed, if it is pressed, it will terminate the program
        setScreen(new GameScreen(this));
    }

    @Override
    public void render(){
        Gdx.gl.glClearColor(0, 0, 0, 1); // classic gdx clear screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT); // classic gdx clear screen 2
        screen.render(Gdx.graphics.getDeltaTime()); // render the screen
    }

    @Override
    public void resize(int width, int height){
        screen.resize(width, height);
    }

    public void setScreen(Screen screen){
        if (this.screen != null){
            this.screen.hide();
            this.screen.dispose();
        }
        this.screen = screen;

        if (this.screen != null){
            this.screen.show();
            this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    @Override
    public void dispose(){

    }
}
