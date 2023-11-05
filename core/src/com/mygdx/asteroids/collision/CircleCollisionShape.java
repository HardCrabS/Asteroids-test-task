package com.mygdx.asteroids.collision;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class CircleCollisionShape extends CollisionShape {
    public CircleCollisionShape(Sprite sprite, float x, float y, float radius) {
        super(sprite);
        mShape = new Circle(x, y, radius);
    }
    @Override
    public void update() {
        Circle circle = (Circle) mShape;
        circle.setPosition(mSprite.getX() + mSprite.getOriginX(), mSprite.getY() + mSprite.getOriginY());
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
    public boolean collide(RectangleCollisionShape rectangle) {
        return Collision.overlaps((Circle)mShape, (Rectangle)rectangle.mShape);
    }
    @Override
    public boolean collide(PolygonCollisionShape polygon) {
        return Collision.overlaps((Circle)mShape, (Polygon)polygon.mShape);
    }
    @Override
    public void draw() {
        Circle circle = (Circle) mShape;
        mShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        mShapeRenderer.setColor(0, 0, 1, 0.3f);
        mShapeRenderer.circle(mSprite.getX() + mSprite.getOriginX(), mSprite.getY() + mSprite.getOriginY(), circle.radius);
        mShapeRenderer.end();
    }
}