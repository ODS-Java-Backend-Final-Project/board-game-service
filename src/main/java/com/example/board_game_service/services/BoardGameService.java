package com.example.board_game_service.services;

import com.example.board_game_service.exceptions.BoardGameNotFoundException;
import com.example.board_game_service.models.BoardGame;
import com.example.board_game_service.repositories.BoardGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BoardGameService {
    @Autowired
    BoardGameRepository boardGameRepository;

    public BoardGame findBoardGameById(Long id) {
        Optional<BoardGame> foundBoardGame =   boardGameRepository.findById(id);
        if (foundBoardGame.isPresent()) {
            return foundBoardGame.get();
        } else {
            throw new BoardGameNotFoundException("Board game with ID " + id + " not found.");
        }
    }

    public void deleteBoardGameById(Long id) {
        Optional<BoardGame> foundBoardGame =   boardGameRepository.findById(id);
        if (foundBoardGame.isPresent()) {
            boardGameRepository.deleteById(id);
        } else {
            throw new BoardGameNotFoundException("Board game with ID " + id + " not found.");
        }
    }
}
