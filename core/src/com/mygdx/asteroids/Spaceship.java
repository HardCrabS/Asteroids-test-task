package com.mygdx.asteroids;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.asteroids.collision.PolygonCollisionShape;

public class Spaceship extends Entity {
    private static final int MAX_HEALTH = 3;
    private static final float TIME_PER_SHOT = 0.5f;
    private static final float BULLET_SPEED = 500.f;
    private Vector2 mFaceDirection;
    private int mHealth = 3;
    private float mTimeSinceLastShot = 0.f;
    private Texture mBulletTexture;

    public Spaceship(Sprite sprite, float speed) {
        super(sprite, speed);
        mFaceDirection = new Vector2(0, 1);

        mBulletTexture = ResourceHolder.get().getResource(ResourceId.Bullet);
        float[] vertices = {-50, -30, -50, 0, 0, 45, 50, 0, 50, -30, 0, -40};
        mCollisionShape = new PolygonCollisionShape(mSprite, vertices);
    }
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 offset = mVelocityDirection.cpy().scl(mSpeed).scl(deltaTime);
        mSprite.translate(offset.x, offset.y);
        mTimeSinceLastShot += deltaTime;
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
    public void shoot() {
        if (mTimeSinceLastShot > TIME_PER_SHOT) {
            Bullet bullet = new Bullet(new Sprite(mBulletTexture), BULLET_SPEED, mFaceDirection);
            bullet.setOriginBasedPosition(
                    mSprite.getX() + mSprite.getOriginX() + mFaceDirection.x*50,
                    mSprite.getY() + mSprite.getOriginY() + mFaceDirection.y*50);
            bullet.setRotation(mSprite.getRotation());
            EntitiesController.get().registerEntity(bullet);
            mTimeSinceLastShot = 0.f;
        }
    }
    @Override
    public void onCollision(Entity collision) {
        super.onCollision(collision);
        mHealth--;
        System.out.println("Spaceship collided with " + collision + ". HP left: " + mHealth);
        if (mHealth <= 0)
            die();
    }
    public void reset() {
        mHealth=MAX_HEALTH;
        mTimeSinceLastShot = 0.f;
    }
}
