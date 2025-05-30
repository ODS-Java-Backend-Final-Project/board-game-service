package com.example.board_game_service.repositories;

import com.example.board_game_service.models.BoardGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardGameRepository extends JpaRepository<BoardGame, Long> {
    Optional<BoardGame> findByName(String name);
}
