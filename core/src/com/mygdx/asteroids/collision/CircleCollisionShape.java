package com.mygdx.asteroids.collision;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;

public class CircleCollisionShape extends CollisionShape {
    public CircleCollisionShape() {
        super(null);
    }
    public CircleCollisionShape(Sprite sprite, float x, float y, float radius) {
        super(sprite);
        mShape = new Circle(x, y, radius);
    }
    public void set(float x, float y, float radius) {
        mShape = new Circle(x, y, radius);
    }
    @Override
    public void update() {
        if (mSprite != null) {
            Circle circle = (Circle) mShape;
            circle.setPosition(mSprite.getX() + mSprite.getOriginX(), mSprite.getY() + mSprite.getOriginY());
        }
    }
    @Override
    public boolean collideVisit(CollisionShape collisionShape) {
        return collisionShape.collide(this);
    }

    @Override
    public boolean collide(CircleCollisionShape circle) {
        return Collision.overlaps((Circle)mShape, (Circle)circle.mShape);
    }
    @Override
    public boolean collide(PolygonCollisionShape polygon) {
        return Collision.overlaps((Circle)mShape, (Polygon)polygon.mShape);
    }
    @Override
    public void draw() {
        if (mSprite == null)
            return;
        Circle circle = (Circle) mShape;
        mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        mShapeRenderer.setColor(0, 0, 1, 0.3f);
        mShapeRenderer.circle(mSprite.getX() + mSprite.getOriginX(), mSprite.getY() + mSprite.getOriginY(), circle.radius);
        mShapeRenderer.end();
    }
}
