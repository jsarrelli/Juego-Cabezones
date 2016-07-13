package Juego;
import processing.core.PApplet;


public class Personaje implements Dibujable {

	private int posicionX, posicionY;
	private int velSalto;
	private String imagen;
	private boolean saltando;
	private boolean SobreLaPelota=false;

	public Personaje(int posicionX, int posicionY, String imagen) {
		super();
		this.posicionX = posicionX;
		this.posicionY = posicionY;
		this.imagen = imagen;
		this.velSalto=5;
		this.saltando=false;




	}






	public int getPosicionX() {
		return posicionX;
	}






	public void setPosicionX(int posicionX) {
		this.posicionX = posicionX;
	}






	public int getPosicionY() {
		return posicionY;
	}






	public void setPosicionY(int posicionY) {
		this.posicionY = posicionY;
	}






	public int getVelSalto() {
		return velSalto;
	}






	public void setVelSalto(int velSalto) {
		this.velSalto = velSalto;
	}






	public String getImagen() {
		return imagen;
	}






	public void setImagen(String imagen) {
		this.imagen = imagen;
	}






	public boolean isSobreLaPelota() {
		return SobreLaPelota;
	}






	public void setSobreLaPelota(boolean sobreLaPelota) {
		SobreLaPelota = sobreLaPelota;
	}






	@Override
	public void dibujar(PApplet pantalla) {
		// TODO Auto-generated method stub

		if(!SobreLaPelota){
			if(this.saltando)
			{
				this.posicionY+=this.velSalto;
			}


			//GRAVEDAD
			if(this.posicionY<=700)
			{

				this.velSalto++;

			}


			if(this.posicionY>=700)
			{
				this.velSalto=0;
				saltando=false;

			}
		}


		pantalla.rect(this.posicionX,posicionY,100,100);


	}

	public void saltar ()
	{
		if(saltando==false)
		{
			this.velSalto=-20;
			this.saltando=true;
		}

	}









	public boolean isSaltando() {
		return saltando;
	}



	public void setSaltando(boolean saltando) {
		this.saltando = saltando;
	}




}
