# -----------------------------------------------------------------------------
#       TABLE : UTILISATEUR
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS UTILISATEUR
 (
   ID int(11) NOT NULL AUTO_INCREMENT ,
   NOM CHAR(32) NULL  ,
   GMAIL CHAR(32) NULL  ,
   CHAINEUNIQUE CHAR(32) NULL  
   , PRIMARY KEY (ID) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : LOCALISATION
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS LOCALISATION
 (
   ID int(11) NOT NULL AUTO_INCREMENT ,
   ID_UTILISATEUR CHAR(32) NOT NULL  ,
   COORDONNEES CHAR(32) NULL  
   , PRIMARY KEY (ID) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : PHOTO
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS PHOTO
 (
   ID int(11) NOT NULL AUTO_INCREMENT ,
   ID_UTILISATEUR CHAR(32) NOT NULL  ,
   DATEPRISE CHAR(32) NULL  ,
   VENANTDE CHAR(32) NULL  
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
   ADRESSE CHAR(32) NULL  ,
   IMPORTANCE CHAR(32) NULL  
   , PRIMARY KEY (ID) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : SMS
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS SMS
 (
   ID int(11) NOT NULL AUTO_INCREMENT ,
   ID_CONVERSATION CHAR(32) NOT NULL  ,
   BODY CHAR(32) NULL  ,
   DATEENVOI CHAR(32) NULL  ,
   ENVOYEPARUTILISATEUR CHAR(32) NULL  
   , PRIMARY KEY (ID) 
 ) 
 comment = "";

# -----------------------------------------------------------------------------
#       TABLE : CONVERSATION
# -----------------------------------------------------------------------------

CREATE TABLE IF NOT EXISTS CONVERSATION
 (
   ID int(11) NOT NULL AUTO_INCREMENT ,
   ID_CONTACT CHAR(32) NOT NULL  ,
   ID_UTILISATEUR CHAR(32) NOT NULL  ,
   THREADID CHAR(32) NULL  
   , PRIMARY KEY (ID) 
 ) 
 comment = "";

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

