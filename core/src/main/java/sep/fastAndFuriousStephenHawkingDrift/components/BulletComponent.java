package sep.fastAndFuriousStephenHawkingDrift.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;

import sep.fastAndFuriousStephenHawkingDrift.render.MotionState;

public class BulletComponent implements Component{
    public MotionState motionState;
    public btRigidBody.btRigidBodyConstructionInfo bodyInfo;
    public btCollisionObject body;
}