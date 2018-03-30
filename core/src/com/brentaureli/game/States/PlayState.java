package com.brentaureli.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.brentaureli.game.FlappyDemo;
import com.brentaureli.game.sprites.Bird;
import com.brentaureli.game.sprites.Tube;

import static com.brentaureli.game.sprites.Tube.TUBE_WIDTH;

/**
 * Created by Mirko on 08.11.2017.
 */

public class PlayState extends State {

    private static final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;

    private Array<Tube> tubes;

    private Bird bird;
    private Texture background;
    private Sprite t;


    public PlayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50,100);
        cam.setToOrtho(false, FlappyDemo.WIDTH/2, FlappyDemo.HEIGHT/2);
        background = new Texture("background.png");
        t = new Sprite(new Texture(("topTube.png")));



        tubes = new Array<Tube>();

        for(int i = 1; i <= TUBE_COUNT; i++){
            tubes.add(new Tube(i * (TUBE_SPACING + TUBE_WIDTH)));
        }
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()) {
            bird.jump();

            System.out.println( Gdx.input.getX());
        }


      //  Gdx.input.getAccelerometerX();
      //  Gdx.input.getX();

        Gdx.input.setInputProcessor(new InputAdapter(){

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {

                if(t.getBoundingRectangle().contains(screenX, screenY))
                    System.out.println("Image Clicked");

                return true;
            }

        });
    }



    @Override
    public void update(float dt) {
        handleInput();
        bird.update(dt);
        cam.position.x = bird.getPosition().x + 80;
        for(Tube tube : tubes){
            if(cam.position.x - (cam.viewportWidth/2) > tube.getPosTopTube().x+tube.getTopTube().getWidth()) {
                tube.reposition(tube.getPosTopTube().x + ((Tube.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }

            if(tube.collides(bird.getBounds())){
                gsm.set(new PlayState(gsm));
            }
        }
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, cam.position.x - (cam.viewportWidth/2), 0);
        sb.draw(bird.getBird(),bird.getPosition().x,bird.getPosition().y);
       for(Tube tube : tubes) {
           sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
           sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
       }
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
