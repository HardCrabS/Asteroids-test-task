package com.mygdx.asteroids;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.asteroids.collision.CollisionShape;

public abstract class Entity {
    protected Sprite mSprite;
    protected CollisionShape mCollisionShape;
    protected float mSpeed;
    protected Vector2 mVelocityDirection;

    public void setObserver(IEntityDiedObserver observer) {
        this.mOnDieObserver = observer;
    }
    IEntityDiedObserver mOnDieObserver = null;

    public Entity(Sprite sprite, float speed)
    {
        mSprite = sprite;
        mSpeed = speed;
        mSprite.setOriginCenter();
    }
    public void draw(Batch batch) {
        mSprite.draw(batch);
    }
    public boolean isCollision(Entity other) {
        // TODO: check for matching tags?
        return mCollisionShape.collideVisit(other.mCollisionShape);
    }
    public void drawCollisionShape() {mCollisionShape.draw();}
    public void update(float deltaTime) {mCollisionShape.update();}
    public void onCollision(Entity collision) {}
    public void setOriginBasedPosition(float x, float y) {
        mSprite.setOriginBasedPosition(x, y);
    }
    public Vector2 getOriginBasedPosition() {
        return new Vector2(mSprite.getX() + mSprite.getOriginX(), mSprite.getY() + mSprite.getOriginY());
    }
    protected void die() {
        if (mOnDieObserver != null)
            mOnDieObserver.onEntityDead(this);
    }
}
