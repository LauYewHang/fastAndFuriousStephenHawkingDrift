package sep.spacegladiators;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class StephenHawking extends Sprite{
    private Boolean isLeft;

    public StephenHawking(Texture texture){
        super(texture);
        this.isLeft = false;
    }

    public Boolean getFacing(){
        return this.isLeft;
    }

    public void setDirection(Direction direction){
        switch(direction){
            case LEFT:
                this.isLeft = true;
                break;
            case RIGHT:
                this.isLeft = false;
                break;
        }
    }
    
    public enum Direction{
        LEFT,
        RIGHT
    }
}