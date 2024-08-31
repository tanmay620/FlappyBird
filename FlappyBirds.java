import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class FlappyBirds extends JPanel implements ActionListener, KeyListener{
    
    Image backgroundImg;
    Image backgroundImgDark;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;
    int width=360;
    int height=640;

    int birdx=width/8;
    int birdy=height/2;
    int birdWidth=34;
    int birdHeight=24;

    int pipeX=width;
    int pipeY=0;
    int pipeWidth=64;
    int pipeHeight=512;

    class pipe{
        int x=pipeX;
        int y=0;
        int width=pipeWidth;
        int height=pipeHeight;
        Image img;
        boolean passed=false;
        pipe(Image img){
            this.img=img;
        }
    }

    class Bird{
        int x=birdx;
        int y=birdy;
        int width=birdWidth;
        int height=birdHeight;
        Image img;

        Bird(Image img){
            this.img=img;
        }
    }

    Bird bird;
    Timer gameloop;
    Timer placePipeTimer;
    boolean gameover=false;

    int velY=0;
    int velX=-4;
    int gravity=1;
    double score=0.0;

    ArrayList<pipe> pipes;
    Random random =new Random();

    FlappyBirds(){
        setPreferredSize(new Dimension(width,height));
        //setFocusable(true);
        addKeyListener(this);

        backgroundImg= new ImageIcon(getClass().getResource("./flappybirdbg.png")).getImage();
        birdImg= new ImageIcon(getClass().getResource("./flappybird.png")).getImage();
        topPipeImg= new ImageIcon(getClass().getResource("./toppipe.png")).getImage();
        bottomPipeImg= new ImageIcon(getClass().getResource("./bottompipe.png")).getImage();

       bird = new Bird(birdImg);
       pipes=new ArrayList<pipe>();
       placePipeTimer=new Timer(1500,new ActionListener() {
        public void actionPerformed(ActionEvent ae){
            placepipe();
        }
       });
       placePipeTimer.start();
       gameloop=new Timer(1000/60,this);
       gameloop.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        if(score<=1){
            g.drawImage( backgroundImg, 0, 0,width,height,null );
            g.setColor(Color.WHITE);
        }
        else{
            g.drawImage( backgroundImgDark, 0, 0,width,height,null );
            g.setColor(Color.WHITE);
        }
        g.drawImage(bird.img, bird.x, bird.y, bird.width, bird.height, null);
        for(int i=0;i<pipes.size();i++){
            pipe pipee=pipes.get(i);
            g.drawImage(pipee.img, pipee.x, pipee.y, pipee.width,pipee.height,null);
        }
        
        g.setFont(new Font("Arial",Font.PLAIN,32));
        if(gameover==true){
            g.setFont(new Font("Arial",Font.BOLD,48));
            g.drawString("GameOver: "+String.valueOf((int)score), width/12,height/2);
        }
        else{
            g.drawString("Score "+String.valueOf((int)score), width-100, 35);
        }
    }

    public void move(){
        velY+=gravity;
        bird.y +=velY;
        if(bird.y>height ||bird.y<-bird.height/2){
            //System.out.println("due to y 2" + bird.y );
             gameover=true;
         }
        for(int i=0;i<pipes.size();i++){
            pipe pipe=pipes.get(i);
            pipe.x+=velX;
           // System.out.println(bird.x  + " bird x " + bird.y  + " bird y " +bird.height+" bird height "+bird.width+" bird widht\n"+ pipe.x +" pipe.x "+pipe.y + " pipe y " +pipe.height+" pipe height "+pipe.width+" pipe width ");
            if(collision(bird, pipe)){
               //System.out.println("due to collision " + bird.x  + " bird x " + bird.y  + " bird y " +bird.height+" bird height "+bird.width+" bird widht\n"+ pipe.x +" pipe.x "+pipe.y + " pipe y "+pipe.height+" pipe height "+pipe.width+" pipe width ");
               gameover=true;
            }

            if(!pipe.passed&&bird.x>pipe.x+pipe.width){
                pipe.passed=true;
                score +=0.5;
            }
        }
    }

    public boolean collision(Bird b,pipe p){
        return b.x<p.x+p.width && b.x+ b.width>p.x && b.y<p.y+p.height  && b.y +b.height>p.y ;
    }
    public void placepipe(){
        int randomPipeY=(int)(pipeY - pipeHeight/4 -Math.random()*pipeHeight/2);
        int openingspace=height/4;
        pipe toppipe=new pipe(topPipeImg);
        toppipe.y=randomPipeY;
        pipes.add(toppipe);

        pipe bottompipe=new pipe(bottomPipeImg);
        bottompipe.y=toppipe.y+pipeHeight+openingspace;
        pipes.add(bottompipe);
    }

    public void actionPerformed(ActionEvent ae){
        move();
        
        repaint();
        if(gameover==true){
            placePipeTimer.stop();
            gameloop.stop();
        }
    }
   
    public void keyPressed(KeyEvent e){
 if(e.getKeyCode()==KeyEvent.VK_SPACE)
        velY=-9;
        if(gameover==true){
            bird.y=birdy;
            velY=0;
            pipes.clear();
            score=0;
            gameover=false;
            gameloop.start();
            placePipeTimer.start();
        }
    }
    public void keyReleased(KeyEvent e) {
        // Handle key released event
    }  
    public void keyTyped(KeyEvent e) {
        // Handle key typed event
    }
}
