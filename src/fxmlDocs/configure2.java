package fxmlDocs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import objects.Bag;
import objects.SQLComputer;
import objects.SQLRW;

import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


public class configure2 {
    SQLComputer comp;

    @FXML
    BorderPane BorderPaneConfigure;
    @FXML
    VBox VBoxTopConfigure;
    @FXML
    HBox HBoxBottomConfigure;
//    @FXML
//    Text TextTitleCompare;
    @FXML
    HBox HBoxConfigureMain;
    @FXML
    ScrollPane ScrollPaneButtons;
    @FXML
    VBox VBoxButtons;
    @FXML
    Text TextCSVConfigure;
    @FXML
    Text TextPriceConfigure;
//    @FXML
//    Label LabelTileConfig;

    ImageView i;


    public void initialize(){
        //TextBagTotalConfigure.setText(Bag.getBagTotal());
        Background back = new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), Insets.EMPTY));

        BorderPaneConfigure.setBackground(back);
        VBoxTopConfigure.setBackground(back);
        HBoxBottomConfigure.setBackground(back);
        HBoxConfigureMain.setBackground(back);
        //ScrollPaneConfigure.setBackground(back);
        HBoxBottomConfigure.setBackground(back);
        VBoxButtons.setBackground(back);
        ScrollPaneButtons.setBackground(back);
        comp = new SQLComputer(Bag.currentComputerName);

        TextPriceConfigure.setText(Bag.currentComputerName+ " $"+comp.getPrice());
        TextCSVConfigure.setText(comp.getCSVConfig());


        i = new ImageView(comp.getCurrentImage()); i.setFitWidth(Main.picSize); i.setFitHeight(Main.picSize);
        HBoxConfigureMain.getChildren().add(0,i);

        LinkedHashMap<String, String> configvalueXprice = SQLRW.getAllConfigValuePrice(comp);

        for(String value : configvalueXprice.keySet()){
            String price = configvalueXprice.get(value);
            Button b = new Button(value+"  +$"+price);
            b.setPrefWidth(Main.picSize);
            b.setOnAction(this::onMouseClick);
            VBoxButtons.getChildren().add(b);
        }
        VBox fill = new VBox(); fill.setFillWidth(true); fill.setMinHeight(300); fill.setBackground(back); VBoxButtons.getChildren().add(fill);






    }

    public void onMouseClick(ActionEvent event) {
        String buttonText = ((Button) event.getSource()).getText();
        System.out.println(buttonText+"clicked");

        //Remove the configure from button text and get the name
        Scanner scan = new Scanner(buttonText);
        scan.useDelimiter("  ");
        String configValue = scan.next();

        comp.setCurrentConfig(configValue);
        i.setImage(comp.getCurrentImage());
        TextCSVConfigure.setText(comp.getCSVConfig());
        TextPriceConfigure.setText(Bag.currentComputerName+ " $"+comp.getPrice());
    }

    public void reviewBag(){
        Main.changeFXML("/fxmlDocs/reviewBagDoc.fxml");
    }
    public void  addToBag(){
        Bag.compList.add(comp);
        TextPriceConfigure.setText("Added To Bag Total: "+ Bag.getBagTotal());
        TimerTask updateText =  new TimerTask() {
            @Override
            public void run() {
                TextPriceConfigure.setText(Bag.currentComputerName+ " $"+comp.getPrice());
            }
        };
        new Timer().schedule(updateText,3000);
        comp = new SQLComputer(Bag.currentComputerName);
        TextCSVConfigure.setText(comp.getCSVConfig());
        i.setImage(comp.getCurrentImage());

    }
    public void compare(){
        Main.changeFXML("/fxmlDocs/compare2.fxml");
    }








}
