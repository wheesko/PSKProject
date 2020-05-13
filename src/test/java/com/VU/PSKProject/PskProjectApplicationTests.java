package com.VU.PSKProject;

import com.VU.PSKProject.Entity.LearningDay;
import com.VU.PSKProject.Entity.Worker;
import com.VU.PSKProject.Service.Mapper.LearningDayMapper;
import com.VU.PSKProject.Service.Model.LearningDayDTO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;

@SpringBootTest
class PskProjectApplicationTests {

	@Test
	void contextLoads() {
	}

	private ModelMapper modelMapper = new ModelMapper();

	@Test
	public void whenConvertLearningDayEntityToLearningDayDto_thenCorrect() {
		LearningDay learningDay = new LearningDay();
		Worker worker = new Worker();
		LearningDayMapper learningDayMapper = new LearningDayMapper();

		worker.setId(1L);
		worker.setConsecutiveLearningDayLimit(5);
		worker.setGoals(null);
		worker.setManagedTeam(null);
		worker.setName("A");
		worker.setSurname("B");
		worker.setWorkingTeam(null);

		learningDay.setId(1L);
		learningDay.setAssignee(worker);
		learningDay.setComment("Java");
		learningDay.setName("Java");
		learningDay.setDateTimeAt(Timestamp.valueOf("2020-05-03 00:00:00"));

		LearningDayDTO learningDayDTO = modelMapper.map(learningDay, LearningDayDTO.class);

		Assert.assertEquals(learningDay.getDateTimeAt(), learningDayDTO.getDateTimeAt());
		Assert.assertEquals(learningDay.getId(), learningDayDTO.getId());
		Assert.assertEquals(learningDay.getComment(), learningDayDTO.getComment());
		Assert.assertEquals(learningDay.getAssignee().getId(), learningDayDTO.getAssignee().getId());
		Assert.assertEquals(learningDay.getName(), learningDayDTO.getName());
	}

	/*@Test
	public void whenConvertPostDtoToPostEntity_thenCorrect() {
		PostDto postDto = new PostDto();
		postDto.setId(1L);
		postDto.setTitle(randomAlphabetic(6));
		postDto.setUrl("www.test.com");

		Post post = modelMapper.map(postDto, Post.class);
		assertEquals(postDto.getId(), post.getId());
		assertEquals(postDto.getTitle(), post.getTitle());
		assertEquals(postDto.getUrl(), post.getUrl());
	}*/

}
