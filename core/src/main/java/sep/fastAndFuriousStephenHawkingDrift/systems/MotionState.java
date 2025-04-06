package sep.fastAndFuriousStephenHawkingDrift.systems;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState;

// use for representing the physical transformation of the model instance
public class MotionState extends btMotionState{
    private final Matrix4 transform;

    public MotionState(final Matrix4 transform){
        this.transform = transform;
    }

    @Override
    public void getWorldTransform(final Matrix4 worldTrans){
        worldTrans.set(transform);
        // I think it is a bit counter-intuitive when it uses set for getter
        // but I believe the idea is that instead of returning the value, it directly change the value of argument
    }

    @Override
    public void setWorldTransform(final Matrix4 worldTrans){
        transform.set(worldTrans);
    }
}