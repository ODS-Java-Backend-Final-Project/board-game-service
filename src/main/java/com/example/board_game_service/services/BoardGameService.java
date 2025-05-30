package com.example.board_game_service.services;

import com.example.board_game_service.exceptions.BoardGameNotFoundException;
import com.example.board_game_service.exceptions.InvalidBoardGameException;
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
            throw new BoardGameNotFoundException("Can´t delete. Board game with ID " + id + " is not in Data Base.");
        }
    }

    public BoardGame saveBoardGame(BoardGame boardGame) {
        Optional<BoardGame> foundBoardGame = boardGameRepository.findByName(boardGame.getName());
        if(foundBoardGame.isPresent()) {
            throw new InvalidBoardGameException(boardGame.getName() + " is already in the data base. ID: " + boardGame.getId());
        }
        boardGameValidation(boardGame);
        return boardGameRepository.save(boardGame);
    }

    public BoardGame updateBoardGame(Long id, BoardGame boardGame ) throws BoardGameNotFoundException{
        boardGameValidation(boardGame);

        Optional<BoardGame> optionalBoardGame = boardGameRepository.findById(id);
        if(optionalBoardGame.isPresent()) {
           BoardGame existingBoardGame = optionalBoardGame.get();
            existingBoardGame.setName(boardGame.getName());
            existingBoardGame.setCategory(boardGame.getCategory());
            existingBoardGame.setMinPlayers(boardGame.getMinPlayers());
            existingBoardGame.setMaxPlayers(boardGame.getMaxPlayers());
            existingBoardGame.setDuration(boardGame.getDuration());

            return boardGameRepository.save(existingBoardGame);
        } else {
            throw new BoardGameNotFoundException("Board game with ID " + id + " not found.");
        }
    }

    private void boardGameValidation(BoardGame boardGame) {

        if (boardGame.getMinPlayers() > boardGame.getMaxPlayers()) {
            throw new InvalidBoardGameException("Minimum players cannot be greater than maximum players.");
        }
        if (boardGame.getMinPlayers() == 0 || boardGame.getMaxPlayers() == 0 ) {
            throw new InvalidBoardGameException("Number of player must be at least 1.");
        }
        if (boardGame.getDuration() == 0 ) {
            throw new InvalidBoardGameException("Duration must be at least 1 minute.");
        }
    }

}
