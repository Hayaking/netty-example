package n5.a1.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author haya
 */
public class Utils {
    /**
     * 获取指定包下的所有类的集合
     * @param pack
     * @return
     */
    public static Set<Class<?>> getClasses(String pack) throws IOException {

        Set<Class<?>> classes = new LinkedHashSet<>();
        String packageDirName = pack.replace( '.', '/' );
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        dirs = Thread.currentThread()
                .getContextClassLoader()
                .getResources( packageDirName );
        while (dirs.hasMoreElements()) {
            URL url = dirs.nextElement();
            // 得到协议的名称
            String protocol = url.getProtocol();
            switch (protocol) {
                case "file":
                    scanFile( classes, true, pack, url );
                    break;
                case "jar":
                    scanJar( classes, true, pack, packageDirName, url );
                    break;
                default:
                    break;
            }
        }
        return classes;
    }

    private static void scanJar(Set<Class<?>> classes, boolean recursive, String packageName, String packageDirName, URL url) {
        JarFile jar;
        try {
            // 获取jar
            jar = ((JarURLConnection) url.openConnection())
                    .getJarFile();
            // 从此jar包 得到一个枚举类
            Enumeration<JarEntry> entries = jar.entries();
            // 同样的进行循环迭代
            while (entries.hasMoreElements()) {
                // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                // 如果是以/开头的
                if (name.charAt( 0 ) == '/') {
                    // 获取后面的字符串
                    name = name.substring( 1 );
                }
                // 如果前半部分和定义的包名相同
                if (name.startsWith( packageDirName )) {
                    int idx = name.lastIndexOf( '/' );
                    // 如果以"/"结尾 是一个包
                    if (idx != -1) {
                        // 获取包名 把"/"替换成"."
                        packageName = name.substring( 0, idx )
                                .replace( '/', '.' );
                    }
                    // 如果可以迭代下去 并且是一个包
                    if ((idx != -1) || recursive) {
                        // 如果是一个.class文件 而且不是目录
                        if (name.endsWith( ".class" )
                                && !entry.isDirectory()) {
                            // 去掉后面的".class" 获取真正的类名
                            String className = name.substring(
                                    packageName.length() + 1, name
                                            .length() - 6 );
                            try {
                                // 添加到classes
                                classes.add( Class
                                        .forName( packageName + '.'
                                                + className ) );
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void scanFile(Set<Class<?>> classes, boolean recursive, String packageName, URL url) throws UnsupportedEncodingException {
        // 获取包的物理路径
        String filePath = URLDecoder.decode( url.getFile(), "UTF-8" );
        // 以文件的方式扫描整个包下的文件 并添加到集合中
        findAndAddClassesInPackageByFile( packageName, filePath, recursive, classes );
    }

    private static void findAndAddClassesInPackageByFile(String packageName,
                                                         String packagePath,
                                                         final boolean recursive,
                                                         Set<Class<?>> classes) {
        // 获取此包的目录 建立一个File
        File dir = new File( packagePath );
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
        File[] dirFiles = dir.listFiles( file -> (recursive && file.isDirectory())
                || (file.getName().endsWith( ".class" )) );
        // 循环所有文件
        if (dirFiles == null) {
            return;
        }
        for (File file : dirFiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile( packageName + "."
                                + file.getName(), file.getAbsolutePath(), recursive,
                        classes );
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring( 0, file.getName().length() - 6 );
                try {
                    classes.add( Thread.currentThread().getContextClassLoader()
                            .loadClass( packageName + '.' + className ) );
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
