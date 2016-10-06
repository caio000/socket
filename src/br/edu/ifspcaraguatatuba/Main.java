package br.edu.ifspcaraguatatuba;

import javax.swing.JFrame;

import br.edu.ifspcaraguatatuba.view.Cliente;

public class Main {

	public static void main(String[] args) {
		
		try {
			JFrame client = new Cliente();
			client.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
