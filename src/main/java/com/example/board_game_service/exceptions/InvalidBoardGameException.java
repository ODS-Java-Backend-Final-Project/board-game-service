package com.example.board_game_service.exceptions;

public class InvalidBoardGameException extends RuntimeException {
    public InvalidBoardGameException(String message) {
        super(message);
    }
}
