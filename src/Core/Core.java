package Core;

import CaveBot.CaveBot;
import Healer.Healer;

public class Core {
    
    private static Healer healer;
    private static CaveBot caveBot;
    
    private static long curTime;
    private static long oldTime;
    
    
    /*
    Constructor where we initialize timing variables
    */
    public Core(){
        healer = new Healer();
        caveBot = new CaveBot();
        curTime = System.currentTimeMillis();
        oldTime = System.currentTimeMillis();
    }
    
    /*
    Here is the main loop of the whole application. The heart.
    */
    public static void cycle() {
        curTime = System.currentTimeMillis();
        if(curTime - oldTime > 1000){
            oldTime = curTime;
            
            healer.heal();
            caveBot.bot();
        }
    }
    
}
