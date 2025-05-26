package com.example.board_game_service.controllers;

import com.example.board_game_service.exceptions.BoardGameNotFoundException;
import com.example.board_game_service.models.BoardGame;
import com.example.board_game_service.services.BoardGameService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/board-games")
public class BoardGameController {
    @Autowired
    BoardGameService boardGameService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getBoardGameById(@PathVariable Long id) {
        try {
            BoardGame foundboardGame = boardGameService.findBoardGameById(id);
            return new ResponseEntity<>(foundboardGame, HttpStatus.OK);
        } catch (BoardGameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BoardGame> findAllBoardGames() {
        return boardGameService.findAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBoardGameById(@PathVariable Long id) {
        try {
            boardGameService.deleteBoardGameById(id);
        } catch (BoardGameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoardGame createBoardGame(@RequestBody BoardGame boardGame) {
        return boardGameService.saveBoardGame(boardGame);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BoardGame updateBoardGame(@PathVariable Long id, @RequestBody @Valid BoardGame boardGame) {
        try {
            return boardGameService.updateBoardGame(id, boardGame);
        } catch (BoardGameNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}