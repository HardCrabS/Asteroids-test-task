package com.mygdx.asteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Shape2D;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    protected Sprite mSprite;
    protected Circle mCollisionShape;
    protected float mSpeed;
    protected Vector2 mVelocityDirection;
    private ShapeRenderer mShapeRenderer;

    public Entity(Sprite sprite, float speed)
    {
        mSprite = sprite;
        mSpeed = speed;
        mSprite.setOriginCenter();
        mCollisionShape = new Circle(0, 0, mSprite.getWidth()/2);
        mShapeRenderer = new ShapeRenderer();
    }
    public void draw(Batch batch) {
        mSprite.draw(batch);
    }
    public boolean isCollision(Entity other) {
        return Intersector.overlaps(getCollisionShape(), other.getCollisionShape());
    }
    public void drawCollisionShape() {
        mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        mShapeRenderer.setColor(0, 0, 1, 0.3f); // Blue color
        Vector2 pos = getOriginBasedPosition();
        mShapeRenderer.circle(pos.x, pos.y, mCollisionShape.radius);
        mShapeRenderer.end();
    }
    public abstract void update(float deltaTime);
    public void onCollision(Entity collision) {
        System.out.println(this + " collided with: " + collision);
    }
    public void setOriginBasedPosition(float x, float y) {
        mSprite.setOriginBasedPosition(x, y);
    }
    public Vector2 getOriginBasedPosition() {
        return new Vector2(mSprite.getX() + mSprite.getOriginX(), mSprite.getY() + mSprite.getOriginY());
    }
    private Circle getCollisionShape() {
        mCollisionShape.setPosition(getOriginBasedPosition());
        return mCollisionShape;
    }
}
