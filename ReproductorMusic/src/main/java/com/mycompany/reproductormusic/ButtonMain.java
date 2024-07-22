/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.reproductormusic;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
/**
 *
 * @author Usuario
 */
public class ButtonMain {
    public void start(Stage primaryStage) {
        // Crear un botón con un texto específico
        Button btn = new Button();
        btn.setText("Haz clic aquí");

        // Configurar el evento de clic del botón
        btn.setOnAction(event -> System.out.println("¡Has hecho clic en el botón!"));

        // Crear un layout apilado (StackPane) para el botón
        StackPane root = new StackPane();
        root.getChildren().add(btn);

        // Crear la escena y configurarla en el escenario (Stage)
        Scene scene = new Scene(root, 300, 250);
        primaryStage.setTitle("Ejemplo de Botón JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
