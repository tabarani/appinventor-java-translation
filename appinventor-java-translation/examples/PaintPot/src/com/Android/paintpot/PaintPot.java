package com.Android.paintpot;

import com.google.devtools.simple.runtime.components.HandlesEventDispatching;

import com.google.devtools.simple.runtime.components.android.Button;
import com.google.devtools.simple.runtime.components.android.Camera;
import com.google.devtools.simple.runtime.components.android.Canvas;
import com.google.devtools.simple.runtime.components.android.Form;
import com.google.devtools.simple.runtime.components.android.HorizontalArrangement;

import com.google.devtools.simple.runtime.events.EventDispatcher;

/**
 * PaintPot example from David Wolber's book.
 * 
 * @author Joshua Swank
 */
public class PaintPot extends Form implements HandlesEventDispatching
{
	public Button btnRedButton, btnGreenButton, btnBlueButton, btnTakePictureButton, btnWipeButton, btnBigButton, btnSmallButton;
	public Camera cmrCamera1;
	public Canvas cnvDrawingCanvas;
	private HorizontalArrangement hrzHorizontalArrangement1, hrzHorizontalArrangement2;
	
	private float dotSize = 2.0f;
	
	void $define()
	{
		cmrCamera1 = new Camera( this );
		
		hrzHorizontalArrangement1 = new HorizontalArrangement( this );
		
		btnRedButton = new Button( hrzHorizontalArrangement1 );
		btnRedButton.Text( "Red" );
		btnRedButton.BackgroundColor( Button.COLOR_RED );
		
		btnGreenButton = new Button( hrzHorizontalArrangement1 );
		btnGreenButton.Text( "Green" );
		btnGreenButton.BackgroundColor( Button.COLOR_GREEN );
		
		btnBlueButton = new Button( hrzHorizontalArrangement1 );
		btnBlueButton.Text( "Blue" );
		btnBlueButton.BackgroundColor( Button.COLOR_BLUE );
		
		cnvDrawingCanvas = new Canvas( this );
		cnvDrawingCanvas.BackgroundImage( "kitty.png" );
		cnvDrawingCanvas.PaintColor( Canvas.COLOR_RED );
		
		hrzHorizontalArrangement2 = new HorizontalArrangement( this );
		
		btnTakePictureButton = new Button( hrzHorizontalArrangement2 );
		btnTakePictureButton.Text( "Take Picture" );
		
		btnWipeButton = new Button( hrzHorizontalArrangement2 );
		btnWipeButton.Text( "Wipe" );
		
		btnBigButton = new Button( hrzHorizontalArrangement2 );
		btnBigButton.Text( "Big Dots" );
		
		btnSmallButton = new Button( hrzHorizontalArrangement2 );
		btnSmallButton.Text( "Small Dots" );
		
		EventDispatcher.registerEventForDelegation( this, "PaintPot", "AfterPicture" );
		EventDispatcher.registerEventForDelegation( this, "PaintPot", "Click" );
		EventDispatcher.registerEventForDelegation( this, "PaintPot", "Dragged" );
		EventDispatcher.registerEventForDelegation( this, "PaintPot", "Touched" );
	}
	
	public void dispatchEvent( Object component, String id, String eventName, Object[] args )
	{
		if( eventName.equals( "Click" ) )
			if( component.equals( btnRedButton ))
				btnRedButton_Click();
			else if( component.equals( btnGreenButton ))
				btnGreenButton_Click();
			else if( component.equals( btnBlueButton ))
				btnBlueButton_Click();
			else if( component.equals( btnTakePictureButton ))
				btnTakePictureButton_Click();
			else if( component.equals( btnWipeButton ))
				btnWipeButton_Click();
			else if( component.equals( btnBigButton ))
				btnBigButton_Click();
			else if( component.equals( btnSmallButton ))
				btnSmallButton_Click();
		
		if( eventName.equals( "Dragged" ) && component.equals( cnvDrawingCanvas ))
			cnvDrawingCanvas_Dragged( (Float)args[0], (Float)args[1], (Float)args[2], (Float)args[3], (Float)args[4], (Float)args[5], (Boolean)args[6] );
		
		if( eventName.equals( "Touched" ) && component.equals( cnvDrawingCanvas ))
			cnvDrawingCanvas_Touched( (Float)args[0], (Float)args[1], (Boolean)args[2] );
		
		if( eventName.equals( "AfterPicture" ) && component.equals( cmrCamera1 ))
			cmrCamera1_AfterPicture( args[0].toString() );
	}
	
	private void btnRedButton_Click()
	{
		cnvDrawingCanvas.PaintColor( Canvas.COLOR_RED );
	}
	
	private void btnGreenButton_Click()
	{
		cnvDrawingCanvas.PaintColor( Canvas.COLOR_GREEN );
	}
	
	private void btnBlueButton_Click()
	{
		cnvDrawingCanvas.PaintColor( Canvas.COLOR_BLUE );
	}
	
	private void btnTakePictureButton_Click()
	{
		cmrCamera1.TakePicture();
	}
	
	private void btnWipeButton_Click()
	{
		cnvDrawingCanvas.Clear();
	}
	
	private void btnBigButton_Click()
	{
		dotSize = 8.0f;
	}
	
	private void btnSmallButton_Click()
	{
		dotSize = 2.0f;
	}
	
	private void cnvDrawingCanvas_Touched( float x, float y, boolean touchedSprite )
	{
		cnvDrawingCanvas.DrawCircle( (int)x, (int)y, dotSize );
	}
	
	private void cmrCamera1_AfterPicture( String image )
	{
		cnvDrawingCanvas.BackgroundImage( image );
	}
	
	private void cnvDrawingCanvas_Dragged( float startX, float startY, float prevX, float prevY, float currentX, float currentY, boolean draggedSprite )
	{
		cnvDrawingCanvas.DrawLine( (int)prevX, (int)prevY, (int)currentX, (int)currentY );
	}
}
