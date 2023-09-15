package Controller;

import Model.Model;
import View.Gui;
import javafx.event.EventHandler;
import javafx.scene.control.RadioButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class MouseController implements EventHandler<MouseEvent>
{
    private Model model;
    private Gui view;

    public MouseController(Model model, Gui Gui)
    {
        this.model = model;
        this.view = Gui;
    }

    @Override
    public void handle(MouseEvent me) {
        if(me.isControlDown())
        {
            view.getGraphContext().clearRect(0, 0, 400, 400);
            model.setMyX((float) me.getX());
            model.setMyY((float) (400-me.getY()));
            view.drawHorizLine(Color.RED, model.getTarY());
            view.drawVertLine(Color.RED, model.getTarX());
            view.drawHorizLine(Color.GREEN, model.getMyY());
            view.drawVertLine(Color.GREEN, model.getMyX());

            view.setEnterTargetXManual(model.getTarX());
            view.setEnterTargetYManual(model.getTarY());
            view.setEnterYourXManual(model.getMyX());
            view.setEnterYourYManual(model.getMyY());
            view.setRange(((java.lang.Float)model.calculateDistance()).toString());
            view.setHeading(((java.lang.Float)model.calculateHeading()).toString());

            //view.promptPrintLn("My click detected on x: " + me.getX() + " y: " + me.getY());
        }
        else
        {
            view.getGraphContext().clearRect(0, 0, 400, 400);
            model.setTarX((float) me.getX());
            model.setTarY((float) (400-me.getY()));
            view.drawHorizLine(Color.RED, model.getTarY());
            view.drawVertLine(Color.RED, model.getTarX());
            view.drawHorizLine(Color.GREEN, model.getMyY());
            view.drawVertLine(Color.GREEN, model.getMyX());

            view.setEnterTargetXManual(model.getTarX());
            view.setEnterTargetYManual(model.getTarY());
            view.setEnterYourXManual(model.getMyX());
            view.setEnterYourYManual(model.getMyY());
            view.setRange(((java.lang.Float)model.calculateDistance()).toString());
            view.setHeading(((java.lang.Float)model.calculateHeading()).toString());

            //view.promptPrintLn("Target click detected on x: " + me.getX() + " y: " + me.getY());
        }
    }
}
