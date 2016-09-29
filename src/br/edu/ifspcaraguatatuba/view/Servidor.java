package br.edu.ifspcaraguatatuba.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.edu.ifspcaraguatatuba.socket_servidor.Socket_Servidor;

public class Servidor extends JFrame {

	private static final long serialVersionUID = 4097681924742090665L;
	private JPanel contentPane;
	
	private JLabel lblStatusServidor;

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
				
				Socket_Servidor servidor = new Socket_Servidor();
				Thread thread = new Thread(servidor);
				
				thread.setPriority(Thread.MAX_PRIORITY);
				thread.start();
				
				String state = thread.getState().toString();
				if (state.equals("RUNNABLE")) {
					lblStatusServidor.setText("Conectado");
					lblStatusServidor.setForeground(Color.GREEN);
					btnConectar.setEnabled(false);
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
	}
}
