package com.mygdx.asteroids;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.asteroids.entities.Entity;
import com.mygdx.asteroids.entities.Spaceship;
import com.mygdx.asteroids.events.*;

public class AsteroidsGame extends ApplicationAdapter implements IObserver {
	private SpriteBatch mBatch;
	private TiledDrawable mTiledBackground;
	private Player mPlayer;
	private MeteorSpawner mMeteorSpawner;
	private OrthographicCamera mCamera;
	private BitmapFont mFont;
	private int mDestroyedMeteorsCount = 0;
	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;
	private static final boolean DEBUG_DRAW = false;

	@Override
	public void create () {
		mCamera = new OrthographicCamera();
		mCamera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);

		mBatch = new SpriteBatch();
		TextureRegion textureRegion = new TextureRegion(ResourceHolder.get().getResource(ResourceId.Background));
		mTiledBackground = new TiledDrawable(textureRegion);
		mFont = new BitmapFont();
		mFont.getData().setScale(2);

		Sprite spaceShipSprite = new Sprite(ResourceHolder.get().getResource(ResourceId.Spaceship));
		Spaceship spaceship = new Spaceship(spaceShipSprite, 300.f);
		EntitiesController.get().registerEntity(spaceship);
		spaceship.setOriginBasedPosition(CAMERA_WIDTH / 2.f,CAMERA_HEIGHT / 2.f);
		mPlayer = new Player(spaceship, mCamera);

		EventsDispatcher.addPlayerDiedObserver(this);
		EventsDispatcher.addMeteorKilledObserver(this);

		EntitiesController.get().setBounds(CAMERA_WIDTH, CAMERA_HEIGHT);
		mMeteorSpawner = new MeteorSpawner(CAMERA_WIDTH, CAMERA_HEIGHT);
		mMeteorSpawner.fillWithMeteors();
	}

	@Override
	public void render () {
		update();

		ScreenUtils.clear(1, 0, 0, 1);
		mBatch.setProjectionMatrix(mCamera.combined);
		mBatch.begin();
		mTiledBackground.draw(mBatch, 0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EntitiesController.get().draw(mBatch);
		mFont.setColor(Color.WHITE);
		mFont.draw(mBatch, "Score: " + Integer.toString(mDestroyedMeteorsCount), 0, CAMERA_HEIGHT, 0, Align.left, false);
		mFont.draw(mBatch, "HP: " + Integer.toString(mPlayer.getSpaceship().getHealth()), CAMERA_WIDTH, CAMERA_HEIGHT, 0, Align.right, false);
		mBatch.end();
		if (DEBUG_DRAW)
			EntitiesController.get().drawCollisionShape();
	}

	private void update() {
		float deltaTime = Gdx.graphics.getDeltaTime();
		mPlayer.update(deltaTime);
		EntitiesController.get().update(deltaTime);
	}

	@Override
	public void dispose () {
		mBatch.dispose();
		mFont.dispose();
		ResourceHolder.get().dispose();
	}
	@Override
	public void onNotify(Entity entity, EventData eventData) {
		if (eventData.eventID == EventID.PLAYER_DIED) {
			Spaceship spaceship = (Spaceship) entity;
			System.out.println("------Player died! Restarting the game...");
			spaceship.setOriginBasedPosition(CAMERA_WIDTH / 2.f, CAMERA_HEIGHT / 2.f);
			spaceship.reset();
			mMeteorSpawner.resetMeteors();
			mDestroyedMeteorsCount = 0;
		} else if (eventData.eventID == EventID.METEOR_DESTROYED) {
			MeteorEventData eData = (MeteorEventData) eventData;
			if (eData.isDestroyedByBullet) {
				mDestroyedMeteorsCount++;
			}
		}
	}
}
