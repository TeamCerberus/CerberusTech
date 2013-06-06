package teamcerberus.cerberustech.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class PrefixOutputStream extends FilterOutputStream {
    private String prefix = "";
    private boolean wroteNewLine = true;

    public PrefixOutputStream(OutputStream output) {
        super(output);
    }
    
    public PrefixOutputStream setPrefix(String name){
        this.prefix = name;
        return this;
    }

    public PrefixOutputStream clearPrefix() {
        this.prefix = "";
        return this;
    }
    
    @Override
    public void write(int b) throws IOException {
        if(wroteNewLine) out.write(prefix.getBytes("UTF-8"));
        out.write(b);
        wroteNewLine = (b == '\n');
    }
}