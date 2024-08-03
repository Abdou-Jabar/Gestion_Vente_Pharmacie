CREATE DATABASE GESTIONPHARMACIE
    DEFAULT CHARACTER SET = 'utf8mb4';

use GESTIONPHARMACIE

CREATE Table VENTE
(
    CODE_VENTE INT PRIMARY KEY AUTO_INCREMENT,
    NOM_PRODUIT CHAR(50) NOT NULL,
    NOM_CLIENT CHAR(50) NOT NULL,
    NOM_VENDEUR CHAR(50) NOT NULL,
    PRIX_VENTE DOUBLE NOT NULL,
    QUANTITE INT NOT NULL,
    TOTAL FLOAT NOT NULL
);

SELECT * FROM `VENTE`;