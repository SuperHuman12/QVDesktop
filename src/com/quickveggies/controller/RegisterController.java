package com.quickveggies.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.quickveggies.CosmeticStyles;
import com.quickveggies.GeneralMethods;
import com.quickveggies.Main;
import com.quickveggies.dao.DatabaseClient;
import com.quickveggies.entities.User;
import com.quickveggies.misc.CryptDataHandler;

public class RegisterController implements Initializable {

    @FXML
    private Button reg;

    @FXML
    private PasswordField pass;

    @FXML
    private TextField username;

    @FXML
    private PasswordField pass1;

    @FXML
    private Label invalid;

    @FXML
    private TextField email;

    @FXML
    private Button back;

    public void initialize(URL location, ResourceBundle resources) {
        CosmeticStyles.addHoverEffect(reg,back);
        pass.setId("txtinput");
        pass1.setId("txtinput");
        email.setId("txtinput");
        username.setId("txtinput");
        reg.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                if (pass.getText().contains(" ") || pass.getText().contains(";")
                        || pass.getText().contains("'")
                        || pass.getText().contains("\"")) {
                    System.out.println("bad pass");
                    pass.setText("");
                    pass1.setText("");
                    invalid.setVisible(true);
                }
                if (pass.getText().equals(pass1.getText()) && !pass.getText().equals("")) {
                    System.out.println("okay, pass is good");
                    if (email.getText().toUpperCase().matches(GeneralMethods.emailPattern)) {
                        System.out.println("mail matches pattern!");
                        try {
                           
                            String name = username.getText();
                            String password = CryptDataHandler.getInstance().encrypt(pass.getText());
                            String mail = email.getText();
                            User user = new User(name, password, mail, 0);
                            DatabaseClient.getInstance().saveUser(user);
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("User created!");
                            alert.setHeaderText(null);
                            alert.setContentText("User " + name + " successfully created.");
                            alert.showAndWait();
                            new Main().replaceSceneContent("/login.fxml");
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });
        back.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                new Main().replaceSceneContent("/login.fxml");
            }
        });
    }
}
