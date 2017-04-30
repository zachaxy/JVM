package classpath;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Author: zhangxin
 * Time: 2017/4/30 0030.
 * Desc: ZipJarEntry表示ZIP或JAR文件形式的类路径,避免和Java中的ZipEntry冲突,起名为ZipJarEntry;
 */
public class ZipJarEntry extends Entry {
    String absDir;
    byte[] data;

    public ZipJarEntry(String path) {
        File dir = new File(path);
        if (dir.exists()) {
            absDir = dir.getAbsolutePath();
        }
    }

    /**
     * 从zip或者jar文件中提取class文件;
     *
     * @param className class文件的相对路径，路径之间用斜线 / 分隔，文件名有.class后缀
     */
    @Override
    void readClass(String className) {
        File file = new File(absDir, "test.zip");

        ZipInputStream zin = null;
        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            ZipFile zf = new ZipFile(file);
            zin = new ZipInputStream(new BufferedInputStream(new FileInputStream(file)));
            ZipEntry ze;
            boolean isExist = false;
            while ((ze = zin.getNextEntry()) != null) {
                if (ze.isDirectory()) {
                    //TODO:如果是文件夹的话,需要继续进行深入的遍历...
                } else {
//                    System.err.println("file - " + ze.getName() + " : " + ze.getSize() + " bytes");
                    if (ze.getName().equals(className)) {
                        isExist = true;
                        byte[] temp = new byte[1024];
                        int size = 0;

                        in = new BufferedInputStream(zf.getInputStream(ze));
                        out = new ByteArrayOutputStream(1024);

                        while ((size = in.read(temp)) != -1) {
                            out.write(temp, 0, size);
                        }
                        data = out.toByteArray();
                        break;
                    }
                }
            }
            if (!isExist) {
                throw new FileNotFoundException("jar包中不存在" + className);
            }
        } catch (ZipException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zin != null) {
                try {
                    zin.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (out!=null){
                try {
                    out.close();
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
