package com.mygdx.asteroids.entities;

import com.badlogic.gdx.math.MathUtils;

public enum MeteorSize {
    Small, Medium, Large;

    public static MeteorSize randomSize()  {
        MeteorSize[] sizes = values();
        return sizes[MathUtils.random(0, sizes.length - 1)];
    }
}