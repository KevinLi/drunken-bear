public class DrunkenBear{
    private Render _render;
    
    public DrunkenBear(){
	_render = new Render();
    }
    public void run(){
	_render.setup();
	while (true){
	    if (!_render.paused){
		
	    }
	}
    }
    public static void main(String[] args){
	DrunkenBear game = new DrunkenBear();
	game.run();
    }
}