package cs4347.jdbcGame.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcGame.dao.GameDAO;
import cs4347.jdbcGame.dao.impl.GameDAOImpl;
import cs4347.jdbcGame.entity.Game;
import cs4347.jdbcGame.services.GameService;
import cs4347.jdbcGame.util.DAOException;

public class GameServiceImpl implements GameService
{

    private DataSource dataSource;
    private GameDAO gd;

    public GameServiceImpl(DataSource dataSource)
    {
        this.dataSource = dataSource;
        this.gd = new GameDAOImpl();
    }

    @Override
    public Game create(Game game) throws DAOException, SQLException
    {
        Connection connection = dataSource.getConnection();
        try {
            return gd.create(connection, game);
        }
        finally {
            connection.close();
        }
    }

    @Override
    public Game retrieve(long gameID) throws DAOException, SQLException
    {
        Connection connection = dataSource.getConnection();
        try {
            return gd.retrieve(connection, gameID);
        }
        finally {
            connection.close();
        }
    }

    @Override
    public int update(Game game) throws DAOException, SQLException
    {
        Connection connection = dataSource.getConnection();
        try {
            return gd.update(connection, game);
        }
        finally {
            connection.close();
        }
    }

    @Override
    public int delete(long gameID) throws DAOException, SQLException
    {
        Connection connection = dataSource.getConnection();
        try {
            return gd.delete(connection, gameID);
        }
        finally {
            connection.close();
        }
    }

    @Override
    public int count() throws DAOException, SQLException
    {
        Connection connection = dataSource.getConnection();
        try {
            return gd.count(connection);
        }
        finally {
            connection.close();
        }

    }

    @Override
    public List<Game> retrieveByTitle(String titlePattern) throws DAOException, SQLException
    {
        Connection connection = dataSource.getConnection();
        try {
            return gd.retrieveByTitle(connection, titlePattern);
        }
        finally {
            connection.close();
        }
    }

    @Override
    public List<Game> retrieveByReleaseDate(Date start, Date end) throws DAOException, SQLException
    {
        Connection connection = dataSource.getConnection();
        try {
            return gd.retrieveByReleaseDate(connection, start, end);
        }
        finally {
            connection.close();
        }
    }

}