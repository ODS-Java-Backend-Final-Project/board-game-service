package com.example.board_game_service.controllers;

import com.example.board_game_service.exceptions.BoardGameNotFoundException;
import com.example.board_game_service.exceptions.InvalidBoardGameException;
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
    public ResponseEntity<?> findAllBoardGames() {
        try {
            return new ResponseEntity<>(boardGameService.findAll(), HttpStatus.OK);
        } catch (BoardGameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteBoardGameById(@PathVariable Long id) {
        try {
            boardGameService.deleteBoardGameById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (BoardGameNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createBoardGame(@RequestBody  BoardGame boardGame) {
        try {
           BoardGame newBoardGame = boardGameService.saveBoardGame(boardGame);
            return new ResponseEntity<>(newBoardGame, HttpStatus.CREATED);
        } catch (InvalidBoardGameException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateBoardGame(@PathVariable Long id, @RequestBody  BoardGame boardGame) {
        try {
            BoardGame updatedBoardGame = boardGameService.updateBoardGame(id, boardGame);
            return new ResponseEntity<>(updatedBoardGame, HttpStatus.OK);
        } catch (BoardGameNotFoundException e) {
            return new ResponseEntity<>( e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (InvalidBoardGameException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

}