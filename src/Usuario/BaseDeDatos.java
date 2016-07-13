package Usuario;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import Juego.Pelota;
import Juego.Personaje;

public class BaseDeDatos {



	public static void getJuego(String NombrePartida,Main main) throws ClassNotFoundException, SQLException
	{


		Class.forName("com.mysql.jdbc.Driver") ;
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabezonesfutbol", "root", "") ;
		Statement stmt = conn.createStatement() ;
		String query = "SELECT * FROM juego WHERE  NombrePartida = '"+NombrePartida+"';" ;
		ResultSet rs = stmt.executeQuery(query) ;
		while (rs.next())
		{ 


			main.marcador1=rs.getInt("Puntaje1");
			main.marcador2=rs.getInt("Puntaje2");
			main.s=rs.getInt("TiempoSegundos");
			main.m=rs.getInt("TiempoMinutos");
			main.cont=rs.getInt("cont");
			main.saltoatras=intToBool(rs.getInt("saltoatras"));
			main.fuerzarebote=rs.getInt("fuerzarebote");
			main.rebote=intToBool(rs.getInt("rebote"));
		}

	}


	public static Vector<Personaje> getPersonajes(String NombrePartida) throws ClassNotFoundException, SQLException
	{
		Vector<Personaje> resultado = new Vector<Personaje>();

		Class.forName("com.mysql.jdbc.Driver") ;
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabezonesfutbol", "root", "") ;
		Statement stmt = conn.createStatement() ;
		String query = "SELECT * FROM personaje WHERE Juego_NombrePartida = '"+NombrePartida+"';" ;
		ResultSet rs = stmt.executeQuery(query) ;

		while (rs.next())
		{ 
			Personaje personaje=new Personaje(rs.getInt("PosicionX"),rs.getInt("PosicionY"),null);
			personaje.setSaltando(intToBool(rs.getInt("saltando")));
			personaje.setSobreLaPelota(intToBool(rs.getInt("SobreLaPelota")));
			resultado.add(personaje);
		}

		return resultado;
	}

	public static Pelota getPelota(String NombrePartida) throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver") ;
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabezonesfutbol", "root", "") ;
		Statement stmt = conn.createStatement() ;
		String query = "SELECT * FROM pelota WHERE Juego_NombrePartida = '"+NombrePartida+"';" ;
		ResultSet rs = stmt.executeQuery(query) ;

		while (rs.next())
		{//modifica el main de acuerdo a los datos de la base de datos
			Pelota pelota=new Pelota(rs.getInt("PosicionX"),rs.getInt("PosicionY"),50,null);
			pelota.setBajando(rs.getBoolean("bajando"));
			pelota.setPicando(rs.getBoolean("picando"));
			pelota.setPateada(rs.getBoolean("pateada"));
			pelota.setCabeceada(rs.getBoolean("cabeceada"));
			pelota.setMovimiento(rs.getInt("movimiento"));
			pelota.setDireccionderecha(rs.getBoolean("direccionderecha"));
			pelota.setSubiendo(rs.getBoolean("subiendo"));
			pelota.setEnvion(rs.getInt("envion"));
			pelota.setEnergia(rs.getInt("energia"));
			pelota.setDespeje(rs.getBoolean("despeje"));
			pelota.setFuerzaDespeje(rs.getInt("fuerzaDespeje"));
			pelota.setVelocidadRebote(rs.getInt("velocidadRebote"));
			return pelota;
		}

