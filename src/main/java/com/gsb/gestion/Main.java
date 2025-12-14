package com.gsb.gestion;

import com.gsb.gestion.model.Comptes;
import com.gsb.gestion.model.TypeDeCompte;
import com.gsb.gestion.model.TypeDeTransaction;
import com.gsb.gestion.service.ComptesService;
import com.gsb.gestion.service.TransactionService;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {

        ComptesService comptesService = new ComptesService();
        TransactionService transactionService = new TransactionService();

        int choix;

        do {
            afficherMenu();
            choix = lireInt("Votre choix : ");

            switch (choix) {
                case 1 -> creerCompte(comptesService);
                case 2 -> afficherComptes(comptesService);
                case 3 -> ajouterTransaction(transactionService);
                case 4 -> listerTransactions(transactionService);
                case 0 -> System.out.println("Au revoir !");
                default -> System.out.println("Choix invalide !");
            }

        } while (choix != 0);
    }

  


    private static void afficherMenu() {
        System.out.println("\n===== GESTION BANCAIRE =====");
        System.out.println("1. Créer un compte");
        System.out.println("2. Afficher les comptes");
        System.out.println("3. Ajouter une transaction");
        System.out.println("4. Lister les transactions d’un compte");
        System.out.println("0. Quitter");
    }


    private static void creerCompte(ComptesService comptesService) throws Exception {

        String numero = comptesService.generateNumAcc();

String typeChoisi = lireString("Choisir le type : ");
TypeDeCompte type = TypeDeCompte.valueOf(typeChoisi);

        Comptes compte = new Comptes();
        compte.setNumeroDeCompte(numero);
        compte.setType(type);
        compte.setSolde(0);

        if (type == TypeDeCompte.EPARGNE) {
            int duree = lireInt("Durée de blocage (en mois) : ");
            compte.setDureeDeblocage(duree);
        }

        comptesService.creatAcc(compte);
        System.out.println("Compte créé avec succès !");
        System.out.println("Numéro de compte : " + numero);
    }


    private static void afficherComptes(ComptesService comptesService) {

        List<Comptes> comptes = comptesService.searchAcc();

        if (comptes.isEmpty()) {
            System.out.println("Aucun compte trouvé.");
            return;
        }

        System.out.println("\n--- LISTE DES COMPTES ---");
        for (Comptes c : comptes) {
            System.out.println(
                "Numéro: " + c.getNumeroDeCompte() +
                " | Type: " + c.getType().getLabel() +
                " | Solde: " + c.getSolde() + " FCFA"
            );
        }
    }




    private static void ajouterTransaction(TransactionService transactionService) throws Exception {

        String numero = lireString("Numéro de compte : ");
        
String typeChoisi = lireString("Choisir le type : ");
TypeDeTransaction type = TypeDeTransaction.valueOf(typeChoisi);

        double montant = lireDouble("Montant : ");

        boolean ok = transactionService.creatTransac(numero, type, montant);

        if (ok) {
            System.out.println("Transaction effectuée avec succès !");
        } else {
            System.out.println("Échec de la transaction.");
        }
    }


    private static void listerTransactions(TransactionService transactionService) {

        String numero = lireString("Numéro de compte : ");

        var transactions = transactionService.searchTransacByAcc(numero);

        if (transactions.isEmpty()) {
            System.out.println("Aucune transaction trouvée.");
            return;
        }

        System.out.println("\n--- TRANSACTIONS DU COMPTE " + numero + " ---");
        transactions.forEach(t ->
            System.out.println(
                t.getType().getLabel() +
                " | Montant: " + t.getMontant() +
                " | Frais: " + t.getFrais()
            )
        );
    }





    private static int lireInt(String msg) {
        System.out.print(msg);
        return Integer.parseInt(scanner.nextLine());
    }

    private static double lireDouble(String msg) {
        System.out.print(msg);
        return Double.parseDouble(scanner.nextLine());
    }

    private static String lireString(String msg) {
        System.out.print(msg);
        return scanner.nextLine().trim();
    }
}
