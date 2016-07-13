

import java.util.Vector;

import javax.swing.JOptionPane;

import processing.core.PApplet;
import processing.core.PImage;
//No usas nada de esto 


public class Main extends PApplet {
	PImage fondo,BotonPlay;
	private Vector<Personaje> Personajes;
	private int cont;
	public int velocidad=1;
	private int marcador1=0,marcador2=0;
	private boolean inicio=false;
	private boolean[] keys;
	private boolean saltoatras;
	private int ganador;
	private int fuerzarebote=10;
	private boolean rebote=false;
	
	Pelota pelota=new Pelota(850,500,50,null);
	int s,m;
	public void settings()  

	{
		size(1920, 1080);

	}

	public void setup()
	{

		Personajes= new Vector<Personaje>();

		Personajes.add(new Personaje(300,700,null));
		Personajes.add(new Personaje(1350,700,null));
		s=59;
		m=2;
		fondo=loadImage("fondo.jpg"); 
		BotonPlay=loadImage("BotonPlay.png");
		keys=new boolean[3];
		keys[0]=false;
		keys[1]=false;
		keys[2]=false;
		cont=0;

	}

	public void draw()
	{

		Personaje personaje1=Personajes.elementAt(0);
		Personaje personaje2=Personajes.elementAt(1);
		if(!inicio){
			image(BotonPlay,700,300);
			background(fondo);

		}





		if(inicio){
			//Reloj cuenta regresiva
			if(cont==59){
				s--;
				cont=0;
			}

			if(s==0)
			{m--;
			s=59;
			}

			cont++;
			if(m==-1)
			{
				if(marcador1<marcador2)ganador=2;
				if(marcador2<marcador1)ganador=1;
				if(marcador2==marcador1){	JOptionPane.showMessageDialog(null,"Time is up! Es un empat"+ganador);
				this.exit();
				}
				JOptionPane.showMessageDialog(null,"Time is up! El ganador es el jugador"+ganador);
				this.exit();

			}

			//Muetra reloj
			text(m,1200,300);
			text(':',1250,300);
			text(s,1300,300);

			//Muestra contador
			fill(255);
			textSize(100);
			text(marcador1,800,300);
			text(marcador2,1000,300);		
			text("-",900,300);


			background(fondo);




			//cuando el personaje 2 ve que la pelota esta a 300 pixeles la va a buscar para la izquierda
			
if(personaje2.getPosicionY()>=700){
				if(personaje2.getPosicionX()-400<=pelota.getPosicionX())
				{

					//cuando no se pase del marco
					if(personaje2.getPosicionX()>300)
					{
						//mueve la pelota cuando la tiene al lado y no se choque con el contrario
						if(PelotaEnElPiso()){
							if(TieneLaPelota(2,"izquierda")&&personaje2.getPosicionX()>=personaje1.getPosicionX()+230)
							{
								pelota.cambiodireccion(0);

								patear(false);
								pelota.setPicando(true);
								pelota.cambiodireccion(2);


							}



						}
						//mueve al personaje siempre y cuadno no se choqe con el otro personaje
						if(!TieneLaPelota(2,"izquierda")&&personaje2.getPosicionX()>=personaje1.getPosicionX()+110)
						{

							personaje2.setPosicionX((personaje2.getPosicionX()-10)*velocidad);
						}
					}
				
				}
				}


				//cuando el personaje 2 ve que la pelota esta atras da un salto para atras
				if(pelota.getPosicionY()>=770){	
				if(personaje2.getPosicionX()<=pelota.getPosicionX()+30)
				{

					//cuando no se pase del marco
					if(personaje2.getPosicionX()<1520)
					{


						saltoatras=true;


					}

				}
			
				}

			//PERSONAJE1
			if(!personaje1.isSaltando())
			{


				//mueve el personaje para la derecha
				if(keys[1]==true&&!keys[0])
				{

					//Controla que este dentro de los margenes
					if(personaje1.getPosicionX()<1520){


						//Mueve la pelota siempre y cuando no se choque con el otro personaje


						if(TieneLaPelota(1,"derecha")&&personaje1.getPosicionX()+200<personaje2.getPosicionX())

						{	pelota.cambiodireccion(0);

						pelota.setPosicionX(pelota.getPosicionX()+10*velocidad);
						if(key==' ')
						{
							patear(true);
							pelota.cambiodireccion(1);

						}

						}


					}




					if(!TieneLaPelota(1,"derecha")&&personaje2.getPosicionX()>=personaje1.getPosicionX()+110)
					{
						personaje1.setPosicionX((personaje1.getPosicionX()+10)*velocidad);

					}

				}	


			}



			//Se mueve para la izquierda

			if(keys[2]&&!keys[0])
			{
				//Controla que no se pase de los margenes
				if(personaje1.getPosicionX()>300){

					//controla que no se choque con el otro personaje
					if(!TieneLaPelota(1,"izquierda")&&personaje1.getPosicionX()-100!=personaje2.getPosicionX())
					{
						personaje1.setPosicionX((personaje1.getPosicionX()-10)*velocidad);
					}

					//mueve la pelota


					if(PelotaEnElPiso()){


						if(TieneLaPelota(1,"izquierda"))
						{
							pelota.cambiodireccion(0);
							pelota.setPosicionX(pelota.getPosicionX()-10*velocidad);
						}
					}
				}
			}





			//salta para la derecha	
			if(keys[0]&&keys[1])
			{

				if(personaje1.getPosicionX()<1520){
					personaje1.setPosicionX((personaje1.getPosicionX()+10)*velocidad);
				}
				personaje1.saltar();


			}

			//salta para la izquierda
			if(keys[0]&&keys[2])
			{

				if(personaje1.getPosicionX()>300){
					personaje1.setPosicionX((personaje1.getPosicionX()-10)*velocidad);
				}
				personaje1.saltar();

			}	
			if(keys[0])
			{
				personaje1.saltar();
			}





			//dibuja a los personajes
			for (Personaje personaje: Personajes)
			{
				personaje.dibujar(this);

			}
			//dibuja a la pelota
			pelota.dibujar(this);

			//si se anota un gol aparece una ventana emergente
			if((pelota.getPosicionX()<300&&pelota.getPosicionY()>=600)||(pelota.getPosicionX()>=1600&&pelota.getPosicionY()>=600)){

				if(pelota.getPosicionX()<300)
					marcador2++;

				else
					marcador1++;
				JOptionPane.showMessageDialog(null,"Gol");
				pelota.setPosicionX(850);
				pelota.setPosicionY(500);
				personaje1.setPosicionX(300);
				personaje2.setPosicionX(1350);


				keys[0]=false;
				keys[1]=false;
				keys[2]=false;

				pelota.setPicando(true);
				pelota.setPateada(false);
				pelota.setCabeceada(false);
				pelota.cambiodireccion(0);

			}
			
			if((pelota.getPosicionX()<300&&pelota.getPosicionY()<=600)||(pelota.getPosicionX()>=1600&&pelota.getPosicionY()<=600)){
				
				rebote=true;
				rebotederecha();
				if(rebote){
					
					
					pelota.setPosicionX(pelota.getPosicionX()-fuerzarebote);
					fuerzarebote--;
					if(fuerzarebote==0)
					{
						fuerzarebote=10;
						rebote=false;
					}
			
				}
			}
			
			

			//El peronsaje2 da un salto para la derecha para poder quedar atras de la pelota
			if(saltoatras)
			{

				if(personaje2.getPosicionX()<=1520){
					personaje2.setPosicionX((personaje2.getPosicionX()+10)*velocidad);

					personaje2.saltar();

					if(personaje2.getPosicionY()<=700)
						saltoatras=false;
				}
			}






			//si el personaje esta sobre la pelota que la pelota avance hacia adeltante del personaje
			if(PelotaDebajoP1())		
			{
				personaje1.setSobreLaPelota(true);

				pelota.setPosicionX(pelota.getPosicionX()+3);

			}
			else{
				personaje1.setSobreLaPelota(false);}

			if(PelotaDebajoP2())		
			{
				personaje2.setSobreLaPelota(true);

				pelota.setPosicionX(pelota.getPosicionX()-3);

			}
			else{
				personaje2.setSobreLaPelota(false);}



			//muestra el marcador y el cronometro
			text(m,1200,300);
			text(':',1250,300);
			text(s,1300,300);
			fill(255);
			textSize(100);
			text(marcador1,800,300);
			text(marcador2,1000,300);		
			text("-",900,300);
		}

		if(PelotaEnElPiso())
			pelota.envionPelota();

		if(!inicio)
		{

			background(fondo);
			image(BotonPlay,700,300);

		}
		
		
		//si los dos tienen la pelota y apretas la barra se despeja
		if(TieneLaPelota(2, "izquierda")&&TieneLaPelota(1, "derecha"))
		{
			if(key==' ')
			{
				pelota.setDespeje(true);

			}
		}


		if(personaje1.getPosicionY()<700)
		{
			if(pelota.getPosicionX()>=personaje1.getPosicionX()&&pelota.getPosicionX()<=personaje1.getPosicionX()+100
					&&pelota.getPosicionY()+pelota.getRadio()>=personaje1.getPosicionY()&&pelota.getPosicionY()-pelota.getRadio()<personaje1.getPosicionY())
			{
				cabecear(true);
				pelota.cambiodireccion(1);

			}
		}
		
		
		
		
		
	}

