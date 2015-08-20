-- change name column from name to display_name
ALTER TABLE podcast_db.users CHANGE name display_name VARCHAR(45);

-- add registration date column
ALTER TABLE podcast_db.users ADD registration_date DATETIME NULL;
