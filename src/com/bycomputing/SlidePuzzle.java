package com.bycomputing;

import java.applet.Applet;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;

class MyButton extends Canvas {
	private Image image;
	
	public MyButton(int width, int height) {
		setSize(width, height);
	}

	public MyButton(Image image, int width, int height) {
		this.image = image;
		setSize(width, height);
	}
	
	public void paint(Graphics g) {		
		g.drawImage(image, 0, 0, this);		
	}
}

public class SlidePuzzle extends Applet {
    private BufferedImage source, resized;
	private int width, height;
	private Image image;
	private ArrayList<MyButton> buttons;
	private MyButton lastButton;
	
	public void init() {
		width = 800;
		height = 480;
		buttons = new ArrayList<MyButton>();
		
		setBackground( Color.GREEN );
        setLayout(new GridLayout(4, 3, 0, 0));
        setSize(width, height);
       
        try {
			source = loadImage();
			resized = resizeImage(source, width, height,
	                BufferedImage.TYPE_INT_ARGB);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 3; j++) {

                image = createImage(new FilteredImageSource(resized.getSource(),
                        new CropImageFilter(j * width / 3, i * height / 4,
                                (width / 3), height / 4)));
                MyButton button = new MyButton(image, (width / 3), height / 4);
                //button.putClientProperty("position", new Point(i, j));

                if (i == 3 && j == 2) {
                    lastButton = new MyButton((width / 3), height / 4);
                    //lastButton.setBorderPainted(false);
                    //lastButton.setContentAreaFilled(false);
                    //lastButton.setLastButton();
                    //lastButton.putClientProperty("position", new Point(i, j));
                } else {
                    buttons.add(button);
                }
            }
        }
        
        Collections.shuffle(buttons);
        buttons.add(lastButton);
        
        for (int i = 0; i < 12; i++) {

            MyButton btn = buttons.get(i);
            add(btn);            
        }  
    }
	
	private BufferedImage resizeImage(BufferedImage originalImage, int width,
            int height, int type) throws IOException {

        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();

        return resizedImage;
    }

    private BufferedImage loadImage() throws IOException {

        BufferedImage bimg = ImageIO.read(new File("image.jpg"));

        return bimg;
    }
}