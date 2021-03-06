DROP TABLE app;

CREATE TABLE app (
  app_id INT(11) NOT NULL AUTO_INCREMENT,
  app_name VARCHAR(100) NOT NULL,
  app_type_id INT(11) NOT NULL,
  description VARCHAR(2000),
  status_id INT(1) NOT NULL,
  created_by VARCHAR(50) NOT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT 0,
  modified_by VARCHAR(50),
  modified_date TIMESTAMP NOT NULL DEFAULT 0,
  PRIMARY KEY(app_id),
  UNIQUE(app_name)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

CREATE TRIGGER appInsTrg BEFORE INSERT ON app
   FOR EACH ROW
   SET NEW.created_by = USER(),
       NEW.created_date = NOW(),
       NEW.modified_by = USER(),
       NEW.modified_date = NOW();

CREATE TRIGGER appUpdTrg BEFORE UPDATE ON app
  FOR EACH ROW
  SET NEW.modified_by = USER(),
      NEW.modified_date = NOW();

DROP TABLE app_type;

CREATE TABLE app_type (
  app_type_id INT(11) NOT NULL AUTO_INCREMENT,
  app_type_name VARCHAR(100) NOT NULL,
  description VARCHAR(2000),
  status_id INT(1) NOT NULL,
  created_by VARCHAR(50) NOT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT 0,
  modified_by VARCHAR(50),
  modified_date TIMESTAMP NOT NULL DEFAULT 0,
  PRIMARY KEY(app_type_id),
  UNIQUE(app_type_name)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

CREATE TRIGGER appTypeInsTrg BEFORE INSERT ON app_type
   FOR EACH ROW
   SET NEW.created_by = USER(),
       NEW.created_date = NOW(),
       NEW.modified_by = USER(),
       NEW.modified_date = NOW();

CREATE TRIGGER appTypeUpdTrg BEFORE UPDATE ON app_type
  FOR EACH ROW
  SET NEW.modified_by = USER(),
      NEW.modified_date = NOW();

DROP TABLE initiative;

CREATE TABLE initiative (
  initiative_id INT(11) NOT NULL AUTO_INCREMENT,
  initiative_name VARCHAR(100) NOT NULL,
  initiative_type_id INT(11) NOT NULL,
  description VARCHAR(2000),
  status_id INT(1) NOT NULL,
  created_by VARCHAR(50) NOT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT 0,
  modified_by VARCHAR(50),
  modified_date TIMESTAMP NOT NULL DEFAULT 0,
  PRIMARY KEY(initiative_id),
  UNIQUE(initiative_name)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

CREATE TRIGGER initiativeInsTrg BEFORE INSERT ON initiative
   FOR EACH ROW
   SET NEW.created_by = USER(),
       NEW.created_date = NOW(),
       NEW.modified_by = USER(),
       NEW.modified_date = NOW();

CREATE TRIGGER initiativeUpdTrg BEFORE UPDATE ON initiative
  FOR EACH ROW
  SET NEW.modified_by = USER(),
      NEW.modified_date = NOW();

DROP TABLE initiative_type;

CREATE TABLE initiative_type (
  initiative_type_id INT(11) NOT NULL AUTO_INCREMENT,
  initiative_type_name VARCHAR(100) NOT NULL,
  description VARCHAR(2000),
  status_id INT(1) NOT NULL,
  created_by VARCHAR(50) NOT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT 0,
  modified_by VARCHAR(50),
  modified_date TIMESTAMP NOT NULL DEFAULT 0,
  PRIMARY KEY(initiative_type_id),
  UNIQUE(initiative_type_name)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

CREATE TRIGGER initiativeTypeInsTrg BEFORE INSERT ON initiative_type
   FOR EACH ROW
   SET NEW.created_by = USER(),
       NEW.created_date = NOW(),
       NEW.modified_by = USER(),
       NEW.modified_date = NOW();

CREATE TRIGGER initiativeTypeUpdTrg BEFORE UPDATE ON initiative_type
  FOR EACH ROW
  SET NEW.modified_by = USER(),
      NEW.modified_date = NOW();

DROP TABLE menu;

CREATE TABLE menu (
  menu_id INT(11) NOT NULL AUTO_INCREMENT,
  parent_menu_id INT(11) NOT NULL,
  menu_name VARCHAR(100) NOT NULL,
  controller_url VARCHAR(100) NOT NULL,
  display_order INT(11) NOT NULL,
  menu_hint VARCHAR(2000),
  description VARCHAR(2000),
  status_id INT(1) NOT NULL,
  created_by VARCHAR(50) NOT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT 0,
  modified_by VARCHAR(50),
  modified_date TIMESTAMP NOT NULL DEFAULT 0,
  PRIMARY KEY(menu_id),
  UNIQUE(menu_name)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

CREATE TRIGGER menuInsTrg BEFORE INSERT ON menu
   FOR EACH ROW
   SET NEW.created_by = USER(),
       NEW.created_date = NOW(),
       NEW.modified_by = USER(),
       NEW.modified_date = NOW();

CREATE TRIGGER menuUpdTrg BEFORE UPDATE ON menu
  FOR EACH ROW
  SET NEW.modified_by = USER(),
      NEW.modified_date = NOW();


DROP TABLE package;

CREATE TABLE package (
  package_id INT(11) NOT NULL AUTO_INCREMENT,
  package_name VARCHAR(100) NOT NULL,
  package_type_id INT(11) NOT NULL,
  description VARCHAR(2000),
  status_id INT(1) NOT NULL,
  created_by VARCHAR(50) NOT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT 0,
  modified_by VARCHAR(50),
  modified_date TIMESTAMP NOT NULL DEFAULT 0,
  PRIMARY KEY(package_id),
  UNIQUE(package_name)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

CREATE TRIGGER packageInsTrg BEFORE INSERT ON package
   FOR EACH ROW
   SET NEW.created_by = USER(),
       NEW.created_date = NOW(),
       NEW.modified_by = USER(),
       NEW.modified_date = NOW();

CREATE TRIGGER packageUpdTrg BEFORE UPDATE ON package
  FOR EACH ROW
  SET NEW.modified_by = USER(),
      NEW.modified_date = NOW();

DROP TABLE package_type;

CREATE TABLE package_type (
  package_type_id INT(11) NOT NULL AUTO_INCREMENT,
  package_type_name VARCHAR(100) NOT NULL,
  description VARCHAR(2000),
  status_id INT(1) NOT NULL,
  created_by VARCHAR(50) NOT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT 0,
  modified_by VARCHAR(50),
  modified_date TIMESTAMP NOT NULL DEFAULT 0,
  PRIMARY KEY(package_type_id),
  UNIQUE(package_type_name)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

CREATE TRIGGER packageTypeInsTrg BEFORE INSERT ON package_type
   FOR EACH ROW
   SET NEW.created_by = USER(),
       NEW.created_date = NOW(),
       NEW.modified_by = USER(),
       NEW.modified_date = NOW();

CREATE TRIGGER packageTypeUpdTrg BEFORE UPDATE ON package_type
  FOR EACH ROW
  SET NEW.modified_by = USER(),
      NEW.modified_date = NOW();

DROP TABLE project;

CREATE TABLE project (
  project_id INT(11) NOT NULL AUTO_INCREMENT,
  requestor_id INT(11) NOT NULL,
  associated_project_id INT(11),
  initiative_id INT(11),
  ticket_id INT(11),
  service_id INT(11),
  app_id INT(11),
  package_id INT(11),
  legacy_owner VARCHAR(100),
  business_unit VARCHAR(100),
  planned_start_date TIMESTAMP,
  planned_end_date TIMESTAMP,  
  description VARCHAR(2000),
  status_id INT(1) NOT NULL,
  created_by VARCHAR(50) NOT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT 0,
  modified_by VARCHAR(50),
  modified_date TIMESTAMP NOT NULL DEFAULT 0,
  PRIMARY KEY(project_id)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

CREATE TRIGGER projectInsTrg BEFORE INSERT ON project
   FOR EACH ROW
   SET NEW.created_by = USER(),
       NEW.created_date = NOW(),
       NEW.modified_by = USER(),
       NEW.modified_date = NOW();

CREATE TRIGGER projectUpdTrg BEFORE UPDATE ON project
  FOR EACH ROW
  SET NEW.modified_by = USER(),
      NEW.modified_date = NOW();

DROP TABLE project_team;

CREATE TABLE project_team (
  project_team_id INT(11) NOT NULL AUTO_INCREMENT,
  project_id INT(11) NOT NULL,
  resource_id INT(11) NOT NULL,
  role_id INT(11) NOT NULL,
  support_level_id INT(11),
  est_hours INT(11),
  assigned_date TIMESTAMP NOT NULL,
  description VARCHAR(2000),
  status_id INT(1) NOT NULL,
  created_by VARCHAR(50) NOT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT 0,
  modified_by VARCHAR(50),
  modified_date TIMESTAMP NOT NULL DEFAULT 0,
  PRIMARY KEY(project_team_id),
  UNIQUE(project_id, resource_id)  
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

CREATE TRIGGER projectTeamInsTrg BEFORE INSERT ON project_team
   FOR EACH ROW
   SET NEW.created_by = USER(),
       NEW.created_date = NOW(),
       NEW.modified_by = USER(),
       NEW.modified_date = NOW();

CREATE TRIGGER projectTeamUpdTrg BEFORE UPDATE ON project_team
  FOR EACH ROW
  SET NEW.modified_by = USER(),
      NEW.modified_date = NOW();

DROP TABLE resource;

CREATE TABLE resource (
  resource_id INT(11) NOT NULL AUTO_INCREMENT,
  resource_name VARCHAR(100) NOT NULL,
  resource_number VARCHAR(100) NOT NULL,
  description VARCHAR(2000),
  status_id INT(1) NOT NULL,
  created_by VARCHAR(50) NOT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT 0,
  modified_by VARCHAR(50),
  modified_date TIMESTAMP NOT NULL DEFAULT 0,
  PRIMARY KEY(resource_id),
  UNIQUE(resource_name)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

CREATE TRIGGER resourceInsTrg BEFORE INSERT ON resource
   FOR EACH ROW
   SET NEW.created_by = USER(),
       NEW.created_date = NOW(),
       NEW.modified_by = USER(),
       NEW.modified_date = NOW();

CREATE TRIGGER resourceUpdTrg BEFORE UPDATE ON resource
  FOR EACH ROW
  SET NEW.modified_by = USER(),
      NEW.modified_date = NOW();

DROP TABLE role;

CREATE TABLE role (
  role_id INT(11) NOT NULL AUTO_INCREMENT,
  role_name VARCHAR(100) NOT NULL,
  description VARCHAR(2000),
  status_id INT(1) NOT NULL,
  created_by VARCHAR(50) NOT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT 0,
  modified_by VARCHAR(50),
  modified_date TIMESTAMP NOT NULL DEFAULT 0,
  PRIMARY KEY(role_id),
  UNIQUE(role_name)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

CREATE TRIGGER roleInsTrg BEFORE INSERT ON role
   FOR EACH ROW
   SET NEW.created_by = USER(),
       NEW.created_date = NOW(),
       NEW.modified_by = USER(),
       NEW.modified_date = NOW();

CREATE TRIGGER roleUpdTrg BEFORE UPDATE ON role
  FOR EACH ROW
  SET NEW.modified_by = USER(),
      NEW.modified_date = NOW();

DROP TABLE service;

CREATE TABLE service (
  service_id INT(11) NOT NULL AUTO_INCREMENT,
  service_name VARCHAR(100) NOT NULL,
  description VARCHAR(2000),
  status_id INT(1) NOT NULL,
  created_by VARCHAR(50) NOT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT 0,
  modified_by VARCHAR(50),
  modified_date TIMESTAMP NOT NULL DEFAULT 0,
  PRIMARY KEY(service_id),
  UNIQUE(service_name)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

CREATE TRIGGER serviceInsTrg BEFORE INSERT ON service
   FOR EACH ROW
   SET NEW.created_by = USER(),
       NEW.created_date = NOW(),
       NEW.modified_by = USER(),
       NEW.modified_date = NOW();

CREATE TRIGGER serviceUpdTrg BEFORE UPDATE ON service
  FOR EACH ROW
  SET NEW.modified_by = USER(),
      NEW.modified_date = NOW();

DROP TABLE support_level;

CREATE TABLE support_level (
  support_level_id INT(11) NOT NULL AUTO_INCREMENT,
  support_level_name VARCHAR(100) NOT NULL,
  description VARCHAR(2000),
  status_id INT(1) NOT NULL,
  created_by VARCHAR(50) NOT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT 0,
  modified_by VARCHAR(50),
  modified_date TIMESTAMP NOT NULL DEFAULT 0,
  PRIMARY KEY(support_level_id),
  UNIQUE(support_level_name)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

CREATE TRIGGER supportLevelInsTrg BEFORE INSERT ON support_level
   FOR EACH ROW
   SET NEW.created_by = USER(),
       NEW.created_date = NOW(),
       NEW.modified_by = USER(),
       NEW.modified_date = NOW();

CREATE TRIGGER supportLevelUpdTrg BEFORE UPDATE ON support_level
  FOR EACH ROW
  SET NEW.modified_by = USER(),
      NEW.modified_date = NOW();

DROP TABLE ticket;

CREATE TABLE ticket (
  ticket_id INT(11) NOT NULL AUTO_INCREMENT,
  ticket_reference_number VARCHAR(100),
  ticket_created_date TIMESTAMP,
  description VARCHAR(2000),
  status_id INT(1) NOT NULL,
  created_by VARCHAR(50) NOT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT 0,
  modified_by VARCHAR(50),
  modified_date TIMESTAMP NOT NULL DEFAULT 0,
  PRIMARY KEY(ticket_id),
  UNIQUE(ticket_reference_number, ticket_created_date)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

CREATE TRIGGER tickeInsTrg BEFORE INSERT ON ticket
   FOR EACH ROW
   SET NEW.created_by = USER(),
       NEW.created_date = NOW(),
       NEW.modified_by = USER(),
       NEW.modified_date = NOW();

CREATE TRIGGER tickeUpdTrg BEFORE UPDATE ON ticket
  FOR EACH ROW
  SET NEW.modified_by = USER(),
      NEW.modified_date = NOW();

DROP TABLE user;

CREATE TABLE user (
  user_id INT(11) NOT NULL AUTO_INCREMENT,
  username VARCHAR(50) NOT NULL,
  password VARCHAR(64) NOT NULL,
  ldap_username VARCHAR(50),
  ldap_password VARCHAR(64),
  first_name VARCHAR(100) NOT NULL,
  last_name VARCHAR(100) NOT NULL,
  last_login DATETIME,
  email_address VARCHAR(100) NOT NULL,
  status_id INT(1) NOT NULL,
  created_by VARCHAR(50) NOT NULL,
  created_date TIMESTAMP NOT NULL DEFAULT 0,
  modified_by VARCHAR(50),
  modified_date TIMESTAMP NOT NULL DEFAULT 0,
  PRIMARY KEY(user_id),
  UNIQUE(username),
  UNIQUE(email_address)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

CREATE TRIGGER userInsTrg BEFORE INSERT ON user
   FOR EACH ROW
   SET NEW.created_by = USER(),
       NEW.created_date = NOW(),
       NEW.modified_by = USER(),
       NEW.modified_date = NOW();

CREATE TRIGGER userUpdTrg BEFORE UPDATE ON user
  FOR EACH ROW
  SET NEW.modified_by = USER(),
      NEW.modified_date = NOW();

