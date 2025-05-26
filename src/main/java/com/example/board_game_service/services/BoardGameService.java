package com.example.board_game_service.services;

import com.example.board_game_service.exceptions.BoardGameNotFoundException;
import com.example.board_game_service.models.BoardGame;
import com.example.board_game_service.repositories.BoardGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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

    public List<BoardGame> findAll() {
        return boardGameRepository.findAll();
    }

    public void deleteBoardGameById(Long id) {
        Optional<BoardGame> foundBoardGame = boardGameRepository.findById(id);
        if (foundBoardGame.isPresent()) {
            boardGameRepository.deleteById(id);
        } else {
            throw new BoardGameNotFoundException("Board game with ID " + id + " not found.");
        }
    }

    public BoardGame saveBoardGame(BoardGame boardGame) {
        return boardGameRepository.save(boardGame);
    }

    public BoardGame updateBoardGame(Long id, BoardGame boardGame ) throws BoardGameNotFoundException{
        BoardGame existingBoardGame = boardGameRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Board game with ID " + id + " not found."));
        existingBoardGame.setName(boardGame.getName());
        existingBoardGame.setCategory(boardGame.getCategory());
        existingBoardGame.setMinPlayers(boardGame.getMinPlayers());
        existingBoardGame.setMaxPlayers(boardGame.getMaxPlayers());
        existingBoardGame.setDuration(boardGame.getDuration());

        return boardGameRepository.save(existingBoardGame);
    }

}
