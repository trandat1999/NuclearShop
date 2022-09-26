INSERT INTO `nuclearshop`.`tbl_role` (`created_by`, `last_modified_by`,`role_name`)
SELECT * FROM (SELECT 'admin' as created_by , 'admin' as last_modified_by, 'ADMIN' as role_name) AS tmp
WHERE NOT EXISTS (
        SELECT role_name FROM `nuclearshop`.`tbl_role` WHERE role_name = 'ADMIN'
    ) LIMIT 1;
INSERT INTO `nuclearshop`.`tbl_role` (`created_by`, `last_modified_by`, `role_name`)
SELECT * FROM (SELECT 'admin' as created_by, 'admin' as last_modified_by,'SALE' as role_name) AS tmp
WHERE NOT EXISTS (
        SELECT role_name FROM `nuclearshop`.`tbl_role` WHERE role_name = 'SALE'
    ) LIMIT 1;
INSERT INTO `nuclearshop`.`tbl_role` (`created_by`, `last_modified_by`, `role_name`)
SELECT * FROM (SELECT 'admin' as created_by, 'admin' as last_modified_by, 'STAFF_WAREHOUSE' as role_name) AS tmp
WHERE NOT EXISTS (
        SELECT role_name FROM `nuclearshop`.`tbl_role` WHERE role_name= 'STAFF_WAREHOUSE'
    ) LIMIT 1;
INSERT INTO `nuclearshop`.`tbl_role` (`created_by`, `last_modified_by`, `role_name`)
SELECT * FROM (SELECT 'admin' as created_by, 'admin' as last_modified_by, 'SHIPPER' as role_name) AS tmp
WHERE NOT EXISTS (
        SELECT role_name FROM `nuclearshop`.`tbl_role` WHERE role_name= 'SHIPPER'
    ) LIMIT 1;
INSERT INTO `nuclearshop`.`tbl_role` (`created_by`, `last_modified_by`, `role_name`)
SELECT * FROM (SELECT 'admin' as created_by, 'admin' as last_modified_by, 'ACCOUNTANT' as role_name) AS tmp
WHERE NOT EXISTS (
        SELECT role_name FROM `nuclearshop`.`tbl_role` WHERE role_name= 'ACCOUNTANT'
    ) LIMIT 1;
INSERT INTO `nuclearshop`.`tbl_role` (`created_by`, `last_modified_by`,`role_name`)
SELECT * FROM (SELECT 'admin' as created_by , 'admin' as last_modified_by, 'USER' as role_name) AS tmp
WHERE NOT EXISTS (
        SELECT role_name FROM `nuclearshop`.`tbl_role` WHERE role_name = 'USER'
    ) LIMIT 1;