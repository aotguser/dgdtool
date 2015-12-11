SET FOREIGN_KEY_CHECKS=0;

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

CREATE TABLE error (
  error_id INT(11) NOT NULL AUTO_INCREMENT,
  user_id INT(11) NOT NULL,
  error_page VARCHAR(200) NOT NULL,
  error_call VARCHAR(2000) NOT NULL,
  error_text TEXT NOT NULL,  
  created_by VARCHAR(50) NOT NULL,
  create_date TIMESTAMP NOT NULL DEFAULT 0,
  PRIMARY KEY(error_id),
  INDEX (user_id), FOREIGN KEY (user_id) REFERENCES user (user_id) 
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

CREATE TRIGGER errorInsTrg BEFORE INSERT ON error
   FOR EACH ROW
   SET NEW.created_by = USER(),
       NEW.create_date = NOW();

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

CREATE TABLE message (
  message_id INT(11) NOT NULL AUTO_INCREMENT,
  sender_id INT(11) NOT NULL,
  recipient_id INT(11) NOT NULL,
  sender_deleted INT(11) NOT NULL,
  recipient_deleted INT(11) NOT NULL,
  recipient_viewed INT(11) NOT NULL,
  message VARCHAR(256),
  status_id INT(11),
  create_date TIMESTAMP NOT NULL DEFAULT 0,
  modified_date TIMESTAMP NOT NULL DEFAULT 0,
  PRIMARY KEY(message_id) 
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

CREATE TRIGGER messageInsTrg BEFORE INSERT ON message
   FOR EACH ROW
   SET NEW.create_date = NOW(),
       NEW.modified_date = NOW();

CREATE TRIGGER messageUpdTrg BEFORE UPDATE ON message
  FOR EACH ROW
  SET NEW.modified_date = NOW();

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

CREATE TABLE partner (
  partner_id INT(11) NOT NULL AUTO_INCREMENT,
  partner_name VARCHAR(200) NOT NULL,
  partner_key VARCHAR(400),
  partner_base_url VARCHAR(2000),
  partner_broker_url VARCHAR(2000),
  description VARCHAR(2000),
  created_by VARCHAR(50) NOT NULL,
  create_date TIMESTAMP NOT NULL DEFAULT 0,
  modified_by VARCHAR(50),
  modified_date TIMESTAMP NOT NULL DEFAULT 0,
  PRIMARY KEY(partner_id) 
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

CREATE TRIGGER partnerInsTrg BEFORE INSERT ON partner
   FOR EACH ROW
   SET NEW.created_by = USER(),
       NEW.create_date = NOW(),
       NEW.modified_by = USER(),
       NEW.modified_date = NOW();

CREATE TRIGGER partnerUpdTrg BEFORE UPDATE ON partner
  FOR EACH ROW
  SET NEW.modified_by = USER(),
      NEW.modified_date = NOW();

CREATE TABLE partner_api (
  partner_api_id INT(11) NOT NULL AUTO_INCREMENT,
  partner_id INT(11) NOT NULL,
  partner_req_url VARCHAR(2000),
  partner_req_obj VARCHAR(100),
  partner_res_url VARCHAR(2000),
  asp_method VARCHAR(100),
  asp_app_id INT(11),
  description VARCHAR(2000),
  created_by VARCHAR(50) NOT NULL,
  create_date TIMESTAMP NOT NULL DEFAULT 0,
  modified_by VARCHAR(50),
  modified_date TIMESTAMP NOT NULL DEFAULT 0,
  PRIMARY KEY(partner_api_id),
  INDEX (partner_id), FOREIGN KEY (partner_id) REFERENCES partner (partner_id),
  INDEX (asp_app_id), FOREIGN KEY (asp_app_id) REFERENCES app (app_id)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

CREATE TRIGGER partner_apiInsTrg BEFORE INSERT ON partner_api
   FOR EACH ROW
   SET NEW.created_by = USER(),
       NEW.create_date = NOW(),
       NEW.modified_by = USER(),
       NEW.modified_date = NOW();

CREATE TRIGGER partner_apiUpdTrg BEFORE UPDATE ON partner_api
  FOR EACH ROW
  SET NEW.modified_by = USER(),
      NEW.modified_date = NOW();

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

CREATE TABLE status (
  id INT(11) NOT NULL AUTO_INCREMENT,
  status VARCHAR(25) NOT NULL,  
  description VARCHAR(2000) NOT NULL,
  created_by VARCHAR(50) NOT NULL,
  create_date TIMESTAMP NOT NULL DEFAULT 0,
  modified_by VARCHAR(50),
  modified_date TIMESTAMP NOT NULL DEFAULT 0,
  PRIMARY KEY(id),
  UNIQUE(status)  
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

CREATE TRIGGER statusInsTrg BEFORE INSERT ON status
   FOR EACH ROW
   SET NEW.created_by = USER(),
       NEW.create_date = NOW(),
       NEW.modified_by = USER(),
       NEW.modified_date = NOW();

CREATE TRIGGER statusUpdTrg BEFORE UPDATE ON status
  FOR EACH ROW
  SET NEW.modified_by = USER(),
      NEW.modified_date = NOW();

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

CREATE TABLE user_role (
  user_role_id INT(11) NOT NULL AUTO_INCREMENT,
  role_id INT(11) NOT NULL,
  user_id INT(11) NOT NULL,  
  created_by VARCHAR(50) NOT NULL,
  create_date TIMESTAMP NOT NULL DEFAULT 0,
  modified_by VARCHAR(50),
  modified_date TIMESTAMP NOT NULL DEFAULT 0,
  PRIMARY KEY(user_role_id),
  INDEX (role_id), FOREIGN KEY (role_id) REFERENCES role (role_id),
  INDEX (user_id), FOREIGN KEY (user_id) REFERENCES user (role_id)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

CREATE TRIGGER user_roleInsTrg BEFORE INSERT ON user_role
   FOR EACH ROW
   SET NEW.created_by = USER(),
       NEW.create_date = NOW(),
       NEW.modified_by = USER(),
       NEW.modified_date = NOW();

CREATE TRIGGER user_roleUpdTrg BEFORE UPDATE ON user_role
  FOR EACH ROW
  SET NEW.modified_by = USER(),
      NEW.modified_date = NOW();

