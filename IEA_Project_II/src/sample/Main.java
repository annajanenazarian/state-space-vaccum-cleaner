//            if(checkMove('r'))
//                animation.addStep(agentY, ++agentX);
//            if(checkMove('l'))
//                animation.addStep(agentY, --agentX);
//            if(checkMove('d'))
//                animation.addStep(++agentY, agentX);
//            if(checkMove('u'))
//                animation.addStep(--agentY, agentX);

package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Main extends Application {

    public int TILE_SIZE = 80;
    public int WIDTH = 10; //x
    public int HEIGHT = 5; //y

    private BorderPane mainPane = new BorderPane();
    private GridPane boardPane, settingPane;

    public int[][] board_use;

    private TextField setY, setX, setBattery;
    private Button btnCreateRoom, generateSelected, btnWall, btnDirt, btnRechargeAgent, btnStartAgent;
    private CheckBox room, dirt, walls, removeComponent, cleanAgent, dirtAgent;

    ArrayList<Integer> cleanAgentX = new ArrayList<>();
    ArrayList<Integer> cleanAgentY = new ArrayList<>();
    ArrayList<Integer> dirtAgentX = new ArrayList<>();
    ArrayList<Integer> dirtAgentY = new ArrayList<>();

    private int mouseX, mouseY;

    @Override
    public void start(Stage primaryStage) throws Exception {
        initializeBoard();

        settingPane = new GridPane();

        mainPane.setCenter(roomPane());
        mainPane.setRight(settingsPane());

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(mainPane, 1400, 800));
        primaryStage.show();
    }

    private void initializeBoard() {
        // Initialization of the Board
        board_use = new int[HEIGHT][WIDTH];
        cleanAgentY.clear();
        cleanAgentX.clear();
        dirtAgentX.clear();
        dirtAgentY.clear();

        for (int i = 0; i < board_use.length; i++) {
            for (int j = 0; j < board_use[i].length; j++) {
                if (i == 0 && j == 0) {
                    board_use[i][j] = 1;
                    cleanAgentX.add(j);
                    cleanAgentY.add(i);
                } else if (i == 0 && j == 1) {
                    board_use[i][j] = 4;
                    dirtAgentY.add(i);
                    dirtAgentX.add(j);
                }
                else
                    board_use[i][j] = 0;
            }
        }
        if(WIDTH<14 && HEIGHT<10)
            TILE_SIZE = 80;
        else if(WIDTH<22 && HEIGHT<15)
            TILE_SIZE =  50;
        else if(WIDTH<27 && HEIGHT<19)
            TILE_SIZE =  40;
        else if(WIDTH<31 && HEIGHT<22)
            TILE_SIZE =  35;
        else if(WIDTH<36 && HEIGHT<26)
            TILE_SIZE =  30;
        else if(WIDTH<44 && HEIGHT<31)
            TILE_SIZE =  25;
        else
            TILE_SIZE =  20;
    }
    private Parent roomPane() {
        boardPane = new GridPane();
        boardPane.setPadding(new Insets(10));
        boardPane.setOnMousePressed(e-> {
            for( Node node: boardPane.getChildren()) {
                if( node.getBoundsInParent().contains(e.getSceneX(),  e.getSceneY())) {
                    mouseX = Integer.parseInt(GridPane.getRowIndex(node).toString());
                    mouseY = Integer.parseInt(GridPane.getColumnIndex(node).toString());

                    if(room.isSelected()){ }

                    if(removeComponent.isSelected()){
                        if(board_use[mouseX][mouseY] == 1){
                            for(int i=0; i<cleanAgentX.size(); i++){
                                if((cleanAgentX.get(i) == mouseY) && cleanAgentY.get(i)==mouseX) {
                                    cleanAgentX.remove(i);
                                    cleanAgentY.remove(i);
                                }
                            }
                        }
                        if(board_use[mouseX][mouseY] == 4){
                            for(int i=0; i<dirtAgentX.size(); i++){
                                if((dirtAgentX.get(i) == mouseY) && dirtAgentY.get(i)==mouseX) {
                                    dirtAgentX.remove(i);
                                    dirtAgentY.remove(i);
                                }
                            }
                        }
                        board_use[mouseX][mouseY] = 0;
                    }

                    else if(dirt.isSelected()){
                        if (board_use[mouseX][mouseY] == 0){
                            board_use[mouseX][mouseY] = 2;
                        }
                    }
                    else if(walls.isSelected()){
                        if (board_use[mouseX][mouseY] == 0){
                            board_use[mouseX][mouseY] = 3;
                        }
                    }else if(dirtAgent.isSelected()){
                        if (board_use[mouseX][mouseY] == 0){
                            board_use[mouseX][mouseY] = 4;
                            dirtAgentX.add(mouseY);
                            dirtAgentY.add(mouseX);
                        }
                    }else if(cleanAgent.isSelected()){
                        if (board_use[mouseX][mouseY] == 0){
                            board_use[mouseX][mouseY] = 1;
                            cleanAgentX.add(mouseY);
                            cleanAgentY.add(mouseX);
                        }
                    }
                    mainPane.setCenter(roomPane());
                }

            }
        });
        try {
            for (int i = 0; i < board_use.length; i++) {
                for (int j = 0; j < board_use[i].length; j++) {
                    System.out.print(board_use[i][j]);
                    switch (board_use[i][j]) {
                        case 4:
                            // DIRT AGENT
                            Image dirtAgent = new Image(Main.class.getResourceAsStream("virus.png"));
                            ImageView dirtAgentView = new ImageView();
                            dirtAgentView.setImage(dirtAgent);

                            dirtAgentView.setFitWidth(TILE_SIZE);
                            dirtAgentView.setFitHeight(TILE_SIZE);
                            dirtAgentView.setPreserveRatio(true);
                            dirtAgentView.setSmooth(true);
                            dirtAgentView.setCache(true);

                            boardPane.add(dirtAgentView, j, i, 1, 1);
                            break;

                        case 3:
                            // WALL
                            Image wall = new Image(Main.class.getResourceAsStream("close-1.png"));
                            ImageView wallView = new ImageView();
                            wallView.setImage(wall);

                            wallView.setFitWidth(TILE_SIZE);
                            wallView.setFitHeight(TILE_SIZE);
                            wallView.setPreserveRatio(true);
                            wallView.setSmooth(true);
                            wallView.setCache(true);

                            boardPane.add(wallView, j, i, 1, 1);
                            break;

                        case 2:
                            // DIRT
                            Image dirt = new Image(Main.class.getResourceAsStream("spot.png"));
                            ImageView dirtView = new ImageView();
                            dirtView.setImage(dirt);

                            dirtView.setFitWidth(TILE_SIZE);
                            dirtView.setFitHeight(TILE_SIZE);
                            dirtView.setPreserveRatio(true);
                            dirtView.setSmooth(true);
                            dirtView.setCache(true);

                            boardPane.add(dirtView, j, i, 1, 1);
                            break;

                        case 1:
                            // AGENT
                            Image vacuum = new Image(Main.class.getResourceAsStream("vacuum-cleaner.png"));
                            ImageView vacuumView = new ImageView();
                            vacuumView.setImage(vacuum);

                            vacuumView.setFitWidth(TILE_SIZE);
                            vacuumView.setFitHeight(TILE_SIZE);
                            vacuumView.setPreserveRatio(true);
                            vacuumView.setSmooth(true);
                            vacuumView.setCache(true);
                            boardPane.add(vacuumView, j, i, 1, 1);
                            break;

                        case 0:
                            // CLEAN TILE

                            ImageView tileView = new ImageView();
                            Image tile = new Image(Main.class.getResourceAsStream("tile.PNG"));
                            tileView.setImage(tile);

                            tileView.setFitWidth(TILE_SIZE);
                            tileView.setFitHeight(TILE_SIZE);
                            tileView.setPreserveRatio(true);
                            tileView.setSmooth(true);
                            tileView.setCache(true);

                            boardPane.add(tileView, j, i, 1, 1);
                            break;
                    }
                }
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println();
        for (int i=0; i<cleanAgentY.size(); i++){
            System.out.println("Clean Agent " + i + " -> agentX: " + cleanAgentX.get(i) + " agentY: " + cleanAgentY.get(i));
        }
        for (int i=0; i<dirtAgentX.size(); i++){
            System.out.println("Dirt Agent " + i + " -> agentX: " + dirtAgentX.get(i) + " agentY: " + dirtAgentY.get(i));
        }
        return boardPane;
    }
    private Parent settingsPane() {

        settingPane.setPadding(new Insets(30,50,30,30));
        settingPane.setHgap(10);
        settingPane.setVgap(10);
        settingPane.setAlignment(Pos.CENTER);

        setX = new TextField();
        setX.setPromptText("Set X");
        setX.setPrefWidth(90);
        settingPane.add(setX, 0, 0, 1, 1);

        setY = new TextField();
        setY.setPromptText("Set Y");
        setY.setPrefWidth(80);
        settingPane.add(setY, 1, 0, 1, 1);

        btnCreateRoom = new Button("Create New Room");
        btnCreateRoom.setPrefWidth(200);
        settingPane.add(btnCreateRoom, 0, 1, 2, 1);

        btnCreateRoom.setOnMouseClicked(e -> {
            WIDTH = Integer.parseInt(setX.getText());
            HEIGHT = Integer.parseInt(setY.getText());
            initializeBoard();
            mainPane.setCenter(roomPane());
        });

        room = new CheckBox("Room");
        settingPane.add(room, 0, 2, 1, 1);

        dirt = new CheckBox("Dirt");
        settingPane.add(dirt, 0, 3, 1, 1);

        walls = new CheckBox("Walls");
        settingPane.add(walls, 0, 4, 1, 1);

        dirtAgent = new CheckBox("Dirt Agent");
        settingPane.add(dirtAgent, 0, 5,1,1);

        cleanAgent = new CheckBox("Vacuum");
        settingPane.add(cleanAgent, 1, 5,1,1);

        removeComponent = new CheckBox("Remove");
        settingPane.add(removeComponent, 0, 6, 2, 1);

        generateSelected = new Button("Generate Selected Boxes");
        generateSelected.setPrefWidth(90);
        generateSelected.setWrapText(true);
        generateSelected.setTextAlignment(TextAlignment.CENTER);
        settingPane.add(generateSelected, 1, 2, 1, 3);

        generateSelected.setOnMouseClicked(e -> {
            Random random = new Random();

            if(room.isSelected()){
                WIDTH = random.nextInt(30);
                HEIGHT = random.nextInt(30);
                initializeBoard();
            }
            if(walls.isSelected()){
                int tempX = random.nextInt(HEIGHT);
                int tempY = random.nextInt(WIDTH);
                for(int i=0; i<5; i++){
                    if(board_use[tempX][tempY] == 0){
                        board_use[tempX][tempY] = 3;
                        tempX = random.nextInt(HEIGHT);
                        tempY = random.nextInt(WIDTH);
                    }
                    else {
                        tempX = random.nextInt(HEIGHT);
                        tempY = random.nextInt(WIDTH);
                    }
                }
            }
            if(dirt.isSelected()){
                int tempX = random.nextInt(HEIGHT);
                int tempY = random.nextInt(WIDTH);
                for(int i=0; i<5; i++){
                    if(board_use[tempX][tempY] == 0){
                        board_use[tempX][tempY] = 2;
                        tempX = random.nextInt(HEIGHT);
                        tempY = random.nextInt(WIDTH);
                    }
                    else {
                        tempX = random.nextInt(HEIGHT);
                        tempY = random.nextInt(WIDTH);
                    }
                }
            }

            mainPane.setCenter(roomPane());
        });

        setBattery = new TextField();
        setBattery.setPromptText("Set Battery");
        setBattery.setPrefWidth(90);
        settingPane.add(setBattery, 0, 7, 1, 1);

        btnRechargeAgent = new Button("Recharge Agent");
        btnRechargeAgent.setPrefWidth(90);
        btnRechargeAgent.setWrapText(true);
        btnRechargeAgent.setTextAlignment(TextAlignment.CENTER);
        settingPane.add(btnRechargeAgent, 1, 7, 1, 1);


        btnStartAgent = new Button("Start Agent");
        btnStartAgent.setPrefWidth(200);
        btnStartAgent.setWrapText(true);
        btnStartAgent.setTextAlignment(TextAlignment.CENTER);
        settingPane.add(btnStartAgent, 0, 8, 2, 1);

        btnStartAgent.setOnMouseClicked(e->{
            moveDirtAgent(dirtAgentX.get(0),1, dirtAgentY.get(0), 0, 1000);
            moveCleanAgent(cleanAgentX.get(0), 1, cleanAgentY.get(0),0, 1000);
            moveDirtAgent(dirtAgentX.get(0), 1, dirtAgentY.get(0),0, 1000);
            moveCleanAgent(cleanAgentX.get(0), 1, cleanAgentY.get(0),0, 1000);

        });

        return settingPane;
    }

    private boolean checkMove(char direction, int aX, int aY){
        switch (direction){
            case 'r':
                if( (aX == WIDTH-1) || (board_use[aY][aX+1] == 3) || ((board_use[aY][aX+1] == 1)) || (board_use[aY][aX+1] == 4))
                    return false;
                break;

            case 'l':
                if( (aX == 0) || (board_use[aY][aX-1] == 3) || (board_use[aY][aX-1] == 1) || (board_use[aY][aX-1] == 4))
                    return false;
                break;

            case 'u':
                if( (aY == 0) || (board_use[aY-1][aX] == 3) || (board_use[aY-1][aX] == 1) || (board_use[aY-1][aX] == 4) )
                    return false;
                break;

            case 'd':
                if( (aY == HEIGHT-1) || (board_use[aY+1][aX] == 3) || (board_use[aY+1][aX] == 1) || (board_use[aY+1][aX] == 4))
                    return false;
                break;

            default:
                break;
        }
        return true;
    }
    private void moveDirtAgent(final int aX, int x, final int aY, final int y, int delay){

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                switch (x){
                    case 1:
                        if (checkMove('r', aX, aY)){
                            board_use[aY][aX]=2;
                            board_use[aY][aX+1]=4;

                            int temp;

                            for (int i = 0; i<dirtAgentX.size(); i++){
                                if( (aX == dirtAgentX.get(i)) && (aY == dirtAgentY.get(i))) {
                                    temp = dirtAgentX.get(i);
                                    dirtAgentX.set(i, ++temp);
                                }
                            }

                        } else {
                            if (checkMove('d', aX, aY))
                                moveDirtAgent(aX,  0, aY, 1, delay);
                            else if (checkMove('l', aX, aY))
                                moveDirtAgent(aX,  -1, aY, 0, delay);
                            else if (checkMove('u', aX, aY))
                                moveDirtAgent(aX,  0, aY, -1, delay);
                        }
                        break;

                    case -1:
                        if (checkMove('l', aX, aY)){
                            board_use[aY][aX]=2;
                            board_use[aY][aX-1]=4;

                            int temp;

                            for (int i = 0; i<dirtAgentX.size(); i++){
                                if( (aX == dirtAgentX.get(i)) && (aY == dirtAgentY.get(i))) {
                                    temp = dirtAgentX.get(i);
                                    dirtAgentX.set(i, --temp);
                                }
                            }
                        } else {
                            if (checkMove('d', aX, aY))
                                moveDirtAgent(aX,  0, aY, 1, delay);
                            else if (checkMove('r', aX, aY))
                                moveDirtAgent(aX,  1, aY, 0, delay);
                            else if (checkMove('u', aX, aY))
                                moveDirtAgent(aX,  0, aY, -1, delay);
                        }
                        break;

                    default:
                        break;
                }
                switch (y){
                    case 1:
                        if (checkMove('d', aX, aY)){
                            board_use[aY][aX]=2;
                            board_use[aY+1][aX]=4;

                            int temp;

                            for (int i = 0; i<dirtAgentY.size(); i++){
                                if( (aX == dirtAgentX.get(i)) && (aY == dirtAgentY.get(i))) {
                                    temp = dirtAgentY.get(i);
                                    dirtAgentY.set(i, ++temp);
                                }
                            }

                        } else {
                            if (checkMove('r', aX, aY))
                                moveDirtAgent(aX,  1, aY, 0, delay);
                            else if (checkMove('l', aX, aY))
                                moveDirtAgent(aX,  -1, aY, 0, delay);
                            else if (checkMove('u', aX, aY))
                                moveDirtAgent(aX,  0, aY, -1, delay);
                        }
                        break;
                    case -1:
                        if (checkMove('u', aX, aY)){
                            board_use[aY][aX]=2;
                            board_use[aY-1][aX]=4;

                            int temp;

                            for (int i = 0; i<dirtAgentY.size(); i++){
                                if( (aX == dirtAgentX.get(i)) && (aY == dirtAgentY.get(i))) {
                                    temp = dirtAgentY.get(i);
                                    dirtAgentY.set(i, --temp);
                                }
                            }

                        } else {
                            if (checkMove('r', aX, aY))
                                moveDirtAgent(aX,  1, aY, 0, delay);
                            else if (checkMove('l', aX, aY))
                                moveDirtAgent(aX,  -1, aY, 0, delay);
                            else if (checkMove('d', aX, aY))
                                moveDirtAgent(aX,  0, aY, 1, delay);
                        }
                        break;
                    default:
                        break;
                }

                Platform.runLater(() -> {
                    mainPane.setCenter(roomPane());
                });
            }
        }, delay);
    }
    private void moveCleanAgent(final int aX, int x, final int aY, final int y, int delay){

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                switch (x){
                    case 1:
                        if (checkMove('r', aX, aY)){
                            board_use[aY][aX]=0;
                            board_use[aY][aX+1]=1;

                            int temp;

                            for (int i = 0; i<cleanAgentX.size(); i++){
                                if( (aX == cleanAgentX.get(i)) && (aY == cleanAgentY.get(i))) {
                                    temp = cleanAgentX.get(i);
                                    cleanAgentX.set(i, ++temp);
                                    break;
                                }
                            }

                        } else {
                            if (checkMove('d', aX, aY))
                                moveCleanAgent(aX,  0, aY, 1, delay);
                            else if (checkMove('l', aX, aY))
                                moveCleanAgent(aX,  -1, aY, 0, delay);
                            else if (checkMove('u', aX, aY))
                                moveCleanAgent(aX,  0, aY, -1, delay);
                        }
                        break;

                    case -1:
                        if (checkMove('l', aX, aY)){
                            board_use[aY][aX]=0;
                            board_use[aY][aX-1]=1;

                            int temp;

                            for (int i = 0; i<cleanAgentX.size(); i++){
                                if( (aX == cleanAgentX.get(i)) && (aY == cleanAgentY.get(i))) {
                                    temp = cleanAgentX.get(i);
                                    cleanAgentX.set(i, --temp);
                                    break;
                                }
                            }
                        } else {
                            if (checkMove('d', aX, aY))
                                moveCleanAgent(aX,  0, aY, 1, delay);
                            else if (checkMove('r', aX, aY))
                                moveCleanAgent(aX,  1, aY, 0, delay);
                            else if (checkMove('u', aX, aY))
                                moveCleanAgent(aX,  0, aY, -1, delay);
                        }
                        break;

                    default:
                        break;
                }
                switch (y){
                    case 1:
                        if (checkMove('d', aX, aY)){
                            board_use[aY][aX]=0;
                            board_use[aY+1][aX]=1;

                            int temp;

                            for (int i = 0; i<cleanAgentY.size(); i++){
                                if( (aX == cleanAgentX.get(i)) && (aY == cleanAgentY.get(i))) {
                                    temp = cleanAgentY.get(i);
                                    cleanAgentY.set(i, ++temp);
                                    break;
                                }
                            }

                        } else {
                            if (checkMove('r', aX, aY))
                                moveCleanAgent(aX,  1, aY, 0, delay);
                            else if (checkMove('l', aX, aY))
                                moveCleanAgent(aX,  -1, aY, 0, delay);
                            else if (checkMove('u', aX, aY))
                                moveCleanAgent(aX,  0, aY, -1, delay);
                        }
                        break;
                    case -1:
                        if (checkMove('u', aX, aY)){
                            board_use[aY][aX]=0;
                            board_use[aY-1][aX]=1;

                            int temp;

                            for (int i = 0; i<cleanAgentY.size(); i++){
                                if( (aX == cleanAgentX.get(i)) && (aY == cleanAgentY.get(i))) {
                                    temp = cleanAgentY.get(i);
                                    cleanAgentY.set(i, --temp);
                                    break;
                                }
                            }

                        } else {
                            if (checkMove('r', aX, aY))
                                moveCleanAgent(aX,  1, aY, 0, delay);
                            else if (checkMove('l', aX, aY))
                                moveCleanAgent(aX,  -1, aY, 0, delay);
                            else if (checkMove('d', aX, aY))
                                moveCleanAgent(aX,  0, aY, 1, delay);
                        }
                        break;
                    default:
                        break;
                }

                Platform.runLater(() -> {
                    mainPane.setCenter(roomPane());
                });
            }
        }, delay);
    }


    public static void main(String[] args) {
        launch(args);
    }
}