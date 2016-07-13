package Usuario;

import java.sql.SQLException;
import java.util.Vector;


import javax.swing.JOptionPane;

import Juego.Pelota;
import Juego.Personaje;

public class Logica {

	Personaje personaje1;
	Personaje personaje2;
	Pelota pelota;








	protected Logica(Personaje personaje1, Personaje personaje2, Pelota pelota) {
		super();
		this.personaje1 = personaje1;
		this.personaje2 = personaje2;
		this.pelota = pelota;
	}

	protected void patear(boolean direccionderecha)
	{
		pelota.setPateada(true);
		pelota.setDireccionderecha(direccionderecha);


	}

	protected void cabecear(boolean direccionderecha)
	{
		pelota.setCabeceada(true);
		pelota.setDireccionderecha(direccionderecha);
	}

	protected boolean PelotaEnElPiso()
	{
		if(pelota.getPosicionY()>=770)
		{
			return true;
		}

		return false;
	}


	//funcion que comprueba si los personajes tienen la pelota	
	protected boolean TieneLaPelota(int personaje,String lado)
	{



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


	protected boolean PelotaDebajoP1 ()
	{

		if(personaje1.getPosicionY()<700){
			if(pelota.getPosicionY()-pelota.getRadio()<=personaje1.getPosicionY()+100
					&&pelota.getPosicionX()>=personaje1.getPosicionX()&&pelota.getPosicionX()<=personaje1.getPosicionX()+130)
			{

				return true;

			}
		}
		return false;
	}

	protected boolean PelotaDebajoP2 ()
	{

		if(personaje2.getPosicionY()<700){
			if(pelota.getPosicionY()-pelota.getRadio()<=personaje2.getPosicionY()+100
					&&pelota.getPosicionX()>=personaje2.getPosicionX()&&pelota.getPosicionX()<=personaje2.getPosicionX()+130)
			{

				return true;

			}
		}
		return false;
	}





	public void rebotederecha()
	{
		pelota.setPicando(true);
		pelota.setBajando(true);

		pelota.setPateada(false);
		pelota.setCabeceada(false);


		pelota.cambiodireccion(0);

	}






	protected void FuncionesEnCero(Main m)
	{


		JOptionPane.showMessageDialog(null,"Gol");
		pelota.setPosicionX(850);
		pelota.setPosicionY(500);
		personaje1.setPosicionX(300);
		personaje2.setPosicionX(1350);



		m.TeclasEnFalse();


		pelota.setPicando(true);
		pelota.setPateada(false);
		pelota.setCabeceada(false);
		pelota.cambiodireccion(0);
	}


	protected boolean EntroAlArco ()
	{
		if((pelota.getPosicionX()<300&&pelota.getPosicionY()>=600)||(pelota.getPosicionX()>=1600&&pelota.getPosicionY()>=600))
			return true;
		else return false;
	}


	protected void guardarPartida(Main main) throws ClassNotFoundException, SQLException{
		String NombrePartida;


		while(true)
		{
			NombrePartida= JOptionPane.showInputDialog("Nombre de Partida : ");

			if(!BaseDeDatos.comprobarExistencia(NombrePartida)&&!NombrePartida.equals(' '))
			{
				break;

			}
			JOptionPane.showMessageDialog(null,"Nombre invalido o ya registrado");
		}

		BaseDeDatos.grabarPartida(NombrePartida, main);
	}


	protected void cargarPartida(Main main) throws ClassNotFoundException, SQLException{

		String NombrePartida;


		while(true)
		{
			NombrePartida= JOptionPane.showInputDialog("Nombre de Partida a Cargar: ");

			if(BaseDeDatos.comprobarExistencia(NombrePartida)&&!NombrePartida.equals(' '))
			{
				break;

			}
			JOptionPane.showMessageDialog(null,"No existe partida con ese nombre");
		}

		BaseDeDatos.getJuego(NombrePartida,main);


		modificarPersonajes(BaseDeDatos.getPersonajes(NombrePartida)); 
		modificarPelota(BaseDeDatos.getPelota(NombrePartida));


	}

	protected void modificarPersonajes(Vector<Personaje> personajesCargados)
	{

		Personaje personajecargado1=personajesCargados.elementAt(0);
		Personaje personajecargado2=personajesCargados.elementAt(1);

		personaje1.setPosicionX(personajecargado1.getPosicionX());
		personaje1.setPosicionY(personajecargado1.getPosicionY());
		personaje1.setSaltando(personajecargado1.isSaltando());
		personaje1.setSobreLaPelota(personajecargado1.isSobreLaPelota());
		personaje1.setVelSalto(personajecargado1.getVelSalto());

		personaje2.setPosicionX(personajecargado2.getPosicionX());
		personaje2.setPosicionY(personajecargado2.getPosicionY());
		personaje2.setSaltando(personajecargado2.isSaltando());
		personaje2.setSobreLaPelota(personajecargado2.isSobreLaPelota());
		personaje2.setVelSalto(personajecargado2.getVelSalto());

	}


	protected void modificarPelota(Pelota pelotacargada)
	{



		pelota.setBajando(pelotacargada.isBajando());
		pelota.setCabeceada(pelotacargada.isCabeceada());
		pelota.setDespeje(pelotacargada.isDespeje());
		pelota.setDireccionderecha(pelotacargada.isDireccionderecha());
		pelota.setEnergia(pelota.getEnergia());
		pelota.setEnvion(pelotacargada.getEnvion());
		pelota.setEnvionActivado(pelotacargada.isEnvionActivado());
		pelota.setFuerza(pelotacargada.getFuerza());
		pelota.setFuerzaDespeje(pelotacargada.getFuerzaDespeje());
		pelota.setMovimiento(pelotacargada.getMovimiento());
		pelota.setPateada(pelotacargada.isPateada());
		pelota.setPicando(pelotacargada.isPicando());
		pelota.setPosicionX(pelotacargada.getPosicionX());
		pelota.setPosicionY(pelotacargada.getPosicionY());
		pelota.setSubiendo(pelotacargada.isSubiendo());
		pelota.setVelocidadRebote(pelotacargada.getVelocidadRebote());


	}


}