	///////////////////////////////////////////////////////////////////////////////
	///////////////////////////FIN DEL DRAW///////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////

	public void keyPressed()

	{

		if(keyCode==UP){
			keys[0]=true;}

		if(keyCode==RIGHT){
			keys[1]=true;}

		if(keyCode==LEFT){
			keys[2]=true;}


	}
	public void keyReleased()
	{
		if(keyCode==UP){
			keys[0]=false;}
		if(keyCode==RIGHT){
			keys[1]=false;}
		if(keyCode==LEFT){
			keys[2]=false;}


		if(key=='p')
		{
			if(inicio)
				inicio=false;

			else
				inicio=true;
		}
	} 


	private void patear(boolean direccionderecha)
	{
		pelota.setPateada(true);
		pelota.setDireccionderecha(direccionderecha);


	}
	
	private void cabecear(boolean direccionderecha)
	{
		pelota.setCabeceada(true);
		pelota.setDireccionderecha(direccionderecha);
	}

	private boolean PelotaEnElPiso()
	{
		if(pelota.getPosicionY()>=770)
		{
			return true;
		}

		return false;
	}


	//funcion que comprueba si los personajes tienen la pelota	
	private boolean TieneLaPelota(int personaje,String lado)
	{

		Personaje personaje1=Personajes.elementAt(0);
		Personaje personaje2=Personajes.elementAt(1);

		if(personaje==1)
		{
			if(pelota.getPosicionY()>=personaje1.getPosicionY()&&pelota.getPosicionY()<=personaje1.getPosicionY()+100){
				//controla que el personaje1 tenga la pelota a su derecha
				if(lado.equals("derecha"))
				{
					if(personaje1.getPosicionX()+100>=pelota.getPosicionX()-pelota.getRadio()&& personaje1.getPosicionX()+100<=pelota.getPosicionX()+pelota.getRadio()&& personaje1.getPosicionY()+5<=pelota.getPosicionY()+pelota.getRadio())
					{
						return true;
					}



					return false;
				}
				//controla que el personaje1 tenga la pelota a su izquierda			
				if(lado.equals("izquierda"))
				{
					if(personaje1.getPosicionX()<=pelota.getPosicionX()+pelota.getRadio()&& personaje1.getPosicionX()>=pelota.getPosicionX()-pelota.getRadio())
					{
						return true;
					}
					return false;
				}
			}
		}




		if(personaje==2)
		{
			if(pelota.getPosicionY()>=personaje2.getPosicionY()&&pelota.getPosicionY()<=personaje2.getPosicionY()+100){
				//controla que el personaje2 tenga la pelota a su derecha
				if(lado.equals("derecha"))
				{
					if(personaje2.getPosicionX()+100>=pelota.getPosicionX()-pelota.getRadio() && personaje2.getPosicionX()+100<=pelota.getPosicionX()+pelota.getRadio() && personaje2.getPosicionY()+5<=pelota.getPosicionY()+pelota.getRadio())
					{
						return true;
					}
					return false;
				}
				//controla que el personaje2 tenga la pelota a su izquierda			
				if(lado.equals("izquierda"))
				{
					if(personaje2.getPosicionX()<=pelota.getPosicionX()+pelota.getRadio()&&personaje2.getPosicionX()>=pelota.getPosicionX()-pelota.getRadio() )

					{
						return true;
					}
					return false;
				}
			}
		}

		return false;
	}


	public boolean PelotaDebajoP1 ()
	{
		Personaje personaje1=Personajes.elementAt(0);
		if(personaje1.getPosicionY()<700){
			if(pelota.getPosicionY()-pelota.getRadio()<=personaje1.getPosicionY()+100
					&&pelota.getPosicionX()>=personaje1.getPosicionX()&&pelota.getPosicionX()<=personaje1.getPosicionX()+130)
			{

				return true;

			}
		}
		return false;
	}

	public boolean PelotaDebajoP2 ()
	{
		Personaje personaje2=Personajes.elementAt(1);
		if(personaje2.getPosicionY()<700){
			if(pelota.getPosicionY()-pelota.getRadio()<=personaje2.getPosicionY()+100
					&&pelota.getPosicionX()>=personaje2.getPosicionX()&&pelota.getPosicionX()<=personaje2.getPosicionX()+130)
			{

				return true;

			}
		}
		return false;
	}





private void rebotederecha()
{
	pelota.setPicando(true);
	pelota.setBajando(true);
	
	pelota.setPateada(false);
	pelota.setCabeceada(false);
	
	
	pelota.cambiodireccion(0);
	
}



	public static void main(String args[]) {
		PApplet.main(new String[] { "--present", "Main" });
	}
}

