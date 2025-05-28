package com.example.board_game_service.controllers;

import com.example.board_game_service.models.BoardGame;
import com.example.board_game_service.repositories.BoardGameRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(BoardGameController.class)
public class BoardGameControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    BoardGameRepository boardGameRepository;


    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach

    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        BoardGame newBoardGame1 = new BoardGame();

        newBoardGame1.setName("Catan");
        newBoardGame1.setCategory("Strategy");
        newBoardGame1.setMinPlayers(2);
        newBoardGame1.setMaxPlayers(6);
        newBoardGame1.setDuration(90);

        BoardGame newBoardGame2= new BoardGame();

        newBoardGame2.setName("Unstable Unicorns");
        newBoardGame2.setCategory("Strategy");
        newBoardGame2.setMinPlayers(2);
        newBoardGame2.setMaxPlayers(6);
        newBoardGame2.setDuration(30);

        boardGameRepository.saveAll(List.of(newBoardGame1, newBoardGame2));
    }

    @AfterEach
    void tearDown() {
        boardGameRepository.deleteAll();
    }

    @Test
    void getAllBoardGames_Valid() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/board-games"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Catan"));
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Unstable Unicorns"));
    }

}





//import com.example.board_game_service.controllers.BoardGameController;
//import com.example.board_game_service.models.BoardGame;
//import com.example.board_game_service.services.BoardGameService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.mockito.Mockito.*;
//        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//
//@WebMvcTest(BoardGameController.class)
//public class BoardGameControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private BoardGameService boardGameService;
//
//    @Test
//    public void testGetBoardGameById() throws Exception {
//        BoardGame boardGame = new BoardGame();
//        boardGame.setId(1L);
//        boardGame.setName("Catan");
//
//        when(boardGameService.findBoardGameById(1L)).thenReturn(boardGame);
//
//        mockMvc.perform(get("/api/board-games/1"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.name").value("Catan"));
//    }
//}
