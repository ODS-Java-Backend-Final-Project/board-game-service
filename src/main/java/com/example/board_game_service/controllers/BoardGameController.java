package com.example.board_game_service.controllers;

import com.example.board_game_service.models.BoardGame;
import com.example.board_game_service.repositories.BoardGameRepository;
import com.example.board_game_service.services.BoardGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board-games")
public class BoardGameController {
    @Autowired
    BoardGameService boardGameService;

    @GetMapping("/{id}")
    public ResponseEntity<BoardGame> getBoardGameById(@PathVariable Long id) {
        BoardGame foundboardGame = boardGameService.findBoardGameById(id);
        if(foundboardGame != null) {
            return new ResponseEntity<>(foundboardGame, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
