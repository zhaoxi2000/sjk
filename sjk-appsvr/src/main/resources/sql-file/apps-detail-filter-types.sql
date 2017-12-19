SELECT 
     app.ID ,
     app.Name, 
     app.Logo, 
     app.DownLoadLink, 
     app.OneKeyPack,
     app.OneKeyPackSize,
     app.Version,
     app.HaveData,
     -- 星级
     app.Rank AS starRating,
     Version,
     Language,
     Size AS size,
     PublisherShortName AS publisherName,
     Rank AS rate,
     Description, 
     UpdateDate, 
     EditDate AS lastEditDate
FROM 
        AndroidApp app 
WHERE ID = ?