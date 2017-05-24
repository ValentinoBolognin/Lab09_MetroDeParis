package it.polito.tdp.metrodeparis.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;

import it.polito.tdp.metrodeparis.dao.MetroDAO;

public class Model {
	
	MetroDAO metroDAO;
	
	private WeightedMultigraph<Fermata, DefaultWeightedEdge> graph ;
	private List<Fermata> fermate ;
	
	private List<DefaultWeightedEdge> archiPercorso ;
	private List<Fermata> percorso;
	private double tempoPercorso ;
	
	public Model() {
		metroDAO = new MetroDAO() ;
		archiPercorso = null ;
		percorso = new ArrayList<Fermata>();
		tempoPercorso = 0.0 ;
	}

	public void creaGrafo() {
		
		this.graph = new WeightedMultigraph<Fermata, DefaultWeightedEdge>(DefaultWeightedEdge.class) ;
		
		Graphs.addAllVertices(graph, this.getAllFermate()) ;
	
		for(Connessione c : metroDAO.listConnessioni()) {
			DefaultWeightedEdge edge = graph.addEdge(c.getF1(), c.getF2());
			graph.setEdgeWeight(edge, c.getTempo());
		}
	}
	
	private WeightedMultigraph<Fermata, DefaultWeightedEdge> getGrafo() {
		if(this.graph==null) {
			this.creaGrafo();
		}
		return this.graph ;
	}
	
	public List<Fermata> getAllFermate() {
		if(this.fermate==null) {
			this.fermate = metroDAO.getAllFermate() ;
		}
		return this.fermate ;
	}

	public void calcolaPercorso(Fermata partenza, Fermata arrivo) {
		DijkstraShortestPath<Fermata, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<Fermata, DefaultWeightedEdge>(graph, partenza, arrivo);

		archiPercorso = dijkstra.getPathEdgeList();
		
		if (archiPercorso == null)
			throw new RuntimeException("Non è stato possiible creare un percorso.");
		
		for (DefaultWeightedEdge edge : archiPercorso) {
			Fermata fermata = graph.getEdgeTarget(edge);
			percorso.add(fermata);
		}
		
		if (archiPercorso.size() - 1 > 0) {
			tempoPercorso = dijkstra.getPathLength() + (archiPercorso.size() - 1) * 30;
		}
	}

	public List<Fermata> getPercorso() {
		if (archiPercorso == null)
			throw new RuntimeException("Non è stato possiible creare un percorso.");
		
		return percorso;
	}

	public double getTempoPercorso() {
		if (archiPercorso == null)
			throw new RuntimeException("Non è stato possiible creare un percorso.");
		
		return tempoPercorso;
	}
	
}
