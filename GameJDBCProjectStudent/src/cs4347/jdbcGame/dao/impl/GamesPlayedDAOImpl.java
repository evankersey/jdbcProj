/* NOTICE: All materials provided by this project, and materials derived 
 * from the project, are the property of the University of Texas. 
 * Project materials, or those derived from the materials, cannot be placed 
 * into publicly accessible locations on the web. Project materials cannot 
 * be shared with other project teams. Making project materials publicly 
 * accessible, or sharing with other project teams will result in the 
 * failure of the team responsible and any team that uses the shared materials. 
 * Sharing project materials or using shared materials will also result 
 * in the reporting of all team members for academic dishonesty. 
 */
package cs4347.jdbcGame.dao.impl;

import java.sql.*;
import java.util.List;

import cs4347.jdbcGame.dao.GamesPlayedDAO;
import cs4347.jdbcGame.entity.GamesPlayed;
import cs4347.jdbcGame.util.DAOException;

public class GamesPlayedDAOImpl implements GamesPlayedDAO
{
/*
private Long id;
    private Long playerID;
    private Long gameID;
    private Date timeFinished;
    private int score;
 */
    @Override
    public GamesPlayed create(Connection connection, GamesPlayed gamesPlayed) throws SQLException, DAOException
    {
        final String insertSQL = "INSERT INTO gamesPlayed(playerID, gameID, timeFinished, score) "
                + "VALUES(?,?,?,?);";

        if (gamesPlayed.getId() != null) {
            throw new DAOException("Trying to insert gamesPlayed with NON-NULL ID");
        }
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);


            ps.setLong(1, gamesPlayed.getPlayerID());
            ps.setLong(2, gamesPlayed.getGameID());
            ps.setDate(3, (Date) gamesPlayed.getTimeFinished());
            ps.setLong(4, gamesPlayed.getScore());
            ps.executeUpdate();

            // Copy the assigned ID to the game instance. //todo what is this??? insert what key where?
            ResultSet keyRS = ps.getGeneratedKeys();
            keyRS.next();
            int lastKey = keyRS.getInt(1);
            gamesPlayed.setId((long) lastKey);
            return gamesPlayed;
        } finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }

    @Override
    public GamesPlayed retrieveID(Connection connection, Long gamePlayedID) throws SQLException, DAOException
    {
        return null;
    }

    @Override
    public List<GamesPlayed> retrieveByPlayerGameID(Connection connection, Long playerID, Long gameID)
            throws SQLException, DAOException
    {
        return null;
    }

    @Override
    public List<GamesPlayed> retrieveByPlayer(Connection connection, Long playerID) throws SQLException, DAOException
    {
        return null;
    }

    @Override
    public List<GamesPlayed> retrieveByGame(Connection connection, Long gameID) throws SQLException, DAOException
    {
        return null;
    }

    @Override
    public int update(Connection connection, GamesPlayed gamesPlayed) throws SQLException, DAOException
    {
        return 0;
    }

    @Override
    public int delete(Connection connection, Long gamePlayedID) throws SQLException, DAOException
    {
        return 0;
    }

    @Override
    public int count(Connection connection) throws SQLException, DAOException
    {
        return 0;
    }

}
