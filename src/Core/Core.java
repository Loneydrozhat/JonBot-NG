package Core;

public class Core {
    
    private static long curTime;
    private static long oldTime;
    
    
    /*
    Constructor where we initialize timing variables
    */
    public Core(){
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
            System.out.println(curTime);
        }
    }
    
}
