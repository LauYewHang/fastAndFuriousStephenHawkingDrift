package sep.fastAndFuriousStephenHawkingDrift.managers;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.physics.bullet.Bullet;
import com.badlogic.gdx.physics.bullet.collision.btBoxShape;
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody.btRigidBodyConstructionInfo;

import sep.fastAndFuriousStephenHawkingDrift.components.BulletComponent;
import sep.fastAndFuriousStephenHawkingDrift.components.ModelComponent;
import sep.fastAndFuriousStephenHawkingDrift.render.MotionState;

public class EntityFactory{
    public static Entity createStaticEntity(Model model, float x, float y, float z){
        Bullet.init(); // VERY IMPORTANT
        
        System.err.println("initiate");
        final BoundingBox boundingBox = new BoundingBox();
        model.calculateBoundingBox(boundingBox);

        Vector3 tmpV = new Vector3();
        tmpV.set(boundingBox.getWidth() * 0.5f, boundingBox.getHeight() * 0.5f, boundingBox.getDepth() * 0.5f);
        
        System.err.print("btBoxShape: ");
        btCollisionShape col = new btBoxShape(tmpV);
        System.err.println("success");

        Entity entity = new Entity();
        ModelComponent ModelComponent = new ModelComponent(model, x, y, z);
        entity.add(ModelComponent);

        BulletComponent bulletComponent = new BulletComponent();
        bulletComponent.bodyInfo = new btRigidBodyConstructionInfo(0, null, col, Vector3.Zero);
        bulletComponent.body = new btRigidBody(bulletComponent.bodyInfo);
        bulletComponent.body.userData = entity;
        bulletComponent.motionState = new MotionState(ModelComponent.instance.transform);
        ((btRigidBody) bulletComponent.body).setMotionState(bulletComponent.motionState);

        entity.add(bulletComponent);
        System.err.println("success");
        return entity;
    }
}