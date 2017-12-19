SELECT 
    app.ID ,
    app.Name, 
    app.Logo, 
    app.DownLoadLink,
    app.HaveData, 
    app.OneKeyPack,
    app.OneKeyPackSize,
    app.Version as version,
    app.Size,
    -- 星级
    app.Rank AS starRating,
    app.Language, 
     (SELECT  Name  FROM AppDict WHERE  TypeID=91 AND ID = app.OSVersionGroupID) AS  osVersionGroup ,
      PublisherShortName AS publisherName,
      Rank AS rate,
      Description, 
      UpdateDate, 
      UpdateInfo,
      EditDate AS lastEditDate
FROM 
        AndroidApp app 
WHERE ID = ?