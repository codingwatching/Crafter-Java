package org.crafter.engine.gui.components;

import org.crafter.engine.gui.GUI;
import org.crafter.engine.gui.alignment.Alignment;

/**
 * Holds text data in memory.
 */
public class Label extends GUIElement {
    private String data;

    private float fontSize;

    public Label(String name, String data, float fontSize, Alignment alignment) {
        super(name, false,false, false, false, true, alignment);
        this.data = data;
        this.fontSize = fontSize;
    }

    @Override
    public void onStep(GUI gui) {
        System.out.println("I'm on step woo");
    }

    @Override
    public void onHover(GUI gui) {

    }

    @Override
    public void onClick(GUI gui) {

    }

    @Override
    public void onKeyInput(GUI gui, int keyboardKey) {

    }

    @Override
    public void render() {

    }

    @Override
    public boolean collisionDetect() {
        // Does nothing
        return false;
    }
}
