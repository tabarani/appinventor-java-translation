package com.Android.hellopurr;
import com.google.devtools.simple.runtime.components.HandlesEventDispatching;

import com.google.devtools.simple.runtime.components.android.AccelerometerSensor;
import com.google.devtools.simple.runtime.components.android.Button;
import com.google.devtools.simple.runtime.components.android.Form;
import com.google.devtools.simple.runtime.components.android.Sound;

import com.google.devtools.simple.runtime.events.EventDispatcher;

/**
 * HelloPurr example from David Wolber's book.
 * 
 * @author Joshua Swank
 */
public class HelloPurr extends Form implements HandlesEventDispatching
{
	public Button btnButton1;
	private Sound sndSound1;
	private AccelerometerSensor asnAccelerometerSensor1;
	
	void $define()
	{
		btnButton1 = new Button(this);
		btnButton1.Text("");
		btnButton1.Image("kitty.png");
		
		sndSound1 = new Sound(this);
		sndSound1.Source("meow.mp3");
		
		asnAccelerometerSensor1 = new AccelerometerSensor( this );
		
		EventDispatcher.registerEventForDelegation( this, "HelloPurr", "Click" );
		EventDispatcher.registerEventForDelegation( this, "HelloPurr", "Shaking" );
	}
	
	public void dispatchEvent( Object component, String id, String eventName, Object[] args )
	{
		if( component.equals( btnButton1 ) && eventName.equals( "Click" ) )
			btnButton1_Click();
		else if( component.equals( asnAccelerometerSensor1 ) && eventName.equals( "Shaking" ) )
			asnAccelerometerSensor1_Shaking();
	}
	
	private void btnButton1_Click()
	{
		sndSound1.Play();
		sndSound1.Vibrate( 500 );
	}
	
	private void asnAccelerometerSensor1_Shaking()
	{
		sndSound1.Play();
	}
}