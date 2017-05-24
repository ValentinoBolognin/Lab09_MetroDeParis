package it.polito.tdp.metrodeparis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javadocmd.simplelatlng.LatLng;

import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.Connessione;

public class MetroDAO {

	public List<Fermata> getAllFermate() {

		final String sql = "SELECT id_fermata, nome, coordx, coordy FROM fermata ORDER BY nome ASC";
		List<Fermata> fermate = new ArrayList<Fermata>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				Fermata f = new Fermata(rs.getInt("id_Fermata"), rs.getString("nome"), new LatLng(rs.getDouble("coordx"), rs.getDouble("coordy")));
				fermate.add(f);
			}

			st.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore di connessione al Database.");
		}

		return fermate;
	}

	public List<Connessione> listConnessioni() {
		final String sql = "SELECT f1.id_fermata as id_fermata1, f1.nome as nome1, f1.coordX as coordX1, f1.coordY as coordY1, " +
				"f2.id_fermata as id_fermata2, f2.nome as nome2, f2.coordX as coordX2, f2.coordY as coordY2, " +
				"l.velocita as velocita " +
				"FROM connessione c, fermata f1, fermata f2, linea l " +
				"WHERE f1.id_fermata = c.id_stazP " +
				"AND f2.id_fermata = c.id_stazA " + 
				"AND l.id_linea = c.id_linea";

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
						
			ResultSet rs = st.executeQuery() ;
			
			List<Connessione> connessioni = new ArrayList<>() ;
			
			while(rs.next()) {
				Fermata f1 = new Fermata(rs.getInt("id_fermata1"), rs.getString("nome1"), new LatLng(rs.getDouble("coordX1"), rs.getDouble("coordY1"))) ;
				Fermata f2 = new Fermata(rs.getInt("id_fermata2"), rs.getString("nome2"), new LatLng(rs.getDouble("coordX2"), rs.getDouble("coordY2"))) ;
				double velocita = rs.getDouble("velocita") ;
				Connessione c = new Connessione(f1, f2, velocita) ;
				connessioni.add(c) ;
			}
			
			rs.close();
			conn.close();
			
			return connessioni ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
}
