package br.edu.ifspcaraguatatuba;

import javax.swing.JFrame;

import br.edu.ifspcaraguatatuba.view.Cliente;
import br.edu.ifspcaraguatatuba.view.Servidor;

public class Main {

	public static void main(String[] args) {
		
		JFrame client = new Cliente();
		JFrame server = new Servidor();
		
		client.setVisible(true);
		server.setVisible(true);

	}

}
