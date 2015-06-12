SELECT table_name "Name",
( data_length + index_length ) / 1024 /
1024 "Table Size in MB",
( data_free )/ 1024 / 1024 "Free Space in MB"
FROM information_schema.TABLES
where table_schema="pcm_db"; 