package com.demo.covid19.controller;

import com.demo.covid19.classes.GoToScene;
import com.demo.covid19.classes.UserHolder;
import com.demo.covid19.connection.ConnectionDatabase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController extends GoToScene {

    @FXML
    TextField textField;

    @FXML
    PasswordField passwordField;

    @FXML
    Button loginButton;


    private Stage stage;
    private Scene scene;
    private Parent root;

    public void login(ActionEvent event) throws Exception {
        System.out.println("Click Login Button ...");

        // Get data from forms
        String username = textField.getText();
        String password = passwordField.getText();

        // Get database connection
        ConnectionDatabase connectionDatabase = new ConnectionDatabase();

        // Query user by username
        String sql = "SELECT * FROM users WHERE username = ?";
        PreparedStatement stmt = connectionDatabase.getConn().prepareStatement(sql);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        // Check is found user by username or not
        if (rs.next()) {
            String hashedPassword = rs.getString("password");

            // Check password is correct
            if (BCrypt.checkpw(password, hashedPassword)) {
                UserHolder.getInstance().setUsername(rs.getString("username"));
                UserHolder.getInstance().setUserId(rs.getInt("id"));
                // Go to next scene

                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("home-view.fxml"));
                root = loader.load();
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setTitle("Covid Diary");
                stage.setScene(scene);
                stage.show();

            } else {
                System.out.println("Password not correct");
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("password-not-correct-view.fxml"));
                    root = loader.load();

                    stage = new Stage();
                    stage.setTitle("Covid Diary");
                    stage.setScene(new Scene(root));
                    stage.show();

                } catch (Exception e) {
                    System.out.println("Can't load new window");
                }
            }
        } else {
            System.out.println("User " + username + " not found");
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("username-not-found-view.fxml"));
                root = loader.load();

                stage = new Stage();
                stage.setTitle("Covid Diary");
                stage.setScene(new Scene(root));
                stage.show();

            } catch (Exception e) {
                System.out.println("Can't load new window");
            }
        }

    }

    @FXML
    private void sendData (ActionEvent event) {
        int userId = 0;
        Node node = (Node) event.getSource();
        stage = (Stage) node.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("covid-alert-view.fxml"));
            root = loader.load();
            stage.setUserData(userId);
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(String.format("Error: %s", e.getMessage()));
        }
    }

}
