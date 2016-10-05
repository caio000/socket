package br.edu.ifspcaraguatatuba.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

public class Servidor extends JFrame {

	private static final long serialVersionUID = 4097681924742090665L;
	private JPanel contentPane;
	
	private JLabel lblStatusServidor;
	private JTextPane textPane;

	public Servidor() {
		setTitle("Servidor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(700, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JButton btnConectar = new JButton("Conectar");
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					
					ServerSocket servidor = new ServerSocket(12345);
					
					Runnable r = new Runnable() {
						
						@Override
						public void run() {
							
							try {
								
								Socket cliente = servidor.accept();
								Scanner entrada = new Scanner(cliente.getInputStream());
								System.err.println("executando thread");
								
								String messages = "";
								
								while (entrada.hasNextLine()) {
									messages += "Cliente: " + entrada.nextLine() + "\n";
									textPane.setText(messages);
								}
								
								entrada.close();
								servidor.close();
							}catch (Exception e) {
								e.printStackTrace();
							}
						}
					};
					
					
					Thread thread = new Thread(r);
					thread.setPriority(Thread.MAX_PRIORITY);
					thread.start();
					
					btnConectar.setEnabled(false);
					lblStatusServidor.setText("Conectado");
					lblStatusServidor.setForeground(Color.green);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		btnConectar.setBounds(335, 227, 89, 23);
		contentPane.add(btnConectar);
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblStatus.setBounds(10, 11, 56, 14);
		contentPane.add(lblStatus);
		
		lblStatusServidor = new JLabel("Desconectado");
		lblStatusServidor.setForeground(Color.RED);
		lblStatusServidor.setBounds(64, 13, 111, 14);
		contentPane.add(lblStatusServidor);
		
		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(10, 81, 414, 135);
		contentPane.add(textPane);
	}
}
