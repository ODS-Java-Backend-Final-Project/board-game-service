package com.example.board_game_service.controllers;

import com.example.board_game_service.exceptions.BoardGameNotFoundException;
import com.example.board_game_service.models.BoardGame;
import com.example.board_game_service.repositories.BoardGameRepository;
import com.example.board_game_service.services.BoardGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        } catch (BoardGameNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
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
}
