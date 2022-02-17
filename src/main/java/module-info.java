module com.example.robotics_sd_controlgui {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.robotics_sd_controlgui to javafx.fxml;
    exports com.example.robotics_sd_controlgui;
}