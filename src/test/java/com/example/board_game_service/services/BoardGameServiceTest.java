package com.example.board_game_service.services;

import com.example.board_game_service.exceptions.BoardGameNotFoundException;
import com.example.board_game_service.models.BoardGame;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BoardGameServiceTest {
    @Autowired
    BoardGameService boardGameService;

    private  BoardGame testBoardGame;

    @BeforeEach
    public void  setUp () {
        BoardGame newBoardGame = new BoardGame();

        newBoardGame.setName("Catan");
        newBoardGame.setCategory("Strategy");
        newBoardGame.setMinPlayers(2);
        newBoardGame.setMaxPlayers(6);
        newBoardGame.setDuration(90);

        testBoardGame = boardGameService.saveBoardGame(newBoardGame);
    }

    @Test
    @DisplayName("We received the right board game by ID")
    public void testFindBoardGameById() {
        BoardGame foundBoardGame = boardGameService.findBoardGameById(testBoardGame.getId());
        assertNotNull(foundBoardGame);
    }

    @Test
    @DisplayName("We received a exception when trying to find a board game by non-existing ID")
    public void testFindBoardGameByIdException() {
        assertThrows(BoardGameNotFoundException.class, () -> boardGameService.findBoardGameById(99L));
    }

    @Test
    @DisplayName("We received all board games in data base")
    public void testFindAllBoardGames() {
        List<BoardGame> foundBoardGames = boardGameService.findAll();
        assertNotNull(foundBoardGames);
    }

    @Test
    @DisplayName("We save a new board game and check if it is saved properly")
    public void testSaveBoardGame() {
        BoardGame newBoardGame = new BoardGame();

        newBoardGame.setName("Catan");
        newBoardGame.setCategory("Strategy");
        newBoardGame.setMinPlayers(2);
        newBoardGame.setMaxPlayers(6);
        newBoardGame.setDuration(90);
        BoardGame savedBoardGame = boardGameService.saveBoardGame(newBoardGame);

        assertNotNull(savedBoardGame);

        assertEquals("Catan", savedBoardGame.getName());
        assertEquals("Strategy", savedBoardGame.getCategory());
        assertEquals(2, savedBoardGame.getMinPlayers());
        assertEquals(6, savedBoardGame.getMaxPlayers());
        assertEquals(90, savedBoardGame.getDuration());
    }

    @Test
    @DisplayName("Delete board game by ID and check if it is deleted properly")
    public void testDeleteBoardGameById() {
        boardGameService.deleteBoardGameById(testBoardGame.getId());
        assertThrows(BoardGameNotFoundException.class, () -> boardGameService.findBoardGameById(testBoardGame.getId()));
    }

    @Test
    @DisplayName("We update a board game and check if it is updated properly")
    public void testUpdateBoardGame() {
        BoardGame updatedBoardGame = new BoardGame();

        updatedBoardGame.setName("Unstable Unicorns");
        updatedBoardGame.setCategory("Strategy");
        updatedBoardGame.setMinPlayers(3);
        updatedBoardGame.setMaxPlayers(6);
        updatedBoardGame.setDuration(90);

        BoardGame boardGameChanged = boardGameService.updateBoardGame(testBoardGame.getId(), updatedBoardGame);

        assertNotNull(boardGameChanged);
        assertEquals("Unstable Unicorns", boardGameChanged.getName());
        assertEquals("Strategy", boardGameChanged.getCategory());
        assertEquals(3, boardGameChanged.getMinPlayers());
        assertEquals(6, boardGameChanged.getMaxPlayers());
        assertEquals(90, boardGameChanged.getDuration());
    }


    @AfterEach
    public void tearDown() {
        try {
            boardGameService.deleteBoardGameById(testBoardGame.getId());
        } catch (BoardGameNotFoundException e) {
            System.out.println("Delete test already clean this");
        }
    }

}
