package it.polito.tdp.metrodeparis.model;

import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

public class Connessione {
	
	private Fermata f1;
	private Fermata f2;
	private double velocita;
	private double spazio;
	private double tempo;
	
	public Connessione(Fermata f1, Fermata f2, double velocita) {
		super();
		this.f1 = f1;
		this.f2 = f2;
		this.velocita = velocita;
		this.spazio = LatLngTool.distance(f1.getCoords(),f2.getCoords(), LengthUnit.KILOMETER);
		this.tempo = (this.spazio / this.velocita) * 60 * 60;
	}

	public Fermata getF1() {
		return f1;
	}

	public void setF1(Fermata f1) {
		this.f1 = f1;
	}

	public Fermata getF2() {
		return f2;
	}

	public void setF2(Fermata f2) {
		this.f2 = f2;
	}

	public double getVelocita() {
		return velocita;
	}

	public void setVelocita(double velocita) {
		this.velocita = velocita;
	}

	public double getSpazio() {
		return spazio;
	}

	public void setSpazio(double spazio) {
		this.spazio = spazio;
	}

	public double getTempo() {
		return tempo;
	}

	public void setTempo(double tempo) {
		this.tempo = tempo;
	}
	
}
