package display;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.Board;



public class Main extends Application {

    /**
     * Number of rows and coloumns
     */
    private static int DIM;

    /**
     * Number of Conway iterations to perform. 0 for infinite
     */
    private static int iterations;

    /**
     * Color of a live cell.
     */
    private static final String CELL_ALIVE_COLOR = "-fx-background-color: #ffff00;\n"
            + "-fx-border-insets: 1;\n"
            + "-fx-border-width: 1;\n"
            + "-fx-border-style: solid;\n";

    /**
     * Color of a dead cell.
     */
    private static final String CELL_DEAD_COLOR = "-fx-background-color: #ffffff;\n"
            + "-fx-border-insets: 1;\n"
            + "-fx-border-width: 1;\n"
            + "-fx-border-style: solid;\n";

    /**
     * The Board of true-false cells
     */
    private Board board;

    /**
     *Start the Game by setting everything up and running.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Conway");
        this.board = new Board(DIM);
        VBox startVBox = new VBox(); //Vertical box with tile pane and hBox
        TilePane pane = new TilePane(); //Conway cells
        pane.setPrefColumns(DIM);

        //Make the Buttons
        for (int x = 0; x < DIM; x++) {
            for (int y = 0; y < DIM; y++) {
                Button cell = new Button();
                cell.setMinHeight(25);
                cell.setMinWidth(25);
                cell.setMaxHeight(25);
                cell.setMaxWidth(25);
                cell.setUserData(x + "," + y + "," + Boolean.toString(false)); //Store the cell data
                cell.setStyle("");
                int finalY = y;
                int finalX = x;
                cell.setStyle(CELL_DEAD_COLOR);
                cell.setOnAction( (ActionEvent e) -> {
                    this.board.switchCell(finalX, finalY); //Update backend
                    if (this.board.getCell(finalX, finalY)){
                        cell.setStyle(CELL_ALIVE_COLOR);
                    }
                    else{
                        cell.setStyle(CELL_DEAD_COLOR);
                    }


                });
                pane.getChildren().add(cell);
            }
        }
        Button start = new Button("Start");
        Button stop = new Button("Stop");

        start.setOnAction(e->{
            for (Node child : pane.getChildren()){
                Button cell = (Button) child;
                cell.setOnAction(r -> {});
            }
            int i = 0;
            while (i < iterations){
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                this.board.next();
                this.update(board, pane);
            }
        });

        stop.setOnAction(e-> Platform.exit());

        HBox buttons = new HBox();//Hbox of buttons
        buttons.getChildren().addAll(start, stop);

        startVBox.getChildren().addAll(pane, buttons);

        Scene startScene = new Scene(startVBox);
        primaryStage.setScene(startScene);
        primaryStage.show();
    }


    /**
     * Update the GUI from the backend
     * @param board
     */
    private void update(Board board, TilePane pane){
        for( Node child: pane.getChildrenUnmodifiable()) {
            if (child instanceof Button){
                Button button = (Button) child;
                String[] data = button.getUserData().toString().split(",");
                int x = Integer.parseInt(data[0]);
                int y = Integer.parseInt(data[1]);
                button.setUserData(x + "," + y + "," + board.getCell(x, y));
                if (board.getCell(x, y)){
                    button.setStyle(CELL_ALIVE_COLOR);
                }
                else{
                    button.setStyle(CELL_DEAD_COLOR);
                }
            }
        }
    }

    public static void main(String[] args) {
        if (args.length == 2){
            DIM = Integer.parseInt(args[0]);
            iterations = Integer.parseInt(args[1]);
            Application.launch(args);
        }
        else{
            System.out.println("Error. Input must be 2 numbers. The Dimensions and then the number of iterations");
        }
    }
}
