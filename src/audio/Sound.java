package audio;

public interface Sound {
    void play();
    void pause();
    void stop();
    void goTo(int frames);
    void fadeOut(double seconds);
    String getName();
    boolean isPlaying();
    
}
