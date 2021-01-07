package com.erecycler.server.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.erecycler.server.common.ErrorCase;
import com.erecycler.server.domain.RecycleGuide;
import com.erecycler.server.service.RecycleGuideService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(RecycleGuideController.class)
@AutoConfigureMockMvc
class RecycleGuideControllerTest {
	List<RecycleGuide> mockRecycleGuides = new ArrayList<>();
	List<String> mockMaterials = new ArrayList<>();
	List<String> mockItems = new ArrayList<>();
	RecycleGuide mockRecycleGuide1 = RecycleGuide.builder()
		.material("materialName1")
		.item("itemName1")
		.guideline("guidelineBody1")
		.build();
	RecycleGuide mockRecycleGuide2 = RecycleGuide.builder()
		.material("materialName1")
		.item("itemName2")
		.guideline("guidelineBody2")
		.build();
	RecycleGuide mockRecycleGuide3 = RecycleGuide.builder()
		.material("materialName2")
		.item("itemName3")
		.guideline("guidelineBody3")
		.build();
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private RecycleGuideService recycleGuideService;

	private String toJSONArray(List<String> list) throws JSONException {
		return new JSONArray(list.toString()).toString();
	}

	@BeforeEach
	void setUp() {
		mockRecycleGuides.add(mockRecycleGuide1);
		mockMaterials.add(mockRecycleGuide1.getMaterial());
		mockItems.add(mockRecycleGuide1.getItem());

		mockRecycleGuides.add(mockRecycleGuide2);
		mockMaterials.add(mockRecycleGuide2.getMaterial());
		mockItems.add(mockRecycleGuide2.getItem());

		mockRecycleGuides.add(mockRecycleGuide3);
		mockMaterials.add(mockRecycleGuide3.getMaterial());
		mockItems.add(mockRecycleGuide3.getItem());
	}

	@Test
	@DisplayName("POST /guide controller")
	void addGuide() throws Exception {
		String requestBody = objectMapper.writeValueAsString(mockRecycleGuide1);

		// given
		given(this.recycleGuideService.addGuide(any(RecycleGuide.class))).willReturn("OK");
		// when
		mockMvc.perform(post("/guide")
			.content(requestBody)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			// then
			.andExpect(status().isCreated())
			.andExpect(content().string(""))
			.andDo(print());

		// given
		given(this.recycleGuideService.addGuide(any(RecycleGuide.class)))
			.willReturn(ErrorCase.INVALID_FIELD_ERROR);
		// when
		mockMvc.perform(post("/guide")
			.content(requestBody)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			// then
			.andExpect(status().isBadRequest())
			.andDo(print());
	}
}