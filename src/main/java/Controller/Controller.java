package Controller;

import View.Gui;
import Model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class Controller implements EventHandler<ActionEvent>{
    private Model model;
    private Gui view;

    public Controller(Model model, Gui Gui)
    {
        this.model = model;
        this.view = Gui;
    }

    @Override
    public void handle(ActionEvent ae) {
        String todo="";
        if(ae.getSource() instanceof MenuItem)
        {
            todo = ((MenuItem) ae.getSource()).getUserData().toString();
            if(todo.equals("loadImage"))
            {
                model.setMap();
            }
            else if(todo.equals("scale"))
            {
                model.setScale(view.getScale());
            }
            else if(todo.equals("addPres"))
            {
                model.addMapAndScale();
            }
            else if(todo.equals("delPres"))
            {
                model.deleteMapAndScale();
            }
            else if(todo.equals("savPres"))
            {
                model.savePresets();
            }
            else if(todo.equals("loaPres"))
            {
                model.loadPresets();
            }
        }
        /*else if(ae.getSource() instanceof RadioButton)
        {
            todo = ((RadioButton) ae.getSource()).getUserData().toString();
            if(todo.equals("me"))
            {
                view.setSliderListenerForMe();
            }
            else if(todo.equals("enemy"))
            {
                view.setSliderListenerForTarget();
            }
        }*/
        else if(ae.getSource() instanceof Button)
        {
            todo = ((Button) ae.getSource()).getUserData().toString();
            if(todo.equals("calculate"))
            {
                model.setScale(view.getScale());
                view.setRange(((java.lang.Float)model.calculateDistance()).toString());
                view.setHeading(((java.lang.Float)model.calculateHeading()).toString());
            }
        }
        else if(ae.getSource() instanceof Menu)
        {
            todo = ((Menu) ae.getSource()).getUserData().toString();
            if(todo.equals("preset"))
            {
                model.buildPresetsMenu();
            }
        }
        /*else if(ae.getSource() instanceof CheckBox)
        {
            todo = ((CheckBox) ae.getSource()).getUserData().toString();
            if(todo.equals("mouseCoords"))
            {
                model.changeListenerForMouse();
            }
        }*/
    }
}
