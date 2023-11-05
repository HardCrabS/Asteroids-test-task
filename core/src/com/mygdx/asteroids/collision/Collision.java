package com.mygdx.asteroids.collision;

import com.badlogic.gdx.math.*;

public class Collision {
    public static boolean overlaps(Polygon p, Circle c) {
        float[] vertices = p.getTransformedVertices();
        Vector2 center = new Vector2(c.x, c.y);
        float squareRadius = c.radius * c.radius;
        for (int i = 0; i < vertices.length; i += 2) {
            if (i == 0) {
                if (Intersector.intersectSegmentCircle(new Vector2(
                        vertices[vertices.length - 2],
                        vertices[vertices.length - 1]), new Vector2(
                        vertices[i], vertices[i + 1]), center, squareRadius))
                    return true;
            } else {
                if (Intersector.intersectSegmentCircle(new Vector2(
                        vertices[i - 2], vertices[i - 1]), new Vector2(
                        vertices[i], vertices[i + 1]), center, squareRadius))
                    return true;
            }
        }
        return false;
    }
    public static boolean overlaps(Circle c, Polygon p) {return overlaps(p, c);}
    public static boolean overlaps(Circle c, Rectangle r) {return Intersector.overlaps(c, r);}
    public static boolean overlaps(Rectangle r, Circle c) {return Intersector.overlaps(c, r);}
    public static boolean overlaps(Circle c1, Circle c2) {return Intersector.overlaps(c1, c2);}
}
