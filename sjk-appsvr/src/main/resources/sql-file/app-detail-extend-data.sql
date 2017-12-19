SELECT
    Id,
    Name,
    FileSize AS size,
    DownloadLink, 
    Screenshot,
    Description,
    SDPath AS sdPath
FROM 
    ExtendData 
where 
    Softids like 