package JNA;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LPMODULEINFO extends Structure {

    public int lpBaseOfDll; //FIXME Pointer
    public int SizeOfImage;
    public Pointer EntryPoint;

    @Override
    protected List getFieldOrder() {
        List fields = new ArrayList();
        fields.addAll(Arrays.asList(new String[]{"lpBaseOfDll", "SizeOfImage", "EntryPoint"}));
        return fields;
    }

}
