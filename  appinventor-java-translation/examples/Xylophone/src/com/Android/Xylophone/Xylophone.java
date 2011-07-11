package com.Android.Xylophone;

import com.google.devtools.simple.runtime.components.HandlesEventDispatching;

import com.google.devtools.simple.runtime.components.android.Button;
import com.google.devtools.simple.runtime.components.android.Clock;
import com.google.devtools.simple.runtime.components.android.Form;
import com.google.devtools.simple.runtime.components.android.HorizontalArrangement;
import com.google.devtools.simple.runtime.components.android.Sound;
import com.google.devtools.simple.runtime.events.EventDispatcher;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Xylophone example from David Wolber's book.
 * 
 * @author Joshua Swank
 */

public class Xylophone extends Form implements HandlesEventDispatching
{
	private Button btnButton1, btnButton2, btnButton3, btnButton4, btnButton5, btnButton6, btnButton7, btnButton8, btnPlayButton, btnResetButton;
	private HorizontalArrangement hrzHorizontalArrangement1;
	private Sound sndSound1;
	private Clock clkClock1;
	
	private final List<String> notes = new ArrayList<String>();
	private final List<Calendar> times = new ArrayList<Calendar>();
	private int count = 0;
	
	void $define()
	{
		sndSound1 = new Sound( this );
		
		clkClock1 = new Clock( this );
		clkClock1.TimerEnabled( false );
		
		btnButton1 = new Button( this );
		btnButton1.Text( "C" );
		btnButton1.BackgroundColor( btnButton1.COLOR_MAGENTA );
		btnButton1.Width( Button.LENGTH_FILL_PARENT );
		btnButton1.Height( 40 );
		
		btnButton2 = new Button( this );
		btnButton2.Text( "D" );
		btnButton2.BackgroundColor( btnButton1.COLOR_RED );
		btnButton2.Width( Button.LENGTH_FILL_PARENT );
		btnButton2.Height( 40 );
		
		btnButton3 = new Button( this );
		btnButton3.Text( "E" );
		btnButton3.BackgroundColor( btnButton1.COLOR_PINK );
		btnButton3.Width( Button.LENGTH_FILL_PARENT );
		btnButton3.Height( 40 );
		
		btnButton4 = new Button( this );
		btnButton4.Text( "F" );
		btnButton4.BackgroundColor( btnButton1.COLOR_ORANGE );
		btnButton4.Width( Button.LENGTH_FILL_PARENT );
		btnButton4.Height( 40 );
		
		btnButton5 = new Button( this );
		btnButton5.Text( "G" );
		btnButton5.BackgroundColor( btnButton1.COLOR_YELLOW );
		btnButton5.Width( Button.LENGTH_FILL_PARENT );
		btnButton5.Height( 40 );
		
		btnButton6 = new Button( this );
		btnButton6.Text( "A" );
		btnButton6.BackgroundColor( btnButton1.COLOR_GREEN );
		btnButton6.Width( Button.LENGTH_FILL_PARENT );
		btnButton6.Height( 40 );
		
		btnButton7 = new Button( this );
		btnButton7.Text( "B" );
		btnButton7.BackgroundColor( btnButton1.COLOR_CYAN );
		btnButton7.Width( Button.LENGTH_FILL_PARENT );
		btnButton7.Height( 40 );
		
		btnButton8 = new Button( this );
		btnButton8.Text( "C" );
		btnButton8.BackgroundColor( btnButton1.COLOR_BLUE );
		btnButton8.Width( Button.LENGTH_FILL_PARENT );
		btnButton8.Height( 40 );
		
		hrzHorizontalArrangement1 = new HorizontalArrangement( this );
		
		btnPlayButton = new Button( hrzHorizontalArrangement1 );
		btnPlayButton.Text( "Play" );
		
		btnResetButton = new Button( hrzHorizontalArrangement1 );
		btnResetButton.Text( "Reset" );
		
		EventDispatcher.registerEventForDelegation( this, "Xylophone", "Click" );
		EventDispatcher.registerEventForDelegation( this, "Xylophone", "Initialize" );
		EventDispatcher.registerEventForDelegation( this, "Xylophone", "Timer" );
	}
	
	public void dispatchEvent( Object component, String id, String eventName, Object[] args )
	{
		if( component.equals( this ) && eventName.equals( "Initialize" ))
			this_Initialize();
		else if( eventName.equals( "Click" ))
		{
			if( component.equals( btnButton1 ))
				btnButton1_Click();
			else if( component.equals( btnButton2 ))
				btnButton2_Click();
			else if( component.equals( btnButton3 ))
				btnButton3_Click();
			else if( component.equals( btnButton4 ))
				btnButton4_Click();
			else if( component.equals( btnButton5 ))
				btnButton5_Click();
			else if( component.equals( btnButton6 ))
				btnButton6_Click();
			else if( component.equals( btnButton7 ))
				btnButton7_Click();
			else if( component.equals( btnButton8 ))
				btnButton8_Click();
			else if( component.equals( btnResetButton ))
				btnResetButton_Click();
			else if( component.equals( btnPlayButton ))
				btnPlayButton_Click();
		}
		else if( component.equals( clkClock1 ) && eventName.equals( "Timer" ))
			clkClock1_Timer();
	}
	
	private void this_Initialize()
	{
		sndSound1.Source( "1.wav" );
		sndSound1.Source( "2.wav" );
		sndSound1.Source( "3.wav" );
		sndSound1.Source( "4.wav" );
		sndSound1.Source( "5.wav" );
		sndSound1.Source( "6.wav" );
		sndSound1.Source( "7.wav" );
		sndSound1.Source( "8.wav" );
	}
	
	private void PlayNote( int number )
	{
		sndSound1.Source( String.valueOf( number ) + ".wav" );
		notes.add( sndSound1.Source() );
		times.add( Clock.Now() );
		sndSound1.Play();
	}
	
	private void btnButton1_Click()
	{
		PlayNote( 1 );
	}
	
	private void btnButton2_Click()
	{
		PlayNote( 2 );
	}
	
	private void btnButton3_Click()
	{
		PlayNote( 3 );
	}
	
	private void btnButton4_Click()
	{
		PlayNote( 4 );
	}
	
	private void btnButton5_Click()
	{
		PlayNote( 5 );
	}
	
	private void btnButton6_Click()
	{
		PlayNote( 6 );
	}
	
	private void btnButton7_Click()
	{
		PlayNote( 7 );
	}
	
	private void btnButton8_Click()
	{
		PlayNote( 8 );
	}
	
	private void btnResetButton_Click()
	{
		notes.clear();
		times.clear();
		sndSound1.Vibrate( 500 );
	}
	
	private void btnPlayButton_Click()
	{
		if( notes.size() > 0 )
		{
			count = 0;
			PlayBackNote();
		}
	}
	
	private void clkClock1_Timer()
	{
		clkClock1.TimerEnabled( false );
		PlayBackNote();
	}
	
	private void PlayBackNote()
	{
		sndSound1.Source( notes.get( count ));
		sndSound1.Play();
		if( count < notes.size() - 1 )
		{
			clkClock1.TimerInterval( (int)Clock.Duration( times.get( count ), times.get( count + 1 ) ));
			count++;
			clkClock1.TimerEnabled( true );
		}
	}
}
