
# -----------------------------------------------------------------------------
#       TABLE : UTILISATEUR
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS UTILISATEUR
 (
   ID int(11) NOT NULL AUTO_INCREMENT ,
   NOM CHAR(32) NULL  ,
   GMAIL CHAR(32) NULL  
   , PRIMARY KEY (ID) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : CONTACT
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS CONTACT
 (
   ID int(11) NOT NULL AUTO_INCREMENT ,
   ID_UTILISATEUR CHAR(32) NOT NULL  ,
   NOM CHAR(32) NULL  ,
   NUMEROTEL1 CHAR(32) NULL  ,
   NUMEROTEL2 CHAR(32) NULL  ,
   EMAIL CHAR(32) NULL  ,
   ADRESSE CHAR(32) NULL  
   , PRIMARY KEY (ID) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       INDEX DE LA TABLE CONTACT
# -----------------------------------------------------------------------------


CREATE  INDEX I_FK_CONTACT_UTILISATEUR
     ON CONTACT (ID_CONTIENT ASC);

# -----------------------------------------------------------------------------
#       TABLE : TELEPHONE
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS TELEPHONE
 (
   ID int(11) NOT NULL AUTO_INCREMENT ,
   ID_UTILISATEUR CHAR(32) NOT NULL  ,
   NUMEROTEL CHAR(32) NULL  ,
   MODELE CHAR(32) NULL  ,
   VERSION CHAR(32) NULL  ,
   IMEI CHAR(32) NULL  
   , PRIMARY KEY (ID) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       INDEX DE LA TABLE TELEPHONE
# -----------------------------------------------------------------------------


CREATE  INDEX I_FK_TELEPHONE_UTILISATEUR
     ON TELEPHONE (ID_POSSEDE ASC);


# -----------------------------------------------------------------------------
#       CREATION DES REFERENCES DE TABLE
# -----------------------------------------------------------------------------


ALTER TABLE CONTACT 
  ADD FOREIGN KEY FK_CONTACT_UTILISATEUR (ID_CONTIENT)
      REFERENCES UTILISATEUR (ID) ;


ALTER TABLE TELEPHONE 
  ADD FOREIGN KEY FK_TELEPHONE_UTILISATEUR (ID_POSSEDE)
      REFERENCES UTILISATEUR (ID) ;

