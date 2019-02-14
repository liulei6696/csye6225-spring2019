package edu.neu.coe.csye6225.controller;

import edu.neu.coe.csye6225.entity.Note;
import edu.neu.coe.csye6225.entity.User;
import edu.neu.coe.csye6225.service.AccountService;
import edu.neu.coe.csye6225.service.NoteService;
import edu.neu.coe.csye6225.service.UserVerification;
import edu.neu.coe.csye6225.util.ResultJson;
import net.sf.json.JSON;
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

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

@RestController
public class NoteController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private NoteService noteService;

    @GetMapping("/note")
    public ResponseEntity<String> getAllNotes(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        String auth = httpServletRequest.getHeader("Authorization");
        User user = UserVerification.addVerification(auth);
        JSONObject resultJson = new JSONObject();
        if (user == null) {
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED, "Login failure! The username or password is wrong");
            resultJson.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
            resultJson.put("message", "Login failure! The username or password is wrong");
            return ResponseEntity.badRequest()
                    .body(resultJson.toString());
        }
        if (accountService.logIn(user)) {
            List<Note> noteList = noteService.getAllNotes(user);
            //TODO list-> string
            JSONArray jsonArr = new JSONArray();
            for(Note n : noteList){
                JSONObject jo = new JSONObject();
                jo.put("noteId", n.getNoteId());
                jo.put("title", n.getTitle());
                jo.put("content", n.getContent());
                jo.put("created_on", n.getCreateTime());
                jo.put("last_updated_on", n.getLastModifiedTime());
                jsonArr.add(jo);
            }


            resultJson.put("status", String.valueOf(HttpStatus.OK));
            resultJson.put("Note List", jsonArr.toString());

            return ResponseEntity.ok()
                    .body(resultJson.toString());
        } else {
            resultJson.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
            resultJson.put("message", "Login failure! The username or password is wrong");
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED, "Login failure! The username or password is wrong");
            return ResponseEntity.badRequest()
                    .body(resultJson.toString());
        }

    }


    @GetMapping("/note/{id}")
    public ResponseEntity<String> getNoteById(@PathVariable("id") String noteId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        String auth = httpServletRequest.getHeader("Authorization");
        User user = UserVerification.addVerification(auth);
        JSONObject resultJson = new JSONObject();
        if (user == null) {
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED, "Login failure! The username or password is wrong");
            resultJson.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
            resultJson.put("message", "Login failure! The username or password is wrong");
            return ResponseEntity.badRequest()
                    .body(resultJson.toString());
        }
        if (accountService.logIn(user)) {
            Note note = noteService.getNoteById(user, noteId);
            if (note == null) {
                resultJson.put("status", String.valueOf(HttpStatus.NOT_FOUND));
                return ResponseEntity.badRequest()
                        .body(resultJson.toString());
            } else {
                resultJson.put("status", String.valueOf(HttpStatus.OK));
                resultJson.put("Note", note);
                return ResponseEntity.ok()
                        .body(resultJson.toString());
            }

        } else {
            resultJson.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
            resultJson.put("message", "Login failure! The username or password is wrong");
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED, "Login failure! The username or password is wrong");
            return ResponseEntity.badRequest()
                    .body(resultJson.toString());
        }

    }


    @RequestMapping(method = RequestMethod.POST, value = "/note")
    public ResponseEntity<String> createNote(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        String auth = httpServletRequest.getHeader("Authorization");
        User user = UserVerification.addVerification(auth);
        JSONObject resultJson = new JSONObject();
        if (user == null) {
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED, "Login failure! The username or password is wrong");
            resultJson.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
            resultJson.put("message", "Login failure! The username or password is wrong");
            return ResponseEntity.badRequest()
                    .body(resultJson.toString());
        }
        if (accountService.logIn(user)) {
            Note note = noteService.createNote(user);
            JSONArray array= JSONArray.fromObject(note);
            resultJson.put("status", String.valueOf(HttpStatus.CREATED));
            resultJson.put("note", array.toString());
//            resultJson.put("message", "Note created success");
            return ResponseEntity.ok()
                    .body(resultJson.toString());
        } else {
            resultJson.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
            resultJson.put("message", "No authorization to create a note!");
            return ResponseEntity.badRequest()
                    .body(resultJson.toString());
        }
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/note/{id}")
    public ResponseEntity<String> updateNote(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("id") String noteId, @RequestBody Note updatedNote) throws IOException{
        String auth = httpServletRequest.getHeader("Authorization");
        User user = UserVerification.addVerification(auth);
        JSONObject resultJson = new JSONObject();
        if (user == null) {
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED, "Login failure! The username or password is wrong");
            resultJson.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
            resultJson.put("message", "Login failure! The username or password is wrong");
            return ResponseEntity.badRequest()
                    .body(resultJson.toString());
        }
        if (accountService.logIn(user)) {
            Note note = noteService.getNoteById(user, noteId);
            if (note != null) {
                noteService.updateNote(user, updatedNote);
                resultJson.put("status", String.valueOf(HttpStatus.NO_CONTENT));
//                resultJson.put("note", updatedNote.toString());
//                resultJson.put("message", "Note updated success");
                return ResponseEntity.ok()
                        .body(resultJson.toString());
            } else {
                resultJson.put("status", String.valueOf(HttpStatus.BAD_REQUEST));
                resultJson.put("message", "No such a note for user " + user.getUsername());
                return ResponseEntity.badRequest()
                        .body(resultJson.toString());
            }

        } else {
            resultJson.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
            resultJson.put("message", "No authorization to update a note!");
            return ResponseEntity.badRequest()
                    .body(resultJson.toString());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/note/{id}")
    public ResponseEntity<String> deleteNote(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, @PathVariable("id") String noteId) throws IOException {
        String auth = httpServletRequest.getHeader("Authorization");
        User user = UserVerification.addVerification(auth);
        JSONObject resultJson = new JSONObject();
        if (user == null) {
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED, "Login failure! The username or password is wrong");
            resultJson.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
            resultJson.put("message", "Login failure! The username or password is wrong");
            return ResponseEntity.badRequest()
                    .body(resultJson.toString());
        }
        if (accountService.logIn(user)) {
            if (noteService.deleteNote(user, noteId)) {
                resultJson.put("status", String.valueOf(HttpStatus.NO_CONTENT));
                resultJson.put("message", "Note deleted success");
                return ResponseEntity.ok()
                        .body(resultJson.toString());
            } else {
                resultJson.put("status", String.valueOf(HttpStatus.BAD_REQUEST));
                resultJson.put("message", "Fail to delete note!");
                return ResponseEntity.badRequest()
                        .body(resultJson.toString());
            }

        } else {
            resultJson.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
            resultJson.put("message", "No authorization to delete a note!");
            return ResponseEntity.badRequest()
                    .body(resultJson.toString());
        }
    }


}
