package edu.neu.coe.csye6225.controller;

import edu.neu.coe.csye6225.entity.Note;
import edu.neu.coe.csye6225.entity.User;
import edu.neu.coe.csye6225.service.AccountService;
import edu.neu.coe.csye6225.service.NoteService;
import edu.neu.coe.csye6225.service.UserVerification;
import edu.neu.coe.csye6225.util.ResultJson;
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
    public ResponseEntity<String> getALlNotes(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        String auth = httpServletRequest.getHeader("Authorization");
        User user = UserVerification.addVerification(auth);
        ResultJson resultJson = new ResultJson();
        if(user == null){
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED,"Login failure! The username or password is wrong");
            resultJson.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
            resultJson.put("message", "Login failure! The username or password is wrong");
            return ResponseEntity.badRequest()
                    .body(resultJson.toString());
        }
        if(accountService.logIn(user)){
            List<Note> noteList = noteService.getAllNotes(user);
            //TODO list-> string
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < noteList.size(); i++) {
                if(i < noteList.size() - 1) {
                    stringBuilder.append(noteList.get(i).toString()).append(",");
                } else {
                    stringBuilder.append(noteList.get(i).toString());
                }
            }
            String noteListStr = "[" + stringBuilder.toString() + "]";

            resultJson.put("Note List", noteListStr);
            return ResponseEntity.ok()
                    .body(resultJson.toString());
        }else {
            resultJson.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
            resultJson.put("message", "Login failure! The username or password is wrong");
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED,"Login failure! The username or password is wrong");
            return ResponseEntity.badRequest()
                    .body(resultJson.toString());
        }

    }


    @GetMapping("/note/{id}")
    public ResponseEntity<String> getNoteById(@PathVariable("id") String noteId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        String auth = httpServletRequest.getHeader("Authorization");
        User user = UserVerification.addVerification(auth);
        ResultJson resultJson = new ResultJson();
        if(user == null){
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED,"Login failure! The username or password is wrong");
            resultJson.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
            resultJson.put("message", "Login failure! The username or password is wrong");
            return ResponseEntity.badRequest()
                    .body(resultJson.toString());
        }
        if(accountService.logIn(user)){
            Note note = noteService.getNoteById(user, noteId);
            resultJson.put("Note", note.toString());
            return ResponseEntity.ok()
                    .body(resultJson.toString());
        }else {
            resultJson.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
            resultJson.put("message", "Login failure! The username or password is wrong");
            httpServletResponse.setStatus(SC_UNAUTHORIZED);
            httpServletResponse.sendError(SC_UNAUTHORIZED,"Login failure! The username or password is wrong");
            return ResponseEntity.badRequest()
                    .body(resultJson.toString());
        }

    }


    @RequestMapping(method = RequestMethod.POST, value = "/note")
    public ResponseEntity<String> createNote(@RequestBody User user) {
        ResultJson resultJson = new ResultJson();
        if(accountService.logIn(user)){
            Note note = noteService.createNote(user);
            resultJson.put("status", String.valueOf(HttpStatus.OK));
            resultJson.put("note", note.toString());
            resultJson.put("message", "Note created success");
            return ResponseEntity.ok()
                    .body(resultJson.toString());
        }else {
            resultJson.put("status", String.valueOf(HttpStatus.BAD_REQUEST));
            resultJson.put("message", "No authorization to create a note!");
            return ResponseEntity.badRequest()
                    .body(resultJson.toString());
        }
    }


    @RequestMapping(method = RequestMethod.PUT, value = "/note{id}")
    public ResponseEntity<String> updateNote(@RequestBody User user,@PathVariable("id") String noteId,Note note) {
        ResultJson resultJson = new ResultJson();
        if(accountService.logIn(user)){
//            Note originNote = noteService.getNoteById(user, noteId);
//            originNote.setTitle(note.getTitle());
//            originNote.setContent(note.getContent());
//            originNote.setLastModifiedTime();
            Note updatedNote = noteService.updateNote(user, note);
            resultJson.put("status", String.valueOf(HttpStatus.OK));
            resultJson.put("note", updatedNote.toString());
            resultJson.put("message", "Note updated success");
            return ResponseEntity.ok()
                    .body(resultJson.toString());
        }else {
            resultJson.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
            resultJson.put("message", "No authorization to update a note!");
            return ResponseEntity.badRequest()
                    .body(resultJson.toString());
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/note{id}")
    public ResponseEntity<String> deleteNote(@RequestBody User user,@PathVariable("id") String noteId) {
        ResultJson resultJson = new ResultJson();
        if(accountService.logIn(user)){
            if(noteService.deleteNote(user, noteId)) {
                resultJson.put("status", String.valueOf(HttpStatus.OK));
                resultJson.put("message", "Note deleted success");
                return ResponseEntity.ok()
                        .body(resultJson.toString());
            } else {
                resultJson.put("status", String.valueOf(HttpStatus.BAD_REQUEST));
                resultJson.put("message", "Fail to delete note!");
                return ResponseEntity.badRequest()
                        .body(resultJson.toString());
            }

        }else {
            resultJson.put("status", String.valueOf(HttpStatus.UNAUTHORIZED));
            resultJson.put("message", "No authorization to delete a note!");
            return ResponseEntity.badRequest()
                    .body(resultJson.toString());
        }
    }


}
