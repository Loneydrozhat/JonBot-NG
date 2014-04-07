package JNA.Interfaces;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;

public interface Kernel32 extends Library {
    
    //create instance of kernel32
    Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("kernel32",Kernel32.class, W32APIOptions.UNICODE_OPTIONS);
    
}
