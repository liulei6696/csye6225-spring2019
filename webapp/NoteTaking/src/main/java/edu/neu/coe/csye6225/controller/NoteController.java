package edu.neu.coe.csye6225.controller;

import edu.neu.coe.csye6225.entity.Attachment;
import edu.neu.coe.csye6225.entity.Note;
import edu.neu.coe.csye6225.entity.User;
import edu.neu.coe.csye6225.service.*;
import edu.neu.coe.csye6225.util.QuickResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.*;
import com.timgroup.statsd.StatsDClient;
import com.timgroup.statsd.NonBlockingStatsDClient;


@RestController
public class NoteController {

    private final AccountService accountService;
    private final NoteService noteService;
    private final AttachmentService attachmentService;
    private final FileService fileService;
    private static final StatsDClient statsd = new NonBlockingStatsDClient("my.prefix", "localhost", 8125);

    @Autowired
    public NoteController(AccountService accountService, NoteService noteService, AttachmentService attachmentService, FileService fileService) {
        this.accountService = accountService;
        this.noteService = noteService;
        this.attachmentService = attachmentService;
        this.fileService = fileService;
    }

    /**
     * query all notes of this user
     * only return part of information of each note
     * which is noteId, title and lastModifiedDate
     *
     */
    @GetMapping("/note")
    public ResponseEntity<String> getAllNotes(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        statsd.incrementCounter("endpoint.note.http.get");
        User user = UserVerification.addVerification(httpServletRequest.getHeader("Authorization"));

        if (user == null) {
            return QuickResponse.userUnauthorized(httpServletResponse);
        }
        if (accountService.logIn(user)) {
            JSONObject resultJson = new JSONObject();
            List<Note> noteList = noteService.getAllNotes(user);
            JSONArray jsonArr = new JSONArray();
            for(Note n : noteList){
                JSONObject jo = noteService.getNoteDetailWithAttachment(n.getNoteId());
//                jo.put("noteId", n.getNoteId());
//                jo.put("title", n.getTitle());
////                jo.put("content", n.getContent());
////                jo.put("created_on", n.getCreateTime());
//                jo.put("last_updated_on", n.getLastModifiedTime());
                jsonArr.add(jo);
            }

            httpServletResponse.setHeader("status", String.valueOf(SC_OK));
            resultJson.put("NoteList", jsonArr);

            return ResponseEntity.ok()
                    .body(resultJson.toString());
        } else {
            return QuickResponse.userUnauthorized(httpServletResponse);
        }

    }


    /**
     * get note by id in the path
     *
     */
    @GetMapping("/note/{id}")
    public ResponseEntity<String> getNoteById(@PathVariable("id") String noteId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        statsd.incrementCounter("endpoint.noteId.http.get");
        User user = UserVerification.addVerification(httpServletRequest.getHeader("Authorization"));

        if (user == null) {
            return QuickResponse.userUnauthorized(httpServletResponse);
        }
        if (accountService.logIn(user)) {
            JSONObject resultJson = new JSONObject();
            Note note = noteService.getNoteById(user, noteId);
            if (note == null) {
                httpServletResponse.setHeader("status", String.valueOf(SC_NOT_FOUND));
                resultJson.put("message", "note not found");
                return ResponseEntity.badRequest()
                        .body(resultJson.toString());
            } else {


                httpServletResponse.setHeader("status", String.valueOf(HttpStatus.OK));
                return ResponseEntity.ok()
                        .body(noteService.getNoteDetailWithAttachment(noteId).toString());
            }

        } else {
            return QuickResponse.userUnauthorized(httpServletResponse);
        }

    }


