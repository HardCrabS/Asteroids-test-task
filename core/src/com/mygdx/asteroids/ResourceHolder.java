package com.mygdx.asteroids;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.mygdx.asteroids.entities.MeteorSize;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ResourceHolder implements Disposable {
    private static ResourceHolder Instance = null;
    private final HashMap<ResourceId, Texture> mResources;
    private final HashMap<MeteorSize, ArrayList<Texture>> mMeteorTextures;

    private ResourceHolder()
    {
        mResources = new HashMap<>();
        mResources.put(ResourceId.Background, new Texture("BG.png"));
        mResources.put(ResourceId.Spaceship, new Texture("spaceShip.png"));
        mResources.put(ResourceId.Bullet, new Texture("bullet.png"));

        mMeteorTextures = new HashMap<>();
        for (MeteorSize size: MeteorSize.values()) {
            ArrayList<Texture> meteors = new ArrayList<>();
            for (int i = 0; ; i++) {
                String path = String.format("Meteors/%s/meteor_%d.png", size.toString(), i);
                if (fileExists(path))
                    meteors.add(new Texture(path));
                else
                    break;
            }
            mMeteorTextures.put(size, meteors);
        }
    }

    public static ResourceHolder get()
    {
        if (Instance == null)
            Instance = new ResourceHolder();
        return Instance;
    }

    public Texture getResource(ResourceId rId)
    {
        return mResources.get(rId);
    }
    public Texture getRandomMeteorTexture(MeteorSize meteorSize) {
        ArrayList<Texture> meteors = mMeteorTextures.get(meteorSize);
        return meteors.get(MathUtils.random(0, meteors.size() - 1));
    }
    private boolean fileExists(String path) {
        return new File(path).exists();
    }
    @Override
    public void dispose() {
        for (Texture texture : mResources.values()) {
            texture.dispose();
        }
        for (ArrayList<Texture> meteors : mMeteorTextures.values()) {
            for (Texture texture : meteors) {
                texture.dispose();
            }
        }
    }
}
