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

import cs4347.jdbcGame.dao.GamesOwnedDAO;
import cs4347.jdbcGame.entity.GamesOwned;
import cs4347.jdbcGame.util.DAOException;

public class GamesOwnedDAOImpl implements GamesOwnedDAO {

    @Override
    public GamesOwned create(Connection connection, GamesOwned gamesOwned) throws SQLException, DAOException {
        final String insertSQL = "INSERT INTO GamesOwned(id, playerID, gameID, purchaseDate, purchasePrice) "
                + "VALUES(?,?,?,?,?);";

        if (gamesOwned.getId() != null) {
            throw new DAOException("Trying to insert gamesOwned with NON-NULL ID");
        }
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

            ps.setLong(1, gamesOwned.getPlayerID());
            ps.setLong(2, gamesOwned.getGameID());
            ps.setDate(3, (Date) gamesOwned.getPurchaseDate());
            ps.setFloat(4, gamesOwned.getPurchasePrice());
            ps.executeUpdate();

            // Copy the assigned ID to the game instance.
            ResultSet keyRS = ps.getGeneratedKeys();
            keyRS.next();
            int lastKey = keyRS.getInt(1);
            gamesOwned.setId((long) lastKey);
            return gamesOwned;
        } finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }

    @Override
    public GamesOwned retrieveID(Connection connection, Long gamesOwnedID) throws SQLException, DAOException {
        final String selectQuery = "SELECT id, playerID, gameID, purchaseDate, purchasePrice "
                + "FROM GamesOwned where id = ?";

        if (gamesOwnedID == null) {
            throw new DAOException("Trying to retrieve gamesOwned with NULL ID");
        }

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(selectQuery);
            ps.setLong(1, gamesOwnedID);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                return null;
            }

            GamesOwned gamesOwned = new GamesOwned();
            gamesOwned.setGameID(rs.getLong("id"));
            gamesOwned.setPlayerID(rs.getLong("playerID"));
            gamesOwned.setGameID(rs.getLong("gameID"));
            gamesOwned.setPurchaseDate(rs.getDate("purchaseDate"));
            gamesOwned.setPurchasePrice(rs.getFloat("purchasePrice"));
            return gamesOwned;
        } finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }

        @Override
        public GamesOwned retrievePlayerGameID (Connection connection, Long playerID, Long gameID)
            throws SQLException, DAOException
        {
            //fixme
            return null;
        }

        @Override
        public List<GamesOwned> retrieveByGame (Connection connection, Long gameID) throws SQLException, DAOException
        {
            //fixme
            return null;
        }

        @Override
        public List<GamesOwned> retrieveByPlayer (Connection connection, Long playerID) throws
        SQLException, DAOException
        {
            //fixme
            return null;
        }

        @Override
        public int update (Connection connection, GamesOwned gamesOwned) throws SQLException, DAOException
        {
            //fixme
            return 0;
        }

        @Override
        public int delete (Connection connection, Long gameOwnedID) throws SQLException, DAOException
        {
            //fixme
            return 0;
        }

        @Override
        public int count (Connection connection) throws SQLException, DAOException
        {
            //fixme
            return 0;
        }

    }
