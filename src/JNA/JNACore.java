package JNA;

import com.sun.jna.Native;
import com.sun.jna.Pointer;

public class JNACore {

    /*
    Access modes
    */
    public static final int PROCESS_QUERY_INFORMATION = 0x0400;
    public static final int PROCESS_VM_READ = 0x0010;
    public static final int PROCESS_VM_WRITE = 0x0020;
    public static final int PROCESS_VM_OPERATION = 0x0008;

    /*
    Buffers for reading
    */
    private static int[] processList = new int[1024];
    private static int[] dummyList = new int[1024];

    /*
    Fills the processList array with process id's.
    */
    public static void getProcesses() {

        Psapi.INSTANCE.EnumProcesses(processList, 1024, dummyList);

        int pid;
        int i = 0;
        while (i<processList.length) {
            pid = processList[i];
            if (pid != 0 ) {
                Pointer ph = Kernel32.INSTANCE.OpenProcess(PROCESS_VM_READ, false, pid);

                if (ph != null) {
                    byte[] filename = new byte[512];
                    Psapi.INSTANCE.GetModuleBaseNameW(ph, new Pointer(0), filename, 512);

                    System.out.println(pid);
                    System.out.println(Native.toString(filename));
                    Kernel32.INSTANCE.CloseHandle(ph);
                }
            }
            i++;
        }
    }
}
