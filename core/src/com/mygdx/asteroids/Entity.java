package com.mygdx.asteroids;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    protected Sprite mSprite;
    protected float mSpeed;
    protected Vector2 mVelocityDirection;

    public Entity(Sprite sprite, float speed)
    {
        mSprite = sprite;
        mSpeed = speed;
        mSprite.setOriginCenter();
    }
    public void draw(Batch batch)
    {
        mSprite.draw(batch);
    }
    public abstract void update(float deltaTime);
    public void setOriginBasedPosition(float x, float y) {
        mSprite.setOriginBasedPosition(x, y);
    }
    public Vector2 getOriginBasedPosition() {
        return new Vector2(mSprite.getX() + mSprite.getOriginX(), mSprite.getY() + mSprite.getOriginY());
    }
}
