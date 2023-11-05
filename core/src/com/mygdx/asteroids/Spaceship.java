package com.mygdx.asteroids;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.asteroids.collision.PolygonCollisionShape;

public class Spaceship extends Entity {
    private Vector2 mFaceDirection;

    public Spaceship(Sprite sprite, float speed) {
        super(sprite, speed);
        mFaceDirection = new Vector2(0, 1);

        float[] vertices = {-50, -30, -50, 0, 0, 45, 50, 0, 50, -30, 0, -40};
        mCollisionShape = new PolygonCollisionShape(mSprite, vertices);
    }
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 offset = mVelocityDirection.cpy().scl(mSpeed).scl(deltaTime);
        mSprite.translate(offset.x, offset.y);
    }
    public void move(Vector2 direction) {
        mVelocityDirection = direction;
    }
    public void lookAt(Vector2 point) {
        Vector2 spritePos = new Vector2(mSprite.getX() + mSprite.getOriginX(), mSprite.getY() + mSprite.getOriginY());
        Vector2 dirToPoint = point.sub(spritePos).nor();
        float angleRad = MathUtils.atan2(mFaceDirection.crs(dirToPoint), mFaceDirection.dot(dirToPoint));
        mSprite.rotate(MathUtils.radiansToDegrees * angleRad);
        mFaceDirection = dirToPoint;
    }
    @Override
    public void onCollision(Entity collision) {
        super.onCollision(collision);
        die();
    }
}
