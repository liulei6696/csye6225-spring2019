package edu.neu.coe.csye6225;

import edu.neu.coe.csye6225.entity.Note;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NoteTakingApplicationTests {

	@Test
	public void contextLoads() {
		Note note = new Note("123","title", "content");
		System.out.println(note);
	}

}

