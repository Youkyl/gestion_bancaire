package com.gsb.gestion.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gsb.gestion.config.DatabaseConnection;
import com.gsb.gestion.model.Comptes;
import com.gsb.gestion.model.Transaction;
import com.gsb.gestion.model.TypeDeCompte;
import com.gsb.gestion.model.TypeDeTransaction;

public class TransactionDao {
    
     private final Connection connection;

    public TransactionDao() throws SQLException {

        this.connection = DatabaseConnection.getConnection();
    }

    public void insertTransaction(Transaction transaction) throws SQLException {

        String sql = """
            INSERT INTO transaction (numero_compte, type, montant, frais)
            VALUES (?, ?::type_transaction, ?, ?)
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, transaction.getCompte().getNumeroDeCompte());
            ps.setString(2, transaction.getType().name());
            ps.setDouble(3, transaction.getMontant());
            ps.setDouble(4, transaction.getFrais()); 


            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erreur insertion compte", e);
        }
    }






    public List<Transaction> selectAllTransacOfAcc(String numeroDeCompte) {

        List<Transaction> transactions = new ArrayList<>();

        String sql = """
            SELECT * FROM transaction
            WHERE numero_compte = ?
            ORDER BY date_transaction DESC
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, numeroDeCompte);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapToTransaction(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lecture transactions", e);
        }

        return transactions;
    }






    private Transaction mapToTransaction(ResultSet rs) throws SQLException {

        Transaction t = new Transaction();
        t.getCompte().setNumeroDeCompte(rs.getString("numero_compte"));
        t.setType(TypeDeTransaction.valueOf(rs.getString("type")));
        t.setMontant(rs.getDouble("montant"));
        t.setFrais(rs.getDouble("frais"));

        return t;
    }



}
