package com.efun.core.utils;

import java.io.File;

/**
 * FileUtils
 *
 * @author Galen
 * @since 2016/05/30.
 */
public class FileUtils {

    /**
     * add separator for dir path
     *
     * @param dirName
     * @return
     */
    public static String addSeparatorIfNec(String dirName) {
        if (StringUtils.isNotBlank(dirName)
                && !dirName.endsWith(File.separator)) {
            dirName += File.separator;
        }
        return dirName;
    }
}