		return null;
	}

	public static int getId(String nombrePartida) throws ClassNotFoundException, SQLException
	{
		int id=0;
		Class.forName("com.mysql.jdbc.Driver") ;
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabezonesfutbol", "root", "") ;
		Statement stmt = conn.createStatement() ;

		String query = "SELECT idJuego FROM juego WHERE NombrePartida = '"+nombrePartida+"';" ;

		ResultSet rs = stmt.executeQuery(query) ;
		while (rs.next())
		{
			id=rs.getInt("idJuego");
		}


		return id;
	}

	public static  void grabarPelota(String NombrePartida,Pelota pelota) throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver") ;
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabezonesfutbol", "root", "");
		Statement stmt = conn.createStatement() ;

		stmt.executeUpdate("INSERT  INTO pelota(PosicionX,PosicionY,Juego_idJuego,Juego_NombrePartida,bajando,picando,pateada,cabeceada,movimiento,direccionderecha,"
				+ "subiendo,envion,energia,despeje,envionActivado,fuerza,fuerzaDespeje,VelocidadRebote)"
				+ " VALUES('"+pelota.getPosicionX()+"', '"+pelota.getPosicionY()+"','"+getId(NombrePartida)+"','"+NombrePartida+"','"+boolToInt(pelota.isBajando())+"', '"+boolToInt(pelota.isPicando())+"'"
				+ ",'"+boolToInt(pelota.isPateada())+"','"+boolToInt(pelota.isCabeceada())+"','"+pelota.getMovimiento()+"','"+boolToInt(pelota.isDireccionderecha())+"','"+boolToInt(pelota.isSubiendo())+"', '"+pelota.getEnvion()+"',"
				+ "'"+pelota.getEnergia()+"','"+boolToInt(pelota.isDespeje())+"','"+boolToInt(pelota.isEnvionActivado())+"', '"+pelota.getFuerza()+"','"+pelota.getFuerzaDespeje()+"','"+pelota.getVelocidadRebote()+"')");
	}


	public static void grabarPersonajes(Vector<Personaje> personajes,String NombrePartida) throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver") ;
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabezonesfutbol", "root", "") ;
		Statement stmt = conn.createStatement() ;

		for(Personaje personaje:personajes){
			stmt.executeUpdate("INSERT  INTO personaje(PosicionX,PosicionY,Juego_idJuego,Juego_NombrePartida,saltando,sobreLaPelota) VALUES('"+personaje.getPosicionX()+"', '"+personaje.getPosicionY()+"','"+getId(NombrePartida)+"','"+NombrePartida+"'"
					+ ",'"+boolToInt(personaje.isSaltando())+"', '"+boolToInt(personaje.isSobreLaPelota())+"')");
		}

	}




	public static void grabarJuego(String NombrePartida,Main main) throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver") ;
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabezonesfutbol", "root", "") ;
		Statement stmt = conn.createStatement() ;



		stmt.execute("INSERT INTO juego (NombrePartida,Puntaje1,Puntaje2,TiempoSegundos,TiempoMinutos,cont,saltoatras,fuerzarebote,rebote)"
				+ " VALUES('"+NombrePartida+"','"+main.marcador1+"','"+main.marcador2+"','"+main.s+"','"+main.m+"','"+main.cont+"','"+boolToInt(main.saltoatras)+"','"+main.fuerzarebote+"','"+boolToInt(main.rebote)+"')");


	}

	public static void grabarPartida(String NombrePartida,Main main) throws ClassNotFoundException, SQLException
	{
		//Une las funciones grabarpelota,grabarpersonajes y grabarjuego
		grabarJuego(NombrePartida,main);
		grabarPelota(NombrePartida, main.pelota);
		grabarPersonajes(main.Personajes, NombrePartida);

	}

	public static void cargarPartida(String NombrePartida) throws ClassNotFoundException, SQLException
	{
		//Une las funciones grabarpelota,grabarpersonajes y grabarjuego


	}


	public static boolean comprobarExistencia(String NombrePartida)throws ClassNotFoundException, SQLException
	{
		Class.forName("com.mysql.jdbc.Driver") ;
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cabezonesfutbol", "root", "") ;
		Statement stmt = conn.createStatement() ;

		String query = "SELECT idJuego FROM juego WHERE NombrePartida = '"+NombrePartida+"';" ;
		ResultSet rs = stmt.executeQuery(query) ;
		if(rs.next()){return true;}

		return false;


	}

	private  static int boolToInt( boolean b ){
		if ( b )
			return 1;
		return 0;
	}

	private  static boolean intToBool( int b ){
		if ( b==1 )
			return true;
		return false;
	}




}
