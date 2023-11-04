package com.mygdx.asteroids;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class MeteorSpawner {
    private static final int METEORS_MAX = 10;
    private static final int SPEED_MAX = 50;
    private int currentlySpawned = 0;
    private Vector2 mBounds;
    public MeteorSpawner(float xBoundary, float yBoundary) {
        mBounds = new Vector2(xBoundary, yBoundary);
        fillWithMeteors();
    }
    private void fillWithMeteors() {
        int attempts = 0;
        while (currentlySpawned < METEORS_MAX) {
            Vector2 randPoint = getRandomPoint(0, 0, mBounds.x, mBounds.y);
            MeteorSize meteorSize = MeteorSize.randomSize();
            if (!isCorrectPlaceForSpawn(randPoint, meteorSize)) {
                attempts++;
                if (attempts > 30) {
                    System.out.println("Meteors spawn exceeded attempts limit!");
                    break;
                }
                continue;
            }
            spawnMeteor(randPoint, meteorSize);
            currentlySpawned++;
        }
    }
    private boolean isCorrectPlaceForSpawn(Vector2 point, MeteorSize meteorSize) {
        // TODO: check for collision between all entities
        return true;
    }
    private Vector2 getRandomPoint(float minX, float minY, float maxX, float maxY) {
        float randomX = MathUtils.random(minX, maxX);
        float randomY = MathUtils.random(minY, maxY);

        return new Vector2(randomX, randomY);
    }
    private void spawnMeteor(Vector2 pos, MeteorSize meteorSize) {
        Texture texture = ResourceHolder.get().getRandomMeteorTexture(meteorSize);
        Sprite sprite = new Sprite(texture);
        Vector2 randDirection = getRandomPoint(-1f, -1f, 1f, 1f);
        float randSpeed = MathUtils.random(0, SPEED_MAX);
        Meteor meteor = new Meteor(sprite, randSpeed, randDirection, meteorSize);
        meteor.setOriginBasedPosition(pos.x, pos.y);
        EntitiesController.get().registerEntity(meteor);
        // TODO: subscribe for meteor dead state to immediately respawn it
    }
}
