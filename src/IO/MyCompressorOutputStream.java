package IO;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class MyCompressorOutputStream extends OutputStream {
    OutputStream out;

    public MyCompressorOutputStream(OutputStream outputStream) {
        this.out = outputStream;
    }

    @Override
    public void write(int b) throws IOException {
        //Not supported
    }

    @Override
    public void write(byte[] b) throws IOException {

        int col = 0, row = 0, index = 0;
        if(b.length < 24 || b.length % 4 != 0)
            try {
                throw new Exception("Cannot form maze");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        int dem = b.length - 24;
        int size = 24 + dem/8;
        if(dem % 8 != 0)
            size++;
        ByteBuffer bb = ByteBuffer.allocate(size);
        byte tempByte = 0x00;
        byte[] tempBytes = {(byte) 0x80, (byte) 0x40, (byte) 0x20, (byte) 0x10, (byte) 0x08, (byte) 0x04, (byte) 0x02, (byte) 0x01};
        for(int i = 0; i < 24; i++)
            bb.put(b[i]);
        for (int i = 24; i < b.length; i+=4) {

            if (byteArrayToInt(b, i) == 1) {
                tempByte = (byte) (tempByte | tempBytes[index]);
            }
            if (index == 7) {
                bb.put(tempByte);
                tempByte = 0x00;
            }
            index = (index + 1) % 8;

        }
        if (index != 0)
            bb.put(tempByte);
        byte[] saveMe = bb.array();
        out.write(saveMe);
    }

    private int byteArrayToInt(byte[] bytes, int start){
        return ((0xFF & bytes[start]) << 24) | ((0xFF & bytes[start+1]) << 16) |
                ((0xFF & bytes[start+2]) << 8) | (0xFF & bytes[start+3]);
    }
    /*
    @Override
    public void write(byte[] b) throws IOException {
        //String input = byteArrayToString(b);
        String input = "101011011010101010";
        List<String> separatedBytes = SeperateStringToLemphel(input);
        Map<String,String> lemphLocation = new HashMap<>();
        Map<String,String> lemphCode = new HashMap<>();
        String stringIndex = getFirstAddress(separatedBytes);
        for (String s:
            separatedBytes ) {
            if(!lemphLocation.containsKey(s)) {
                stringIndex = getNextBinary(stringIndex);
                lemphLocation.put(s, stringIndex);
                String enter = "";
                if(s.length() >= 2)
                    enter += lemphLocation.get(s.substring(0,s.length()-2));
                else
                    enter += getFirstAddress(separatedBytes);
                enter += s.charAt(s.length()-1);
                lemphCode.put(s, enter);
            }
        }
        for (String s:
             lemphLocation.keySet()) {
            System.out.println("Key: "+ s + ", location: " + lemphLocation.get(s) + ", code: " + lemphCode.get(s));
        }
        */
        /*for (String s:
             sepreatedBytes) {
            String enter = "";
            enter += lemphLocation.get(s.substring(0,s.length()-2));
            enter += s.charAt(s.length()-1);
            lemphCode.put(s, enter);
        }*/
        /*
    }

    private String getFirstAddress(List<String> sepreatedBytes) {
        int size = sepreatedBytes.size();
        String last = sepreatedBytes.remove(size - 1);
        String result = "";
        double temp = Math.log(size - 1) / Math.log(2);
        if((Math.floor(temp) == temp && sepreatedBytes.contains(last))){
            size = (int)Math.floor(temp);
        }
        else
            size = (int)Math.floor(temp) + 1;
        sepreatedBytes.add(last);
        for(int i = 0; i < size; i++)
            result += "0";
        return result;
    }

    private String byteArrayToString(byte[] b) {
        String str = "";
        byte temp;
        byte[] tempBytes = {(byte)0x80,(byte)0x40, (byte)0x20, (byte)0x10, (byte)0x08, (byte)0x04, (byte)0x02, (byte)0x01};
        for(int i = 0; i < b.length; i++){
            for(int j = 0; j < 8; j++){
                temp = (byte)(tempBytes[j] & b[i]);
                if(temp == 0x00)
                    str += '0';
                else
                    str += '1';
            }
        }
        return str;
    }

    private List<String> SeperateStringToLemphel(String input) {
        List<String> stringList = new ArrayList<>();
        for (int i = 0; i < input.length(); i++) {
            boolean inserted = false;
            int index = i;
            while (!inserted) {
                boolean found = false;
                if (index + 1 < input.length() && stringList.contains(input.substring(i, index + 1)))
                    found = true;
                if (!found) {
                    stringList.add(input.substring(i, index + 1));
                    inserted = true;
                }
                index++;
            }
            i = index - 1;
        }
        return stringList;
    }

    private int byteArrayToInt(byte[] bytes, int start){
        return ((0xFF & bytes[start]) << 24) | ((0xFF & bytes[start+1]) << 16) |
                ((0xFF & bytes[start+2]) << 8) | (0xFF & bytes[start+3]);
    }


    private String getNextBinary(String curr){
        char[] chars = curr.toCharArray();
        for(int i = chars.length - 1; i >= 0; i--){
            if(chars[i] == '0'){
                chars[i] = '1';
                break;
            }
            else
                chars[i] = '0';
        }
        return new String(chars);
    }

    public static void main(String[] args){
        MyCompressorOutputStream hi = new MyCompressorOutputStream(null);
        byte[] ee = {1,0,1,0,1,1,0,1,1,0,1,0,1,0,1,0,1,0};
        try {
            hi.write(ee);
        }
        catch (Exception e){
            System.out.println("fuck");
        }
    }
    */
}
