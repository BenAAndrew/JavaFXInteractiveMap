package application;
	
import java.io.File;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;


public class Main extends Application {
	static WebEngine webEngine;
	static WebView webView;
	static boolean ready = true;
	static LeapControl leap = null;
	
	@Override
	public void start(Stage primaryStage) {
		try {	
			buildView();
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			Scene scene = new Scene(webView,500,500);
			primaryStage.setScene(scene);
			primaryStage.setFullScreen(true);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void buildView() {
		webView = new WebView();
		webEngine = webView.getEngine();	
		webEngine.getLoadWorker().stateProperty().addListener(
		new ChangeListener<State>() {
			@Override
			public void changed(ObservableValue<? extends State> ov, State oldState, State newState) {
				System.out.println(newState);
				if(newState == Worker.State.SUCCEEDED) {
					System.out.println("LEAP");
					leap = new LeapControl();
				}
			}
		});		
		webEngine.setJavaScriptEnabled(true);
		System.out.println(System.getProperty("user.dir")+"\\src\\application\\map.html");
		File f = new File(System.getProperty("user.dir")+"\\src\\application\\map.html");
		webEngine.load(f.toURI().toString());
	}
	
	public static void runJS(String cmd) {
		webEngine.executeScript(cmd);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
