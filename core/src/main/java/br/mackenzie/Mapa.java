package br.mackenzie;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.ArrayList;

public class Mapa {
    private TiledMap mapa;
    private ArrayList<Rectangle> paredes;

    public Mapa(String caminho){
        mapa = new TmxMapLoader().load(caminho);
        paredes = new ArrayList<>();
    }

    MapLayer layerParedes = mapa.getLayers().get("paredes");
    if(layerParedes != null){
        for(MapObject obj : layerParedes.getObjects()){
            if(obj instanceof RectangleMapObject){
                Rectangle rect = ((RectangleMapObject) obj).getRectangle();
                paredes.add(rect);
            }
        }
    }

    public TiledMap getMapa(){
        return mapa;
    }

    public ArrayList<Rectangle> getParedes() {
        return paredes;
    }

    public void dispose(){
        mapa.dispose();
    }
}
