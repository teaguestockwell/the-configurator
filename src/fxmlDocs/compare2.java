package fxmlDocs;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import objects.Bag;
import objects.SQLRW;

import java.util.Scanner;

public class compare2 {


    @FXML
    VBox VBoxCompareScroll;
    @FXML
    ScrollPane ScrollPaneCompare;
    @FXML
    BorderPane BorderPaneCompare;


    public void initialize() {

            //TextBagTotal.setText(Bag.getBagTotal());

            int size = SQLRW.getTotalItems();
            System.out.println("Loading " + size + " items into Configurator from SQL BLOBS");

            // for each item in the table item, build a layout card
            for (int i = 1; i <= size; i++) {


                //load default image into left of Hbox
                ImageView iw = new ImageView();
                iw.setFitHeight(Main.picSize);
                iw.setFitWidth(Main.picSize);
                iw.setImage(SQLRW.getItemDefaultImageAtIndex(i));

                HBox hb = new HBox(iw);
                hb.setAlignment(Pos.CENTER);
                hb.setSpacing(50);

                //load name, description and config button into right of Hbox
                String name = SQLRW.getItemNameAtIndex(i);
                System.out.println(name + " loading....");
                Text n = new Text(name + " $" + SQLRW.getItemCostAtIndex(i));


                //use scanner to make more lines of text for each csv
                Scanner scan = new Scanner(SQLRW.getItemDescriptionAtIndex(i));
                scan.useDelimiter(",");
                VBox vb = new VBox(n);
                vb.setAlignment(Pos.TOP_LEFT);
                vb.setPadding(new Insets(40, 0, 0, 0));
                vb.setSpacing(5);
                while (scan.hasNext()) {
                    vb.getChildren().add(new Text(scan.next()));
                }
                Button b = new Button(name);
                b.setPrefWidth(Main.picSize);
                b.setOnAction(this::onMouseClick);
                vb.getChildren().add(b);


                hb.getChildren().add(vb);
                VBoxCompareScroll.getChildren().add(hb);

            }
            Background back = new Background(new BackgroundFill(Color.WHITE, new CornerRadii(0), Insets.EMPTY));
            VBoxCompareScroll.setBackground(back);
            ScrollPaneCompare.setBackground(back);
            BorderPaneCompare.setBackground(back);

    }

        /**
         * when mouse is clicked focus is cast to a Button and its .getText is called.
         * .getText of the button is passed to switch to assign action
         *
         * @param event the mouse click
         */
        public void onMouseClick (ActionEvent event){

            Bag.currentComputerName = ((Button) event.getSource()).getText();
            Main.changeFXML("/fxmlDocs/configure2.fxml");
        }


        public void reviewBag() {
            Main.changeFXML("/fxmlDocs/reviewBagDoc.fxml");
        }
    }


