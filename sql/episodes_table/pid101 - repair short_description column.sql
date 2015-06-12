-- fix of corrupt data 
ALTER TABLE `pcmdb`.`episodes` CHANGE COLUMN `short_description` `short_description` VARCHAR(600) NULL DEFAULT NULL;

select count(*) from episodes
where substring(description, 1, 100) <> substring(short_description, 1, 100);

update episodes
set short_description = null;

update episodes
set short_description = substring(description, 1, 600)
where char_length(description) > 600;

SELECT count( * )
FROM `episodes`
WHERE char_length( description ) > 600;

SELECT short_description, description
FROM `episodes`
WHERE char_length( short_description ) >550;

SELECT char_length(description), substring(description, 1,600)
FROM podcasts;

-- FINAL SOlution - get rid of the columns :)
ALTER TABLE `pcmdb`.`episodes` DROP COLUMN `short_description` ;
ALTER TABLE `pcmdb`.`podcasts` DROP COLUMN `short_description` ;