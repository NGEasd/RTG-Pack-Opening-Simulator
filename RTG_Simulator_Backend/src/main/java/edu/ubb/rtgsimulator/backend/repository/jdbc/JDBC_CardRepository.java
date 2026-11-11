package edu.ubb.rtgsimulator.backend.repository.jdbc;

import edu.ubb.rtgsimulator.backend.controller.dto.cards.CardReqParamsDTO;
import edu.ubb.rtgsimulator.backend.modell.card.PlayerCard;
import edu.ubb.rtgsimulator.backend.modell.card.Position;
import edu.ubb.rtgsimulator.backend.modell.card.Rarity;
import edu.ubb.rtgsimulator.backend.modell.card.Stat;
import edu.ubb.rtgsimulator.backend.repository.CardRepository;
import edu.ubb.rtgsimulator.backend.repository.exception.CardNotFoundException;
import edu.ubb.rtgsimulator.backend.repository.exception.RepositoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("JDBC")
@Lazy
@Slf4j
public class JDBC_CardRepository implements CardRepository {
    @Autowired
    private ConnectionManager connectionManager;
    @Override
    public PlayerCard create(PlayerCard card) {
        Connection connection = connectionManager.getConnection();
        log.info(card.toString());
        try {
            // stat
            Stat stat = card.getStats();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO card_stat(pace, shooting, dribbling, passing, " +
                            " defending, keeper_abilities) VALUES (?,?,?,?,?,?);",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, stat.getPace());
            ps.setInt(2, stat.getShooting());
            ps.setInt(3, stat.getDribbling());
            ps.setInt(4, stat.getPassing());
            ps.setInt(5, stat.getDefending());
            ps.setInt(6, stat.getKeeperAbilities());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int statID = rs.getInt(1);

            // card
            ps = connection.prepareStatement("INSERT INTO card(name, position, rarity, price, overall, stat_id) VALUES (?,?,?,?,?,?);",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, card.getName());
            ps.setString(2, card.getPosition().toString());
            ps.setString(3, card.getRarity().toString());
            ps.setInt(4, card.getPrice());
            ps.setInt(5, card.getOverall());
            ps.setInt(6, statID);
            ps.execute();

            rs = ps.getGeneratedKeys();
            rs.next();
            card.setPlayerID(rs.getLong(1));
        } catch (SQLException e) {
            log.error("Error creating user", e);
            throw new RepositoryException("Error creating user");

        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
        return card;
    }

    @Override
    public PlayerCard getById(Long id) {
        Connection connection = connectionManager.getConnection();
        try {

            // get card
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM card WHERE player_id=?");
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();
            rs.next();

            PlayerCard card = new PlayerCard();
            card.setPlayerID(rs.getLong("player_id"));
            card.setName(rs.getString("name"));
            card.setPosition(Position.valueOf(rs.getString("position")));
            card.setRarity(Rarity.valueOf(rs.getString("rarity")));
            card.setOverall(rs.getInt("overall"));
            card.setPrice(rs.getInt("price"));

            // get stat
            int statID = rs.getInt("stat_id");

            ps = connection.prepareStatement("SELECT * FROM card_stat WHERE stat_id=?");
            ps.setLong(1, statID);


            rs = ps.executeQuery();
            rs.next();

            Stat stat = new Stat();
            stat.setPace(rs.getInt("pace"));
            stat.setShooting(rs.getInt("shooting"));
            stat.setDribbling(rs.getInt("dribbling"));
            stat.setPassing(rs.getInt("passing"));
            stat.setDefending(rs.getInt("defending"));
            stat.setKeeperAbilities(rs.getInt("keeper_abilities"));

            // connect together
            card.setStats(stat);

            return card;
        } catch (SQLException e) {
            log.error("Error finding by ID", e);
            throw new RepositoryException("Error finding card by ID");
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }

    @Override
    public void update(PlayerCard card) {
        if (!cardExists(card.getPlayerID())) throw new CardNotFoundException("The updating card not exists!");

        Connection connection = connectionManager.getConnection();
        try {
            String sql = "UPDATE card SET name = ?, position = ?, rarity = ?, price = ?, overall = ? WHERE player_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, card.getName());
            ps.setString(2, card.getPosition().toString());
            ps.setString(3, card.getRarity().toString());
            ps.setInt(4, card.getPrice());
            ps.setInt(5, card.getOverall());
            ps.setLong(6, card.getPlayerID());

            ps.executeUpdate();

        } catch (SQLException e) {
            log.error("Error updating player", e);
            throw new RepositoryException("Error updating player");
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }

    @Override
    public void delete(PlayerCard card) {

    }

    @Override
    public List<PlayerCard> getAllCards() {
        Connection connection = connectionManager.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<PlayerCard> cards = new ArrayList<>();

        try {
            String sql = "SELECT c.*, s.pace, s.shooting, s.dribbling, s.passing, s.defending, s.keeper_abilities " +
                    "FROM card c JOIN card_stat s ON c.stat_id = s.stat_id";

            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                cards.add(mapResultSetToPlayerCard(rs));
            }

            return cards;
        } catch (SQLException e) {
            log.error("Error retrieving all cards", e);
            throw new RepositoryException("Error retrieving all cards", e);
        } catch (IllegalArgumentException e) {
            log.error("Error parsing enum value for one or more cards", e);
            throw new RepositoryException("Invalid enum value in database for one or more cards", e);
        } finally {
            connectionManager.returnConnection(connection);
        }
    }

    @Override
    public List<PlayerCard> getFilteredCards(CardReqParamsDTO params) {
        Connection connection = connectionManager.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<PlayerCard> cards = new ArrayList<>();
        List<Object> queryParams = new ArrayList<>();

        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT c.*, s.pace, s.shooting, s.dribbling, s.passing, s.defending, s.keeper_abilities ")
            .append(" FROM card c JOIN card_stat s ON c.stat_id = s.stat_id ")
            .append("WHERE 1 = 1 ");

            Integer minOVR = params.getMinimOVR();
            if (minOVR != null) {
                sql.append("AND c.overall >= ? ");
                queryParams.add(minOVR);
            }

            Integer maxOVR = params.getMaxOVR();
            if (maxOVR!= null) {
                sql.append("AND c.overall <= ? ");
                queryParams.add(maxOVR);
            }

            Rarity rarity = params.getRarity();
            if (rarity != null) {
                sql.append("AND c.rarity = ? ");
                queryParams.add(rarity.name());
            }

            Position position = params.getPosition();
            if (position != null) {
                sql.append("AND c.position = ? ");
                queryParams.add(position.name());
            }

            Boolean sortAcc = params.getSortByAcc();
            if (sortAcc != null) {
                if (!sortAcc) sql.append("ORDER BY c.overall DESC ");
                else sql.append("ORDER BY c.overall ASC ");
            } else {
                sql.append("ORDER BY  c.overall DESC ");
            }

            ps = connection.prepareStatement(sql.toString());
            for (int i = 0; i < queryParams.size(); i++) {
                Object param = queryParams.get(i);
                if (param instanceof Integer) {
                    ps.setInt(i + 1, (Integer) param);
                } else if (param instanceof String) {
                    ps.setString(i + 1, (String) param);
                }
            }

            rs = ps.executeQuery();

            while (rs.next()) {
                cards.add(mapResultSetToPlayerCard(rs));
            }

            return cards;
        } catch (SQLException e) {
            log.error("Error retrieving all cards", e);
            throw new RepositoryException("Error retrieving all cards", e);
        } catch (IllegalArgumentException e) {
            log.error("Error parsing enum value for one or more cards", e);
            throw new RepositoryException("Invalid enum value in database for one or more cards", e);
        } finally {
            connectionManager.returnConnection(connection);
        }
    }

    private PlayerCard mapResultSetToPlayerCard(ResultSet rs) throws SQLException {
        PlayerCard card = new PlayerCard();
        card.setPlayerID(rs.getLong("player_id"));
        card.setName(rs.getString("name"));

        card.setPosition(Position.valueOf(rs.getString("position").toUpperCase()));
        card.setRarity(Rarity.valueOf(rs.getString("rarity").toUpperCase()));

        card.setOverall(rs.getInt("overall"));
        card.setPrice(rs.getInt("price"));

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

    private boolean cardExists(Long cardID) {
        Connection connection = connectionManager.getConnection();

        try {
            String sql = "SELECT 1 FROM card WHERE player_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setLong(1, cardID);

            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RepositoryException("Error in card exisiting verification", e);
        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
    }
}
