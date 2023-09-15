package Model;

import View.Gui;
import javafx.scene.image.Image;

import java.io.*;
import java.math.*;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.*;

public class Model {
    private Gui view;

    HashMap<String, Map> maps = new HashMap<String, Map>();
    float mx;
    float my;

    float tx;
    float ty;

    float heading;

    float length;

    int scale;


    public Model(Gui view)
    {
        this.view = view;
    }

    public void setMap()
    {
        view.setMapImage(new Image(view.getFileFromChooser("select map image").getAbsolutePath()));
    }

    public void SetScale(int sc)
    {
        scale = sc;
    }

    public void buildPresetsMenu()
    {
        for(String i: maps.keySet())
        {
            view.addMapToPresets(maps.get(i));
        }
    }
    public void setMapAndScale(String name)
    {
        Map tmp = maps.get(name);
        if(tmp.getImage() != null)
        {
            view.setMapImage(tmp.getImage());
            view.setScale(tmp.getScale());
            view.setMapName(tmp.getname());
        }
        else if(tmp.getImage() == null)
        {
            view.setMapImage(new Image(tmp.getPath()));
            view.setScale(tmp.getScale());
            view.setMapName(tmp.getname());
        }
    }


    public void addMapAndScale()
    {
        String name = view.getMapName();
        if(maps.containsKey(name))
        {
            view.promptPrintLn("Cannot add: Map already exists!");
        }
        else
        {
            view.promptPrintLn("Successfully Added map: " + view.getMapName());
            maps.put(name, new Map(name, view.getScale(), view.getImage(), view.getPathOfCurrentImage()));
            view.addMapToPresets(maps.get(view.getMapName()));
        }
    }

    public void deleteMapAndScale()
    {
        view.promptPrintLn("Deleting map " + view.getMapName() + "...");
        maps.remove(view.getMapName());
        view.deleteAllMapsFromPresets();
        buildPresetsMenu();
        view.promptPrintLn("Deleted Successfully!");
    }

    public void savePresets()
    {
        File file = view.getDirFromChooser("Choose file to save to (needs to be pre-created)", "presets2");
        try
        {
            FileOutputStream fis = new FileOutputStream(file);
            ObjectOutputStream bis = new ObjectOutputStream(fis);
            bis.writeObject(maps);
            view.promptPrintLn("Successfully saved to file!");
            bis.close();
            fis.close();
        }
        catch(NullPointerException npe)
        {
            view.promptPrintLn("No file was chosen: " + npe.getMessage());
        }
        catch(FileNotFoundException fne)
        {
            view.promptPrintLn("File not found: " + fne.getMessage());
        }
        catch(IOException ioe)
        {
            view.promptPrintLn("Writing error: " + ioe.getMessage());
        }
    }

    public void loadPresets()
    {
        File file = view.getFileFromChooser("Choose file to load from");
        try
        {
            maps.clear();
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream bis = new ObjectInputStream(fis);
            maps = (HashMap<String, Map>)bis.readObject();
            buildPresetsMenu();
            view.promptPrintLn("Successfully loaded from file! " + maps.size());

        }
        catch(FileNotFoundException fne)
        {
            view.promptPrintLn("File not found: " + fne.getMessage());
        }
        catch(ClassNotFoundException cnfe)
        {
            view.promptPrintLn("Class not found: " + cnfe.getMessage());
        }
        catch(IOException ioe)
        {
            view.promptPrintLn("Writing error: " + ioe.getMessage());
        }
    }


    public void setMyX(float value)
    {
        mx = value;
    }
    public void setMyY(float value)
    {
        my = value;
    }

    public void setTarX(float value)
    {
        tx = value;
    }
    public void setTarY(float value)
    {
        ty = value;
    }

    public float getMyX()
    {
        return mx;
    }
    public float getMyY()
    {
        return my;
    }

    public float getTarX()
    {
        return tx;
    }
    public float getTarY()
    {
        return ty;
    }



    public float calculateDistance()
    {
        try
        {
            float distance = (float)(sqrt(pow((tx-mx), 2) + pow((ty-my), 2)));
            return distance*scale/400;
        }
        catch(NumberFormatException nfe)
        {
            view.buildError("Scale illegal or empty", nfe.getMessage());
        }
        return 0.0f;
    }

    public float calculateHeading()
    {
        float distance = (float)(sqrt(pow((tx-mx), 2) + pow((ty-my), 2)));
        float actualtargx = tx-mx;
        float actualtergy = ty-my;
        if(tx == mx && ty > my)
        {
            return 0f;
        }
        else if(tx == mx && ty < my)
        {
            return 180f;
        }
        else if(ty == my && tx < mx)
        {
            return 90f;
        }
        else if(ty == my && tx > mx)
        {
            return 270f;
        }
        else if(tx>mx)
        {
            if(ty>my)
            {
                //angle between vertical axis and vector is a, heading is a-90
                //1 viertel
                float cosine = actualtergy/distance;
                //System.out.println("cosine is: " + cosine + ", difference in Y: " + actualtergy);
                float a = (float)toDegrees(acos(cosine));
                return a;
            }
            else if(ty<my)
            {
                float cosine = actualtergy/distance;
                float a = (float)toDegrees(acos(cosine));
                //System.out.println("cosine is: " + cosine + ", difference in Y: " + actualtergy);
                //angle between vertical axis and vector is 270<a<0
                //4 viertel
                return a;
            }
        }
        else if(tx<mx)
        {
            if(ty>my)
            {
                float cosine = actualtergy/distance;
                float a = (float)toDegrees(acos(cosine));
                //System.out.println("cosine is: " + cosine + ", difference in Y: " + actualtergy);
                //angle between vertical axis and vector is 270<a<0
                //2 viertel
                return 360-a;
            }
            else if(ty<my)
            {
                float cosine = actualtergy/distance;
                float a = (float)toDegrees(acos(cosine));
                //System.out.println("cosine is: " + cosine + ", difference in Y: " + actualtergy);
                //angle between vertical axis and vector is 270<a<0
                //3 viertel
                return 360-a;
            }
        }
        return Float.NaN;
    }

    public void setScale(int Scale)
    {
        this.scale = Scale;
    }

    /*public void changeListenerForMouse()
    {
        if(view.followsMouse())
        {
            view.setMouseListenerForCoordinates();
        }
        else if(!view.followsMouse())
        {
            view.removeMouseListenerForCoordinates();
        }
        else
        {
            view.promptPrintLn("Error: Could not set coordinates via mouse.");
        }
    }*/
}
