/**
 * Sample Skeleton for 'MetroDeParis.fxml' Controller Class
 */

package it.polito.tdp.metrodeparis;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.Model;
import it.polito.tdp.metrodeparis.model.Util;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class MetroDeParisController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxPartenza"
    private ComboBox<Fermata> boxPartenza; // Value injected by FXMLLoader

    @FXML // fx:id="boxArrivo"
    private ComboBox<Fermata> boxArrivo; // Value injected by FXMLLoader

    @FXML // fx:id="btnPercorso"
    private Button btnPercorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
		
		try {
		
			Fermata partenza = boxPartenza.getValue();
			Fermata arrivo = boxArrivo.getValue();

			if( partenza == null ) {
				txtResult.setText("Selezionare una Stazione di Partenza.");
				return;
			}

			if( arrivo == null ) {
				txtResult.setText("Selezionare una Stazione di Arrivo.");
				return;
			}

			model.creaGrafo();
		
			model.calcolaPercorso(partenza, arrivo);

			List<Fermata> percorso = model.getPercorso();
			String tempoPercorso = Util.timeConverter((int) model.getTempoPercorso());				

			txtResult.setText("Percorso: "+percorso.toString()+"\n\nTempo di percorrenza stimato: "+tempoPercorso);
		
		} catch (RuntimeException re) {
			txtResult.setText(re.getMessage());
		}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxPartenza != null : "fx:id=\"boxPartenza\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert boxArrivo != null : "fx:id=\"boxArrivo\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'MetroDeParis.fxml'.";

    }

    public void setModel(Model model) {
		this.model = model;
		boxPartenza.getItems().addAll(model.getAllFermate());
		boxArrivo.getItems().addAll(model.getAllFermate());
	}
}
