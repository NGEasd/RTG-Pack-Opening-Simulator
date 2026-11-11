package edu.ubb.rtgsimulator.backend.repository.jdbc;

import edu.ubb.rtgsimulator.backend.utils.PropertyProvider;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Repository
@Slf4j
@Lazy
@RequiredArgsConstructor
public class ConnectionManager {
    private List<Connection> connections;
    private final PropertyProvider provider;
    private int counter = 20;

    @PostConstruct
    private void initializePool() {
        connections = new LinkedList<Connection>();
        try {
            Class.forName(provider.getProperty("JDBC_DRIVER"));
        } catch (ClassNotFoundException e) {
            log.error("Error connecting to database", e);
            // throw new RepositoryException("Error connecting to database", e);
        }
        for (int i = 0; i < Integer.parseInt(provider.getProperty("POOL_SIZE")); i++) {
            try {
                connections.add(DriverManager.getConnection(provider.getProperty("JDBC_URL"), provider.getProperty("JDBC_USER"), provider.getProperty("JDBC_PASSWORD")));
            } catch (SQLException e) {
                log.error("Error creating connection pool", e);
                //throw new RepositoryException("Error creating connection pool", e);
            }
        }
        log.info("Connection pool initialized");
    }

    public Connection getConnection() {
        if (!connections.isEmpty()) {
            return connections.removeFirst();
        }
        return null;
    }

    public void returnConnection(Connection c) {
        connections.add(c);
    }
}
