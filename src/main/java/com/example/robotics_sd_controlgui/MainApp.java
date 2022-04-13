package com.example.robotics_sd_controlgui;


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.function.BiConsumer;


public class MainApp extends Application {
    private static Stage stage;
    private static String robotChosen;
    private static String userChosen;
    public static ArrayList<String> JPLControls;
    public static ArrayList<String> AugerControls;
    public static boolean controlsOpen = false;
    public static boolean notSelected = true;
    public static String selectedRobotButtonStyle = "-fx-text-fill: black;" + "-fx-background-color: red;" + "-fx-border-color:white;" + "-fx-border-width: 1 1 1 1;";
    @Override
    public void start(Stage primaryStage) {

        stage = primaryStage;
        Scene scene = openScreen();

//        Scene scene = testingScene();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Temple Robotics Robot Control GUI!");
        //stage.setScene(nextScene);
        primaryStage.show();
    }
    public static Scene openScreen() {
        ImageView landingImgView = new ImageView();
        String currentDir = System.getProperty("user.dir");
        System.out.println(currentDir);
        //Image image = new Image(App2.display,this.getClass().getResourceAsStream("search.jpg"));
        //File landingIMG = new File("/Temple_Robotics_Landing.png");
        //Image landingImg = new Image(currentDir + "/src/main/java/com.example.robotics_sd_controlgui/Temple_Robotics_Landing.PNG");

        Image landingImg = new Image("file:Temple_Robotics_Landing.PNG");
        landingImgView.setImage(landingImg);
        BorderPane bp = new BorderPane();
        bp.setCenter(landingImgView);
        Button startBtn = new Button("START");
        hoverEnlarge(startBtn);
        startBtn.setOnAction(new EventHandler<ActionEvent>() {          //open new scene when clicked
            public void handle(ActionEvent t) {
                stage.setScene(RobotScene());
            }
        });

        startBtn.setFont(Font.font("boulder", FontWeight.BOLD, 30));
        startBtn.setStyle("-fx-text-fill: white;" + "-fx-background-color: black;" + "-fx-border-color:white;" + "-fx-border-width: 3 3 3 3;");
        VBox vb = new VBox();
        vb.getChildren().add(startBtn);
        vb.setAlignment(Pos.CENTER);
        bp.setBottom(vb);
        bp.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        return new Scene(bp);
    }
    public static Scene RobotScene(){

        BorderPane bp = new BorderPane();
        bp.setStyle("-fx-background-color: black;");
        HBox hb = new HBox();
        Text selectBot = new Text("Select Robot: ");
        selectBot.setFont(Font.font("boulder", FontWeight.BOLD, 50));
        selectBot.setFill(Color.WHITE);
        hb.getChildren().add(selectBot);
        hb.setAlignment(Pos.CENTER);
        bp.setTop(hb);
        VBox userButtons = createUserButtons();
        userButtons.setVisible(false);
        bp.setRight(userButtons);
        bp.setLeft(createRobotButtons(userButtons));
        Button selectNewBot = new Button("Select new bot");
        Button createNewButton = new Button("Create new button");
        HBox hbox = new HBox();
        hbox.getChildren().addAll(createNewButton, selectNewBot);
        bp.setBottom(hbox);
        selectNewBot.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent){
                bp.setLeft(createRobotButtons(userButtons));
            }
        });
        return new Scene(bp, bp.getMaxWidth()+400,bp.getMaxHeight() );
    }
    public static Scene TabScene() {

        TabPane tabPane = new TabPane();
        BorderPane mainPane = new BorderPane();
        Group root = new Group();
        Tab robotSelectionTab = new Tab();
        robotSelectionTab.setText("Robots");
        Button JPLButton = new Button("JPL");
        JPLButton.setPrefWidth(150);
        JPLButton.setStyle("-fx-text-fill: white;" + "-fx-background-color: black;" + "-fx-border-color:white;" + "-fx-border-width: 3 3 3 3;");
        JPLButton.setFont(Font.font("boulder", FontWeight.BOLD, 20));
        hoverEnlarge(JPLButton);
        Button Lunabotics2022Button = new Button("Lunabotics 2022");
        Lunabotics2022Button.setPrefWidth(150);
        Lunabotics2022Button.setStyle("-fx-text-fill: white;" + "-fx-background-color: black;" + "-fx-border-color:white;" + "-fx-border-width: 3 3 3 3;");
        Lunabotics2022Button.setFont(Font.font("boulder", FontWeight.BOLD, 20));
        hoverEnlarge(Lunabotics2022Button);
        Button AugerButton = new Button("Augerbot");
        AugerButton.setPrefWidth(150);
        AugerButton.setStyle("-fx-text-fill: white;" + "-fx-background-color: black;" + "-fx-border-color:white;" + "-fx-border-width: 3 3 3 3;");
        AugerButton.setFont(Font.font("boulder", FontWeight.BOLD, 20));
        hoverEnlarge(AugerButton);
        Text prompRobotSelect = new Text("Choose robot: ");
        prompRobotSelect.setFont(Font.font("boulder", FontWeight.BOLD, 30));
        prompRobotSelect.setFill(Color.GHOSTWHITE);

        JPLButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                System.out.println("JPL Button is pressed");
                robotChosen = chosenBot(JPLButton);
                choseUser();
            }
        });
        Lunabotics2022Button.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                System.out.println("Lunabotics2022 Button is pressed");
                robotChosen = chosenBot(Lunabotics2022Button);
                choseUser();
            }
        });
        AugerButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                System.out.println("Augerbot Button is pressed");
                robotChosen = chosenBot(AugerButton);
                choseUser();
            }
        });
        VBox robotSelectionVBox = new VBox();
        robotSelectionVBox.getChildren().addAll(prompRobotSelect, JPLButton, Lunabotics2022Button, AugerButton);
        robotSelectionTab.setContent(robotSelectionVBox);
        tabPane.getTabs().add(robotSelectionTab);

        Tab tabB = new Tab();
        tabB.setText("Tab B");
        StackPane tabB_stack = new StackPane();
        tabB_stack.setAlignment(Pos.CENTER);
        tabB_stack.getChildren().add(new Label("Label@Tab B"));
        tabB.setContent(tabB_stack);
        tabPane.getTabs().add(tabB);

        Tab tabC = new Tab();
        tabC.setText("Tab C");
        VBox tabC_vBox = new VBox();
        tabC_vBox.getChildren().addAll(
                    new Button("Button 1@Tab C"),
                    new Button("Button 2@Tab C"),
                    new Button("Button 3@Tab C"));
        tabC.setContent(tabC_vBox);
        tabPane.getTabs().add(tabC);
        tabPane.setPrefWidth(stage.getWidth());
        mainPane.setPrefHeight(stage.getHeight());
        mainPane.setPrefWidth(stage.getWidth());
        mainPane.setCenter(tabPane);
        mainPane.setStyle("-fx-background-color: black");
        root.getChildren().add(mainPane);

        return new Scene(root, 500, 400);
    }
    public static Scene choseUser() {   //static, should be updated to dynamic
        //add ability to add new users + resulting actions
        Stage openWindow = new Stage();
        BorderPane userSelect = new BorderPane();
        Text promptUserSelect = new Text("Chose profile: ");
        promptUserSelect.setFont(Font.font("boulder", FontWeight.BOLD, 30));
        promptUserSelect.setFill(Color.WHITE);
        promptUserSelect.setUnderline(true);

        userSelect.setTop(promptUserSelect);
        userSelect.setAlignment(promptUserSelect, Pos.CENTER);
        userSelect.setCenter(createUserButtons());

        Button backButton = new Button("Back");
        backButton.setFont(Font.font("boulder", FontWeight.BOLD, 20));
        backButton.setStyle("-fx-text-fill: white;" + "-fx-background-color: black;" + "-fx-border-color:white;" + "-fx-border-width: 1 1 1 1;");
        hoverChangeColorToRed(backButton);
        backButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                System.out.println("back button is pressed");
                stage.setScene(RobotScene());
                openWindow.hide();
            }
        });
        HBox backHbox = new HBox();
        backHbox.getChildren().addAll(backButton);
        backHbox.setAlignment(Pos.BOTTOM_RIGHT);
        backHbox.setStyle("-fx-background-color: black;");
        userSelect.setBottom(backHbox);
        userSelect.setStyle("-fx-background-color: black;");
        Scene newScene = new Scene(userSelect, 800, 200);
        openWindow.setScene(newScene);
        openWindow.show();
        openWindow.setTitle("Augerbot User Selection");
        return newScene;
    }
    public static VBox createRobotButtons(VBox userButtons){
        VBox vb = new VBox();
        Button JPLButton = new Button("JPL");
        JPLButton.setPrefWidth(200);
        JPLButton.setStyle("-fx-text-fill: white;" + "-fx-background-color: black;" + "-fx-border-color:white;" + "-fx-border-width: 1 1 1 1;");
        JPLButton.setFont(Font.font("boulder", FontWeight.BOLD, 20));
        hoverEnlargeRobotButtons(JPLButton, userButtons);

        Button Lunabotics2022Button = new Button("Lunabotics 2022");
        Lunabotics2022Button.setPrefWidth(200);
        Lunabotics2022Button.setStyle("-fx-text-fill: white;" + "-fx-background-color: black;" + "-fx-border-color:white;" + "-fx-border-width: 1 1 1 1;");
        Lunabotics2022Button.setFont(Font.font("boulder", FontWeight.BOLD, 20));
        hoverEnlargeRobotButtons(Lunabotics2022Button, userButtons);

        Button AugerButton = new Button("Augerbot");
        AugerButton.setPrefWidth(200);
        AugerButton.setStyle("-fx-text-fill: white;" + "-fx-background-color: black;" + "-fx-border-color:white;" + "-fx-border-width: 1 1 1 1;");
        AugerButton.setFont(Font.font("boulder", FontWeight.BOLD, 20));
        hoverEnlargeRobotButtons(AugerButton, userButtons);

        vb.getChildren().addAll(JPLButton, Lunabotics2022Button, AugerButton);
        vb.setAlignment(Pos.CENTER);
        vb.setSpacing(30);
        return vb;
    }
    //public static
    public static VBox createUserButtons(){
        Stage openWindow = new Stage();
        VBox vbox = new VBox();
        Button DefaultButton = new Button("Default");
        DefaultButton.setFont(Font.font("boulder", FontWeight.BOLD, 20));
        DefaultButton.setPrefWidth(100);
        DefaultButton.setStyle("-fx-text-fill: white;" + "-fx-background-color: black;" + "-fx-border-color:white;" + "-fx-border-width: 1 1 1 1;");
        DefaultButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                System.out.println("Default User is Chosen");
                userChosen = chosenUser(DefaultButton);
                if (robotChosen == "Lunabotics 2022") {
                    launchFRC();
                }
                else {
                    stage.setScene(controlScene());
                    openWindow.hide();
                }
                openWindow.hide();
            }
        });
        hoverEnlarge(DefaultButton);
        Button GemmaButton = new Button("Gemma");
        GemmaButton.setFont(Font.font("boulder", FontWeight.BOLD, 20));
        GemmaButton.setPrefWidth(100);
        GemmaButton.setStyle("-fx-text-fill: white;" + "-fx-background-color: black;" + "-fx-border-color:white;" + "-fx-border-width: 1 1 1 1;");
        GemmaButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                System.out.println("Gemma is Chosen");
                userChosen = chosenUser(GemmaButton);
                if (robotChosen == "Lunabotics 2022") {
                    launchFRC();
                }
                else {
                    stage.setScene(controlScene());
                    openWindow.hide();
                }
                openWindow.hide();
            }
        });
        hoverEnlarge(GemmaButton);
        Button MarkButton = new Button("Mark");
        MarkButton.setFont(Font.font("boulder", FontWeight.BOLD, 20));
        MarkButton.setPrefWidth(100);
        MarkButton.setStyle("-fx-text-fill: white;" + "-fx-background-color: black;" + "-fx-border-color:white;" + "-fx-border-width: 1 1 1 1;");
        MarkButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                System.out.println("Mark is Chosen");
                userChosen = chosenUser(MarkButton);
                if (robotChosen == "Lunabotics 2022") {
                    launchFRC();
                }
                else {
                    stage.setScene(controlScene());
                    openWindow.hide();
                }
                openWindow.hide();
            }
        });
        hoverEnlarge(MarkButton);
        Button JoeButton = new Button("Joe");
        JoeButton.setFont(Font.font("boulder", FontWeight.BOLD, 20));
        JoeButton.setPrefWidth(100);
        JoeButton.setStyle("-fx-text-fill: white;" + "-fx-background-color: black;" + "-fx-border-color:white;" + "-fx-border-width: 1 1 1 1;");
        JoeButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                System.out.println("Joe is Chosen");
                userChosen = chosenUser(JoeButton);
                if (robotChosen == "Lunabotics 2022") {
                    launchFRC();
                }
                else {
                    stage.setScene(controlScene());
                    openWindow.hide();
                }
                openWindow.hide();
            }
        });
        hoverEnlarge(JoeButton);
        Button BrianButton = new Button("Brian");
        BrianButton.setFont(Font.font("boulder", FontWeight.BOLD, 20));
        BrianButton.setPrefWidth(100);
        BrianButton.setStyle("-fx-text-fill: white;" + "-fx-background-color: black;" + "-fx-border-color:white;" + "-fx-border-width: 1 1 1 1;");
        BrianButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                System.out.println("Brian is Chosen");
                userChosen = chosenUser(BrianButton);
                if (robotChosen == "Lunabotics 2022") {
                    launchFRC();
                }
                else {
                    stage.setScene(controlScene());
                    openWindow.hide();
                }
                openWindow.hide();
            }
        });
        hoverEnlarge(BrianButton);
        Button IsabelButton = new Button("Isabel");
        IsabelButton.setFont(Font.font("boulder", FontWeight.BOLD, 20));
        IsabelButton.setPrefWidth(100);
        IsabelButton.setStyle("-fx-text-fill: white;" + "-fx-background-color: black;" + "-fx-border-color:white;" + "-fx-border-width: 1 1 1 1;");
        IsabelButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                System.out.println("Isabel is Chosen");
                userChosen = chosenUser(IsabelButton);
                if (robotChosen == "Lunabotics 2022") {
                    launchFRC();
                }
                else {
                    stage.setScene(controlScene());
                    openWindow.hide();
                }
                openWindow.hide();
            }
        });
        hoverEnlarge(IsabelButton);
        Button KevinButton = new Button("Kevin");
        KevinButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                System.out.println("Kevin is Chosen");
                userChosen = chosenUser(KevinButton);
                if (robotChosen == "Lunabotics 2022") {
                    launchFRC();
                }
                else {
                    stage.setScene(controlScene());
                    openWindow.hide();
                }
                openWindow.hide();
            }
        });
        hoverEnlarge(KevinButton);
        KevinButton.setFont(Font.font("boulder", FontWeight.BOLD, 20));
        KevinButton.setStyle("-fx-text-fill: white;" + "-fx-background-color: black;" + "-fx-border-color:white;" + "-fx-border-width: 1 1 1 1;");
        KevinButton.setPrefWidth(100);
        vbox.getChildren().addAll(DefaultButton, GemmaButton, MarkButton, JoeButton, BrianButton, IsabelButton, KevinButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(30);
        vbox.setStyle("-fx-background-color: black");
        return vbox;
    }
    public static Scene controlScene(){
        controlsOpen = true;
        BorderPane bp = new BorderPane();
        HBox hb = new HBox(500);
        Text sayThis = new Text(robotChosen + " for " + userChosen + ":");
        sayThis.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
        Button backButton = new Button("Back");
        backButton.setFont(Font.font("Boulder", FontWeight.NORMAL, 15));
        backButton.setStyle("-fx-text-fill: white;" + "-fx-background-color: black;" + "-fx-border-color:white;" + "-fx-border-width: 1 1 1 1;");
        backButton.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent t){
                controlsOpen = false;
                System.out.println("Going Back from Control Scene for " + robotChosen + " by " + userChosen);
                stage.setScene(RobotScene());
            }
        });

        sayThis.setFont(Font.font("boulder", FontWeight.BOLD, 30));
        sayThis.setFill(Color.WHITE);
        HBox hb1 = new HBox();
        hb.getChildren().add(sayThis);
        hb.setAlignment(Pos.CENTER);
        bp.setTop(hb);

        ImageView img1 = new ImageView();
        Image controlImg = new Image("file:xbox_controller_config.jpg");
        img1.setImage(controlImg);
        bp.setCenter(img1);
        bp.setRight(createControlBind());

        Button saveBtn = new Button("Save");
        saveBtn.setFont(Font.font("Boulder", FontWeight.NORMAL, 15));
        saveBtn.setStyle("-fx-text-fill: white;" + "-fx-background-color: black;" + "-fx-border-color:white;" + "-fx-border-width: 1 1 1 1;");
        hb1.getChildren().addAll(backButton, saveBtn);
        bp.setBottom(hb1);
        bp.setStyle("-fx-background-color: black");
        return new Scene(bp, 1100, 600);
    }
    public static ComboBox createComboBox(int index, ArrayList<String> controls){
        ComboBox comboBox1 = new ComboBox();
        comboBox1.getItems().addAll("Dig", "Move", "Stop", "Add function 1", "Add fuction 2");
        comboBox1.valueProperty().addListener(new ChangeListener<String>(){
            @Override public void changed(ObservableValue ov, String t, String t1){
                System.out.println(ov);
                System.out.println("Old for control " + index + " : " + t);
                System.out.println("New for control" + index + " : " + t1);
                controls.add(index, t1);
                if(robotChosen == "JPL") {
                    JPLControls = controls;
                    System.out.println("Controls for " + robotChosen + " for " + userChosen + " : " + JPLControls);
                }
                else if(robotChosen == "Augerbot") {
                    AugerControls = controls;
                    System.out.println("Controls for " + robotChosen + " for " + userChosen + " : " + AugerControls);
                }
            }
        });
        return comboBox1;
    }
    public static void hoverEnlarge(Button button){
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {     //enlarge when mouse hovered
            @Override
            public void handle(MouseEvent mouseEvent) {
                button.setScaleX(1.15);
                button.setScaleY(1.15);
                button.setStyle("-fx-text-fill: black;" + "-fx-background-color: gray;" + "-fx-border-color:white;" + "-fx-border-width: 2 2 2 2;");
            }
        });
        button.setOnMouseExited(new EventHandler<MouseEvent>() {      //default when mouse not hovered
            @Override public void handle(MouseEvent e) {
                button.setScaleX(1);
                button.setScaleY(1);
                button.setStyle("-fx-text-fill: white;" + "-fx-background-color: black;" + "-fx-border-color:white;" + "-fx-border-width: 1 1 1 1;");
            }
        });
    }
    public static void hoverEnlargeRobotButtons(Button button, VBox userButtons){

        button.setOnMouseEntered(new EventHandler<MouseEvent> (){       //if hovering a button
           public void handle(MouseEvent mouseEvent){
               if(button.getStyle() != selectedRobotButtonStyle) {

                   System.out.println("mouse entered for " + button);
                   button.setScaleX(1.15);
                   button.setScaleY(1.15);
                   button.setStyle("-fx-text-fill: black;" + "-fx-background-color: gray;" + "-fx-border-color:white;" + "-fx-border-width: 2 2 2 2;");

               }
               button.setOnAction(new EventHandler<ActionEvent>() {
                   @Override
                   public void handle(ActionEvent actionEvent) {    //if hovering and clicked

                           //resetRobotButtons(createRobotButtons(userButtons), userButtons);
                            createRobotButtons(userButtons);
                            System.out.println("resetting button styles");
                           //reset other buttons
                           button.setStyle("-fx-text-fill: black;" + "-fx-background-color: red;" + "-fx-border-color:white;" + "-fx-border-width: 1 1 1 1;");
                           System.out.println("setting clicked button flag as on");
                           System.out.println(button.getText() + " button is pressed");
                           robotChosen = chosenBot(button);
                           userButtons.setVisible(true);
                   }
               });
               }
        });
            button.setOnMouseExited(new EventHandler<MouseEvent>() {      //default when mouse not hovered
                public void handle(MouseEvent e) {
                    if (button.getText() == robotChosen) {

                    }
                    else {

                        button.setStyle("-fx-text-fill: white;" + "-fx-background-color: black;" + "-fx-border-color:white;" + "-fx-border-width: 1 1 1 1;");
                        button.setScaleX(1);
                        button.setScaleY(1);
                    }
                }
            });
    }
    public static void hoverChangeColorToRed(Button button){
        button.setOnMouseEntered(new EventHandler<MouseEvent>() {     //enlarge when mouse hovered
            @Override
            public void handle(MouseEvent mouseEvent) {
                button.setStyle("-fx-text-fill: red;");
            }
        });
        button.setOnMouseExited(new EventHandler<MouseEvent>() {      //default when mouse not hovered
            @Override public void handle(MouseEvent e) {
                button.setStyle("-fx-text-fill: white;" + "-fx-background-color: black;" + "-fx-border-color:white;" + "-fx-border-width: 1 1 1 1;");
            }
        });
    }
    public static GridPane createControlBind(){
        GridPane gridPane = new GridPane();
        Label pos1_label = new Label("1: ");
        formatLabels(pos1_label);
        Label pos2_label = new Label("2: ");
        formatLabels(pos2_label);
        Label pos3_label = new Label("3: ");
        formatLabels(pos3_label);
        Label pos4_label = new Label("4: ");
        formatLabels(pos4_label);
        Label posA_label = new Label("A: ");
        formatLabels(posA_label);
        Label posB_label = new Label("B: ");
        formatLabels(posB_label);

        //top, right, bottom, left
        //column, row
        ArrayList<String> controls = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            controls.add("");
        }
        gridPane.add(pos1_label, 0, 0);
        gridPane.add(createComboBox(0, controls), 1, 0);
        gridPane.add(pos2_label, 0, 1);
        gridPane.add(createComboBox(1, controls), 1, 1);
        gridPane.add(pos3_label, 0, 2);
        gridPane.add(createComboBox(2, controls), 1, 2);
        gridPane.add(pos4_label, 0, 3);
        gridPane.add(createComboBox(3, controls), 1, 3);
        gridPane.add(posA_label, 0, 4);
        gridPane.add(createComboBox(4, controls), 1, 4);
        gridPane.add(posB_label, 0, 5);
        gridPane.add(createComboBox(5, controls), 1, 5);
        return gridPane;
    }
    public static void formatLabels(Label label){
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font("boulder", FontWeight.NORMAL, 15));
    }
    public static String chosenUser(Button profileButton) {
        String ChosenUser = profileButton.getText();
        return ChosenUser;
    }
    public static String chosenBot(Button robotButton) {
        String ChosenBot = robotButton.getText();
        return ChosenBot;
    }
    public static void launchFRC(){
 //           try {
                //String command = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe";
                //String command = "C:\\Program Files (x86)\\FRC Driver Station\\DriverStation.exe";
            //    String command = "/System/Applications/Mail.app";
              //  Runtime run = Runtime.getRuntime();
              //  Process proc = run.exec(command);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
     }
public static void main(String[] args) {
    launch();
    //File controlsFile = new File("C:\\Users\\Gemma\\IdeasProjects\\Robotics_SD_ControlGUI\\controlsFile.txt");
//    File file = new File("controlsFile.txt");
//    Writer writer = null;
//    if (controlsOpen == true) {
//        for (int i = 0; i < 21; i++) {
//            String text = JPLControls.get(i);
//            this.writer.write(text);
//        }
//    }
}
}
