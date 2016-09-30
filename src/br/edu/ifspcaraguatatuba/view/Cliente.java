package br.edu.ifspcaraguatatuba.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.edu.ifspcaraguatatuba.socket_cliente.Socket_Cliente;

public class Cliente extends JFrame {

	private static final long serialVersionUID = 6294910249439867800L;
	private JPanel contentPane;
	
	private Socket_Cliente client = new Socket_Cliente("127.0.0.1", 12345);
	
	private JLabel lblStatusServidor;
	private JButton btnDesconectar;
	private JTextField textField;

	public Cliente() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 200);
		setTitle("Cliente");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() { // BOT�O ENVIAR
			public void actionPerformed(ActionEvent e) {
				// TODO implementar fun��o para enviar mensagem ao servidor
				try {
					String message = textField.getText();
					client.sendMessage(message);
					
					textField.setText(null); // limpa a caixa de texto
					textField.setCaretPosition(0);
				}catch (Exception exception) {
					exception.printStackTrace();
					JOptionPane.showMessageDialog(contentPane, exception.getMessage());
				}
			}
		});
		btnEnviar.setBounds(335, 127, 89, 23);
		btnEnviar.setEnabled(false);
		contentPane.add(btnEnviar);
		
		JLabel lblDigiteSuaMensagem = new JLabel("Digite sua mensagem:");
		lblDigiteSuaMensagem.setBounds(10, 71, 414, 14);
		contentPane.add(lblDigiteSuaMensagem);
		
		JButton btnConectar = new JButton("Conectar");
		btnConectar.addActionListener(new ActionListener() { // Cria conexão com o servidor
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					client.connect();
					
					lblStatusServidor.setForeground(Color.green);
					lblStatusServidor.setText("Conectado");
					btnEnviar.setEnabled(true);
					btnConectar.setVisible(false);
					btnDesconectar.setVisible(true);
					textField.setEnabled(true);
					
				} catch (UnknownHostException e) {
					JOptionPane.showMessageDialog(contentPane, e.getMessage());
				} catch (IOException e) {
					JOptionPane.showMessageDialog(contentPane, e.getMessage());
				}
				
			}
		});
		btnConectar.setBounds(205, 127, 120, 23);
		contentPane.add(btnConectar);
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblStatus.setBounds(10, 11, 59, 14);
		contentPane.add(lblStatus);
		
		lblStatusServidor = new JLabel("Desconectado");
		lblStatusServidor.setForeground(Color.RED);
		lblStatusServidor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStatusServidor.setBounds(64, 11, 100, 14);
		contentPane.add(lblStatusServidor);
		
		btnDesconectar = new JButton("Desconectar");
		btnDesconectar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Implementar fun��o para desconectar o cliente....
				
			}
		});
		btnDesconectar.setBounds(205, 127, 120, 23);
		btnDesconectar.setVisible(false);
		contentPane.add(btnDesconectar);
		
		textField = new JTextField();
		textField.setBounds(10, 96, 414, 20);
		textField.setEnabled(false);
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) // Verifica se a tecla ENTER foi pressionada
					btnEnviar.doClick();
			}
		});
		contentPane.add(textField);
		textField.setColumns(10);
	}
}
