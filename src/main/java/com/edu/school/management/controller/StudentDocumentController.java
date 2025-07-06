package com.edu.school.management.controller;

import com.edu.school.management.dto.StudentDocDTO;
import com.edu.school.management.entity.StudentDocEntity;
import com.edu.school.management.entity.StudentEntity;
import com.edu.school.management.repository.StudentDocRepository;
import com.edu.school.management.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/docs")
@RequiredArgsConstructor
public class StudentDocumentController {

    private final StudentRepository studentRepository;
    private final StudentDocRepository docRepository;

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    @PostMapping("/{studentId}/upload")
    public ResponseEntity<?> uploadDocument(
            @PathVariable Long studentId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("docType") String docType) {

        Optional<StudentEntity> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student not found");
        }

        try {
            StudentEntity student = studentOpt.get();
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String newFileName = System.currentTimeMillis() + "_" + originalFilename;
            Path studentPath = Paths.get(uploadDir, "student_" + studentId).toAbsolutePath().normalize();
            Files.createDirectories(studentPath);

            Path filePath = studentPath.resolve(newFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            StudentDocEntity doc = StudentDocEntity.builder()
                    .student(student)
                    .docType(docType)
                    .fileName(originalFilename)
                    .filePath(filePath.toString())
                    .contentType(file.getContentType())
                    .build();

            docRepository.save(doc);
            return ResponseEntity.ok("Document uploaded");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        }
    }
    @PutMapping("/updateDocument/{stuDocId}")
    public ResponseEntity<?> updateSpecificDocument(
            @PathVariable Long stuDocId,
            @RequestParam("file") MultipartFile file) {

        Optional<StudentDocEntity> docOpt = docRepository.findById(stuDocId);
        if (docOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document not found");
        }

        try {
            StudentDocEntity existingDoc = docOpt.get();
            Long studentId = existingDoc.getStudent().getStudentPin();

            // Delete the old file
            Path oldFilePath = Paths.get(existingDoc.getFilePath());
            if (Files.exists(oldFilePath)) {
                Files.delete(oldFilePath);
            }

            // Save new file
            String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
            String newFileName = System.currentTimeMillis() + "_" + originalFilename;
            Path studentPath = Paths.get(uploadDir, "student_" + studentId).toAbsolutePath().normalize();
            Files.createDirectories(studentPath);

            Path newFilePath = studentPath.resolve(newFileName);
            Files.copy(file.getInputStream(), newFilePath, StandardCopyOption.REPLACE_EXISTING);

            // Update the existing document
            existingDoc.setFileName(originalFilename);
            existingDoc.setFilePath(newFilePath.toString());
            existingDoc.setContentType(file.getContentType());

            docRepository.save(existingDoc);

            return ResponseEntity.ok("Document updated successfully");

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update document");
        }
    }


//    @GetMapping("/{studentId}/studentDocList")
//    public ResponseEntity<List<StudentDocEntity>> studentDocList(@PathVariable Long studentId) {
//        List<StudentDocEntity> docs = docRepository.findByStudent_StudentPin(studentId);
//        return ResponseEntity.ok(docs);
//    }

    @GetMapping("/{studentId}/studentDocList")
    public ResponseEntity<List<StudentDocDTO>> studentDocList(@PathVariable Long studentId) {
        List<StudentDocEntity> docs = docRepository.findByStudent_StudentPin(studentId);

        List<StudentDocDTO> dtoList = docs.stream()
            .map(doc -> new StudentDocDTO(
                doc.getStuDocId(),
                doc.getDocType(),
                doc.getFileName(),
                doc.getFilePath(),
                doc.getContentType()
            ))
            .collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }
    
    @GetMapping("/{studentId}/download/{docId}")
    public ResponseEntity<Resource> download(@PathVariable Long studentId, @PathVariable Long docId) {
        Optional<StudentDocEntity> docOpt = docRepository.findByStuDocIdAndStudent_StudentPin(docId, studentId);
        if (docOpt.isEmpty()) return ResponseEntity.notFound().build();

        StudentDocEntity doc = docOpt.get();
        Path filePath = Paths.get(doc.getFilePath());

        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) return ResponseEntity.notFound().build();

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(doc.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + doc.getFileName() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{studentId}/preview/{docId}")
    public ResponseEntity<Resource> preview(@PathVariable Long studentId, @PathVariable Long docId) {
        Optional<StudentDocEntity> docOpt = docRepository.findByStuDocIdAndStudent_StudentPin(docId, studentId);
        if (docOpt.isEmpty()) return ResponseEntity.notFound().build();

        StudentDocEntity doc = docOpt.get();
        Path filePath = Paths.get(doc.getFilePath());

        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) return ResponseEntity.notFound().build();

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(doc.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "inline; filename=\"" + doc.getFileName() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{studentId}/delete/{docId}")
    public ResponseEntity<String> delete(@PathVariable Long studentId, @PathVariable Long docId) {
        Optional<StudentDocEntity> docOpt = docRepository.findByStuDocIdAndStudent_StudentPin(docId, studentId);
        if (docOpt.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Document not found");

        StudentDocEntity doc = docOpt.get();
        try {
            Files.deleteIfExists(Paths.get(doc.getFilePath()));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete file");
        }

        docRepository.delete(doc);
        return ResponseEntity.ok("Document deleted");
    }
}
