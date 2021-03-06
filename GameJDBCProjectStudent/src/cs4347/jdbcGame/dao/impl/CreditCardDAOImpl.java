package cs4347.jdbcGame.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cs4347.jdbcGame.dao.CreditCardDAO;
import cs4347.jdbcGame.entity.CreditCard;
import cs4347.jdbcGame.entity.Player;
import cs4347.jdbcGame.util.DAOException;

public class CreditCardDAOImpl implements CreditCardDAO
{
    private static final String insertSQL = "INSERT INTO CreditCard (ccName, ccNumber, expDate, securityCode, Player_id) "
            + "VALUES(?,?,?,?,?);";
    @Override
    public CreditCard create(Connection connection, CreditCard creditCard, Long playerID)
            throws SQLException, DAOException
    {
        if (creditCard.getId() != null)
        {
            throw new DAOException("Trying to insert CreditCard with NON-NULL ID");
        }

        creditCard.setPlayerID(playerID);

        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, creditCard.getCcName());
            ps.setString(2, creditCard.getCcNumber());
            ps.setString(3, creditCard.getExpDate());
            ps.setInt(4, creditCard.getSecurityCode());
            ps.setLong(5, playerID);

            ps.executeUpdate();


            // Copy the assigned ID to the game instance.
            ResultSet keyRS = ps.getGeneratedKeys();
            keyRS.next();
            int lastKey = keyRS.getInt(1);
            creditCard.setId((long) lastKey);
            return creditCard;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }



    final static String retriveQuery = "SELECT id, player_id, ccName, ccNumber, securityCode, expDate FROM CreditCard where id = ?;";



    public CreditCard retrieve(Connection connection, Long ccID) throws SQLException, DAOException
    {
        if (ccID == null)
        {
            throw new DAOException("Trying to retrieve Player with NULL ID");
        }

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(retriveQuery);
            ps.setLong(1, ccID);
            ResultSet rs = ps.executeQuery();

            if(!rs.next())
            {
                return null;
            }
            CreditCard cc = extractFromRS(rs);

            return cc;

        }
        finally
        {
            if (ps != null && !ps.isClosed())
            {
                ps.close();
            }
        }
    }



    final static String retrieveCreditCardsForPlayerQuery = "SELECT * FROM CreditCard where player_id = ?";




    public List<CreditCard> retrieveCreditCardsForPlayer(Connection connection, Long playerID)
            throws SQLException, DAOException
    {
        List<CreditCard> result = new ArrayList<CreditCard>();

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(retrieveCreditCardsForPlayerQuery);
            ps.setLong(1, playerID);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                CreditCard cc = extractFromRS(rs);
                result.add(cc);
            }
            return result;
        }

        finally
        {
            if (ps != null && !ps.isClosed())
            {
                ps.close();
            }
        }
    }



    final static String updateQuery = "UPDATE CreditCard SET Player_id = ?, ccName = ?, ccNumber = ?, securityCode = ?, expDate = ? WHERE id = ?;";
    @Override
    public int update(Connection connection, CreditCard creditCard) throws SQLException, DAOException
    {
        Long id = creditCard.getId();
        if (id == null)
        {
            throw new DAOException("Trying to update CreditCard with NULL ID");
        }
        int rows;
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(updateQuery);
            ps.setLong(1, creditCard.getPlayerID());
            ps.setString(2, creditCard.getCcName());
            ps.setString(3, creditCard.getCcNumber());
            ps.setInt(4, creditCard.getSecurityCode());
            ps.setString(5, creditCard.getExpDate());
            ps.setLong(6, creditCard.getId());

            rows = ps.executeUpdate();
            return rows;
        }



        finally
        {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }

    }


    final static String deleteQuery = "DELETE from CreditCard where id = ?;";

    @Override
    public int delete(Connection connection, Long ccID) throws SQLException, DAOException
    {
        if (ccID == null) {
            throw new DAOException("Trying to delete CreditCard with NULL ID");
        }

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(deleteQuery);
            ps.setLong(1, ccID);

            int rows = ps.executeUpdate();
            return rows;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }

    }

    @Override
    public int deleteForPlayer(Connection connection, Long playerID) throws SQLException, DAOException
    {
        if (playerID == null) {
            throw new DAOException("Trying to delete CreditCard with NULL ID");
        }

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(deleteQuery);
            ps.setLong(1, playerID);

            int rows = ps.executeUpdate();
            return rows;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }

    final static String countSQL = "select count(*) from CreditCard";

    @Override
    public int count(Connection connection) throws SQLException, DAOException
    {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(countSQL);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new DAOException("No Count Returned");
            }
            int count = rs.getInt(1);
            return count;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }

    private CreditCard extractFromRS(ResultSet rs) throws SQLException
    {
        CreditCard cc = new CreditCard();
        cc.setId(rs.getLong("id"));
        cc.setPlayerID(rs.getLong("Player_id"));
        cc.setCcName(rs.getString("ccName"));
        cc.setCcNumber(rs.getString("ccNumber"));
        cc.setSecurityCode(rs.getInt("securityCode"));
        cc.setExpDate(rs.getString("expDate"));

        return cc;
    }







}