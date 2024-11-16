package utn.frc.dis;

import utn.frc.dis.Interface.PantallaGRRV;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Crear una instancia de la pantalla desde el paquete utn.frc.dis.Interface
            PantallaGRRV pantalla = new PantallaGRRV();
            pantalla.setVisible(true); // Mostrar la pantalla
        });
    }
}
