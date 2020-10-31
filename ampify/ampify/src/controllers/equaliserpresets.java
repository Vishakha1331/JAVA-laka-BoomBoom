
package controllers;

import javax.swing.JLabel;
import javax.swing.JSlider;

import javafx.scene.media.MediaPlayer;

public class equaliserpresets{

	public String[] name = new String[12];
	public Float[][] Presets = new Float[12][];

	public equaliserpresets() {

		{
			name[0] = "Normal";
			Presets[0] = new Float[] { 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f };
		}
		
		{
			name[1] = "dance";
			Presets[1] = new Float[] { 9.6f, 7.2f, 2.4f, -1.11022e-15f, -1.11022e-15f, -5.6f, -7.2f, -7.2f,
					-1.11022e-15f, -1.11022e-15f };
		}
		{
			name[2] = "fullbass";
			Presets[2] = new Float[] { -8.0f, 9.6f, 9.6f, 5.6f, 1.6f, -4.0f, -8.0f, -10.4f, -11.2f, -11.2f };
		}
		{
			name[3] = "fullbasstreble";
			Presets[3] = new Float[] { 7.2f, 5.6f, -1.11022e-15f, -7.2f, -4.8f, 1.6f, 8.0f, 11.2f, 12.0f, 12.0f };
		}
		{
			name[4] = "fulltreble";
			Presets[4] = new Float[] { -9.6f, -9.6f, -9.6f, -4.0f, 2.4f, 11.2f, 16.0f, 16.0f, 16.0f, 16.8f };
		}
		{
			name[5] = "headphones";
			Presets[5] = new Float[] { 4.8f, 11.2f, 5.6f, -3.2f, -2.4f, 1.6f, 4.8f, 9.6f, 12.8f, 14.4f };
		}
		
	
		{
			name[6] = "party";
			Presets[6] = new Float[] { 7.2f, 7.2f, -1.11022e-15f, -1.11022e-15f, -1.11022e-15f, -1.11022e-15f,
					-1.11022e-15f, -1.11022e-15f, 7.2f, 7.2f };
		}
		{
			name[7] = "pop";
			Presets[7] = new Float[] { -1.6f, 4.8f, 7.2f, 8.0f, 5.6f, -1.11022e-15f, -2.4f, -2.4f, -1.6f, -1.6f };
		}
		
		{
			name[8] = "rock";
			Presets[8] = new Float[] { 8.0f, 4.8f, -5.6f, -8.0f, -3.2f, 4.0f, 8.8f, 11.2f, 11.2f, 11.2f };
		}
		
		{
			name[9] = "soft";
			Presets[9] = new Float[] { 4.8f, 1.6f, -1.11022e-15f, -2.4f, -1.11022e-15f, 4.0f, 8.0f, 9.6f, 11.2f,
					12.0f };
		}
		{
			name[10] = "softrock";
			Presets[10] = new Float[] { 4.0f, 4.0f, 2.4f, -1.11022e-15f, -4.0f, -5.6f, -3.2f, -1.11022e-15f, 2.4f,
					8.8f };
		}
		{
			name[11] = "techno";
			Presets[11] = new Float[] { 8.0f, 5.6f, -1.11022e-15f, -5.6f, -4.8f, -1.11022e-15f, 8.0f, 9.6f, 9.6f,
					8.8f };
		}
	}
}