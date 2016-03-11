package darklight.amino.common;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created by hongmiao.yu on 2016/3/11.
 */
public class ClassLoaders {

    private static Logger logger = LoggerFactory.getLogger(ClassLoaders.class);

    public static void addClassPath(ClassLoader classLoader, File libDir) {
        Class classLoaderClass = classLoader.getClass();
        Method addURL = null;
        while (!classLoaderClass.equals(Object.class)) {
            try {
                addURL = classLoaderClass.getDeclaredMethod("addURL", URL.class);
                addURL.setAccessible(true);
                break;
            } catch (NoSuchMethodException e) {
                // no method, try the parent
                classLoaderClass = classLoaderClass.getSuperclass();
            }
        }
        if (addURL == null) {
            logger.debug("failed to find addURL method on classLoader [" + classLoader + "] to add methods");
            return;
        }

        for (File file : libDir.listFiles()) {

            logger.trace("--- adding path [{}]", file.getAbsolutePath());

            try {
                // add the root
                addURL.invoke(classLoader, file.toURI().toURL());

                if (file.isDirectory()) {
                    // gather files to add
                    List<File> libFiles = Lists.newArrayList();
                    if (file.listFiles() != null) {
                        libFiles.addAll(Arrays.asList(file.listFiles()));
                    }
                    File libLocation = new File(file, "lib");
                    File listing[] = libLocation.listFiles();
                    if (listing != null) {
                        libFiles.addAll(Arrays.asList(listing));
                    }

                    // if there are jars in it, add it as well
                    for (File libFile : libFiles) {
                        if (isJarPackage(libFile.getName())) {
                            addURL.invoke(classLoader, libFile.toURI().toURL());
                        }
                    }
                }
            } catch (Throwable e) {
                logger.warn("failed to add path [" + file + "]", e);
            }
        }
    }

    public static boolean isJarPackage(String fileName) {
        return  (fileName.endsWith(".jar") || fileName.endsWith(".zip"));
    }

}
