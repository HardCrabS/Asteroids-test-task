package com.mygdx.asteroids.collision;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class RectangleCollisionShape extends CollisionShape{
    public RectangleCollisionShape(Sprite sprite, float x, float y, float width, float height) {
        super(sprite);
        mShape = new Rectangle(x, y, width, height);
    }

    @Override
    public boolean collideVisit(CollisionShape collisionShape) {
        return collisionShape.collide(this);
    }

    @Override
    public boolean collide(CircleCollisionShape circle) {
        return Collision.overlaps((Rectangle)mShape, (Circle)circle.mShape);
    }
    @Override
    public boolean collide(RectangleCollisionShape rectangle) {
        throw new NotImplementedException();
    }
    @Override
    public boolean collide(PolygonCollisionShape polygon) {
        throw new NotImplementedException();
    }
}
