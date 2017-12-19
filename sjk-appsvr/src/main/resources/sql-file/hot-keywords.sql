-- 热门且有结果的搜索关键字
SELECT 
    KeyWords , KeyWordsPY
FROM AppMgr.so_search_keywords
ORDER BY DaySearchTimes DESC
LIMIT 20
