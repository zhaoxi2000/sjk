SELECT 
    x.ID, x.Name, 
    x.DownLoadLink , x.Logo, 
    x.OneKeyPack,
    x.OneKeyPackSize,
    x.Version,
    x.size,
    -- 下载次数
    y.DownTimes, 
    -- 简介
    x.Description,
    -- 星级
    x.Rank AS starRating,
    x.HaveData
FROM AppMgr.AndroidApp x
LEFT JOIN AppMgr.AppCount y
    ON x.ID = y.SoftId
WHERE 
    x.ID IN ( ? )
