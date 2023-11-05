package com.mygdx.asteroids;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.asteroids.collision.PolygonCollisionShape;

public class Bullet extends Entity {
    public Bullet(Sprite sprite, float speed, Vector2 direction) {
        super(sprite, speed);
        mVelocityDirection = direction;
        float widthHalf = mSprite.getWidth()/2;
        float heightHalf = mSprite.getHeight()/2;
        mCollisionShape = new PolygonCollisionShape(mSprite, new float[]{-widthHalf,-heightHalf,-widthHalf,heightHalf,widthHalf,heightHalf,widthHalf,-heightHalf});
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
        destroy();
    }
    @Override
    public void onOutOfScreen() {
        destroy();
    }
}
