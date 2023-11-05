package com.mygdx.asteroids;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Meteor extends Entity {
    public Meteor(Sprite sprite) {
        super(sprite, 0);
    }
    public void setValues(Texture texture, float speed, Vector2 direction) {
        mSprite.setTexture(texture);
        mSprite.setBounds(0, 0, texture.getWidth(), texture.getHeight());
        mSprite.setRegion(0, 0, texture.getWidth(), texture.getHeight());
        mSprite.setOriginCenter();
        mCollisionShape = new Circle(0, 0, mSprite.getWidth()/2);
        mSpeed = speed;
        mVelocityDirection = direction;
    }
    @Override
    public void update(float deltaTime) {
        Vector2 offset = mVelocityDirection.cpy().scl(mSpeed).scl(deltaTime);
        mSprite.translate(offset.x, offset.y);
    }
}
