import processing.core.PApplet;


public class Pelota implements Dibujable {

	private int posicionX, posicionY,radio;
	private String imagen;
	private int velocidadRebote,fuerza=20,fuerzaDespeje=30;
	private boolean bajando=true;
	private boolean picando=true;
	private boolean pateada=false;
	private boolean cabeceada=false;
	private int movimiento=20;
	private boolean direccionderecha,subiendo=true;
	private int envion=0,energia;
	private boolean Despeje=false;
	private boolean envionActivado=false;
	
	public Pelota(int posicionX, int posicionY,int radio, String imagen) {
		super();
		this.posicionX = posicionX;
		this.posicionY = posicionY;
		this.radio=radio;
		this.imagen = imagen;
		this.velocidadRebote=0;

	}



	public int getRadio() {
		return radio;
	}



	public void setRadio(int radio) {
		this.radio = radio;
	}



	public int getPosicionX() {
		return posicionX;
	}



	public boolean isCabeceada() {
		return cabeceada;
	}



	public void setCabeceada(boolean cabeceada) {
		this.cabeceada = cabeceada;
	}



	public boolean isDespeje() {
		return Despeje;
	}



	public boolean isBajando() {
		return bajando;
	}



	public void setBajando(boolean bajando) {
		this.bajando = bajando;
	}



	public void setDespeje(boolean despeje) {
		Despeje = despeje;
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



	public boolean isPateada() {
		return pateada;
	}



	public void setPateada(boolean pateada) {
		this.pateada = pateada;
	}




	public boolean isDireccionderecha() {
		return direccionderecha;
	}



	public void setDireccionderecha(boolean direccionderecha) {
		this.direccionderecha = direccionderecha;
	}





	public boolean isPicando() {
		return picando;
	}



	public void setPicando(boolean picando) {
		this.picando = picando;
	}
	




	public int getEnergia() {
		return energia;
	}



	public void setEnergia(int energia) {
		this.energia = energia;
	}



	public int getEnvion() {
		return envion;
	}



	public void setEnvion(int envion) {
		this.envion = envion;
	}



	@Override
	public void dibujar(PApplet pantalla) {
		// TODO Auto-generated method stub

		pantalla.ellipse(this.posicionX,this.posicionY,this.radio,this.radio);


		





		if(picando){

			if(bajando)
			{
				velocidadRebote++;

				posicionY=posicionY+velocidadRebote;



				if(posicionY>=770){

					posicionY=770;
					bajando= false;
				}




			}
			else
			{

				velocidadRebote-=2;
				posicionY=posicionY-velocidadRebote;

				if(velocidadRebote<=0)
				{
					bajando=true;


				}

				if(posicionY>=770)
				{
					posicionY=770;

					picando=false;
					velocidadRebote=0;
				}





			}
		}

		if(posicionX>300&&posicionX<1600){
			if(pateada)
			{ patear();
				if(direccionderecha)
				{
					
					envionDerecha();
					envionActivado=true;

				}

				if(!direccionderecha)
				{
				
					envionIzquierda();
					envionActivado=true;

				}
			}
			
			
			if(Despeje)
			{
				Despeje();
			}

		}
		
		
		
		if(posicionX>300&&posicionX<1600){
			
			if(cabeceada)
			{	patear();
				if(direccionderecha)
				{
					cambiodireccion(1);
					envionDerecha();
					
				}
				
				if(!direccionderecha)
				{
					
					envionIzquierda();

				}
			}
			
		}

	}




	public int getVelocidadRebote() {
		return velocidadRebote;
	}



	public void setVelocidadRebote(int velocidadPelota) {
		this.velocidadRebote = velocidadPelota;
	}

	private void patear()
	{

		if(subiendo){

			
			posicionY=posicionY-fuerza;

			fuerza--;
		}


		if(!subiendo)
		{

			posicionY=posicionY-fuerza;
			fuerza--;

			if(posicionY>=770){
				posicionY=770;

				pateada=false;
				cabeceada=false;
				fuerza=20;
				subiendo=true;
				
			}

		}
		if(fuerza<=0)
		{
			subiendo=false;

		}

	}


	private void envionDerecha()
	{
		if(posicionY<770)
			posicionX=posicionX+movimiento;


	}
	private void envionIzquierda()
	{
		if(posicionY<770)
			posicionX=posicionX-movimiento;
	}

	

	private void Despeje()
	{

		if(subiendo){

			
			posicionY=posicionY-fuerzaDespeje;

			fuerzaDespeje--;
		}


		if(!subiendo)
		{

			posicionY=posicionY-fuerzaDespeje;
			fuerzaDespeje--;

			if(posicionY>=770){
				posicionY=770;

				Despeje=false;
				fuerzaDespeje=30;
				subiendo=true;
			}

		}
		if(fuerzaDespeje<=0)
		{
			subiendo=false;

		}

	}
//cambia la direccion del envion
	public void cambiodireccion(int direccion)
	{
		
		if(direccion==1)envion=1;
		if(direccion==2)envion=2;
		if(direccion==0)envion=0;
		
		if(envion==0)
		{
			energia=0;
		}if(envion==1)
		{
			energia=15;
		}
		if(envion==2)
		{
			energia=-15;
		}
	}
	
	
//disminuye el envion hasta llegar a 0	
	private void envionPelota()
	{

		//mueve la pelota de acuerdo al envion, si el envion es 0 teoricamente no se deberia mover
		posicionY=posicionY+energia;



		//va disminuyendo el envion hasta que sea cero
		if(envion==2)
		{
			energia=energia+1;
		}

		if(envion==1)
		{
			energia=energia-1;
		}

		if(energia==0)
		{
			envion=0;
			envionActivado=false;
		}


	}

}
