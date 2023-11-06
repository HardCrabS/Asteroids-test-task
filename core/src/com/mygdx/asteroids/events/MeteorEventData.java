package com.mygdx.asteroids.events;

public class MeteorEventData extends EventData {
    public boolean isDestroyedByBullet;
    public MeteorEventData(EventID eventID, boolean isDestroyedByBullet) {
        super(eventID);
        this.isDestroyedByBullet = isDestroyedByBullet;
    }
}
