
DROP TABLE IF EXISTS Franchise;
DROP TABLE IF EXISTS Dealer;

CREATE TABLE Franchise (
  id varchar(36) NOT NULL,
  name varchar(100) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY Franchise_id_index (id),
  UNIQUE KEY Franchise_name_index (name)
);

CREATE TABLE Dealer (
  id varchar(36) NOT NULL,
  name varchar(150) NOT NULL,
  franchise_id varchar(36) NOT NULL,
  PRIMARY KEY (id),
  CONSTRAINT dealer_franchise_id_fk FOREIGN KEY (franchise_id) REFERENCES Franchise (id)
) ;
