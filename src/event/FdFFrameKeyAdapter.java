package event;

import gui.FdFFrame;
import transform.FdFMap;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FdFFrameKeyAdapter extends KeyAdapter {

    // objects
    private FdFFrame frame;

    public FdFFrameKeyAdapter(FdFFrame frame) {
        this.frame = frame;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE -> System.exit(0);
            case KeyEvent.VK_I -> frame.importMap();
        }
    }
}
