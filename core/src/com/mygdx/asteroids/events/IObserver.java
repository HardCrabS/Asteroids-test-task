package com.mygdx.asteroids.events;

import com.mygdx.asteroids.entities.Entity;

public interface IObserver {
    void onNotify(Entity entity, EventData eventData);
}
