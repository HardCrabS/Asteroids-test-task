package com.mygdx.asteroids;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Meteor extends Entity {
    private MeteorSize mMeteorSize;
    public Meteor(Sprite sprite, float speed, Vector2 direction, MeteorSize meteorSize) {
        super(sprite, speed);
        mVelocityDirection = direction;
        mMeteorSize = meteorSize;
    }

    @Override
    public void update(float deltaTime) {
        Vector2 offset = mVelocityDirection.cpy().scl(mSpeed).scl(deltaTime);
        mSprite.translate(offset.x, offset.y);
    }
}
