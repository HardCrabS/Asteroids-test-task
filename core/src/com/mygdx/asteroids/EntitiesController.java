package com.mygdx.asteroids;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import java.util.*;

class Tuple <T1, T2> {
    public T1 v1;
    public T2 v2;
    public Tuple(T1 v1, T2 v2) {
        this.v1 = v1;
        this.v2 = v2;
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(v1);
    }
    @Override
    public boolean equals(Object other) {
        Tuple<T1, T2> tuple = (Tuple<T1, T2>)other;
        return Objects.deepEquals(this.v1, tuple.v1);
    }
}
public class EntitiesController {
    private static EntitiesController Instance;
    private final ArrayList<Entity> mEntities;
    private Vector2 mBounds;
    private final Set<Tuple<Entity, Entity>> mCollisionPairs;
    private final Set<Tuple<Entity, Entity>> mPrevCollisionPairs;

    private EntitiesController() {
        mEntities = new ArrayList<>();
        mCollisionPairs = new HashSet<>();
        mPrevCollisionPairs = new HashSet<>();
    }
    public static EntitiesController get() {
        if (Instance == null)
            Instance = new EntitiesController();
        return Instance;
    }
    public void update(float deltaTime) {
        for (Entity entity: mEntities) {
            entity.update(deltaTime);
            clampToBounds(entity);
        }
        checkCollisions();

        // remove destroyed
        Iterator<Entity> iterator = mEntities.iterator();
        while (iterator.hasNext()) {
            Entity entity = iterator.next();
            if (entity.getEntityState() == EntityState.Destroyed) {
                iterator.remove();
            }
        }
    }
    public void draw(Batch batch) {
        for (Entity entity: mEntities) {
            entity.draw(batch);
        }
    }
    public void drawCollisionShape() {
        for (Entity entity: mEntities) {
            entity.drawCollisionShape();
        }
    }
    public void registerEntity(Entity entity) {
        mEntities.add(entity);
    }
    public void setBounds(float boundX, float boundY) {
        mBounds = new Vector2(boundX, boundY);
    }
    private void checkCollisions() {
        mPrevCollisionPairs.clear();
        mPrevCollisionPairs.addAll(mCollisionPairs);
        mCollisionPairs.clear();
        for (Entity entity: mEntities) {
            for (Entity entityToCheckCollisionWith: mEntities) {
                if (entity != entityToCheckCollisionWith &&
                        entity.isCollision(entityToCheckCollisionWith)) {
                    int minHash = Math.min(entity.hashCode(), entityToCheckCollisionWith.hashCode());
                    // put object with min hash in front to eliminate duplicates
                    if (minHash == entity.hashCode())
                        mCollisionPairs.add(new Tuple<>(entity, entityToCheckCollisionWith));
                    else
                        mCollisionPairs.add(new Tuple<>(entityToCheckCollisionWith, entity));
                }
            }
        }
        for (Tuple<Entity, Entity> pair: mCollisionPairs) {
            if (!mPrevCollisionPairs.contains(pair)) {
                pair.v1.onCollision(pair.v2);
                pair.v2.onCollision(pair.v1);
            }
        }
    }
    private void clampToBounds(Entity entity) {
        Vector2 entityPos = entity.getOriginBasedPosition();
        if (entityPos.x >= 0 && entityPos.y >= 0 && entityPos.x <= mBounds.x && entityPos.y <= mBounds.y)
            return;

        float clampedX = (entityPos.x < 0) ? mBounds.x : (entityPos.x > mBounds.x) ? 0 : entityPos.x;
        float clampedY = (entityPos.y < 0) ? mBounds.y : (entityPos.y > mBounds.y) ? 0 : entityPos.y;

        entity.onOutOfScreen();
        entity.setOriginBasedPosition(clampedX, clampedY);
    }
}
