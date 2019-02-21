package edu.neu.coe.csye6225.controller;
import edu.neu.coe.csye6225.entity.Attachment;
import edu.neu.coe.csye6225.entity.Note;
import edu.neu.coe.csye6225.entity.User;
import edu.neu.coe.csye6225.service.*;
import edu.neu.coe.csye6225.util.QuickResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.servlet.http.HttpServletResponse.*;

@RestController
public class AttachmentController {

    private final NoteService noteService;
    private final AccountService accountService;
    private final AttachmentService attachmentService;
    private final FileSaveService fileSaveService;

    @Autowired
    public AttachmentController(NoteService noteService, AccountService accountService,
                                AttachmentService attachmentService,
                                FileSaveService fileSaveService) {
        this.noteService = noteService;
        this.accountService = accountService;
        this.attachmentService = attachmentService;
        this.fileSaveService = fileSaveService;
    }

//    @PostMapping
//    public Map<String, String> uploadFile(@RequestPart(value = "file") MultipartFile file) {
//        //if attachmentId="", means create new attachment. If attachmentId is passed, means update existed attachment
//        this.amazonS3ClientService.createAttachmentToS3Bucket(new Note(), "", file, true);
//
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "file [" + file.getOriginalFilename() + "] uploading request submitted successfully.");
//
//        return response;
//    }
//
//
//    @DeleteMapping
//    public Map<String, String> deleteFile(@RequestParam("file_name") String fileName) {
//        this.amazonS3ClientService.deleteAttachmentFromS3Bucket(new Note(), fileName);
//
//        Map<String, String> response = new HashMap<>();
//        response.put("message", "file [" + fileName + "] removing request submitted successfully.");
//
//        return response;
//    }

    /**
     * get all the attachments that belongs to the user
     *
     * @param noteId              note id
     * @throws IOException by sendError()
     */
    @GetMapping("/note/{noteId}/attachments")
    public ResponseEntity<String> getListOfAllAttachments(@PathVariable("noteId") String noteId,
                                                          HttpServletRequest httpServletRequest,
                                                          HttpServletResponse httpServletResponse) throws IOException {

        User user = UserVerification.addVerification(httpServletRequest.getHeader("Authorization"));

        if (user == null) {
            return QuickResponse.userUnauthorized(httpServletResponse);
        }
        if (accountService.logIn(user)) {
            if (!noteService.noteBelongToUser(noteId, user.getUsername())) {
                return QuickResponse.userNoAccess(httpServletResponse);
            } else {
                List<Attachment> attList = attachmentService.getAllAttachments(noteId);
                JSONArray jsonArray = new JSONArray();
                for (Attachment att : attList) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("attachmentId", att.getAttachmentId());
                    jsonObject.put("fileName", att.getFileName());
                    jsonObject.put("url", att.getUrl());
                    jsonArray.add(jsonObject);
                }

                httpServletResponse.setHeader("status", String.valueOf(SC_OK));
                JSONObject result = new JSONObject();
                result.put("AttachmentList", jsonArray);

                return ResponseEntity.ok()
                        .body(result.toString());
            }

        } else {
            return QuickResponse.userUnauthorized(httpServletResponse);
        }
    }


    /**
     * get all the attachments related to the note
     *
     */
    @PostMapping("/note/{noteId}/attachments")
    public ResponseEntity<String> attachAttachmentToNote(@RequestPart(value = "file") MultipartFile file,
                                                         @PathVariable("noteId") String noteId,
                                                         HttpServletRequest httpServletRequest,
                                                         HttpServletResponse httpServletResponse) throws IOException {
        User user = UserVerification.addVerification(httpServletRequest.getHeader("Authorization"));

        if (user == null) {
            return QuickResponse.userUnauthorized(httpServletResponse);
        }
        if (file == null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "No file");
            httpServletResponse.setHeader("status", String.valueOf(SC_BAD_REQUEST));
            return ResponseEntity.badRequest()
                    .body(jsonObject.toString());
        }

        if (accountService.logIn(user)) {
            if (!noteService.noteBelongToUser(noteId, user.getUsername())) {
                return QuickResponse.userNoAccess(httpServletResponse);
            } else {
                Attachment attachment = fileSaveService.saveFile(noteId, file);
                if (attachment == null) {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("message", "upload file error");
                    httpServletResponse.setHeader("status", String.valueOf(SC_BAD_REQUEST));
                    return ResponseEntity.badRequest()
                            .body(jsonObject.toString());
                } else {
                    attachmentService.addAttachment(noteId, attachment);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", attachment.getAttachmentId());
                    jsonObject.put("url", attachment.getUrl());
                    httpServletResponse.setHeader("status", String.valueOf(SC_OK));
                    return ResponseEntity.ok()
                            .body(jsonObject.toString());
                }
            }
        } else {
            return QuickResponse.userUnauthorized(httpServletResponse);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/note/{noteId}/attachments/{attachmentId}")
    public ResponseEntity<String> updateAttachment(@PathVariable("noteId") String noteId,
                                                   @PathVariable("attachmentId") String attachmentId,
                                                   HttpServletRequest httpServletRequest,
                                                   HttpServletResponse httpServletResponse) throws IOException {
        User user = UserVerification.addVerification(httpServletRequest.getHeader("Authorization"));

        if (user == null) {
            return QuickResponse.userUnauthorized(httpServletResponse);
        }
        if (accountService.logIn(user)) { // TODO
            return null;
        } else {
            return QuickResponse.userUnauthorized(httpServletResponse);
        }
    }

    /**
     * delete one attachment from the note
     *
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/note/{noteId}/attachments/{attachmentId}")
    public ResponseEntity<String> deleteAttachment(@PathVariable("noteId") String noteId,
                                                   @PathVariable("attachmentId") String attachmentId,
                                                   HttpServletRequest httpServletRequest,
                                                   HttpServletResponse httpServletResponse) throws IOException {
        User user = UserVerification.addVerification(httpServletRequest.getHeader("Authorization"));

        if (user == null) {
            return QuickResponse.userUnauthorized(httpServletResponse);
        }

        if (accountService.logIn(user)) {
            if(!noteService.noteBelongToUser(noteId, user.getUsername())) {
                return QuickResponse.userNoAccess(httpServletResponse);
            }
            if(!attachmentService.attBelongToUser(attachmentId, user.getUsername())){
                return QuickResponse.userNoAccess(httpServletResponse);
            }
            if(fileSaveService.deleteFile(attachmentId)) {
                if (attachmentService.deleteAttachment(attachmentId) != null){
                    // success, delete file and attachment data in database
                    return ResponseEntity.noContent().build();
                }
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message", "delete failed!");
            httpServletResponse.setHeader("status", String.valueOf(SC_UNAUTHORIZED));
            return ResponseEntity.badRequest()
                    .body(jsonObject.toString());
        } else {
            return QuickResponse.userUnauthorized(httpServletResponse);
        }
    }
}
