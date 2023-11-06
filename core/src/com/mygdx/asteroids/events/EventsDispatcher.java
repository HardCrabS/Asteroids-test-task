package com.mygdx.asteroids.events;

import com.mygdx.asteroids.entities.Entity;
import com.mygdx.asteroids.entities.Meteor;

import java.util.ArrayList;

public class EventsDispatcher {
    static ArrayList<IObserver> mMeteorKilledObservers = new ArrayList<>();
    static ArrayList<IObserver> mPlayerDiedObservers = new ArrayList<>();
    public static void addMeteorKilledObserver(IObserver observer) {mMeteorKilledObservers.add(observer);}
    public static void addPlayerDiedObserver(IObserver observer) {mPlayerDiedObservers.add(observer);}

    public static void fireMeteorKilledEvent(Meteor meteor, EventData eventData) {
        notifyAll(mMeteorKilledObservers, meteor, eventData);
    }
    public static void firePlayerDiedEvent(Entity entity, EventData eventData) {
        notifyAll(mPlayerDiedObservers, entity, eventData);
    }

    private static void notifyAll(ArrayList<IObserver> observers, Entity entity, EventData eventData) {
        for (IObserver observer : observers) {
            observer.onNotify(entity, eventData);
        }
    }
}
