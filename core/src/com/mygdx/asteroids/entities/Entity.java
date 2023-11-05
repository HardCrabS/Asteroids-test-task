package com.mygdx.asteroids.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.asteroids.IEntityDiedObserver;
import com.mygdx.asteroids.collision.CollisionShape;

public abstract class Entity {
    public EntityState getEntityState() {
        return mState;
    }
    protected EntityState mState = EntityState.Active;
    protected Sprite mSprite;
    protected CollisionShape mCollisionShape;
    protected float mSpeed;
    protected Vector2 mVelocityDirection;
    private final int mCollisionLayer;
    private final int mCollisionMask;

    public void setObserver(IEntityDiedObserver observer) {
        this.mOnDieObserver = observer;
    }
    IEntityDiedObserver mOnDieObserver = null;

    public Entity(Sprite sprite, int colLayer, int colMask, float speed)
    {
        mSprite = sprite;
        mCollisionLayer = colLayer;
        mCollisionMask = colMask;
        mSpeed = speed;
        mSprite.setOriginCenter();
    }
    public void draw(Batch batch) {
        mSprite.draw(batch);
    }
    public boolean isCollision(Entity other) {
        return (mCollisionMask & other.mCollisionLayer) != 0 &&
                (other.mCollisionMask & mCollisionLayer) != 0 &&
                mCollisionShape.collideVisit(other.mCollisionShape);
    }
    public boolean isCollision(CollisionShape otherShape) {
        return mCollisionShape.collideVisit(otherShape);
    }
    public void drawCollisionShape() {mCollisionShape.draw();}
    public void update(float deltaTime) {mCollisionShape.update();}
    public void onCollision(Entity collision) {}
    public void onOutOfScreen() {}
    public void setOriginBasedPosition(float x, float y) {
        mSprite.setOriginBasedPosition(x, y);
    }
    public Vector2 getOriginBasedPosition() {
        return new Vector2(mSprite.getX() + mSprite.getOriginX(), mSprite.getY() + mSprite.getOriginY());
    }
    public void setRotation(float degrees) {
        mSprite.setRotation(degrees);
    }
    protected void die() {
        if (mOnDieObserver != null)
            mOnDieObserver.onEntityDead(this);
    }
    protected void destroy() {mState = EntityState.Destroyed;}
}
