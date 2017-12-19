SELECT 
    `id`, 
    `name`, 
    `desc`, 
    `img_path` AS `imgUrl`, 
    `status`, 
    `rank`, 
    `record_time` AS `recordTime` 
FROM `AppMgr`.`topic` 
WHERE
    `status` = ? 
ORDER BY `rank`
LIMIT ?,?

