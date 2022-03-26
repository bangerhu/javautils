package com.hujiya.gz;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Iterator;

@Slf4j
public class Test {

    public static void main(String[] args) {
        File dir = new File("/Users/vivian/fsdownload");
        if (!dir.exists()) {
            log.error("dir is not exists");
        }
        Collection<File> files = FileUtils.listFiles(dir, new String[]{"log.tar.gz"}, false);

        if (files.isEmpty()) {
            log.warn("files not exists");
        }
        Iterator<File> iterable = files.iterator();
        while (iterable.hasNext()) {
            File file = iterable.next();
            if (file.isFile()) {
                try {
                    log.info("read gz file path : {}", file.getName());
                    TarArchiveInputStream tarInput = new TarArchiveInputStream(new GzipCompressorInputStream(
                            new FileInputStream(file)));
                    TarArchiveEntry currentEntry = tarInput.getNextTarEntry();
                    log.info("1currentEntry is {}", currentEntry);
                    BufferedReader br = null;
                    while (currentEntry != null && currentEntry.isFile()) {
                        br = new BufferedReader(new InputStreamReader(tarInput));
                        log.info("processing file : {} ", currentEntry.getName());
                        String line;
                        while ((line = br.readLine()) != null) {
                            if (StringUtils.isNotEmpty(line)) {
                                JSONObject jsonObject = JSONObject.parseObject(line);
//                                log.info(jsonObject.toString());
                            }
                        }
                        currentEntry = tarInput.getNextTarEntry();
                        log.info("2currentEntry is {}", currentEntry);
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }
}
