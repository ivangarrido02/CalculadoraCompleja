package dad;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CalculadoraCom extends Application {

    private ComboBox<String> operationComboBox; // Mover la declaración aquí

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Calculadora de Números Complejos");

        // HBox para los números complejos
        HBox complexNumber1 = createComplexNumberInput();
        HBox complexNumber2 = createComplexNumberInput();
        HBox result = createComplexNumberInput();

        // Separator para separar los números complejos del resultado
        Separator separator = new Separator();

        // ComboBox para elegir la operación
        operationComboBox = new ComboBox<>(); // Inicializar la variable
        operationComboBox.getItems().addAll("+", "-", "*", "/");
        operationComboBox.setValue("+");

        // VBox para colocar todo
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(operationComboBox, complexNumber1, complexNumber2, separator, result);

        // Configurar el binding para mostrar el resultado automáticamente
        DoubleProperty real1 = new SimpleDoubleProperty();
        DoubleProperty imaginary1 = new SimpleDoubleProperty();
        DoubleProperty real2 = new SimpleDoubleProperty();
        DoubleProperty imaginary2 = new SimpleDoubleProperty();

        TextField realTextField1 = (TextField) complexNumber1.getChildren().get(0);
        TextField imaginaryTextField1 = (TextField) complexNumber1.getChildren().get(2);
        TextField realTextField2 = (TextField) complexNumber2.getChildren().get(0);
        TextField imaginaryTextField2 = (TextField) complexNumber2.getChildren().get(2);

        real1.bind(Bindings.createDoubleBinding(() -> {
            try {
                return Double.parseDouble(realTextField1.getText());
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }, realTextField1.textProperty()));

        imaginary1.bind(Bindings.createDoubleBinding(() -> {
            try {
                return Double.parseDouble(imaginaryTextField1.getText());
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }, imaginaryTextField1.textProperty()));

        real2.bind(Bindings.createDoubleBinding(() -> {
            try {
                return Double.parseDouble(realTextField2.getText());
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }, realTextField2.textProperty()));

        imaginary2.bind(Bindings.createDoubleBinding(() -> {
            try {
                return Double.parseDouble(imaginaryTextField2.getText());
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }, imaginaryTextField2.textProperty()));

        // Manejar cambios en la operación seleccionada
        operationComboBox.setOnAction(event -> calculateResult(real1, imaginary1, real2, imaginary2, result));

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Método para crear un HBox que contiene un número complejo
    private HBox createComplexNumberInput() {
        TextField realPart = new TextField("0.0");
        Label plusLabel = new Label("+");
        TextField imaginaryPart = new TextField("0.0");
        Label iLabel = new Label("i");

        HBox complexNumberBox = new HBox(5);
        complexNumberBox.setAlignment(Pos.CENTER);
        complexNumberBox.getChildren().addAll(realPart, plusLabel, imaginaryPart, iLabel);

        return complexNumberBox;
    }

    // Método para calcular el resultado y actualizar el resultado
    private void calculateResult(DoubleProperty real1, DoubleProperty imaginary1, DoubleProperty real2, DoubleProperty imaginary2, HBox resultBox) {
        double realResult = 0;
        double imaginaryResult = 0;

        String operation = operationComboBox.getValue();
        if ("+".equals(operation)) {
            realResult = real1.get() + real2.get();
            imaginaryResult = imaginary1.get() + imaginary2.get();
        } else if ("-".equals(operation)) {
            realResult = real1.get() - real2.get();
            imaginaryResult = imaginary1.get() - imaginary2.get();
        } else if ("*".equals(operation)) {
            realResult = real1.get() * real2.get() - imaginary1.get() * imaginary2.get();
            imaginaryResult = real1.get() * imaginary2.get() + imaginary1.get() * real2.get();
        } else if ("/".equals(operation)) {
            double denominator = real2.get() * real2.get() + imaginary2.get() * imaginary2.get();
            realResult = (real1.get() * real2.get() + imaginary1.get() * imaginary2.get()) / denominator;
            imaginaryResult = (imaginary1.get() * real2.get() - real1.get() * imaginary2.get()) / denominator;
        }

        TextField realResultField = (TextField) resultBox.getChildren().get(0);
        TextField imaginaryResultField = (TextField) resultBox.getChildren().get(2);

        realResultField.setText(String.format("%.2f", realResult));
        imaginaryResultField.setText(String.format("%.2f", imaginaryResult));
    }
}


