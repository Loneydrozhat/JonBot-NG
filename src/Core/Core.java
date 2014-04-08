package Core;

import CaveBot.CaveBot;
import GUI.GUI;
import Healer.Healer;
import JNA.JNACore;

public class Core{
    
    private static Healer healer;
    private static CaveBot caveBot;
    
    private static long curTime;
    private static long oldTime;
    
    private static boolean didOnce = false;
    
    
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
        if(didOnce == false){
            //System.out.println(MemoryHandler.returnPID());
            JNACore.getProcesses();
            didOnce = true;
        }
        if(curTime - oldTime > 1000){
            oldTime = curTime;
            
            GUI.debug.append(curTime+"\n");
            
            healer.heal();
            caveBot.bot();
        }
    }
    
}
