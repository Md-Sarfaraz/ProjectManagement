package com.sarfaraz.management.controller;

/*
import com.sarfaraz.management.model.FileInfo;
import com.sarfaraz.management.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping(value = {"/files/"})
public class FilesCtrl {

    private static final Logger logger = LoggerFactory.getLogger(FilesCtrl.class);


    private FileStorageService fileStorageService;

    @Autowired
    public FilesCtrl(FileStorageService fss) {
        fileStorageService = fss;
    }

    @PostMapping("/upload")
    public FileInfo uploadFile(@RequestParam("file") MultipartFile file) {
        FileInfo fileInfo = fileStorageService.storeFile(file);
        return fileInfo;
    }

    @PostMapping("/uploadmany")
    public List<FileInfo> uploadManyFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping(value = {"/download/{fileName:.+}"})
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        if (contentType == null) {// Fallback to the default content type if type could not be determined
            contentType = MimeTypeUtils.APPLICATION_OCTET_STREAM_VALUE;
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}

*/