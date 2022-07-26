package com.example.tictactoe.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.example.tictactoe.dto.GameUserTableDto;
import com.example.tictactoe.model.GameUser;
import com.example.tictactoe.repository.GameUserRepository;

@Repository
@Transactional
public class GameUserRepositoryImpl implements GameUserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(GameUser gameUser) {
        entityManager.persist(gameUser);

    }

    @Override
    public List<GameUser> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<GameUserTableDto> tableGameUsers() {
        List<GameUser> gameUsers = entityManager
                .createQuery(
                        "SELECT GameUser from GameUser GameUser order by (GameUser.win/GameUser.win+GameUser.defeat)",
                        GameUser.class)
                .setMaxResults(10).getResultList();
        List<GameUserTableDto> gameUserTableDtos = new ArrayList<GameUserTableDto>();
        for (GameUser gameUser : gameUsers) {
            gameUserTableDtos.add(new GameUserTableDto(gameUser.getFio(), gameUser.getWin(), gameUser.getDefeat()));
        }

        return gameUserTableDtos;
    }

    @Override
    public GameUser findById(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GameUser authorization(String login, String password) {
        System.out.println(login + " " + password);
        List<GameUser> gameUsers = entityManager
                .createQuery("SELECT GameUser from GameUser GameUser where GameUser.login=?1 and GameUser.password=?2",
                        GameUser.class)
                .setParameter(1, login)
                .setParameter(2, password)
                .getResultList();
        return gameUsers.size() == 0 ? null : gameUsers.get(0);
    }

}