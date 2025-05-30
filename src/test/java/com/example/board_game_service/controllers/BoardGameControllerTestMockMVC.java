package com.example.board_game_service.controllers;

import com.example.board_game_service.exceptions.BoardGameNotFoundException;
import com.example.board_game_service.models.BoardGame;
import com.example.board_game_service.repositories.BoardGameRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
public class BoardGameControllerTestMockMVC {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    BoardGameRepository boardGameRepository;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private BoardGame testBoardGame;
    private BoardGame testBoardGame2;

    @BeforeEach

    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        BoardGame newBoardGame = new BoardGame();

        newBoardGame.setName("Catan");
        newBoardGame.setCategory("Strategy");
        newBoardGame.setMinPlayers(2);
        newBoardGame.setMaxPlayers(6);
        newBoardGame.setDuration(90);

        testBoardGame = boardGameRepository.save(newBoardGame);

        BoardGame newBoardGame2 = new BoardGame();

        newBoardGame2.setName("Unstable Unicorns");
        newBoardGame2.setCategory("Strategy");
        newBoardGame2.setMinPlayers(2);
        newBoardGame2.setMaxPlayers(5);
        newBoardGame2.setDuration(30);

        testBoardGame2 = boardGameRepository.save(newBoardGame2);
    }
    @AfterEach
    public void tearDown() {
        try {
            boardGameRepository.deleteById(testBoardGame.getId());
            boardGameRepository.deleteById(testBoardGame2.getId());
        } catch (BoardGameNotFoundException e) {
            System.out.println("Delete test worked and already clean this");
        }
    }

    @Test
    public void createBoardGameTest() throws Exception {
        BoardGame newBoardGame = new BoardGame();

        newBoardGame.setName("Polilla Tramposa");
        newBoardGame.setCategory("Trampas");
        newBoardGame.setMinPlayers(2);
        newBoardGame.setMaxPlayers(6);
        newBoardGame.setDuration(90);
        String boardGameJson = objectMapper.writeValueAsString(newBoardGame);

        MvcResult result = mockMvc.perform(post("/api/board-games")
                .contentType(MediaType.APPLICATION_JSON)
                .content(boardGameJson)
        ).andExpect(status().isCreated()).andReturn();

        String stringResponse = result.getResponse().getContentAsString();
        System.out.println(result.getResponse());
        assertTrue(stringResponse.contains(testBoardGame.getName()));
    }

    @Test
    public void putBoardGameTest() throws Exception {
        String boardGameJson = objectMapper.writeValueAsString(testBoardGame2);
        MvcResult result = mockMvc.perform(put("/api/board-games/" + testBoardGame2.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(boardGameJson)
        ).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(testBoardGame2.getName()));
    }

    @Test
    void getAllTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/board-games"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains(testBoardGame.getName()));
        assertTrue(mvcResult.getResponse().getContentAsString().contains(testBoardGame2.getName()));
    }

    @Test
    void getByIdTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/board-games/" + testBoardGame.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains(testBoardGame.getName()));
    }


    @Test
    void getDeleteByIdTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/api/board-games/" + testBoardGame.getId()))
                .andExpect(status().isNoContent()).andReturn();
        Optional<BoardGame> deletedBoardGame = boardGameRepository.findById(testBoardGame.getId());
        assertFalse(deletedBoardGame.isPresent(), "Board Game should´ve been deleted. Test failed");
    }

}