truncate table CatalogConvertor ;
insert into CatalogConvertor ( marketName, catalog, subCatalog, targetcatalog , targetSubCatalog )
 select distinct 'eoemarket' , 1, y.subCatalog , 1,  x.id   from Catalog x, MarketApp y 
 where x.name = '系统' and pid = 1  and y.subCatalogName = '系统工具' ;

insert into CatalogConvertor ( marketName, catalog, subCatalog, targetcatalog , targetSubCatalog )
 select distinct 'eoemarket' , 1, y.subCatalog , 1,  x.id   from Catalog x, MarketApp y 
 where x.name = '生活' and pid = 1  and y.subCatalogName = '生活娱乐' ;

insert into CatalogConvertor ( marketName, catalog, subCatalog, targetcatalog , targetSubCatalog )
 select distinct 'eoemarket' , 1, y.subCatalog , 1,  x.id   from Catalog x, MarketApp y 
 where x.name = '社交' and pid = 1  and y.subCatalogName = '社区交友' ;

insert into CatalogConvertor ( marketName, catalog, subCatalog, targetcatalog , targetSubCatalog )
 select distinct 'eoemarket' , 1, y.subCatalog , 1,  x.id   from Catalog x, MarketApp y 
 where x.name = '通讯' and pid = 1  and y.subCatalogName = '网络通信' ;

insert into CatalogConvertor ( marketName, catalog, subCatalog, targetcatalog , targetSubCatalog )
 select distinct 'eoemarket' , 1, y.subCatalog , 1,  x.id   from Catalog x, MarketApp y 
 where x.name = '图书' and pid = 1  and y.subCatalogName = '小说漫画' ;

insert into CatalogConvertor ( marketName, catalog, subCatalog, targetcatalog , targetSubCatalog )
 select distinct 'eoemarket' , 1, y.subCatalog , 1,  x.id   from Catalog x, MarketApp y 
 where x.name = '影音' and pid = 1  and y.subCatalogName = '铃声视频' ;

insert into CatalogConvertor ( marketName, catalog, subCatalog, targetcatalog , targetSubCatalog )
 select distinct 'eoemarket' , 1, y.subCatalog , 1,  x.id   from Catalog x, MarketApp y 
 where x.name = '美化' and pid = 1  and y.subCatalogName = '桌面美化' ;

insert into CatalogConvertor ( marketName, catalog, subCatalog, targetcatalog , targetSubCatalog )
 select distinct 'eoemarket' , 1, y.subCatalog , 1,  x.id   from Catalog x, MarketApp y 
 where x.name = '商业' and pid = 1  and y.subCatalogName = '商务财经' ;

insert into CatalogConvertor ( marketName, catalog, subCatalog, targetcatalog , targetSubCatalog )
 select distinct 'eoemarket' , 1, y.subCatalog , 1,  x.id   from Catalog x, MarketApp y 
 where x.name = '系统' and pid = 1  and y.subCatalogName = '系统工具' ;




insert into CatalogConvertor ( marketName, catalog, subCatalog, targetcatalog , targetSubCatalog )
 select distinct 'eoemarket' , 2, y.subCatalog , 2,  x.id   from Catalog x, MarketApp y 
 where x.name = '飞行射击' and pid = 2  and y.subCatalogName = '飞行射击' ;
 
insert into CatalogConvertor ( marketName, catalog, subCatalog, targetcatalog , targetSubCatalog )
 select distinct 'eoemarket' , 2, y.subCatalog , 2,  x.id   from Catalog x, MarketApp y 
 where x.name = '动作冒险' and pid = 2  and y.subCatalogName = '动作冒险' ;
 
insert into CatalogConvertor ( marketName, catalog, subCatalog, targetcatalog , targetSubCatalog )
 select distinct 'eoemarket' , 2, y.subCatalog , 2,  x.id   from Catalog x, MarketApp y 
 where x.name = '休闲益智' and pid = 2  and y.subCatalogName = '益智休闲' ;
 
insert into CatalogConvertor ( marketName, catalog, subCatalog, targetcatalog , targetSubCatalog )
 select distinct 'eoemarket' , 2, y.subCatalog , 2,  x.id   from Catalog x, MarketApp y 
 where x.name = '角色养成' and pid = 2  and y.subCatalogName = '角色扮演' ;
 
insert into CatalogConvertor ( marketName, catalog, subCatalog, targetcatalog , targetSubCatalog )
 select distinct 'eoemarket' , 2, y.subCatalog , 2,  x.id   from Catalog x, MarketApp y 
 where x.name = '棋牌游戏' and pid = 2  and y.subCatalogName = '棋牌天地' ;
 
insert into CatalogConvertor ( marketName, catalog, subCatalog, targetcatalog , targetSubCatalog )
 select distinct 'eoemarket' , 2, y.subCatalog , 2,  x.id   from Catalog x, MarketApp y 
 where x.name = '策略经营' and pid = 2  and y.subCatalogName = '经营策略' ;
 
insert into CatalogConvertor ( marketName, catalog, subCatalog, targetcatalog , targetSubCatalog )
 select distinct 'eoemarket' , 2, y.subCatalog , 2,  x.id   from Catalog x, MarketApp y 
 where x.name = '竞速体育' and pid = 2  and y.subCatalogName = '体育竞技' ;
 
insert into CatalogConvertor ( marketName, catalog, subCatalog, targetcatalog , targetSubCatalog )
 select distinct 'eoemarket' , 2, y.subCatalog , 2,  x.id   from Catalog x, MarketApp y 
 where x.name = '其它' and pid = 2  and y.subCatalogName = '网络游戏' ;