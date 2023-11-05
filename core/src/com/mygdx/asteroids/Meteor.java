package com.mygdx.asteroids;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.asteroids.collision.CircleCollisionShape;

public class Meteor extends Entity {
    public Meteor(Sprite sprite) {
        super(sprite, 0);
    }
    public void setValues(Texture texture, float speed, Vector2 direction) {
        mSprite.setTexture(texture);
        mSprite.setSize(texture.getWidth(), texture.getHeight());
        mSprite.setRegion(0, 0, texture.getWidth(), texture.getHeight());
        mSprite.setOriginCenter();
        mSpeed = speed;
        mVelocityDirection = direction;
        mCollisionShape = new CircleCollisionShape(mSprite,mSprite.getX() + mSprite.getOriginX(), mSprite.getY() + mSprite.getOriginY(), mSprite.getWidth()/2);
    }
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 offset = mVelocityDirection.cpy().scl(mSpeed).scl(deltaTime);
        mSprite.translate(offset.x, offset.y);
    }
    @Override
    public void onCollision(Entity collision) {
        super.onCollision(collision);
        die();
    }
}
