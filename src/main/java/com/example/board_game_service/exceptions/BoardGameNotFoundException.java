package com.example.board_game_service.exceptions;

public class BoardGameNotFoundException extends RuntimeException {
    public BoardGameNotFoundException(String message) {
        super(message);
    }
}
