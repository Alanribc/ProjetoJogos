package br.mackenzie;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MapManager {

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private Array<Rectangle> collisionRectangles;

    public MapManager(String mapaPath) {
        // Carrega o mapa
        map = new TmxMapLoader().load(mapaPath);
        renderer = new OrthogonalTiledMapRenderer(map, 1 / 32f); // tiles 32px
        collisionRectangles = new Array<>();
        carregarColisoes();
    }

    private void carregarColisoes() {
        // Lê a camada "Colisao" para detectar paredes/chão sólido
        MapLayer colisaoLayer = map.getLayers().get("Colisao");
        if (colisaoLayer != null) {
            MapObjects objects = colisaoLayer.getObjects();
            for (MapObject obj : objects) {
                Rectangle rect = ((com.badlogic.gdx.maps.objects.RectangleMapObject) obj).getRectangle();
                collisionRectangles.add(rect);
            }
        }
    }

    public void render(Viewport viewport) {
        // Cast para OrthographicCamera
        renderer.setView((OrthographicCamera) viewport.getCamera());
        renderer.render();
    }

    public Array<Rectangle> getCollisionRectangles() {
        return collisionRectangles;
    }

    public void dispose() {
        map.dispose();
        renderer.dispose();
    }
}
