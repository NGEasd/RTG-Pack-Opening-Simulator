package edu.ubb.rtgsimulator.backend.repository.jdbc;

import edu.ubb.rtgsimulator.backend.modell.pack.Pack;
import edu.ubb.rtgsimulator.backend.repository.PackRepository;
import edu.ubb.rtgsimulator.backend.repository.exception.RepositoryException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Profile("JDBC")
@Lazy
@Slf4j
public class JDBC_PackRepository implements PackRepository {
    @Autowired
    private ConnectionManager connectionManager;
    @Override
    public Pack createPack(Pack pack) {
        return null; // we cannot create packs
    }

    @Override
    public Pack getById(Long id) {
        Connection connection = connectionManager.getConnection();
        Pack pack = new Pack();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM pack WHERE pack_id = (?)");
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();
            rs.next();

            pack = mapResultSetToPack(rs);

        } catch (SQLException e) {
            log.error("Error creating user!", e);
            throw new RepositoryException("Error creating user!");

        } finally {
            if (connection != null) {
                connectionManager.returnConnection(connection);
            }
        }
        return pack;
    }

    @Override
    public void updatePack(Pack pack) {

    }

    @Override
    public List<Pack> getAllPacks() {
        Connection connection = connectionManager.getConnection();
        List<Pack> packs = new ArrayList<>();

        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM pack");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                packs.add(mapResultSetToPack(rs));
            }

        } catch (SQLException e) {
            log.error("Error retrieving all packs from database!", e);
            throw new RepositoryException("Error retrieving all packs!", e);

        } finally {
            if (connection != null) connectionManager.returnConnection(connection);
        }
        return packs;
    }

    private Pack mapResultSetToPack(ResultSet rs) throws SQLException {
        Pack pack = new Pack();
        pack.setPackID(rs.getLong("pack_id"));
        pack.setName(rs.getString("name"));
        pack.setDescription(rs.getString("description"));
        pack.setMinimumOverall(rs.getInt("minimum_overall"));
        pack.setPrice(rs.getInt("price"));

        return pack;
    }
}
