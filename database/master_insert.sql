INSERT INTO user (`user_id`, `username`, `password`, `first_name`, `last_name`, `email_address`, `status_id`, `created_by`, `modified_by`) VALUES ('1', 'goochalba', 'gooch123', 'Mike', 'Alba', 'michaelpalba@yahoo.com', '1', '1', '1');

INSERT INTO user (`user_id`, `username`, `password`, `first_name`, `last_name`, `email_address`, `status_id`, `created_by`, `modified_by`) VALUES ('2', 'bryon', 'bryon123', 'Bryon', 'Rickey', 'bryon@asp.com', '1', '1', '1');

INSERT INTO user (`user_id`, `username`, `password`, `first_name`, `last_name`, `email_address`, `status_id`, `created_by`, `modified_by`) VALUES ('3', 'jeff', 'jeff123', 'Jeff', 'Schuster', 'jeff@asp.com', '1', '1', '1');

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (-1, 0, 'Login', '/dgdtool/login', 'This is never seen. The Login Page is the Root Menu item.', 'This is never seen. The Login Page is the Root Menu item.', 0, 1, USER(), USER());


INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (1, 0, 'Factory Service', '/dgdtool/factoryService', 'This is the Factory Service Menu item.', 'This is the Factory Service Menu item.', 0, 1, USER(), USER());

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (7, 1, 'Performance Archive', '/dgdtool/perfArchive', 'This is the Performance Archive Menu item.', 'This is the Performance Archive Menu item.', 1, 1, USER(), USER());

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (8, 1, 'App Retirement - Archive', '/dgdtool/appRetArch', 'This is the App Retirement - Archive Menu item.', 'This is the App Retirement - Archive Menu item.', 2, 1, USER(), USER());

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (9, 1, 'App Retirement - No Archive', '/dgdtool/appRetNoArch', 'This is the App Retirement - No Archive Menu item.', 'This is the App Retirement - No Archive Menu item.', 3, 1, USER(), USER());

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (10, 1, 'RIM Purge', '/dgdtool/rimPurge', 'This is the RIM Purge Menu item.', 'This is the RIM Purge Menu item.', 4, 1, USER(), USER());

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (11, 1, 'Data Retirement', '/dgdtool/dataRet', 'This is the Data Retirement Menu item.', 'This is the Data Retirement Menu item.', 5, 1, USER(), USER());

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (12, 1, 'Test Data Management', '/dgdtool/testDataMgmt', 'This is the Test Data Management Menu item.', 'This is the Test Data Management Menu item.', 6, 1, USER(), USER());



INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (2, 0, 'eDiscovery', '/dgdtool/eDiscovery', 'This is the eDiscovery Menu item.', 'This is the eDiscovery Menu item.', 0, 1, USER(), USER());

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (13, 2, 'Execute Hold Sweep', '/dgdtool/execHoldSweep', 'This is the Execute Hold Sweep Menu item.', 'This is the Execute Hold Sweep Menu item.', 1, 1, USER(), USER());

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (14, 2, 'Execute Hold Release', '/dgdtool/execHoldRelease', 'This is the Execute Hold Release Menu item.', 'This is the Execute Hold Release Menu item.', 2, 1, USER(), USER());

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (15, 2, 'Search DG Metadata', '/dgdtool/searchDGMeta', 'This is the Search DG Metadata Menu item.', 'This is the Search DG Metadata Menu item.', 3, 1, USER(), USER());

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (16, 2, 'Search Hold Repository', '/dgdtool/searchHoldRepo', 'This is the Search Hold Repository Menu item.', 'This is the Search Hold Repository Menu item.', 4, 1, USER(), USER());



INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (3, 0, 'Dashboards', '/dgdtool/dashboards', 'This is the Dashboard Screen Menu item.', 'This is the Dashboard Screen Menu item.', 0, 1, USER(), USER());

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (17, 3, 'Legal Holds', '/dgdtool/legalHolds', 'This is the Legal Holds Screen Menu item.', 'This is the Legal Holds Screen Menu item.', 1, 1, USER(), USER());

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (18, 3, 'Service Stats', '/dgdtool/serviceStats', 'This is the Service Stats Screen Menu item.', 'This is the Service Stats Screen Menu item.', 2, 1, USER(), USER());

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (19, 3, 'Quarterly ROI', '/dgdtool/qtrROI', 'This is the Quarterly ROI Screen Menu item.', 'This is the Quarterly ROI Screen Menu item.', 3, 1, USER(), USER());

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (20, 3, 'Quarterly Key Metrics', '/dgdtool/qtrKeyMetrics', 'This is the Quarterly Key Metrics Screen Menu item.', 'This is the Quarterly Key Metrics Screen Menu item.', 4, 1, USER(), USER());




INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (4, 0, 'Jobs', '/dgdtool/jobs', 'This is the Jobs Screen Menu item.', 'This is the Jobs Screen Menu item.', 0, 1, USER(), USER());

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (21, 4, 'Schedule Jobs', '/dgdtool/scheduleJobs', 'This is the Schedule Jobs Screen Menu item.', 'This is the Schedule Jobs Screen Menu item.', 1, 1, USER(), USER());

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (22, 4, 'Monitor Jobs', '/dgdtool/monitorJobs', 'This is the Monitor Jobs Screen Menu item.', 'This is the Monitor Jobs Screen Menu item.', 2, 1, USER(), USER());

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (23, 4, 'Manage Jobs', '/dgdtool/manageJobs', 'This is the Manage Jobs Screen Menu item.', 'This is the Manage Jobs Screen Menu item.', 3, 1, USER(), USER());

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (24, 4, 'Logging', '/dgdtool/logging', 'This is the Logging Screen Menu item.', 'This is the Logging Screen Menu item.', 4, 1, USER(), USER());



INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (5, 0, 'Analytics', '/dgdtool/analytics', 'This is the Analytics Screen Menu item.', 'This is the Analytics Screen Menu item.', 0, 1, USER(), USER());

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (25, 5, 'ROI Assessor', '/dgdtool/roiAssessor', 'This is the ROI Assessor Screen Menu item.', 'This is the ROI Assessor Screen Menu item.', 1, 1, USER(), USER());

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (26, 5, 'Cost Analyzer', '/dgdtool/costAnalyzer', 'This is the Cost Analyzer Screen Menu item.', 'This is the Cost Analyzer Screen Menu item.', 2, 1, USER(), USER());



INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (6, 0, 'Admin', '/dgdtool/admin', 'This is the Administration Screen Menu item.', 'This is the Administration Screen Menu item.', 0, 1, USER(), USER());

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (27, 6, 'My Account', '/dgdtool/admin', 'This is the My Account Screen Menu item.', 'This is the My Account Screen Menu item.', 1, 1, USER(), USER());

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (28, 6, 'Manage User', '/dgdtool/admin', 'This is the Manage User Screen Menu item.', 'This is the Manage User Screen Menu item.', 2, 1, USER(), USER());

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (29, 6, 'Manage Roles', '/dgdtool/admin', 'This is the Manage Roles Screen Menu item.', 'This is the Manage Roles Screen Menu item.', 3, 1, USER(), USER());

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (30, 6, 'Notifications', '/dgdtool/admin', 'This is the Notifications Screen Menu item.', 'This is the Notifications Screen Menu item.', 4, 1, USER(), USER());

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (31, 6, 'Data Management - Reference Tables', '/dgdtool/admin', 'This is the Data Management - Reference Tables Screen Menu item.', 'This is the Data Management - Reference Tables Screen Menu item.', 5, 1, USER(), USER());

INSERT INTO menu(menu_id, parent_menu_id, menu_name, controller_url, menu_hint, description, display_order, status_id, created_by, modified_by) 
VALUES (32, 6, 'Data Management - Core Tables', '/dgdtool/admin', 'This is the Data Management - Core Tables Screen Menu item.', 'This is the Data Management - Core Tables Screen Menu item.', 6, 1, USER(), USER());

