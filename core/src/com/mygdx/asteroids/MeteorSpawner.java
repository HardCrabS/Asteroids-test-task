package com.mygdx.asteroids;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.asteroids.collision.CircleCollisionShape;
import com.mygdx.asteroids.entities.Entity;
import com.mygdx.asteroids.entities.Meteor;
import com.mygdx.asteroids.entities.MeteorSize;
import com.mygdx.asteroids.events.EventData;
import com.mygdx.asteroids.events.EventID;
import com.mygdx.asteroids.events.EventsDispatcher;
import com.mygdx.asteroids.events.IObserver;

import java.util.ArrayList;

public class MeteorSpawner implements IObserver {
    private static final int MAX_METEORS = 10;
    private static final int MAX_SPEED = 100;
    private static final int MAX_RANDOMIZE_ATTEMPTS = 30;
    private final Vector2 mBounds;
    private final ArrayList<Meteor> mMeteors;
    public MeteorSpawner(float xBoundary, float yBoundary) {
        mBounds = new Vector2(xBoundary, yBoundary);
        mMeteors = new ArrayList<>();
        EventsDispatcher.addMeteorKilledObserver(this);
    }
    public void fillWithMeteors() {
        for (int i = 0; i < MAX_METEORS; i++) {
            Meteor meteor = spawnMeteor();
            randomizeMeteor(meteor);
            EntitiesController.get().registerEntity(meteor);
        }
    }
    public void resetMeteors() {
        System.out.println("Resetting meteors...");
        for (Meteor meteor: mMeteors) {
            randomizeMeteor(meteor);
        }
    }
    private void randomizeMeteor(Meteor meteor) {
        MeteorSize meteorSize = MeteorSize.randomSize();
        Texture texture = ResourceHolder.get().getRandomMeteorTexture(meteorSize);
        Vector2 randPoint = findRandomPointForMeteor(texture.getWidth()/2.f);
        Vector2 randDirection = getRandomPoint(-1f, -1f, 1f, 1f);
        float randSpeed = MathUtils.random(0, MAX_SPEED);
        meteor.setOriginBasedPosition(randPoint.x, randPoint.y);
        meteor.setValues(texture, randSpeed, randDirection);
    }
    private Vector2 findRandomPointForMeteor(float radius) {
        CircleCollisionShape circleShape = new CircleCollisionShape();
        for (int i = 0; i < MAX_RANDOMIZE_ATTEMPTS; i++) {
            Vector2 randPoint = getRandomPoint(0, 0, mBounds.x, mBounds.y);
            circleShape.set(randPoint.x + radius, randPoint.y + radius, radius);
            if (!EntitiesController.get().isColliding(circleShape))
                return randPoint;
        }
        System.out.println("Couldn't find a proper place for meteor!");
        return Vector2.Zero;
    }
    private Vector2 getRandomPoint(float minX, float minY, float maxX, float maxY) {
        float randomX = MathUtils.random(minX, maxX);
        float randomY = MathUtils.random(minY, maxY);

        return new Vector2(randomX, randomY);
    }
    private Meteor spawnMeteor() {
        Meteor meteor = new Meteor(new Sprite());
        mMeteors.add(meteor);
        return meteor;
    }
    @Override
    public void onNotify(Entity entity, EventData eventData) {
        if (eventData.eventID == EventID.METEOR_DESTROYED) {
            randomizeMeteor((Meteor)entity);
        }
    }
}
