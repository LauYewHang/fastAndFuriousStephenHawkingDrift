package sep.fastAndFuriousStephenHawkingDrift;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class GameScreen implements Screen{
    Main game;
    GameWorld gameWorld;

    public GameScreen(Main game){
        this.game = game;
        gameWorld = new GameWorld();
        Gdx.input.setCursorCatched(true); // "focus" the cursor into the game, like you won't see your cursor anymore, it "blend" into it
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        gameWorld.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        gameWorld.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        gameWorld.dispose();
    }
}