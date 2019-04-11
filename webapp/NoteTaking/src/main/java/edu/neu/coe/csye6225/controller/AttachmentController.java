package edu.neu.coe.csye6225.controller;
import edu.neu.coe.csye6225.entity.Attachment;
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
import java.util.List;

import static javax.servlet.http.HttpServletResponse.*;
import com.timgroup.statsd.StatsDClient;
import com.timgroup.statsd.NonBlockingStatsDClient;

@RestController
public class AttachmentController {

    private final NoteService noteService;
    private final AccountService accountService;
    private final AttachmentService attachmentService;
    private final FileService fileService;
    private static final StatsDClient statsd = new NonBlockingStatsDClient("my.prefix", "localhost", 8125);

    @Autowired
    public AttachmentController(NoteService noteService, AccountService accountService,
                                AttachmentService attachmentService,
                                FileService fileService) {
        this.noteService = noteService;
        this.accountService = accountService;
        this.attachmentService = attachmentService;
        this.fileService = fileService;
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
        statsd.incrementCounter("endpoint.attachments.http.get");

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
     * upload new file and create a new attachment
     *
     */
    @PostMapping("/note/{noteId}/attachments")
    public ResponseEntity<String> attachAttachmentToNote(@RequestPart(value = "file") MultipartFile file,
                                                         @PathVariable("noteId") String noteId,
                                                         HttpServletRequest httpServletRequest,
                                                         HttpServletResponse httpServletResponse) throws IOException {
        User user = UserVerification.addVerification(httpServletRequest.getHeader("Authorization"));
        statsd.incrementCounter("endpoint.note.http.post");

        if (user == null)
            return QuickResponse.userUnauthorized(httpServletResponse);

        if (file.isEmpty())
            return QuickResponse.noFile(httpServletResponse);

        if (accountService.logIn(user)) {
            if (!noteService.noteBelongToUser(noteId, user.getUsername())) {
                return QuickResponse.userNoAccess(httpServletResponse);
            } else {
                Attachment attachment = fileService.saveFile(noteId, file);
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
    public ResponseEntity<String> updateAttachment(@RequestPart("file") MultipartFile file,
                                                   @PathVariable("noteId") String noteId,
                                                   @PathVariable("attachmentId") String attachmentId,
                                                   HttpServletRequest httpServletRequest,
                                                   HttpServletResponse httpServletResponse) throws IOException {
        User user = UserVerification.addVerification(httpServletRequest.getHeader("Authorization"));
        statsd.incrementCounter("endpoint.attachmentsId.http.get");

        if (user == null) {
            return QuickResponse.userUnauthorized(httpServletResponse);
        }

        if (file.isEmpty())
            return QuickResponse.noFile(httpServletResponse);

        if (accountService.logIn(user)) {
            if(!noteService.noteBelongToUser(noteId, user.getUsername()))
                return QuickResponse.userNoAccess(httpServletResponse);
            if(!attachmentService.attBelongToUser(attachmentId, user.getUsername()))
                return QuickResponse.userNoAccess(httpServletResponse);

            if(file.getOriginalFilename().equals(attachmentService.getAttachmentById(attachmentId).getFileName())){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("message", "file exists");
                httpServletResponse.setHeader("status", String.valueOf(SC_BAD_REQUEST));
                return ResponseEntity.badRequest()
                        .body(jsonObject.toString());
            }

            fileService.deleteFile(attachmentId);
            Attachment att = fileService.saveFile(noteId, file);

            if(att == null) {
                return QuickResponse.quickBadRequestConstruct(httpServletResponse, "file saving error");
            }

            if(attachmentService.updateAttachment(attachmentId, att)) {
                return ResponseEntity.noContent()
                        .build();
            } else {
                return QuickResponse.quickBadRequestConstruct(httpServletResponse, "file not updated successfully");
            }

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
        statsd.incrementCounter("endpoint.attachmentId.http.delete");

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
            if(fileService.deleteFile(attachmentId)) {
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
