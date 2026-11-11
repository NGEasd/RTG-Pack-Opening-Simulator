package edu.ubb.rtgsimulator.backend.repository.jdbc;

import edu.ubb.rtgsimulator.backend.modell.card.PlayerCard;
import edu.ubb.rtgsimulator.backend.modell.card.Position;
import edu.ubb.rtgsimulator.backend.modell.card.Rarity;
import edu.ubb.rtgsimulator.backend.modell.card.Stat;
import edu.ubb.rtgsimulator.backend.modell.user.Role;
import edu.ubb.rtgsimulator.backend.modell.user.User;
import edu.ubb.rtgsimulator.backend.repository.UserRepository;
import edu.ubb.rtgsimulator.backend.repository.exception.CardNotOwnedException;
import edu.ubb.rtgsimulator.backend.repository.exception.RepositoryException;
import edu.ubb.rtgsimulator.backend.repository.exception.UsernameAlreadyExistsException;
import edu.ubb.rtgsimulator.backend.service.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("JDBC")
@Lazy
@Slf4j
public class JDBC_UserRepository implements UserRepository {
    @Autowired
    private ConnectionManager connectionManager;
    @Override
    public User createDefaultUser(User user) {
        Connection connection = null;
        try {
            // check if the username is occupied
            String username = user.getUsername();
            if (usernameAlreadyExist(username)) {
                log.error("Cannot create user: username already exists!");
                throw new UsernameAlreadyExistsException(username);
            }

            // insert user in db
            connection = connectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO user(uuid, username, password, role, balance) VALUES (?,?,?,?,?);",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, user.getUuid());
            ps.setString(2, username);
            ps.setString(3, user.getPassword());
            ps.setString(4, Role.DEFAULT.toString());
            ps.setInt(5, user.getBalance());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            user.setUserID(rs.getLong(1));

        } catch (SQLException e) {
            log.error("Error creating user!", e);
            throw new RepositoryException("Error creating user!");

        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
        return user;
    }

    @Override
    public User getById(Long id) {
        Connection connection = connectionManager.getConnection();
        User user = new User();
        try {
            // check if the ID exists
            if (!userExists(id)) {
                throw new UserNotFoundException("User with ID not found!");
            }


            PreparedStatement ps = connection.prepareStatement("SELECT * FROM user WHERE user_id = (?)");
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();
            rs.next();

            user = mapResultSetToUser(rs);

        } catch (SQLException e) {
            log.error("Error creating user!", e);
            throw new RepositoryException("Error creating user!");

        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
        return user;
    }

    @Override
    public User getByUsername(String name) {
        Connection connection = null;
        User user = new User();
        try {
            // insert user in db
            connection = connectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM user WHERE username = (?);");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();

            rs.next();

            user = mapResultSetToUser(rs);

        } catch (SQLException e) {
            log.error("Error creating user!", e);
            throw new RepositoryException("Error creating user!");

        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
        return user;
    }

    @Override
    public void update(User user) {
        Connection connection = connectionManager.getConnection();
        try {
            String sql = "UPDATE user SET balance=? WHERE user_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, user.getBalance());
            ps.setLong(2, user.getUserID());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                log.warn("No user found with ID: " + user.getUserID() + " for update.");
                throw new RepositoryException("User with ID " + user.getUserID() + " not found for update.");
            } else {
                log.info("User with ID: " + user.getUserID() + " updated successfully. Affected rows: " + affectedRows);
            }

        } catch (SQLException e) {
            log.error("Error updating user! ", e);
            throw new RepositoryException("Error updating user", e);
        } finally {
            if (connection != null) connectionManager.returnConnection(connection);
        }
    }

    @Override
    public void delete(User user) {

    }

    @Override
    public void addCardToInventory(User user, PlayerCard card) {
        Connection connection = connectionManager.getConnection();
        try {
            // insert user in db
            PreparedStatement ps = connection.prepareStatement("INSERT INTO card_owner(user_id, card_id) VALUES (?,?);",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setLong(1, user.getUserID());
            ps.setLong(2, card.getPlayerID());

            ps.execute();

        } catch (SQLException e) {
            log.error("Error inserting card!", e);
            throw new RepositoryException("Error inserting card!");

        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }

    @Override
    public void deleteCardFromInventory(User user, PlayerCard card) {
        Connection connection = null;
        try {
            if (!doesUserOwnCard(user, card)){
                throw new CardNotOwnedException("Card not owned by user, cannot sell!");
            }

            String sql = "DELETE FROM card_owner WHERE user_id = ? AND card_id = ? LIMIT 1";

            connection = connectionManager.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setLong(1, user.getUserID());
            ps.setLong(2, card.getPlayerID());

            int affectedRows = ps.executeUpdate();

            if (affectedRows == 0) {
                log.warn("Failed to delete card {} from inventory for user {}. Card not found.", card.getPlayerID(), user.getUserID());
                throw new RepositoryException("Card " + card.getPlayerID() + " not found in user " + user.getUserID() + "'s inventory for deletion.");
            } else {
                log.info("One instance of card {} deleted from inventory for user {}. Affected rows: {}", card.getPlayerID(), user.getUserID(), affectedRows);
            }

        } catch (SQLException e) {
            log.error("Error deleting card {} from inventory for user {}: {}", card.getPlayerID(), user.getUserID(), e.getMessage(), e);
            throw new RepositoryException("Error deleting card from inventory!", e);
        } finally {
            if (connection != null) connectionManager.returnConnection(connection);
        }
    }

    @Override
    public List<PlayerCard> getInventoryPlayers(User user) {
        Connection connection = connectionManager.getConnection();
        List<PlayerCard> userCards = new ArrayList<>();

        try {
            String sql = "SELECT c.player_id, c.name, c.position, c.rarity, c.price, c.overall, c.stat_id, " +
                    "cs.pace, cs.shooting, cs.dribbling, cs.passing, cs.defending, cs.keeper_abilities " +
                    "FROM card_owner co " +
                    "JOIN card c ON co.card_id = c.player_id " +
                    "JOIN card_stat cs ON c.stat_id = cs.stat_id " +
                    "WHERE co.user_id = ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, user.getUserID());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                userCards.add(mapResultSetToPlayerCardWithStats(rs));
            }

        } catch (SQLException e) {
            log.error("Error retrieving inventory cards for user ID {}: {}", user.getUserID(), e.getMessage(), e);
            throw new RepositoryException("Error retrieving user inventory cards!", e);
        } finally {
            if (connection != null) connectionManager.returnConnection(connection);
        }
        return userCards;
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUuid(rs.getString("uuid"));
        user.setUserID(rs.getLong("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setRole(Role.valueOf(rs.getString("role").toUpperCase()));
        user.setBalance(rs.getInt("balance"));

        return user;
    }

    private PlayerCard mapResultSetToPlayerCardWithStats(ResultSet rs) throws SQLException {
        // card
        PlayerCard card = new PlayerCard();
        card.setPlayerID(rs.getLong("player_id"));
        card.setName(rs.getString("name"));
        card.setPosition(Position.valueOf(rs.getString("position")));
        card.setRarity(Rarity.valueOf(rs.getString("rarity")));
        card.setPrice(rs.getInt("price"));
        card.setOverall(rs.getInt("overall"));

        // stat
        Stat stat = new Stat();
        stat.setPace(rs.getInt("pace"));
        stat.setShooting(rs.getInt("shooting"));
        stat.setDribbling(rs.getInt("dribbling"));
        stat.setPassing(rs.getInt("passing"));
        stat.setDefending(rs.getInt("defending"));
        stat.setKeeperAbilities(rs.getInt("keeper_abilities"));

        card.setStats(stat);

        return card;
    }

    private boolean doesUserOwnCard(User user, PlayerCard card) {
        Connection connection = connectionManager.getConnection();
        String sql = "SELECT 1 FROM card_owner WHERE user_id = ? AND card_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setLong(1, user.getUserID());
            ps.setLong(2, card.getPlayerID());

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            log.error("Error: cannot get the users card from DB! (SQL Error!)", e);
            throw new RepositoryException("Error: cannot get the users card from DB!");

        } finally {
            connectionManager.returnConnection(connection);
        }
    }

    private boolean usernameAlreadyExist(String username) {
        Connection connection = connectionManager.getConnection();
        String sql = "SELECT 1 FROM user WHERE username = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            return rs.next();


        } catch (SQLException e){
            log.error("Error: cannot get the user from username (SQL error)!", e);
            throw new RepositoryException("Error: cannot get the user from username!");
        } finally {
            connectionManager.returnConnection(connection);
        }
    }

    private boolean userExists(Long id) throws SQLException {
        Connection connection = connectionManager.getConnection();
        String sql = "SELECT 1 FROM user WHERE user_id = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } finally {
            connectionManager.returnConnection(connection);
        }
    }
}
