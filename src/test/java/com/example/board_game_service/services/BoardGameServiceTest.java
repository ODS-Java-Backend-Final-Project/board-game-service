package com.example.board_game_service.services;

import com.example.board_game_service.models.BoardGame;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class BoardGameServiceTest {
    @Autowired
    BoardGameService boardGameService;

    @Test
    @DisplayName("We received the right board game by ID")
    public void testFindBoardGameById() {
        BoardGame foundBoardGame = boardGameService.findBoardGameById(1L);
        assertNotNull(foundBoardGame);
    }
}
