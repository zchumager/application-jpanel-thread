package net.me.threads;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.border.EtchedBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import java.awt.Color;

public class Main {

	private JFrame frame;
	private PanelLineaRecta panelLineaRecta;
	private PanelPendulo panelPendulo;
	private Thread threadLineaRecta;
	private Thread threadPendulo;
	
	private byte toogleCounter1;
	private byte toogleCounter2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		System.out.println("MAIN OBJECT " + Thread.currentThread());
		/*
		 *Thread.currentThread returns an array that contains
		 *	[name, the priority, group] 
		 */
		
		//code added by windows builder
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
		Thread.currentThread().setName("Main Thread");
		System.out.println("MAIN OBJECT'S CONSTRUCTOR " + Thread.currentThread());//Imprime [nombre, prioridad, tipo] se puede googlear como thread.tostring 
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 739, 411);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//Es necesario declarar constantes para la anchura y la altura de los paneles porque al estar dentro de un layout null tanto el ancho como el alto vale 0
		int panelWidth = 216;
		int panelHeight = 245;
		
		this.panelLineaRecta = new PanelLineaRecta(panelWidth, panelHeight);
		this.panelLineaRecta.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		this.panelLineaRecta.setBounds(50, 32, 216, 245);
		frame.getContentPane().add(panelLineaRecta);
		
		this.panelPendulo = new PanelPendulo(panelWidth, panelHeight);
		this.panelPendulo.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		this.panelPendulo.setBounds(378, 32, 212, 245);
		frame.getContentPane().add(this.panelPendulo);
		
		final JButton playButton1 = new JButton("play");
		final JButton pauseButton1 = new JButton("pause");
		final JButton stopButton1 = new JButton("stop");
		//la palabra reservada final define al objeto como inmutable es decir es un objeto que no se puede cambiar
		
		playButton1.setEnabled(true);
		pauseButton1.setEnabled(false);
		stopButton1.setEnabled(false);
		
		this.toogleCounter1 = 0;
		this.toogleCounter2 = 0;
		
		playButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.this.threadLineaRecta = new Thread(Main.this.panelLineaRecta);
				Main.this.threadLineaRecta.start();
				
				playButton1.setEnabled(false);
				pauseButton1.setEnabled(true);
				stopButton1.setEnabled(true);
				/*
				 * Si se quiere hacer referencia desde una clase anonima a un miembro declarado en un metodo es necesario que el miembro
				 * sea declarado como una constante. Se debe tomar en cuenta que al ser una constante tal miembrio es inmutable, por lo que
				 * si se pretende que el metodo se comporte de manera variable (por eso el nombre de variable) es necesario declarar al
				 * miembro como un atributo de la clase, para poder hacer referencia de la siguiente manera 
				 * 
				 * Clase.this.miembro
				 * 
				 */
			}
		});
		playButton1.setBounds(53, 302, 64, 25);
		frame.getContentPane().add(playButton1);
		
		pauseButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.this.toogleCounter1++;
				
				if(Main.this.toogleCounter1 == 2) {
					Main.this.toogleCounter1 = 0;
				}
				
				if(Main.this.toogleCounter1 == 1) {
					pauseButton1.setText("restart");
					Main.this.threadLineaRecta.suspend();
				}
				
				if (Main.this.toogleCounter1 == 0){
					pauseButton1.setText("pause");
					Main.this.threadLineaRecta.resume();
				}
				
				
				
				playButton1.setEnabled(false);
			}
		});
		pauseButton1.setBounds(109, 302, 78, 25);
		frame.getContentPane().add(pauseButton1);
		
		stopButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.this.threadLineaRecta.stop();
				Main.this.panelLineaRecta.reset();
				
				playButton1.setEnabled(true);
				pauseButton1.setEnabled(false);
				pauseButton1.setText("pause");
				stopButton1.setEnabled(false);
			}
		});
		stopButton1.setBounds(182, 302, 84, 25);
		frame.getContentPane().add(stopButton1);
		
		final JButton playButton2 = new JButton("play");
		final JButton pauseButton2 = new JButton("pause");
		final JButton stopButton2 = new JButton("stop");
		
		playButton2.setEnabled(true);
		pauseButton2.setEnabled(false);
		stopButton2.setEnabled(false);
		
		playButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.this.threadPendulo = new Thread(Main.this.panelPendulo);
				Main.this.threadPendulo.start();
				
				playButton2.setEnabled(false);
				pauseButton2.setEnabled(true);
				stopButton2.setEnabled(true);
			}
		});
		playButton2.setBounds(378, 302, 64, 25);
		frame.getContentPane().add(playButton2);
		
		pauseButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Main.this.toogleCounter2++;
				
				if(Main.this.toogleCounter2 == 2) {
					Main.this.toogleCounter2 = 0;
				}
				
				if(Main.this.toogleCounter2 == 1) {
					Main.this.panelPendulo.suspend();
					pauseButton2.setText("restart");
				} else {
					Main.this.panelPendulo.resume();
					pauseButton2.setText("pause");
				}
			}
		});
		pauseButton2.setBounds(436, 302, 78, 25);
		frame.getContentPane().add(pauseButton2);
		
		stopButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.this.threadPendulo.interrupt();//Esta mal
				Main.this.panelPendulo.reset();
				
				playButton2.setEnabled(true);
				pauseButton2.setEnabled(false);
				pauseButton2.setText("pause");
				stopButton2.setEnabled(false);
			}
		});
		stopButton2.setBounds(512, 302, 78, 25);
		frame.getContentPane().add(stopButton2);
	}
}