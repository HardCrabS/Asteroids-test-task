package com.mygdx.asteroids;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.asteroids.entities.Spaceship;

public class Player {
    public Spaceship getSpaceship() {
        return mSpaceship;
    }
    private final Spaceship mSpaceship;
    private final Camera mCamera;

    public Player(Spaceship spaceship, Camera camera) {
        mSpaceship = spaceship;
        mCamera = camera;
    }

    void update(float deltaTime)
    {
        Vector2 dir = new Vector2();
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            dir.x = -1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            dir.x = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            dir.y = 1;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            dir.y = -1;
        }
        mSpaceship.move(dir);

        Vector3 touchPos = new Vector3();
        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        mCamera.unproject(touchPos);
        mSpaceship.lookAt(new Vector2(touchPos.x, touchPos.y));

        if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            mSpaceship.shoot();
        }
    }
}
