package View;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import Controller.*;
import Model.*;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Gui extends BorderPane {
    private Controller ctrl;

    private Model model;

    private MouseController mctrl;

    int shotcount = 0;

    // private DragController dctrl;

    private Image placeholder = new Image("C://Users//ulka8//eclipse-workspace//demo1//src//main//java//Model//placeholder.png");
    //private Image mapImage;

    private MenuBar menubar;
    private Menu Map;
    Slider SBX;
    Slider SBY;
    private MenuItem LoadMapImage;
    //private MenuItem SetScale;
    private Menu PresetScalesAndMaps;

    private Menu MapActions;
    private MenuItem add;
    private MenuItem delete;
    private MenuItem save;
    private MenuItem load;

    private CheckBox followMouse;

    //private RadioButton forMe;
    //private RadioButton forEnemy;

    private String imageFilePath = "C://Users//ulka8//eclipse-workspace//demo1//src//main//java//Model//placeholder.png";

    //private MenuItem[];

    private TextField heading;

    File presets = new File("C:\\Users\\ulka8\\eclipse-workspace\\ArtyHint\\src\\main\\java\\Data\\data.bin");

    private TextField scale;

    private TextField mapName;

    private TextField range;
    private TextField enterYourXManual;
    private TextArea prompt;

    private TextField enterYourYManual;

    private TextField enterTargetYManual;
    private TextField enterTargetXManual;

    private Button calculate;

    ToggleGroup tg = new ToggleGroup();


    public GraphicsContext getGraphContext()
    {
        return Lines.getGraphicsContext2D();
    }


    private Canvas Lines;
    private Canvas LinesEnemy;

    private ImageView mapHolder;

    public Gui() {
        this.model = new Model(this);
        this.ctrl = new Controller(model, this);
        this.mctrl = new MouseController(model, this);
        //this.dctrl = new DragController(model, this);
        buildMenu();
        buildToolbarOnLeft();
        buildCenter();
        buildBottom();
        promptPrintLn("Version 1.2, coordinates are mouse addable only now" + System.lineSeparator() + "Hold ctrl to set your position, click without holding ctrl to set enemy");
    }

    public void buildMenu() {
        menubar = new MenuBar();

        Map = new Menu("Map and Scale");
        MapActions = new Menu("Map actions");
        add = new MenuItem("Add this map to preset");
        add.setUserData("addPres");
        add.setOnAction(ctrl);
        delete = new MenuItem("Delete this map from presets");
        delete.setUserData("delPres");
        delete.setOnAction(ctrl);
        save = new MenuItem("Save Presets to file");
        save.setUserData("savPres");
        save.setOnAction(ctrl);
        load = new MenuItem("Load Presets from file");
        load.setUserData("loaPres");
        load.setOnAction(ctrl);
        LoadMapImage = new MenuItem("Load image of map");
        LoadMapImage.setUserData("loadImage");
        PresetScalesAndMaps = new Menu("Preset Map and Scale");
        PresetScalesAndMaps.setUserData("preset");

        Menu DevTools = new Menu("recordings");

        MenuItem resetShots = new MenuItem("Reset prompt");
        resetShots.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                prompt.clear();
                shotcount = 0;
            }
        });
        MenuItem resetPrompt = new MenuItem("Record shot");
        resetPrompt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                shotcount++;
                promptPrintLn("Shot: " + shotcount + " Tx: " +model.getTarX() + ", Ty: " + model.getTarY() + ", Mx: " + model.getMyX() + ", My: " + model.getMyY());
            }
        });

        LoadMapImage.setOnAction(ctrl);
        PresetScalesAndMaps.setOnAction(ctrl);

        DevTools.getItems().addAll(resetPrompt, resetShots);
        Map.getItems().addAll(LoadMapImage, PresetScalesAndMaps);
        MapActions.getItems().addAll(add, delete, save, load);
        menubar.getMenus().addAll(Map, MapActions, DevTools);
        setTop(menubar);
    }

    public void addMapToPresets(Map map)
    {
        MenuItem mi = new MenuItem(
                map.getname());
        mi.setUserData(map);
        mi.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                model.setMapAndScale(((Map)mi.getUserData()).getname());
            }
        });
        PresetScalesAndMaps.getItems().add(mi);
    }

    public void deleteAllMapsFromPresets()
    {
        PresetScalesAndMaps.getItems().clear();
    }


    public void buildCenter() {
        Pane mainPane = new Pane();
        mapHolder = new ImageView();
        mapHolder.setImage(placeholder);
        mapHolder.setFitHeight(400);
        mapHolder.setFitWidth(400);
        BorderPane fp = new BorderPane();
        fp.setMaxSize(420, 420);
        Lines = new Canvas(400, 400);
        Lines.setOnMouseClicked(mctrl);
        GraphicsContext gc = Lines.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.beginPath();
        //gc.fillRect(0,0,400,400);
        //gc.moveTo(100, 100);
        //gc.lineTo(50, 50);
        setCenter(fp);


        SBX = new Slider();
        SBX.setOrientation(Orientation.HORIZONTAL);
        //SBX.setLayoutY(420);
        SBX.setMax(400);
        SBX.setMin(0);
        SBX.setBlockIncrement(1);
        SBX.showTickMarksProperty().set(true);
        SBX.showTickLabelsProperty().set(true);


        SBY = new Slider();
        SBY.setOrientation(Orientation.VERTICAL);
        //SBY.setLayoutX(420);
        SBY.setMaxWidth(400);
        SBY.setMax(400);
        SBY.setMin(0);
        SBY.setShowTickMarks(true);
        SBY.setShowTickLabels(true);
        SBY.setBlockIncrement(1);
        BorderPane bottom = new BorderPane();
        bottom.setRight(new Label("prfctizr"));
        bottom.setCenter(SBX);

        fp.setBottom(bottom);
        fp.setRight(SBY);
        fp.setCenter(Lines);

        mainPane.getChildren().addAll(mapHolder, fp);
        setCenter(mainPane);
    }

    public void setMapImage(Image img) {
        mapHolder.setImage(img);
    }

    public void buildToolbarOnLeft() {
        GridPane gp = new GridPane();
        gp.setStyle("-fx-border-color: black");
        gp.setHgap(10);
        gp.setVgap(8);
        mapName = new TextField("Map Name");
        enterYourXManual = new TextField();
        enterYourXManual.setPromptText("Your X");
        enterYourYManual = new TextField();
        enterYourYManual.setPromptText("Your Y");
        enterTargetXManual = new TextField();
        enterTargetXManual.setPromptText("Target X");
        enterTargetYManual = new TextField();
        enterTargetYManual.setPromptText("Target Y");

        //followMouse = new CheckBox("Mouse set position");
        //followMouse.setUserData("mouseCoords");
        //followMouse.setOnAction(ctrl);

        enterTargetXManual.setEditable(false);
        enterTargetYManual.setEditable(false);
        enterYourXManual.setEditable(false);
        enterYourYManual.setEditable(false);

        Label coordsFor = new Label("Choose coordinates for:");

        Label hdg = new Label("Heading");
        heading = new TextField();
        hdg.setLabelFor(heading);

        prompt = new TextArea();

        Label rng = new Label("Range");
        range = new TextField();
        rng.setLabelFor(range);

        scale = new TextField();
        scale.setPromptText("how many m is one side?");

        //forMe = new RadioButton("Me");
        //forMe.setUserData("me");
        //orMe.setOnAction(ctrl);
        //forEnemy = new RadioButton("Enemy");
        //forEnemy.setUserData("enemy");
        //forEnemy.setOnAction(ctrl);


        //forMe.setToggleGroup(tg);
       //forEnemy.setToggleGroup(tg);

        //forMe.setSelected(true);

        calculate = new Button("set Scale and recalculate");
        calculate.setUserData("calculate");
        calculate.setOnAction(ctrl);
        gp.setMaxWidth(150);
        gp.add(new Label("Coordinates"), 0, 1, 2, 1);
        gp.addRow(2, enterYourXManual, enterYourYManual);
        gp.addRow(3, enterTargetXManual, enterTargetYManual);
        gp.addRow(4, hdg, heading);
        gp.addRow(5, rng, range);
        gp.add(scale, 0, 6, 2, 1);
        gp.add(calculate, 0, 7, 2, 1);
        //gp.add(coordsFor, 0, 8, 2, 1);
        //gp.addRow(9, forMe, forEnemy);
        mapName.setPromptText("map name");
        gp.add(mapName, 0, 9, 2, 1);
        gp.add(prompt, 0, 10, 2, 2);
        //gp.add(followMouse, 0, 12, 2, 1);
        prompt.setPromptText("status prompt");
        prompt.setEditable(false);
        //gp.getChildren().addAll(enterYourXManual, enterYourYManual, enterTargetXManual, enterTargetYManual, heading, range, calculate);
        setLeft(gp);
    }

    public void buildError(String title, String message)
    {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setContentText(message);
        a.setTitle(title);
        a.showAndWait();
    }

    public float getValueX()
    {
        return (float)SBX.getValue();
    }

    public float getValueY()
    {
        return (float)SBY.getValue();
    }

    public void buildBottom() {
        GridPane gp = new GridPane();
    }

    public File getFileFromChooser(String title) {
        FileChooser fc = new FileChooser();
        fc.setTitle(title);
        File imageFile = fc.showOpenDialog(new Stage());
        imageFilePath = imageFile.getAbsolutePath();
        return imageFile;
    }

    public File getDirFromChooser(String title, String filename) {
        DirectoryChooser fc = new DirectoryChooser();
        fc.setTitle(title);
        String path = fc.showDialog(new Stage()).getAbsolutePath();
        File file = new File(path + "\\" +filename);
        return file;
    }

    public void promptPrintLn(String s)
    {
        prompt.setText(prompt.getText() + s + System.lineSeparator());
    }


    public String getPathOfCurrentImage()
    {
        return imageFilePath;
    }

    public String getMapName()
    {
        return mapName.getText();
    }

    public void setMapName(String name)
    {
        mapName.setText(name);
    }

    public void drawHorizLine(Color color, float height) {
        Lines.getGraphicsContext2D().setFill(color);
        Lines.getGraphicsContext2D().fillRect(0, 400 - height - 1, 400, 1);
    }

    public void drawVertLine(Color color, float width) {
        Lines.getGraphicsContext2D().setFill(color);
        Lines.getGraphicsContext2D().fillRect(width - 1, 0, 1, 400);
    }


    /*public void setMouseListenerForCoordinates()
    {
        Lines.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mev)
            {
                SBX.setValue(mev.getX());
                SBY.setValue(400-mev.getY());
                promptPrintLn("click detected on x: " + mev.getX() + " y: " + mev.getY());
            }
        });
    }*/

    public void removeMouseListenerForCoordinates()
    {
        Lines.setOnMouseClicked(null);
    }


    public void setEnterYourXManual(java.lang.Float X)
    {
        enterYourXManual.setText(X.toString());
    }

    public void setEnterYourYManual(java.lang.Float Y)
    {
        enterYourYManual.setText(Y.toString());
    }

    public void setEnterTargetXManual(java.lang.Float X) {
        enterTargetXManual.setText(X.toString());
    }

    public void setEnterTargetYManual(java.lang.Float Y)
    {
        enterTargetYManual.setText(Y.toString());
    }

    public void setRange(String s)
    {
        range.setText(s);
    }
    public void setHeading(String s)
    {
        heading.setText(s);
    }

    public int getScale()
    {
        try
        {
            return Integer.parseInt(scale.getText());
        }
        catch(NumberFormatException nfe)
        {
            buildError("Scale illegal or empty", nfe.getMessage());
        }
        return 0;
    }

    public Image getImage()
    {
        return mapHolder.getImage();
    }

    public boolean followsMouse()
    {
        return followMouse.isSelected();
    }
    public void setScale(int scaleToSet)
    {
        scale.setText(((java.lang.Integer)scaleToSet).toString());
    }
}
