package classpath;

import java.io.*;

/**
 * Author: zhangxin
 * Time: 2017/4/30 0030.
 * Desc: 表示目录形式的类路径
 */
public class DirEntry extends Entry {
    String absDir;
    byte[] data;  //读取到的class的字节数组;NOTE:用来干嘛的?

    public DirEntry(String path) {
        File dir = new File(path);
        if (dir.exists()) {
            absDir = dir.getAbsolutePath();
        }
    }


    @Override
    void readClass(String className) {
        File file = new File(absDir, className);
        byte[] temp = new byte[1024];
        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            out = new ByteArrayOutputStream(1024);
            int size = 0;
            while ((size = in.read(temp)) != -1) {
                out.write(temp, 0, size);
            }
            data = out.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    String printClassName() {
        return absDir;
    }
}
