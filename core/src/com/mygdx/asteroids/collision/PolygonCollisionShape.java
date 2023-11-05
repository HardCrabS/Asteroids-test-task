package com.mygdx.asteroids.collision;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Polygon;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class PolygonCollisionShape extends CollisionShape {
    public PolygonCollisionShape(Sprite sprite, float[] vertices) {
        super(sprite);
        mShape = new Polygon(vertices);
    }
    @Override
    public void update() {
        Polygon polygon = (Polygon)mShape;
        polygon.setRotation(mSprite.getRotation());
        polygon.setPosition(mSprite.getX() + mSprite.getOriginX(), mSprite.getY() + mSprite.getOriginY());
    }
    @Override
    public boolean collideVisit(CollisionShape collisionShape) {
        return collisionShape.collide(this);
    }

    @Override
    public boolean collide(CircleCollisionShape circle) {
        return Collision.overlaps((Polygon)mShape, (Circle)circle.mShape);
    }
    @Override
    public boolean collide(RectangleCollisionShape rectangle) {
        throw new NotImplementedException();
    }
    @Override
    public boolean collide(PolygonCollisionShape polygon) {
        throw new NotImplementedException();
    }
    @Override
    public void draw() {
        Polygon polygon = new Polygon(((Polygon)mShape).getVertices());
        polygon.setRotation(mSprite.getRotation());
        polygon.setPosition(mSprite.getX() + mSprite.getOriginX(), mSprite.getY() + mSprite.getOriginY());

        mShapeRenderer.setColor(0, 1, 0, 1);
        mShapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        mShapeRenderer.identity();
        mShapeRenderer.polygon(polygon.getTransformedVertices());
        mShapeRenderer.end();
    }
}
