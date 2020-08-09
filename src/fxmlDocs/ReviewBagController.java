package fxmlDocs;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import objects.Bag;
import objects.SQLComputer;


public class ReviewBagController {

    @FXML
    VBox checkoutVbox;
    @FXML
    Text grandTotal;
    @FXML
    ScrollPane pane;
    @FXML
    BorderPane a;

    @FXML
    HBox c;

    public void initialize(){
        ScrollPane topVB;
        Background back = new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), Insets.EMPTY));
        checkoutVbox.setBackground(back);
        pane.setBackground(back);
        a.setBackground(back);
        //topVB.setBackground(back);
        c.setBackground(back);

        //gets total for baf Text
        grandTotal.setText(Bag.getBagTotal());

        // for all computer in bag put get their image from DB and put config into layout of bag
        for(SQLComputer comp : Bag.compList){

            ImageView iw = new ImageView();
            iw.setFitHeight(Main.picSize); iw.setFitWidth(Main.picSize);
            iw.setImage(comp.getCurrentImage());



            Text t = new Text(comp.getCSVConfig());
            Text name =  new Text (comp.getName());
            Text price = new Text (comp.getPrice());
            VBox v = new VBox(name,price,t);
            v.setAlignment(Pos.CENTER_LEFT);
            v.setSpacing(5);
            v.setBackground(back);

            HBox h = new HBox(iw,v);
            h.setSpacing(20.0); h.setAlignment(Pos.CENTER_LEFT); h.setPadding(new Insets(0,0,0,15));

            checkoutVbox.getChildren().add(h);
        }

    }




    public void onMouseClick(MouseEvent event) {
        Button button = (Button) event.getSource();

        switch (button.getText()) {
            case "Remove All":
                Bag.compList.clear();
                Main.changeFXML("/fxmlDocs/reviewBagDoc.fxml");
                break;
            case "Order":
                break;
            case "Compare":
                Main.changeFXML("/fxmlDocs/compare2.fxml");
                break;
        }
    }

}



