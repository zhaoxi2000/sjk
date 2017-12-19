
UPDATE MarketApp SET AppFetchTime='2012-03-20' 
,MarketUpdateTime='2012-03-22' , MarketApkScanTime = '2012-03-23'

WHERE LastUpdateTime>='2013-03-25 00:13:37'



select pkname from MarketApp
WHERE LastUpdateTime>='2013-03-25 00:13:37';

delete from App where pkname = 'com.yingsoft.zhiyeyaoshi.Activity' or pkname = 'com.yingsoft.jinrong.Activity' or pkname = 'cn.ecook'