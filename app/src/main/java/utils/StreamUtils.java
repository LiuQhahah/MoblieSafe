package utils;

/**
 * Created by asus on 2016/2/27.
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 读取流的工具
 */
public class StreamUtils {


    /**
     * 将输入流读取成string后返回
     * @param in
     * @return
     */
    public static String readFromStream(InputStream in) throws IOException{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len = 0;
        byte[] buffer = new byte[1024];

        while ((len=in.read(buffer))!=-1){
            out.write(buffer,0,len);
        }

        String result = out.toString();
        in.close();
        out.close();

        return result;
    }
}
