SELECT 
        A.ID as id,
        A.Name as name,
        A.Logo, 
        A.DownLoadLink,
        A.OneKeyPack,
        A.OneKeyPackSize,
        A.Version,
        A.Size,
        -- 星级
        A.Rank AS starRating,
        A.HaveData
FROM AndroidApp A 
INNER JOIN AppCount B ON A.ID=B.SoftID 
WHERE 
     A.Catalog = ? and A.SubCatalog = ?
ORDER by B.MonthTimes DESC limit ?