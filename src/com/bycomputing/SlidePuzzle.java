package com.bycomputing;

import java.applet.Applet;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;

class MyButton extends Canvas {
	private Image image;	
	private boolean isLastButton;
	private int width, height;
	
	public MyButton(int width, int height) {
		setSize(width, height);
		this.width = width;
		this.height = height;
		isLastButton = false;
	}

	public MyButton(Image image, int width, int height) {
		this(width, height);
		this.image = image;		
	}
	
	public void paint(Graphics g) {		
		g.drawImage(image, 0, 0, this);		
		g.drawRect(0, 0, width, height);
	}
	
    public void setLastButton() {
        
        isLastButton = true;
    }

    public boolean isLastButton() {

        return isLastButton;
    }
}

public class SlidePuzzle extends Applet implements MouseListener {
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
                    lastButton.setLastButton();
                    //lastButton.putClientProperty("position", new Point(i, j));
                } else {
                    buttons.add(button);
                }
            }
        }
        
        //Collections.shuffle(buttons);
        buttons.add(lastButton);
        
        for (int i = 0; i < 12; i++) {

            MyButton btn = buttons.get(i);
            add(btn);
            btn.addMouseListener(this);
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

	public void mouseClicked(MouseEvent e) {
		System.out.println("Mouse has been clicked");
		checkButton(e);
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	private void checkButton(MouseEvent e) {

        int lidx = 0;
        for (MyButton button : buttons) {
            if (button.isLastButton()) {
                lidx = buttons.indexOf(button);
                System.out.println("lidx is now " + lidx);
            }
        }

        MyButton button = (MyButton) e.getSource();
        int bidx = buttons.indexOf(button);
        System.out.println("bidx is now " + bidx);

        if ((bidx - 1 == lidx) || (bidx + 1 == lidx)
                || (bidx - 3 == lidx) || (bidx + 3 == lidx)) {
        	System.out.println("Swapping with last button");
            Collections.swap(buttons, bidx, lidx);
            updateButtons();
        }
    }
	
	private void updateButtons() {

        removeAll();

        for (MyButton btn : buttons) {

            add(btn);
        }
        
        validate();   
        
    }
}