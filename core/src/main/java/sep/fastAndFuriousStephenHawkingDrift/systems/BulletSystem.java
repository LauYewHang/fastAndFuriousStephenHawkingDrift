package sep.fastAndFuriousStephenHawkingDrift.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.bullet.collision.btAxisSweep3;
import com.badlogic.gdx.physics.bullet.collision.btBroadphaseInterface;
import com.badlogic.gdx.physics.bullet.collision.btCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btCollisionDispatcher;
import com.badlogic.gdx.physics.bullet.collision.btDefaultCollisionConfiguration;
import com.badlogic.gdx.physics.bullet.collision.btGhostPairCallback;
import com.badlogic.gdx.physics.bullet.dynamics.btConstraintSolver;
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld;
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody;
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver;

import sep.fastAndFuriousStephenHawkingDrift.components.BulletComponent;
import sep.fastAndFuriousStephenHawkingDrift.components.CharacterComponent;

// extends EntitySystem: to add to Engine
// implements EntityListener: detect when an entity is (added to / deleted from) physic world
public class BulletSystem extends EntitySystem implements EntityListener{
    public btCollisionConfiguration collisionConfiguration;
    public btCollisionDispatcher dispatcher;
    public btBroadphaseInterface broadphase;
    public btConstraintSolver solver;
    public btDiscreteDynamicsWorld collisionWorld;
    private btGhostPairCallback ghostPairCallback;

    public int maxSubSteps = 5;
    public float fixedTimeStep = 1f / 60f;

    // method from EntitySystem: invoved when this EntitySystem is added to an Engine
    @Override
    public void addedToEngine(Engine engine){
        engine.addEntityListener(Family.all(BulletComponent.class).get(), this); // add `this` EntityListener to all Entity in Engine with type <BulletComponent>
    }

    public BulletSystem(){
        collisionConfiguration = new btDefaultCollisionConfiguration();
        dispatcher = new btCollisionDispatcher(collisionConfiguration);
        broadphase = new btAxisSweep3(new Vector3(-1000, -1000, -1000), new Vector3(1000, 1000, 1000));
        solver = new btSequentialImpulseConstraintSolver();
        collisionWorld = new btDiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
        ghostPairCallback = new btGhostPairCallback();
        broadphase.getOverlappingPairCache().setInternalGhostPairCallback(ghostPairCallback);
        this.collisionWorld.setGravity(new Vector3(0, 0.5f, 0));
    }

    @Override
    public void update(float deltatime){
        collisionWorld.stepSimulation(deltatime, maxSubSteps, fixedTimeStep);
    }

    public void dispose(){
        collisionWorld.dispose();
        if (solver != null)
            solver.dispose();
        if (broadphase != null)
            broadphase.dispose();
        if (dispatcher != null)
            dispatcher.dispose();
        if (collisionConfiguration != null)
            collisionConfiguration.dispose();
        ghostPairCallback.dispose();
    }

    public void removeBody(Entity entity){
        BulletComponent comp = entity.getComponent(BulletComponent.class);
        if (comp != null)
            collisionWorld.removeCollisionObject(comp.body);
        CharacterComponent character = entity.getComponent(CharacterComponent.class);
        if (character != null){
            collisionWorld.removeAction(character.characterController);
            collisionWorld.removeCollisionObject(character.ghostObject);
        }
    }

    @Override // method from EntityListener
    public void entityAdded(Entity entity) {
        BulletComponent bulletComponent = entity.getComponent(BulletComponent.class);
        if (bulletComponent.body != null){
            collisionWorld.addRigidBody((btRigidBody) bulletComponent.body);
        }
    }

    @Override // method from EntityListener
    public void entityRemoved(Entity entity) {

    }

}