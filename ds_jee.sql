-- phpMyAdmin SQL Dump
-- version 4.1.14
-- http://www.phpmyadmin.net
--
-- Client :  127.0.0.1
-- Généré le :  Lun 04 Mai 2015 à 19:32
-- Version du serveur :  5.6.17
-- Version de PHP :  5.5.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données :  `ds_jee`
--

-- --------------------------------------------------------

--
-- Structure de la table `administrateur`
--

CREATE TABLE IF NOT EXISTS `administrateur` (
  `CIN` decimal(8,0) NOT NULL,
  `NOM` varchar(25) DEFAULT NULL,
  `PRENOM` varchar(25) DEFAULT NULL,
  `MAIL` varchar(50) DEFAULT NULL,
  `MDP` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`CIN`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `categorie`
--

CREATE TABLE IF NOT EXISTS `categorie` (
  `ID_CATEGORIE` int(11) NOT NULL,
  `CATEGORIE` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID_CATEGORIE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE IF NOT EXISTS `client` (
  `CIN` decimal(8,0) NOT NULL,
  `NOM` varchar(25) DEFAULT NULL,
  `PRENOM` varchar(25) DEFAULT NULL,
  `MAIL` varchar(50) DEFAULT NULL,
  `MDP` varchar(100) DEFAULT NULL,
  `ADRESSE` varchar(256) DEFAULT NULL,
  `DATE_NAISSANCE` date DEFAULT NULL,
  `TELEPHONE` decimal(12,0) DEFAULT NULL,
  PRIMARY KEY (`CIN`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `client`
--

INSERT INTO `client` (`CIN`, `NOM`, `PRENOM`, `MAIL`, `MDP`, `ADRESSE`, `DATE_NAISSANCE`, `TELEPHONE`) VALUES
('-11', 'z', 'z', 'z', 'zz', 'zzz', '2015-05-06', '141452'),
('0', NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `encherir`
--

CREATE TABLE IF NOT EXISTS `encherir` (
  `ID_ENCHERIR` int(11) NOT NULL,
  `ID_PRODUIT` int(11) NOT NULL,
  `CIN` decimal(8,0) NOT NULL,
  `PRIX` float DEFAULT NULL,
  `DATE` datetime DEFAULT NULL,
  PRIMARY KEY (`CIN`,`ID_PRODUIT`,`ID_ENCHERIR`),
  KEY `FK_ENCHERIR2` (`ID_PRODUIT`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE IF NOT EXISTS `produit` (
  `ID_PRODUIT` int(11) NOT NULL,
  `ID_CATEGORIE` int(11) NOT NULL,
  `CIN_CLIENT` decimal(8,0) NOT NULL,
  `NOM_PRODUIT` varchar(50) DEFAULT NULL,
  `DESCRIPTION` varchar(256) DEFAULT NULL,
  `IMAGE` varchar(75) DEFAULT NULL,
  `PRIX_INITIALE` float DEFAULT NULL,
  `DATE_DEBUT` datetime DEFAULT NULL,
  `DATE_FIN` datetime DEFAULT NULL,
  `CIN_ADMIN` decimal(8,0) DEFAULT NULL,
  `VALIDITE` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID_PRODUIT`),
  KEY `FK_APPARTENIR` (`ID_CATEGORIE`),
  KEY `FK_METTRE` (`CIN_CLIENT`),
  KEY `FK_VALIDER` (`CIN_ADMIN`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `encherir`
--
ALTER TABLE `encherir`
  ADD CONSTRAINT `FK_ENCHERIR1` FOREIGN KEY (`CIN`) REFERENCES `client` (`CIN`),
  ADD CONSTRAINT `FK_ENCHERIR2` FOREIGN KEY (`ID_PRODUIT`) REFERENCES `produit` (`ID_PRODUIT`);

--
-- Contraintes pour la table `produit`
--
ALTER TABLE `produit`
  ADD CONSTRAINT `FK_APPARTENIR` FOREIGN KEY (`ID_CATEGORIE`) REFERENCES `categorie` (`ID_CATEGORIE`),
  ADD CONSTRAINT `FK_METTRE` FOREIGN KEY (`CIN_CLIENT`) REFERENCES `client` (`CIN`),
  ADD CONSTRAINT `FK_VALIDER` FOREIGN KEY (`CIN_ADMIN`) REFERENCES `administrateur` (`CIN`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
