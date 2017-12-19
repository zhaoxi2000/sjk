truncate table Catalog ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 1, 0, '软件', 0 , '应用,软件,App') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 2, 0, '游戏', 0 , '游戏,Game') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 3, 1, '通讯', 1 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 4, 1, '社交', 2 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 5, 1, '新闻', 3 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 6, 1, '图书', 4 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 7, 1, '美化', 5 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 8, 1, '影音', 6 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 9, 1, '生活', 7 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 10, 1, '购物', 8 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 11, 1, '摄影', 9 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 12, 1, '浏览器', 10 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 13, 1, '输入法', 11 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 14, 1, '系统', 12, '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 15, 1, '安全', 13, '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 16, 1, '办公', 14 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 17, 1, '天气', 15, '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 18, 1, '娱乐', 16 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 19, 1, '教育', 17 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 20, 1, '商业', 18 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 21, 1, '财务', 19 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 22, 1, '地图', 20 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 23, 1, '旅行', 21 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 24, 1, '体育', 22 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 25, 1, '医疗', 23 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 26, 1, '其它', 24 , '') ;

insert into Catalog (id, pid, name , rank , keywords ) values ( 41, 2, '棋牌游戏', 1 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 42, 2, '休闲益智', 2 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 43, 2, '策略经营', 3 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 44, 2, '动作冒险', 4 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 45, 2, '飞行射击', 5 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 46, 2, '角色养成', 6 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 47, 2, '竞速体育', 7 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 48, 2, '模拟辅助', 8 , '') ;
insert into Catalog (id, pid, name , rank , keywords ) values ( 49, 2, '其它', 9 , '') ;

-- 系统工具	系统
update App set subcatalog = ( select id from Catalog where name = '系统' and pid = 1  ) 
where exists (
	select 1 from MarketApp x
	where 
		catalog = 1 and subCatalogName = '系统工具' and marketName = 'eoemarket' 
		and marketAppId = id 
);
-- 生活娱乐	生活	
update App set subcatalog = ( select id from Catalog where name = '生活' and pid = 1  ) 
where exists (
	select 1 from MarketApp x
	where 
		catalog = 1 and subCatalogName = '生活娱乐' and marketName = 'eoemarket' 
		and marketAppId = id 
);
-- 社区交友	社交	
update App set subcatalog = ( select id from Catalog where name = '社交' and pid = 1  ) 
where exists (
	select 1 from MarketApp x
	where 
		subCatalogName = '社区交友' and marketName = 'eoemarket' 
		and marketAppId = id 
);
-- 网络通信	通讯	
update App set subcatalog = ( select id from Catalog where name = '通讯' and pid = 1  ) 
where exists (
	select 1 from MarketApp x
	where 
		catalog = 1 and subCatalogName = '网络通信' and marketName = 'eoemarket' 
		and marketAppId = id 
);
-- 小说漫画	图书	
update App set subcatalog = ( select id from Catalog where name = '图书' and pid = 1  ) 
where exists (
	select 1 from MarketApp x
	where 
		subCatalogName = '小说漫画' and marketName = 'eoemarket' 
		and marketAppId = id 
);
-- 铃声视频	影音	
update App set subcatalog = ( select id from Catalog where name = '影音' and pid = 1  ) 
where exists (
	select 1 from MarketApp x
	where 
		catalog = 1 and subCatalogName = '铃声视频' and marketName = 'eoemarket' 
		and marketAppId = id 
);
-- 桌面美化	美化	
update App set subcatalog = ( select id from Catalog where name = '美化' and pid = 1  ) 
where exists (
	select 1 from MarketApp x
	where 
		catalog = 1 and subCatalogName = '桌面美化' and marketName = 'eoemarket' 
		and marketAppId = id 
);
-- 商务财经	商业	
update App set subcatalog = ( select id from Catalog where name = '商业' and pid = 1  ) 
where exists (
	select 1 from MarketApp x
	where 
		catalog = 1 and subCatalogName = '商务财经' and marketName = 'eoemarket' 
		and marketAppId = id 
);
-- 系统工具	系统	
update App set subcatalog = ( select id from Catalog where name = '系统' and pid = 1  ) 
where exists (
	select 1 from MarketApp x
	where 
		catalog = 1 and subCatalogName = '系统工具' and marketName = 'eoemarket' 
		and marketAppId = id 
);



-- 飞行射击	飞行射击 
update App set subcatalog = ( select id from Catalog where name = '飞行射击' and pid = 2  ) 
where exists (
	select 1 from MarketApp x
	where 
		catalog = 2 and subCatalogName = '飞行射击' and marketName = 'eoemarket' 
		and marketAppId = id 
);
-- 动作冒险	动作冒险 
update App set subcatalog = ( select id from Catalog where name = '动作冒险' and pid = 2  ) 
where exists (
	select 1 from MarketApp x
	where 
		catalog = 2 and subCatalogName = '动作冒险' and marketName = 'eoemarket' 
		and marketAppId = id 
);
-- 益智休闲	休闲益智 
update App set subcatalog = ( select id from Catalog where name = '休闲益智' and pid = 2  ) 
where exists (
	select 1 from MarketApp x
	where 
		catalog = 2 and subCatalogName = '益智休闲' and marketName = 'eoemarket' 
		and marketAppId = id 
);
-- 角色扮演	角色养成 
update App set subcatalog = ( select id from Catalog where name = '角色养成' and pid = 2  ) 
where exists (
	select 1 from MarketApp x
	where 
		catalog = 2 and subCatalogName = '角色扮演' and marketName = 'eoemarket' 
		and marketAppId = id 
);
-- 棋牌天地	棋牌游戏 
update App set subcatalog = ( select id from Catalog where name = '棋牌游戏' and pid = 2  ) 
where exists (
	select 1 from MarketApp x
	where 
		catalog = 2 and subCatalogName = '棋牌天地' and marketName = 'eoemarket' 
		and marketAppId = id 
);
-- 经营策略	策略经营 
update App set subcatalog = ( select id from Catalog where name = '策略经营' and pid = 2  ) 
where exists (
	select 1 from MarketApp x
	where 
		catalog = 2 and subCatalogName = '经营策略' and marketName = 'eoemarket' 
		and marketAppId = id 
);
-- 体育竞技	竞速体育 
update App set subcatalog = ( select id from Catalog where name = '竞速体育' and pid = 2  ) 
where exists (
	select 1 from MarketApp x
	where 
		catalog = 2 and subCatalogName = '体育竞技' and marketName = 'eoemarket' 
		and marketAppId = id 
);
-- 网络游戏	其它 
update App set subcatalog = ( select id from Catalog where name = '其它' and pid = 2  ) 
where exists (
	select 1 from MarketApp x
	where 
		catalog = 2 and subCatalogName = '网络游戏' and marketName = 'eoemarket' 
		and marketAppId = id 
);