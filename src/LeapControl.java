package application;

import java.io.IOException;

import com.leapmotion.leap.Bone;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Pointable;
import com.leapmotion.leap.Vector;

import javafx.application.Platform;

import com.leapmotion.leap.Gesture.Type;

class LeapListener extends Listener {
	boolean activeHand = false;

	public void onInit(Controller controller) {
		System.out.println("Leap motion started");
	}
	
	public void onConnect(Controller controller) {
		System.out.println("Leap motion connected");
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
		controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
		controller.enableGesture(Gesture.Type.TYPE_INVALID);
		controller.enableGesture(Gesture.Type.TYPE_SWIPE);
	}
	
	public void onDisconnect(Controller controller) {
		System.out.println("Leap motion disconnected");
	}
	
	public void onExit(Controller controller) {
		System.out.println("Exited");
	}
	
	public void onFrame(Controller controller) {
		Frame frame = controller.frame();	
		Hand hand = frame.hands().get(0);
		if(hand.isValid()) {
			if(Math.abs(hand.palmNormal().roll()) > 2 && !activeHand){
				activeHand = true;
			    Platform.runLater(() -> {
			    	Main.runJS("test()");
			    });
			} else if(Math.abs(hand.palmNormal().roll()) < 0.2) {
				activeHand = false;
			}
		}
	}
}

public class LeapControl {
	LeapListener listener = null;
	Controller controller = null;

	public LeapControl() {
		listener = new LeapListener();
		controller = new Controller();
		controller.addListener(listener);
	}
}
