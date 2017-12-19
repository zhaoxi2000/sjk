SELECT 
   Id, Name,
   Size,
   -- 星级
   Rank AS starRating,
   DownLoadLink, Logo,
   OneKeyPack,
   OneKeyPackSize,
   Version,
   HaveData
FROM AndroidApp
WHERE 
    Catalog = :typeId
    AND Subcatalog = :subTypeId
