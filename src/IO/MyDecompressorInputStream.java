package IO;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class MyDecompressorInputStream extends InputStream {
    InputStream in;

    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    @Override
    public int read(byte[] b) throws IOException {
        //byte[] tempRead = new byte[0];
        //in.read(tempRead);
        int tempRead = in.read();
        int byteCount = 0;
        while(tempRead!= -1){
            if(byteCount >= 0 && byteCount <= 23){
                b[byteCount] = (byte)tempRead;
                byteCount++;
            }
            else{
                for(int i = 7; i >= 0 && byteCount < b.length; i--){
                    b[byteCount++] = 0;
                    b[byteCount++] = 0;
                    b[byteCount++] = 0;
                    if((((byte)tempRead >> i) & 1) == 0)
                        b[byteCount++] = 0;
                    else
                        b[byteCount++] = 1;
                }
            }
            tempRead = in.read();
        }
        return in.read(b);
    }

    @Override
    public int read() throws IOException {
        //not implemented
        return 0;
    }
}
