-- 精品推荐软件/游戏
SELECT 
    x.ID,x.Name,
    x.PKName,
    x.DownLoadLink, 
    x.OneKeyPack,
    x.OneKeyPackSize,
    x.Version,
    x.Logo,
    x.Size,
    -- 星级
    x.Rank AS starRating,
    x.HaveData
FROM AndroidApp x
LEFT JOIN C_TuiJian y
ON y.AppId = x.Id
WHERE 
    typeID = ?
ORDER BY y.OrderID