package com.Android.MoleMash;

import java.util.Random;

import com.google.devtools.simple.runtime.components.HandlesEventDispatching;

import com.google.devtools.simple.runtime.components.android.Button;
import com.google.devtools.simple.runtime.components.android.Canvas;
import com.google.devtools.simple.runtime.components.android.Clock;
import com.google.devtools.simple.runtime.components.android.Form;
import com.google.devtools.simple.runtime.components.android.HorizontalArrangement;
import com.google.devtools.simple.runtime.components.android.ImageSprite;
import com.google.devtools.simple.runtime.components.android.Label;
import com.google.devtools.simple.runtime.components.android.Sound;
import com.google.devtools.simple.runtime.events.EventDispatcher;

import java.util.Random;

/**
 * MoleMash example from David Wolber's book.
 * 
 * @author Joshua Swank
 */
public class MoleMash extends Form implements HandlesEventDispatching
{
	private Canvas cnvCanvas1;
	private Button btnResetButton;
	private HorizontalArrangement hrzHorizontalArrangement1, hrzHorizontalArrangement2;
	private Clock clkClock1;
	private Sound sndSound1;
	private ImageSprite ispMole;
	private Label lblHitsLabel, lblHitsCountLabel, lblMissesLabel, lblMissesCountLabel;
	
	void $define()
	{
		sndSound1 = new Sound( this );
		clkClock1 = new Clock( this );
		
		cnvCanvas1 = new Canvas( this );
		cnvCanvas1.Width( Canvas.LENGTH_FILL_PARENT );
		cnvCanvas1.Height( 300 );
		
		ispMole = new ImageSprite( cnvCanvas1 );
		ispMole.Picture( "mole.png" );
		
		btnResetButton = new Button( this );
		btnResetButton.Text( "Reset" );
		
		hrzHorizontalArrangement1 = new HorizontalArrangement( this );
		
		lblHitsLabel = new Label( hrzHorizontalArrangement1 );
		lblHitsLabel.Text( "Hits:" );
		
		lblHitsCountLabel = new Label( hrzHorizontalArrangement1 );
		lblHitsCountLabel.Text( "0" );
		
		hrzHorizontalArrangement2 = new HorizontalArrangement( this );
		
		lblMissesLabel = new Label( hrzHorizontalArrangement2 );
		lblMissesLabel.Text( "Misses:" );
		
		lblMissesCountLabel = new Label( hrzHorizontalArrangement2 );
		lblMissesCountLabel.Text( "0" );
		
		EventDispatcher.registerEventForDelegation( this, "MoleMash", "Click" );
		EventDispatcher.registerEventForDelegation( this, "MoleMash", "Initialize" );
		EventDispatcher.registerEventForDelegation( this, "MoleMash", "Timer" );
		EventDispatcher.registerEventForDelegation( this, "MoleMash", "Touched" );
	}
	
	public void dispatchEvent( Object component, String id, String eventName, Object[] args )
	{
		if( eventName.equals( "Initialize" ) && component.equals( this ))
			this_Initialize();
		else if( eventName.equals( "Touched" ))
		{
			if( component.equals( cnvCanvas1 ) )
				cnvCanvas1_Touched( (Float)args[0], (Float)args[1], (Boolean)args[2] );
			else if( component.equals( ispMole ))
				ispMole_Touched( (Float)args[0], (Float)args[1] );
		}
		else if( eventName.equals( "Click" ) && component.equals( btnResetButton ))
			btnResetButton_Click();
		else if( eventName.equals( "Timer" ) && component.equals( clkClock1 ))
			clkClock1_Timer();
	}
	
	private void MoveMole()
	{
		Random random = new Random();
		
		ispMole.MoveTo( random.nextDouble() * (cnvCanvas1.Width() - ispMole.Width()), random.nextDouble() * (cnvCanvas1.Height() - ispMole.Height()));
	}
	
	private void cnvCanvas1_Touched( float x, float y, boolean touchedSprite )
	{
		if( touchedSprite )
			lblHitsCountLabel.Text( new Integer(Integer.valueOf( lblHitsCountLabel.Text()) + 1).toString() );
		else
			lblMissesCountLabel.Text( new Integer(Integer.valueOf( lblMissesCountLabel.Text()) + 1).toString() );
	}
	
	private void ispMole_Touched( float x, float y )
	{
		sndSound1.Vibrate( 100 );
	}
	
	private void btnResetButton_Click()
	{
		lblHitsCountLabel.Text( "0" );
		lblMissesCountLabel.Text( "0" );
	}
	
	private void clkClock1_Timer()
	{
		MoveMole();
	}
	
	private void this_Initialize()
	{
		MoveMole();
	}
}
