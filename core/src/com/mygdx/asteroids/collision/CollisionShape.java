package com.mygdx.asteroids.collision;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Shape2D;

public abstract class CollisionShape {
    protected Shape2D mShape;
    protected Sprite mSprite;
    protected ShapeRenderer mShapeRenderer;

    public CollisionShape(Sprite sprite) {
        mSprite = sprite;
        mShapeRenderer = new ShapeRenderer();
    }
    public void update() {}

    public abstract boolean collideVisit(CollisionShape collisionShape);
    public void draw() {}
    public abstract boolean collide(CircleCollisionShape circle);
    public abstract boolean collide(RectangleCollisionShape rectangle);
    public abstract boolean collide(PolygonCollisionShape polygon);
}
