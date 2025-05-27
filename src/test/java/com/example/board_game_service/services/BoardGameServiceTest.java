package com.example.board_game_service.services;

import com.example.board_game_service.models.BoardGame;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class BoardGameServiceTest {
    @Autowired
    BoardGameService boardGameService;

    @Test
    @DisplayName("We received the right board game by ID")
    public void testFindBoardGameById() {
        BoardGame foundBoardGame = boardGameService.findBoardGameById(5L);
        assertNotNull(foundBoardGame);
    }

    @Test
    @DisplayName("We received all board games in data base")
    public void testFindAllBoardGames() {
        List<BoardGame> foundBoardGames = boardGameService.findAll();
        assertNotNull(foundBoardGames);
    }
}
