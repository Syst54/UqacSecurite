# -----------------------------------------------------------------------------
#       TABLE : UTILISATEUR
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS UTILISATEUR
 (
   ID int(11) NOT NULL AUTO_INCREMENT ,
   NOM1 CHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
   NOM2 CHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
   ADRESSE1 CHAR(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
   ADRESSE2 CHAR(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
   COMPTEMAIL1 CHAR(100) NULL  ,
   COMPTEMAIL2 CHAR(100) NULL  ,
   DATEENREGISTREMENTBDD DATETIME NULL  ,
   CHAINEUNIQUE CHAR(32) NULL  
   , PRIMARY KEY (ID) 
 ) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

# -----------------------------------------------------------------------------
#       TABLE : LOCALISATION
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS LOCALISATION
 (
   ID int(11) NOT NULL AUTO_INCREMENT ,
   ID_UTILISATEUR int(11) NOT NULL  ,
   COORDONNEES CHAR(32) NULL  
   , PRIMARY KEY (ID) 
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

# -----------------------------------------------------------------------------
#       TABLE : PHOTO
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS PHOTO
 (
   ID int(11) NOT NULL AUTO_INCREMENT ,
   ID_UTILISATEUR int(11) NOT NULL  ,
   DATEPRISE DATETIME NULL  ,
   CHEMINFICHIER CHAR(255) NULL  ,
   VENANTDE CHAR(32) NULL  
   , PRIMARY KEY (ID) 
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

# -----------------------------------------------------------------------------
#       TABLE : CONTACT
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS CONTACT
 (
   ID int(11) NOT NULL AUTO_INCREMENT ,
   ID_UTILISATEUR int(11) NOT NULL  ,
   NOM CHAR(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
   NUMEROTEL1 CHAR(20) NULL  ,
   NUMEROTEL2 CHAR(20) NULL  ,
   EMAIL1 CHAR(100) NULL  ,
   EMAIL2 CHAR(100) NULL  ,
   ADRESSE CHAR(255) NULL  ,
   NOMORGANISATION CHAR(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL  ,
   TITREORGANISATION CHAR(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL  ,
   NBCONTACTSTEL int(6) NULL  ,
   NBCONTACTSSMS int(6) NULL  
   , PRIMARY KEY (ID) 
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

# -----------------------------------------------------------------------------
#       TABLE : SMS
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS SMS
 (
   ID int(11) NOT NULL AUTO_INCREMENT ,
   ID_CONVERSATION int(11) NOT NULL  ,
   BODYSMS TEXT CHARACTER SET utf8 COLLATE utf8_general_ci NULL  ,
   DATEENVOI DATETIME NULL  ,
   ENVOYEPARUTILISATEUR int(1) NULL  
   , PRIMARY KEY (ID) 
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

# -----------------------------------------------------------------------------
#       TABLE : CONVERSATION
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS CONVERSATION
 (
   ID int(11) NOT NULL AUTO_INCREMENT ,
   ID_CONTACT int(11) NOT NULL  ,
   ID_UTILISATEUR int(11) NOT NULL  ,
   THREADID CHAR(32) NULL  
   , PRIMARY KEY (ID) 
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;

# -----------------------------------------------------------------------------
#       TABLE : TELEPHONE
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS TELEPHONE
 (
   ID int(11) NOT NULL AUTO_INCREMENT ,
   ID_UTILISATEUR int(11) NOT NULL  ,
   NUMEROTEL CHAR(32) NULL  ,
   MODELE CHAR(32) NULL  ,
   VERSION CHAR(32) NULL  ,
   IMEI CHAR(32) NULL  
   , PRIMARY KEY (ID) 
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;


# -----------------------------------------------------------------------------
#       TABLE : HISTORIQUE
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS HISTORIQUE
 (
   ID int(11) NOT NULL AUTO_INCREMENT ,
   ID_UTILISATEUR int(11) NOT NULL  ,
   TITRE CHAR(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
   URL TEXT CHARACTER SET utf8 COLLATE utf8_general_ci NULL
   , PRIMARY KEY (ID) 
) ENGINE=InnoDB CHARACTER SET utf8 COLLATE utf8_general_ci;



# -----------------------------------------------------------------------------
#       CREATION DES REFERENCES DE TABLE
# -----------------------------------------------------------------------------


ALTER TABLE LOCALISATION 
  ADD FOREIGN KEY FK_LOCALISATION_UTILISATEUR (ID_UTILISATEUR)
      REFERENCES UTILISATEUR (ID) ;


ALTER TABLE PHOTO 
  ADD FOREIGN KEY FK_PHOTO_UTILISATEUR (ID_UTILISATEUR)
      REFERENCES UTILISATEUR (ID) ;


ALTER TABLE CONTACT 
  ADD FOREIGN KEY FK_CONTACT_UTILISATEUR (ID_UTILISATEUR)
      REFERENCES UTILISATEUR (ID) ;


ALTER TABLE SMS 
  ADD FOREIGN KEY FK_SMS_CONVERSATION (ID_CONVERSATION)
      REFERENCES CONVERSATION (ID) ;


ALTER TABLE CONVERSATION 
  ADD FOREIGN KEY FK_CONVERSATION_CONTACT (ID_CONTACT)
      REFERENCES CONTACT (ID) ;


ALTER TABLE CONVERSATION 
  ADD FOREIGN KEY FK_CONVERSATION_UTILISATEUR (ID_UTILISATEUR)
      REFERENCES UTILISATEUR (ID) ;


ALTER TABLE TELEPHONE 
  ADD FOREIGN KEY FK_TELEPHONE_UTILISATEUR (ID_UTILISATEUR)
      REFERENCES UTILISATEUR (ID) ;

	  
ALTER TABLE HISTORIQUE 
  ADD FOREIGN KEY FK_HISTORIQUE_UTILISATEUR (ID_UTILISATEUR)
      REFERENCES UTILISATEUR (ID) ;
