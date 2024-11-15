package utn.frc.dis;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import utn.frc.dis.controllers.GestorGRRV;
import utn.frc.dis.entity.*;

import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Main extends JFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
//              new PantallaImportarActualizacionVinos().setVisible(true);
            }
        });
    }

    public Main() {
        habilitarPantalla();
        System.out.println("LINEA 14");
    }

    private void habilitarPantalla(){
        System.out.println("Habilitando Pantalla");
        setTitle("Bonvino - Main");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel principal con el diseño original
        JPanel mainPanel = new JPanel(new BorderLayout());
        Color backgroundColor = new Color(58, 29, 29);
        Color textColor = new Color(255, 255, 255);
        mainPanel.setBackground(backgroundColor);
        add(mainPanel);

        JLabel titleLabel = new JLabel("BONVINO", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 36));
        titleLabel.setForeground(textColor);

        JLabel subtitleLabel = new JLabel("Universidad Tecnologica Nacional FRC", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        subtitleLabel.setForeground(textColor);

        JLabel infoLabel = new JLabel("PROYECTO PRÁCTICO DE APLICACIÓN INTEGRADOR: BONVINO", SwingConstants.CENTER);
        infoLabel.setFont(new Font("Serif", Font.PLAIN, 14));
        infoLabel.setForeground(textColor);

        JPanel textPanel = new JPanel(new GridLayout(3, 1));
        textPanel.setBackground(backgroundColor);
        textPanel.add(titleLabel);
        textPanel.add(subtitleLabel);
        textPanel.add(infoLabel);
        mainPanel.add(textPanel, BorderLayout.NORTH);



        // Botón principal para abrir la ventana de importar actualizaciones de vinos
        JButton importButton = new JButton("Generar reporte de vinos");
        importButton.setForeground(new Color(240, 238, 232));
        importButton.setBackground(new Color(53, 2, 0));
        importButton.setFont(new Font("Serif", Font.PLAIN, 18));
        mainPanel.add(importButton, BorderLayout.CENTER);


        // Acción del botón para abrir la nueva ventana
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestorGRRV gestor = new GestorGRRV();
                gestor.buscarVinos();

                //new PantallaImportarActualizacionVinos().setVisible(true);

                dispose(); // Cierra la ventana principal
            }
        });
    }

}
