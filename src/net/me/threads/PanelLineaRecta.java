package net.me.threads;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

public class PanelLineaRecta extends JPanel implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;//atributo autogenerado por Eclipse IDE
	
	private int panelWidth;	
	private int posicionX;
	private int posicionY;
	
	public PanelLineaRecta(int panelWidth, int panelHeight){
		this.panelWidth = panelWidth;
		
		this.posicionX = (panelWidth/2);
		this.posicionY = panelHeight/2;
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
		
		while(true){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(this.posicionX == 0){
				aumentar = true;
			}
			
			if(this.posicionX == this.panelWidth){
				aumentar = false;
			}
			
			if(aumentar){
				this.posicionX++;
			}else{
				this.posicionX--;
			}
			
			System.out.println("Posicion de X del threadLineaRecta: " + this.posicionX);
			this.repaint();
		}
	}
	
	public void reset(){
		this.posicionX = (this.panelWidth/2);
		this.repaint();
	}
}
