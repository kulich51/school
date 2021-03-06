package ru.hogwarts.school.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarServiceImpl implements AvatarService {

    @Value("${path.to.avatars.folder}")
    private String avatarsDir;

    private final AvatarRepository avatars;
    private final StudentRepository students;

    private Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public AvatarServiceImpl(AvatarRepository avatars, StudentRepository students) {
        this.avatars = avatars;
        this.students = students;
    }

    @Override
    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {

        logger.info("uploadAvatar called");
        Student student = students.getById(studentId);
        Path filePath = Path.of(avatarsDir,
                student.getId().toString(),
                student.getId() + "." + getExtension(avatarFile.getOriginalFilename()));
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);

        try (InputStream is = avatarFile.getInputStream();
             OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)) {

            bis.transferTo(bos);
        }

        Avatar avatar = findAvatarOrCreateNew(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatars.save(avatar);
    }

    private Avatar findAvatarOrCreateNew(Long studentId) {
        logger.info("findAvatarOrCreateNew called");
        return avatars.getAvatarByStudentId(studentId).orElse(new Avatar());
    }

    @Override
    public Avatar findAvatar(Long studentId) {
        logger.info("findAvatar called");
        return avatars.getAvatarByStudentId(studentId).orElseThrow();
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    @Override
    public List<Avatar> getAll(int page, int size) {
        logger.info("getAll called");
        Pageable currentPage = PageRequest.of(page - 1, size);
        return avatars.findAll(currentPage).getContent();
    }
}
