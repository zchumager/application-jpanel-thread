package net.me.threads;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

public class PanelPendulo extends JPanel implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;//atributo autogenerado por Eclipse IDE
	
	private boolean paused;
	
	private int panelWidth;
	private int panelHeight;
	
	private int degrees;
	private int posicionX;
	private int posicionY;
	
	public PanelPendulo(int panelWidth, int panelHeight){
		System.out.println("CURRENT THREAD " + Thread.currentThread());//
		this.setName("PanelPendulo Thread");
		System.out.println("PANELPENDULO OBJECT " + this); 
		
		this.paused = false;
		
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
		
		this.reset();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2D = (Graphics2D) g;
		
		//Sin estas dos lineas la animacion se trabaria
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
	    
		Ellipse2D ellipse2D = new Ellipse2D.Double(this.posicionX, this.posicionY, 10, 10);
		g2D.fill(ellipse2D);
	}

	public void run() {
		boolean aumentar = true;
		
		while(!Thread.currentThread().isInterrupted()){
			try {
				Thread.sleep(10); // desaceleras la velocidad del thread durmiendolo
				
				synchronized(this) {
					if(this.paused) {
						this.wait();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
				return; //El thread se interrumpe en el programa principal y en el catch se regresa la función para terminar el programa
			} catch (IllegalMonitorStateException e){
				e.printStackTrace();
			}
			
			if(this.degrees == 0){
				aumentar = true;
			}
			
			if(this.degrees == 180){
				aumentar = false;
			}
			
			
			if(aumentar){
				this.degrees++;
			}else{
				this.degrees--;
			}
			
			//System.out.println("Grados del threadPendulo : "+this.degrees);
			this.setCoordinates(this.degrees);
			
			this.repaint();
		}
	}
	
	public void suspend() {
		this.paused = true;
	}
	
	public void resume() {
		this.paused = false;
		
		synchronized(this) {
			this.notify();
		}
	}
	
	public void reset() {
		this.degrees = 0;
		this.setCoordinates(this.degrees);
		this.repaint();
	}
	
	public void setCoordinates(int degrees) {
		this.posicionX = (int) (100 * 
				Math.cos(Math.toRadians(degrees)))
				+ (this.panelWidth/2);
		this.posicionY = (int) (100 
				* Math.sin(Math.toRadians(degrees)))
				+ (this.panelHeight/2);
	}
}
