package com.mygdx.asteroids;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Spaceship extends Entity {
    private Vector2 mFaceDirection;

    public Spaceship(Sprite sprite, float speed) {
        super(sprite, speed);
        float x = mSprite.getX();
        mFaceDirection = new Vector2(0, 1);
    }
    @Override
    public void update(float deltaTime) {
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
}
