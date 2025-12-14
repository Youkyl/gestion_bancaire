package com.gsb.gestion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gsb.gestion.config.DatabaseConnection;
import com.gsb.gestion.model.Comptes;
import com.gsb.gestion.model.TypeDeCompte;

public class ComptesDao {

    private final Connection connection;

    public ComptesDao() throws SQLException {

        this.connection = DatabaseConnection.getConnection();
    }

   

    public void insertCompte(Comptes compte) throws SQLException {

        String sql = """
            INSERT INTO compte (numero_compte, type, solde, duree_blocage)
            VALUES (?, ?::type_compte, ?, ?)
        """;

        try (
             PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, compte.getNumeroDeCompte());
            ps.setString(2, compte.getType().name());
            ps.setDouble(3, compte.getSolde());

            if (compte.getType() == TypeDeCompte.EPARGNE) {
                ps.setInt(4, compte.getDureeDeblocage());
            } else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur insertion compte", e);
        }
    }



    public List<Comptes> selectAll() {

        List<Comptes> comptes = new ArrayList<>();
        String sql = "SELECT * FROM compte";

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                comptes.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lecture comptes", e);
        }

        return comptes;
    }

    


    public Comptes selectAccByNum(String numeroDeCompte) {

        String sql = "SELECT * FROM compte WHERE numero_compte = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, numeroDeCompte);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Compte non trouvé", e);
        }

        return null;
    }

    


    public void updateSoldeAcc(String numeroDeCompte, double newSolde) {

        String sql = """
            UPDATE compte
            SET solde = ?
            WHERE numero_compte = ?
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, newSolde);
            ps.setString(2, numeroDeCompte);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur mise à jour solde", e);
        }
    }

    

    
    private Comptes mapRow(ResultSet rs) throws SQLException {

        Comptes compte = new Comptes();
        compte.setNumeroDeCompte(rs.getString("numero_compte"));
        compte.setSolde(rs.getDouble("solde"));
        compte.setType(TypeDeCompte.valueOf(rs.getString("type")));

        int duree = rs.getInt("duree_blocage");
        if (!rs.wasNull()) {
            compte.setDureeDeblocage(duree);
        }

        return compte;
    }
}
