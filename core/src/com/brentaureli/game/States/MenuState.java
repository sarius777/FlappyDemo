package com.brentaureli.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.brentaureli.game.FlappyDemo;

/**
 * Created by Mirko on 08.11.2017.
 */

public class MenuState extends State {
    private Texture background;
    private Texture playbutton;


    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, FlappyDemo.WIDTH/2, FlappyDemo.HEIGHT/2);
        background = new Texture("background.png");
        playbutton = new Texture("playbutton2.png");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
            dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0,0);
        sb.draw(playbutton, cam.position.x - playbutton.getWidth()/2, cam.position.y);
        sb.end();
    }

    public void dispose(){
        background.dispose();
        playbutton.dispose();
    }
}
