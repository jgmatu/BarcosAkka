package com.masterinformatica.bingo.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.masterinformatica.bingo.actors.Manager;

import akka.actor.ActorRef;

@SuppressWarnings("serial")
public class ViewExit extends JFrame {

	private static final int WINDOW_WIDTH = 300;
	private static final int WINDOW_HEIGHT = 300;
	
	private static final int BUTTON_WIDTH = 100;
	private static final int BUTTON_HEIGTH = 100;
	
	private JPanel panel;
	private JLabel label;
	private JButton boton;
	
	
	public ViewExit(String text) {		
		this.boton = new JButton();
		this.boton.setSize(BUTTON_HEIGTH, BUTTON_WIDTH);
		this.boton.setLocation(WINDOW_HEIGHT / 2, WINDOW_WIDTH / 2);
		this.boton.setVisible(true);
		this.boton.setText("Close Game");

		this.label = new JLabel();		
		this.label.setText(text);
		this.label.setLocation(WINDOW_HEIGHT / 4 , WINDOW_WIDTH / 4);
		this.label.setVisible(true);
		this.label.setText(text);
		
		this.panel = new JPanel();
		this.panel.setVisible(true);
		this.panel.setSize(WINDOW_HEIGHT, WINDOW_WIDTH);
		this.panel.add(label);
		this.panel.add(boton);
		
		super.setTitle("Close Game Panel");
		super.setSize(WINDOW_HEIGHT, WINDOW_WIDTH);
		super.setVisible(true);
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);
		super.setResizable(false);
		super.add(panel);
	}
	
	public void setExitButton(Manager manager, ActorRef winner) {
		ActionListener listener = new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				manager.closeSystem(winner);
				dispose();
			}
		};
		boton.addActionListener(listener);		
	}
	
}
