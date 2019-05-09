package gameEvent;

import level.Chunk;
import level.Sprite;

public interface AI {

    void doAI();

    void updateChunk(Chunk[][] c);

    boolean hasChildren();

    Sprite getChildren();
}
