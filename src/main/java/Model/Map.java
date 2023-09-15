package Model;

import javafx.scene.image.Image;

import java.io.Serializable;

public class Map implements Serializable {

    int scaleMeter;
    String name;

    String imagepath;
    transient Image Photo;
    public Map(String name, int oneSideLength, Image Photo, String path)
    {
        this.Photo = Photo;
        this.name = name;
        this.scaleMeter = oneSideLength;
        this.imagepath = path;
    }
    public String getname()
    {
        return this.name;
    }

    public Image getImage()
    {
        return this.Photo;
    }

    public int getScale()
    {
        return this.scaleMeter;
    }

    public String getPath()
    {
        return this.imagepath;
    }
}
