package com.mygdx.asteroids;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

public class AsteroidsGame extends ApplicationAdapter implements IEntityDiedObserver {
	private SpriteBatch mBatch;
	private TiledDrawable mTiledBackground;
	private Player mPlayer;
	private MeteorSpawner mMeteorSpawner;
	private OrthographicCamera mCamera;
	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;

	@Override
	public void create () {
		mCamera = new OrthographicCamera();
		mCamera.setToOrtho(false, CAMERA_WIDTH, CAMERA_HEIGHT);

		mBatch = new SpriteBatch();
		TextureRegion textureRegion = new TextureRegion(ResourceHolder.get().getResource(ResourceId.Background));
		mTiledBackground = new TiledDrawable(textureRegion);

		Sprite spaceShipSprite = new Sprite(ResourceHolder.get().getResource(ResourceId.Spaceship));
		Spaceship spaceship = new Spaceship(spaceShipSprite, 300.f);
		spaceship.setObserver(this);
		EntitiesController.get().registerEntity(spaceship);
		spaceship.setOriginBasedPosition(CAMERA_WIDTH / 2.f,CAMERA_HEIGHT / 2.f);
		mPlayer = new Player(spaceship, mCamera);

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
		mTiledBackground.draw(mBatch, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		EntitiesController.get().draw(mBatch);
		mBatch.end();
		EntitiesController.get().drawCollisionShape();
	}

	private void update()
	{
		float deltaTime = Gdx.graphics.getDeltaTime();
		mPlayer.update(deltaTime);
		EntitiesController.get().update(deltaTime);
	}

	@Override
	public void dispose () {
		mBatch.dispose();
		ResourceHolder.get().dispose();
	}

	@Override
	public void onEntityDead(Entity spaceship) {
		spaceship.setOriginBasedPosition(CAMERA_WIDTH / 2.f,CAMERA_HEIGHT / 2.f);
		mMeteorSpawner.resetMeteors();
	}
}
