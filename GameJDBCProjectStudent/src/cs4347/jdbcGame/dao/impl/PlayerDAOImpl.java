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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cs4347.jdbcGame.dao.CreditCardDAO;
import cs4347.jdbcGame.dao.PlayerDAO;
import cs4347.jdbcGame.entity.CreditCard;
import cs4347.jdbcGame.entity.Player;
import cs4347.jdbcGame.util.DAOException;

public class PlayerDAOImpl implements PlayerDAO
{

    private static final String insertSQL = "INSERT INTO Player (firstName, lastName, join_date, email ) VALUES (?, ?, ?, ?);";

    @Override
    public Player create(Connection connection, Player player) throws SQLException, DAOException
    {
        if (player.getId() != null) {
            throw new DAOException("Trying to insert Player with NON-NULL ID");
        }

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, player.getFirstName());
            ps.setString(2, player.getLastName());
            ps.setDate(3, new java.sql.Date(player.getJoinDate().getTime()));
            ps.setString(4, player.getEmail());
            ps.executeUpdate();

            // Copy the assigned ID to the customer instance.
            ResultSet keyRS = ps.getGeneratedKeys();
            keyRS.next();
            int lastKey = keyRS.getInt(1);
            player.setId((long) lastKey);
            return player;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    }

    
    final static String selectSQL = "select p.id as pid, p.firstName, p.lastName, p.join_date, p.email, "
  		  + "c.id as cid, c.ccName, c.ccNumber, c.securityCode, c.expDate "
  		  + "from Player as p left join CreditCard as c on Player_id = p.id where p.id = ?";
    @Override
    public Player retrieve(Connection connection, Long playerID) throws SQLException, DAOException
    {
    	 if (playerID == null) {
             throw new DAOException("Trying to retrieve Player with NULL ID");
         }

         PreparedStatement ps = null;
         try {
             ps = connection.prepareStatement(selectSQL);
             ps.setLong(1, playerID);
             ResultSet rs = ps.executeQuery();
             if(rs.next())
             {
             	Player player = extractFromRSWithCC(rs);
                 return player;
             }
             return null;
             
         }
         finally {
             if (ps != null && !ps.isClosed()) {
                 ps.close();
             }
         }
    }

    
    final static String updateSQL = "UPDATE Player SET firstName = ?, lastName = ?, join_date = ?, email = ? "
	        + "WHERE id = ?;";
    

    @Override
    public int update(Connection connection, Player player) throws SQLException, DAOException
    {
    	CreditCardDAO ccDAO = new CreditCardDAOImpl();
    	
    	Long id = player.getId();
        if (id == null) {
            throw new DAOException("Trying to update player with NULL ID");
        }

        int rows;

        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(updateSQL);
            ps.setString(1, player.getFirstName());
            ps.setString(2, player.getLastName());
            ps.setDate(3, new java.sql.Date(player.getJoinDate().getTime()));
            ps.setString(4, player.getEmail());
            ps.setLong(5, player.getId());
            
            rows = ps.executeUpdate();
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
        
        for(CreditCard card: player.getCreditCards())
        {
        	if (card.getId() != null) {
        		ccDAO.update(connection, card);
        	} else {
        		ccDAO.create(connection, card, player.getId());
        	}
        }
        
        return rows;
    }


    
    final static String deleteSQL = "DELETE FROM Player WHERE id = ?;";
    public int delete(Connection connection, Long playerID) throws SQLException, DAOException
    {
    	
    	if (playerID == null) {
			throw new DAOException("Trying to delete PLAYER with NULL ID");
		}

		PreparedStatement ps = null;
		try {
			ps = connection.prepareStatement(deleteSQL);
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

 
    
    final static String countSQL = "select count(*) from Player";
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

    final static String selectJoinDateSQL = "select p.id as pid, p.firstName, p.lastName, p.join_date, p.email, "
    		  + "c.id as cid, c.ccName, c.ccNumber, c.securityCode, c.expDate "
    		  + "from Player as p left join CreditCard as c on Player_id = p.id where p.join_date between ? and ?";    
    
    
    @Override
    public List<Player> retrieveByJoinDate(Connection connection, Date start, Date end)
            throws SQLException, DAOException
    {
    	List<Player> result = new ArrayList<Player>();
    	
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(selectJoinDateSQL);
            ps.setDate(1, new java.sql.Date(start.getTime()));
            ps.setDate(2, new java.sql.Date(end.getTime()));
            ResultSet rs = ps.executeQuery();
            while(rs.next())
            {
            	result.add(extractFromRSWithCC(rs));
            }

            return result;
        }
        finally {
            if (ps != null && !ps.isClosed()) {
                ps.close();
            }
        }
    	
    }


	private Player extractFromRSWithCC(ResultSet rs) throws SQLException
	{
		Player player = new Player();
        player.setId(rs.getLong("pid"));
        player.setFirstName(rs.getString("firstName"));
        player.setLastName(rs.getString("lastName"));
        player.setJoinDate(rs.getDate("join_date"));
        player.setEmail(rs.getString("email"));
        

        return player;
    }
		
	

}