    /**
     * create a new note using default structure
     *
     */
    @RequestMapping(method = RequestMethod.POST, value = "/note")
    public ResponseEntity<String> createNote(@RequestBody Note uploadedNote,
                                             HttpServletRequest httpServletRequest,
                                             HttpServletResponse httpServletResponse) throws IOException {

        User user = UserVerification.addVerification(httpServletRequest.getHeader("Authorization"));
        statsd.incrementCounter("endpoint.note.http.post");

        if (user == null) {
            return QuickResponse.userUnauthorized(httpServletResponse);
        }
        // if missing content or title
        if(uploadedNote.getContent() == null || uploadedNote.getTitle() == null)
            return QuickResponse.quickBadRequestConstruct(httpServletResponse, "note title or content missing");

        if (accountService.logIn(user)) {
            JSONObject resultJson = new JSONObject();
            Note note = noteService.createNote(user);
            note.setContent(uploadedNote.getContent());
            note.setTitle(uploadedNote.getTitle());
            noteService.updateNote(user, note);
//            JSONArray array= JSONArray.fromObject(note);
            httpServletResponse.setHeader("status", String.valueOf(HttpStatus.CREATED));
            resultJson.put("note", note);
//            resultJson.put("message", "Note created success");
            return ResponseEntity.ok()
                    .body(noteService.getNoteDetailWithAttachment(note.getNoteId()).toString());
        } else {
            return QuickResponse.userUnauthorized(httpServletResponse);
        }
    }


    /**
     * update a note by its id
     * @param httpServletRequest request body
     * @param httpServletResponse response body
     * @param noteId path parameter, note Id
     * @param updatedNote content to be update
     * @return response
     * @throws IOException by sendError()
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/note/{id}")
    public ResponseEntity<String> updateNote(HttpServletRequest httpServletRequest,
                                             HttpServletResponse httpServletResponse,
                                             @PathVariable("id") String noteId,
                                             @RequestBody Note updatedNote) throws IOException{

        User user = UserVerification.addVerification(httpServletRequest.getHeader("Authorization"));
        statsd.incrementCounter("endpoint.noteId.http.put");

        if (user == null) {
            return QuickResponse.userUnauthorized(httpServletResponse);
        }
        if (accountService.logIn(user)) {
            Note note = noteService.getNoteById(user, noteId);
            if (note != null) {
                JSONObject resultJson = new JSONObject();
                updatedNote.setNoteId(noteId);
                noteService.updateNote(user, updatedNote);
                httpServletResponse.setHeader("status", String.valueOf(HttpStatus.NO_CONTENT));
                resultJson.put("message", "Note updated success");
                JSONObject noteM = new JSONObject();
                noteM.put("noteId", updatedNote.getNoteId());
                noteM.put("title", updatedNote.getTitle());
                noteM.put("content", updatedNote.getContent());
                noteM.put("lastModifiedTime", updatedNote.getLastModifiedTime());
                resultJson.put("note", noteM);
                return ResponseEntity.ok()
                        .body(resultJson.toString());
            } else {
                JSONObject resultJson = new JSONObject();
                httpServletResponse.setHeader("status", String.valueOf(HttpStatus.BAD_REQUEST));
                resultJson.put("message", "No such a note for user " + user.getUsername());
                return ResponseEntity.badRequest()
                        .body(resultJson.toString());
            }

        } else {
            return QuickResponse.userUnauthorized(httpServletResponse);
        }
    }


    /**
     * delete note by id
     *
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/note/{id}")
    public ResponseEntity<String> deleteNote(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("id") String noteId) throws IOException {

        User user = UserVerification.addVerification(httpServletRequest.getHeader("Authorization"));
        statsd.incrementCounter("endpoint.noteId.http.delete");
        JSONObject resultJson = new JSONObject();
        if (user == null) {
            return QuickResponse.userUnauthorized(httpServletResponse);
        }
        if (accountService.logIn(user)) {
            // delete all of the attachments in this note
//            List<Attachment> atts = attachmentService.getAllAttachments(noteId);
//            if (atts != null){
//                for (Attachment att : atts){
//                    attachmentService.deleteAttachment(att.getAttachmentId());
//                    fileService.deleteFile(att.getAttachmentId());
//                }
//            } // TODO: change this logic, could successfully delete attachments but delete note failed!
            if (noteService.deleteNote(user, noteId)) {
                httpServletResponse.setHeader("status", String.valueOf(HttpStatus.NO_CONTENT));
                resultJson.put("message", "Note deleted success");
                return ResponseEntity.ok()
                        .body(resultJson.toString());
            } else {
                httpServletResponse.setHeader("status", String.valueOf(HttpStatus.BAD_REQUEST));
                resultJson.put("message", "Fail to delete note!");
                return ResponseEntity.badRequest()
                        .body(resultJson.toString());
            }

        } else {
            return QuickResponse.userUnauthorized(httpServletResponse);
        }
    }


}