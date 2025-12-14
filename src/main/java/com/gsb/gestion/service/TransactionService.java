package com.gsb.gestion.service;

import com.gsb.gestion.model.Comptes;
import com.gsb.gestion.model.Transaction;
import com.gsb.gestion.model.TypeDeTransaction;
import com.gsb.gestion.dao.TransactionDao;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class TransactionService {

    private final TransactionDao transacDao;
    private final ComptesService comptesService;

    public TransactionService() throws Exception {
        this.transacDao = new TransactionDao();
        this.comptesService = new ComptesService();
    }

    public boolean creatTransac(String numeroDeCompte,
                                TypeDeTransaction type,
                                double montant) throws SQLException {

        Comptes compte = comptesService.searchAccByNum(numeroDeCompte);

        if (compte == null) {
            return false;
        }

        double frais = 0;
        double montantFinal = montant;


        if (type == TypeDeTransaction.RETRAIT) {

            if (compte.isCompteEpargne()) {
                System.out.println(
                    "ERREUR : Retrait interdit sur compte épargne !");
                return false;
            }

            if (compte.isCompteCheque()) {
                frais = compte.getFraisTransaction(montant);
                montantFinal += frais;
                System.out.println("Frais : " + frais + " FCFA");
            }

            if (compte.getSolde() < montantFinal) {
                System.out.println("ERREUR : Solde insuffisant !");
                return false;
            }

            comptesService.initilizationSolde(
                numeroDeCompte,
                compte.getSolde() - montantFinal
            );
        }


        else {

            if (compte.isCompteCheque()) {
                frais = compte.getFraisTransaction(montant);
                montantFinal -= frais;
                System.out.println("Frais : " + frais + " FCFA");
            }

            comptesService.initilizationSolde(numeroDeCompte, compte.getSolde() + montantFinal);
        }


        Transaction transaction = new Transaction(montant, frais, type, numeroDeCompte);

        transacDao.insertTransaction(transaction);

        return true;
    }

    public List<TypeDeTransaction> listTypeTrans() {
        return Arrays.asList(TypeDeTransaction.values());
    }

    public List<Transaction> searchTransacByAcc(String numeroDeCompte) {
        return transacDao.selectAllTransacOfAcc(numeroDeCompte);
    }
}
