package com.mygdx.asteroids;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class EntitiesController {
    private static EntitiesController Instance;
    private final ArrayList<Entity> mEntities;
    private Vector2 mBounds;
    private EntitiesController() {
        mEntities = new ArrayList<>();
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
    }
    public void draw(Batch batch) {
        for (Entity entity: mEntities) {
            entity.draw(batch);
        }
    }
    public void registerEntity(Entity entity) {
        mEntities.add(entity);
    }
    public void setBounds(float boundX, float boundY) {
        mBounds = new Vector2(boundX, boundY);
    }
    private void clampToBounds(Entity entity) {
        Vector2 entityPos = entity.getOriginBasedPosition();
        if (entityPos.x < 0)
            entityPos.x = mBounds.x;
        else if (entityPos.x > mBounds.x)
            entityPos.x = 0;
        if (entityPos.y < 0)
            entityPos.y = mBounds.y;
        else if (entityPos.y > mBounds.y)
            entityPos.y = 0;
        entity.setOriginBasedPosition(entityPos.x, entityPos.y);
    }
}
