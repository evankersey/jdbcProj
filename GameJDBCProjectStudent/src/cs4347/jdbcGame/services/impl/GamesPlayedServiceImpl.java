package cs4347.jdbcGame.services.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import cs4347.jdbcGame.dao.GameDAO;
import cs4347.jdbcGame.dao.GamesPlayedDAO;
import cs4347.jdbcGame.dao.impl.GameDAOImpl;
import cs4347.jdbcGame.dao.impl.GamesPlayedDAOImpl;
import cs4347.jdbcGame.entity.GamesPlayed;
import cs4347.jdbcGame.services.GamesPlayedService;
import cs4347.jdbcGame.util.DAOException;

public class GamesPlayedServiceImpl implements GamesPlayedService
{
    private DataSource dataSource;
    private GamesPlayedDAO gdp;

    public GamesPlayedServiceImpl(DataSource dataSource)
    {
        this.dataSource = dataSource;
        this.gdp = new GamesPlayedDAOImpl();
    }

    @Override
    public GamesPlayed create(GamesPlayed gamesPlayed) throws DAOException, SQLException
    {
        Connection connection = dataSource.getConnection();
        try {
            return gdp.create(connection, gamesPlayed);
        }
        finally {
            connection.close();
        }
    }

    @Override
    public GamesPlayed retrieveByID(long gamePlayedID) throws DAOException, SQLException
    {
        Connection connection = dataSource.getConnection();
        try {
            return gdp.retrieveID(connection, gamePlayedID);
        }
        finally {
            connection.close();
        }
    }

    @Override
    public List<GamesPlayed> retrieveByPlayerGameID(long playerID, long gameID) throws DAOException, SQLException
    {
        Connection connection = dataSource.getConnection();
        try {
            return gdp.retrieveByPlayerGameID(connection, playerID, gameID);
        }
        finally {
            connection.close();
        }
    }

    @Override
    public List<GamesPlayed> retrieveByGame(long gameID) throws DAOException, SQLException
    {
        Connection connection = dataSource.getConnection();
        try {
            return gdp.retrieveByGame(connection, gameID);
        }
        finally {
            connection.close();
        }
    }

    @Override
    public List<GamesPlayed> retrieveByPlayer(long playerID) throws DAOException, SQLException
    {
        Connection connection = dataSource.getConnection();
        try {
            return gdp.retrieveByPlayer(connection, playerID);
        }
        finally {
            connection.close();
        }

    }

    @Override
    public int update(GamesPlayed gamesPlayed) throws DAOException, SQLException
    {
        Connection connection = dataSource.getConnection();
        try {
            return gdp.update(connection, gamesPlayed);
        }
        finally {
            connection.close();
        }
    }

    @Override
    public int delete(long gamePlayedID) throws DAOException, SQLException
    {
        Connection connection = dataSource.getConnection();
        try {
            return gdp.delete(connection, gamePlayedID);
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
            return gdp.count(connection);
        }
        finally {
            connection.close();
        }
    }

}
