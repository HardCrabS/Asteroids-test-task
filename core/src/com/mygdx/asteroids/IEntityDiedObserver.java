package com.mygdx.asteroids;

import com.mygdx.asteroids.entities.Entity;

public interface IEntityDiedObserver {
    void onEntityDead(Entity entity);
}
