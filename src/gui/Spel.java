package gui;

import domein.DomeinController;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class Spel extends GridPane{
	
	private DomeinController dc;
	private Button[][] buttons  = new Button[15][15];
	Button btnScore;
	
	
	public Spel(DomeinController dc) {
		
		btnScore= new Button("Score board");
		this.add(btnScore, 60,17);
		btnScore.setMinHeight(50);
		btnScore.setMinWidth(90);
		btnScore.setStyle("-fx-background-color: #778899;");
		btnScore.setOnAction(this::buttonOnAction);
	for (int i = 0; i < buttons.length; i++) {
		for (int j = 0; j < buttons.length; j++) {
			buttons[i][j] = new Button();
			this.add(buttons[i][j], i, j);
			buttons[i][j].setMinHeight(40);
			buttons[i][j].setMinWidth(40);
			setAlignment(buttons[i][j].getAlignment());				
			buttons[i][j].setStyle("-fx-border-color: #778899 ;");
		
	}  
		
	}
	
	private void buttonOnAction(ActionEvent event) {
			
	    	ScoreBord scr = new ScoreBord(dc); // <1>

	    	Scene scene = new Scene(scr, 1550, 800);
	        Stage stage = (Stage) this.getScene().getWindow();
	        scr.setStyle("-fx-background-color: 	#100000  ;");
	        stage.setScene(scene);
	        stage.show();
		}
   }
}
	
