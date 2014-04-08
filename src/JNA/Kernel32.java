package JNA;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.win32.StdCallLibrary;

public interface Kernel32 extends StdCallLibrary {

    Kernel32 INSTANCE = (Kernel32) Native.loadLibrary("Kernel32", Kernel32.class);

    Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, int dwProcessId);

    boolean CloseHandle(Pointer hObject);

}
